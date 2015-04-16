package Backend;

import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

/**
 * This class handles setting up connections to the database and updating or
 * returning information.
 *
 * @author Sean Johnston, Brendan Casey
 *
 *         TODO: proper error handling
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
	 * Initialize and create new database.
	 * 
	 */
	public static void initDatabase() {
		// wipe existing database
		dropTables();
		// create new ADMIN table
		executeUpdate("CREATE TABLE ADMIN ("
				+ "user varchar(100) NOT NULL COMMENT 'username', "
				+ "hash varchar(105) NOT NULL COMMENT 'password hash', "
				+ "PRIMARY KEY (user), UNIQUE KEY user_UNIQUE (user))");
		// create new USER table
		executeUpdate("CREATE TABLE USER ("
				+ "fName varchar(50) NOT NULL COMMENT 'First name', "
				+ "lName varchar(50) NOT NULL COMMENT 'Last name', "
				+ "email varchar(50) NOT NULL, "
				+ "phone varchar(20) NOT NULL, "
				+ "role varchar(50) NOT NULL, "
				+ "PRIMARY KEY (email, phone), "
				+ "UNIQUE KEY email_UNIQUE (email), "
				+ "UNIQUE KEY phone_UNIQUE (phone))");
		// create new SPECIALIST table
		executeUpdate("CREATE TABLE SPECIALIST ("
				+ "photo varchar(100) DEFAULT 'search.jpg', "
				+ "hash varchar(105) NOT NULL, "
				+ "email varchar(50) NOT NULL, "
				+ "PRIMARY KEY (email), "
				+ "CONSTRAINT spec_constraint FOREIGN KEY (email) "
				+ "REFERENCES USER (email) ON DELETE CASCADE ON UPDATE CASCADE)");
		// create new VISITS table
		executeUpdate("CREATE TABLE VISITS ("
				+ "visitDate date DEFAULT NULL, "
				+ "visitTime time DEFAULT NULL, "
				+ "reason varchar(100) DEFAULT NULL, "
				+ "followUp bit(1) DEFAULT b'0', "
				+ "email varchar(50) NOT NULL, "
				+ "ID int(10) unsigned NOT NULL AUTO_INCREMENT, "
				+ "specialist varchar(50) NOT NULL, "
				+ "location varchar(20) NOT NULL, "
				+ "PRIMARY KEY (ID), "
				+ "KEY email_idx (email), "
				+ "CONSTRAINT visits_constraint FOREIGN KEY (email) "
				+ "REFERENCES USER (email) ON DELETE CASCADE ON UPDATE CASCADE)");
		// create VISITS data and time TRIGGER
		executeUpdate("CREATE TRIGGER set_date_time BEFORE INSERT ON VISITS "
				+ "FOR EACH ROW BEGIN "
				+ "SET NEW.visitDate = CURRENT_DATE(); "
				+ "SET NEW.visitTime = CURRENT_TIME(); END");
	}

	/**
	 * Drop all tables from database in order to prevent foreign key problems.
	 * 
	 */
	private static void dropTables() {
		executeUpdate("DROP TABLE IF EXISTS ADMIN");
		executeUpdate("DROP TABLE IF EXISTS SPECIALIST");
		executeUpdate("DROP TABLE IF EXISTS VISITS");
		executeUpdate("DROP TABLE IF EXISTS USER");
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
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				try (ResultSet rs = stmt.executeQuery()) {
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
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				try (ResultSet rs = stmt.executeQuery()) {
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
	 * Overload of executeQueryString with one string parameter
	 * 
	 * @param column
	 * @param query
	 * @param input
	 * @return
	 */
	public static String executeQueryString(String column, String query,
			String input) {
		String retString = null;
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, input);
				try (ResultSet rs = stmt.executeQuery()) {
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
	 * Overload of executeQueryString with two string parameters
	 * 
	 * @param column
	 * @param query
	 * @param input
	 * @return
	 */
	public static String executeQueryString(String column, String query,
			String input1, String input2) {
		String retString = null;
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, input1);
				stmt.setString(2, input2);
				try (ResultSet rs = stmt.executeQuery()) {
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
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				try (ResultSet rs = stmt.executeQuery()) {
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
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				try (ResultSet rs = stmt.executeQuery()) {
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
	 * Overload of executeQueryVisitData with one string parameter
	 * 
	 * @param query
	 * @param input
	 * @return
	 */
	public static ArrayList<VisitData> executeQueryVisitData(String query,
			String input) {
		ArrayList<VisitData> retArray = new ArrayList<VisitData>();
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, input);
				try (ResultSet rs = stmt.executeQuery()) {
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
	 * Overload of executeQueryVisitData with one boolean parameter
	 * 
	 * @param query
	 * @param input
	 * @return
	 */
	public static ArrayList<VisitData> executeQueryVisitData(String query,
			boolean input) {
		ArrayList<VisitData> retArray = new ArrayList<VisitData>();
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setBoolean(1, input);
				try (ResultSet rs = stmt.executeQuery()) {
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
	 * Overload of executeQueryVisitData with two string parameters
	 * 
	 * @param query
	 * @param input1
	 * @param input2
	 * @return
	 */
	public static ArrayList<VisitData> executeQueryVisitData(String query,
			String input1, String input2) {
		ArrayList<VisitData> retArray = new ArrayList<VisitData>();
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, input1);
				stmt.setString(2, input2);
				try (ResultSet rs = stmt.executeQuery()) {
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
	 * Overload of executeQueryVisitData with three string parameters
	 * 
	 * @param query
	 * @param input1
	 * @param input2
	 * @param input3
	 * @return
	 */
	public static ArrayList<VisitData> executeQueryVisitData(String query,
			String input1, String input2, String input3) {
		ArrayList<VisitData> retArray = new ArrayList<VisitData>();
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, input1);
				stmt.setString(2, input2);
				stmt.setString(3, input3);
				try (ResultSet rs = stmt.executeQuery()) {
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
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				try (ResultSet rs = stmt.executeQuery()) {
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
	 * Overload of executeQueryUserData with three string parameters
	 * 
	 * @param query
	 * @param input1
	 * @param input2
	 * @param input3
	 * @return
	 */
	public static ArrayList<UserData> executeQueryUserData(String query,
			String input1, String input2, String input3) {
		ArrayList<UserData> retArray = new ArrayList<UserData>();
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(query)) {
				stmt.setString(1, input1);
				stmt.setString(2, input2);
				stmt.setString(3, input3);
				try (ResultSet rs = stmt.executeQuery()) {
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
			try (PreparedStatement stmt = conn.prepareStatement(statement)) {
				rowsAffected = stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

	/**
	 * Overload of executeUpdate with one string parameter.
	 * 
	 * @param statement
	 * @param input
	 * @return
	 */
	public static int executeUpdate(String statement, String input) {
		int rowsAffected = 0;
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(statement)) {
				stmt.setString(1, input);
				rowsAffected = stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

	/**
	 * Overload of executeUpdate with two string parameters.
	 * 
	 * @param statement
	 * @param input1
	 * @param input2
	 * @return
	 */
	public static int executeUpdate(String statement, String input1,
			String input2) {
		int rowsAffected = 0;
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(statement)) {
				stmt.setString(1, input1);
				stmt.setString(2, input2);
				rowsAffected = stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

	/**
	 * Overload of executeUpdate with three string parameters.
	 * 
	 * @param statement
	 * @param input1
	 * @param input2
	 * @param input3
	 * @return
	 */
	public static int executeUpdate(String statement, String input1,
			String input2, String input3) {
		int rowsAffected = 0;
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(statement)) {
				stmt.setString(1, input1);
				stmt.setString(2, input2);
				stmt.setString(3, input3);
				rowsAffected = stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

	/**
	 * Overload of executeUpdate with five string parameters.
	 * 
	 * @param statement
	 * @param input1
	 * @param input2
	 * @param input3
	 * @param input4
	 * @param input5
	 * @return
	 */
	public static int executeUpdate(String statement, String input1,
			String input2, String input3, String input4, String input5) {
		int rowsAffected = 0;
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(statement)) {
				stmt.setString(1, input1);
				stmt.setString(2, input2);
				stmt.setString(3, input3);
				stmt.setString(4, input4);
				stmt.setString(5, input5);
				rowsAffected = stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

	/**
	 * Overload of executeUpdate with six string parameters.
	 * 
	 * @param statement
	 * @param input1
	 * @param input2
	 * @param input3
	 * @param input4
	 * @param input5
	 * @param input6
	 * @return
	 */
	public static int executeUpdate(String statement, String input1,
			String input2, String input3, String input4, String input5,
			String input6) {
		int rowsAffected = 0;
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(statement)) {
				stmt.setString(1, input1);
				stmt.setString(2, input2);
				stmt.setString(3, input3);
				stmt.setString(4, input4);
				stmt.setString(5, input5);
				stmt.setString(5, input6);
				rowsAffected = stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}

	/**
	 * Overload of executeUpdate with four string parameters and one boolean.
	 * 
	 * @param statement
	 * @param input1
	 * @param input2
	 *            boolean
	 * @param input3
	 * @param input4
	 * @param input5
	 * @return
	 */
	public static int executeUpdate(String statement, String input1,
			boolean input2, String input3, String input4, String input5) {
		int rowsAffected = 0;
		try (Connection conn = dataSource.getConnection()) {
			try (PreparedStatement stmt = conn.prepareStatement(statement)) {
				stmt.setString(1, input1);
				stmt.setBoolean(2, input2);
				stmt.setString(3, input3);
				stmt.setString(4, input4);
				stmt.setString(5, input5);
				rowsAffected = stmt.executeUpdate();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rowsAffected;
	}
}
