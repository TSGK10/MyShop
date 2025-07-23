package com.HelloWorld.service;

import com.HelloWorld.mapper.ProductMapper;
import com.HelloWorld.mapper.PurchaseHistoryMapper;
import com.HelloWorld.model.Product;
import com.HelloWorld.model.PurchaseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductMapper productMapper;

    public List<Product> getAllProducts() {
        List<Product> list = productMapper.findAll();
        return list != null ? list : new ArrayList<>();
    }

    @Transactional
    public void addProduct(Product product) {
        productMapper.insertProduct(product);
    }


    @Transactional
    public void deleteProductById(int id) {
        productMapper.deleteById(id);
    }


    public Product getProductById(int id) {
        return productMapper.findById(id);
    }

    @Transactional
    public void updateProduct(Product product) {
        productMapper.updateProduct(product);
    }

    public List<Product> searchProducts(String keyword) {
        return productMapper.searchByKeyword(keyword);
    }

/*
    @Autowired
    private PurchaseHistoryMapper purchaseHistoryMapper;

    public void savePurchaseHistory(Product product) {
        PurchaseHistory history = new PurchaseHistory();
        history.setProductId(product.getProductId());
        history.setProductName(product.getProductName());
        purchaseHistoryMapper.insertPurchase(history);
    }
*/

}
