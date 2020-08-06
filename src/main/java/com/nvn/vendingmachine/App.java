package com.nvn.vendingmachine;

import java.util.ArrayList;
import java.util.List;

import com.nvn.vendingmachine.constants.Coins;
import com.nvn.vendingmachine.dto.Result;
import com.nvn.vendingmachine.entity.Product;
import com.nvn.vendingmachine.entity.ProductStock;
import com.nvn.vendingmachine.exception.InsufficientMoneyInsertedException;
import com.nvn.vendingmachine.exception.InsufficientStockException;
import com.nvn.vendingmachine.exception.InvalidProductNameException;
import com.nvn.vendingmachine.exception.InvalidProductPriceException;
import com.nvn.vendingmachine.exception.InvalidQuantityException;
import com.nvn.vendingmachine.exception.ProductNotFoundException;
import com.nvn.vendingmachine.service.IVendingMachineService;
import com.nvn.vendingmachine.service.VendingMachineService;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws InvalidQuantityException, InvalidProductNameException,
			ProductNotFoundException, InsufficientStockException, InsufficientMoneyInsertedException,
			InvalidProductPriceException {
		IVendingMachineService vendingMachineService = new VendingMachineService();

		Product p1 = new Product("Cola", 10);
		Product p2 = new Product("Popsi", 10);
		Product p3 = new Product("Candy bar", 10);

		Product p4 = new Product("Coffee", 10);
		Product p5 = new Product("Tea", 10);
		Product p6 = new Product("Chips", 10);
		Product p7 = new Product("Biscuit", 10);

		ProductStock stock1 = new ProductStock(p1, 10);

		ProductStock stock2 = new ProductStock(p2, 12);
		ProductStock stock3 = new ProductStock(p3, 15);
		ProductStock stock4 = new ProductStock(p4, 17);
		ProductStock stock5 = new ProductStock(p5, 25);
		ProductStock stock6 = new ProductStock(p6, 50);
		ProductStock stock7 = new ProductStock(p7, 75);

		List<ProductStock> productsStock = new ArrayList<>();
		productsStock.add(stock1);
		productsStock.add(stock2);
		productsStock.add(stock3);
		productsStock.add(stock4);
		productsStock.add(stock5);
		productsStock.add(stock6);
		productsStock.add(stock7);

//		Product p1 = new Product("Cola", 10);
//
//		ProductStock stock1 = new ProductStock(p1, 10);
//
//		ProductStock stock2 = new ProductStock(p1, 12);
//		ProductStock stock3 = new ProductStock(p1, 15);
//		ProductStock stock4 = new ProductStock(p1, 17);
//		ProductStock stock5 = new ProductStock(p1, 25);
//		ProductStock stock6 = new ProductStock(p1, 50);
//		ProductStock stock7 = new ProductStock(p1, 75);
//
//		List<ProductStock> productsStock = new ArrayList<>();
//		productsStock.add(stock1);
//		productsStock.add(stock2);
//		productsStock.add(stock3);
//		productsStock.add(stock4);
//		productsStock.add(stock5);
//		productsStock.add(stock6);
//		productsStock.add(stock7);
//
		vendingMachineService.addProduct(productsStock);

		List<Coins> coins = new ArrayList<>();
		coins.add(Coins.CENT);
		coins.add(Coins.DIME);
		coins.add(Coins.DIME);
		coins.add(Coins.NICKEL);


		Result buyProduct = vendingMachineService.buyProduct("Cola", 1, coins);
		System.out.println(
				"Bought Product- " + buyProduct.getProduct().getName() + " Change- " + buyProduct.getChange());
		
	}
}
