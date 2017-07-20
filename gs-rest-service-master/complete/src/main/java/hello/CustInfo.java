package hello;

public class CustInfo {
	
	private String custNm = "";
	private String ssnBirthDt = "";
	private String ssnSexCd = "";
	
	CustInfo( String custNm, String ssnBirthDt, String ssnSexCd){
		this.setCustNm(custNm);
		this.setSsnBirthDt(ssnBirthDt);
		this.setSsnSexCd(ssnSexCd);
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
