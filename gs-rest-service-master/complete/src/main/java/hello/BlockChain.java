package hello;

public class BlockChain {
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
	 */
	
	public BlockChain(String blckCustNum, String pwd, String svcMgmtNum , String custNum, String lastLogonDt, String fstCreDtm,String lastUpdDtm ){
		this.blckCustNum = blckCustNum;
		this.pwd = pwd;
		this.svcMgmtNum = svcMgmtNum;
		this.custNum = custNum;
		this.lastLogonDt =lastLogonDt;
		this.fstCreDtm =fstCreDtm; 
		this.lastUpdDtm = lastUpdDtm;
	}
	
    private final String blckCustNum;
    private final String pwd;
    private final String svcMgmtNum;
    private final String custNum;
    private final String lastLogonDt;
    private final String fstCreDtm;
    private final String lastUpdDtm;
    
	public String getBlckCustNum() {
		return blckCustNum;
	}
	public String getPwd() {
		return pwd;
	}
	public String getSvcMgmtNum() {
		return svcMgmtNum;
	}
	public String getCustNum() {
		return custNum;
	}
	public String getLastLogonDt() {
		return lastLogonDt;
	}
	public String getFstCreDtm() {
		return fstCreDtm;
	}
	public String getLastUpdDtm() {
		return lastUpdDtm;
	}

    
}
