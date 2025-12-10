package consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConsultasUsuario {
	
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

	public static boolean usuarioExiste(String dni, Connection conexion, String email) {
		String sentenciaSql = "SELECT dni, email FROM usuarios";
		PreparedStatement sentencia = null;

		try {
			sentencia = conexion.prepareStatement(sentenciaSql);
			ResultSet resultado = sentencia.executeQuery();
			while (resultado.next()) {
				String dniExistente = resultado.getString(1);
				String emailExistente = resultado.getString(2);
				if (dniExistente.equals(dni) || emailExistente.equals(email)) {
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
	
	public static String asignarRol(String email, Connection conexion) {
		String sentenciaSql = "SELECT rol FROM usuarios where email = ?";
		PreparedStatement sentencia = null;
		ResultSet resultado = null;

		try {
			sentencia = conexion.prepareStatement(sentenciaSql);
			sentencia.setString(1, email);
			resultado = sentencia.executeQuery();

			if (resultado.next()) {
				return resultado.getString(1);
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
		return null;
	}

	// metodo para obtener el Dni del usuario que ha iniciado sesion
	// asi evitamos pedirle el Dni de nuevo
	public static String obtenerDniUsuario(String email, Connection conexion) {
		String sentenciaSql = "SELECT dni FROM usuarios where email = ?";
		PreparedStatement sentencia = null;
		ResultSet resultado = null;

		try {
			sentencia = conexion.prepareStatement(sentenciaSql);
			sentencia.setString(1, email);
			resultado = sentencia.executeQuery();

			if (resultado.next()) {
				return resultado.getString(1);
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
		return null;
	}
	
}
