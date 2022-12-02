package microservices;
import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;



public class MyHttpHandler implements HttpHandler{    

  @Override    

  public void handle(HttpExchange httpExchange) throws IOException {

    String requestParamValue=null; 
    if("GET".equals(httpExchange.getRequestMethod())) { 

       requestParamValue = handleGetRequest(httpExchange);

     }else if("POST".equals(httpExchange)) { 

       requestParamValue = "Hi";        

      }  


    handleResponse(httpExchange,requestParamValue); 

  }


   private String handleGetRequest(HttpExchange httpExchange) {
       
            String params = httpExchange.

                    getRequestURI()

                    .toString()

                    .split("\\?")[1];
            System.out.println(params);
            
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
            httpExchange.sendResponseHeaders(200, resp.length());

            // htmlResponse.getBytes()
            outputStream.write(resp.getBytes());

            outputStream.flush();

            outputStream.close();

        }

}