package br.com.avaliacao.checkout.to;

public class CartTO extends TO {

	private static final long serialVersionUID = 1L;
	
	private String cartId; 
	private Integer q;
	private String codeProduct;
	private String nameProduct;
	private String brand;
	private Double price;

	public String getCartId() {
		return cartId;
	}

	public void setCartId(String cartId) {
		this.cartId = cartId;
	}
	
	public Integer getQ() {
		return q;
	}

	public void setQ(Integer q) {
		this.q = q;
	}

	public String getCodeProduct() {
		return codeProduct;
	}

	public void setCodeProduct(String codeProduct) {
		this.codeProduct = codeProduct;
	}

	public String getNameProduct() {
		return nameProduct;
	}

	public void setNameProduct(String nameProduct) {
		this.nameProduct = nameProduct;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
}
