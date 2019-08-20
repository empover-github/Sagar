package com.fictional.model;

public class Order {

	private int orderId;
	private int numberOfBricks;
	private String status;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getNumberOfBricks() {
		return numberOfBricks;
	}

	public void setNumberOfBricks(int numberOfBricks) {
		this.numberOfBricks = numberOfBricks;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", numberOfBricks=" + numberOfBricks + ", status=" + status + "]";
	}

}
