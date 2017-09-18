package hello;

public class BuypassStruct {
	
	private String blckCustNum = "";
	private String balace="";
	private String sender="";
	private String receiver="";
	private String amount="";
	private String event_dtm="";
	private String accountCd="";
	private String accountDtlCd ="";
	
	
	public BuypassStruct(String blckCustNum, String balace, String sender, String receiver, String amount,
			String event_dtm, String accountCd, String accountDtlCd) {
		super();
		this.blckCustNum = blckCustNum;
		this.balace = balace;
		this.sender = sender;
		this.receiver = receiver;
		this.amount = amount;
		this.event_dtm = event_dtm;
		this.accountCd = accountCd;
		this.accountDtlCd = accountDtlCd;
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

	public String getBlckCustNum() {
		return blckCustNum;
	}
	public void setBlckCustNum(String blckCustNum) {
		this.blckCustNum = blckCustNum;
	}
	public String getBalace() {
		return balace;
	}
	public void setBalace(String balace) {
		this.balace = balace;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getEvent_dtm() {
		return event_dtm;
	}
	public void setEvent_dtm(String event_dtm) {
		this.event_dtm = event_dtm;
	}

}
