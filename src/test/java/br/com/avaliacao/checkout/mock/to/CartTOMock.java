package br.com.avaliacao.checkout.mock.to;

import org.junit.Assert;

import br.com.avaliacao.checkout.mock.MockGenerator;
import br.com.avaliacao.checkout.to.CartTO;

public class CartTOMock extends MockGenerator<CartTO> {

	private static final String BRAND = "DELL";
	private static final String CODE_PRODUCT = "78293";
	private static final String NAME_PRODUCT = "Notebook Latitude";
	private static final Double PRICE = 2800.00;
	private static final Integer QUANTITY = 1;
	
	@Override
	public CartTO createMock() {
		
		CartTO to = new CartTO();
		to.setBrand(BRAND);		
		to.setCodeProduct(CODE_PRODUCT);
		to.setNameProduct(NAME_PRODUCT);
		to.setPrice(PRICE);
		to.setQ(QUANTITY);
		
		return to;
				
	}

	@Override
	public void assertMock(CartTO to) {
		Assert.assertNotNull(to);
		Assert.assertEquals(BRAND, to.getBrand());		
		Assert.assertEquals(CODE_PRODUCT, to.getCodeProduct());
		Assert.assertEquals(NAME_PRODUCT, to.getNameProduct());
		Assert.assertEquals(PRICE, to.getPrice());
		Assert.assertEquals(QUANTITY, to.getQ());
	}


}
