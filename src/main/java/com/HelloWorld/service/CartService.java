package com.HelloWorld.service;

import com.HelloWorld.mapper.CartMapper;
import com.HelloWorld.mapper.CartProductMapper;
import com.HelloWorld.model.Cart;
import com.HelloWorld.model.CartProduct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CartProductMapper cartProductMapper;

    public Cart getOrCreateCart(int userId) {
        Cart cart = cartMapper.findByUserId(userId);
        if (cart == null) {
            cartMapper.createCart(userId);
            cart = cartMapper.findByUserId(userId);
        }
        return cart;
    }

    public void addProductToCart(int userId, int productId, int quantity) {
        Cart cart = getOrCreateCart(userId); // ← これが重要！
        CartProduct cartProduct = new CartProduct();
        cartProduct.setCartId(cart.getCartId());
        cartProduct.setProductId(productId);
        cartProduct.setQuantity(quantity);
        cartProductMapper.addProductToCart(cartProduct);
    }

    public List<CartProduct> getCartProducts(int userId) {
        Cart cart = cartMapper.findByUserId(userId);
        return cart != null ? cartProductMapper.findWithProductByCartId(cart.getCartId()) : new ArrayList<>();
    }


    public void removeProduct(int cartProductId) {
        cartProductMapper.removeProduct(cartProductId);
    }

    public void updateQuantity(int cartProductId, int quantity) {
        cartProductMapper.updateQuantity(cartProductId, quantity);
    }
}
