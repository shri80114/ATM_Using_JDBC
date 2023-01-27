package Projects;

import java.sql.Connection;

public interface InterfaceATM {
	
	public void createNewAccount(Connection con)throws Throwable;
	public void ExistingUser(Connection con) throws Throwable;
	public void checkBalance(Connection con)throws Throwable;
	public void WithdrawalAmount(Connection con) throws Throwable;
	public void DepositAmount(Connection con)throws Throwable;
	public void MiniStatement(Connection con)throws Throwable;
	public void MoneyTransfer(Connection con)throws Throwable;
	
}
