package hello;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockChainController {
	
	@RequestMapping("/check")	// 회원가입여부 체크 
	public String regCheck(@RequestParam(value="uuid", defaultValue="") String uuid){

		SvcInfo svcInfo = null;
		
		BlockChainDAO blockChainDAO = new BlockChainDAO();
		
		BlockChain blckChn = blockChainDAO.CheckBlckChainMbrInfo(uuid);
		
		BlockChainReturnParam blockChainReturnParam = new BlockChainReturnParam() ;
		
		if( blckChn == null ){
			// 회선이 존재하지 않습니다.
			blockChainReturnParam.setReturnCode("Y");
		} else {
			blockChainReturnParam.setReturnCode("N");
		}
		
		return blockChainReturnParam;
	}
	
	@RequestMapping("/signup")		// 회원가입 처리 
	public CustInfo custInfo(@RequestParam(value="blckChnId", defaultValue="01063294236@kr") String blckChnId,
			@RequestParam(value="pwd", defaultValue="") String pwd,
			@RequestParam(value="uuid", defaultValue="") String uuid){
		
		String svcNum = blckChnId.split("@")[0];
		
		BlockChainDAO blockChainDAO = new BlockChainDAO();
		
		BlockChainReturnParam blockChainReturnParam = new BlockChainReturnParam() ;
		
		SvcInfo svcInfo = blockChainDAO.SelectSvcInfo(svcNum);
		
		if( svcInfo == null ){
			// 회선이 존재하지 않습니다.
			blockChainReturnParam.setReturnCode("1002");
		}else{
			long blckChnCustNum = blockChainDAO.SelectNextBlckCustNum();
			
			BlockChain blckchnForInsert = new BlockChain(String.valueOf(blckChnCustNum),
					pwd,
					svcInfo.getSvcMgmtNum(),
					svcInfo.getCustNum(),
					uuid,
					"",
					"",
					"");
			
			boolean result = blockChainDAO.InsertBlckChnMbrInfo(blckchnForInsert);
			
			if(result){
				blockChainReturnParam.setReturnCode("1001");
				blockChainReturnParam.setBlckCustNum(String.valueOf(blckChnCustNum));
			}else{
				blockChainReturnParam.setReturnCode("1003");
			}
		}
		
		return blockChainReturnParam;
	}
	
	@RequestMapping("/login")	// 로그인 처리
	public CustInfo custInfo(@RequestParam(value="uuid", defaultValue="") String uuid,
			@RequestParam(value="pwd", defaultValue="") String pwd){
		
		BlockChainDAO blockChainDAO = new BlockChainDAO();
		
		BlockChain blckChn = blockChainDAO.SelectBlckChnMbrInfo(uuid, pwd);
		
		BlockChainReturnParam blockChainReturnParam = new BlockChainReturnParam() ;
		
		// 블럭체인정보 존재 여부 확인
		if(blckChn != null ){
			blockChainReturnParam.setReturnCode("2001");
			blockChainReturnParam.setBlckCustNum(blckChn.getBlckCustNum());	// 블록체인 고객번호 
		}else{
			blockChainReturnParam.setReturnCode("2002");
		}
		
		return blockChainReturnParam;
	}
	
	
}
