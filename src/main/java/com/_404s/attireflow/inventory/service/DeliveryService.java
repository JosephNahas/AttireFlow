package com._404s.attireflow.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com._404s.attireflow.inventory.model.Delivery;
import com._404s.attireflow.inventory.model.VariantStock;
import com._404s.attireflow.inventory.repo.DeliveryRepository;
import com._404s.attireflow.inventory.repo.VariantStockRepository;

@Service
public class DeliveryService {

    @Autowired
    private DeliveryRepository deliveryRepository;
    
    @Autowired
    private VariantStockRepository variantStockRepository;

    public Page<Delivery> getAllDeliveries(Pageable pageable) {
        return deliveryRepository.findAll(pageable);
    }

@Transactional
public Delivery saveDelivery(Delivery delivery) {
    Delivery savedDelivery = deliveryRepository.save(delivery);

    String warehouseLocationName = "Downtown Warehouse";
    
    VariantStock stock = variantStockRepository.findByVariantIdAndLocationName(
        delivery.getVariant().getId(), 
        warehouseLocationName
    );
    
    if (stock != null) {
        int newQuantity = stock.getQuantity() - delivery.getQuantity();
        if (newQuantity < 0) newQuantity = 0;
        stock.setQuantity(newQuantity);
        variantStockRepository.save(stock);
        System.out.println("Warehouse stock decreased to: " + newQuantity);
    }
    
    return savedDelivery;
}

    public Delivery getDeliveryById(Long id) {
        return deliveryRepository.findById(id).orElse(null);
    }

    public void updateDeliveryStatus(Long id, String status) {
        Delivery delivery = getDeliveryById(id);
        if (delivery != null) {
            delivery.setStatus(status);
            deliveryRepository.save(delivery);
        }
    }

    public Page<Delivery> filterDeliveries(String status, String type, String location, Pageable pageable) {
        return deliveryRepository.filterDeliveries(status, type, location, pageable);
    }
}