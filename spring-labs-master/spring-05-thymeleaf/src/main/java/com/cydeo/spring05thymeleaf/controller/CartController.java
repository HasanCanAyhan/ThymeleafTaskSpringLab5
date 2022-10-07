package com.cydeo.spring05thymeleaf.controller;

import com.cydeo.spring05thymeleaf.model.Cart;
import com.cydeo.spring05thymeleaf.service.CartService;
import com.cydeo.spring05thymeleaf.service.impl.CartServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/cart")
    public String seeCartPage(Model model){

        model.addAttribute("cartItemList", CartServiceImpl.CART.getCartItemList());
        model.addAttribute("cartTotalAmount", CartServiceImpl.CART.getCartTotalAmount());

        return "/cart/show-cart";
    }

    @GetMapping("/addToCart/{productId}/{quantity}")
    public String addToCart(@PathVariable("productId") UUID productId, @PathVariable("quantity") int quantity, Model model){

        cartService.addToCart(productId,quantity);


        return "redirect:/cart";
    }


    @GetMapping("/delete/{productId}")
    public String deleteProductFromCart(@PathVariable("productId") UUID productId){

        cartService.deleteFromCart(productId);

        return "redirect:/cart";
    }


}
