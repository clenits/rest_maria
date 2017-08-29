package hello;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;


public class BlockChainDAO {
	Connection connection;
	

	String driver = "org.mariadb.jdbc.Driver";
	String url = "jdbc:mariadb://127.0.0.1:3306/mysql";
	String id = "root";
	String pw = "haha1027!";

	public BlockChain SelectBlckChnMbrInfo(String uuid, String pwd){
		ResultSet resultSet = null;
		Statement statement = null;
		BlockChain blockChain = null;
		
		String query = "SELECT "
				+ "BLCK.BLCK_CUST_NUM ,"
				+ "BLCK.PWD ,"
				+ "BLCK.SVC_MGMT_NUM ,"
				+ "BLCK.CUST_NUM ,"
				+ "BLCK.UUID ,"
				+ "BLCK.LAST_LOGON_DTM ,"
				+ "BLCK.FST_CRE_DTM ,"
				+ "BLCK.LAST_UPD_DTM  "
				+ "FROM BLCK_CHN_MBR_INFO BLCK , SVC_INFO SVC "
				+ "WHERE BLCK.SVC_MGMT_NUM = SVC.SVC_MGMT_NUM "
				+ "AND BLCK.UUID = " + uuid
				+ "AND BLCK.PWD = " + pwd;
		
		try{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, id, pw);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				blockChain = new BlockChain(resultSet.getString("BLCK_CUST_NUM"),
						resultSet.getString("PWD"),
						resultSet.getString("SVC_MGMT_NUM"),
						resultSet.getString("CUST_NUM"),
						resultSet.getString("UUID"),
						resultSet.getString("LAST_LOGON_DTM"),
						resultSet.getString("FST_CRE_DTM"),
						resultSet.getString("LAST_UPD_DTM"));
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

		return blockChain;
		
	}
	
	public BlockChain CheckBlckChainMbrInfo(String uuid){
		SvcInfo svcInfo = null;
		
		ResultSet resultSet = null;
		Statement statement = null;
		
		String query = "SELECT "
				+ " BLCK.BLCK_CUST_NUM ,"
				+ "BLCK.PWD ,"
				+ "BLCK.SVC_MGMT_NUM ,"
				+ "BLCK.CUST_NUM ,"
				+ "BLCK.UUID ,"
				+ "BLCK.LAST_LOGON_DTM ,"
				+ "BLCK.FST_CRE_DTM ,"
				+ "BLCK.LAST_UPD_DTM "
				+ "FROM BLCK_CHN_MBR_INFO BLCK , SVC_INFO SVC "
				+ "WHERE BLCK.SVC_MGMT_NUM = SVC.SVC_MGMT_NUM "
				+ "AND BLCK.UUID = " + uuid;
		
		try{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, id, pw);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				blockChain = new BlockChain(resultSet.getString("BLCK_CUST_NUM"),
						resultSet.getString("PWD"),
						resultSet.getString("SVC_MGMT_NUM"),
						resultSet.getString("CUST_NUM"),
						resultSet.getString("UUID"),
						resultSet.getString("LAST_LOGON_DTM"),
						resultSet.getString("FST_CRE_DTM"),
						resultSet.getString("LAST_UPD_DTM"));
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
		
		return blockChain;
		
	}
		
	public SvcInfo SelectSvcInfo(String svcNum){
		SvcInfo svcInfo = null;
		
		ResultSet resultSet = null;
		Statement statement = null;
		
		String query = "SELECT "
				+ "SVC.SVC_MGMT_NUM ,"
				+ "SVC.SVC_NUM ,"
				+ "SVC.CUST_NUM"
				+ "FROM SVC_INFO SVC "
				+ "WHERE SVC.SVC_NUM = " + svcNum;
		try{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, id, pw);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				svcInfo = new SvcInfo(
						resultSet.getString("SVC_MGMT_NUM"),
						resultSet.getString("SVC_NUM"),
						resultSet.getString("CUST_NUM")
						);
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
		
		return svcInfo;
		
	}
	
	public long SelectNextBlckCustNum(){
		long nextVal = 0;
		ResultSet resultSet = null;
		Statement statement = null;
		String query = " SELECT MAX(BLCK_CUST_NUM)+1 AS NEXTVAL FROM BLCK_CHN_MBR_INFO ";
		
		try{
			Class.forName(driver);
			connection = DriverManager.getConnection(url, id, pw);
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);
			
			while(resultSet.next()){
				nextVal = resultSet.getLong("NEXTVAL");
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
		
		return nextVal;
		
	}
	
	public boolean InsertBlckChnMbrInfo(BlockChain blckchn){
		int result = 0;
		Statement statement = null;
		String query = "INSERT INTO BLCK_CHN_MBR_INFO "
				+ "( BLCK_CUST_NUM,"
				+ " PWD,"
				+ " SVC_MGMT_NUM,"
				+ " CUST_NUM,"
				+ " UUID,"
				+ " LAST_LOGON_DTM,"
				+ " FST_CRE_DTM,"
				+ " LAST_UPD_DTM ) "
				+ " VALUES ("
				+ blckchn.getBlckCustNum()
				+ ", '"
				+ blckchn.getPwd()
				+ "',"
				+ blckchn.getSvcMgmtNum()
				+ ","
				+ blckchn.getCustNum()
				+ ","
				+ blckchn.getUuid()
				+ ",DATE_FORMAT(NOW(),'%Y%m%d%h%i')"
				+ ",DATE_FORMAT(NOW(),'%Y%m%d%h%i')"
				+ ",DATE_FORMAT(NOW(),'%Y%m%d%h%i')"
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
		
	/*
	CREATE TABLE BLCK_CHN_MBR_INFO (
	BLCK_CUST_NUM VARCHAR(20) NOT NULL,
	PWD VARCHAR(64) NOT NULL,
	SVC_MGMT_NUM VARCHAR(10) NOT NULL,
	CUST_NUM VARCHAR(10) NOT NULL,
	LAST_LOGON_DTM VARCHAR(12) ,
	FST_CRE_DTM VARCHAR(12),
	LAST_UPD_DTM VARCHAR(12),
	PRIMARY KEY(SVC_MGMT_NUM)
	);

	CREATE TABLE CUST_INFO (
	CUST_NUM VARCHAR(10) NOT NULL,
	CUST_NM VARCHAR(10) NOT NULL,
	SSN_SEX_CD VARCHAR(1) NOT NULL,
	SSN_BIRTH_DT VARCHAR(6) NOT NULL,
	PRIMARY KEY(CUST_NUM) );

	CREATE TABLE SVC_INFO (
	SVC_MGMT_NUM VARCHAR(10) NOT NULL,
	SVC_NUM VARCHAR(11) NOT NULL,
	CUST_NUM VARCHAR(10) NOT NULL,
	PRIMARY KEY(SVC_MGMT_NUM));
		 */
}
