package com._404s.attireflow.inventory.service;

import com._404s.attireflow.inventory.model.Variant;
import com._404s.attireflow.inventory.model.VariantStock;
import com._404s.attireflow.inventory.repo.VariantRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class InventoryService {

    private final VariantRepository variantRepository;

    public InventoryService(VariantRepository variantRepository) {
        this.variantRepository = variantRepository;
    }

    public List<InventoryRow> getInventory() {
        List<Variant> variants = variantRepository.findAllWithStocks();
        List<InventoryRow> rows = new ArrayList<>();

        for (Variant v : variants) {
            Map<String, Integer> byLocation = new LinkedHashMap<>();
            int total = 0;

            for (VariantStock stock : v.getStocks()) {
                String locName = stock.getLocation().getName();
                int qty = stock.getQuantity();
                byLocation.put(locName, qty);
                total += qty;
            }

            rows.add(new InventoryRow(v, total, byLocation.size(), byLocation));
        }

        return rows;
    }

    public VariantDetails getVariantDetails(long variantId) {
        Variant v = variantRepository.findByIdWithStocks(variantId)
                .orElseThrow(() -> new IllegalArgumentException("Variant not found: " + variantId));

        int totalQty = 0;
        List<VariantLocationDetails> locations = new ArrayList<>();

        for (VariantStock stock : v.getStocks()) {
            int qty = stock.getQuantity();
            totalQty += qty;

            BigDecimal locValue = v.getUnitPrice().multiply(BigDecimal.valueOf(qty));
            locations.add(new VariantLocationDetails(
                    stock.getLocation().getName(),
                    stock.getLocation().getType(),
                    stock.getBinLocation(),
                    qty,
                    locValue
            ));
        }

        BigDecimal totalValue = v.getUnitPrice().multiply(BigDecimal.valueOf(totalQty));

        return new VariantDetails(
                v,
                totalQty,
                locations.size(),
                v.getUnitPrice(),
                totalValue,
                locations
        );
    }
}
