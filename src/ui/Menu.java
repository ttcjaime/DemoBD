package ui;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import acceso.ConexionBd;
import consultas.ConsultasArtista;
import consultas.ConsultasCliente;
import consultas.ConsultasMetodoPago;
import consultas.ConsultasPedido;
import consultas.ConsultasPinturas;
import consultas.ConsultasUsuario;

public class Menu {

	private Connection conexion;
	private String rol;
	private Scanner scanner = new Scanner(System.in);
	boolean inicioSesion = false;
	private String emailUsuario;

	public void menuAcceder() throws SQLException {
		conexion = ConexionBd.conectar();
		scanner = new Scanner(System.in);
		
		while (!inicioSesion) {
			System.out.print("Bienvenido a Inkly \n" + "1. Registrarse \n" + "2. Inicio sesión \n" + "Escoge: ");
			String eleccion = scanner.nextLine();
			switch (eleccion) {
			case "1":
				definirRoles();
				break;
			case "2":
				inicioSesion();
				break;
			default:
				System.out.println("Escoge una opción correcta");
				break;
			}
		}
		menuInicio();
	}

	private void menuInicio() throws SQLException {
		int opcion = 0;
		do {
			try {
				System.out.print("Bienvenido a Inkly \n" + "1.Ver pinturas \n"
						+ "2.Comprar dibujo \n" + "3.Subir dibujo \n" + "4.Salir \n" + "Elige: ");
				opcion = scanner.nextInt();
				scanner.nextLine();

				switch (opcion) {
				case 1:
					ConsultasPinturas.verPinturas(conexion);
					break;
				case 2:
					System.out.print("Introduce el nombre de la obra que desea comprar: ");
					String dibujo = scanner.nextLine();
					
					int idDibujo = (int) ConsultasPinturas.obtenerIdDibujo(dibujo, conexion);
					if (idDibujo == -1) {
						System.out.println("No se encontró la obra.");
						break;
					}
					double precioPorId = ConsultasPinturas.obtenerPrecioDibujo(idDibujo, conexion);

					String dni = ConsultasUsuario.obtenerDniUsuario(emailUsuario, conexion);

					int idMetodo = 1; // ya que solo es un metodo de pago
					try {
						if (!ConsultasMetodoPago.existeMetodoPago(conexion)) {
							ConsultasMetodoPago.insertarMetodoPago(idMetodo, "Visa 1234", "Visa", "Tarjeta", conexion);
						} else {
							System.out.println("Ya existe un método de pago, no se inserta de nuevo.");
						}
					} catch (SQLException e) {
						e.printStackTrace();
					}

					int idPedido = ConsultasPedido.crearPedido(conexion, dni, precioPorId, idMetodo);
					
					ConsultasPinturas.comprarPintura(idPedido, idDibujo, precioPorId, conexion);
					
					System.out.println("Compra realizada correctamente. ID de pedido: " + idPedido);
					break;
				case 3:
					subirPintura();	
					break;
				case 4:
					System.out.println("Saliendo de Inkly...");
					break;
				default:
					System.out.println("Introduce una opción corretcta.");
					break;
				}
			} catch (InputMismatchException ime) {
				System.out.println("Introduce un caracter valido.");
				scanner.nextLine();
			}
		} while (opcion != 4);

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
				if (edad < 100 && edad >= 18) {
					edadCorrecta = true;
					formatoCorrecto = true;
				} else {
					System.out.println("La edad tiene que ser menor a 100 años");
				}
			} catch (InputMismatchException ime) {
				System.out.println("Introduce una edad correcta");
				formatoCorrecto = false;
				scanner.nextLine();
			}
		} while (!formatoCorrecto || !edadCorrecta);

		while (!numeroCorrecto) {
			System.out.print("Introduce tu telefono: ");
			telefono = scanner.nextLine();
			if (telefono.length() == 9 && telefono.matches("^\\d+$")) {
				numeroCorrecto = true;
			} else {
				System.out.println("Introduce un número de telefono correcto");
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

		if (ConsultasUsuario.usuarioExiste(dni, conexion, email)) {
			System.out.println("Ya existe un usuario con ese Email o DNI");
		} else {
			ConsultasUsuario.insertarUsuario(nombre, edad, telefono, dni, nacionalidad, email, pswd, rol, conexion);
			System.out.println("Usuario introducido correctamente");
			// añadi esto
			if (rol.equals("artista")) {
				System.out.println("_________________________________________");
				System.out.println("Terminemos configurando unos detalles...");
				System.out.print("Introduce una biografia: ");
				String biografia = scanner.nextLine();
				System.out.print("¿Cual es tu estilo artistico?: ");
				String estilo = scanner.nextLine();
					
				int seguidores = (int) (Math.random() * 100) + 1;
				boolean verificado = true;
				ConsultasArtista.insertarArtista(dni, biografia, estilo, verificado, seguidores, conexion);
				System.out.println("_________________________________________");
			} else {
				System.out.println("_________________________________________");
				System.out.println("Terminemos configurando unos detalles...");
				System.out.print("Introduce tu dirección: ");
				String direccion = scanner.nextLine();
				ConsultasCliente.insertarCliente(direccion, dni, conexion);
				System.out.println("_________________________________________");
			}

		}

	}

	private void inicioSesion() {
		System.out.print("Introduce tu email: ");
		String email = scanner.nextLine();

		System.out.print("Introduce tu contraseña: ");
		String pswd = scanner.nextLine();

		if (ConsultasUsuario.puedeIniciarSesion(email, pswd, conexion)) {
			System.out.println("Se ha iniciado sesión");
			rol = ConsultasUsuario.asignarRol(email, conexion);
			emailUsuario = email;
			inicioSesion = true;
		} else {
			System.out.println("El correo o contraseña no son validos");
		}

	}

	private void definirRoles() throws SQLException {
		System.out.print("¿Vas a ser artista o cliente? \n" + "1.Artista \n" + "2.Cliente \n" + "Elige: ");
		String opcion = scanner.nextLine();
		switch (opcion) {
		case "1":
			rol = "artista";
			registroUsuario();
			break;
		case "2":
			rol = "cliente";
			registroUsuario();
			break;
		}
	}

	private void subirPintura() {
		if (rol.equals("artista")) {
			System.out.print("Introduce el titulo de tu obra: ");
			String titulo = scanner.nextLine();

			System.out.print("Introduce una descripción: ");
			String descripcion = scanner.nextLine();

			System.out.print("Introduce el estilo de tu obra: ");
			String estilo = scanner.nextLine();

			System.out.print("Introduce el precio: ");
			double precio = scanner.nextDouble();
			scanner.nextLine();

			LocalDate fecha = LocalDate.now();

			ConsultasPinturas.subirPintura(titulo, descripcion, estilo, precio, fecha, conexion);

		} else {
			System.out.println("Debes ser un artista para subir un dibujo.");
		}
	}

}
