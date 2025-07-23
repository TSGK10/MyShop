package com.HelloWorld.mapper;

import com.HelloWorld.model.Product;
import jdk.jfr.Enabled;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProductMapper {

    @Select("SELECT * FROM product")
    @Results({
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productCategory", column = "product_category"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "listPrice", column = "list_price"),
            @Result(property = "salePrice", column = "sale_price"),
            @Result(property = "saleStartDate", column = "sale_start_date"),
            @Result(property = "saleEndDate", column = "sale_end_date"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "quantity", column = "quantity")
    })
    List<Product> findAll();

    @Insert("""
        INSERT INTO product (
            product_category, product_name, list_price, sale_price,
            sale_start_date, sale_end_date, created_at, quantity
        ) VALUES (
            #{productCategory}, #{productName}, #{listPrice}, #{salePrice},
            #{saleStartDate}, #{saleEndDate}, NOW(), #{quantity}
        )
    """)
    void insertProduct(Product product);

    @Delete("DELETE FROM product WHERE product_id = #{id}")
    void deleteById(int id);

    @Select("SELECT * FROM product WHERE product_id = #{id}")
    @Results({
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productCategory", column = "product_category"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "listPrice", column = "list_price"),
            @Result(property = "salePrice", column = "sale_price"),
            @Result(property = "saleStartDate", column = "sale_start_date"),
            @Result(property = "saleEndDate", column = "sale_end_date"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "quantity", column = "quantity")
    })
    Product findById(int id);

    @Update("""
    UPDATE product SET
        product_category = #{productCategory},
        product_name = #{productName},
        list_price = #{listPrice},
        sale_price = #{salePrice},
        sale_start_date = #{saleStartDate},
        sale_end_date = #{saleEndDate},
        quantity = #{quantity}
    WHERE product_id = #{productId}
""")
    void updateProduct(Product product);

    @Select("""
    SELECT * FROM product
    WHERE product_name LIKE CONCAT('%', #{keyword}, '%')
       OR product_category LIKE CONCAT('%', #{keyword}, '%')
""")
    @Results({
            @Result(property = "productId", column = "product_id"),
            @Result(property = "productCategory", column = "product_category"),
            @Result(property = "productName", column = "product_name"),
            @Result(property = "listPrice", column = "list_price"),
            @Result(property = "salePrice", column = "sale_price"),
            @Result(property = "saleStartDate", column = "sale_start_date"),
            @Result(property = "saleEndDate", column = "sale_end_date"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "quantity", column = "quantity")
    })
    List<Product> searchByKeyword(String keyword);

}
