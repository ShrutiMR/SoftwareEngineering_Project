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
import routers.AssociationsRequestRouter;

/**
 *
 * @author mkonidala
 */
public class AssociationsTest {

    public AssociationsTest() {
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
    
    // test1() to test17() tests all types of GET requests in Associations
    
    @Test
    //Get Request with invalid type
    public void test1() {
        String url = "http://localhost:8001/associations/?type=randomType";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Get Request with valid type 'administrator'
    public void test2(){
        String url = "http://localhost:8001/associations/?type=administrator";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'all'
    public void test3(){
        String url = "http://localhost:8001/associations/?type=all";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'tag' and valid tag_id
    public void test4(){
        String url = "http://localhost:8001/associations/?type=tag&tag_id=5";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'tag' and without tag_id
    public void test5(){
        String url = "http://localhost:8001/associations/?type=tag";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'tag' and with invalid tag_id
    public void test6(){
        String url = "http://localhost:8001/associations/?type=tag";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'single' and with valid user_id
    public void test7(){
        String url = "http://localhost:8001/associations/?type=single&user_id=5";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'single' and with invalid user_id
    public void test8(){
        String url = "http://localhost:8001/associations/?type=single&user_id=abc";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'single' and with valid association_id
    public void test9(){
        String url = "http://localhost:8001/associations/?type=single&association_id=5";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'single' and with invalid association_id
    public void test10(){
        String url = "http://localhost:8001/associations/?type=single&association_id=abc";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'single' and with no association_id or user_id
    public void test11(){
        String url = "http://localhost:8001/associations/?type=single";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'newAssociations' and with valid user_id
    public void test12(){
        String url = "http://localhost:8001/associations/?type=newAssociations&user_id=5";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'newAssociations' and with invalid user_id
    public void test13(){
        String url = "http://localhost:8001/associations/?type=newAssociations&user_id=abc";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'newAssociations' and with no user_id parameter
    public void test14(){
        String url = "http://localhost:8001/associations/?type=newAssociations";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'oldAssociations' and with valid user_id
    public void test15(){
        String url = "http://localhost:8001/associations/?type=oldAssociations&user_id=5";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(true, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'oldAssociations' and with invalid user_id
    public void test16(){
        String url = "http://localhost:8001/associations/?type=oldAssociations&user_id=abc";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test 
    //Get Request with valid type 'oldAssociations' and with no user_id parameter
    public void test17(){
        String url = "http://localhost:8001/associations/?type=oldAssociations";
        JSONObject response = this.invokeGetMethod(url);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    
    // test18() to test26() tests all types of invalid POST requests in Associations
    
    @Test
    //Post approval request with invalid approval type and valid association_id
    public void test18(){
        String url = "http://localhost:8001/associations/?approval=abc&association_id=5";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Post approval request with valid approval type and invalid association_id
    public void test19(){
        String url = "http://localhost:8001/associations/?approval=Y&association_id=abc";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Post approval request with valid approval type and invalid association_id
    public void test20(){
        String url = "http://localhost:8001/associations/?approval=N&association_id=abc";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Post approval request with valid approval type and no association_id parameter
    public void test21(){
        String url = "http://localhost:8001/associations/?approval=N";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Post follow request with invalid follow type and valid association_id and user_id
    public void test22(){
        String url = "http://localhost:8001/associations/?follow=abc&association_id=5&user_id=5";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Post follow request with valid follow type and invalid association_id and valid user_id
    public void test23(){
        String url = "http://localhost:8001/associations/?follow=Y&association_id=abc&user_id=5";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Post follow request with valid follow type and valid association_id and invalid user_id
    public void test24(){
        String url = "http://localhost:8001/associations/?follow=N&association_id=15&user_id=abc";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Post follow request with valid follow type and no association_id and user_id parameter
    public void test25(){
        String url = "http://localhost:8001/associations/?follow=N";
        JSONObject response = this.invokePostMethod(url,null);
        assertEquals(false, (boolean) response.get("isSuccess"));
    }
    
    @Test
    //Invoke Post Method with missing mandatory attribute password
    public void test26(){
        String url = "http://localhost:8001/associations/?";
        HashMap data = new HashMap();
        
        data.put("user_name", "mkonidala");
        data.put("description", "Test Description");
        data.put("association_name", "Test Association");
        
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

            //Starting Http Server on port 8001
            server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            server.createContext("/associations", new AssociationsRequestRouter(c));
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
