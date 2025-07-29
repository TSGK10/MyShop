package com.HelloWorld.service;

import com.HelloWorld.mapper.PurchaseHistoryMapper;
import com.HelloWorld.model.PurchaseHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PurchaseHistoryService {

    @Autowired
    private PurchaseHistoryMapper mapper;

    @Transactional
    public void savePurchase(Integer userId, Integer productId, Integer quantity) {
        PurchaseHistory history = new PurchaseHistory();
        history.setUserId(userId); // ← これが重要！
        history.setProductId(productId);
        history.setQuantity(quantity);
        history.setPurchaseDate(LocalDateTime.now());
        mapper.insert(history);
    }

    public List<PurchaseHistory> getHistoryByUserId(Integer userId) {
        return mapper.findByUserId(userId);
    }

    public PurchaseHistory getById(Long id) {
        return mapper.findById(id);
    }

    public List<PurchaseHistory> getHistoryByUserIdPagedSorted(Integer userId, int page, int pageSize, String sort) {
        int offset = (page - 1) * pageSize;
        if ("asc".equalsIgnoreCase(sort)) {
            return mapper.findByUserIdPagedAsc(userId, pageSize, offset);
        } else {
            return mapper.findByUserIdPagedDesc(userId, pageSize, offset);
        }
    }

    public List<PurchaseHistory> getHistoryByUserIdPaged(Integer userId, int page, int pageSize) {
        int offset = (page - 1) * pageSize;
        return mapper.findByUserIdPaged(userId, pageSize, offset);
    }

    public int countByUserId(Integer userId) {
        return mapper.countByUserId(userId);
    }


}
