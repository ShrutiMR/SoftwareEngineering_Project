package routers;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;
import java.io.*;
import org.json.JSONObject;


public class UsersRequestHandler implements HttpHandler{    

   
  public static Connection connection;
  public UsersRequestHandler(Connection c){
      connection = c;
  }
  
  @Override
  public void handle(HttpExchange httpExchange) throws IOException {
 System.out.println(httpExchange.getRemoteAddress());
    String requestParamValue=null; 
    if("GET".equals(httpExchange.getRequestMethod())) { 
           System.out.println("I am at Get");
       requestParamValue = handleGetRequest(httpExchange);

     }else if("POST".equals(httpExchange)) { 
       System.out.println("I am at Post");
       requestParamValue = "Hi";        

      }  


    handleResponse(httpExchange,requestParamValue); 

  }


   private String handleGetRequest(HttpExchange httpExchange) {
       
            String[] params = httpExchange.

                    getRequestURI()

                    .toString()

                    .split("\\?")[1].split("[=&]");
            System.out.println(params);
            
            try{
            Statement st = connection.createStatement();
            System.out.println(params[1]+params[3]);
            ResultSet rs = st.executeQuery("Select * from Users where user_name = '" + params[1] + "' and password = '" + params[3]+"'");
            
            if(rs.next()){
                String username = rs.getString(1);
                System.out.println(username);
                System.out.println("Login Success");
            }
            else{
                System.out.println("Login Failed"); 
            }
            } catch(Exception e){
                System.out.println("In Sql Exception: " + e.getMessage());
            }
            
            return 

                    httpExchange.

                    getRequestURI()

                    .toString()

                    .split("\\?")[1].split("=")[1];

   }


   private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {

           System.out.println(requestParamValue); 
        OutputStream outputStream = httpExchange.getResponseBody();
        System.out.println(httpExchange.getHttpContext().getPath());
        

            StringBuilder htmlBuilder = new StringBuilder();

            

            htmlBuilder.append("<html>").

                    append("<body>").

                    append("<h1>").

                    append("Hello ")

                    .append(requestParamValue)

                    .append("</h1>")

                    .append("</body>")

                    .append("</html>");


            // encode HTML content 

            //String htmlResponse = StringEscapeUtils.escapeHtml4(htmlBuilder.toString());

     

            // this line is a must
            // htmlResponse.length()
            String resp = "Response";
            for(int i=0;i<50000;i++){
                resp+="a";
            }
            JSONObject jo = new JSONObject();
jo.put("user_id",1);

resp = jo.toString();
            httpExchange.sendResponseHeaders(200, resp.length());

            // htmlResponse.getBytes()
            outputStream.write(resp.getBytes());

            outputStream.flush();

            outputStream.close();

        }

}