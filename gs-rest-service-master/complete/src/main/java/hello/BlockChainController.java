package hello;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlockChainController {
	
	@RequestMapping("/check")	// 회원가입여부 체크 
	public BlockChainReturnParam regCheck(@RequestParam(value="uuid", defaultValue="") String uuid){
		
		BlockChainDAO blockChainDAO = new BlockChainDAO();
		
		BlockChain blckChn = blockChainDAO.CheckBlckChainMbrInfo(uuid);
		
		BlockChainReturnParam blockChainReturnParam = new BlockChainReturnParam() ;
		
		if( blckChn == null ){
			// 회선이 존재하지 않습니다.
			blockChainReturnParam.setReturnCode("N");
		} else {
			blockChainReturnParam.setReturnCode("Y");
		}
		
		return blockChainReturnParam;
	}
	
	@RequestMapping("/signup")		// 회원가입 처리 
	public BlockChainReturnParam custInfo(@RequestParam(value="blckChnId", defaultValue="01063294236@kr") String blckChnId,
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
				// Legacy 계정 초기화
				initAccount(String.valueOf(blckChnCustNum));
			}else{
				blockChainReturnParam.setReturnCode("1003");
			}
			
		}
		
		
		
		return blockChainReturnParam;
	}
	
	@RequestMapping("/login")	// 로그인 처리
	public BlockChainReturnParam custInfo(@RequestParam(value="uuid", defaultValue="") String uuid,
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
	
	@RequestMapping("/query") // 잔액조회
	public ChainCodeReturnParam query( @RequestParam(value="blckCustNum", defaultValue="") String blckCustNum  ) {
		
		String USER_AGENT = "Mozilla/5.0";
		
		ChainCodeReturnParam chainCodeReturnParam = null;
		
		String url = "http://blockchain.skcc.com:4000/query";
		String targetPeers = "{\"org1\":[\"peer1\"]}";
		String chaincodeId = "buypassV3";
		String args = blckCustNum;
		
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String urlParameters = "fcn=query&args=" + args + "&chainCodeId=" + chaincodeId + "&targetPeers=" + targetPeers;
			
			con.setDoOutput(true);
			DataOutputStream  wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONParser jsonParser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
			JSONArray returnMsg =  (JSONArray) jsonObject.get("msg");
			
			JSONObject blockChainAccount = (JSONObject) jsonParser.parse( returnMsg.get(0).toString() );
			
			chainCodeReturnParam = new ChainCodeReturnParam(
															blockChainAccount.get("BlckCustNum").toString()
															, blockChainAccount.get("Balance").toString()
															,"0");
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return chainCodeReturnParam;
	}
	
	@RequestMapping("/deposit") // 입금
	public ChainCodeReturnParam deposit( @RequestParam(value="blckCustNum", defaultValue="") String blckCustNum ,
			                             @RequestParam(value="amount", defaultValue="") String amount ) {
		String USER_AGENT = "Mozilla/5.0";
		
		ChainCodeReturnParam chainCodeReturnParam = null;
		
		String url = "http://blockchain.skcc.com:4000/invoke";
		String targetPeers = "{\"org1\":[\"peer1\"]}";
		String chaincodeId = "buypassV3";
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		
		String args = blckCustNum + " " + amount + " " + sdf.format(date);
			
		
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String urlParameters = "fcn=deposit&args=" + args + "&chainCodeId=" + chaincodeId + "&targetPeers=" + targetPeers;
			
			con.setDoOutput(true);
			DataOutputStream  wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONParser jsonParser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());

			String returnTx =  (String) jsonObject.get("msg");
			
			if(returnTx.startsWith("TX_ID")) {
				chainCodeReturnParam = new ChainCodeReturnParam("", "", "0");
			}else {
				chainCodeReturnParam = new ChainCodeReturnParam("", "", "-1");
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return chainCodeReturnParam;
	}
	
	@RequestMapping("/transfer") // 송금
	public ChainCodeReturnParam transfet( @RequestParam(value="blckCustNum", defaultValue="") String blckCustNum ,
                                          @RequestParam(value="amount", defaultValue="") String amount ,
                                          @RequestParam(value="svcNum", defaultValue="") String svcNum ) {
		String USER_AGENT = "Mozilla/5.0";
		
		ChainCodeReturnParam chainCodeReturnParam = null;
		
		String url = "http://blockchain.skcc.com:4000/invoke";
		String targetPeers = "{\"org1\":[\"peer1\"]}";
		String chaincodeId = "buypassV3";
		
		BlockChainDAO bcDao = new BlockChainDAO();
		
		BlockChain blockChain = bcDao.selectBlckCustNumBySvcNum(svcNum);
		
		if(blockChain == null) {
			return new ChainCodeReturnParam("", "", "-1");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		
		String args = blckCustNum + " " + amount + " " + blockChain.getBlckCustNum() + " " + sdf.format(date);;
		
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String urlParameters = "fcn=transfer&args=" + args + "&chainCodeId=" + chaincodeId + "&targetPeers=" + targetPeers;
			
			con.setDoOutput(true);
			DataOutputStream  wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONParser jsonParser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());

			String returnTx =  (String) jsonObject.get("msg");
			
			if(returnTx.startsWith("TX_ID")) {
				chainCodeReturnParam = new ChainCodeReturnParam("", "", "0");
			}else {
				chainCodeReturnParam = new ChainCodeReturnParam("", "", "-1");
			}
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return chainCodeReturnParam;
	}
	
	@RequestMapping("/hist") // 이력조회
	public BuypassStruct[] hist( @RequestParam(value="blckCustNum", defaultValue="") String blckCustNum  ) {
		
		String USER_AGENT = "Mozilla/5.0";
		
		BuypassStruct[] buypassStructArray = null;
		
		String url = "http://blockchain.skcc.com:4000/query";
		String targetPeers = "{\"org1\":[\"peer1\"]}";
		String chaincodeId = "buypassV3";
		String args = blckCustNum;
		
		try {
			URL obj = new URL(url);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			con.setRequestMethod("POST");
			con.setRequestProperty("User-Agent", USER_AGENT);
			con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			
			String urlParameters = "fcn=hist&args=" + args + "&chainCodeId=" + chaincodeId + "&targetPeers=" + targetPeers;
			
			con.setDoOutput(true);
			DataOutputStream  wr = new DataOutputStream(con.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.flush();
			wr.close();
			
			int responseCode = con.getResponseCode();
			
			System.out.println("\nSending 'POST' request to URL : " + url);
			System.out.println("Post parameters : " + urlParameters);
			System.out.println("Response Code : " + responseCode);

			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();

			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();

			JSONParser jsonParser = new JSONParser();
			
			JSONObject jsonObject = (JSONObject) jsonParser.parse(response.toString());
			System.out.println("jsonObject = " + jsonObject);
			
			JSONArray msgJSONArray = (JSONArray) jsonObject.get("msg");
			System.out.println("msgJSONArray = " + msgJSONArray);
			
			JSONObject returnMsg = (JSONObject) jsonParser.parse( msgJSONArray.get(0).toString() );
			System.out.println("returnMsg = " + returnMsg);
			
			
			JSONArray histJSONArray = (JSONArray) returnMsg.get("AccountHist");
			System.out.println("histJSONArray = " + histJSONArray);
			
			buypassStructArray = new BuypassStruct[histJSONArray.size()];
			
			for ( int i = 0 ; i < histJSONArray.size() ; i ++ ) {
				
				JSONObject blockChainAccountHist = (JSONObject) jsonParser.parse( histJSONArray.get(i).toString() );
				
				BuypassStruct buypassStruct = new BuypassStruct(
						blockChainAccountHist.get("BlckCustNum").toString()
						, blockChainAccountHist.get("Balance").toString()
						, blockChainAccountHist.get("Sender").toString()
						, blockChainAccountHist.get("Receiver").toString()
						, blockChainAccountHist.get("Amount").toString()
						, blockChainAccountHist.get("Event_dtm").toString() );
				
				buypassStructArray[i] = buypassStruct;
			}
			
			
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return buypassStructArray;
	}
	
	@RequestMapping("/changeInto") // 블록체인 포인트로 환전
	public ChainCodeReturnParam change( @RequestParam(value="blckCustNum", defaultValue="") String blckCustNum ,
                                          @RequestParam(value="amount", defaultValue="") String amount ,
                                          @RequestParam(value="accountCd", defaultValue="") String accountCd,
                                          @RequestParam(value="accountDtlCd", defaultValue="") String accountDtlCd  ) {
		ChainCodeReturnParam chainCodeReturnParam = null;
		LegacyAccountDao laDao = new LegacyAccountDao();
		
		LegacyAccount la = new LegacyAccount(blckCustNum, amount,"1.0",accountCd , accountDtlCd );
		
		String currentBalance = laDao.selectAccountBalance(la);
		String tobeBalance = "";
		
		if(accountCd.equals("bank")) {
			//은행 출금
			
			tobeBalance = String.valueOf(  Integer.valueOf(currentBalance) - Integer.valueOf(amount) );
			
			la.setBalance(tobeBalance);
			
			if( laDao.updateBalace(la) ) {
				deposit(blckCustNum,amount);
			}
			
		}else if(accountCd.equals("eCash")){
			
			la.setTransferRate("0.9");
			
			tobeBalance = String.valueOf(  Integer.valueOf(currentBalance) - Integer.valueOf(amount) );
			
			la.setBalance(tobeBalance);
			
			String tobeAmount = String.valueOf( Integer.valueOf(amount) * 0.9 );
			
			if( laDao.updateBalace(la) ) {
				deposit(blckCustNum,tobeAmount);
			}
			
		}else {
			chainCodeReturnParam = new ChainCodeReturnParam("","","Wrong AccountCd");
		}
		
		
	
		return chainCodeReturnParam;
	}
	
	
	private boolean initAccount(String blckChnCustNum) {
		
		LegacyAccountDao laDao = new LegacyAccountDao();
		
		LegacyAccount la = new LegacyAccount(blckChnCustNum,"1000000","1.0","bank","01");
		// 은핸 계좌 초기화
		if( laDao.deposit(la) ) {
			la.setAccountDtlCd("02");
			laDao.deposit(la);
			la.setAccountDtlCd("03");
			laDao.deposit(la);
			la.setAccountDtlCd("04");
			laDao.deposit(la);
		}
		
		
		// eCash 초기화
		la.setAccountCd("eCash");
		la.setAccountDtlCd("01");
		la.setBalance("110000");
		la.setTransferRate("1.1");
		
		if ( laDao.deposit(la) ) {
			la.setAccountDtlCd("02");
			laDao.deposit(la);
			la.setAccountDtlCd("03");
			laDao.deposit(la);
		}
		
		// point 초기화
		la.setAccountCd("point");
		la.setAccountDtlCd("01");
		la.setBalance("500000");
		la.setTransferRate("5");
		if ( laDao.deposit(la) ) {
			la.setAccountDtlCd("02");
			laDao.deposit(la);
			la.setAccountDtlCd("03");
			laDao.deposit(la);
		}
		
		//gameMoney 초기화
		la.setAccountCd("gameMoney");
		la.setAccountDtlCd("01");
		la.setBalance("10000");
		la.setTransferRate("0.1");
		if ( laDao.deposit(la) ) {
			la.setAccountDtlCd("02");
			laDao.deposit(la);
		}
		
		
		return true;
	}
}
