package principal;

import java.sql.SQLException;

import acceso.Menu;

public class Main {

	public static void main(String[] args) {
		
		Menu menu = new Menu();
		try {
			menu.acceder();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
