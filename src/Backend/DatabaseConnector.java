package Backend;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * This class handles setting up connections to the database and updating or
 * returning information.
 *
 * @author Sean Johnston, Brendan Casey
 *
 *         TODO: - proper error handling - createDatabase : create initial
 *         database using SQL queries - delete database -> clear tables
 */
public class DatabaseConnector {

	// static variables with database connection details
	private static String user, password, url;
	private static MysqlDataSource dataSource;

	/**
	 * Set database connection details
	 *
	 * @param user
	 * @param password
	 * @param url
	 * @param dbName
	 * @return true or false if details are valid
	 */
	public static boolean setDatabaseConnection(String user, String password,
			String url, String dbName) {
		// set details
		DatabaseConnector.user = user;
		DatabaseConnector.password = password;
		DatabaseConnector.url = url;
		// create MysqlDataSource with details
		dataSource = new MysqlDataSource();
		dataSource.setUser(DatabaseConnector.user);
		dataSource.setPassword(DatabaseConnector.password);
		dataSource.setURL("jdbc:mysql://" + DatabaseConnector.url + "/"
				+ dbName);
		// checking if details are valid by attempting a query
		return executeQueryString("1", "SELECT 1") != null ? true : false;
	}

	// Handles connecting and reading content from the database (SELECT)
	public static ArrayList<String> executeQueryStrings(String table, String sql) {
		ArrayList<String> retArray = new ArrayList<String>();
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(sql)) {
					while (rs.next()) {
						retArray.add(rs.getString(table));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArray;
	}

	public static String executeQueryString(String column, String sql) {
		String retString = null;
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(sql)) {
					if (rs.next()) {
						retString = rs.getString(column);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retString;
	}

	public static int executeQueryInt(String table, String sql) {
		int retVal = 0;
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(sql)) {
					if (rs.next()) {
						retVal = rs.getInt(table);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}
	
	public static ArrayList<Date> executeQueryDates(String table, String sql, Boolean isTime) {
		ArrayList<Date> retArray = new ArrayList<Date>();
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(sql)) {
					while (rs.next()) {
						if(isTime){
							retArray.add(rs.getTime(table));
						}else{
							retArray.add(rs.getDate(table));
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArray;
	}
	
	public static ArrayList<Boolean> executeQueryBools(String table, String sql) {
		ArrayList<Boolean> retArray = new ArrayList<Boolean>();
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(sql)) {
					while (rs.next()) {
						retArray.add(rs.getBoolean(table));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArray;
	}

	/**
	 * Handles connecting and altering database (DROP, INSERT, UPDATE, DELETE)
	 * Pass SQL statement and returns number of rows affected
	 *
	 * @param sql
	 * @return
	 */
	public static int executeUpdate(String sql) {
		int rowsAffected = 0;
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				rowsAffected = stmt.executeUpdate(sql);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}
}
