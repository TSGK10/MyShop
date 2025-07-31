package com.HelloWorld.mapper;

import com.HelloWorld.model.Cart;
import org.apache.ibatis.annotations.*;

@Mapper
public interface CartMapper {
    @Insert("INSERT INTO cart (user_id) VALUES (#{userId})")
    void createCart(@Param("userId") int userId);

    @Select("SELECT * FROM cart WHERE user_id = #{userId}")
    @Results({
            @Result(property = "cartId", column = "cart_id"),
            @Result(property = "userId", column = "user_id")
    })
    Cart findByUserId(@Param("userId") int userId);
}

