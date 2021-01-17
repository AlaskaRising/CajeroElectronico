package com.acsendo.CajeroElectronico.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.acsendo.CajeroElectronico.domain.EstadoCajero;
import com.acsendo.CajeroElectronico.domain.Transaccion;

@Repository
public class TransaccionPostgresql implements ITransaccion {

	@Value("${spring.datasource.url}")
	private String url;
	@Value("${spring.datasource.username}")
	private String username;
	@Value("${spring.datasource.password}")
	private String password;

	public Connection connect() throws SQLException {
		return DriverManager.getConnection(url, username, password);
	}

	@Override
	public boolean comprobarFondos(int valor_solicitado) {
		boolean retiro = false;
		String SQL = "select sum (denominacion * cantidad) as fondos_disponibles from banco.tipo_billete_cajero as ca, banco.tipo_billete as bi\r\n"
				+ "where ca.id_tipo_billete = bi.id_tipo_billete and id_cajero = 1;";
		try {
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				retiro = (rs.getInt("fondos_disponibles") >= valor_solicitado) ? true : false;
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return retiro;
	}

	@Override
	public Transaccion realizarRetiro(Transaccion retiro, List<EstadoCajero> list) {
		int total_retiro = retiro.getValor_solicitado();
		int[] billetes = new int[] { 0, 0, 0, 0, 0, 0 };
		int[] denominacion = new int[] { 50000, 20000, 10000, 5000, 2000, 1000 };
		for (int i = 0; i < billetes.length; i++) {
			billetes[i] = total_retiro / list.get(5 - i).getDenominacion();
			if (billetes[i] <= list.get(5 - i).getCantidad()) {
				total_retiro = total_retiro - (billetes[i] * list.get(5 - i).getDenominacion());
			} else {
				total_retiro = total_retiro - (list.get(5 - i).getCantidad() * list.get(5 - i).getDenominacion());
				billetes[i] = list.get(5 - i).getCantidad();
			}
			retiro.setBilletes_entregados(billetes[i] + "(" + denominacion[i] + ") ");
			if (total_retiro == 0) {
				break;
			}			
		}
		for (int i = 0; i < billetes.length; i++) {
			System.out.println("Total billetes" + billetes[i]);
		}

		for (int i = 0; i < billetes.length; i++) {
			String SQL = "update banco.tipo_billete_cajero set cantidad = cantidad - ?\r\n"
					+ "where id_cajero = 1 and id_tipo_billete = (select id_tipo_billete from banco.tipo_billete as bi where bi.denominacion = ?);";
			try {
				Connection conn = connect();
				PreparedStatement st = conn.prepareStatement(SQL);
				st.setInt(1, billetes[i]);
				st.setInt(2, denominacion[i]);
				ResultSet rs = st.executeQuery();
				rs.close();
				st.close();
			} catch (SQLException ex) {
				System.out.println(ex.getMessage());
			}
		}
		
		return retiro;
	}

	@Override
	public boolean evaluarRetiro(Transaccion retiro, List<EstadoCajero> list) {
		int total_retiro = retiro.getValor_solicitado();
		int[] billetes = new int[] { 0, 0, 0, 0, 0, 0 };
		for (int i = 0; i < billetes.length; i++) {
			billetes[i] = total_retiro / list.get(5 - i).getDenominacion();
			if (billetes[i] <= list.get(5 - i).getCantidad()) {
				total_retiro = total_retiro - (billetes[i] * list.get(5 - i).getDenominacion());
			} else {
				total_retiro = total_retiro - (list.get(5 - i).getCantidad() * list.get(5 - i).getDenominacion());
				billetes[i] = list.get(5 - i).getCantidad();
			}
			if (total_retiro == 0) {
				break;
			}
		}
		if (total_retiro != 0) {
			return false;
		}
		return true;
	}
}
