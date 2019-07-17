package com.Bank;

public class Atm extends Thread{
	Customer c;
	int amount;
	public Atm(Customer c,int amount,String name) {
		super.setName(name);
		this.c=c;
		this.amount=amount;
		
	}
	
	@Override
	public void run() {
		
		c.controllingThread(amount);
	}

}
