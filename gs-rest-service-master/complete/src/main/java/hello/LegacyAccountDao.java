package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class LegacyAccountDao {
Connection connection;
	

	String driver = "org.mariadb.jdbc.Driver";
	String url = "jdbc:mariadb://127.0.0.1:3306/mysql";
	String id = "root";
	String pw = "test1234";

	public boolean deposit(LegacyAccount la){
		int result = 0;
		Statement statement = null;
		String query = "INSERT INTO LegacyAccount "
				+ "( ACCOUNT_NUM,"
				+ " ACCOUNT_CD,"
				+ " ACCOUNT_DTL_CD,"
				+ " BALANCE,"
				+ " TRANSFER_RATE "
				+ " LEGACY_ACCOUNT_NUM ) "
				+ " VALUES ("
				+ la.getAccountNum()
				+ ", '"
				+ la.getAccountCd()
				+ "',"
				+ la.getAccountDtlCd()
				+ ","
				+ la.getBalance()
				+ ","
				+ la.getTransferRate()
				+ ","
				+ la.getLegacyAccountNum()
				+ '"'
				+ ")";
		
		try{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, id, pw);
			statement = connection.createStatement();
			result = statement.executeUpdate(query);
			
		}catch(Exception e){
			System.out.println(e);
		}finally {
			try {
				if(statement != null) statement.close();
				if(connection != null) connection.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if(result > 0 ){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean updateBalace(LegacyAccount la){
		int result = 0;
		Statement statement = null;
		String query = "UPDATE LegacyAccount "
				+ " SET BALANCE = " + la.getBalance()
				+ " WHERE ACCOUNT_NUM = '" + la.getAccountNum() + "'"
				+ " AND ACCOUNT_CD = '" + la.getAccountCd() + "'"
				+ " AND ACCOUNT_DTL_CD  = '" + la.getAccountDtlCd() + "'";
		
		try{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, id, pw);
			statement = connection.createStatement();
			result = statement.executeUpdate(query);
			
		}catch(Exception e){
			System.out.println(e);
		}finally {
			try {
				if(statement != null) statement.close();
				if(connection != null) connection.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		if(result > 0 ){
			return true;
		}else{
			return false;
		}
		
	}
	
	public String selectAccountBalance(LegacyAccount la) {
		
		ResultSet resultSet = null;
		Statement statement = null;
		String strBalance = "-1";
		String query = "SELECT "
				+ " BALANCE "
				+ " FROM LegacyAccount "
				+ " WHERE ACCOUNT_NUM = "  + '"' + la.getAccountNum() + '"'
				+ " AND ACCOUNT_CD = " + '"' + la.getAccountCd() + '"'
				+ " AND ACCOUNT_DTL_CD = "  + '"' + la.getAccountDtlCd()+ '"';
		
		try{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, id, pw);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				strBalance = resultSet.getString("BALANCE");
			}

			
		}catch(Exception e){
			System.out.println(e);
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(statement != null) statement.close();
				if(connection != null) connection.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}

		return strBalance;
	}
	
	public LegacyAccount[] selectLegacyAccount(String blckCustNum ) {
		
		ResultSet resultSet = null;
		Statement statement = null;
		
		LegacyAccount[] laArray = new LegacyAccount[12];
		
		String query =" SELECT "
				+ " ACCOUNT_NUM ,"
				+ " ACCOUNT_CD  , "
				+ " ACCOUNT_DTL_CD   , "
				+ " BALANCE   , "
				+ " TRANSFER_RATE  "
				+ " FROM LegacyAccount "
				+ " WHERE ACCOUNT_NUM = " + '"' + blckCustNum + '"';
				
		try{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, id, pw);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			int cnt = 0;
			
			while(resultSet.next()){
				laArray[cnt] = new LegacyAccount( resultSet.getString("ACCOUNT_NUM") ,
						                              resultSet.getString("ACCOUNT_CD") ,
						                              resultSet.getString("ACCOUNT_DTL_CD") ,
						                              resultSet.getString("BALANCE") ,
						                              resultSet.getString("TRANSFER_RATE"),
						                              resultSet.getString("LEGACY_ACCOUNT_NUM")
						                              );
				
				cnt++;
			}

			
		}catch(Exception e){
			System.out.println(e);
		}finally {
			try {
				if(resultSet != null) resultSet.close();
				if(statement != null) statement.close();
				if(connection != null) connection.close();
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		
		return laArray;
		
	}
	
}
