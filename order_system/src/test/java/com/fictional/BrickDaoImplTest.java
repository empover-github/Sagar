package com.fictional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.fictional.dao.BrickDaoImpl;
import com.fictional.model.Order;

@RunWith(value = SpringJUnit4ClassRunner.class)
public class BrickDaoImplTest {

	@Mock
	private BrickDaoImpl brickDoaImpl;

	@Test
	public void createOrder() {

		Order order = new Order();
		order.setOrderId(12);
		order.setNumberOfBricks(2);
		order.setStatus("Booked");

		order = brickDoaImpl.createOder(12);
		verify(brickDoaImpl, times(1)).createOder(12);
	}

	@Test
	public void getOrderById() {
		Order inputOrder = new Order();
		inputOrder.setOrderId(55);
		inputOrder.setNumberOfBricks(3);
		inputOrder.setStatus("Booked");

		when(brickDoaImpl.getOrder(55)).thenReturn(inputOrder);

		Order order2 = brickDoaImpl.getOrder(55);
		assertNotNull(order2);
		assertEquals(inputOrder.getNumberOfBricks(), order2.getNumberOfBricks());
		assertEquals(inputOrder.getOrderId(), order2.getOrderId());
		assertNotEquals("dispatched", order2.getStatus());
	}

	@Test
	public void getAllOrders() {
		
		List<Order> orders = new ArrayList<Order>();
		
		Order order1 = new Order();
		order1.setOrderId(10);
		order1.setNumberOfBricks(3);
		order1.setStatus("Booked");
		

		Order order2 = new Order();
		order2.setOrderId(20);
		order2.setNumberOfBricks(6);
		order2.setStatus("Booked");
		
		Order order3 = new Order();
		order3.setOrderId(20);
		order3.setNumberOfBricks(6);
		order3.setStatus("dispatched");
		
		orders.add(order1);
		orders.add(order2);
		orders.add(order3);
		
		when(brickDoaImpl.getOrders()).thenReturn(orders);

		assertNotNull(orders);
		assertEquals(3, orders.size());
	
	}
	
	
	@Test
	public void updateOrder() {

		Order order = new Order();
		order.setOrderId(12);
		order.setNumberOfBricks(2);
		order.setStatus("Booked");

		
		when(brickDoaImpl.updateOrder(order.getOrderId(), order)).thenReturn(order);
		Order updateOrder = brickDoaImpl.updateOrder(order.getOrderId(), order);
		assertNotNull(updateOrder);
		assertEquals(2, updateOrder.getNumberOfBricks());
		
	}
}
