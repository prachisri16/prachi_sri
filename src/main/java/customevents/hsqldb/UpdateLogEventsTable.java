package customevents.hsqldb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import customevents.model.LogEvents;

public class UpdateLogEventsTable {

	public static void updateLogEventsTable(List<LogEvents> logEventsList, Map<String, Long> finalEventsMap) {
		Connection con = null;
		Statement stmt = null;
		int result = 0;
		
		try {
			Class.forName("org.hsqldb.jdbc.JDBCDriver");
			con = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost/prachidb", "SA", "");
			stmt = con.createStatement();
			
			for (LogEvents logEvent : logEventsList) {
				try {
					if (finalEventsMap.containsKey(logEvent.getId()) && "FINISHED".equalsIgnoreCase(logEvent.getState())) {
						/*result = stmt
								.executeUpdate("INSERT INTO events_tbl VALUES (" + logEvent.getId() + "," + finalEventsMap.get(logEvent.getId()) + "," + logEvent.getType() + "," + logEvent.getHost() + "," + (finalEventsMap.get(logEvent.getId()) > 4 ? "true" : "false") + ")");
						con.commit();*/
						
						String sql = 
							    "INSERT INTO events_tbl (id, duration, type, host, alert) " +
							    " Values (?, ?, ?, ?, ?)";
						
						PreparedStatement pstmt = con.prepareStatement(sql);
						pstmt.setString(1, logEvent.getId());
						pstmt.setInt(2, new Long(finalEventsMap.get(logEvent.getId())).intValue());
						pstmt.setString(3, logEvent.getType());
						pstmt.setString(4, logEvent.getHost());
						pstmt.setString(5, finalEventsMap.get(logEvent.getId()) > 4 ? "true" : "false");
						result = pstmt.executeUpdate();    // run the query
						
						System.out.println("Update Result: " + result);
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
	}

}
