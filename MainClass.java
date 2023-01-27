package Projects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Scanner;

public class MainClass {
	static Connection con;

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) throws Throwable {

		InterfaceATM it1 = new AtmOperation();

		Class.forName("com.mysql.cj.jdbc.Driver");
		con = DriverManager.getConnection("jdbc:mysql://localhost:3306/banking", "root", "1234");

		System.out.println(" ---------------  Welcome to HDFC Bank --------------- ");
		System.out.println();
		System.out.println("If you want create new Bank Account select Option 1 .... Otherwise go to 2 option");
		System.out.println();
		System.out.println(" 1) New User \n 2) Existing user ");
		int num = sc.nextInt();

		switch (num) {

		case 1:
			it1.createNewAccount(con);
			break;
		case 2:
			it1.ExistingUser(con);
			int check = sc.nextInt();
			switch (check) {
			case 1:
				it1.checkBalance(con);
				break;
			case 2:
				it1.WithdrawalAmount(con);
				break;
			case 3:
				it1.DepositAmount(con);
				break;
			case 4:
				it1.MiniStatement(con);
				break;
			case 5:
				it1.MoneyTransfer(con);
				break;
			default : throw new Exception("Invalid Input");
			}
		}
		
		/*
		 * System.out.println("if you want continue press 1 ...Otherwise press 2"); int
		 * press=sc.nextInt(); if(press==1) { it1.ExistingUser(con); } else {
		 * System.out.println("---------- Visit again ----------"); }
		 */
		sc.close();
		con.close();
	}
}
