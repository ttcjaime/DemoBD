package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ConsultasPedido {

	public static int crearPedido(Connection conexion, String dniCliente, double total, int idMetodo) {
		String sentenciaSQL = "INSERT INTO pedido (total, dni_cliente, id_metodo) VALUES (?, ?, ?)";
		try (PreparedStatement sentencia = conexion.prepareStatement(sentenciaSQL)) {
			sentencia.setDouble(1, total);
			sentencia.setString(2, dniCliente);
			sentencia.setInt(3, idMetodo);
			sentencia.executeUpdate();

			try (Statement st = conexion.createStatement();
				ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()")) {
				
				if (rs.next()) {
					return rs.getInt(1);
				}
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return 0;
	}

}
