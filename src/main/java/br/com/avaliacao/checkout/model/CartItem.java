package br.com.avaliacao.checkout.model;

public class CartItem {

    private Produto produto;

    private Integer quantity;

    public Double getPrice() {    	
        return quantity * produto.getPreco();
    }

    //?
    public void incrementQuantity(final Integer quantity) {
        this.quantity += quantity;
    }

    public Produto getProduto() {
        return this.produto;
    }

    public Integer getQuantity() {
        return this.quantity;
    }

    public void setProduto(final Produto produto) {
        this.produto = produto;
    }

    public void setQuantity(final Integer quantity) {
        this.quantity = quantity;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartItem other = (CartItem) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CartItem [produto=");
		builder.append(produto);
		builder.append(", quantity=");
		builder.append(quantity);
		builder.append("]");
		return builder.toString();
	}	
}