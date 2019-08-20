package com.fictional.service;

import java.util.List;

import com.fictional.model.Order;

public interface BrickService {

	public Order createOder(int numberOfBricks);
	
	public Order getOrder(int orderId);

	public Order updateOrder(int orderId,Order order);
	
	public Order fulFillOrder(Order order);
	
	public List<Order> getOrders();
	
}
