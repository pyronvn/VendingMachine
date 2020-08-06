package com.nvn.vendingmachine.service;

import java.util.List;

import com.nvn.vendingmachine.constants.Coins;
import com.nvn.vendingmachine.dto.Result;
import com.nvn.vendingmachine.entity.ProductStock;
import com.nvn.vendingmachine.exception.InsufficientMoneyInsertedException;
import com.nvn.vendingmachine.exception.InsufficientStockException;
import com.nvn.vendingmachine.exception.InvalidProductNameException;
import com.nvn.vendingmachine.exception.InvalidProductPriceException;
import com.nvn.vendingmachine.exception.InvalidQuantityException;
import com.nvn.vendingmachine.exception.ProductNotFoundException;

public interface IVendingMachineService {


	public Result buyProduct(String productName, int quantity, List<Coins> coins) throws ProductNotFoundException,
			InsufficientStockException, InsufficientMoneyInsertedException, InvalidQuantityException;

	public boolean addProduct(List<ProductStock> products)
			throws InvalidQuantityException, InvalidProductNameException, InvalidProductPriceException;

}
