package Shopping;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;

import java.sql.*;
import java.util.ArrayList;

public class DBQuery {

	private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
	private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String DB_USER = "scott";
	private static final String DB_PASSWORD = "tiger";

	public static void main(String[] argv) throws SQLException {
	}

	public Connection getDBConnection() throws SQLException {

		
		Connection dbConnection = null;

		try {

			Class.forName(DB_DRIVER);

		} catch (ClassNotFoundException e) {

			System.out.println(e.getMessage());

		}



		dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
		return dbConnection;

	}

	public void insertRecordIntoTable(String username, String password) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO users" + "(user_id, username, password, user_type_id) VALUES"
				+ "(ID_SEQ.NEXTVAL,?,?, 2)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			// execute insert SQL statement
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into DBUSER table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	public boolean exist(String username, String password) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean exist = false;

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection
					.prepareStatement("SELECT USERNAME, PASSWORD from users WHERE USERNAME = ? AND PASSWORD = ?");
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();

			exist = resultSet.next();
			System.out.println("Account Exist : " + exist);
			;

		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException ignore) {
				}
			if (preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (SQLException ignore) {
				}
			if (dbConnection != null)
				try {
					dbConnection.close();
				} catch (SQLException ignore) {
				}
		}

		return exist;
	}

	public boolean isAdmin(String username) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean isAdmin = false;

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement("SELECT user_type_id from users WHERE USERNAME = ?");
			preparedStatement.setString(1, username);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next())
				isAdmin = resultSet.getString("user_type_id").equals("1");
			System.out.println("user " + username + " is admin : " + isAdmin);

		} catch (Exception e) {
			System.out.println("isAdmin error : " + e.getMessage());
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException ignore) {
				}
			if (preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (SQLException ignore) {
				}
			if (dbConnection != null)
				try {
					dbConnection.close();
				} catch (SQLException ignore) {
				}
		}

		return isAdmin;
	}

	public String listOfItem() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		String data = "";

		String selectTableSQL = "SELECT NAME, item_id from ITEM";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);
			Person row = new Person();
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {
				row.setItemName(rs.getString("NAME"));
				String name = rs.getString("NAME");

				data += "<tr>" + " <td>" + name + "</td>" + " <td><a href='adminItems.jsp?item_id="
						+ rs.getString("item_id") + "'>Get Details</a></td>" + "</tr>";

				System.out.println(row.getItemName());

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return data;
	}
	
	
	public String listOfOrders() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		String data = "";

		String selectTableSQL = "SELECT order_id, user_id, username from orders INNER JOIN users USING(user_id)";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {
				//row.setItemName(rs.getString("NAME"));

				data += "<tr>" + " <td>Order #" + rs.getString("order_id") + " for user " + rs.getString("username") +"</td>" + " <td><a href='viewOrderInformation.jsp?order_id="
						+ rs.getString("order_id") + "'>Get Details</a></td>" + "</tr>";
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return data;
	}
	
	
	public String listOfOrders(String user_id) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement statement = null;
		String data = "";

		String selectTableSQL = "SELECT order_id, user_id, username from orders INNER JOIN users USING(user_id) WHERE user_id=?";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.prepareStatement(selectTableSQL);
			statement.setString(1, user_id);
			//System.out.println(selectTableSQL);
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				//row.setItemName(rs.getString("NAME"));

				data += "<tr>" + " <td>Order #" + rs.getString("order_id") + " for user " + rs.getString("username") +"</td>" + " <td><a href='viewOrderInformation.jsp?order_id="
						+ rs.getString("order_id") + "'>Get Details</a></td>" + "</tr>";
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return data;
	}
	
	
	
	public String listOfAccounts() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;
		String data = "";

		String selectTableSQL = "SELECT USERNAME, user_id from users WHERE user_type_id=2";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			
			System.out.println(selectTableSQL);
			Person row = new Person();
			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {
				String name = rs.getString("USERNAME");

				data += "<tr>" + " <td>" + name + "</td>" + " <td><a href='viewAccountInformation.jsp?user_id="
						+ rs.getString("user_id") + "'>Get Details</a></td>" + "</tr>";

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return data;
	}
	
	public Person getPerson(int user_id)throws SQLException {
		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		boolean isAdmin = false;
		
		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement("SELECT * FROM users INNER JOIN address USING(user_id) WHERE user_id=?");
			preparedStatement.setInt(1, user_id);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next())
			{
				Person person = new Person();
				person.setId(resultSet.getInt("user_id"));
				person.setFirstName(resultSet.getString("first_name"));
				person.setLastName(resultSet.getString("last_name"));
				person.setCountry(resultSet.getString("country"));
				person.setUsername(resultSet.getString("username"));
				person.setProvince(resultSet.getString("province"));
				person.setPostcode(resultSet.getString("postal_code"));
				person.setStreet(resultSet.getString("street"));
				return person;
			}

		} catch (Exception e) {
			System.out.println("getPerson error : " + e.getMessage());
		} finally {
			if (resultSet != null)
				try {
					resultSet.close();
				} catch (SQLException ignore) {
				}
			if (preparedStatement != null)
				try {
					preparedStatement.close();
				} catch (SQLException ignore) {
				}
			if (dbConnection != null)
				try {
					dbConnection.close();
				} catch (SQLException ignore) {
				}
		}
		return null;
	}

	public void selectRegisteredAccounts() throws SQLException {
		Connection dbConnection = null;
		Statement statement = null;

		String selectTableSQL = "SELECT USERNAME, PASSWORD from users";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			Person row = new Person();
			while (rs.next()) {
				row.setUsername(rs.getString("USERNAME"));
				row.setPassword(rs.getString("PASSWORD"));
				System.out.println(row.getUsername());
				System.out.println(row.getPassword());

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	public void selectRecordsFromDbUserTable() throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;

		String selectTableSQL = "SELECT FIRSTNAME, LASTNAME from DBUSER";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);

			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				String firstname = rs.getString("FIRSTNAME");
				String lastname = rs.getString("LASTNAME");

				System.out.println("First name : " + firstname);
				System.out.println("Last name : " + lastname);

			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	public String displayList() throws SQLException {
		ArrayList<String> al = new ArrayList<String>();
		Connection dbConnection = null;
		Statement statement = null;
		String data = "";

		String selectTableSQL = "SELECT FIRSTNAME, LASTNAME from DBUSER";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();

			System.out.println(selectTableSQL);

			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);

			while (rs.next()) {

				al.add(rs.getString("FIRSTNAME") + " " + rs.getString("LASTNAME"));

			}
			for (String display : al) {
				data += "<tr> <td>" + display + " " + "</td> </tr>";
			}

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return data;
	}

	public Item getItem(int item_id) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement statement = null;

		
		String selectTableSQL = "select name, price, item_description, quantity from item where item_id = ?";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.prepareStatement(selectTableSQL);
			statement.setInt(1, item_id);
			ResultSet resultSet = statement.executeQuery();

			
			if (resultSet.next()) {
				Item item = new Item();
				item.setName(resultSet.getString("name"));
				item.setDescription(resultSet.getString("item_description"));
				item.setPrice(resultSet.getDouble("price"));
				item.setQuantity(resultSet.getInt("quantity"));
				return item;
			} else {
				return null;
			}

		} catch (SQLException e) {

			System.out.println("getItem : ");
			e.printStackTrace();

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return null;
	}
	
	public String get_user_id(String username) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement statement = null;
		String user_id = "";

		String selectTableSQL = "select user_id from users where username=?";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.prepareStatement(selectTableSQL);
			//ResultSet rs = statement.executeQuery(selectTableSQL);
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();	
			System.out.println("db query: " + user_id);
			resultSet.next();
			return Integer.toString(resultSet.getInt("user_id"));

		} catch (SQLException e) {

			System.out.println("getItem : ");
			e.printStackTrace();

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return user_id;
	}

	
	
	///did this last
	public Item userId(int user_id) throws SQLException {
		Connection dbConnection = null;
		PreparedStatement statement = null;

		String selectTableSQL = "select user_id, username, item_description, quantity from item where user_id = ?";
		try {
			dbConnection = getDBConnection();
			statement = dbConnection.prepareStatement(selectTableSQL);
			statement.setInt(1, user_id);
			ResultSet resultSet = statement.executeQuery();

			if (resultSet.next()) {
				Item item = new Item();
				item.setName(resultSet.getString("name"));
				item.setDescription(resultSet.getString("item_description"));
				item.setPrice(resultSet.getDouble("price"));
				item.setQuantity(resultSet.getInt("quantity"));
				return item;
			} else {
				return null;
			}

		} catch (SQLException e) {

			System.out.println("getItem : ");
			e.printStackTrace();

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return null;
	}

	public void deleteItem(int item_id) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String deleteTableSQL = "delete from item where item_id=?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(deleteTableSQL);

			// execute insert SQL statement
			preparedStatement.setInt(1, item_id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}

	public Item addItem(String name, double price, String description, int quantity) throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String insertTableSQL = "INSERT INTO item(item_id,name,price,item_description,quantity) VALUES"
				+ "(ID_SEQ.NEXTVAL,?,?,?,?)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			// execute insert SQL statement
			//preparedStatement.setInt(1, item_id);
			preparedStatement.setString(1, name);
			preparedStatement.setDouble(2, price);
			preparedStatement.setString(3, description);
			preparedStatement.setInt(4, quantity);

			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into Item table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return null;
     }

	public void updateItem(int item_id, String name, double price, String description, int quantity)
			throws SQLException {

		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

		String updateTableSQL = "update item set name=?, price=?, item_description=?, quantity=? where item_id=?";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(updateTableSQL);

			// execute insert SQL statement
			preparedStatement.setString(1, name);
			preparedStatement.setDouble(2, price);
			preparedStatement.setString(3, description);
			preparedStatement.setInt(4, quantity);
			preparedStatement.setInt(5, item_id);
			preparedStatement.executeUpdate();

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}

	}
	
	
	public int selectCart_ID(int user_id) throws SQLException {

		Connection dbConnection = null;
		Statement statement = null;
		int cart_id = 0;
		String selectTableSQL = "SELECT cart_id from cart where user_id=" + user_id + "";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();


			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
			
			while (rs.next())
			{
				cart_id = (rs.getInt("cart_id"));
					
			}

			
			
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return cart_id;
		

	}
	
	
	public String getItemName() throws SQLException
	{
		Connection dbConnection = null;
		Statement statement = null;
		String getItemName = "";
		
		String selectTableSQL = "SELECT * from item";

		try {
			dbConnection = getDBConnection();
			statement = dbConnection.createStatement();


			// execute select SQL stetement
			ResultSet rs = statement.executeQuery(selectTableSQL);
		
			while (rs.next())
			{				
				String itemID = (rs.getString("item_id"));
				String itemName = (rs.getString("name"));
				
				getItemName +="<tr> <td>" + "<a href=items.jsp?itemID=" + itemID + ">" + itemName + "</a>" + "</td>" +
				         "</tr>";
				
			}
			
			
			
			
			
		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (statement != null) {
				statement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}

		}
		return getItemName;
	}
	public void createCart(String user_id, int item_id, int quantity, String usertype) throws SQLException {


		Connection dbConnection = null;
		PreparedStatement preparedStatement = null;

	
		String insertTableSQL = "INSERT INTO cart"
				+ "(cart_id, "+ usertype + ", item_id, quantity) VALUES"
				+ "(ID_SEQ.NEXTVAL,?,?,?)";

		try {
			dbConnection = getDBConnection();
			preparedStatement = dbConnection.prepareStatement(insertTableSQL);

			// execute insert SQL statement
			preparedStatement.setString(1, user_id);
			preparedStatement.setInt(2, item_id);
			preparedStatement.setInt(3, quantity);
			preparedStatement.executeUpdate();

			System.out.println("Record is inserted into cart table!");

		} catch (SQLException e) {

			System.out.println(e.getMessage());

		} finally {

			if (preparedStatement != null) {
				preparedStatement.close();
			}

			if (dbConnection != null) {
				dbConnection.close();
			}
		}

		}
		
		public void updateCart(int quantity, String userid, int item_id, String usertype) throws SQLException {

			Connection dbConnection = null;
			PreparedStatement statement = null;
			
			String quantitySQL = "select quantity from cart where "+ usertype +"=? and item_id=?";
			
			
			String updateTableSQL = "UPDATE cart SET quantity=? where "+ usertype +"= ? and item_id=?";

			try {
				dbConnection = getDBConnection();
				
				statement = dbConnection.prepareStatement(quantitySQL);
				statement.setString(1, userid);
				statement.setInt(2, item_id);
				
				ResultSet rs = statement.executeQuery();
				
				rs.next();
				
				int prevQuantity = rs.getInt("quantity");
				
				statement.close();
				statement = dbConnection.prepareStatement(updateTableSQL);

				statement.setInt(1, prevQuantity + quantity);
				statement.setString(2, userid);
				statement.setInt(3, item_id);
				
				System.out.println(updateTableSQL);

				// execute update SQL stetement
				statement.execute();

				System.out.println("Record is updated to cart table!");

			} catch (SQLException e) {

				System.out.println("updateCart error: " + e.getMessage());

			} finally {

				if (statement != null) {
					statement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}

		}
		
		public void deleteCartItem(String userid, int item_id, String usertype) throws SQLException {

			Connection dbConnection = null;
			PreparedStatement statement = null;
			
			String updateTableSQL = "DELETE FROM cart WHERE item_id=? and "+ usertype +"=?";

			try {
				dbConnection = getDBConnection();
				statement = dbConnection.prepareStatement(updateTableSQL);

				statement.setInt(1, item_id);
				statement.setString(2, userid);
				

				// execute update SQL stetement
				statement.execute();


			} catch (SQLException e) {

				System.out.println("updateCart error: " + e.getMessage());

			} finally {

				if (statement != null) {
					statement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}

		}	
		
		public void transferCart(String annon_id, String user_id) throws SQLException {

			Connection dbConnection = null;
			PreparedStatement statement = null;
			
			String updateTableSQL = "UPDATE cart SET user_id=?, annon_id=NULL where annon_id= ?";

			try {
				dbConnection = getDBConnection();
				
				statement = dbConnection.prepareStatement(updateTableSQL);
				
				statement.setString(1, user_id);
				statement.setString(2, annon_id);
				
				statement.execute();
				
				//remove duplicates
				statement.close();
				
				System.out.println("annon_id : " + annon_id);
				updateTableSQL = "SELECT SUM(quantity) as newQuantity, item_id, COUNT(quantity) FROM cart WHERE user_id=? GROUP BY item_id HAVING count(item_id) > 1";
				statement = dbConnection.prepareStatement(updateTableSQL);
				statement.setString(1, user_id);
				
				ResultSet rs = statement.executeQuery();
				if(rs.next()){
					statement.close();
					
					int item_id = rs.getInt("item_id");
					int newQuantity = rs.getInt("newQuantity");
					
					updateTableSQL = "DELETE FROM cart WHERE user_id=? AND item_id=?";
					
					statement = dbConnection.prepareStatement(updateTableSQL);
					statement.setString(1, user_id);
					statement.setInt(2, item_id);
					statement.execute();
					
					this.createCart(user_id, item_id, newQuantity, "user_id");
				}


			} catch (SQLException e) {

				System.out.println("transferCart error: " + e.getMessage());

			} finally {

				if (statement != null) {
					statement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}

		}
		
		
		
		public boolean selectCart(int userid) throws SQLException {

			Connection dbConnection = null;
			Statement statement = null;

			String selectTableSQL = "SELECT cart_id, name FROM cart INNER JOIN item using (item_id) where user_id = " + userid + "";

			try {
				dbConnection = getDBConnection();
				statement = dbConnection.createStatement();


				// execute select SQL stetement
				ResultSet rs = statement.executeQuery(selectTableSQL);
				while(rs.next())
				{
					String itemName = (rs.getString("name"));
					if(itemName != null )
					{
						return true;
					}
					
				}
				
				
				
			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (statement != null) {
					statement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
			return false;
			

		}
		
		public boolean hasCartItem(String user_id, int item_id, String usertype) throws SQLException {

			Connection dbConnection = null;
			PreparedStatement statement = null;

			String selectTableSQL = "SELECT item_id FROM cart where " + usertype +" = ? and item_id = ?";

			try {
				dbConnection = getDBConnection();
				statement = dbConnection.prepareStatement(selectTableSQL);
				statement.setString(1, user_id);
				statement.setInt(2, item_id);
				// execute select SQL stetement
				ResultSet rs = statement.executeQuery();
				return rs.next();
				
				
				
			} catch (SQLException e) {

				System.out.println("hasCartItem error : " + e.getMessage());

			} finally {

				if (statement != null) {
					statement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
			return false;
			

		}
		
		public void insertIntoCart(int item_id) throws SQLException {


			Connection dbConnection = null;
			PreparedStatement preparedStatement = null;

			String insertTableSQL = "INSERT INTO cart"
					+ "(item_id) VALUES"
					+ "(?)";

			try {
				dbConnection = getDBConnection();
				preparedStatement = dbConnection.prepareStatement(insertTableSQL);

				// execute insert SQL statement
				preparedStatement.setInt(1, item_id);
				
				
				preparedStatement.executeUpdate();

				System.out.println("Record is inserted into DBUSER table!");

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}

		}
		public boolean selectItemId(int item_id) throws SQLException {

			Connection dbConnection = null;
			Statement statement = null;

			String selectTableSQL = "SELECT item_id, name FROM item INNER JOIN cart using (item_id)";

			try {
				dbConnection = getDBConnection();
				statement = dbConnection.createStatement();


				// execute select SQL stetement
				ResultSet rs = statement.executeQuery(selectTableSQL);
				
				while(rs.next())
				{
					String itemName = (rs.getString("name"));
					if(itemName != null )
					{
						return true;
					}
					
				}

				
				
			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (statement != null) {
					statement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
			return false;
			

		}
		public void insertIntoAddress(String fname, String lname, String city,String pcode, String province, String street, String country, int user_id) throws SQLException {


			Connection dbConnection = null;
			PreparedStatement preparedStatement = null;

			String insertTableSQL = "INSERT INTO address"
					+ "(address, first_name, last_name, city, postal_code,province,street,country, user_id) VALUES"
					+ "(ID_SEQ.NEXTVAL,?,?,?,?,?,?,?,?)";

			try {
				dbConnection = getDBConnection();
				preparedStatement = dbConnection.prepareStatement(insertTableSQL);

				// execute insert SQL statement
				preparedStatement.setString(1, fname);
				preparedStatement.setString(2, lname);
				preparedStatement.setString(3, city);
				preparedStatement.setString(4, pcode);
				preparedStatement.setString(5, province);
				preparedStatement.setString(6, street);
				preparedStatement.setString(7, country);
				preparedStatement.setInt(8, user_id);
			
				preparedStatement.executeUpdate();

				System.out.println("Record is inserted into address table!");

			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}

	}
		
		public String displayAddress(String user_id) throws SQLException
		{
			Connection dbConnection = null;
			PreparedStatement statement = null;
			String displayAddress = "";
			
			String selectTableSQL = "SELECT * from address WHERE user_id=?";

			try {
				dbConnection = getDBConnection();
				statement = dbConnection.prepareStatement(selectTableSQL);
				statement.setString(1, user_id);

				// execute select SQL stetement
				ResultSet rs = statement.executeQuery();
				//address, first_name, last_name, city, postal_code,province,street,country, user_id
				while (rs.next())
				{				
					String fname = (rs.getString("first_name"));
					String lastname = (rs.getString("last_name"));
					String city = (rs.getString("city"));
					String postal_code = (rs.getString("postal_code"));
					String province = (rs.getString("province"));
					String street = (rs.getString("street"));
					String country = (rs.getString("country"));
					//int user_id =  (rs.getInt("user_id"));
					String shipName = "Ship to this address";
					
					int address = rs.getInt("address");
					
					displayAddress +="<tr> <td>" + "<a href=payment.jsp?address=" + address + ">" + shipName + "</a>" + "</td> <td>" + city 
									 + "</td> <td>" + postal_code + "</td> <td>" + province +"</td><td>"+ street 
									 + "</td> <td>" + country+ "</tr>";
					
				}
				
				
				
				
				
			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (statement != null) {
					statement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
			return displayAddress;
		}
		
		public String displayPayments(String user_id) throws SQLException
		{
			Connection dbConnection = null;
			PreparedStatement statement = null;
			String displayAddress = "";
			
			String selectTableSQL = "SELECT * from payment_method WHERE user_id=?";

			try {
				dbConnection = getDBConnection();
				statement = dbConnection.prepareStatement(selectTableSQL);
				statement.setString(1, user_id);

				// execute select SQL stetement
				ResultSet rs = statement.executeQuery();
				//address, first_name, last_name, city, postal_code,province,street,country, user_id
				while (rs.next())
				{				
					int payment_id = (rs.getInt("payment_id"));
					String credit_type = (rs.getString("credit_type"));
					String card_number = (rs.getString("card_number"));
					String security_code = (rs.getString("security_code"));
					String expiration_date = (rs.getString("expiration_date"));
					//int address = (rs.getInt("address"));
					
					displayAddress +="<tr> <td>" + "<a href=completeOrder.jsp?payment=" + payment_id + ">Complete order</a>" + "</td> <td>" + credit_type 
									 + "</td> <td>" + card_number + "</td> <td>" + security_code +"</td><td>"+ expiration_date + "</td>";
					
				}
				
				
				
				
				
			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (statement != null) {
					statement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
			return displayAddress;
		}
		
		public int getItemQuantity(int item_id) throws SQLException
		{
			Connection dbConnection = null;
			PreparedStatement statement = null;
			
			String selectTableSQL = "SELECT quantity from item WHERE item_id=?";
			

			try {
				dbConnection = getDBConnection();
				statement = dbConnection.prepareStatement(selectTableSQL);
				statement.setInt(1, item_id);

				// execute select SQL stetement
				ResultSet rs = statement.executeQuery();
				//address, first_name, last_name, city, postal_code,province,street,country, user_id
				rs.next();
				return rs.getInt("quantity");
				
			} catch (SQLException e) {

				System.out.println(e.getMessage());

			} finally {

				if (statement != null) {
					statement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}
			return -1;
		}
	
		public void addPayment(String creditType, String creditCardNumber, String cardName, String security, String expiration, int address, String user_id) throws SQLException, ParseException {


			Connection dbConnection = null;
			PreparedStatement preparedStatement = null;

			String insertTableSQL = "INSERT INTO payment_method"
									+ "(PAYMENT_ID, USER_ID, CREDIT_TYPE, CARD_NUMBER, SECURITY_CODE, EXPIRATION_DATE, ADDRESS) VALUES"
									+ "(ID_SEQ.nextval, ?, ?, ?, ?, ?, ?)";

			try {
				dbConnection = getDBConnection();
				preparedStatement = dbConnection.prepareStatement(insertTableSQL);

				// execute insert SQL statement
				preparedStatement.setString(1, user_id);
				preparedStatement.setString(2, creditType);
				preparedStatement.setString(3, creditCardNumber);
				preparedStatement.setString(4, security);
				preparedStatement.setDate(5,new java.sql.Date(new SimpleDateFormat("dd/MM/YYYY").parse(expiration).getTime()));
				preparedStatement.setInt(6, address);
				
				
				preparedStatement.executeUpdate();

				System.out.println("Record is inserted into PAYMENT table!");

			} catch (SQLException e) {

				System.out.println("addPayment: " + e.getMessage());

			} finally {

				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}

		}
		
		public void addOrder(String user_id, int payment_id) throws SQLException, ParseException {


			Connection dbConnection = null;
			PreparedStatement preparedStatement = null;

			String addressSQL = "SELECT address FROM payment_method WHERE payment_id=?";
			
			String insertTableSQL = "INSERT INTO orders"
									+ "(ORDER_ID, USER_ID, ADDRESS, ORDER_DATE, PAYMENT_ID) VALUES"
									+ "(ID_SEQ.nextval, ?, ?, ?, ?)";
			
			String order_idSQL = "SELECT ID_SEQ.currval as order_id FROM dual";
			
			String cart_itemsSQL = "SELECT item_id, cart.quantity, price FROM cart INNER JOIN item USING(item_id) WHERE user_id=?";
			
			String order_detailSQL = "INSERT INTO order_detail"
									+"(ORDER_DETAIL_ID,ITEM_ID,ORDER_ID,QUANTITY,SALES_PRICE) VALUES"
									+"(ID_SEQ.nextval, ?, ?, ?, ?)";
			
			String quantitySQL = "UPDATE item SET quantity=quantity-? WHERE item_id=?";
			
			String cartSQL = "DELETE FROM cart WHERE user_id=?";

			try {
				
				dbConnection = getDBConnection();
				preparedStatement = dbConnection.prepareStatement(addressSQL);

				preparedStatement.setInt(1, payment_id);
				
				System.out.println("Getting address");
				ResultSet rs = preparedStatement.executeQuery();
				rs.next();
				
				int address = rs.getInt("address");
				preparedStatement.close();
				
				preparedStatement = dbConnection.prepareStatement(insertTableSQL);

				preparedStatement.setString(1, user_id);
				preparedStatement.setInt(2, address);
				preparedStatement.setDate(3, new Date(new java.util.Date().getTime()));
				preparedStatement.setInt(4, payment_id);
				
				System.out.println("Adding Order");
				preparedStatement.executeUpdate();
				preparedStatement.close();
				
				
				int order_id = 0;
				
				preparedStatement = dbConnection.prepareStatement(order_idSQL);
				
				System.out.println("Getting order_id");
				rs = preparedStatement.executeQuery();
				rs.next();
				order_id = rs.getInt("order_id");
				preparedStatement.close();
				
				preparedStatement = dbConnection.prepareStatement(cart_itemsSQL);
				preparedStatement.setString(1, user_id);
				
				System.out.println("Getting cart items");
				rs = preparedStatement.executeQuery();
				
				PreparedStatement preparedStatement2 = null;
				while(rs.next()){
					int item_id = rs.getInt("item_id");
					int quantity = rs.getInt("quantity");
					double price = rs.getDouble("price");
					System.out.println("Adding item : " + item_id + " to order") ;
					preparedStatement2 = dbConnection.prepareStatement(order_detailSQL);
					preparedStatement2.setInt(1, item_id);
					preparedStatement2.setInt(2, order_id);
					preparedStatement2.setInt(3, quantity);
					preparedStatement2.setDouble(4, price);
					preparedStatement2.executeUpdate();
					preparedStatement2.close();
					
					
					//remove quantity from inventory
					preparedStatement2 = dbConnection.prepareStatement(quantitySQL);
					preparedStatement2.setInt(1, quantity);
					preparedStatement2.setInt(2, item_id);
					preparedStatement2.executeUpdate();
					preparedStatement2.close();
				}
				
				preparedStatement.close();
				preparedStatement = dbConnection.prepareStatement(cartSQL);
				preparedStatement.setString(1, user_id);
				
				System.out.println("deleteing cart");
				preparedStatement.execute();
				

			} catch (SQLException e) {

				System.out.println("addOrder: " + e.getMessage());

			} finally {

				if (preparedStatement != null) {
					preparedStatement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}

		}
		public void updateCartQuantity(int quantity) throws SQLException {

			Connection dbConnection = null;
			PreparedStatement statement = null;
			
			//String quantitySQL = "select quantity from cart";
			
			
			String updateTableSQL = "UPDATE cart SET quantity=?";

			try {
				dbConnection = getDBConnection();
				
				statement = dbConnection.prepareStatement(updateTableSQL);
				statement.setInt(1, quantity);
				// execute update SQL stetement
				statement.execute();

				System.out.println("Record updated the quantity!");

			} catch (SQLException e) {

				System.out.println("updateCart error: " + e.getMessage());

			} finally {

				if (statement != null) {
					statement.close();
				}

				if (dbConnection != null) {
					dbConnection.close();
				}

			}

		}
}
