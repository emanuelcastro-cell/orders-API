package com.sis.innov.orders.dto;

import org.springframework.boot.autoconfigure.SpringBootApplication;

public record OrderItemRequest (
	String sku,
	Integer quantity,
	Double price) {

}
