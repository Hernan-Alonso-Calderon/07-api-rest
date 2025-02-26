package com.restaurant.restaurant_management.repositories;

import com.restaurant.restaurant_management.models.ClientOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<ClientOrder, Long> {
  List<ClientOrder> findByOrderDateTimeBetween(LocalDateTime startDate, LocalDateTime endDate);
}
