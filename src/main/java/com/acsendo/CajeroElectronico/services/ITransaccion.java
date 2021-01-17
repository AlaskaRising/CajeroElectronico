package com.acsendo.CajeroElectronico.services;

import java.util.List;

import com.acsendo.CajeroElectronico.domain.EstadoCajero;
import com.acsendo.CajeroElectronico.domain.Transaccion;

public interface ITransaccion {

	boolean comprobarFondos(int valor_solicitado);

	Transaccion realizarRetiro(Transaccion retiro, List<EstadoCajero> list);

	boolean evaluarRetiro(Transaccion retiro, List<EstadoCajero> estadoCajero);

}
