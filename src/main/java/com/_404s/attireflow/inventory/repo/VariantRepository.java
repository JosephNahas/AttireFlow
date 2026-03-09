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
           where (:title is null or lower(v.title) like lower(concat('%', :title, '%')))
             and (:size is null or lower(v.size) = lower(:size))
             and (:color is null or lower(v.color) = lower(:color))
             and (:category is null or lower(v.category) = lower(:category))
           order by v.id
           """)
    List<Variant> findAllWithStocksFiltered(@Param("title") String title,
                                            @Param("size") String size,
                                            @Param("color") String color,
                                            @Param("category") String category);

    @Query("""
           select distinct v.category
           from Variant v
           order by v.category
           """)
    List<String> findDistinctCategories();

    @Query("""
           select distinct v.size
           from Variant v
           order by v.size
           """)
    List<String> findDistinctSizes();

    @Query("""
           select distinct v.color
           from Variant v
           order by v.color
           """)
    List<String> findDistinctColors();

    @Query("""
           select distinct v
           from Variant v
           left join fetch v.stocks s
           left join fetch s.location
           where v.id = :id
           """)
    Optional<Variant> findByIdWithStocks(@Param("id") Long id);
}
