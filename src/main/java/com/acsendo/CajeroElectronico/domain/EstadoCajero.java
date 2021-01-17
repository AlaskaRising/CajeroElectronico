package com.acsendo.CajeroElectronico.domain;

public class EstadoCajero {

	private int denominacion;
	private int cantidad;

	public EstadoCajero() {

	}

	public EstadoCajero(int denominacion, int cantidad) {
		super();
		this.denominacion = denominacion;
		this.cantidad = cantidad;
	}

	public int getDenominacion() {
		return denominacion;
	}

	public void setDenominacion(int denominacion) {
		this.denominacion = denominacion;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}

}
