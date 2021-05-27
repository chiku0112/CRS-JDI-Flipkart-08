/**
 * 
 */
package com.flipkart.utils;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBUtils {
	
	private static Connection connection = null;
	
	public static Connection getConnection() {
		
        if (connection != null)
            return connection;
        else {
            //System.out.println("here : ");
           // System.out.println(connection);
            try {
           // System.out.println("here 324: ");
            	Properties prop = new Properties();
           // System.out.println("here 2: ");
                InputStream inputStream = DBUtils.class.getClassLoader().getResourceAsStream("./config.properties");
            //System.out.println("here 3: ");
                prop.load(inputStream);
           // System.out.println("here 4: ");
                String driver = prop.getProperty("driver");
           // System.out.println("here I 5: ");
                String url = prop.getProperty("url");
           // System.out.println("here 6: ");
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");
                Class.forName(driver);
                connection = DriverManager.getConnection(url, user, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return connection;
        }

    }


}