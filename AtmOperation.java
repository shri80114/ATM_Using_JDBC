package Projects;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;

public class AtmOperation implements InterfaceATM {

	private static String AccountHolderName;
	private static int password;
	public static String userName;
	public static String UserName;
	static int amount;
	static double balance;
	static Scanner sc = new Scanner(System.in);

	public void createNewAccount(Connection con) throws Throwable {
		String query = "insert into hdfcbank(name,userName,pass,balance) values(?,?,?,0);";
		PreparedStatement psmt = con.prepareStatement(query);
		System.out.println("Enter Your Name ");
		String name = sc.next();
		System.out.println("Set your password ");
		int Pass = sc.nextInt();
		System.out.println("Set your user name ");
		String userName1 = sc.next();

		psmt.setString(1, name);
		psmt.setString(2, userName1);
		psmt.setInt(3, Pass);
		System.out.println("Document verification Processing...");
		Thread.sleep(5000);
		String query10 = "create table " + name
				+ "(Trans_Id int auto_increment primary key ,amount decimal(8,2) ,Trans_Date date, type varchar(100));";
		Statement st = con.createStatement();
		st.execute(query10);

		String query11 = "insert into hdfcbank(name,userName,pass,balance) values(?,?,?,0)";
		PreparedStatement ps = con.prepareStatement(query11);
		ps.setString(1, name);
		ps.setString(2, userName1);
		ps.setInt(3, Pass);
		ps.executeUpdate();
		System.out.println("Your Account is Be Created Sucessfully");

	}

	public void ExistingUser(Connection con) throws Throwable {
		System.out.println(" Enter Your Account Number ");
		String UserName = sc.next();
		System.out.println("Enter Your Passoword ");
		int pass = sc.nextInt();
		Statement sta1 = con.createStatement();
		ResultSet set1 = sta1
				.executeQuery("select name from hdfcbank where pass =" + pass + " and name = '" + UserName + "';");
		while (set1.next()) {
			// AccountHolderName = set1.getString("name");
//			password =set1.getInt("passCode");
//			System.out.println(AccountHolderName);
			password = pass;
			AccountHolderName = UserName;

		}
		if (pass == password && AccountHolderName == UserName) {
			System.out.println("Processing...");
			Thread.sleep(3000);

			System.out.println("Account Holder Name : " + AccountHolderName);
			System.out.println("Select Options");
			System.out.println(
					" 1) check Balance \n 2) Withdrawal amount \n 3) Deposit Amount  \n 4) Mini Statement \n 5) Money Transfer ");
		} else {
			throw new Exception("Incorrect User * Password");
		}
	}

	public void checkBalance(Connection con) throws Throwable {
		Statement sta = con.createStatement();
		ResultSet set = sta.executeQuery("select balance from hdfcbank where name ='" + AccountHolderName + "';");
		while (set.next()) {
			System.out.println("Available account balance : " + set.getDouble("balance"));
		}

	}

	public void WithdrawalAmount(Connection con) throws Throwable {
		System.out.println("Enter Withdrawal amount ");
		int amount = sc.nextInt();

		String query12 = "select balance ,name from hdfcbank where name ='" + AccountHolderName + "';";

		Statement sta = con.createStatement();
		ResultSet set = sta.executeQuery(query12);
		while (set.next()) {
			AccountHolderName = set.getString("name");
			balance = set.getDouble("balance");
		}

		if (amount < balance) {
			String query1 = "update hdfcbank set balance = balance - ? where name = '" + AccountHolderName + "';";

			System.out.println("Account Holder Name is " + AccountHolderName);
			PreparedStatement psmt0 = con.prepareStatement(query1);

			psmt0.setDouble(1, amount);
			psmt0.executeUpdate();

			Statement sta2 = con.createStatement();
			ResultSet set2 = sta2.executeQuery("select balance from hdfcbank where name ='" + AccountHolderName + "';");
			while (set.next()) {
				System.out.println("Available Balance : " + set2.getDouble("balance"));
			}

			String query3 = "insert into " + AccountHolderName + "(amount,Trans_Date ,type) values(?,now(),?)";
			PreparedStatement psmt1 = con.prepareStatement(query3);
			psmt1.setDouble(1, amount);
			psmt1.setString(2, "Debited");
			psmt1.executeUpdate();
		} else {
			throw new Exception("Insufficent Balance");
		}

	}

	public void DepositAmount(Connection con) throws Throwable {
		String query3 = "update hdfcbank set balance = balance + ? where name = '" + AccountHolderName + "';";

		System.out.println("Account Holder Name is " + AccountHolderName);
		PreparedStatement psmt3 = con.prepareStatement(query3);
		System.out.println("Enter Debited amount ");
		int amount = sc.nextInt();
		psmt3.setDouble(1, amount);
		psmt3.executeUpdate();

		Statement sta = con.createStatement();
		ResultSet set = sta.executeQuery("select balance from hdfcbank where name ='" + AccountHolderName + "';");
		while (set.next()) {
			System.out.println("Available Balance : " + set.getDouble("balance"));
		}

		String query4 = "insert into " + AccountHolderName + "(amount,Trans_Date ,type) values(?,now(),?)";
		PreparedStatement psmt4 = con.prepareStatement(query4);
		psmt4.setDouble(1, amount);
		psmt4.setString(2, "Debited");
		psmt4.executeUpdate();

	}

	public void MiniStatement(Connection con) throws Throwable {
		System.out.println("Processing...");
		Thread.sleep(3000);
		Statement sta = con.createStatement();
		String query5 = "select * from " + AccountHolderName + ";";
		ResultSet set = sta.executeQuery(query5);
		ResultSetMetaData data = set.getMetaData();
		int x = data.getColumnCount();

		for (int i = 1; i <= x; i++) {
			if (i == 2) {
				System.out.print(data.getColumnName(i) + "\t\t ");
			} else {
				System.out.print(data.getColumnName(i) + "\t");
			}
		}
		System.out.println();
		while (set.next()) {
			System.out.println(set.getInt(1) + " \t     " + set.getDouble(2) + " \t " + set.getTimestamp(3) + "  "
					+ set.getString(4) + "  ");
		}

	}

	public void MoneyTransfer(Connection con) throws Throwable {
		System.out.println("Which account transfer you money. Enter this Account Holder Name ");
		UserName =sc.next();
		System.out.println("Enter account Number");
		int pass1 = sc.nextInt();
		Statement sta11 = con.createStatement();
		ResultSet set11 = sta11.executeQuery("select name from hdfcbank where pass =" + pass1 + ";");
		while (set11.next()) {
			userName = set11.getString("name");
			password = pass1;
		}

		if (password == pass1  ) { ////&& UserName == userName

			System.out.println("How much transfer amount ? please enter amount ");
			double amount = sc.nextDouble();
			String query6 = "update hdfcbank set balance =balance + ? where name ='" + userName + "';";
			PreparedStatement psmt6 = con.prepareStatement(query6);
			psmt6.setDouble(1, amount);
			psmt6.executeUpdate();

			String query7 = "update hdfcbank set balance = balance - ? where name=' " + AccountHolderName + "';";
			PreparedStatement psmt7 = con.prepareStatement(query7);
			psmt7.setDouble(1, amount);

			String query8 = "update hdfcbank set balance = balance - ? where name=' " + userName + "';";
			PreparedStatement psmt8 = con.prepareStatement(query8);
			psmt8.setDouble(1, amount);
		

			String query9 = "insert into " + AccountHolderName + "(amount,Trans_Date,type) values(?,now(),?)";
			
			PreparedStatement psmt9 = con.prepareStatement(query9);
			psmt9.setDouble(1, amount);
			psmt9.setString(2, "Debited");
			psmt9.executeUpdate();

			String query4 = "insert into " + userName + "(amount,Trans_Date,type) values(?,now(),?)";
			
			PreparedStatement psmt4 = con.prepareStatement(query4);
			psmt4.setDouble(1, amount);
			psmt4.setString(2, "Credited");
			psmt4.executeUpdate();

			//System.out.println("Transction Sucessfully Completed");
			Statement sta = con.createStatement();
			ResultSet set = sta.executeQuery("select balance from hdfcbank where name ='" + AccountHolderName + "';");
			while (set.next()) {
				System.out.println("Available Balance : " + set.getDouble("balance"));
			}
		} else {

			System.out.println(" Incrroct Account Details");
		}

	}

}
