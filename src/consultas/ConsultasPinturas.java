package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ConsultasPinturas {

	public static void verPinturas(Connection conexion) {
		String sentenciaSql = "SELECT descripcion, estilo, titulo, precio, fecha_publicacion FROM dibujos";
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement(sentenciaSql);
			ResultSet resultado = sentencia.executeQuery();
			while (resultado.next()) {
				System.out.println("Descripcion: " + resultado.getString(1) + " | Estilo: " + resultado.getString(2)
						+ "\n Titulo: " + resultado.getString(3) + " | Precio: " + resultado.getInt(4)
						+ "\n Fecha de publicación: " + resultado.getString(5));
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
		} finally {
			try {
				sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static int obtenerIdDibujo(String titulo, Connection conexion) {
		String sentenciaSql = "SELECT id_dibujos FROM dibujos WHERE titulo = ?";
		PreparedStatement sentencia = null;
		ResultSet resultado = null;

		try {
			sentencia = conexion.prepareStatement(sentenciaSql);
			sentencia.setString(1, titulo);
			resultado = sentencia.executeQuery();

			while (resultado.next()) {
				return resultado.getInt("id_dibujos");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultado != null)
					resultado.close();
				if (sentencia != null)
					sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return -1;

	}

	public static double obtenerPrecioDibujo(int idDibujo, Connection conexion) {
		String sentenciaSql = "SELECT precio FROM dibujos WHERE id_dibujos = ?";
		PreparedStatement sentencia = null;
		ResultSet resultado = null;

		try {
			sentencia = conexion.prepareStatement(sentenciaSql);
			sentencia.setInt(1, idDibujo);
			resultado = sentencia.executeQuery();

			if (resultado.next()) {
				return resultado.getDouble("precio");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (resultado != null)
					resultado.close();
				if (sentencia != null)
					sentencia.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return -1;
	}
	
	public static void subirPintura(String titulo, String descripcion, String estilo, double precio,
			LocalDate fecha, Connection conexion) {
		String sentenciaSQL = "INSERT INTO dibujos (titulo, descripcion, estilo,"
				+ " precio, fecha_publicacion) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement(sentenciaSQL);
			sentencia.setString(1, titulo);
			sentencia.setString(2, descripcion);
			sentencia.setString(3, estilo);
			sentencia.setDouble(4, precio);
			sentencia.setString(5, String.valueOf(fecha));
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
	
	public static void comprarPintura(int id_dibujos, int id_pedido, double precio, Connection conexion) {
		String sentenciaSQL = "INSERT INTO pedido_dibujos (id_pedido, id_dibujos, precio_compra) VALUES (?, ?, ?)";
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement(sentenciaSQL);
			sentencia.setInt(1, id_dibujos);
			sentencia.setInt(2, id_pedido);
			sentencia.setDouble(3, precio);
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
