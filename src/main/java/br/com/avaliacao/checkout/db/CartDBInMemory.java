package br.com.avaliacao.checkout.db;

import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import br.com.avaliacao.checkout.model.Cart;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION, proxyMode=ScopedProxyMode.DEFAULT)
public class CartDBInMemory {

    private static Map<String, Cart> carts = new HashMap<>();

    public Cart save(Cart cart) {
        carts.put(cart.getCartId(), cart);
        return cart;
    }

    public Cart findOne(String id) {
        return carts.get(id);
    }

    public void clear() {
        carts.clear();
    }

}
