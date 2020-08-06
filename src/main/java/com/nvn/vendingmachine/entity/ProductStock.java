package com.nvn.vendingmachine.entity;

public class ProductStock {

	private Product product;
	private int quantity;

	public ProductStock(Product product, int currentStock) {
		this.product = product;
		this.quantity = currentStock;
	}


	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int currentStock) {
		this.quantity = currentStock;
	}

}
