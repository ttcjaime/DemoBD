package acceso;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

	private Connection conexion;
	private Scanner scanner = new Scanner(System.in);

	public void acceder() throws SQLException {
		conexion = ConexionBd.conectar();
		scanner = new Scanner(System.in);
		System.out.print("Bienvenido a Inkly \n" + "1. Registrarse \n" + "2. Inicio sesión \n" + "Escoge: ");
		String eleccion = scanner.nextLine();

		switch (eleccion) {
		case "1":
			registroUsuario();
			break;
		case "2":
			inicioSesion();
			break;
		default:
			System.out.println("Escoge una opción correcta");
			break;

		}

		inicio();

	}

	private void inicio() {
		System.out.print("Bienvenido a Inkly \n" + "1.Ver carrito de compras \n" + "2.Ver pinturas \n"
				+ "3.Configuraciones \n" + "4.Salir \n" + "Elige: ");
		String opcion = scanner.nextLine();

		switch (opcion) {
		case "1":
			break;
		case "2":
			Consultas.verPinturas(conexion);
			break;
		case "3":
			break;
		case "4":
			System.out.println("Saliendo de Inkly...");
			break;
		default:
			break;
		}

	}

	private void registroUsuario() throws SQLException {

		boolean edadCorrecta = false;
		boolean numeroCorrecto = false;
		boolean formatoCorrecto = true;
		int edad = 0;
		String telefono = "";
		
		System.out.print("Introduce tu nombre: ");
		String nombre = scanner.nextLine();

		do {
			try {
				System.out.print("Introduce tu edad: ");
				edad = scanner.nextInt();
				scanner.nextLine();
				if (edad < 100) {
					edadCorrecta = true;
				} else {
					System.err.println("La edad tiene que ser menor a 100 años");
				}
			} catch (InputMismatchException ime) {
				System.err.println("Introduce una edad correcta");
				formatoCorrecto = false;
				scanner.nextLine();
			}
		} while (!formatoCorrecto && !edadCorrecta);

		while (!numeroCorrecto) {
			System.out.print("Introduce tu telefono: ");
			telefono = scanner.nextLine();
			if (telefono.length() == 9 && telefono.matches("^\\d+$")) {
				numeroCorrecto = true;
			} else {
				System.err.println("Introduce un número de telefono correcto");
				scanner.nextLine();
			}
		}

		System.out.print("Introduce tu DNI: ");
		String dni = scanner.nextLine();

		System.out.print("Introduce tu nacionalidad: ");
		String nacionalidad = scanner.nextLine();

		System.out.print("Introduce tu email: ");
		String email = scanner.nextLine();

		System.out.print("Introduce tu contraseña: ");
		String pswd = scanner.nextLine();

		if (Consultas.usuarioExiste(dni, conexion)) {
			System.out.println("El usuario con DNI " + dni + " ya existe");
		} else {
			Consultas.insertarUsuario(nombre, edad, telefono, dni, nacionalidad, email, pswd, conexion);
			System.out.println("Usuario introducido correctamente");
		}

	}

	private void inicioSesion() {
		System.out.println("Introduce tu email: ");
		String email = scanner.nextLine();

		System.out.println("Introduce tu contraseña");
		String pswd = scanner.nextLine();

		if (Consultas.puedeIniciarSesion(email, pswd, conexion)) {
			System.out.println("Se ha iniciado sesión");
		} else {
			System.out.println("El correo o contraseña no son validos");
		}

	}

}
