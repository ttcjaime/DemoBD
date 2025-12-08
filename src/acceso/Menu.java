package acceso;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Menu {

	private Connection conexion;
	String rol = "";
	private Scanner scanner = new Scanner(System.in);
	boolean inicioSesion = false;
	String emailUsuario = "";

	public void acceder() throws SQLException {
		conexion = ConexionBd.conectar();
		scanner = new Scanner(System.in);
		
		//mientras no se inicie sesion, nosotros seguiremos en el mismo menu
		while (!inicioSesion) {
			System.out.print("Bienvenido a Inkly \n" + "1. Registrarse \n" + "2. Inicio sesión \n" + "Escoge: ");
			String eleccion = scanner.nextLine();
			switch (eleccion) {
			case "1":
				// modificado por mi
				definirRoles();
				// registroUsuario();
				break;
			case "2":
				inicioSesion();
				break;
			default:
				System.err.println("Escoge una opción correcta");
				break;

			}
		}

		inicio();

	}

	private void inicio() throws InputMismatchException {

		// añadido el do while y lo cambie a int ya que asi el bucle do while no da
		// problemas
		// he quitado lo de configuraciones
		int opcion = 0;
		do {
			try {
				System.out.print("Bienvenido a Inkly \n" + "1.Ver carrito de compras \n" + "2.Ver pinturas \n"
						+ "3.Comprar dibujo \n" + "4.Subir dibujo \n" + "5.Salir \n" + "Elige: ");
				opcion = scanner.nextInt();
				scanner.nextLine();

				switch (opcion) {
				case 1:
					break;
				case 2:
					Consultas.verPinturas(conexion);
					break;
				case 3:
					System.out.print("Introduce el nombre de la obra que desea comprar: ");
					String dibujo = scanner.nextLine();

					int idDibujo = (int) Consultas.obtenerIdDibujo(dibujo, conexion);
					if (idDibujo == -1) {
						System.out.println("No se encontró la obra.");
						break;
					}
					double precioPorId = Consultas.obtenerPrecioDibujo(idDibujo, conexion);

					String dni = Consultas.obtenerDniUsuario(emailUsuario, conexion);

					int idMetodo = 1; // ya que solo es un metodo de pago
					Consultas.insertarMetodoPago(idMetodo, "Visa 1234", "Visa", "Tarjeta", conexion);

					int idPedido = Consultas.crearPedido(conexion, dni, precioPorId, idMetodo);
					if (idPedido == -1) {
						System.out.println("Error al crear el pedido.");
						break;
					}

					Consultas.comprarPintura(idPedido, idDibujo, precioPorId, conexion);
					System.out.println("Compra realizada correctamente. ID de pedido: " + idPedido);
					break;
				case 4:
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

						int id = (int) (Math.random() * 100) + 1;

						Consultas.subirPintura(titulo, descripcion, estilo, precio, id, fecha, conexion);

					} else {
						System.err.println("Debes ser un artista para subir un dibujo.");
					}
					break;
				case 5:
					System.out.println("Saliendo de Inkly...");
					break;
				default:
					System.err.println("Introduce una opción corretcta.");
					break;
				}
			} catch (InputMismatchException ime) {
				System.err.println("Introduce un caracter valido.");
				scanner.nextLine();
			}
		} while (opcion != 5);

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
				// no pueden haber menores de edad
				if (edad < 100 && edad >= 18) {
					edadCorrecta = true;
				} else {
					System.err.println("La edad tiene que ser menor a 100 años");
				}
			} catch (InputMismatchException ime) {
				System.err.println("Introduce una edad correcta");
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
				System.err.println("Introduce un número de telefono correcto");
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

		if (Consultas.usuarioExiste(dni, conexion, email)) {
			System.out.println("Ya existe un usuario con ese Email o DNI");
		} else {
			Consultas.insertarUsuario(nombre, edad, telefono, dni, nacionalidad, email, pswd, rol, conexion);
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
				Consultas.insertarArtista(dni, biografia, estilo, verificado, seguidores, conexion);
				System.out.println("_________________________________________");
			} else {
				System.out.println("_________________________________________");
				System.out.println("Terminemos configurando unos detalles...");
				System.out.print("Introduce tu dirección: ");
				String direccion = scanner.nextLine();
				Consultas.insertarCliente(direccion, dni, conexion);
				System.out.println("_________________________________________");
			}
			
		}

	}

	private void inicioSesion() {
		System.out.print("Introduce tu email: ");
		String email = scanner.nextLine();

		System.out.print("Introduce tu contraseña: ");
		String pswd = scanner.nextLine();

		if (Consultas.puedeIniciarSesion(email, pswd, conexion)) {
			System.out.println("Se ha iniciado sesión");
			rol = Consultas.asignarRol(email, conexion);
			emailUsuario = email;
			inicioSesion = true;
		} else {
			System.out.println("El correo o contraseña no son validos");
		}

	}

	// añadido por mi
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

}
