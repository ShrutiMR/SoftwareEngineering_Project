/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package post_requests_handlers;

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
public class EventsPostRequestHandler {

    private HttpExchange httpExchange;

    public EventsPostRequestHandler(HttpExchange httpExchange) {
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

    public void processPostRequest(HashMap urlParameters, HashMap urlBody) {

        OutputStream outputStream = httpExchange.getResponseBody();
        JSONObject jo = new JSONObject();
        String resp = null;
        ResultSet rs = null;

        String follow = (String) urlParameters.get("follow");
        String user_id = (String) urlParameters.get("user_id");
        String event_id = (String) urlParameters.get("event_id");

        try {
            Statement st = connection.createStatement();

            if (follow != null && user_id != null && event_id != null
                    && checkInteger(user_id) && checkInteger(event_id)) {
                
                // Rest call for an user to follow or unfollow an event
                if ("Y".equals(follow)) {
                    
                    st.executeUpdate("INSERT INTO FOLLOW_EVENTS  (EVENT_ID, USER_ID) VALUES ( " + event_id + "  , " + user_id + ")");
                    
                    //Setting success status in the response
                    jo.put("isSuccess", true);
                } else if ("N".equals(follow)) {
                    
                    st.executeUpdate("DELETE FROM FOLLOW_EVENTS WHERE EVENT_ID = " + event_id + " AND USER_ID = " + user_id);
                    
                    //Setting success status in the response
                    jo.put("isSuccess", true);
                } else {
                    
                    //Setting success status to false if the follow parameter is not Y or N
                    jo.put("isSuccess", false);
                }
                
                //Building Response
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                
                outputStream.write(b);
                outputStream.flush();
                outputStream.close();
            } else {
                
                //Rest call to create a new event
                String description = (String) urlBody.get("description");
                String name = (String) urlBody.get("name");
                String association_id = (String) urlBody.get("association_id");
                String start_time = (String) urlBody.get("start_time");
                String end_time = (String) urlBody.get("end_time");
                String venue = (String) urlBody.get("venue");

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
                    
                    st.executeUpdate(query);
                    
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
                    //Setting success status to false in the response, if requires params are not passed
                    jo.put("isSuccess", false);
                    
                    //Building Response
                    resp = jo.toString();
                    byte[] b = resp.getBytes("UTF-8");
                    httpExchange.sendResponseHeaders(200, b.length);
                    
                    outputStream.write(b);
                    outputStream.flush();
                    outputStream.close();
                }
            }
            st.close();
        } catch (Exception e) {
            //Logging exception
            System.out.println(e.getMessage());
            
            //Setting success status to false if above code fails to execute 
            jo.put("isSuccess", false);

            try {
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                
                outputStream.write(b);
                outputStream.flush();
                outputStream.close();
                
            } catch (Exception e1) {
                System.out.println(e1.getMessage());
            }
        }

    }

}
