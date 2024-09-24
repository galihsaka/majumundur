package com.enigmacamp.majumundur.controller;

import com.enigmacamp.majumundur.dto.request.TransactionRequest;
import com.enigmacamp.majumundur.dto.response.CommonResponse;
import com.enigmacamp.majumundur.dto.response.TransactionResponse;
import com.enigmacamp.majumundur.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/majumundur/api/transaction")
public class TransactionController {
    private TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    private CommonResponse<TransactionResponse> generateTransactionResponse(Integer code, String message, Optional<TransactionResponse> transactionResponse){
        CommonResponse<TransactionResponse> transactionResponseCommonResponse=new CommonResponse<>();
        transactionResponseCommonResponse.setStatusCode(code);
        transactionResponseCommonResponse.setMessage(message);
        transactionResponseCommonResponse.setData(transactionResponse);
        return transactionResponseCommonResponse;
    }

    @PostMapping
    public ResponseEntity<CommonResponse<TransactionResponse>> postTransaction(@RequestBody TransactionRequest transactionRequest){
        TransactionResponse transactionResponse= transactionService.createTransaction(transactionRequest);
        CommonResponse<TransactionResponse> transactionResponseCommonResponse = generateTransactionResponse(HttpStatus.OK.value(), "Transaction Added Succesfully", Optional.of(transactionResponse));
        return ResponseEntity.ok(transactionResponseCommonResponse);
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<TransactionResponse>>> viewTransaction(){
        List<TransactionResponse> transactionResponseList= transactionService.viewTransaction();
        CommonResponse<List<TransactionResponse>> listCommonResponse=new CommonResponse<>();
        listCommonResponse.setStatusCode(HttpStatus.OK.value());
        listCommonResponse.setMessage("List All Transaction");
        listCommonResponse.setData(Optional.of(transactionResponseList));
        return ResponseEntity.ok(listCommonResponse);
    }

}
