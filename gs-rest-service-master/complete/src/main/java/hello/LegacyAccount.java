package hello;

public class LegacyAccount {
	
	private String accountNum;
	private String balance;
	private String transferRate;
	private String accountCd;
	private String accountDtlCd;
	private String legacyAccountNum;
	
	/*
	 * accountCd
	 * bank, eCash, point, gameMoney
	 */
	/*
	 * accountDtlCd
	 * bank - 01 : 하나은행
	 *        02 : 국민은행
	 *        03 : 신한은행
	 *        04 : 우리은행
	 * eCash - 01 : OK cashbag
	 *         02 : 신세계포인트
	 *         03 : 롯데 L포인트
	 * point - 01 : 11st 마일리지
	 *         02 : Gmarget 마일리지
	 *         03 : SKT 멤버십포인트
	 * gameMoney - 01 : 리니지2 레볼루션
	 *             02 : 리니지m
	 * 
	 */
	
	/*
	CREATE TABLE LegacyAccount (
	ACCOUNT_NUM VARCHAR(10) NOT NULL,
	ACCOUNT_CD VARCHAR(10) NOT NULL,
	ACCOUNT_DTL_CD VARCHAR(2) NOT NULL,
	BALANCE INTEGER(20) ,
	TRANSFER_RATE VARCHAR(10) ,
	PRIMARY KEY(ACCOUNT_NUM,ACCOUNT_CD,ACCOUNT_DTL_CD)
	);
	
	 */
	
	public String getLegacyAccountNum() {
		return legacyAccountNum;
	}
	public void setLegacyAccountNum(String legacyAccountNum) {
		this.legacyAccountNum = legacyAccountNum;
	}
	public String getAccountNum() {
		return accountNum;
	}
	public void setAccountNum(String accountNum) {
		this.accountNum = accountNum;
	}
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getTransferRate() {
		return transferRate;
	}
	public void setTransferRate(String transferRate) {
		this.transferRate = transferRate;
	}
	public String getAccountCd() {
		return accountCd;
	}
	public void setAccountCd(String accountCd) {
		this.accountCd = accountCd;
	}
	public String getAccountDtlCd() {
		return accountDtlCd;
	}
	public void setAccountDtlCd(String accountDtlCd) {
		this.accountDtlCd = accountDtlCd;
	}
	public LegacyAccount(String accountNum, String balance, String transferRate, String accountCd,
			String accountDtlCd, String legacyAccountNum) {
		super();
		this.accountNum = accountNum;
		this.balance = balance;
		this.transferRate = transferRate;
		this.accountCd = accountCd;
		this.accountDtlCd = accountDtlCd;
		this.legacyAccountNum = legacyAccountNum;
	}

	
	
	

}
