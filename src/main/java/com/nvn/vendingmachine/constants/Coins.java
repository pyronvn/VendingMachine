package com.nvn.vendingmachine.constants;

public enum Coins {

	CENT(1), NICKEL(5), DIME(10), QUARTER(25), DOLLAR(100);

	private final int coinValue;

	Coins(int val) {
		this.coinValue = val;
	}

	public int getCoinValue() {
		return this.coinValue;
	}
}
