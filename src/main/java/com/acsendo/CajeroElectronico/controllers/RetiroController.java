package com.acsendo.CajeroElectronico.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acsendo.CajeroElectronico.domain.Transaccion;
import com.acsendo.CajeroElectronico.exception.CustomException;
import com.acsendo.CajeroElectronico.services.ICajero;
import com.acsendo.CajeroElectronico.services.ITransaccion;

@RestController
@RequestMapping("/retiro")
public class RetiroController {

	@Autowired
	private ITransaccion transaccion;
	@Autowired
	private ICajero cajero;
	
	@PostMapping("/")
	public Transaccion realizarRetiro(@RequestBody Transaccion retiro) {
		if (retiro.getValor_solicitado() <= 0) {
			throw new CustomException("Pruebe con un valor diferente, solo se permite realizar retiros superiores a 1000. Valor solicitado: " + retiro.getValor_solicitado());
		}
		if (retiro.getValor_solicitado() % 1000 != 0) {
			throw new CustomException("Pruebe con un valor diferente, solo se permite realizar retiros en múltiplos de 1000. Valor solicitado: " + retiro.getValor_solicitado());
		}
		if (!transaccion.comprobarFondos(retiro.getValor_solicitado())) {
			throw new CustomException("Fondos insuficientes, inténtelo nuevamente con un valor diferente. Valor solicitado: " + retiro.getValor_solicitado());
		}
		if(!transaccion.evaluarRetiro(retiro, cajero.getEstadoCajero())) {
			throw new CustomException("La denominación de billetes en el cajero no permite realizar el retiro completo. Valor solicitado: " + retiro.getValor_solicitado());
		}		
		return transaccion.realizarRetiro(retiro, cajero.getEstadoCajero());
	}
}
