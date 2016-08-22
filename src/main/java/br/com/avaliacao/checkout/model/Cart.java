package br.com.avaliacao.checkout.model;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    public enum CartStatus {OPENED, ACCOMPLISHED, ABANDONED}

    private String cartId;

    private List<CartItem> items = new ArrayList<>();

    private CartStatus status = CartStatus.OPENED;

    public String getCartId() {
        return this.cartId;
    }

    public CartStatus getStatus() {
        return this.status;
    }

    public void setCartId(final String cartId) {
        this.cartId = cartId;
    }

    public void setItems(final List<CartItem> items) {
        this.items = items;
    }

    public void setStatus(final CartStatus status) {
        this.status = status;
    }

    public List<CartItem> getItems() {        
        return items;
    }

    public boolean isOpened() {
        return CartStatus.OPENED.equals(this.status);
    }

    public boolean isAccomplished() {
        return CartStatus.ACCOMPLISHED.equals(this.status);
    }

    public void accomplished() {
        this.status = CartStatus.ACCOMPLISHED;
    }

    public boolean itemAlreadyExists(CartItem item) {
    	return items.contains(item);
	}
    
    public Double getPrice() {
        Double total = .0;
        for (int i=0; i < items.size(); i++) {
            total += items.get(i).getPrice();
        }
        return total;
    }

}
