package com.acsendo.CajeroElectronico.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.acsendo.CajeroElectronico.domain.EstadoCajero;

@Repository
public class CajeroPostgresql implements ICajero {

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
	public List<EstadoCajero> getEstadoCajero() {
		List<EstadoCajero> estadoCajero = new ArrayList<EstadoCajero>();
		String SQL = "select denominacion, cantidad \r\n"
				+ "from banco.tipo_billete_cajero as ca, banco.tipo_billete as bi\r\n"
				+ "where ca.id_tipo_billete = bi.id_tipo_billete and id_cajero = 1 order by 1;";
		try {
			Connection conn = connect();
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(SQL);
			while (rs.next()) {
				EstadoCajero billete = new EstadoCajero(rs.getInt("denominacion"), rs.getInt("cantidad"));
				estadoCajero.add(billete);
			}
			rs.close();
			stmt.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return estadoCajero;
	}

	@Override
	public boolean checkDenominacion(int denominacion) {
		boolean existeDenominacion = false;
		String SQL = "select count(denominacion) as existe from banco.tipo_billete as bi where bi.denominacion = ?";
		try {
			Connection conn = connect();
			PreparedStatement st = conn.prepareStatement(SQL);
			st.setInt(1, denominacion);
			ResultSet rs = st.executeQuery();
			while (rs.next()) {
				existeDenominacion = (rs.getInt("existe") == 1) ? true : false;
			}
			rs.close();
			st.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
		return existeDenominacion;
	}

	@Override
	public void setIngreso(EstadoCajero deposito) {
		String SQL = "update banco.tipo_billete_cajero set cantidad = cantidad + ?\r\n"
				+ "where id_cajero = 1 and id_tipo_billete = (select id_tipo_billete from banco.tipo_billete as bi where bi.denominacion = ?);";
		try {
			Connection conn = connect();
			PreparedStatement st = conn.prepareStatement(SQL);
			st.setInt(1, deposito.getCantidad());
			st.setInt(2, deposito.getDenominacion());
			ResultSet rs = st.executeQuery();
			rs.close();
			st.close();
		} catch (SQLException ex) {
			System.out.println(ex.getMessage());
		}
	}
}
