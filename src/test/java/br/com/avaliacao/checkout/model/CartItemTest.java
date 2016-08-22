package br.com.avaliacao.checkout.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.avaliacao.checkout.mock.model.ProdutoMock;

public class CartItemTest {

	private static final Integer QUANTITY = 1;
	private static final Produto PRODUTO = new ProdutoMock().createMock();
		
	private CartItem cartItem;

	@Before
	public void before() {
		cartItem = new CartItem();
		cartItem.setQuantity(QUANTITY);
		cartItem.setProduto(PRODUTO);		
	}

	@Test
	public void assertMock() {
		Assert.assertEquals(PRODUTO, cartItem.getProduto());		
		Assert.assertEquals(QUANTITY, cartItem.getQuantity());		
	}
	
	@Test
	public void testIncrementQuantity() {
		cartItem.incrementQuantity(3);
		Assert.assertEquals(Integer.valueOf("4"), cartItem.getQuantity());		

	}

}
