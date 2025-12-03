package com.sis.innov.orders.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {

@Id
private Long id;

private Double total;
@OneToMany(mappedBy = "order" , cascade = CascadeType.ALL)
private List<OrderItem> items;

public Order(Long id, Double total) {
	this.id = id;
	this.total = total;
	this.items = null;
	
}



}
