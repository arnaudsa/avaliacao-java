package br.com.avaliacao.checkout.model;

import static br.com.avaliacao.checkout.model.Cart.CartStatus.ACCOMPLISHED;
import static br.com.avaliacao.checkout.model.Cart.CartStatus.OPENED;

import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import br.com.avaliacao.checkout.mock.model.CartItemMock;
import br.com.avaliacao.checkout.mock.model.ProdutoMock;
import br.com.avaliacao.checkout.model.Cart.CartStatus;

public class CartTest {

	private static final String CART_ID = UUID.randomUUID().toString();
	private static final CartStatus ABANDONED = CartStatus.ABANDONED;
	private final List<CartItem> ITEMS = new CartItemMock().createMockList();
	private Cart cart;
	
	@Before
	public void before() {
		cart = new Cart();
		cart.setCartId(CART_ID);
		cart.setStatus(ABANDONED);
		cart.setItems(ITEMS);
	}

	@Test
	public void testGet() {
		Assert.assertEquals(CART_ID, cart.getCartId());
		Assert.assertEquals(ABANDONED, cart.getStatus());
		Assert.assertEquals(ITEMS, cart.getItems());
	}

	
	@Test
	public void testIsOpened() {
		
		Assert.assertFalse(cart.isOpened());
		
		cart.setStatus(OPENED);
		Assert.assertTrue(cart.isOpened());
	}

	@Test
	public void testIsAccomplished() {
		
		Assert.assertFalse(cart.isAccomplished());
		
		cart.accomplished();
		Assert.assertTrue(cart.isAccomplished());
	}

	@Test
	public void testAccomplished() {
		cart.accomplished();
		Assert.assertEquals(cart.getStatus(), ACCOMPLISHED);
	}
	
	@Test
	public void testItemAlreadyExists() {
		
		CartItem item1 = new CartItemMock().createMock();
		Assert.assertTrue(cart.itemAlreadyExists(item1));
		
		Produto produto = new ProdutoMock().createMock();
		produto.setMarca("SONY");
		CartItem item2 = new CartItem();
		item2.setProduto(produto);
		item2.setQuantity(1);
		Assert.assertFalse(cart.itemAlreadyExists(item2));

		
		cart.getItems().clear();
		Assert.assertFalse(cart.itemAlreadyExists(item1));
	}

	@Test
	public void testgetPrice() {
		
		cart.getItems().clear();
		Assert.assertEquals(new Double(0.00), cart.getPrice());
		
		Produto produto = new ProdutoMock().createMock();
		produto.setMarca("SONY");
		produto.setPreco(2950.00);
		
		CartItem item1 = new CartItem();
		item1.setProduto(produto);
		item1.setQuantity(2);
		
		cart.getItems().add(item1);
		
		Assert.assertEquals(new Double(5900.00), cart.getPrice());
	}

	
}
