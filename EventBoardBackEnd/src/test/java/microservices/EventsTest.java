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
import routers.EventsRequestRouter;

/**
 *
 * @author mkonidala
 */
public class EventsTest {
    
    public EventsTest() {
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
    
    // test1() to test13() tests all types of GET requests in Associations
    
    @Test
    //Get Request with invalid type
    public void test1() {
        String url = "http://localhost:8002/events/?type=randomType";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Get Request with valid type 'upcoming' and valid user_id
    public void test2(){
        String url = "http://localhost:8002/events/?type=upcoming&user_id=15";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'upcoming' and invalid user_id
    public void test3(){
        String url = "http://localhost:8002/events/?type=upcoming&user_id=abc";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'past' and valid user_id
    public void test4(){
        String url = "http://localhost:8002/events/?type=past&user_id=15";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'past' and invalid user_id
    public void test5(){
        String url = "http://localhost:8002/events/?type=past&user_id=abc";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Get Request with valid type 'associationUpcoming' and valid association_id
    public void test6(){
        String url = "http://localhost:8002/events/?type=associationUpcoming&association_id=15";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'associationUpcoming' and invalid association_id
    public void test7(){
        String url = "http://localhost:8002/events/?type=associationUpcoming&association_id=abc";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'associationPast' and valid association_id
    public void test8(){
        String url = "http://localhost:8002/events/?type=associationPast&association_id=15";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'associationPast' and invalid association_id
    public void test9(){
        String url = "http://localhost:8002/events/?type=associationPast&association_id=abc";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'feed' and invalid user_id
    public void test10(){
        String url = "http://localhost:8002/events/?type=feed&user_id=abc";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'feed' and valid user_id
    public void test11(){
        String url = "http://localhost:8002/events/?type=feed&user_id=15";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'all' and invalid user_id
    public void test12(){
        String url = "http://localhost:8002/events/?type=all&user_id=abc";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'all' and valid user_id
    public void test13(){
        String url = "http://localhost:8002/events/?type=all&user_id=15";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    // test14() to test18() tests all types of invalid POST requests in Associations
    
    @Test
    //Post follow request with invalid follow type and valid event_id and user_id
    public void test14(){
        String url = "http://localhost:8002/events/?follow=abc&event_id=5&user_id=5";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Post follow request with valid follow type and invalid event_id and valid user_id
    public void test15(){
        String url = "http://localhost:8002/events/?follow=Y&event_id=abc&user_id=5";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Post follow request with valid follow type and valid event_id and invalid user_id
    public void test16(){
        String url = "http://localhost:8002/events/?follow=N&event_id=15&user_id=abc";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Post follow request with valid follow type and no event_id and user_id parameter
    public void test17(){
        String url = "http://localhost:8002/events/?follow=N";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Invoke Post Method with missing mandatory attributes start_time and end_time
    public void test18(){
        String url = "http://localhost:8002/events/?";
        HashMap data = new HashMap();
        
        data.put("name", "Test event");
        data.put("description", "Event Description");
        data.put("association_id", "Test Association");
        
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
            
            //Starting Http Server on port 8002
            server = HttpServer.create(new InetSocketAddress("localhost", 8002), 0);
            server.createContext("/events", new EventsRequestRouter(c));
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
