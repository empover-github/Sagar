package com.fictional.dao;

import java.util.List;

import com.fictional.model.Order;

public interface BrickDao {

	public Order createOder(int numberOfBricks);

	public Order getOrder(int orderId);

	public Order updateOrder(int orderId, Order order);

	public List<Order> getOrders();

	public Order fulFillOrder(Order order);

}
