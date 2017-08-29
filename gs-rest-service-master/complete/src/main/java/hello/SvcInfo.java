package hello;

public class SvcInfo {
	/*
	 * CREATE TABLE SVC_INFO (
	SVC_MGMT_NUM VARCHAR(10) NOT NULL,
	SVC_NUM VARCHAR(11) NOT NULL,
	CUST_NUM VARCHAR(10) NOT NULL
	PRIMARY KEY(SVC_MGMT_NUM));
	 */
	
	private String svcMgmtNum = "";
	private String svcNum = "";
	private String custNum = "";
	
	SvcInfo(String svcMgmtNum , String svcNum, String custNum){
		this.setSvcMgmtNum(svcMgmtNum);
		this.setSvcNum(svcNum);
		this.setCustNum(custNum);
	}

	public String getSvcMgmtNum() {
		return svcMgmtNum;
	}

	public void setSvcMgmtNum(String svcMgmtNum) {
		this.svcMgmtNum = svcMgmtNum;
	}

	public String getSvcNum() {
		return svcNum;
	}

	public void setSvcNum(String svcNum) {
		this.svcNum = svcNum;
	}

	public String getCustNum() {
		return custNum;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}

}
