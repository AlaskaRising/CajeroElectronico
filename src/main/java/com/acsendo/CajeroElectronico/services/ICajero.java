package com.acsendo.CajeroElectronico.services;

import java.util.List;

import com.acsendo.CajeroElectronico.domain.EstadoCajero;

public interface ICajero {

	List<EstadoCajero> getEstadoCajero();

	boolean checkDenominacion(int denominacion);

	void setIngreso(EstadoCajero deposito);

}
