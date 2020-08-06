package com.nvn.vendingmachine.constants;

public enum ErrorMessage {

	QUANTITY_CANT_BE_ZERO("Quantity cannot be zero"), 
	ENTER_VALID_PRODUCT_NAME("Enter valid product name"),
	INSUFFIENT_FUNDS_INSERT("Insufficient money to buy product"),
	LOW_STOCK("Low Stock"),
	INVALID_QUANTITY("Enter valid quantity"),
	PRODUCT_NOT_FOUND("Product Not Found"),
	INVALID_PRODUCT_PRICE("Invalid Product Price");

	private final String message;

	ErrorMessage(String msg) {
		this.message = msg;
	}

	public String getMessage() {
		return this.message;
	}
}
