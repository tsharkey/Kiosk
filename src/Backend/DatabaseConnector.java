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
 *         TODO: 
 *         - proper error handling 
 *         - createDatabase : create initial database using SQL queries 
 *         - delete database -> clear tables
 *         - PreparedStatement to prevent sql injection
 */
public class DatabaseConnector {

	/**
	 * Static variables with database connection details
	 */
	private static String user, password, url;
	private static MysqlDataSource dataSource;

	/**
	 * Use details to attempt to create a MysqlDataSource object that will be
	 * used to connect to the database.
	 *
	 * @param user
	 * @param password
	 * @param url
	 * @param dbName
	 * @return boolean whether details are valid
	 */
	public static boolean setDatabaseConnection(String user, String password,
			String url, String dbName) {
		DatabaseConnector.user = user;
		DatabaseConnector.password = password;
		DatabaseConnector.url = url;
		dataSource = new MysqlDataSource();
		dataSource.setUser(DatabaseConnector.user);
		dataSource.setPassword(DatabaseConnector.password);
		dataSource.setURL("jdbc:mysql://" + DatabaseConnector.url + "/"
				+ dbName);
		// checking if details are valid by attempting a query to database
		return executeQueryString("1", "SELECT 1") != null ? true : false;
	}

	/**
	 * Handles connecting and reading content from the database using query
	 * parameter string (SELECT). The ResultSet should only have one specified
	 * column that is parsed and stored in return.
	 * 
	 * @param column
	 * @param query
	 * @return ArrayList of Strings parsed from ResultSet
	 */
	public static ArrayList<String> executeQueryStrings(String column,
			String query) {
		ArrayList<String> retArray = new ArrayList<String>();
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(query)) {
					while (rs.next()) {
						retArray.add(rs.getString(column));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArray;
	}

	/**
	 * Handles connecting and reading content from the database using query
	 * parameter string (SELECT). The ResultSet should only have one specified
	 * column with one String value that is returned.
	 * 
	 * @param column
	 * @param query
	 * @return String from ResultSet
	 */
	public static String executeQueryString(String column, String query) {
		String retString = null;
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(query)) {
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

	/**
	 * Handles connecting and reading content from the database using query
	 * parameter string (SELECT). The ResultSet should only have one specified
	 * column with one int value that is returned.
	 * 
	 * @param column
	 * @param query
	 * @return String from ResultSet
	 */
	public static int executeQueryInt(String column, String query) {
		int retVal = 0;
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(query)) {
					if (rs.next()) {
						retVal = rs.getInt(column);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retVal;
	}

	/**
	 * Handles connecting and reading content from the database using query
	 * parameter string (SELECT). The ResultSet should only have one specified
	 * column with multiple Date or Time values depending on Boolean parameter.
	 * 
	 * @param column
	 * @param query
	 * @param isTime
	 *            specifies whether ResultSet will use SQL Time or Date
	 * @return ArrayList of Dates from ResultSet
	 */
	public static ArrayList<Date> executeQueryDates(String column,
			String query, Boolean isTime) {
		ArrayList<Date> retArray = new ArrayList<Date>();
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(query)) {
					while (rs.next()) {
						if (isTime) {
							retArray.add(rs.getTime(column));
						} else {
							retArray.add(rs.getDate(column));
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArray;
	}

	/**
	 * Handles connecting and reading content from the database using query
	 * parameter string (SELECT). The ResultSet should only have one specified
	 * column with multiple Boolean values that are returned.
	 * 
	 * @param column
	 * @param query
	 * @return ArrayList of Booleans from ResultSet
	 */
	public static ArrayList<Boolean> executeQueryBools(String column,
			String query) {
		ArrayList<Boolean> retArray = new ArrayList<Boolean>();
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(query)) {
					while (rs.next()) {
						retArray.add(rs.getBoolean(column));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArray;
	}

	/**
	 * Handles connecting and reading content from the database using query
	 * parameter string (SELECT). The ResultSet will contain multiple columns
	 * that gets parsed into a VisitData object and multiple rows that get
	 * stored in an ArrayList.
	 * 
	 * @param column
	 * @param query
	 * @return ArrayList of VisitData objects
	 */
	public static ArrayList<VisitData> executeQueryVisitData(String query) {
		ArrayList<VisitData> retArray = new ArrayList<VisitData>();
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(query)) {
					while (rs.next()) {
						VisitData temp = new VisitData(rs.getDate("visitDate"),
								rs.getTime("visitTime"),
								rs.getString("reason"),
								rs.getBoolean("followUp"),
								rs.getString("specialist"),
								rs.getString("location"),
								rs.getString("fName"), rs.getString("lName"),
								rs.getString("email"), rs.getString("phone"),
								rs.getString("role"));
						retArray.add(temp);
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retArray;
	}

	/**
	 * Handles connecting and reading content from the database using query
	 * parameter string (SELECT). The ResultSet will contain multiple columns
	 * that gets parsed into a UserData object and multiple rows that get stored
	 * in an ArrayList.
	 * 
	 * @param column
	 * @param query
	 * @return ArrayList of UserData objects
	 */
	public static ArrayList<UserData> executeQueryUserData(String query) {
		ArrayList<UserData> retArray = new ArrayList<UserData>();
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				try (ResultSet rs = stmt.executeQuery(query)) {
					while (rs.next()) {
						UserData temp = new UserData(rs.getString("fName"),
								rs.getString("lName"), rs.getString("email"),
								rs.getString("phone"), rs.getString("role"));
						retArray.add(temp);
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
	 * using SQL statement parameter.
	 *
	 * @param statement
	 * @return number of rows affected
	 */
	public static int executeUpdate(String statement) {
		int rowsAffected = 0;
		try (Connection conn = dataSource.getConnection()) {
			try (Statement stmt = conn.createStatement()) {
				rowsAffected = stmt.executeUpdate(statement);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}
}