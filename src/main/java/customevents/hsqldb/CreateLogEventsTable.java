package customevents.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class CreateLogEventsTable {

	public static int createLogEventsTable() {
		Connection con = null;
		Statement stmt = null;
		int result = 0;

		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/prachidb", "SA", "");
			stmt = con.createStatement();

			result = stmt.executeUpdate(
					"CREATE TABLE events_tbl (id VARCHAR(50) NOT NULL, duration INT NOT NULL, type VARCHAR(20), host VARCHAR(20), alert VARCHAR(50), PRIMARY KEY (id))");
			System.out.println("Table created successfully");
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		
		System.out.println("Result: " + result);
		
		return result;
	}

}
