package com.Bank;

public class Customer {
	int balance=10000;
	
	
	public static boolean go=true;
	
	
	 
	
	public  void withdraw(int amount) {
		
		
		go=false;
		System.out.println(Thread.currentThread().getName()+" ");
		if(balance>=amount)balance-=amount;
		System.out.println("current Balance: "+balance);
				
		go=true;
		
		
	}
	
	public void controllingThread(int amount) {

			
		        
		          while(!go)System.out.println("waiting "+Thread.currentThread().getName());
					withdraw(amount);
					
					
					
		
	}

}
