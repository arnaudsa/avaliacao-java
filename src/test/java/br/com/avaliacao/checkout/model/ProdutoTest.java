package br.com.avaliacao.checkout.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProdutoTest {

	private static final String CODIGO = "1";
	private static final String MARCA = "DELL";
	private static final String NOME = "Notebook Latitude";
	private static final Double PRECO = 2800.00;
	
	private Produto produto;

	@Before
	public void before() {
		produto = new Produto();
		produto.setCodigo(CODIGO);
		produto.setMarca(MARCA);
		produto.setNome(NOME);
		produto.setPreco(PRECO);
	}

	@Test
	public void testSetCodigo() {	
		Assert.assertEquals(CODIGO, produto.getCodigo());		
		Assert.assertEquals(MARCA, produto.getMarca());
		Assert.assertEquals(NOME, produto.getNome());
		Assert.assertEquals(PRECO, produto.getPreco());		
	}

}
