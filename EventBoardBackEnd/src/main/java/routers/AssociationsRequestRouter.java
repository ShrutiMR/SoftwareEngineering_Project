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
        System.out.println("Recieved request from: " + httpExchange.getRemoteAddress());
        ProcessURL processURL = new ProcessURL(httpExchange);
        HashMap urlParameters = processURL.getURLParameters();
        if ("GET".equals(httpExchange.getRequestMethod())) {
            AssociationsGetRequestHandler getRequestHandler= new AssociationsGetRequestHandler(httpExchange);
            getRequestHandler.processGetRequest(urlParameters);
        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            HashMap urlBody = processURL.getURLBody();
            AssociationsPostRequestHandler postRequestHandler = new AssociationsPostRequestHandler(httpExchange);
            postRequestHandler.processPostRequest(urlParameters, urlBody);
        }
    }
}
