package com.fictional.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fictional.dao.BrickDao;
import com.fictional.model.Order;

@Service
public class BrickServiceImpl implements BrickService {

	@Autowired
	private BrickDao brickDao;

	@Override
	public Order createOder(int numberOfBricks) {
		return brickDao.createOder(numberOfBricks);
	}

	@Override
	public Order getOrder(int orderId) {
		return brickDao.getOrder(orderId);
	}

	@Override
	public Order updateOrder(int orderId, Order order) {
		return brickDao.updateOrder(orderId, order);
	}

	@Override
	public List<Order> getOrders() {
		return brickDao.getOrders();
	}

	@Override
	public Order fulFillOrder(Order order) {

		return brickDao.fulFillOrder(order);
	}

}
