package com.nvn.vendingmachine.service;

import java.util.List;

import com.nvn.vendingmachine.constants.Coins;
import com.nvn.vendingmachine.constants.ErrorMessage;
import com.nvn.vendingmachine.dto.Result;
import com.nvn.vendingmachine.entity.Product;
import com.nvn.vendingmachine.entity.ProductStock;
import com.nvn.vendingmachine.exception.InsufficientMoneyInsertedException;
import com.nvn.vendingmachine.exception.InsufficientStockException;
import com.nvn.vendingmachine.exception.InvalidProductNameException;
import com.nvn.vendingmachine.exception.InvalidProductPriceException;
import com.nvn.vendingmachine.exception.InvalidQuantityException;
import com.nvn.vendingmachine.exception.ProductNotFoundException;
import com.nvn.vendingmachine.repository.ProductRepository;

public class VendingMachineService implements IVendingMachineService {

	private ProductRepository repository = new ProductRepository();

	private static VendingMachineService vendingMachineService = null;

	public static VendingMachineService ProductRepository() {

		if (vendingMachineService == null) {
			vendingMachineService = new VendingMachineService();
		}
		return vendingMachineService;
	}

	// Buying from vending machine
	public Result buyProduct(String product, int quantity, List<Coins> coins) throws ProductNotFoundException,
			InsufficientStockException, InsufficientMoneyInsertedException, InvalidQuantityException {

		ProductStock productStock = repository.getProduct(product);

		int moneyInserted = calculatedMoneyInserted(coins);

		if (quantity <= 0) {
			throw new InvalidQuantityException(ErrorMessage.INVALID_QUANTITY.getMessage());
		}

		// Product not present in Vending machine
		if (productStock == null) {
			throw new ProductNotFoundException(ErrorMessage.PRODUCT_NOT_FOUND.getMessage());
		} else {
			
			// Requested quantity cannot be fulfilled because of low stock
			if (productStock.getQuantity() < quantity) {
				throw new InsufficientStockException(ErrorMessage.LOW_STOCK.getMessage());
			}
			
			// Total money inserted is not sufficient to buy the product
			if ((productStock.getProduct().getPrice() * quantity) > moneyInserted) {
				throw new InsufficientMoneyInsertedException(ErrorMessage.INSUFFIENT_FUNDS_INSERT.getMessage());
			}

			repository.buyProduct(productStock.getProduct(), quantity);
			return new Result(productStock.getProduct(),
					renderChange(productStock.getProduct().getPrice() * quantity, moneyInserted));
		}

	}

	// Add list of products
	public boolean addProduct(List<ProductStock> products)
			throws InvalidQuantityException, InvalidProductNameException, InvalidProductPriceException {

		for (ProductStock prod : products) {
			
			// Check for null or blank product name
			if (prod.getProduct().getName() == null || prod.getProduct().getName().isEmpty()) {
				throw new InvalidProductNameException(ErrorMessage.ENTER_VALID_PRODUCT_NAME.getMessage());
			}

			// Check for 0 quantity
			if (prod.getQuantity() == 0) {
				throw new InvalidQuantityException(ErrorMessage.QUANTITY_CANT_BE_ZERO.getMessage());
			}
			
			// Price of the product should not be zero
			if(prod.getProduct().getPrice() <= 0) {
				throw new InvalidProductPriceException(ErrorMessage.INVALID_PRODUCT_PRICE.getMessage());
			}

			addProduct(prod.getProduct(), prod.getQuantity());
		}

		return true;
	}
	
	private void addProduct(Product product, int quantity) {

		ProductStock stock = repository.getProduct(product);

		if (stock != null) {
			int updatedQty = stock.getQuantity() + quantity;

			repository.addProduct(product, updatedQty);
		} else {
			repository.addProduct(product, quantity);
		}

	}

	// Add and get total coint value
	private int calculatedMoneyInserted(List<Coins> coins) {

		return coins.stream().map(Coins::getCoinValue).reduce(0, Integer::sum);
	}

	// Return cahnge to the usee
	private int renderChange(int productCost, int moneyInserted) {
		return moneyInserted - productCost;
	}

}
