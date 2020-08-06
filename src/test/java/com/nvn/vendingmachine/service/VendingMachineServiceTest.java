package com.nvn.vendingmachine.service;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

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

public class VendingMachineServiceTest {

	VendingMachineService vmService = new VendingMachineService();

	// Adding single product with valid information
	@Test
	public void addProductSingleProductHappyPath() {

		Product p1 = new Product("Cola", 10);

		ProductStock stock1 = new ProductStock(p1, 10);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);

		try {
			boolean addProduct = vmService.addProduct(productsStock);
			assertThat(addProduct, is(true));

		} catch (InvalidQuantityException e) {
			e.printStackTrace();
		} catch (InvalidProductNameException e) {
			e.printStackTrace();
		} catch (InvalidProductPriceException e) {
			e.printStackTrace();
		}

	}

	// Adding multiple products with valid information
	@Test
	public void addProductMultipleHappyPath() {

		Product p1 = new Product("Cola", 10);
		Product p2 = new Product("Popsi", 10);
		Product p3 = new Product("Candy bar", 10);

		ProductStock stock1 = new ProductStock(p1, 10);
		ProductStock stock2 = new ProductStock(p2, 12);
		ProductStock stock3 = new ProductStock(p3, 15);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);
		productsStock.add(stock2);
		productsStock.add(stock3);

		try {
			boolean addProduct = vmService.addProduct(productsStock);
			assertThat(addProduct, is(true));
		} catch (InvalidQuantityException e) {
			e.printStackTrace();
		} catch (InvalidProductNameException e) {
			e.printStackTrace();
		} catch (InvalidProductPriceException e) {
			e.printStackTrace();
		}
	}

	// Adding single product with blank name

	@Test
	public void addProductMultipleInvalidName() {

		Product p1 = new Product("", 10);
		Product p2 = new Product("Popsi", 10);
		Product p3 = new Product("Candy bar", 10);

		ProductStock stock1 = new ProductStock(p1, 10);
		ProductStock stock2 = new ProductStock(p2, 12);
		ProductStock stock3 = new ProductStock(p3, 15);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);
		productsStock.add(stock2);
		productsStock.add(stock3);

		try {
			vmService.addProduct(productsStock);
		} catch (InvalidQuantityException e) {

		} catch (InvalidProductNameException e) {
			assertThat(e.getMessage(), is(ErrorMessage.ENTER_VALID_PRODUCT_NAME.getMessage()));
		} catch (InvalidProductPriceException e) {
			e.printStackTrace();
		}
	}

	// Adding single product with valid information

	@Test
	public void addProductMultipleInvalidQuantity() {

		Product p1 = new Product("Cola", 0);
		Product p2 = new Product("Popsi", 10);
		Product p3 = new Product("Candy bar", 10);

		ProductStock stock1 = new ProductStock(p1, 10);
		ProductStock stock2 = new ProductStock(p2, 12);
		ProductStock stock3 = new ProductStock(p3, 15);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);
		productsStock.add(stock2);
		productsStock.add(stock3);

		try {
			vmService.addProduct(productsStock);
		} catch (InvalidQuantityException e) {
			assertThat(e.getMessage(), is(ErrorMessage.QUANTITY_CANT_BE_ZERO.getMessage()));

		} catch (InvalidProductNameException e) {
		} catch (InvalidProductPriceException e) {
			assertThat(e.getMessage(), is(ErrorMessage.INVALID_PRODUCT_PRICE.getMessage()));

		}
	}

	@Test
	public void buyProductSinlgeItemHappyPath() {

		Product p1 = new Product("Cola", 10);
		Product p2 = new Product("Popsi", 10);
		Product p3 = new Product("Candy bar", 10);

		ProductStock stock1 = new ProductStock(p1, 10);
		ProductStock stock2 = new ProductStock(p2, 12);
		ProductStock stock3 = new ProductStock(p3, 15);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);
		productsStock.add(stock2);
		productsStock.add(stock3);

		List<Coins> coins = new ArrayList<>();
		coins.add(Coins.CENT);
		coins.add(Coins.DIME);
		coins.add(Coins.DIME);
		coins.add(Coins.NICKEL);
		// 26
		try {
			vmService.addProduct(productsStock);

			Result boughtProduct = vmService.buyProduct("Cola", 1, coins);

			assertThat(boughtProduct.getProduct().getName(), is("Cola"));
			assertThat(boughtProduct.getChange(), is(16));

		} catch (ProductNotFoundException e) {
			e.printStackTrace();
		} catch (InsufficientStockException e) {
			e.printStackTrace();
		} catch (InsufficientMoneyInsertedException e) {
			e.printStackTrace();
		} catch (InvalidQuantityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	// Buy multiple products 
	@Test
	public void buyProductMultipleItemHappyPath() {

		Product p1 = new Product("Cola", 10);
		Product p2 = new Product("Popsi", 10);
		Product p3 = new Product("Candy bar", 10);

		ProductStock stock1 = new ProductStock(p1, 10);
		ProductStock stock2 = new ProductStock(p2, 12);
		ProductStock stock3 = new ProductStock(p3, 15);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);
		productsStock.add(stock2);
		productsStock.add(stock3);

		List<Coins> coins = new ArrayList<>();
		coins.add(Coins.CENT); // 1
		coins.add(Coins.DIME); // 10
		coins.add(Coins.DIME); // 10
		coins.add(Coins.NICKEL); // 5
		try {
			vmService.addProduct(productsStock);

			Result boughtProduct = vmService.buyProduct("Cola", 2, coins);

			assertThat(boughtProduct.getProduct().getName(), is("Cola"));
			assertThat(boughtProduct.getChange(), is(6));

		} catch (ProductNotFoundException e) {
			e.printStackTrace();
		} catch (InsufficientStockException e) {
			e.printStackTrace();
		} catch (InsufficientMoneyInsertedException e) {
			e.printStackTrace();
		} catch (InvalidQuantityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	// Product is more expensive that inserted money
	@Test
	public void buyProductSingleItemInsifficientMoneyInserted() {

		Product p1 = new Product("Cola", 10);
		Product p2 = new Product("Popsi", 10);
		Product p3 = new Product("Candy bar", 10);

		ProductStock stock1 = new ProductStock(p1, 10);
		ProductStock stock2 = new ProductStock(p2, 12);
		ProductStock stock3 = new ProductStock(p3, 15);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);
		productsStock.add(stock2);
		productsStock.add(stock3);

		List<Coins> coins = new ArrayList<>();
		coins.add(Coins.CENT); // 1
		coins.add(Coins.DIME); // 10
		coins.add(Coins.DIME); // 10

		try {
			vmService.addProduct(productsStock);
			vmService.buyProduct("Cola", 3, coins);

		} catch (ProductNotFoundException e) {
			e.printStackTrace();
		} catch (InsufficientStockException e) {
		} catch (InsufficientMoneyInsertedException e) {
			assertThat(e.getMessage(), is(ErrorMessage.INSUFFIENT_FUNDS_INSERT.getMessage()));
		} catch (InvalidQuantityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	// Trying to buy quantity not in inventory
	@Test
	public void buyProductSingleItemProductNotFound() {

		Product p1 = new Product("Cola", 10);
		Product p3 = new Product("Candy bar", 10);

		ProductStock stock1 = new ProductStock(p1, 10);
		ProductStock stock3 = new ProductStock(p3, 15);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);
		productsStock.add(stock3);

		List<Coins> coins = new ArrayList<>();
		coins.add(Coins.CENT); // 1
		coins.add(Coins.DIME); // 5
		coins.add(Coins.DIME); // 5
//		coins.add(Coins.NICKEL); // 10

		try {
			vmService.addProduct(productsStock);

			vmService.buyProduct("Pepsi", 2, coins);

		} catch (ProductNotFoundException e) {
			assertThat(e.getMessage(), is(ErrorMessage.PRODUCT_NOT_FOUND.getMessage()));

		} catch (InsufficientStockException e) {
			assertThat(e.getMessage(), is(ErrorMessage.INSUFFIENT_FUNDS_INSERT.getMessage()));
		} catch (InsufficientMoneyInsertedException e) {
			e.printStackTrace();
		} catch (InvalidQuantityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	// Buy single product with invalid quantity
	@Test
	public void buyProductSingleItemInvalidQuantity() {

		Product p1 = new Product("Cola", 10);
		Product p3 = new Product("Candy bar", 10);

		ProductStock stock1 = new ProductStock(p1, 10);
		ProductStock stock3 = new ProductStock(p3, 15);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);
		productsStock.add(stock3);

		List<Coins> coins = new ArrayList<>();
		coins.add(Coins.CENT); // 1
		coins.add(Coins.DIME); // 10
		coins.add(Coins.DIME); // 10

		try {
			vmService.addProduct(productsStock);

			vmService.buyProduct("Pepsi", -100, coins);

		} catch (ProductNotFoundException e) {
			assertThat(e.getMessage(), is(ErrorMessage.PRODUCT_NOT_FOUND.getMessage()));

		} catch (InsufficientStockException e) {
		} catch (InsufficientMoneyInsertedException e) {
			assertThat(e.getMessage(), is(ErrorMessage.INSUFFIENT_FUNDS_INSERT.getMessage()));
		} catch (InvalidQuantityException e) {
			assertThat(e.getMessage(), is(ErrorMessage.INVALID_QUANTITY.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	// Buy product more than stock
	@Test
	public void buyProductLowStock() {

		Product p1 = new Product("Cola", 3);
		Product p3 = new Product("Candy bar", 10);

		ProductStock stock1 = new ProductStock(p1, 10);
		ProductStock stock3 = new ProductStock(p3, 15);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);
		productsStock.add(stock3);

		List<Coins> coins = new ArrayList<>();
		coins.add(Coins.CENT); // 1
		coins.add(Coins.DIME); // 10
		coins.add(Coins.DIME); // 10

		try {
			vmService.addProduct(productsStock);

			vmService.buyProduct("Pepsi", 10, coins);

		} catch (ProductNotFoundException e) {
			assertThat(e.getMessage(), is(ErrorMessage.PRODUCT_NOT_FOUND.getMessage()));

		} catch (InsufficientStockException e) {
			assertThat(e.getMessage(), is(ErrorMessage.LOW_STOCK.getMessage()));

		} catch (InsufficientMoneyInsertedException e) {
			assertThat(e.getMessage(), is(ErrorMessage.INSUFFIENT_FUNDS_INSERT.getMessage()));
		} catch (InvalidQuantityException e) {
			assertThat(e.getMessage(), is(ErrorMessage.INVALID_QUANTITY.getMessage()));
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	@Test
	public void buyProductBuyMoreQuantity() {

		Product p1 = new Product("Cola", 10);
		Product p3 = new Product("Candy bar", 25);

		ProductStock stock1 = new ProductStock(p1, 150);
		ProductStock stock3 = new ProductStock(p3, 300);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);
		productsStock.add(stock3);

		List<Coins> coins = new ArrayList<>();
		coins.add(Coins.DIME); // 10
		coins.add(Coins.DIME); // 10
		coins.add(Coins.QUARTER); // 25
		coins.add(Coins.DOLLAR); // 100
		// 145

		try {
			vmService.addProduct(productsStock);

			Result buyProduct = vmService.buyProduct("cola", 10, coins);
			assertThat(buyProduct.getChange(), is(45));

		} catch (ProductNotFoundException e) {
			e.printStackTrace();
		} catch (InsufficientStockException e) {
			e.printStackTrace();

		} catch (InsufficientMoneyInsertedException e) {
			e.printStackTrace();
		} catch (InvalidQuantityException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();

		}
	}

}
