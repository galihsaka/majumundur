package com.enigmacamp.majumundur.service;

import com.enigmacamp.majumundur.dto.request.ProductRequest;
import com.enigmacamp.majumundur.dto.response.ProductResponse;

import java.util.List;

public interface ProductService {
    public void deleteProduct(String id);
    public ProductResponse saveProductResponse(ProductRequest request);
    public ProductResponse updateProductResponsePatch(ProductRequest request, String id);
    public List<ProductResponse> viewProductResponse();
}
