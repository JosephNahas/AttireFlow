package com._404s.attireflow.inventory.repo;

import com._404s.attireflow.inventory.model.VariantStock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface VariantStockRepository extends CrudRepository<VariantStock, Long> {

    @Query("""
           select distinct vs.binLocation
           from VariantStock vs
           order by vs.binLocation
           """)
    List<String> findDistinctBinLocations();

    VariantStock findByVariantIdAndLocationName(Long variantId, String locationName);
}