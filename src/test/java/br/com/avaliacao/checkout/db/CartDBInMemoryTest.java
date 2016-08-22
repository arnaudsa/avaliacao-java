package br.com.avaliacao.checkout.db;

import static br.com.avaliacao.checkout.constants.Constants.CART_INVALID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.avaliacao.checkout.mock.model.CartMock;
import br.com.avaliacao.checkout.model.Cart;

public class CartDBInMemoryTest {

	private CartDBInMemory cartDBInMemory;
	private Cart cart;

	@Before
	public void before() {
		cartDBInMemory = new CartDBInMemory();
		cart = new CartMock().createMock();
	}

	@Test
	public void testSave() {
		
		try {
			cartDBInMemory.save(cart);
			cart = cartDBInMemory.findOne(cart.getCartId());
			Assert.assertNotNull(cart);
			
			cartDBInMemory.save(null);
			Assert.fail();
			
		} catch (Exception e) {
			Assert.assertEquals(CART_INVALID, e.getMessage());
		}
	}

	@Test
	public void testFindOne() {
		
		Cart findOne = cartDBInMemory.findOne(null);
		Assert.assertNull(findOne);
		
		cartDBInMemory.save(cart);
		cart = cartDBInMemory.findOne(cart.getCartId());
		Assert.assertNotNull(cart);		
	}

	@Test
	public void testClear() {
		cartDBInMemory.save(cart);
		cart = cartDBInMemory.findOne(cart.getCartId());
		Assert.assertNotNull(cart);
		
		cartDBInMemory.clear();
		cart = cartDBInMemory.findOne(cart.getCartId());
		Assert.assertNull(cart);
	}

}
