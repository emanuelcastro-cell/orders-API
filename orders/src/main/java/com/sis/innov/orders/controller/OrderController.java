package com.sis.innov.orders.controller;

import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sis.innov.orders.dto.OrderRequest;
import com.sis.innov.orders.model.Order;
import com.sis.innov.orders.model.OrderItem;
import com.sis.innov.orders.repository.OrderRepository;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	private final OrderRepository repository;
	
	public OrderController(OrderRepository repository) {
		this.repository = repository;
	}
	
	@PostMapping
	public ResponseEntity<Order> createOrder(@RequestBody OrderRequest request){
		
		double total = request.items().stream()
				.mapToDouble(item -> item.price() * item.quantity())
				.sum();
		
		
		Order order = new Order(request.id(), total);
		
		
		var items = request.items().stream()
				.map(i -> {
					OrderItem item = new OrderItem();
							item.setSku(i.sku());
							item.setQuantity(i.quantity());
							item.setPrice(i.price());
							item.setOrder(order);
							item.setOrderId(order.getId());
					return item;
				}).collect(Collectors.toList());
		
		order.setItems(items);
		
		Order saved = repository.save(order);
		
		for (OrderItem i : saved.getItems()) {
			i.setOrderId(order.getId());
		}
		
		return ResponseEntity.ok(saved);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> findOrder(@PathVariable Long id){
		return repository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
}
