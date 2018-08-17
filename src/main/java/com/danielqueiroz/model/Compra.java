package com.danielqueiroz.model;

public class Compra {

	private String produto;
	private double valorPago;

	public Compra() {
	}

	public Compra(String produto, double valorPago) {
		this.produto = produto;
		this.valorPago = valorPago;
	}

	public String getProduto() {
		return produto;
	}

	public void setProduto(String produto) {
		this.produto = produto;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((produto == null) ? 0 : produto.hashCode());
		long temp;
		temp = Double.doubleToLongBits(valorPago);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Compra other = (Compra) obj;
		if (produto == null) {
			if (other.produto != null)
				return false;
		} else if (!produto.equals(other.produto))
			return false;
		if (Double.doubleToLongBits(valorPago) != Double.doubleToLongBits(other.valorPago))
			return false;
		return true;
	}

}
