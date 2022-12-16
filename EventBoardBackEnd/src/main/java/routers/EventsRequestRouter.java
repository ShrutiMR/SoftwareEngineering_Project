package routers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import get_requests_handlers.EventsGetRequestHandler;
import java.io.IOException;
import java.util.*;
import java.sql.*;
import post_requests_handlers.EventsPostRequestHandler;
import url_processor.ProcessURL;

public class EventsRequestRouter implements HttpHandler {

    public static Connection connection;

    public EventsRequestRouter(Connection c) {
        connection = c;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        
        //Getting URL parameters
        ProcessURL processURL = new ProcessURL(httpExchange);
        HashMap urlParameters = processURL.getURLParameters();
        
        if ("GET".equals(httpExchange.getRequestMethod())) {  
            
            //Routing to process the GET Request
            EventsGetRequestHandler getRequestHandler= new EventsGetRequestHandler(httpExchange);
            getRequestHandler.processGetRequest(urlParameters);     
        } else if ("POST".equals(httpExchange.getRequestMethod())) { 
            
            //Getting Request Body parameters
            HashMap urlBody = processURL.getURLBody();
            
            //Routing to process the POST Request
            EventsPostRequestHandler postRequestHandler = new EventsPostRequestHandler(httpExchange);
            postRequestHandler.processPostRequest(urlParameters, urlBody);
        }
    }
}
