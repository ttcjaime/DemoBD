package acceso;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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

	public static void insertarUsuario(String nombre, int edad, String telefono, String dni,
			String nacionalidad, String email, String pswd, Connection conexion) throws SQLException {
		String sentenciaSQL = "INSERT INTO usuarios (nombre, edad, telefono,"
				+ " dni, nacionalidad, email, passwor) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
				System.out.println(resultado.getString(1) + " - " + resultado.getString(2) + " - "
						+ resultado.getString(3) + " - " + resultado.getInt(4) + " - " + resultado.getString(5));
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
	
}
