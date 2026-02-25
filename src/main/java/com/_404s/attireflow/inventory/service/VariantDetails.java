package com._404s.attireflow.inventory.service;

import com._404s.attireflow.inventory.model.Variant;

import java.math.BigDecimal;
import java.util.List;

public class VariantDetails {
    private final Variant variant;
    private final int totalStock;
    private final int locationCount;
    private final BigDecimal unitPrice;
    private final BigDecimal totalValue;
    private final List<VariantLocationDetails> locations;

    public VariantDetails(Variant variant,
                          int totalStock,
                          int locationCount,
                          BigDecimal unitPrice,
                          BigDecimal totalValue,
                          List<VariantLocationDetails> locations) {
        this.variant = variant;
        this.totalStock = totalStock;
        this.locationCount = locationCount;
        this.unitPrice = unitPrice;
        this.totalValue = totalValue;
        this.locations = locations;
    }

    public Variant getVariant() { return variant; }
    public int getTotalStock() { return totalStock; }
    public int getLocationCount() { return locationCount; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public BigDecimal getTotalValue() { return totalValue; }
    public List<VariantLocationDetails> getLocations() { return locations; }
}
