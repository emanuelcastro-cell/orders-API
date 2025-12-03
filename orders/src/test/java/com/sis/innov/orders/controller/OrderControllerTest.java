package com.sis.innov.orders.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sis.innov.orders.dto.OrderItemRequest;
import com.sis.innov.orders.dto.OrderRequest;
import com.sis.innov.orders.model.Order;
import com.sis.innov.orders.model.OrderItem;
import com.sis.innov.orders.repository.OrderRepository;

class OrderControllerTest {

	
		 private MockMvc mockMvc;

		    private OrderRepository repository;

		    private OrderController controller;

		    private ObjectMapper mapper = new ObjectMapper();

		    @BeforeEach
		    void setup() {
		        MockitoAnnotations.openMocks(this);
		        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
		    }

		    @Test
		    void testCreateOrder() throws Exception {

		        OrderItemRequest itemReq = new OrderItemRequest("SKU123", 2, 10.0);
		        OrderRequest req = new OrderRequest(1L, List.of(itemReq));

		        Order order = new Order(1L, 20.0);
		        OrderItem item = new OrderItem();
		        item.setSku("SKU123");
		        item.setQuantity(2);
		        item.setPrice(10.0);
		        item.setOrder(order);
		        item.setOrderId(1L);
		        order.setItems(List.of(item));

		        when(repository.save(any(Order.class))).thenReturn(order);

		        mockMvc.perform(
		                post("/orders")
		                        .contentType(org.springframework.http.MediaType.APPLICATION_JSON)
		                        .content(mapper.writeValueAsString(req))
		        )
		        .andExpect(status().isOk())
		        .andExpect(jsonPath("$.id").value(1L))
		        .andExpect(jsonPath("$.total").value(20.0))
		        .andExpect(jsonPath("$.items[0].sku").value("SKU123"));

		        verify(repository, times(1)).save(any(Order.class));
		    }

		    @Test
		    void testFindOrder_found() throws Exception {
		        Order order = new Order(1L, 50.0);
		        when(repository.findById(1L)).thenReturn(Optional.of(order));

		        mockMvc.perform(get("/orders/1"))
		                .andExpect(status().isOk())
		                .andExpect(jsonPath("$.total").value(50.0));
		    }

		    @Test
		    void testFindOrder_notFound() throws Exception {
		        when(repository.findById(1L)).thenReturn(Optional.empty());

		        mockMvc.perform(get("/orders/1"))
		                .andExpect(status().isNotFound());
		    }}
	
	
