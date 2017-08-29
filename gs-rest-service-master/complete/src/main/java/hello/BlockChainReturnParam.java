package hello;

public class BlockChainReturnParam {
	private String returnCode = "";
	private String blckCustNum = "";
	
	/*
	 * returnCode 
	 * 1001 : 블록체인정보 생성 성공 
	 * 1002 : 회선정보 없음
	 * 1003 : 블록체인정보 생성 실패 
	 * 2001 : 로그인 정상 확인 
	 * 2002 : 패스워드 불일치
	 */
	
	BlockChainReturnParam(){
		this.returnCode = "";
		this.blckCustNum = "";
	}
	
	public String getReturnCode() {
		return returnCode;
	}
	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}
	public String getBlckCustNum() {
		return blckCustNum;
	}
	public void setBlckCustNum(String blckCustNum) {
		this.blckCustNum = blckCustNum;
	}
	
	
}
