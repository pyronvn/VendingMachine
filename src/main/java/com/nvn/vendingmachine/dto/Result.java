package com.nvn.vendingmachine.dto;

import com.nvn.vendingmachine.entity.Product;

public class Result {

	private Product product;
	private int change;

	public Result(Product product, int change) {
		this.product = product;
		this.change = change;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getChange() {
		return change;
	}

	public void setChange(int change) {
		this.change = change;
	}

}
