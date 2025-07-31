package com.HelloWorld.mapper;

import com.HelloWorld.model.CartProduct;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CartProductMapper {

    @Insert("INSERT INTO cart_product (cart_id, product_id, quantity) VALUES (#{cartId}, #{productId}, #{quantity})")
    @Results({
            @Result(property = "cartProductId", column = "cart_product_id"),
            @Result(property = "cartId", column = "cart_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "product", column = "product_id",
                    one = @One(select = "com.HelloWorld.mapper.ProductMapper.findById"))

    })
    void addProductToCart(CartProduct cartProduct);

    @Select("SELECT * FROM cart_product WHERE cart_id = #{cartId}")
    @Results({
            @Result(property = "cartProductId", column = "cart_product_id"),
            @Result(property = "cartId", column = "cart_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "product", column = "product_id",
                    one = @One(select = "com.HelloWorld.mapper.ProductMapper.findById"))

    })
    List<CartProduct> findByCartId(@Param("cartId") int cartId);

    @Delete("DELETE FROM cart_product WHERE cart_product_id = #{cartProductId}")
    @Results({
            @Result(property = "cartProductId", column = "cart_product_id"),
            @Result(property = "cartId", column = "cart_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "product", column = "product_id",
                    one = @One(select = "com.HelloWorld.mapper.ProductMapper.findById"))

    })
    void removeProduct(@Param("cartProductId") int cartProductId);

    @Update("UPDATE cart_product SET quantity = #{quantity} WHERE cart_product_id = #{cartProductId}")
    @Results({
            @Result(property = "cartProductId", column = "cart_product_id"),
            @Result(property = "cartId", column = "cart_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "product", column = "product_id",
                    one = @One(select = "com.HelloWorld.mapper.ProductMapper.findById"))

    })
    void updateQuantity(@Param("cartProductId") int cartProductId, @Param("quantity") int quantity);

    @Select("""
    SELECT cp.cart_product_id, cp.cart_id, cp.product_id, cp.quantity,
           p.product_id AS p_id, p.product_name, p.sale_price, p.quantity AS p_stock
    FROM cart_product cp
    JOIN product p ON cp.product_id = p.product_id
    WHERE cp.cart_id = #{cartId}
""")
    @Results({
            @Result(property = "cartProductId", column = "cart_product_id"),
            @Result(property = "cartId", column = "cart_id"),
            @Result(property = "productId", column = "product_id"),
            @Result(property = "quantity", column = "quantity"),
            @Result(property = "product.productId", column = "p_id"),
            @Result(property = "product.productName", column = "product_name"),
            @Result(property = "product.salePrice", column = "sale_price"),
            @Result(property = "product.quantity", column = "p_stock"),
            @Result(property = "product", column = "product_id",
                    one = @One(select = "com.HelloWorld.mapper.ProductMapper.findById"))

    })
    List<CartProduct> findWithProductByCartId(@Param("cartId") int cartId);

}
