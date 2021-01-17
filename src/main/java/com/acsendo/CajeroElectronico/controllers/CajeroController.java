package com.acsendo.CajeroElectronico.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.acsendo.CajeroElectronico.domain.EstadoCajero;
import com.acsendo.CajeroElectronico.exception.CustomException;
import com.acsendo.CajeroElectronico.services.ICajero;

@RestController
@RequestMapping("/cajero")
public class CajeroController {

	@Autowired
	private ICajero cajero;
	
	
	@GetMapping("/")
	public List<EstadoCajero> getEstadoCajero() {		 
		return cajero.getEstadoCajero();
	}
	
	@PostMapping("/")
	public List<EstadoCajero> setDeposito(@RequestBody EstadoCajero deposito) {
		if (deposito.getCantidad() <= 0) {
			throw new CustomException("Ingrese una cantidad de dinero superior a 0. Cantidad ingresada: " + deposito.getCantidad());
		}
		if (!cajero.checkDenominacion(deposito.getDenominacion())) {
			throw new CustomException("Ingrese una denominación de billetes valida. Denominación ingresada: " + deposito.getDenominacion());
		}
		cajero.setIngreso(deposito);
		return cajero.getEstadoCajero();
	}
}
