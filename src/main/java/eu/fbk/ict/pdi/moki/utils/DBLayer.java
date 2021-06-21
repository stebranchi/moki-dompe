package eu.fbk.ict.pdi.moki.utils;

	import java.sql.*;

	public class DBLayer 
	{
		static Connection DbConn;
		
		private DBLayer(){}
		
		public static void connect(String serverName, String dbName, String userName, String password)
		{
			try
	    {
	      /**
	       * Inizializza la libreria per la connessione con MySQL
	       */
	      Class.forName("com.mysql.jdbc.Driver").newInstance();

	      DbConn = DriverManager.getConnection(
	          //"jdbc:mysql://localhost:3306/yourair?user=root&password=root");
	          "jdbc:mysql://" + serverName + ":3306/" + dbName + "?zeroDateTimeBehavior=convertToNull&useSSL=false&user=" + userName + "&password=" + password);
	 
	    }
	    catch(SQLException E)
	    {
	      System.out.println("SQLException: " + E.getMessage());
	      System.out.println("SQLState: " + E.getSQLState());
	      System.out.println("VendorError: " + E.getErrorCode());

	    }
	    catch(Exception e)
	    {
	      e.printStackTrace();
	    }
		}
		
		public static void close()
		{
			try {
				DbConn.close();
			} catch (Exception e) {
				System.out.println("Closing connection...");
			}
		}
		
		
		/**
	   * Creates a new statement to perform a query
	   * @return
	   */
	  private static PreparedStatement createStatement(String Query)
	  {
	    try
	    {
	      return DbConn.prepareStatement(Query);
	    }
	    catch (SQLException e)
	    {
	      handleSQLException(e, "");
	      return null;
	    }
	    
	  }

	  
	  
	  
	  /**
	   * Handles a SQL exception caused by query executions
	   * @param sqle
	   * @param query
	   */
	  private static void handleSQLException(SQLException e, String Query)
	  {
	  	System.out.println("\nSQL Exception:\n");
	    while (e != null)
	    {
	      System.out.println("Message:   " + e.getMessage());
	      System.out.println("SQLState:  " + e.getSQLState());
	      System.out.println("ErrorCode: " + e.getErrorCode());
	      e = e.getNextException();
	      System.out.println("");
	    }

	    System.out.println("Query:     " + Query);
	  }

	  
	  
	  
	  
	  
	  
	  /**
	   * Performs a query
	   * @param query
	   * @return
	   */
		public static ResultSet SQL(String Query, String... values) {
			PreparedStatement S = createStatement(Query);
			int i=1;
			
			//insert values in the query
			for (String value : values) {
				try {
					S.setString(i, value);
				} catch (SQLException e) {
					return null;
				}
				i++;
			}
			
			
			try {
				if (Query.startsWith("INSERT") || Query.startsWith("UPDATE") || Query.startsWith("DELETE")) {
					S.executeUpdate();
					S.close();
					return null;
				}
				ResultSet R = S.executeQuery();
				return R;
			}
			catch(SQLException e) {
				handleSQLException(e, Query);
				return null;
			}
		}
				
		
}

