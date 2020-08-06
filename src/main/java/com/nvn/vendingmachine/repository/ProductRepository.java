package com.nvn.vendingmachine.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.nvn.vendingmachine.entity.Product;
import com.nvn.vendingmachine.entity.ProductStock;

public class ProductRepository implements IProductRepository {

	private static ProductRepository productRepo = null;
	private Map<Product, Integer> productInventory = new HashMap<Product, Integer>();

	public static ProductRepository ProductRepository() {

		if (productRepo == null) {
			productRepo = new ProductRepository();
		}
		return productRepo;
	}

	public boolean addProduct(Product product, int quantity) {

		productInventory.put(product, quantity);

		return true;
	}

	public boolean buyProduct(Product product, int quantity) {

		int currentCount = productInventory.get(product);

		productInventory.put(product, currentCount - quantity);

		return true;
	}

	public ProductStock getProduct(String productName) {

		Product product = null;

		for (Entry<Product, Integer> entry : productInventory.entrySet()) {
			if (entry.getKey().getName().equalsIgnoreCase(productName)) {
				product = entry.getKey();
				break;
			}
		}

		if (product != null) {
			return new ProductStock(product, productInventory.get(product));
		}
		return null;
	}

	public ProductStock getProduct(Product product) {

		if (productInventory.containsKey(product)) {
			return new ProductStock(product, productInventory.get(product));
		}

		return null;
	}

	public Map<Product, Integer> getAllProducts() {
		return productInventory;
	}
}
