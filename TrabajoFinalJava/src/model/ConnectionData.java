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
		dataBase = "zona_fit_db";
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

	private void addSql() {

		sql.add("SHOW TABLES "); // 0
		sql.add("SELECT * FROM "); // 1
	}
}
