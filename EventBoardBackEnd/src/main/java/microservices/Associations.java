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
import routers.AssociationsRequestHandler;

public class Associations {

    public static void main(String args[]) {
        String url = "jdbc:mysql://127.0.0.1:3306/";
        String dbName = "EVENTBOARD";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String dbpassword = "12345678";
        try {
            Class.forName(driver);
            Connection c = DriverManager.getConnection(url + dbName, userName, dbpassword);
            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 9001), 0);
            server.createContext("/associations", new AssociationsRequestHandler(c));
            server.start();
        } catch (Exception i) {
            System.out.println(i);
        }

    }
}