package com.fictional.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Repository;

import com.fictional.model.Order;

@Repository
public class BrickDaoImpl implements BrickDao {

	private Map<Integer, Order> brickOderMap = new HashMap<Integer, Order>();

	@Override
	public Order createOder(int numberOfBricks) {

		Random random = new Random();

		Order order = new Order();
		int orderId = random.nextInt(1000);

		order.setOrderId(orderId);
		order.setNumberOfBricks(numberOfBricks);
		order.setStatus("Booked");

		brickOderMap.put(orderId, order);
		return order;
	}

	@Override
	public Order getOrder(int orderId) {
		return brickOderMap.containsKey(orderId) ? brickOderMap.get(orderId) : null;
	}

	@Override
	public List<Order> getOrders() {

		List<Order> orders = new ArrayList<Order>();
		for (Map.Entry<Integer, Order> brickOrder : brickOderMap.entrySet()) {
			orders.add(brickOrder.getValue());
		}
		return orders;
	}

	@Override
	public Order updateOrder(int orderId, Order order) {

		if (brickOderMap.containsKey(orderId)) {
			Order existOrder = brickOderMap.get(orderId);
			existOrder.setNumberOfBricks(order.getNumberOfBricks());
			existOrder.setStatus(order.getStatus());
			return existOrder;
		}
		return null;
	}

	@Override
	public Order fulFillOrder(Order order) {
		if(brickOderMap.containsKey(order.getOrderId())) {
			Order existOrder = brickOderMap.get(order.getOrderId());
			existOrder.setStatus("dispatched");
			return existOrder;
		}	
		return null;
	}

	
	

}
