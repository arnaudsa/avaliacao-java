package br.com.avaliacao.checkout.validation;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import br.com.avaliacao.checkout.mock.to.CartTOMock;
import br.com.avaliacao.checkout.to.CartTO;
import br.com.avaliacao.checkout.to.MessageErrorTO;


public class CartValidationTest {

	private Validator validator;
	private CartTO cartTO;
		
	@Before
	public void before() {
		validator = new CartValidation();
		cartTO = new CartTOMock().createMock();
	}
	
	@Test
	public void testSupportsErro() {
		Assert.assertFalse(validator.supports(MessageErrorTO.class));
		Assert.assertTrue(validator.supports(CartTO.class));
	}

	@Test
	public void testValidate() {
		BindException errors = new BindException(cartTO, "cartTO");
		ValidationUtils.invokeValidator(validator, cartTO, errors);
		Assert.assertFalse(errors.hasErrors());
	}

	@Test
	public void testValidateErro() {
		cartTO = new CartTO();
		
		BindException errors = new BindException(cartTO, "cartTO");
		ValidationUtils.invokeValidator(validator, cartTO, errors);
		Assert.assertTrue(errors.hasErrors());
		Assert.assertEquals(5, errors.getFieldErrorCount());
		Assert.assertEquals("field.required.to.codeProduct", errors.getFieldError("codeProduct").getCode());
		Assert.assertEquals("field.required.to.nameProduct", errors.getFieldError("nameProduct").getCode());
		Assert.assertEquals("field.required.to.brand", errors.getFieldError("brand").getCode());
		Assert.assertEquals("field.required.to.price", errors.getFieldError("price").getCode());
		Assert.assertEquals("field.required.to.q", errors.getFieldError("q").getCode());

	}

}
