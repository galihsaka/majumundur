package com.enigmacamp.majumundur.service;

import com.enigmacamp.majumundur.dto.request.TransactionRequest;
import com.enigmacamp.majumundur.dto.response.TransactionResponse;

import java.util.List;

public interface TransactionService {
    public TransactionResponse createTransaction(TransactionRequest transactionRequest);
    public List<TransactionResponse> viewTransaction();
}
