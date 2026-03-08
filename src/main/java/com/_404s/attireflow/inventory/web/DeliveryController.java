package com._404s.attireflow.inventory.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com._404s.attireflow.inventory.model.Delivery;
import com._404s.attireflow.inventory.model.Variant;
import com._404s.attireflow.inventory.repo.VariantRepository;
import com._404s.attireflow.inventory.service.DeliveryService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/deliveries")
public class DeliveryController {
    
    @Autowired
    private DeliveryService deliveryService;
    
    @Autowired
    private VariantRepository variantRepository;

    @GetMapping
    public String listDeliveries(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String location,
            @RequestParam(defaultValue = "id,asc") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model) {

        if (status != null && status.trim().isEmpty()) status = null;
        if (type != null && type.trim().isEmpty()) type = null;
        if (location != null && location.trim().isEmpty()) location = null;

        String[] sortParams = sort.split(",");
        String sortField = sortParams[0];
        Sort.Direction direction = sortParams.length > 1 && "desc".equalsIgnoreCase(sortParams[1]) 
                                   ? Sort.Direction.DESC : Sort.Direction.ASC;
        Sort sortBy = Sort.by(direction, sortField);
 
        Pageable pageable = PageRequest.of(page, size, sortBy);
        
        Page<Delivery> deliveriesPage;
        if (status != null || type != null || location != null) {
            deliveriesPage = deliveryService.filterDeliveries(status, type, location, pageable);
        } else {
            deliveriesPage = deliveryService.getAllDeliveries(pageable);
        }
        
        model.addAttribute("deliveriesPage", deliveriesPage);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedType", type);
        model.addAttribute("selectedLocation", location);
        model.addAttribute("currentSort", sort);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        
        return "deliveries";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("delivery", new Delivery());
        model.addAttribute("variants", variantRepository.findAll());
        return "delivery-form";
    }

    @PostMapping("/save")
    public String saveDelivery(
            @Valid @ModelAttribute Delivery delivery,
            BindingResult result,
            @RequestParam("variantId") Long variantId,
            Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("variants", variantRepository.findAll());
            return "delivery-form";
        }
        
        Variant variant = variantRepository.findById(variantId).orElse(null);
        delivery.setVariant(variant);
        deliveryService.saveDelivery(delivery);
        return "redirect:/deliveries";
    }

    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id, @RequestParam String status) {
        deliveryService.updateDeliveryStatus(id, status);
        return "redirect:/deliveries";
    }
}