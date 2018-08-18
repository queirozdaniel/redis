package com.danielqueiroz.model;

public class NotaFiscal {

	private String codigo;
	private double valor;

	public NotaFiscal() {
	}

	public NotaFiscal(String codigo, double valor) {
		this.codigo = codigo;
		this.valor = valor;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

}
