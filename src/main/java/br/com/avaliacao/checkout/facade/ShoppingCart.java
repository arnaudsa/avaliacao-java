package br.com.avaliacao.checkout.facade;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import br.com.avaliacao.checkout.constants.Constants;
import br.com.avaliacao.checkout.db.CartDBInMemory;
import br.com.avaliacao.checkout.exception.CartInvalidException;
import br.com.avaliacao.checkout.model.Cart;
import br.com.avaliacao.checkout.model.CartItem;
import br.com.avaliacao.checkout.model.Produto;
import br.com.avaliacao.checkout.to.CartTO;
import br.com.avaliacao.checkout.to.MessageErrorTO;

@Component
@Scope(value=WebApplicationContext.SCOPE_SESSION)
public class ShoppingCart {
	
	private Cart cart;
			
	@Autowired
    private CartDBInMemory cartDB;
    
	
	public Cart findOne(String cartId) {
		return cartDB.findOne(cartId);
	}
	
    public Cart addItem(CartTO to) throws CartInvalidException {
		
    	findCart(to);    	    	
		validateStatus();
				
		CartItem item = createItem(to);    	
    	if (cart.itemAlreadyExists(item)) {    		
    		CartItem cartItem = cart.getItems().stream().filter(c -> c.equals(item)).findFirst().orElse(null);
    		cartItem.incrementQuantity(item.getQuantity());    		
		
    	}else{
    		cart.getItems().add(item);    	    		
    	}

    	cartDB.save(cart);
    	
    	return cart;
	}
        
    private void findCart(CartTO to) {
    	
    	String cartId = to.getCartId();
    	if (StringUtils.isNotBlank(cartId)) {
    		cart = cartDB.findOne(cartId);    		
    	}
    	
    	if (cart == null) {
			cart = new Cart();
			cart.setCartId(UUID.randomUUID().toString());			
		}
	}
    
    private void validateStatus() throws CartInvalidException {    	
    	if (cart.isAccomplished()) {			
			throw new CartInvalidException(new MessageErrorTO(Constants.CART_ACCOMPLISHED));
		}    	
	}

	private CartItem createItem(CartTO vo) {

    	Produto p = new Produto();
        p.setCodigo(vo.getCodeProduct());
        p.setNome(vo.getNameProduct());
        p.setMarca(vo.getBrand());
        p.setPreco(vo.getPrice());

        CartItem item = new CartItem();
        item.setProduto(p);
        item.setQuantity(vo.getQ());
        
        return item;
	}
	
}
