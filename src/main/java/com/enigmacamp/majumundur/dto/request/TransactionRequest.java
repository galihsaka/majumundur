package com.enigmacamp.majumundur.dto.request;

import java.util.List;

public class TransactionRequest {
    private String customerId;
    private List<TransactionDetailRequest> transactionDetails;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public List<TransactionDetailRequest> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetailRequest> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }
}
