/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */
package microservices;

/**
 *
 * @author mkonidala
 */
import java.sql.*;
import java.net.*;
import com.sun.net.httpserver.HttpServer;
import routers.EventsRequestRouter;

public class Events {

    public static void main(String args[]) {
        
        String url = "jdbc:mysql://127.0.0.1:3306/";
        String dbName = "EVENTBOARD";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String dbpassword = "12345678";
        
        try {
            //Connecting to the my-sql database
            Class.forName(driver);
            Connection c = DriverManager.getConnection(url + dbName, userName, dbpassword);
            
            //Starting Http Server on port 9002
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 9002), 0);
            server.createContext("/events", new EventsRequestRouter(c));
            server.start();
        } catch (Exception e) {
            //Logging the exception
            System.out.println(e.getMessage());
        }
    }
}
