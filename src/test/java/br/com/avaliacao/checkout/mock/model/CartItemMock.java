package br.com.avaliacao.checkout.mock.model;

import org.junit.Assert;

import br.com.avaliacao.checkout.mock.MockGenerator;
import br.com.avaliacao.checkout.model.CartItem;

public class CartItemMock extends MockGenerator<CartItem> {

	private static final Integer QUANTITY = 1;

	@Override
	public CartItem createMock() {
		
		CartItem cartItem = new CartItem();
		cartItem.setQuantity(QUANTITY);
		cartItem.setProduto(new ProdutoMock().createMock());
		
		return cartItem;
	}

	@Override
	public void assertMock(CartItem cartItem) {
		Assert.assertNotNull(cartItem);
		Assert.assertNotEquals(QUANTITY, cartItem.getQuantity());		
	}

}
