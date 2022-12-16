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
    
    public boolean checkInteger(Object obj) {
        boolean check = true;
        try {
            int temp = Integer.parseInt((String) obj);
        } catch (Exception e) {
            check = false;
        }
        return check;
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

            if ("upcoming".equals(type) && user_id != null && checkInteger(user_id)) {

                //Rest call to get the all the upcoming events of a user 
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

                //Setting success status in the response
                jo.put("isSuccess", true);

                //Building Response
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);

                outputStream.write(b);
                outputStream.flush();
                outputStream.close();
            } else if ("past".equals(type) && user_id != null && checkInteger(user_id)) {

                //Rest call to get all the past events of a user
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

                //Setting success status in the response
                jo.put("isSuccess", true);

                //Building Response
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);

                outputStream.write(b);
                outputStream.flush();
                outputStream.close();

            } else if ("associationUpcoming".equals(type) && association_id != null && checkInteger(association_id)) {

                //Rest call to get all upcoming events of an association
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

                //Setting success status in the response
                jo.put("isSuccess", true);

                //Building Response
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);

                outputStream.write(b);
                outputStream.flush();
                outputStream.close();

            } else if ("associationPast".equals(type) && association_id != null && checkInteger(association_id)) {

                //Rest call to get all the past events of the association
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

                //Setting success status in the response
                jo.put("isSuccess", true);

                //Building Response
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);

                outputStream.write(b);
                outputStream.flush();
                outputStream.close();

            } else if ("feed".equals(type) && user_id != null && checkInteger(user_id)) {

                //Rest call to get the Home feed of the user
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

                //Setting success status in the response
                jo.put("isSuccess", true);

                //Building Response
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);

                outputStream.write(b);
                outputStream.flush();
                outputStream.close();

            } else if ("all".equals(type) && user_id!=null && checkInteger(user_id)) {

                //Rest call to get the events in the next 5 days that the user can explore
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

                //Setting success status in the response
                jo.put("isSuccess", true);
                
                //Building Response
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                
                outputStream.write(b);
                outputStream.flush();
                outputStream.close();
                
            } else {
                //If none of the above conditions were satisfied
                //Setting success status to false in the response
                jo.put("isSuccess", false);
                
                //Building Response
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                
                outputStream.write(b);
                outputStream.flush();
                outputStream.close();
            }
            st.close();
        } catch (Exception e) {
            //Comes here if anything fails in the entire code above
            // Logging exception
            System.out.println(e.getMessage());
            
            //Setting success status to false in the response
            jo.put("isSuccess", false);

            try {
                //Building Response
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                
                outputStream.write(b);
                outputStream.flush();
                outputStream.close();
            } catch (Exception e1) {
                // Logging exception
                System.out.println(e1.getMessage());
            }
        }

    }

}
