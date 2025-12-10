package principal;

import java.sql.SQLException;

import ui.Menu;

public class Main {

	public static void main(String[] args) {
		
		Menu menu = new Menu();
		try {
			menu.menuAcceder();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
