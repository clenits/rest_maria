package hello;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockChainController {
	
	@RequestMapping("/auth")
	public BlockChainReturnParam auth(@RequestParam(value="blckChnId", defaultValue="01063294236@kr") String blckChnId,
			@RequestParam(value="pwd", defaultValue="pwd") String pwd) {
		
		String svcNum = blckChnId.split("@")[0];
		
		BlockChainDAO blockChainDAO = new BlockChainDAO();
		
		BlockChain blckChn = blockChainDAO.SelectBlckChnMbrInfo(svcNum);
		
		BlockChainReturnParam blockChainReturnParam = new BlockChainReturnParam() ;
		// 블럭체인정보 존재 여부 확인
		if(blckChn != null ){
			// 패스워드 일치 여부 확인
			if(pwd.equals(blckChn.getPwd() )){
				blockChainReturnParam.setReturnCode("1001");
				blockChainReturnParam.setBlckCustNum(blckChn.getBlckCustNum());
			}else{
				blockChainReturnParam.setReturnCode("2001");
			}
		}else{
			SvcInfo svcInfo = blockChainDAO.SelectSvcInfo(svcNum);
			
			if( svcInfo == null ){
				// 회선이 존재하지 않습니다.
				blockChainReturnParam.setReturnCode("3001");
			}else{
				// 신규 회원 가입
				long blckChnCustNum = blockChainDAO.SelectNextBlckCustNum();
				
				BlockChain blckchnForInsert = new BlockChain(String.valueOf(blckChnCustNum),
						pwd,
						svcInfo.getSvcMgmtNum(),
						svcInfo.getCustNum(),
						"",
						"",
						"");
				
				boolean result = blockChainDAO.InsertBlckChnMbrInfo(blckchnForInsert);
				
				if(result){
					blockChainReturnParam.setReturnCode("1002");
					blockChainReturnParam.setBlckCustNum(String.valueOf(blckChnCustNum));
				}else{
					blockChainReturnParam.setReturnCode("4001");
				}
				
			}
		}
		
        return blockChainReturnParam;
    }
	
	@RequestMapping("/custInfo")
	public CustInfo custInfo(@RequestParam(value="blckCustNum", defaultValue="") String blckCustNum){
		CustInfo custInfo = null;
		
		BlockChainDAO blockChainDAO = new BlockChainDAO();
		
		custInfo = blockChainDAO.SelectCustInfo(blckCustNum);
		
		return custInfo;
	}
}
