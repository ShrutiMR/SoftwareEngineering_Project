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

public class UsersRequestHandler implements HttpHandler {

    public static Connection connection;

    public UsersRequestHandler(Connection c) {
        connection = c;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Recieved request from: "+httpExchange.getRemoteAddress());
        String requestParamValue = null;
        if ("GET".equals(httpExchange.getRequestMethod())) {
            //There has to be a check function here
            processGetRequest(httpExchange);

        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            processPostRequest(httpExchange);

        }

        //handleResponse(httpExchange, requestParamValue);
    }

    private HashMap getRequestParameters(HttpExchange httpExchange) {

        String[] parser = httpExchange.
                getRequestURI()
                .toString()
                .split("\\?")[1].split("[=&]");

        HashMap<String, String> parameters = new HashMap();

        for (int i = 0; i < parser.length - 1; i = i + 2) {
            parameters.put(parser[i], parser[i + 1]);
        }
        return parameters;

    }

    public void processGetRequest(HttpExchange httpExchange) {
        HashMap parameters = getRequestParameters(httpExchange);
        String user_name = (String) parameters.get("user_name");
        String password = (String) parameters.get("password");
        OutputStream outputStream = httpExchange.getResponseBody();
        JSONObject jo = new JSONObject();
        String resp = null;

        if (user_name != null && password != null) {
            try {
                Statement st = connection.createStatement();

                ResultSet rs = st.executeQuery("Select * from Users where user_name = '" + user_name + "' and password = '" + password + "'");

                if (rs.next()) {
                    int user_id = Integer.valueOf(rs.getString("USER_ID"));
                    int user_code = Integer.valueOf(rs.getString("USER_CODE"));

                    jo.put("isSuccess", true);
                    jo.put("user_id", user_id);
                    jo.put("user_code", user_code);
                    jo.put("first_name", rs.getString("FIRST_NAME"));
                    jo.put("email", rs.getString("EMAIL"));
                        
                    resp = jo.toString();
                    httpExchange.sendResponseHeaders(200, resp.length());

                    // htmlResponse.getBytes()
                    outputStream.write(resp.getBytes());

                    outputStream.flush();
                    outputStream.close();

                } else {
                    jo.put("isSuccess", false);

                    resp = jo.toString();
                    httpExchange.sendResponseHeaders(200, resp.length());

                    // htmlResponse.getBytes()
                    outputStream.write(resp.getBytes());

                    outputStream.flush();
                    outputStream.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        } else {
            try {
                jo.put("isSuccess", false);
                resp = jo.toString();
                httpExchange.sendResponseHeaders(200, resp.length());

                // htmlResponse.getBytes()
                outputStream.write(resp.getBytes());

                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private HashMap getPostParameters(HttpExchange httpExchange) {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = httpExchange.getRequestBody();
        int i;
        JSONObject jo = null;
        HashMap parameters = null;
        try {
            System.out.println("I am here");
            while ((i = inputStream.read()) != -1) {
                sb.append((char) i);
            }
            System.out.println(sb.toString());
            jo = new JSONObject(sb.toString());
            System.out.println(jo);
            Iterator<String> keys = jo.keys();
            parameters = new HashMap();

            while (keys.hasNext()) {
                String key = keys.next();
                parameters.put(key, jo.get(key));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(parameters);
        return parameters;

    }

    public void processPostRequest(HttpExchange httpExchange) {
        System.out.println("Hello");
        HashMap parameters = getPostParameters(httpExchange);
        JSONObject jo = new JSONObject();
        OutputStream outputStream = httpExchange.getResponseBody();
        
        try {
            Statement st = connection.createStatement();
            String query = "insert into users (USER_CODE, USER_NAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL)  "
                    + "values(" + parameters.get("user_code") + ", '"
                    + parameters.get("user_name") + "', '" + parameters.get("password") + "', '" + parameters.get("first_name") + "', '" + parameters.get("last_name") + "', '" + parameters.get("email") + "')";
            System.out.println(query);
            st.executeUpdate(query);
            jo.put("isSuccess",true);

            String resp = jo.toString();
            httpExchange.sendResponseHeaders(200, resp.length());

            // htmlResponse.getBytes()
            
            outputStream.write(resp.getBytes());

            outputStream.flush();

            outputStream.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            jo.put("isSuccess",false);

            String resp = jo.toString();
            try{
                httpExchange.sendResponseHeaders(200, resp.length());
            
                outputStream.write(resp.getBytes());

                outputStream.flush();

                outputStream.close();
            } catch(Exception e1){
                System.out.println(e1.getMessage());
            }
        }
    }

    private void handleResponse(HttpExchange httpExchange, String requestParamValue) throws IOException {

        StringBuilder sb = new StringBuilder();
        InputStream inputStream = httpExchange.getRequestBody();
        int i;
        while ((i = inputStream.read()) != -1) {
            sb.append((char) i);
        }
        System.out.println("hm: " + sb.toString());

        System.out.println(inputStream.toString());

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

        JSONObject jo = new JSONObject();
        jo.put("user_id", 1);

        resp = jo.toString();
        httpExchange.sendResponseHeaders(200, resp.length());

        // htmlResponse.getBytes()
        OutputStream outputStream = httpExchange.getResponseBody();
        outputStream.write(resp.getBytes());

        outputStream.flush();

        outputStream.close();

    }

}
