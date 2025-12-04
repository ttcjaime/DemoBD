package acceso;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionBd {
	
	private static Connection conexion;
	
	 public static Connection conectar() throws SQLException {
		conexion = null;
		return conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + "aplicacion_arte", "root", "");
	}

	 public static Connection desconectar() throws SQLException {
		conexion.close();
		return conexion = null;
	}
	
}
