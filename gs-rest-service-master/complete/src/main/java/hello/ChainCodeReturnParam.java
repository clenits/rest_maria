package hello;

import java.util.List;

public class ChainCodeReturnParam {

	private String blckCustNum = "";
	private String balance = "";
	private String returnCode = "";
	private List<BuypassStruct> hist = null;
	
	ChainCodeReturnParam(String blckCustNum, String balance, String returnCode  ){
		this.blckCustNum = blckCustNum;
		this.balance = balance;
		this.returnCode = returnCode;
	}
	
	public String getBlckCustNum() {
		return blckCustNum;
	}
	public void setBlckCustNum(String blckCustNum) {
		this.blckCustNum = blckCustNum;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
}
