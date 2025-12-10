package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConsultasCliente {
	
	public static void insertarCliente(String direccion, String dni, Connection conexion) throws SQLException {
		String sentenciaSQL = "INSERT INTO cliente (direccion, dni) VALUES (?, ?)";
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement(sentenciaSQL);
			sentencia.setString(1, direccion);
			sentencia.setString(2, dni);
			sentencia.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			if (sentencia != null) {
				try {
					sentencia.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}	
