package br.com.avaliacao.checkout.mock.model;

import java.util.UUID;

import org.junit.Assert;

import br.com.avaliacao.checkout.mock.MockGenerator;
import br.com.avaliacao.checkout.model.Cart;

public class CartMock extends MockGenerator<Cart> {

	private static final String CART_ID = UUID.randomUUID().toString();

	@Override
	public Cart createMock() {
		Cart cart = new Cart();
		cart.setCartId(CART_ID);
		return cart;
	}

	@Override
	public void assertMock(Cart cart) {
		Assert.assertNotNull(cart);
		Assert.assertEquals(CART_ID, cart.getCartId());
	}

}
