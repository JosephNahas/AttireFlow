package com._404s.attireflow.inventory.repo;

import com._404s.attireflow.inventory.model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
    
    @Query("SELECT d FROM Delivery d WHERE " +
           "(:status IS NULL OR LOWER(d.status) = LOWER(:status)) AND " +
           "(:type IS NULL OR LOWER(d.deliveryType) = LOWER(:type)) AND " +
           "(:location IS NULL OR LOWER(d.location) LIKE LOWER(CONCAT('%', :location, '%')))")
    Page<Delivery> filterDeliveries(@Param("status") String status, 
                                    @Param("type") String type, 
                                    @Param("location") String location,
                                    Pageable pageable);
}