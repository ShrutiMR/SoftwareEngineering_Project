package routers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.util.*;
import java.sql.*;
import url_processor.ProcessURL;
import get_requests_handlers.AssociationsGetRequestHandler;
import post_requests_handlers.AssociationsPostRequestHandler;

public class AssociationsRequestRouter implements HttpHandler {

    public static Connection connection;

    public AssociationsRequestRouter(Connection c) {
        connection = c;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        
        ProcessURL processURL = new ProcessURL(httpExchange);
        
        //Getting URL parameters
        HashMap urlParameters = processURL.getURLParameters();
        if ("GET".equals(httpExchange.getRequestMethod())) {
            
            //Routing to process the GET Request
            AssociationsGetRequestHandler getRequestHandler= new AssociationsGetRequestHandler(httpExchange);
            getRequestHandler.processGetRequest(urlParameters);
        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            
            //Getting Request Body parameters
            HashMap urlBody = processURL.getURLBody();
            
            //Routing to process the POST Request
            AssociationsPostRequestHandler postRequestHandler = new AssociationsPostRequestHandler(httpExchange);
            postRequestHandler.processPostRequest(urlParameters, urlBody);
        }
    }
}
