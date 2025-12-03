package com.sis.innov.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sis.innov.orders.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{


}
