package com._404s.attireflow.inventory.service;

import com._404s.attireflow.inventory.model.Variant;

import java.util.LinkedHashMap;
import java.util.Map;

public class InventoryRow {
    private final Variant variant;
    private final int totalStock;
    private final int locationCount;
    private final Map<String, Integer> stockByLocation;

    public InventoryRow(Variant variant, int totalStock, int locationCount, Map<String, Integer> stockByLocation) {
        this.variant = variant;
        this.totalStock = totalStock;
        this.locationCount = locationCount;
        this.stockByLocation = new LinkedHashMap<>(stockByLocation);
    }

    public Variant getVariant() { return variant; }
    public int getTotalStock() { return totalStock; }
    public int getLocationCount() { return locationCount; }
    public Map<String, Integer> getStockByLocation() { return stockByLocation; }
}
