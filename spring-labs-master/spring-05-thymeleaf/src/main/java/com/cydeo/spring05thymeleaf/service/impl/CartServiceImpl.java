package com.cydeo.spring05thymeleaf.service.impl;

import com.cydeo.spring05thymeleaf.model.Cart;
import com.cydeo.spring05thymeleaf.model.CartItem;
import com.cydeo.spring05thymeleaf.model.Product;
import com.cydeo.spring05thymeleaf.service.CartService;
import com.cydeo.spring05thymeleaf.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class CartServiceImpl implements CartService {
    public static Cart CART = new Cart(BigDecimal.ZERO,new ArrayList<>());

    private final ProductService productService;

    public CartServiceImpl(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public Cart addToCart(UUID productId, Integer quantity){
        //todo retrieve product from repository method

        Product product_retrieving = productService.findProductById(productId);

        //todo initialise cart item

        CartItem cartItem = new CartItem();

        cartItem.setProduct(product_retrieving);
        cartItem.setQuantity(quantity);
        cartItem.setTotalAmount( product_retrieving.getPrice().multiply(BigDecimal.valueOf(quantity)) );
        //todo calculate cart total amount

        CART.getCartItemList().add(cartItem);
        CART.setCartTotalAmount(CART.getCartTotalAmount().add(cartItem.getTotalAmount()));

        //todo add to cart
        return CART;
    }

    @Override
    public boolean deleteFromCart(UUID productId){
        //todo delete product object from cart using stream

        Product deletingProduct = productService.findProductById(productId);

        CartItem removing_cartItem = CART.getCartItemList().stream().filter(cartItem -> cartItem.getProduct().equals(deletingProduct))
                .findFirst().orElseThrow();

        CART.getCartItemList().remove(removing_cartItem);
        CART.setCartTotalAmount(CART.getCartTotalAmount().subtract(removing_cartItem.getTotalAmount()));


        return true;
    }
}
