package com.HelloWorld.mapper;

import com.HelloWorld.model.PurchaseHistory;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PurchaseHistoryMapper {

    @Insert("""
    INSERT INTO purchase_history (user_id, quantity, product_id, purchase_date)
    VALUES (#{userId}, #{quantity}, #{productId},#{purchaseDate})
""")

    void insert(PurchaseHistory history);

    @Select("SELECT * FROM purchase_history WHERE user_id = #{userId}")
    @Results({
            @Result(property = "userId", column = "user_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "purchaseDate", column = "purchase_date")
    })
    List<PurchaseHistory> findByUserId(Integer userId);

    @Select("SELECT * FROM purchase_history WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "purchaseDate", column = "purchase_date")
    })
    PurchaseHistory findById(Long id);

    @Select("""
SELECT * FROM purchase_history
WHERE user_id = #{userId}
ORDER BY purchase_date ASC
LIMIT #{limit} OFFSET #{offset}
""")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "purchaseDate", column = "purchase_date")
    })
    List<PurchaseHistory> findByUserIdPagedAsc(@Param("userId") Integer userId, @Param("limit") int limit, @Param("offset") int offset);

    @Select("""
SELECT * FROM purchase_history
WHERE user_id = #{userId}
ORDER BY purchase_date DESC
LIMIT #{limit} OFFSET #{offset}
""")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "purchaseDate", column = "purchase_date")
    })
    List<PurchaseHistory> findByUserIdPagedDesc(@Param("userId") Integer userId, @Param("limit") int limit, @Param("offset") int offset);

    @Select("""
SELECT * FROM purchase_history
WHERE user_id = #{userId}
ORDER BY purchase_date DESC
LIMIT #{limit} OFFSET #{offset}
""")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "userId", column = "user_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "purchaseDate", column = "purchase_date")
    })
    List<PurchaseHistory> findByUserIdPaged(@Param("userId") Integer userId,
                                            @Param("limit") int limit,
                                            @Param("offset") int offset);

    @Select("SELECT COUNT(*) AS total_count FROM purchase_history WHERE user_id = #{userId}")
    @Results({
            @Result(property = "totalCount", column = "total_count")
    })
    int countByUserId(@Param("userId") Integer userId);

}

