package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultasMetodoPago {

	public static boolean existeMetodoPago(Connection conexion) throws SQLException {
		String sql = "SELECT 1 FROM MetodoPago WHERE id_metodo = 1";
		PreparedStatement ps = conexion.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		return rs.next();
	}

	public static void insertarMetodoPago(int idMetodo, String detalles, String proveedor, String tipo,
			Connection conexion) {
		String sentenciaSQL = "INSERT INTO metodopago (id_metodo, detalles, proveedor, tipo) VALUES (?, ?, ?, ?)";
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement(sentenciaSQL);
			sentencia.setInt(1, idMetodo);
			sentencia.setString(2, detalles);
			sentencia.setString(3, proveedor);
			sentencia.setString(4, tipo);
			sentencia.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
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
