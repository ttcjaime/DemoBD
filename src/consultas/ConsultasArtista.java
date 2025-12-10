package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConsultasArtista {
	
	public static void insertarArtista(String dni, String biografia, String estilo, boolean verificado, int seguidores,
			Connection conexion) throws SQLException {
		String sentenciaSQL = "INSERT INTO artista (dni, biografia, estilo, verificado, seguidores) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement(sentenciaSQL);
			sentencia.setString(1, dni);
			sentencia.setString(2, biografia);
			sentencia.setString(3, estilo);
			sentencia.setBoolean(4, verificado);
			sentencia.setInt(5, seguidores);
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
