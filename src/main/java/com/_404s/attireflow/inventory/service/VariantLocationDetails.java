package com._404s.attireflow.inventory.service;

import java.math.BigDecimal;

public class VariantLocationDetails {
    private final String locationName;
    private final String locationType;
    private final String binLocation;
    private final int quantity;
    private final BigDecimal totalValue;

    public VariantLocationDetails(String locationName, String locationType, String binLocation, int quantity, BigDecimal totalValue) {
        this.locationName = locationName;
        this.locationType = locationType;
        this.binLocation = binLocation;
        this.quantity = quantity;
        this.totalValue = totalValue;
    }

    public String getLocationName() { return locationName; }
    public String getLocationType() { return locationType; }
    public String getBinLocation() { return binLocation; }
    public int getQuantity() { return quantity; }
    public BigDecimal getTotalValue() { return totalValue; }
}
