package com._404s.attireflow.inventory.repo;

import com._404s.attireflow.inventory.model.Variant;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VariantRepository extends CrudRepository<Variant, Long> {

    @Query("""
           select distinct v
           from Variant v
           left join fetch v.stocks s
           left join fetch s.location
           order by v.id
           """)
    List<Variant> findAllWithStocks();

    @Query("""
           select distinct v
           from Variant v
           left join fetch v.stocks s
           left join fetch s.location
           where v.id = :id
           """)
    Optional<Variant> findByIdWithStocks(@Param("id") Long id);
}
