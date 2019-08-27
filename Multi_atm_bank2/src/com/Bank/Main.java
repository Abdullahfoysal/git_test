package com.Bank;

public class Main {
	public static void main(String[] args) {
	 
	
	Customer c=new Customer();
	
	Atm t1=new Atm(c,100,"Atm 1");
	Atm t2=new Atm(c,400,"Atm 2");
	Atm t3=new Atm(c,800,"Atm 3");
	Atm t4=new Atm(c,1000,"Atm 4");
	t1.start();
	t2.start();
	t3.start();
	t4.start();
	
	
	}
}
