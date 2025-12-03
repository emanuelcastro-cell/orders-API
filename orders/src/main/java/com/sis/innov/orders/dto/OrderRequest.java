package com.sis.innov.orders.dto;

import java.util.List;

import org.springframework.boot.autoconfigure.SpringBootApplication;

public record OrderRequest (
	Long id,
	List<OrderItemRequest> items) {

}
