package hello;

public class CustInfo {
	
	private String custNum = "";
	private String custNm = "";
	private String ssnBirthDt = "";
	private String ssnSexCd = "";
	
	CustInfo(String custNum, String custNm, String ssnBirthDt, String ssnSexCd){
		this.setCustNum(custNum);
		this.setCustNm(custNm);
		this.setSsnBirthDt(ssnBirthDt);
		this.setSsnSexCd(ssnSexCd);
	}

	public String getCustNum() {
		return custNum;
	}

	public void setCustNum(String custNum) {
		this.custNum = custNum;
	}
	
	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}

	public String getSsnBirthDt() {
		return ssnBirthDt;
	}

	public void setSsnBirthDt(String ssnBirthDt) {
		this.ssnBirthDt = ssnBirthDt;
	}

	public String getSsnSexCd() {
		return ssnSexCd;
	}

	public void setSsnSexCd(String ssnSexCd) {
		this.ssnSexCd = ssnSexCd;
	}

}
