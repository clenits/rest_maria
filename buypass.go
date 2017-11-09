package main

import (
	"encoding/json"
	"strconv"
	"github.com/hyperledger/fabric/core/chaincode/shim"
	"fmt"
	pb "github.com/hyperledger/fabric/protos/peer"
)

type BPChaincode struct {

}

// Account info
type BPAccounts struct {
	BlckCustNum string `json: "blckcustnum"`
	Balance int `json: "balance"`
	sender string `json: "sender"`
	receiver string `json: "receiver"`
	amount string `json: "amount"`
	event_dtm string `json: "event_dtm"`
}

type ArrayOfBPAccounts struct {
	AccountHist []BPAccounts `json: "accountHIst"`
}
/*
func ( bpc *BPChaincode) Init(stub *shim.ChaincodeStub, function string, args []string)([]byte, error){
	return nil ,nil
}
*/
func (bpc *BPChaincode) Init(stub shim.ChaincodeStubInterface) pb.Response {

	return shim.Success(nil)
}

func (bpc *BPChaincode) Invoke(stub shim.ChaincodeStubInterface)pb.Response{
	function, args := stub.GetFunctionAndParameters()
	if function == "deposit"{

		return bpc.deposit(stub, args)
	}else if function == "transfer"{

		return bpc.transfer(stub, args)
	}else if function == "query"{

		return bpc.query(stub, args)
	}else if function == "hist" {

		return bpc.hist(stub, args)
	}
	errStr := fmt.Sprintf("Function not exist.")
	fmt.Printf(errStr)
	return shim.Error(errStr)
}

func (bpc *BPChaincode) Query(stub shim.ChaincodeStubInterface)pb.Response{
	function, args := stub.GetFunctionAndParameters()
	if function =="inquire"{
		//
		return bpc.inquire(stub, args)
	}
	errStr := fmt.Sprintf("Function not exist.")
	fmt.Printf(errStr)
	return shim.Error(errStr)
}

func (bpc *BPChaincode) deposit(stub shim.ChaincodeStubInterface, args []string)pb.Response{
	blckCustNum := args[0]
	amount := args[1]
	event_date := args[2]
	var iAmount,_ = strconv.Atoi(amount)

	bpAcountJson, _ := stub.GetState(blckCustNum)
	bpAcount := BPAccounts{}

	if bpAcountJson == nil {

		bpAcount.BlckCustNum = blckCustNum
		bpAcount.Balance = iAmount
		bpAcount.amount = amount
		bpAcount.sender = blckCustNum
		bpAcount.receiver = blckCustNum
		bpAcount.event_dtm = event_date

		bpAcountJson,_ = json.Marshal(bpAcount)
		stub.PutState(blckCustNum,bpAcountJson )
	}else{
		json.Unmarshal(bpAcountJson,&bpAcount)
		var asisBalance int = bpAcount.Balance
		bpAcount.Balance = asisBalance + iAmount
		bpAcount.amount = amount
		bpAcount.sender = blckCustNum
		bpAcount.receiver = blckCustNum
		bpAcount.event_dtm = event_date

		bpAcountJson,_ = json.Marshal(bpAcount)
		stub.PutState(blckCustNum,bpAcountJson )
	}
	return  shim.Success([]byte("Success"))
}

func (bpc *BPChaincode) transfer(stub shim.ChaincodeStubInterface, args []string)pb.Response{

	sendBlckCustNum := args[0]
	amount := args[1]
	receiveBlckCustNum := args[2]
	event_date := args[3]
	var iAmount,_ = strconv.Atoi(amount)

	bpAcountJson_receive, _ := stub.GetState(receiveBlckCustNum)
	bpAcount_receive := BPAccounts{}

	if bpAcountJson_receive == nil {
		errStr := fmt.Sprintf("Sender Account Not Exist.")
		fmt.Printf(errStr)
		return shim.Error(errStr)
	}

	bpAcountJson_send, _ := stub.GetState(sendBlckCustNum)
	bpAcount_send := BPAccounts{}

	if bpAcountJson_send == nil {
		errStr := fmt.Sprintf("Sender Account Not Exist.")
		fmt.Printf(errStr)
		return shim.Error(errStr)

	}
	json.Unmarshal(bpAcountJson_send,&bpAcount_send)
	var sender_asisBalance int = bpAcount_send.Balance
	bpAcount_send.Balance = sender_asisBalance - iAmount
	bpAcount_send.BlckCustNum = sendBlckCustNum
	bpAcount_send.sender = sendBlckCustNum
	bpAcount_send.receiver = receiveBlckCustNum
	bpAcount_send.amount = "-"+ amount
	bpAcount_send.event_dtm = event_date

	bpAcountJson_send,_ = json.Marshal(bpAcount_send)
	stub.PutState(sendBlckCustNum,bpAcountJson_send )

	json.Unmarshal(bpAcountJson_receive,&bpAcount_receive)
	var receiver_asisBalance int = bpAcount_receive.Balance
	bpAcount_receive.Balance = receiver_asisBalance + iAmount
	bpAcount_receive.BlckCustNum = receiveBlckCustNum
	bpAcount_receive.sender = sendBlckCustNum
	bpAcount_receive.receiver = receiveBlckCustNum
	bpAcount_receive.amount = amount
	bpAcount_receive.event_dtm = event_date


	bpAcountJson_receive,_ = json.Marshal(bpAcount_receive)
	stub.PutState(receiveBlckCustNum,bpAcountJson_receive )

	return  shim.Success([]byte("Success"))
}

func (bpc *BPChaincode) query(stub shim.ChaincodeStubInterface, args []string)pb.Response{

	blckCustNum := args[0]

	var BPAccountsBytes []byte

	BPAccountsBytes,_ = stub.GetState(blckCustNum)

	BPAccounts := BPAccounts{}

	json.Unmarshal(BPAccountsBytes,&BPAccounts)

	var returnJson,_ = json.Marshal(BPAccounts)

	return shim.Success(returnJson)

}

func (bpc *BPChaincode) inquire(stub shim.ChaincodeStubInterface, args []string)pb.Response{

	blckCustNum := args[0]

	var BPAccountsBytes []byte

	BPAccountsBytes,_ = stub.GetState(blckCustNum)

	BPAccounts := BPAccounts{}

	json.Unmarshal(BPAccountsBytes,&BPAccounts)

	var returnJson,_ = json.Marshal(BPAccounts)

	return shim.Success(returnJson)

}

func (bpc *BPChaincode) hist(stub shim.ChaincodeStubInterface, args []string)pb.Response{

	blckCustNum := args[0]

	var errMsg = ""
	histItor,err := stub.GetHistoryForKey(blckCustNum)

	var accountHist ArrayOfBPAccounts

	if err != nil {
		fmt.Println(errMsg)
		return shim.Error(errMsg)
	}

	for histItor.HasNext(){
		BPAccountsHist,err := histItor.Next()
		if err != nil {
			fmt.Println(errMsg)
			return shim.Error(errMsg)
		}
		BPAccounts := BPAccounts{}

		json.Unmarshal(BPAccountsHist.Value,&BPAccounts)
		accountHist.AccountHist = append(accountHist.AccountHist, BPAccounts)
	}

	var returnJson,_ = json.Marshal(accountHist)

	return shim.Success(returnJson)

}


func main() {
	err := shim.Start(new(BPChaincode))
	if err != nil {
		fmt.Printf("Error starting BPChaincode : %s", err)
	}
}