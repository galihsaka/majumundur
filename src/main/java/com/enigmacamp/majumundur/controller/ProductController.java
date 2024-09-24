package com.enigmacamp.majumundur.controller;

import com.enigmacamp.majumundur.dto.request.ProductRequest;
import com.enigmacamp.majumundur.dto.response.CommonResponse;
import com.enigmacamp.majumundur.dto.response.ProductResponse;
import com.enigmacamp.majumundur.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/majumundur/api/product")
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService){
        this.productService=productService;
    }
    private CommonResponse<ProductResponse> generateProductResponse(Integer code, String message, Optional<ProductResponse> productResponse){
        CommonResponse<ProductResponse> productResponseCommonResponse = new CommonResponse<>();
        productResponseCommonResponse.setStatusCode(code);
        productResponseCommonResponse.setMessage(message);
        productResponseCommonResponse.setData(productResponse);
        return productResponseCommonResponse;
    }

    @GetMapping
    public ResponseEntity<CommonResponse<List<ProductResponse>>> getProduct(){
        List<ProductResponse> productResponses= productService.viewProductResponse();
        CommonResponse<List<ProductResponse>> response =new CommonResponse<>();
        response.setMessage("List For All Product");
        response.setStatusCode(HttpStatus.OK.value());
        response.setData(Optional.of(productResponses));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse<ProductResponse>> postProduct(@RequestBody ProductRequest product){
        ProductResponse productResponse=productService.saveProductResponse(product);
        CommonResponse<ProductResponse> response =generateProductResponse(HttpStatus.OK.value(), "Product Added Succesfully", Optional.of(productResponse));
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductResponse>> removeProduct(@PathVariable String id){
        productService.deleteProduct(id);
        CommonResponse<ProductResponse> response =generateProductResponse(HttpStatus.OK.value(), "Product Deleted Succesfully", Optional.empty());
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CommonResponse<ProductResponse>> updateProductResponsePatch(@PathVariable String id, @RequestBody ProductRequest productRequest){
        ProductResponse productResponse= productService.updateProductResponsePatch(productRequest,id);
        CommonResponse<ProductResponse> response =generateProductResponse(HttpStatus.OK.value(), "Product Updated Succesfully", Optional.of(productResponse));
        return ResponseEntity.ok(response);
    }
}
