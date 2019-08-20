package com.fictional.controller;

import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fictional.model.Order;
import com.fictional.service.BrickService;

@RestController
public class BrickController {

	@Autowired
	private BrickService brickService;

	@PostMapping("/brick")
	public ResponseEntity<?> createOder(@RequestBody Order order) {

		return ResponseEntity.ok().body(
				"New Order has been Booked with ID:" + brickService.createOder(order.getNumberOfBricks()).getOrderId());
	}

	@GetMapping("/brick/{orderId}")
	public ResponseEntity<?> getOder(@PathVariable("orderId") int orderId) {


		if (Objects.isNull(brickService.getOrder(orderId))) {
			return ResponseEntity.ok().body("Sorry there is No Order has been Booked with ID\t" + orderId);
		} else {
			return ResponseEntity.ok().body(brickService.getOrder(orderId));
		}

	}

	@GetMapping("/brick")
	public ResponseEntity<List<Order>> getOders() {
		return ResponseEntity.ok().body(brickService.getOrders());

	}

	@PostMapping("/brick/{orderId}")
	public ResponseEntity<?> updateOrder(@PathVariable("orderId") int orderId,@RequestBody Order order) {

		if (Objects.isNull(brickService.getOrder(orderId))) {
			return ResponseEntity.ok().body("Sorry there is No Order available with this order Id\t" + orderId+" to Update");
		} else if("dispatched".equalsIgnoreCase(brickService.getOrder(orderId).getStatus())) {
			return  new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok().body(brickService.updateOrder(orderId, order));
		}
	}
	

	@PostMapping("/brickfull")
	public ResponseEntity<?> fulFillOder(@RequestBody Order order) {

		if (Objects.isNull(brickService.getOrder(order.getOrderId()))) {
			return   new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
		} else {
			return ResponseEntity.ok().body(brickService.fulFillOrder(order));
		}

	}
	

	
}
