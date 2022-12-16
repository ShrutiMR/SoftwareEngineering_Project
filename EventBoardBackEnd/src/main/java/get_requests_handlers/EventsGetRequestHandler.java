/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package get_requests_handlers;

import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import org.json.JSONObject;
import static routers.EventsRequestRouter.connection;

/**
 *
 * @author mkonidala
 */
public class EventsGetRequestHandler {

    private HttpExchange httpExchange;

    public EventsGetRequestHandler(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    public void processGetRequest(HashMap urlParameters) {

        OutputStream outputStream = httpExchange.getResponseBody();
        JSONObject jo = new JSONObject();
        String resp = null;

        String type = (String) urlParameters.get("type");
        String user_id = (String) urlParameters.get("user_id");
        String association_id = (String) urlParameters.get("association_id");
        String event_id = (String) urlParameters.get("event_id");

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

}
