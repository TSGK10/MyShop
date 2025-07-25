package com.HelloWorld.mapper;

import com.HelloWorld.model.OrderCancellation;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface OrderCancellationMapper {

    @Insert("""
        INSERT INTO order_cancellations (order_id, user_id, reason, cancelled_at)
        VALUES (#{orderId}, #{userId}, #{reason}, #{cancelledAt})
    """)
    @Results({
            @Result(property = "orderId", column = "order_id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "reason", column = "reason"),
            @Result(property = "cancelledAt", column = "cancelled_at")
    })
    void insert(OrderCancellation cancellation);

    @Select("SELECT * FROM order_cancellations WHERE user_id = #{userId}")
    @Results({
        @Result(property = "id", column = "id"),
        @Result(property = "orderId", column = "order_id"),
        @Result(property = "userId", column = "user_id"),
        @Result(property = "reason", column = "reason"),
        @Result(property = "cancelledAt", column = "cancelled_at")
    })
    List<OrderCancellation> findByUserId(@Param("userId") Integer userId);
}