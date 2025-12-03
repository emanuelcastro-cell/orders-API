package com.sis.innov.orders.dto;

public record OrderItemResponse (

	Long id,
	String sku,
	Integer quantity,
	Double price,
	Integer order
	) {

}
