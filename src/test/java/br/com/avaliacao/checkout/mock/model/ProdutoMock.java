package br.com.avaliacao.checkout.mock.model;

import org.junit.Assert;

import br.com.avaliacao.checkout.mock.MockGenerator;
import br.com.avaliacao.checkout.model.Produto;

public class ProdutoMock extends MockGenerator<Produto> {

	private static final String CODIGO = "1";
	private static final String MARCA = "DELL";
	private static final String NOME = "Notebook Latitude";
	private static final Double PRECO = 2800.00;

	@Override
	public Produto createMock() {
		
		Produto produto = new Produto();
		produto.setCodigo(CODIGO);
		produto.setMarca(MARCA);
		produto.setNome(NOME);
		produto.setPreco(PRECO);
		
		return produto;
	}

	@Override
	public void assertMock(Produto produto) {
		Assert.assertNotNull(produto);
		Assert.assertEquals(CODIGO, produto.getCodigo());		
		Assert.assertEquals(MARCA, produto.getMarca());
		Assert.assertEquals(NOME, produto.getNome());
		Assert.assertEquals(PRECO, produto.getPreco());		
	}

}
