package com._404s.attireflow.inventory.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com._404s.attireflow.inventory.service.DeliveryService;
import com._404s.attireflow.inventory.service.InventoryService;

@Controller
public class DashboardController {

    @Autowired
    private InventoryService inventoryService;
    
    @Autowired
    private DeliveryService deliveryService;

    @GetMapping("/dashboard")
    public String showDashboard(Model model) {
        var inventoryRows = inventoryService.getInventory();
        long totalProducts = inventoryRows.size();
        long lowStockCount = inventoryRows.stream()
                .filter(row -> row.getTotalStock() < 10)
                .count();
                
        var allDeliveries = deliveryService.getAllDeliveries(PageRequest.of(0, 1000));
        long pendingCount = 0;
        for (var delivery : allDeliveries.getContent()) {
            if ("Pending".equals(delivery.getStatus())) {
                pendingCount++;
            }
        }
        
        model.addAttribute("totalProducts", totalProducts);
        model.addAttribute("lowStockCount", lowStockCount);
        model.addAttribute("pendingDeliveries", pendingCount);
        model.addAttribute("totalWarehouses", 3); 
        
        var lowStockItems = inventoryRows.stream()
                .filter(row -> row.getTotalStock() < 10)
                .limit(5)
                .toList();
        model.addAttribute("lowStockItems", lowStockItems);
        
        return "dashboard";
    }
}