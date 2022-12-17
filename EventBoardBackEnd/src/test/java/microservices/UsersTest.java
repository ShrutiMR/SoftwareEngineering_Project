/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package microservices;

import com.sun.net.httpserver.HttpServer;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import routers.UsersRequestRouter;

/**
 *
 * @author mkonidala
 */
public class UsersTest {
    
    public UsersTest() {
    }
    
    private static HttpServer server;
    private static Connection c;

    // constructor to put ip address and port
    public JSONObject invokeGetMethod(String url) {
        JSONObject ret = null;
        try {
            URL url_obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) url_obj.openConnection();
            con.setRequestMethod("GET");
            String contentType = con.getHeaderField("Content-Type");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            ret = new JSONObject(content.toString());

            in.close();
            con.disconnect();
        } catch (Exception e) {

        }

        return ret;
    }

    public JSONObject invokePostMethod(String url_string, HashMap params) {
        JSONObject ret = null;
        try {
            URL url = new URL(url_string);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            JSONObject a = new JSONObject(params);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try ( OutputStream os = con.getOutputStream()) {
                byte[] input = a.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            String contentType = con.getHeaderField("Content-Type");
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            ret = new JSONObject(content.toString());
            in.close();
            con.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return ret;
    }
    
    //Test GET method passing invalid user_name and password that is not in the database
    @Test
    public void test1(){
       String url = "http://localhost:8000/users/?user_name=random&password=random";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess")); 
    }
    
    //Test GET method passing without user_name
    @Test
    public void test2(){
       String url = "http://localhost:8000/users/?password=random";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess")); 
    }
    
    //Test GET method passing without password
    @Test
    public void test3(){
       String url = "http://localhost:8000/users/?user_name=random";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess")); 
    }
    
    //Test GET method passing without user_name and password
    @Test
    public void test4(){
       String url = "http://localhost:8000/users/?";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess")); 
    }
    
    @Test
    //Invoke Post Method with missing mandatory attribute password
    public void test5(){
        String url = "http://localhost:8000/users/?";
        HashMap data = new HashMap();
        
        data.put("user_name", "test");
        data.put("password", "test");
        data.put("first_name", "First Name");
        data.put("email", "test@umass.edu");
        
        JSONObject response = this.invokePostMethod(url,data);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @BeforeClass
    public static void setUpClass() {
        //Creating a mock rest service for this instance
        String url = "jdbc:mysql://127.0.0.1:3306/";
        String dbName = "EVENTBOARD";
        String driver = "com.mysql.cj.jdbc.Driver";
        String userName = "root";
        String dbpassword = "12345678";
        try {
            //Connecting to the my-sql database
            Class.forName(driver);
            c = DriverManager.getConnection(url + dbName, userName, dbpassword);
            
            //Starting Http Server on port 8000
            server = HttpServer.create(new InetSocketAddress("localhost", 8000), 0);
            server.createContext("/users", new UsersRequestRouter(c));
            server.start();
        } catch (Exception e) {
            //Logging the exception
            System.out.println(e.getMessage());
        }
    }
    
    @AfterClass
    public static void tearDownClass() {
        //Closing the db connection and stopping the server of the mock service
        try {
            c.close();
            server.stop(0);
        } catch (Exception e) {

        }
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }
    
}
