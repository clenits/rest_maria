package hello;

public class BlockChainReturnParam {
	private String returnCode = "";
	private String blckCustNum = "";
	
	/*
	 * returnCode 
	 * 1001 : 인증 성공
	 * 1002 : 회원정보 생성 완료
	 * 2001 : 패스워드 불일치
	 * 3001 : 서비스가 존재하지 않습니다.
	 * 4001 : 회원정보 생성에 실패하였습니다.
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
