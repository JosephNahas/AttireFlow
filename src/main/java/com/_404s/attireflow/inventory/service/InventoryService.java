package com._404s.attireflow.inventory.service;

import com._404s.attireflow.inventory.model.InventoryLocation;
import com._404s.attireflow.inventory.model.Variant;
import com._404s.attireflow.inventory.model.VariantStock;
import com._404s.attireflow.inventory.repo.InventoryLocationRepository;
import com._404s.attireflow.inventory.repo.VariantRepository;
import com._404s.attireflow.inventory.repo.VariantStockRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class InventoryService {

    private final VariantRepository variantRepository;
    private final InventoryLocationRepository inventoryLocationRepository;
    private final VariantStockRepository variantStockRepository;

    public InventoryService(VariantRepository variantRepository,
                            InventoryLocationRepository inventoryLocationRepository,
                            VariantStockRepository variantStockRepository) {
        this.variantRepository = variantRepository;
        this.inventoryLocationRepository = inventoryLocationRepository;
        this.variantStockRepository = variantStockRepository;
    }

    public Page<InventoryRow> getInventory(String title, String size, String color, String category, Pageable pageable) {
        List<Variant> variants = variantRepository.findAllWithStocksFiltered(title, size, color, category);
        List<InventoryRow> rows = buildInventoryRows(variants);

        rows.sort(buildComparator(pageable));

        int start = (int) pageable.getOffset();
        int end = Math.min(start + pageable.getPageSize(), rows.size());
        List<InventoryRow> pageContent = start >= rows.size() ? Collections.emptyList() : rows.subList(start, end);

        return new PageImpl<>(pageContent, pageable, rows.size());
    }

    public List<String> getCategories() {
        return variantRepository.findDistinctCategories();
    }

    public List<String> getSizes() {
        return variantRepository.findDistinctSizes();
    }

    public List<String> getColors() {
        return variantRepository.findDistinctColors();
    }

    public List<InventoryLocation> getAllLocations() {
        return inventoryLocationRepository.findAllByOrderByNameAsc();
    }

    public List<String> getAllBinLocations() {
        return variantStockRepository.findDistinctBinLocations();
    }

    private List<InventoryRow> buildInventoryRows(List<Variant> variants) {
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

    private Comparator<InventoryRow> buildComparator(Pageable pageable) {
        if (pageable.getSort().isUnsorted()) {
            return Comparator.comparing(row -> row.getVariant().getId());
        }

        Comparator<InventoryRow> comparator = null;

        for (org.springframework.data.domain.Sort.Order order : pageable.getSort()) {
            Comparator<InventoryRow> current = comparatorFor(order.getProperty());
            if (current == null) {
                continue;
            }

            if (order.isDescending()) {
                current = current.reversed();
            }

            comparator = comparator == null ? current : comparator.thenComparing(current);
        }

        return comparator != null
                ? comparator.thenComparing(row -> row.getVariant().getId())
                : Comparator.comparing(row -> row.getVariant().getId());
    }

    private Comparator<InventoryRow> comparatorFor(String property) {
        return switch (property) {
            case "title" -> Comparator.comparing(row -> row.getVariant().getTitle(), String.CASE_INSENSITIVE_ORDER);
            case "category" -> Comparator.comparing(row -> row.getVariant().getCategory(), String.CASE_INSENSITIVE_ORDER);
            case "size" -> Comparator.comparing(row -> row.getVariant().getSize(), String.CASE_INSENSITIVE_ORDER);
            case "color" -> Comparator.comparing(row -> row.getVariant().getColor(), String.CASE_INSENSITIVE_ORDER);
            default -> null;
        };
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

    public Variant getVariant(long variantId) {
        return variantRepository.findById(variantId)
                .orElseThrow(() -> new IllegalArgumentException("Variant not found: " + variantId));
    }

    @Transactional
    public void addStock(long variantId, String locationName, int quantity, String binLocation) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        if (binLocation == null || binLocation.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter a bin location.");
        }
        if (locationName == null || locationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter a location.");
        }

        Variant variant = variantRepository.findByIdWithStocks(variantId)
                .orElseThrow(() -> new IllegalArgumentException("Variant not found: " + variantId));

        InventoryLocation location = inventoryLocationRepository.findByName(locationName.trim());
        if (location == null) {
            location = new InventoryLocation();
            location.setName(locationName.trim());
            location.setType("Warehouse");
            location = inventoryLocationRepository.save(location);
        }

        for (VariantStock stock : variant.getStocks()) {
            if (stock.getLocation().getName().equalsIgnoreCase(locationName.trim())) {
                stock.setQuantity(stock.getQuantity() + quantity);
                stock.setBinLocation(binLocation);
                variantStockRepository.save(stock);
                return;
            }
        }

        VariantStock newStock = new VariantStock();
        newStock.setVariant(variant);
        newStock.setLocation(location);
        newStock.setQuantity(quantity);
        newStock.setBinLocation(binLocation);

        variantStockRepository.save(newStock);
    }

    @Transactional
    public void removeStock(long variantId, String locationName, int quantity, String binLocation) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0.");
        }
        if (locationName == null || locationName.trim().isEmpty()) {
            throw new IllegalArgumentException("Please enter a location.");
        }

        VariantStock stock = variantStockRepository.findByVariantIdAndLocationName(variantId, locationName.trim());
        
        if (stock == null) {
            throw new IllegalArgumentException("No stock found at location: " + locationName);
        }
        
        int newQuantity = stock.getQuantity() - quantity;
        if (newQuantity < 0) {
            throw new IllegalArgumentException("Cannot remove more stock than available. Current stock: " + stock.getQuantity());
        }
        
        stock.setQuantity(newQuantity);
        if (binLocation != null && !binLocation.trim().isEmpty()) {
            stock.setBinLocation(binLocation);
        }
        variantStockRepository.save(stock);
    }
}