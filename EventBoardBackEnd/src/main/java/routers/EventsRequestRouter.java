package routers;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import get_requests_handlers.EventsGetRequestHandler;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;
import java.io.*;
import org.json.JSONObject;
import post_requests_handlers.EventsPostRequestHandler;
import url_processor.ProcessURL;

public class EventsRequestRouter implements HttpHandler {

    public static Connection connection;

    public EventsRequestRouter(Connection c) {
        connection = c;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Recieved request from: " + httpExchange.getRemoteAddress());
        
        ProcessURL processURL = new ProcessURL(httpExchange);
        HashMap urlParameters = processURL.getURLParameters();
        
        if ("GET".equals(httpExchange.getRequestMethod())) {     
            EventsGetRequestHandler getRequestHandler= new EventsGetRequestHandler(httpExchange);
            getRequestHandler.processGetRequest(urlParameters);     
        } else if ("POST".equals(httpExchange.getRequestMethod())) {          
            HashMap urlBody = processURL.getURLBody();
            EventsPostRequestHandler postRequestHandler = new EventsPostRequestHandler(httpExchange);
            postRequestHandler.processPostRequest(urlParameters, urlBody);
        }
    }
}
