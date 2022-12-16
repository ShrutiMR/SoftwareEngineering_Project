package routers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import get_requests_handlers.UsersGetRequestHandler;
import java.io.IOException;
import java.util.*;
import java.sql.*;
import post_requests_handlers.UsersPostRequestHandler;
import url_processor.ProcessURL;

public class UsersRequestRouter implements HttpHandler {

    public static Connection connection;

    public UsersRequestRouter(Connection c) {
        connection = c;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        
        
        ProcessURL processURL = new ProcessURL(httpExchange);
        if ("GET".equals(httpExchange.getRequestMethod())) {
            
            //Getting URL parameters
            HashMap urlParameters = processURL.getURLParameters();
         
            //Routing to process the GET Request
            UsersGetRequestHandler getRequestHandler = new UsersGetRequestHandler(httpExchange);
            getRequestHandler.processGetRequest(urlParameters);

        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            
            //Getting Request Body parameters
            HashMap urlBody = processURL.getURLBody();
            
            //Routing to process the POST Request
            UsersPostRequestHandler postRequestHandler = new UsersPostRequestHandler(httpExchange);
            postRequestHandler.processPostRequest(urlBody);
        }
    }
}
