package com._404s.attireflow.inventory.repo;

import com._404s.attireflow.inventory.model.InventoryLocation;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface InventoryLocationRepository extends CrudRepository<InventoryLocation, Long> {

    List<InventoryLocation> findAllByOrderByNameAsc();
    
    InventoryLocation findByName(String name);
}