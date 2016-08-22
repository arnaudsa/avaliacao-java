package br.com.avaliacao.checkout.to;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class CartTOTest {

	private static final String BRAND = "DELL";
	private static final String CART_ID = java.util.UUID.randomUUID().toString();
	private static final String CODE_PRODUCT = "78293";
	private static final String NAME_PRODUCT = "Notebook Latitude";
	private static final Double PRICE = 2800.00;
	private static final Integer QUANTITY = 1;
	
	private CartTO to;

	
	@Before
	public void before() {
		to = new CartTO();
		to.setBrand(BRAND);		
		to.setCartId(CART_ID);
		to.setCodeProduct(CODE_PRODUCT);
		to.setNameProduct(NAME_PRODUCT);
		to.setPrice(PRICE);
		to.setQ(QUANTITY);
	}
	
	@Test
	public void testGetCartId() {
		Assert.assertEquals(BRAND, to.getBrand());
		Assert.assertEquals(CART_ID, to.getCartId());
		Assert.assertEquals(CODE_PRODUCT, to.getCodeProduct());
		Assert.assertEquals(NAME_PRODUCT, to.getNameProduct());
		Assert.assertEquals(PRICE, to.getPrice());
		Assert.assertEquals(QUANTITY, to.getQ());
	}



}
