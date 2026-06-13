package SpotOn;

/**
 *  KELOMPOK 2
 *  - NANANG SAEPUDIN   - 2510631170011
 *  - FITRIA            - 2510631170023
 *  - DIMAS INDRAWiJAYA - 2510631170032
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			String url = "jdbc:mysql://localhost:3306/db_parkir";
			String user = "root";
			String pass = "";

			conn = DriverManager.getConnection(url, user, pass);
		} catch (SQLException e) {
			System.out.println("Koneksi gagal: " + e.getMessage());
		}
		return conn;
	}
}
