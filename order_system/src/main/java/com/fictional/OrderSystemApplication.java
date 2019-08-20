package com.fictional;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import com.fictional.controller.BrickController;
import com.fictional.model.Order;

@SpringBootApplication
public class OrderSystemApplication implements CommandLineRunner {


	@Autowired
	private BrickController brickController;
	
	
	public static void main(String[] args) {
		SpringApplication.run(OrderSystemApplication.class, args);
	}


	@SuppressWarnings("unchecked")
	@Override
	public void run(String... args) throws Exception {
		
		
		//********************** Creating Order **********************************************//
		System.out.println("##########  Creating Orders ##########");
		System.out.println("\n");
		Order myOrder1 = new Order();
		myOrder1.setNumberOfBricks(12);
		Order myOrder2 = new Order();
		myOrder2.setNumberOfBricks(13);
		Order myOrder3 = new Order();
		myOrder3.setNumberOfBricks(14);
		
		List responseList = new ArrayList<>();
		
		responseList.add((ResponseEntity<Order>) brickController.createOder(myOrder1));
		responseList.add((ResponseEntity<Order>) brickController.createOder(myOrder2));
		responseList.add((ResponseEntity<Order>) brickController.createOder(myOrder3));
		
		responseList.forEach(System.out::println);
		System.out.println("\n");
		
		
		//********************** Get All Orders **********************************************//
	
		System.out.println("##########  Getting All Orders ##########");
		System.out.println("\n");
		ResponseEntity<List<Order>> httpOrders = brickController.getOders();
		
		List<Order> listOfOrders = httpOrders.getBody();
		
		Order order1 =listOfOrders.get(0);
		Order order2 =listOfOrders.get(1);
		Order order3 =listOfOrders.get(2);
		
		
		httpOrders.getBody().forEach(System.out::println);
		System.out.println("\n");
		
		
		
		//********************** Get OrderBy Id **********************************************// 
		
		System.out.println("##########  Getting Order By Id ##########");
		System.out.println("\n");
		ResponseEntity<?> responseEntity = brickController.getOder(order1.getOrderId());
		System.out.println(responseEntity.getBody());
		
		ResponseEntity<?> responseEntity1 = brickController.getOder(12121);  //giving wrong orderId
		System.out.println(responseEntity1.getBody());
		System.out.println("\n");
		
		
		
		//********************** Update Order **********************************************//
		
		System.out.println("##########  Update Order ##########");
		System.out.println("\n");
		order3.setNumberOfBricks(500);//updating existing order with diff noofbricks
		 
		ResponseEntity<?> updatedResponse =brickController.updateOrder(order3.getOrderId(), order3);
		System.out.println(updatedResponse.getBody());
		
		
		Order newOrderToUpate = new Order();
		newOrderToUpate.setOrderId(585569); //order id which is not there in memory/db
		newOrderToUpate.setNumberOfBricks(53);
		newOrderToUpate.setStatus("Booked");
		ResponseEntity<?> updatedResponse1 =brickController.updateOrder(newOrderToUpate.getOrderId(), newOrderToUpate);
		System.out.println(updatedResponse1.getBody());
		System.out.println("\n");
		
		 //***************************** fulFillOder ******************************************//
		
		System.out.println("##########  fulFillOder Order ##########");
		System.out.println("\n");
		ResponseEntity<?> fulFillOrderResponse = brickController.fulFillOder(order2);
		System.out.println(fulFillOrderResponse.getBody());
		
		Order newOrderToFulFill = new Order();
		newOrderToFulFill.setOrderId(968522); //order id which is not there in memory/db to FulFill request should give BAD REQUEST
		newOrderToFulFill.setNumberOfBricks(855);
		newOrderToFulFill.setStatus("Booked");
		ResponseEntity<?> fulFillOrderResponse2 = brickController.fulFillOder(newOrderToFulFill);
		System.out.println(fulFillOrderResponse2.getStatusCode()+" because orderId "+newOrderToFulFill.getOrderId()+" "+"is not there to FulFillOrder");
		
		//updating order which has status is dispatched should return BAD REQUEST
		ResponseEntity<?> updatedResponseBadRequest =brickController.updateOrder(order2.getOrderId(),order2);
		System.out.println(updatedResponseBadRequest.getStatusCode()+" because this orderId "+order2.getOrderId()+" "+"status is "+order2.getStatus());
	}

}
