package com.enigmacamp.majumundur.dto.request;

public class TransactionDetailRequest {
    private String productId;
    private Integer qty;

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }
}
