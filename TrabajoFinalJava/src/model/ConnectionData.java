/*
 * Autor Carlos Michelena Rueda
 * Fecha: 09/11/2025
 */

package model;

import java.util.ArrayList;

public class ConnectionData {

	private String user;
	private String password;
	private String dataBase;
	private String ip;
	private String mysql;
	private ArrayList<String> sql = new ArrayList<String>();

	public ConnectionData() {

		user = "root";
		password = "";
		dataBase = "comunidad";
		ip = "jdbc:mysql://localhost/" + dataBase + "?serverTimezone=UTC";
		mysql = "com.mysql.cj.jdbc.Driver";
		addSql();
	}

	// -----------------------
	// -- Getters y Setters --

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getDataBase() {
		return dataBase;
	}

	public void setDataBase(String dataBase) {
		this.dataBase = dataBase;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getMysql() {
		return mysql;
	}

	public void setMysql(String mysql) {
		this.mysql = mysql;
	}

	public ArrayList<String> getSql() {
		return sql;
	}

	public void setSql(ArrayList<String> sql) {
		this.sql = sql;
	}

	// -- Fin Getters y Setters --
	// ---------------------------

	// Método donde se añaden las consultas sql y sentencias concretas
	private void addSql() {

		sql.add("SHOW TABLES;");  														// 0
		sql.add("SELECT * FROM ");  													// 1
		sql.add("SELECT p. codigo_recibo, v.nombre, v.apellido, "
				+ "COUNT(p.pagado) AS no_pagado FROM pagos p "
				+ "JOIN vecinos v ON p.id_vecino = v.id "
				+ "WHERE pagado = 0 GROUP BY id_vecino;");  							// 2
		sql.add("SELECT p. codigo_recibo, v.nombre, v.apellido, "
				+ "COUNT(p.pagado) AS no_pagado FROM pagos p "
				+ "JOIN vecinos v ON p.id_vecino = v.id "
				+ "WHERE pagado = 0 GROUP BY id_vecino;");  							// 3
		sql.add("UPDATE recibos r JOIN pagos p ON r.codigo_recibo = p.codigo_recibo"
				+ " SET r.importe_base = r.importe_base * 1.10 WHERE pagado = 1"
				+ " AND DATEDIFF(p.fecha_pago, r.fecha_limite_pago) >= 2;");  			// 4
	}

	// Método para insertar datos recibiendo el nombre de la tabla
	// en la que se desean insertar los datos
	public String insertData(String tableName) {

		return "INSERT INTO " + tableName + " VALUES ( ";
	}

	// Método para actualizar datos recibiendo el nombre de la tabla
	// donde se desean actualizar los datos
	public String updateData(String tableName, String columnName, String idColumn) {

		return "UPDATE " + tableName + " SET " + columnName
				+ " = ? WHERE " + idColumn + " = ?;";
	}
}
