package com._404s.attireflow.inventory.model;

import jakarta.persistence.*;

@Entity
@Table(
        name = "variant_stock",
        uniqueConstraints = @UniqueConstraint(name = "uq_variant_location", columnNames = {"variant_id", "location_id"})
)
public class VariantStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "variant_id", nullable = false)
    private Variant variant;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id", nullable = false)
    private InventoryLocation location;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String binLocation;

    public VariantStock() {}

    public VariantStock(Variant variant, InventoryLocation location, int quantity, String binLocation) {
        this.variant = variant;
        this.location = location;
        this.quantity = quantity;
        this.binLocation = binLocation;
    }

    public Long getId() { return id; }
    public Variant getVariant() { return variant; }
    public InventoryLocation getLocation() { return location; }
    public int getQuantity() { return quantity; }
    public String getBinLocation() { return binLocation; }

    public void setVariant(Variant variant) { this.variant = variant; }
    public void setLocation(InventoryLocation location) { this.location = location; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setBinLocation(String binLocation) { this.binLocation = binLocation; }
}
