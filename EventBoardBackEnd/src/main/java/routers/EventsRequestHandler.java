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

public class EventsRequestHandler implements HttpHandler {

    public static Connection connection;

    public EventsRequestHandler(Connection c) {
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

        String[] parser = httpExchange.
                getRequestURI()
                .toString()
                .split("\\?");

        if (parser.length > 1) {
            parser = parser[1].split("[=&]");
        } else {
            return parameters;
        }

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
        String user_id = (String) parameters.get("user_id");
        String association_id = (String) parameters.get("association_id");
        String event_id = (String) parameters.get("event_id");

        ResultSet rs = null;
        try {
            Statement st = connection.createStatement();
            if ("upcoming".equals(type)) {

                if (user_id == null) {
                    jo.put("isSuccess", false);
                } else {
                    rs = st.executeQuery("SELECT B.EVENT_ID, B.START_TIME, B.END_TIME, B.NAME, B.DESCRIPTION, B.VENUE FROM USERS A, "
                            + "EVENTS B, FOLLOW_EVENTS C WHERE A.USER_ID=C.USER_ID "
                            + "AND B.EVENT_ID=C.EVENT_ID AND A.USER_ID=" + user_id + " AND B.START_TIME>SYSDATE() ORDER BY B.START_TIME ASC;");

                    while (rs.next()) {
                        JSONObject temp = new JSONObject();
                        temp.put("name", rs.getString("NAME"));
                        temp.put("start_time", rs.getString("START_TIME"));
                        temp.put("end_time", rs.getString("END_TIME"));
                        temp.put("description", rs.getString("DESCRIPTION"));
                        temp.put("venue", rs.getString("VENUE"));

                        jo.put(rs.getString("EVENT_ID"), temp);
                    }
                    jo.put("isSuccess", true);

                }

                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            } else if ("past".equals(type)) {
                if (user_id == null) {
                    jo.put("isSuccess", false);
                } else {
                    rs = st.executeQuery("SELECT B.EVENT_ID, B.START_TIME, B.END_TIME, B.NAME, B.DESCRIPTION, B.VENUE FROM USERS A, "
                            + "EVENTS B, FOLLOW_EVENTS C WHERE A.USER_ID=C.USER_ID "
                            + "AND B.EVENT_ID=C.EVENT_ID AND A.USER_ID=" + user_id + " AND B.START_TIME < SYSDATE() ORDER BY B.START_TIME ASC;");

                    while (rs.next()) {
                        JSONObject temp = new JSONObject();
                        temp.put("name", rs.getString("NAME"));
                        temp.put("start_time", rs.getString("START_TIME"));
                        temp.put("end_time", rs.getString("END_TIME"));
                        temp.put("description", rs.getString("DESCRIPTION"));
                        temp.put("venue", rs.getString("VENUE"));

                        jo.put(rs.getString("EVENT_ID"), temp);
                    }
                    jo.put("isSuccess", true);

                }

                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            } else if ("associationUpcoming".equals(type)) {

                if (association_id == null) {
                    jo.put("isSuccess", false);
                } else {
                    rs = st.executeQuery("SELECT E.EVENT_ID, E.START_TIME, E.END_TIME, E.NAME, E.DESCRIPTION, "
                            + "E.VENUE FROM ASSOCIATIONS A, EVENTS E WHERE A.ASSOCIATION_ID = E.ASSOCIATION_ID "
                            + "AND E.ASSOCIATION_ID = " + association_id + " AND E.START_TIME > SYSDATE() ORDER BY E.START_TIME ASC;");

                    while (rs.next()) {
                        JSONObject temp = new JSONObject();
                        temp.put("name", rs.getString("NAME"));
                        temp.put("start_time", rs.getString("START_TIME"));
                        temp.put("end_time", rs.getString("END_TIME"));
                        temp.put("description", rs.getString("DESCRIPTION"));
                        temp.put("venue", rs.getString("VENUE"));

                        jo.put(rs.getString("EVENT_ID"), temp);
                    }
                    jo.put("isSuccess", true);

                }

                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            } else if ("associationPast".equals(type)) {
                if (association_id == null) {
                    jo.put("isSuccess", false);
                } else {
                    rs = st.executeQuery("SELECT E.EVENT_ID, E.START_TIME, E.END_TIME, E.NAME, E.DESCRIPTION, "
                            + "E.VENUE FROM ASSOCIATIONS A, EVENTS E WHERE A.ASSOCIATION_ID = E.ASSOCIATION_ID "
                            + "AND E.ASSOCIATION_ID = " + association_id + " AND E.START_TIME < SYSDATE() ORDER BY E.START_TIME  ASC;");

                    while (rs.next()) {
                        JSONObject temp = new JSONObject();
                        temp.put("name", rs.getString("NAME"));
                        temp.put("start_time", rs.getString("START_TIME"));
                        temp.put("end_time", rs.getString("END_TIME"));
                        temp.put("description", rs.getString("DESCRIPTION"));
                        temp.put("venue", rs.getString("VENUE"));

                        jo.put(rs.getString("EVENT_ID"), temp);
                    }
                    jo.put("isSuccess", true);

                }

                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            } else if ("feed".equals(type)) {
                if (user_id == null) {
                    jo.put("isSuccess", false);
                } else {
                    rs = st.executeQuery("SELECT E.EVENT_ID, E.START_TIME, E.END_TIME, E.NAME, E.DESCRIPTION, E.VENUE FROM EVENTS E, "
                            + "FOLLOW_ASSOCIATIONS FA WHERE FA.USER_ID =  " + user_id + " AND E.ASSOCIATION_ID = FA.ASSOCIATION_ID  AND E.START_TIME>SYSDATE() AND "
                            + "E.EVENT_ID NOT IN (SELECT FE.EVENT_ID FROM FOLLOW_EVENTS FE WHERE FE.USER_ID = " + user_id + ") ORDER BY E.START_TIME ASC;");

                    while (rs.next()) {
                        JSONObject temp = new JSONObject();
                        temp.put("name", rs.getString("NAME"));
                        temp.put("start_time", rs.getString("START_TIME"));
                        temp.put("end_time", rs.getString("END_TIME"));
                        temp.put("description", rs.getString("DESCRIPTION"));
                        temp.put("venue", rs.getString("VENUE"));

                        jo.put(rs.getString("EVENT_ID"), temp);
                    }
                    jo.put("isSuccess", true);

                }

                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            } else if ("all".equals(type)) {
                //SELECT E.EVENT_ID, E.START_TIME, E.END_TIME, E.NAME, E.DESCRIPTION, E.VENUE FROM EVENTS E WHERE E.START_TIME>SYSDATE() AND E.START_TIME< DATE_ADD(SYSDATE(), INTERVAL 5 DAY) AND E.EVENT_ID NOT IN (SELECT FE.EVENT_ID FROM FOLLOW_EVENTS FE WHERE FE.USER_ID = 12) ORDER BY E.START_TIME ASC;
                if (user_id == null) {
                    jo.put("isSuccess", false);
                } else {
                    rs = st.executeQuery("SELECT E.EVENT_ID, E.START_TIME, E.END_TIME, E.NAME, E.DESCRIPTION, E.VENUE FROM EVENTS E WHERE E.START_TIME>SYSDATE() AND E.START_TIME< DATE_ADD(SYSDATE(), INTERVAL 5 DAY) AND "
                            + "E.EVENT_ID NOT IN (SELECT FE.EVENT_ID FROM FOLLOW_EVENTS FE WHERE FE.USER_ID = " + user_id + ") ORDER BY E.START_TIME ASC;");

                    while (rs.next()) {
                        JSONObject temp = new JSONObject();
                        temp.put("name", rs.getString("NAME"));
                        temp.put("start_time", rs.getString("START_TIME"));
                        temp.put("end_time", rs.getString("END_TIME"));
                        temp.put("description", rs.getString("DESCRIPTION"));
                        temp.put("venue", rs.getString("VENUE"));

                        jo.put(rs.getString("EVENT_ID"), temp);
                    }
                    jo.put("isSuccess", true);

                }

                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            } else {
                jo.put("isSuccess", false);
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            }
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            jo.put("isSuccess", false);

            try {
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

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

            while ((i = inputStream.read()) != -1) {
                sb.append((char) i);
            }

            jo = new JSONObject(sb.toString());

            Iterator<String> keys = jo.keys();
            parameters = new HashMap();

            while (keys.hasNext()) {
                String key = keys.next();
                parameters.put(key, jo.get(key));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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

        String follow = (String) requestParameters.get("follow");
        String user_id = (String) requestParameters.get("user_id");
        String event_id = (String) requestParameters.get("event_id");

        try {
            Statement st = connection.createStatement();

            if (follow != null && user_id != null && event_id != null) {
                if ("Y".equals(follow)) {
                    st.executeUpdate("INSERT INTO FOLLOW_EVENTS  (EVENT_ID, USER_ID) VALUES ( " + event_id + "  , " + user_id + ")");
                    jo.put("isSuccess", true);
                } else if ("N".equals(follow)) {
                    st.executeUpdate("DELETE FROM FOLLOW_EVENTS WHERE EVENT_ID = " + event_id + " AND USER_ID = " + user_id);
                    jo.put("isSuccess", true);
                } else {
                    jo.put("isSuccess", false);
                }

                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            } else {
                HashMap parameters = getPostParameters(httpExchange);
                String description = (String) parameters.get("description");
                String name = (String) parameters.get("name");
                String association_id = (String) parameters.get("association_id");
                String start_time = (String) parameters.get("start_time");
                String end_time = (String) parameters.get("end_time");
                String venue = (String) parameters.get("venue");

                if (name != null && description != null && start_time != null && end_time != null && association_id != null) {
                    name = "'" + name + "'";
                    start_time = "'" + start_time + "'";
                    end_time = "'" + end_time + "'";
                    association_id = "'" + association_id + "'";
                    description = "'" + description + "'";

                    if (venue == null) {
                        venue = "NULL";
                    } else {
                        venue = "'" + venue + "'";
                    }

                    String query = "INSERT INTO EVENTS (ASSOCIATION_ID, NAME, START_TIME, END_TIME, VENUE, DESCRIPTION)  "
                            + "values(" + association_id + "," + name + "," + start_time + "," + end_time + "," + venue + "," + description + ");";
                    System.out.println(query);
                    st.executeUpdate(query);

                    jo.put("isSuccess", true);

                    resp = jo.toString();
                    byte[] b = resp.getBytes("UTF-8");
                    httpExchange.sendResponseHeaders(200, b.length);
                    // htmlResponse.getBytes()
                    outputStream.write(b);

                    outputStream.flush();
                    outputStream.close();
                } else {
                    jo.put("isSuccess", false);
                    resp = jo.toString();
                    byte[] b = resp.getBytes("UTF-8");
                    httpExchange.sendResponseHeaders(200, b.length);
                    // htmlResponse.getBytes()
                    outputStream.write(b);

                    outputStream.flush();
                    outputStream.close();
                }
            }
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            jo.put("isSuccess", false);

            try {
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }
    }

}
