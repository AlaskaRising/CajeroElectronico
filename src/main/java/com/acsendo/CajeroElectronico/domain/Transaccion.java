package com.acsendo.CajeroElectronico.domain;

public class Transaccion {

	private int valor_solicitado;
	private String billetes_entregados = "";

	public int getValor_solicitado() {
		return valor_solicitado;
	}

	public void setValor_solicitado(int valor_solicitado) {
		this.valor_solicitado = valor_solicitado;
	}

	public String getBilletes_entregados() {
		return billetes_entregados;
	}

	public void setBilletes_entregados(String billetes_entregados) {
		this.billetes_entregados += billetes_entregados;
	}

}
