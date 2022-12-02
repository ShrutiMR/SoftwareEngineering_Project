/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package microservices;

/**
 *
 * @author mkonidala
 */
import routers.UsersRequestHandler;
import java.sql.*;
import java.net.*;
import java.io.*;
import com.sun.net.httpserver.HttpServer;

public class EventBoard {


public static void main(String args[]) {
String url = "jdbc:mysql://127.0.0.1:3306/";
String dbName = "EVENTBOARD";
String driver = "com.mysql.cj.jdbc.Driver";
String userName = "root";
String dbpassword = "muneer";
try {
// loading driver

Class.forName(driver);

// Connection set up with database named as user
Connection c = DriverManager.getConnection(url+dbName,userName,dbpassword);
// Creating statement for the connection to use sql queries
Statement st = c.createStatement();
// Executing sql query using the created statement over the table user_details located in the database pointing by the dsn
ResultSet rs = st.executeQuery("SHOW TABLES");
// Accessing the result of query execution
while(rs.next())
{
String username = rs.getString(1);
System.out.println(username);
}
// Closing the statement and connection
st.close();


            
         HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 9000), 0);
         server.createContext("/products", new  UsersRequestHandler(c));
         server.start();
         System.out.println("Hi"); 
}
        
        catch(Exception i)
        {
            System.out.println(i);
        }

}
}
