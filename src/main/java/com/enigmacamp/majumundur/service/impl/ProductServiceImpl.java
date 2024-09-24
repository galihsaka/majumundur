package com.enigmacamp.majumundur.service.impl;

import com.enigmacamp.majumundur.dto.request.ProductRequest;
import com.enigmacamp.majumundur.dto.response.ProductResponse;
import com.enigmacamp.majumundur.entity.Product;
import com.enigmacamp.majumundur.exception.ResourceNotFoundException;
import com.enigmacamp.majumundur.repository.ProductRepository;
import com.enigmacamp.majumundur.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    private ProductRepository productRepository;
    @Autowired
    public ProductServiceImpl(ProductRepository productRepository){
        this.productRepository=productRepository;
    }

    private Product findProductByIdOrThrowNotFound(String id) {
        return productRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product Not Found"));
    }

    private ProductResponse convertToProductResponse(Product product){
        ProductResponse productResponse=new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setId(product.getId());
        productResponse.setPrice(product.getPrice());
        productResponse.setStock(product.getStock());
        return productResponse;
    }

    @Override
    public void deleteProduct(String id) {
        Product product=findProductByIdOrThrowNotFound(id);
        productRepository.deleteById(id);
    }

    @Override
    public ProductResponse saveProductResponse(ProductRequest request) {
        Product product=new Product();
        product.setPrice(request.getPrice());
        product.setName(request.getName());
        product.setStock(request.getStock());
        product=productRepository.save(product);
        return convertToProductResponse(product);
    }

    @Override
    public ProductResponse updateProductResponsePatch(ProductRequest request, String id) {
        Product productFound = findProductByIdOrThrowNotFound(id);
        if(request.getName()!=null){
            productFound.setName(request.getName());
        }
        if(request.getPrice()!=null){
            productFound.setPrice(request.getPrice());
        }
        if(request.getStock()!=null){
            productFound.setStock(request.getStock());
        }
        productFound = productRepository.save(productFound);
        return convertToProductResponse(productFound);

    }

    @Override
    public List<ProductResponse> viewProductResponse() {
        List<Product> productList= productRepository.findAll();
        List<ProductResponse> productResponses = new ArrayList<>();
        for(int i=0; i<productList.size();i++){
            productResponses.add(convertToProductResponse(productList.get(i)));
        }
        return productResponses;
    }
}
