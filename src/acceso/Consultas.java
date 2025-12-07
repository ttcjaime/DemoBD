package acceso;

import java.sql.Connection;
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
			sentencia.setString(6, String.valueOf(fecha));
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

	public static int crearPedido(Connection conexion, String dniCliente, double total, int idMetodo) {
		int idPedido = (int) (Math.random() * 1000) + 1;
		String sentenciaSQL = "INSERT INTO pedido (id_pedido, total, dni_cliente, id_metodo) VALUES (?, ?, ?, ?)";
		try (PreparedStatement sentencia = conexion.prepareStatement(sentenciaSQL)) {
			sentencia.setInt(1, idPedido);
			sentencia.setDouble(2, total);
			sentencia.setString(3, dniCliente);
			sentencia.setInt(4, idMetodo);
			sentencia.executeUpdate();
			return idPedido;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	public static void comprarPintura(int id_pedido, int id_dibujos, double precio, Connection conexion) {
		String sentenciaSQL = "INSERT INTO pedido_dibujos (id_pedido, id_dibujos, precio_compra) VALUES (?, ?, ?)";
		PreparedStatement sentencia = null;
		try {
			sentencia = conexion.prepareStatement(sentenciaSQL);
			sentencia.setInt(1, id_pedido);
			sentencia.setInt(2, id_dibujos);
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

	//este metodo lo ocuparemos para que, cuando se inicie sesión, obtener el rol
	//del usuario, y asi, si es cliente, no pueda subir una obra
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

}
