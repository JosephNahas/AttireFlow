package com._404s.attireflow.inventory.web;

import com._404s.attireflow.inventory.model.Variant;
import com._404s.attireflow.inventory.repo.VariantRepository;
import com._404s.attireflow.inventory.service.InventoryService;
import com._404s.attireflow.inventory.service.VariantDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class InventoryController {

    private final InventoryService inventoryService;
    private final VariantRepository variantRepository;

    public InventoryController(InventoryService inventoryService, VariantRepository variantRepository) {
        this.inventoryService = inventoryService;
        this.variantRepository = variantRepository;
    }

    @GetMapping("/inventory")
    public String inventory(Model model) {
        model.addAttribute("rows", inventoryService.getInventory());
        return "inventory";
    }

    @GetMapping("/inventory/{id}")
    public String variantDetails(@PathVariable Long id, Model model) {
        VariantDetails details = inventoryService.getVariantDetails(id);
        model.addAttribute("details", details);
        return "variant-details";
    }

    @GetMapping("/inventory/{id}/edit")
    public String editVariant(@PathVariable Long id, Model model) {
        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Variant not found: " + id));
        model.addAttribute("variant", variant);
        return "variant-edit";
    }

    @PostMapping("/inventory/{id}/edit")
    public String updateVariant(@PathVariable Long id,
                                @RequestParam String title,
                                @RequestParam String category,
                                @RequestParam String size,
                                @RequestParam String color) {

        Variant variant = variantRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Variant not found: " + id));

        variant.setTitle(title);
        variant.setCategory(category);
        variant.setSize(size);
        variant.setColor(color);

        variantRepository.save(variant);
        return "redirect:/inventory";
    }

    @PostMapping("/inventory/{id}/delete")
    public String deleteVariant(@PathVariable Long id) {
        variantRepository.deleteById(id);
        return "redirect:/inventory";
    }
}
