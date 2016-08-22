package br.com.avaliacao.checkout.to;

import static br.com.avaliacao.checkout.constants.Constants.CART_NOT_FOUND;
import static br.com.avaliacao.checkout.constants.Constants.QUANTITY_NOT_FOUND;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MessageErrorTOTest {

	private MessageErrorTO messageErrorTO;

	@Before
	public void before() {
		messageErrorTO = new MessageErrorTO();
	}
	

	@Test
	public void testAddMensagem() {
		messageErrorTO.addMensagem(CART_NOT_FOUND);
		Assert.assertTrue(messageErrorTO.getMensagens().size() == 1);
		Assert.assertEquals(CART_NOT_FOUND, messageErrorTO.getMensagens().get(0));
	}

	@Test
	public void testGetMensagens() {
		Assert.assertNotNull(messageErrorTO.getMensagens());
		Assert.assertTrue(messageErrorTO.getMensagens().size()==0);
	}

	@Test
	public void testSetMensagens() {
		messageErrorTO.setMensagens(null);
		Assert.assertNotNull(messageErrorTO.getMensagens());

		messageErrorTO.setMensagens(Arrays.asList(QUANTITY_NOT_FOUND));
		Assert.assertEquals(QUANTITY_NOT_FOUND, messageErrorTO.getMensagens().get(0));
	}

	@Test
	public void testFirstMessage() {
		Assert.assertNotNull(messageErrorTO.firstMessage());
		
		messageErrorTO.setMensagens(Arrays.asList(QUANTITY_NOT_FOUND, CART_NOT_FOUND));
		Assert.assertEquals(QUANTITY_NOT_FOUND, messageErrorTO.firstMessage());
	}

	@Test
	public void testHasError() {
		
		Assert.assertFalse(messageErrorTO.hasError());
		
		messageErrorTO.setMensagens(Arrays.asList(QUANTITY_NOT_FOUND, CART_NOT_FOUND));
		Assert.assertTrue(messageErrorTO.hasError());
	}

}
