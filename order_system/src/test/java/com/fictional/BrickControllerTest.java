package com.fictional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fictional.controller.BrickController;
import com.fictional.model.Order;
import com.fictional.service.BrickServiceImpl;

public class BrickControllerTest {

	private static final int UNKNOWN_ID = Integer.MAX_VALUE;
	
	private MockMvc mockMvc;

	@Mock
	private BrickServiceImpl brickServiceImpl;

	@InjectMocks
	private BrickController brickController;

	 @Autowired 
	 private ObjectMapper mapper;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(brickController)
				// .addFilters(new CORSFilter())
				.build();
	}
	
	@Test
	public void test_getOrders() throws Exception {

		Order order1 = new Order();
		order1.setOrderId(10);
		order1.setNumberOfBricks(3);
		order1.setStatus("Booked");
		

		Order order2 = new Order();
		order2.setOrderId(20);
		order2.setNumberOfBricks(6);
		order2.setStatus("Booked");
		
		List<Order> orders = new ArrayList<Order>();
		orders.add(order1);
		orders.add(order2);
		
	    when(brickServiceImpl.getOrders()).thenReturn(orders);
	    mockMvc.perform(get("/brick"))
	            .andExpect(status().isOk())
	            .andExpect(jsonPath("$", hasSize(2)))
	            .andExpect(jsonPath("$[0].orderId", is(10)))
	            .andExpect(jsonPath("$[0].numberOfBricks", is(3)))
	            .andExpect(jsonPath("$[0].status", is("Booked")));
	 
	    verify(brickServiceImpl, times(1)).getOrders();
        verifyNoMoreInteractions(brickServiceImpl);
	}

	
	@Test
    public void test_orderById_success() throws Exception {

		Order order1 = new Order();
		order1.setOrderId(50);
		order1.setNumberOfBricks(8);
		order1.setStatus("Booked");

        when(brickServiceImpl.getOrder(50)).thenReturn(order1);

        mockMvc.perform(get("/brick/{orderId}", 50))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.orderId", is(50)))
                .andExpect(jsonPath("$.numberOfBricks", is(8)))
        		.andExpect(jsonPath("$.status", is("Booked")));
    }
	
	
	 @Test 
	 public void test_create_order_success() throws Exception {
		 
		Order order = new Order();
		order.setOrderId(20);
		order.setNumberOfBricks(6);
		order.setStatus("Booked");
		
		 
		when(brickServiceImpl.createOder(order.getNumberOfBricks())).thenReturn(order);
		 
		 mockMvc.perform(
				 post("/brick")
	                        .contentType(MediaType.APPLICATION_JSON)
	                        .content(asJsonString(order)))
		 			.andExpect(status().isOk());
		 			//.andExpect(MockMvcResultMatchers.jsonPath("$.order").exists());
		
		  verify(brickServiceImpl, times(1)).createOder(order.getNumberOfBricks());
		  verifyNoMoreInteractions(brickServiceImpl);
	 
	 }
	
	  @Test 
	  public void test_update_success() throws Exception {
	  
	  Order order = new Order(); 
	  order.setOrderId(70);
	  order.setNumberOfBricks(41);
	  order.setStatus("Booked");
	  
	  when(brickServiceImpl.updateOrder(order.getOrderId(), order)).thenReturn(order);
	  //doNothing().when(brickServiceImpl).updateOrder(order.getOrderId(), order);
	  
	  
	  mockMvc.perform(
			  post("/brick/{orderId}", order.getOrderId())
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(asJsonString(order)))
	     .andExpect(status().isOk());
	    
	  
	  verify(brickServiceImpl, times(1)).getOrder(order.getOrderId());
	 // verify(brickServiceImpl, times(1)).updateOrder(order.getOrderId(), order);
	  verifyNoMoreInteractions(brickServiceImpl);
	  }
	 
	  

	  @Test 
	  public void test_update_failure() throws Exception {
	  
	  Order order = new Order(); 
	  order.setOrderId(UNKNOWN_ID);
	  order.setNumberOfBricks(81);
	  order.setStatus("Booked");
	  
	  //when(brickServiceImpl.getOrder(order.getOrderId()).getStatus()).thenReturn("dispatched");
	  
	  when(brickServiceImpl.getOrder(UNKNOWN_ID)).thenReturn(null);
	  
	  
	  mockMvc.perform(
			  post("/brick/{orderId}", order.getOrderId())
			  .contentType(MediaType.APPLICATION_JSON)
			  .content(asJsonString(order)))
	     .andExpect(status().isOk());
	    
	  
	  verify(brickServiceImpl, times(1)).getOrder(order.getOrderId());
	  verifyNoMoreInteractions(brickServiceImpl);
	  }
	
	 public static String asJsonString(final Object obj) {
	        try {
	            return new ObjectMapper().writeValueAsString(obj);
	        } catch (Exception e) {
	            throw new RuntimeException(e);
	        }
	    }
	
	

	
}
