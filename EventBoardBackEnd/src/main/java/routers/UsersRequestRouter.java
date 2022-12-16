package routers;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import get_requests_handlers.UsersGetRequestHandler;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;
import java.io.*;
import org.json.JSONObject;
import post_requests_handlers.UsersPostRequestHandler;
import url_processor.ProcessURL;

public class UsersRequestRouter implements HttpHandler {

    public static Connection connection;

    public UsersRequestRouter(Connection c) {
        connection = c;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Recieved request from: " + httpExchange.getRemoteAddress());
        ProcessURL processURL = new ProcessURL(httpExchange);
        if ("GET".equals(httpExchange.getRequestMethod())) {
            HashMap urlParameters = processURL.getURLParameters();
            UsersGetRequestHandler getRequestHandler = new UsersGetRequestHandler(httpExchange);
            getRequestHandler.processGetRequest(urlParameters);

        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            HashMap urlBody = processURL.getURLBody();
            UsersPostRequestHandler postRequestHandler = new UsersPostRequestHandler(httpExchange);
            postRequestHandler.processPostRequest(urlBody);
        }
    }
}
