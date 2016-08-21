package br.com.avaliacao.checkout.facade;

import org.apache.commons.collections.CollectionUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.avaliacao.checkout.constants.Constants;
import br.com.avaliacao.checkout.db.CartDBInMemory;
import br.com.avaliacao.checkout.exception.CartInvalidException;
import br.com.avaliacao.checkout.mock.model.CartMock;
import br.com.avaliacao.checkout.mock.to.CartTOMock;
import br.com.avaliacao.checkout.model.Cart;
import br.com.avaliacao.checkout.model.Cart.CartStatus;
import br.com.avaliacao.checkout.to.CartTO;


@RunWith(MockitoJUnitRunner.class)
public class ShoppingCartTest {

	@Mock
    private CartDBInMemory cartDB;

	@InjectMocks
	private ShoppingCart shoppingCart = new ShoppingCart();
	
	private CartTO cartTO;
	
	@Before
	public void before() {
		cartTO = new CartTOMock().createMock();
	}
	
	@Test
	public void testAddItem() {
			
		Cart cart = shoppingCart.addItem(cartTO);
		Mockito.verify(cartDB, Mockito.never()).findOne(Mockito.anyString());
		
		Assert.assertNotNull(cart);
		Assert.assertNotNull(cart.getCartId());
		Assert.assertTrue(CollectionUtils.isNotEmpty(cart.getItems()));
	}

	@Test
	public void testAddItemCartInvalid() {
		
		try {
			Cart cart = new CartMock().createMock();
			cart.setStatus(CartStatus.ACCOMPLISHED);
			Mockito.when(cartDB.findOne(Mockito.anyString())).thenReturn(cart);
			
			cartTO.setCartId(cart.getCartId());
			
			shoppingCart.addItem(cartTO);
			
			Assert.fail();
			
		} catch (CartInvalidException e) {
			Assert.assertEquals(Constants.CART_ACCOMPLISHED, e.getMessageError().firstMessage());
		}		
	}
	
	@Test
	public void testAddItemAlreadyExists() {
			
		Cart cart = shoppingCart.addItem(cartTO);
		Mockito.verify(cartDB, Mockito.never()).findOne(Mockito.anyString());
				
		Assert.assertNotNull(cart);
		Assert.assertNotNull(cart.getCartId());
		
		cartTO.setCartId(cart.getCartId());				
		Mockito.when(cartDB.findOne(Mockito.anyString())).thenReturn(cart);
		
		cart = shoppingCart.addItem(cartTO);
		
		Assert.assertTrue(cart.getItems().size() == 1);
		Assert.assertTrue(cart.getItems().get(0).getQuantity() == 2);
	}

}
