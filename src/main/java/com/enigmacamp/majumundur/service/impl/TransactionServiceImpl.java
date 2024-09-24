package com.enigmacamp.majumundur.service.impl;

import com.enigmacamp.majumundur.dto.request.TransactionDetailRequest;
import com.enigmacamp.majumundur.dto.request.TransactionRequest;
import com.enigmacamp.majumundur.dto.response.ProductResponse;
import com.enigmacamp.majumundur.dto.response.TransactionDetailResponse;
import com.enigmacamp.majumundur.dto.response.TransactionResponse;
import com.enigmacamp.majumundur.dto.response.UserResponse;
import com.enigmacamp.majumundur.entity.Product;
import com.enigmacamp.majumundur.entity.Transaction;
import com.enigmacamp.majumundur.entity.TransactionDetail;
import com.enigmacamp.majumundur.entity.User;
import com.enigmacamp.majumundur.exception.ResourceNotFoundException;
import com.enigmacamp.majumundur.exception.ValidationException;
import com.enigmacamp.majumundur.repository.ProductRepository;
import com.enigmacamp.majumundur.repository.TransactionDetailRepository;
import com.enigmacamp.majumundur.repository.TransactionRepository;
import com.enigmacamp.majumundur.repository.UserRepository;
import com.enigmacamp.majumundur.service.TransactionService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionServiceImpl implements TransactionService {
    private TransactionRepository transactionRepository;
    private TransactionDetailRepository transactionDetailRepository;
    private UserRepository userRepository;
    private ProductRepository productRepository;

    @Autowired
    public TransactionServiceImpl(TransactionRepository transactionRepository, ProductRepository productRepository, UserRepository userRepository, TransactionDetailRepository transactionDetailRepository) {
        this.transactionRepository = transactionRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.transactionDetailRepository = transactionDetailRepository;
    }

    private List<TransactionDetailResponse> convertToTransactionDetailResponse(List<TransactionDetail> transactionDetail){
        List<TransactionDetailResponse> transactionDetailResponseList=new ArrayList<>();
        for(int i=0;i<transactionDetail.size();i++){
            TransactionDetailResponse transactionDetailResponse=new TransactionDetailResponse();
            transactionDetailResponse.setId(transactionDetail.get(i).getId());
            transactionDetailResponse.setProductResponse(convertToProductResponse(transactionDetail.get(i).getProduct()));
            transactionDetailResponse.setQty(transactionDetail.get(i).getQty());
            transactionDetailResponse.setPrice(transactionDetail.get(i).getPrice());
            transactionDetailResponseList.add(transactionDetailResponse);
        }
        return transactionDetailResponseList;
    }

    private ProductResponse convertToProductResponse(Product product){
        ProductResponse productResponse=new ProductResponse();
        productResponse.setName(product.getName());
        productResponse.setId(product.getId());
        productResponse.setPrice(product.getPrice());
        productResponse.setStock(product.getStock());
        return productResponse;
    }

    private UserResponse convertToUserResponse(User user){
        UserResponse userResponse=new UserResponse();
        userResponse.setUsername(user.getUsername());
        userResponse.setGender(user.getGender());
        userResponse.setFullName(user.getFullName());
        return userResponse;
    }

    @Transactional(rollbackOn = Exception.class)
    public TransactionResponse createTransaction(TransactionRequest transactionRequest){
        Integer totalPay=0;
        Transaction transaction=new Transaction();
        Optional<User> userFound=userRepository.findById(transactionRequest.getCustomerId());
        User custFound=userFound.orElseThrow(()->new ResourceNotFoundException("Customer Not Found"));
        transaction.setUser(custFound);
        List<TransactionDetail> detailList=new ArrayList<>();
        List<TransactionDetailRequest> requestDetailList=transactionRequest.getTransactionDetails();
        for(int i=0; i<requestDetailList.size();i++){
            TransactionDetail transactionDetail=new TransactionDetail();
            Optional<Product> productFound=productRepository.findById(requestDetailList.get(i).getProductId());
            Product prodFound=productFound.orElseThrow(()->new ResourceNotFoundException("Product Not Found"));
            if(prodFound.getStock()-requestDetailList.get(i).getQty()<0){
                throw new ValidationException("The product is currently out of stock");
            }
            prodFound.setStock(prodFound.getStock()-requestDetailList.get(i).getQty());
            transactionDetail.setTransaction(transaction);
            transactionDetail.setProduct(prodFound);
            transactionDetail.setPrice(prodFound.getPrice());
            transactionDetail.setQty(requestDetailList.get(i).getQty());
            transactionDetail= transactionDetailRepository.save(transactionDetail);
            detailList.add(transactionDetail);
            totalPay=totalPay+(prodFound.getPrice()*requestDetailList.get(i).getQty());
        }
        transaction.setTransactionDetails(detailList);
        transaction=transactionRepository.save(transaction);
        TransactionResponse transactionResponse=new TransactionResponse();
        transactionResponse.setId(transaction.getId());
        transactionResponse.setUserResponse(convertToUserResponse(transaction.getUser()));
        transactionResponse.setDate(transaction.getDate());
        transactionResponse.setTransactionDetailsResponse(convertToTransactionDetailResponse(transaction.getTransactionDetails()));
        transactionResponse.setTotalPayment(totalPay);
        return transactionResponse;
    }

    public List<TransactionResponse> viewTransaction() {
        List<Transaction> transactionList = transactionRepository.findAll();
        List<TransactionResponse> transactionResponseList=new ArrayList<>();
        for(int i=0;i<transactionList.size();i++){
            Integer totalPay=0;
            List<TransactionDetail> transactionDetailList=transactionList.get(i).getTransactionDetails();
            TransactionResponse transactionResponse=new TransactionResponse();
            for(int j=0;j<transactionDetailList.size();j++){
                Optional<Product> productFound=productRepository.findById(transactionDetailList.get(j).getProduct().getId());
                Product prodFound=productFound.orElse(null);
                totalPay=totalPay+(prodFound.getPrice()*transactionDetailList.get(j).getQty());
            }
            transactionResponse.setId(transactionList.get(i).getId());
            transactionResponse.setDate(transactionList.get(i).getDate());
            transactionResponse.setUserResponse(convertToUserResponse(transactionList.get(i).getUser()));
            transactionResponse.setTransactionDetailsResponse(convertToTransactionDetailResponse(transactionList.get(i).getTransactionDetails()));
            transactionResponse.setTotalPayment(totalPay);
            transactionResponseList.add(transactionResponse);
        }
        return transactionResponseList;
    }
}
