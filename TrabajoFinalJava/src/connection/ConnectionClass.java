/*
 * Autor Carlos Michelena Rueda
 * Fecha: 09/11/2025
 */

package connection;

import java.sql.*;
import java.util.ArrayList;

import model.ConnectionData;

public class ConnectionClass {

	private Connection connectionDB;
	private ConnectionData connectionData;
	private ArrayList<Statement> statements = new ArrayList<>();
	private ArrayList<ResultSet> resultSets = new ArrayList<>();

	public ConnectionClass() throws ClassNotFoundException, SQLException {

		connectionData = new ConnectionData();
		
		Class.forName(connectionData.getMysql());
		
		connectionDB = DriverManager.getConnection(connectionData.getIp(),
				connectionData.getUser(), connectionData.getPassword());
	}

	public Connection getConnectionDB() {
		return connectionDB;
	}

	public void setConnectionDB(Connection connectionDB) {
		this.connectionDB = connectionDB;
	}

	public Statement getStatements(int n) {
		return statements.get(n);
	}

	public void setStatements() throws SQLException {
		this.statements.add(connectionDB.createStatement());
	}

	public ResultSet getResultSets(int n) {
		return resultSets.get(n);
	}

	public void setResultSets(int nS, int nsql) throws SQLException {
		
		ResultSet rs = statements.get(nS).executeQuery(
				connectionData.getSql().get(nsql));
		
		this.resultSets.add(rs);
	}

	public void setResultSets(int nS, String sql) throws SQLException {
		
		ResultSet rs = statements.get(nS).executeQuery(sql);
		
		this.resultSets.add(rs);
	}
}
