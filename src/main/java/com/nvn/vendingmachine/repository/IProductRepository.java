package com.nvn.vendingmachine.repository;

import java.util.Map;

import com.nvn.vendingmachine.entity.Product;

public interface IProductRepository {

	public  boolean addProduct(Product product, int quantity);
	public boolean buyProduct(Product product, int quantity);
	public Map<Product, Integer> getAllProducts();
}
