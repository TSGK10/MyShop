package com.HelloWorld.service;

import com.HelloWorld.mapper.OrderCancellationMapper;
import com.HelloWorld.model.OrderCancellation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderCancellationService {

    @Autowired
    private OrderCancellationMapper mapper;

    @Transactional
    public void cancelOrder(Long orderId, Integer userId, String reason) {
        OrderCancellation cancellation = new OrderCancellation();
        cancellation.setOrderId(orderId);
        cancellation.setUserId(userId);
        cancellation.setReason(reason);
        cancellation.setCancelledAt(LocalDateTime.now());
        mapper.insert(cancellation);
    }

    public List<OrderCancellation> getCancellationsByUserId(Integer userId) {
        return mapper.findByUserId(userId);
    }
}