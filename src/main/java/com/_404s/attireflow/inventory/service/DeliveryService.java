package com._404s.attireflow.inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com._404s.attireflow.inventory.model.Delivery;
import com._404s.attireflow.inventory.repo.DeliveryRepository;

@Service
public class DeliveryService {
    
    @Autowired
    private DeliveryRepository deliveryRepository;
    
    public Page<Delivery> getAllDeliveries(Pageable pageable) {
        return deliveryRepository.findAll(pageable);
    }
    
    public Delivery saveDelivery(Delivery delivery) {
        return deliveryRepository.save(delivery);
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