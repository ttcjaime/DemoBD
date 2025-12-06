package acceso;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class Consultas {

	public static boolean puedeIniciarSesion(String email, String pswd, Connection conexion) {
		String sentenciaSql = "SELECT passwor FROM usuarios WHERE email = ?";
		PreparedStatement sentencia = null;

		try {
			sentencia = conexion.prepareStatement(sentenciaSql);
			sentencia.setString(1, email); // Sustituimos el ?

			ResultSet resultado = sentencia.executeQuery();

			if (resultado.next()) {
				String pswdExistente = resultado.getString("passwor");
				return pswdExistente.equals(pswd); // true si coincide, false si no
			}

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
		return false;
	}

	public static boolean usuarioExiste(String dni, Connection conexion) {
		String sentenciaSql = "SELECT dni FROM usuarios";
		PreparedStatement sentencia = null;

		try {
			sentencia = conexion.prepareStatement(sentenciaSql);
			ResultSet resultado = sentencia.executeQuery();
			while (resultado.next()) {
				String dniExistente = resultado.getString(1);
				if (dniExistente.equals(dni)) {
					return true;
				}
			}
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
		return false;
	}

	// añadi rol
	public static void insertarUsuario(String nombre, int edad, String telefono, String dni, String nacionalidad,
			String email, String pswd, String rol, Connection conexion) throws SQLException {
		String sentenciaSQL = "INSERT INTO usuarios (nombre, edad, telefono,"
				+ " dni, nacionalidad, email, passwor, rol) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement(sentenciaSQL);
			sentencia.setString(1, nombre);
			sentencia.setInt(2, edad);
			sentencia.setString(3, telefono);
			sentencia.setString(4, dni);
			sentencia.setString(5, nacionalidad);
			sentencia.setString(6, email);
			sentencia.setString(7, pswd);
			sentencia.setString(8, rol);
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

	// añadido por mi
	public static void subirPintura(String titulo, String descripcion, String estilo, double precio, int id,
			LocalDate fecha, Connection conexion) {
		String sentenciaSQL = "INSERT INTO dibujos (titulo, descripcion, estilo,"
				+ " precio, id_dibujos, fecha_publicacion) VALUES (?, ?, ?, ?, ?, ?)";
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement(sentenciaSQL);
			sentencia.setString(1, titulo);
			sentencia.setString(2, descripcion);
			sentencia.setString(3, estilo);
			sentencia.setDouble(4, precio);
			sentencia.setInt(5, id);
			sentencia.setDate(6, java.sql.Date.valueOf(fecha));
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
	
	public static void comprarPintura() {
		
	}

}
