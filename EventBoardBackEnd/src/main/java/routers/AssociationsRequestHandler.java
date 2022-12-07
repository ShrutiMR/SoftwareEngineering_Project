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

public class AssociationsRequestHandler implements HttpHandler {

    public static Connection connection;

    public AssociationsRequestHandler(Connection c) {
        connection = c;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.println("Recieved request from: " + httpExchange.getRemoteAddress());
        String requestParamValue = null;
        if ("GET".equals(httpExchange.getRequestMethod())) {
            //There has to be a check function here
            processGetRequest(httpExchange);

        } else if ("POST".equals(httpExchange.getRequestMethod())) {
            processPostRequest(httpExchange);

        } else if ("DELETE".equals(httpExchange.getRequestMethod())) {
            processPostRequest(httpExchange);
        }

        //handleResponse(httpExchange, requestParamValue);
    }

    private HashMap getRequestParameters(HttpExchange httpExchange) {
        HashMap<String, String> parameters = new HashMap(); 
        System.out.println("I am here inside");
        String[] parser = httpExchange.
                getRequestURI()
                .toString()
                .split("\\?");
        System.out.println(parser[0]);
        if(parser.length>1){
            parser = parser[1].split("[=&]");
        } else{
            return parameters;
        }
            
        System.out.println("I am here inside 1");

        
        

        for (int i = 0; i < parser.length - 1; i = i + 2) {
            parameters.put(parser[i], parser[i + 1]);
        }
        return parameters;

    }

    public void processGetRequest(HttpExchange httpExchange) {
        HashMap parameters = getRequestParameters(httpExchange);
        OutputStream outputStream = httpExchange.getResponseBody();
        JSONObject jo = new JSONObject();
        String resp = null;

        String type = (String) parameters.get("type");

        ResultSet rs = null;
        try {
            Statement st = connection.createStatement();
            if("administrator".equals(type)){
                rs = st.executeQuery("Select * from ASSOCIATIONS WHERE APPROVAL_STATUS='N'");
                while (rs.next()) {
                    JSONObject temp = new JSONObject();
                    temp.put("association_name",  rs.getString("ASSOCIATION_NAME"));
                    temp.put("description",  rs.getString("DESCRIPTION"));
                    temp.put("address",  rs.getString("ADDRESS"));
                    temp.put("contact_info",  rs.getString("CONTACT_INFO"));
                    temp.put("email",  rs.getString("EMAIL"));
                    
                    jo.put(rs.getString("ASSOCIATION_ID"), temp);
                }
                jo.put("isSuccess", true);
                resp = jo.toString();
                httpExchange.sendResponseHeaders(200, resp.length());

                // htmlResponse.getBytes()
                outputStream.write(resp.getBytes());

                outputStream.flush();
                outputStream.close();
            }
            else if ("all".equals(type)) {

                rs = st.executeQuery("Select * from ASSOCIATIONS WHERE APPROVAL_STATUS='Y'");

                while (rs.next()) {
                    jo.put(rs.getString("ASSOCIATION_ID"), rs.getString("ASSOCIATION_NAME"));
                }
                jo.put("isSuccess", true);
                resp = jo.toString();
                httpExchange.sendResponseHeaders(200, resp.length());

                // htmlResponse.getBytes()
                outputStream.write(resp.getBytes());

                outputStream.flush();
                outputStream.close();

            } else if ("tag".equals(type)) {
                String query = "Select * from ASSOCIATIONS WHERE APPROVAL_STATUS='Y' AND TAG_ID = " + (String) parameters.get("tag_id");
                System.out.println(query);
                rs = st.executeQuery(query);
                while (rs.next()) {
                    jo.put(rs.getString("ASSOCIATION_ID"), rs.getString("ASSOCIATION_NAME"));
                }
                jo.put("isSuccess", true);
                resp = jo.toString();
                httpExchange.sendResponseHeaders(200, resp.length());

                // htmlResponse.getBytes()
                outputStream.write(resp.getBytes());

                outputStream.flush();
                outputStream.close();

            } else if ("single".equals(type)) {
                if (parameters.get("user_id") != null) {
                    String query = "Select * from ASSOCIATIONS WHERE APPROVAL_STATUS='Y' AND USER_ID = " + (String) parameters.get("user_id");
                    System.out.println(query);
                    rs = st.executeQuery(query);
                } else if (parameters.get("association_id") != null) {
                    String query = "Select * from ASSOCIATIONS WHERE APPROVAL_STATUS='Y' AND ASSOCIATION_ID = " + (String) parameters.get("association_id");
                    System.out.println(query);
                    rs = st.executeQuery(query);
                }

                if (rs.next()) {
                    jo.put(rs.getString("ASSOCIATION_ID"), rs.getString("ASSOCIATION_NAME"));
                    jo.put("association_id", rs.getString("ASSOCIATION_ID"));
                    jo.put("association_name", rs.getString("ASSOCIATION_NAME"));
                    jo.put("description", rs.getString("DESCRIPTION"));
                    jo.put("address", rs.getString("ADDRESS"));
                    jo.put("contact_info", rs.getString("CONTACT_INFO"));
                    jo.put("email", rs.getString("EMAIL"));

                }
                jo.put("isSuccess", true);
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
            jo.put("isSuccess", false);

            resp = jo.toString();
            try {
                httpExchange.sendResponseHeaders(200, resp.length());

                outputStream.write(resp.getBytes());

                outputStream.flush();

                outputStream.close();
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
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
        
        HashMap requestParameters = getRequestParameters(httpExchange);
        System.out.println("I am here");
        System.out.println(requestParameters);
        OutputStream outputStream = httpExchange.getResponseBody();
        JSONObject jo = new JSONObject();
        String resp = null;
        ResultSet rs = null;
        String approval = (String) requestParameters.get("approval");
        String follow = (String) requestParameters.get("follow");
        String association_id = (String) requestParameters.get("association_id");
        String user_id = (String) requestParameters.get("user_id");

        try {
            Statement st = connection.createStatement();
            if (approval != null && association_id != null) {
                if ("Y".equals(approval)) {
                    st.executeUpdate("UPDATE ASSOCIATIONS SET APPROVAL_STATUS='Y' WHERE ASSOCIATION_ID = " + association_id);
                    jo.put("isSuccess", true);
                    resp = jo.toString();
                    httpExchange.sendResponseHeaders(200, resp.length());

                    // htmlResponse.getBytes()
                    outputStream.write(resp.getBytes());
                    

                    outputStream.flush();

                    outputStream.close();
                } else if ("N".equals(approval)) {
                    rs = st.executeQuery("SELECT USER_ID FROM ASSOCIATIONS WHERE ASSOCIATION_ID = " + association_id);
                    String user_id_of_this_association = null;
                    if(rs.next()){
                        user_id_of_this_association = (String) rs.getString("USER_ID");
                    }
                    
                    st.executeUpdate("DELETE FROM ASSOCIATIONS WHERE ASSOCIATION_ID = " + association_id);
                    st.executeUpdate("DELETE FROM USERS WHERE USER_ID = " + user_id_of_this_association);
                    jo.put("isSuccess", true);
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

            } else if (follow != null && user_id != null && association_id != null) {
                if ("Y".equals(follow)) {
                    st.executeUpdate("INSERT INTO FOLLOW_ASSOCIATIONS  (ASSOCIATION_ID, USER_ID) VALUES ( " + association_id + "  , " + user_id+")");
                    jo.put("isSuccess", true);
                    resp = jo.toString();
                    httpExchange.sendResponseHeaders(200, resp.length());

                    // htmlResponse.getBytes()
                    outputStream.write(resp.getBytes());

                    outputStream.flush();

                    outputStream.close();
                } else if ("N".equals(follow)) {
                    st.executeUpdate("DELETE FROM FOLLOW_ASSOCIATIONS WHERE ASSOCIATION_ID = " + association_id + " AND USER_ID = " + user_id);
                    jo.put("isSuccess", true);
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
            } else {
                System.out.println("I am here");
                HashMap parameters = getPostParameters(httpExchange);
                System.out.println(parameters);
                String user_name = (String) parameters.get("user_name");
                String password = (String) parameters.get("password");
                String description = (String) parameters.get("description");
                String association_name = (String) parameters.get("association_name");

                if (user_name != null && password != null && description != null && association_name != null) {
                    if (association_name == null) {
                        association_name = "";
                    } else {
                        association_name = "'" + association_name + "'";
                    }
                    String email = (String) parameters.get("email");
                    String email_user_table = null;
                    if (email == null) {
                        email = "NULL";
                        email_user_table = "'test@umass.edu'";
                    } else {
                        email = "'" + email + "'";
                        email_user_table = "'" + email + "'";
                    }
                    String tag_id = (String) parameters.get("tag_id");
                    if (tag_id == null) {
                        tag_id = "NULL";
                    }

                    if (description == null) {
                        description = "NULL";
                    } else {
                        description = "'" + description + "'";
                    }
                    String contact_info = (String) parameters.get("contact_info");
                    if (contact_info == null) {
                        contact_info = "NULL";
                    } else {
                        contact_info = "'" + contact_info + "'";
                    }
                    String address = (String) parameters.get("address");
                    if (address == null) {
                        address = "NULL";
                    } else {
                        address = "'" + address + "'";
                    }

                    String new_user_id = null;
                    
                    String query = "INSERT INTO USERS (USER_CODE, USER_NAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL)  "
                            + "values(" + "1" + ", '"
                            + user_name + "', '" + password + "', "
                            + association_name + ", '" + "Association"
                            + "', " + email_user_table + ")";
                    System.out.println(query);
                    st.executeUpdate(query);
                    rs = st.executeQuery("SELECT USER_ID FROM USERS WHERE USER_NAME = '" + user_name + "' AND PASSWORD = '" + password + "'");
                    if (rs.next()) {
                        new_user_id = rs.getString("USER_ID");
                    }
                    System.out.println(query);
                    System.out.println(new_user_id);

                    query = "INSERT INTO ASSOCIATIONS (USER_ID, ASSOCIATION_NAME, TAG_ID, DESCRIPTION, ADDRESS, CONTACT_INFO, EMAIL, APPROVAL_STATUS) VALUES ("
                            + new_user_id + ", " + association_name + "," + tag_id + "," + description + "," + address + "," + contact_info + "," + email + ", 'N')";
                    st.executeUpdate(query);
                    jo.put("isSuccess", true);

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
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            jo.put("isSuccess", false);

            resp = jo.toString();
            try {
                httpExchange.sendResponseHeaders(200, resp.length());

                outputStream.write(resp.getBytes());

                outputStream.flush();

                outputStream.close();
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }
    }

}
