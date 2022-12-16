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
import static routers.AssociationsRequestRouter.connection;

/**
 *
 * @author mkonidala
 */
public class AssociationsGetRequestHandler {
    private HttpExchange httpExchange;
    
    public AssociationsGetRequestHandler(HttpExchange httpExchange){
        this.httpExchange = httpExchange;
    }
    
    public void processGetRequest(HashMap urlParameters) {
        
        OutputStream outputStream = httpExchange.getResponseBody();
        JSONObject jo = new JSONObject();
        String resp = null;

        String type = (String) urlParameters.get("type");

        ResultSet rs = null;
        try {
            Statement st = connection.createStatement();
            if ("administrator".equals(type)) {
                rs = st.executeQuery("Select * from ASSOCIATIONS WHERE APPROVAL_STATUS='N'");
                while (rs.next()) {
                    JSONObject temp = new JSONObject();
                    temp.put("association_name", rs.getString("ASSOCIATION_NAME"));
                    temp.put("description", rs.getString("DESCRIPTION"));
                    temp.put("address", rs.getString("ADDRESS"));
                    temp.put("contact_info", rs.getString("CONTACT_INFO"));
                    temp.put("email", rs.getString("EMAIL"));

                    jo.put(rs.getString("ASSOCIATION_ID"), temp);
                }
                jo.put("isSuccess", true);
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            } else if ("all".equals(type)) {

                rs = st.executeQuery("Select * from ASSOCIATIONS WHERE APPROVAL_STATUS='Y'");

                while (rs.next()) {
                    jo.put(rs.getString("ASSOCIATION_ID"), rs.getString("ASSOCIATION_NAME"));
                }
                jo.put("isSuccess", true);
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();

            } else if ("tag".equals(type)) {
                String query = "Select * from ASSOCIATIONS WHERE APPROVAL_STATUS='Y' AND TAG_ID = " + (String) urlParameters.get("tag_id");
                System.out.println(query);
                rs = st.executeQuery(query);
                while (rs.next()) {
                    jo.put(rs.getString("ASSOCIATION_ID"), rs.getString("ASSOCIATION_NAME"));
                }
                jo.put("isSuccess", true);
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();

            } else if ("single".equals(type) && (urlParameters.get("user_id") != null || urlParameters.get("association_id") != null)) {
                if (urlParameters.get("user_id") != null) {
                    String query = "Select * from ASSOCIATIONS WHERE USER_ID = " + (String) urlParameters.get("user_id");
                    System.out.println(query);
                    rs = st.executeQuery(query);
                } else if (urlParameters.get("association_id") != null) {
                    String query = "Select * from ASSOCIATIONS WHERE ASSOCIATION_ID = " + (String) urlParameters.get("association_id");
                    System.out.println(query);
                    rs = st.executeQuery(query);
                }

                if (rs.next()) {
                    jo.put("association_id", rs.getString("ASSOCIATION_ID"));
                    jo.put("association_name", rs.getString("ASSOCIATION_NAME"));
                    jo.put("description", rs.getString("DESCRIPTION"));
                    jo.put("address", rs.getString("ADDRESS"));
                    jo.put("contact_info", rs.getString("CONTACT_INFO"));
                    jo.put("email", rs.getString("EMAIL"));
                    jo.put("approval_status", rs.getString("APPROVAL_STATUS"));

                }
                jo.put("isSuccess", true);
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();

            } else if ("newAssociations".equals(type) && urlParameters.get("user_id") != null) {
                String query = "SELECT * FROM ASSOCIATIONS A WHERE A.APPROVAL_STATUS='Y' AND A.ASSOCIATION_ID NOT IN "
                        + "(SELECT FE.ASSOCIATION_ID FROM FOLLOW_ASSOCIATIONS FE WHERE FE.USER_ID="
                        + (String) urlParameters.get("user_id") + ");";
                System.out.println(query);
                rs = st.executeQuery(query);

                while (rs.next()) {
                    JSONObject temp = new JSONObject();
                    temp.put("association_name", rs.getString("ASSOCIATION_NAME"));
                    temp.put("description", rs.getString("DESCRIPTION"));
                    temp.put("address", rs.getString("ADDRESS"));
                    temp.put("contact_info", rs.getString("CONTACT_INFO"));
                    temp.put("email", rs.getString("EMAIL"));
                    jo.put(rs.getString("ASSOCIATION_ID"), temp);
                }
                jo.put("isSuccess", true);
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            } else if ("oldAssociations".equals(type) && urlParameters.get("user_id") != null) {
                String query = "SELECT * FROM ASSOCIATIONS A WHERE A.APPROVAL_STATUS='Y' AND A.ASSOCIATION_ID IN "
                        + "(SELECT FE.ASSOCIATION_ID FROM FOLLOW_ASSOCIATIONS FE WHERE FE.USER_ID="
                        + (String) urlParameters.get("user_id") + ");";
                System.out.println(query);
                rs = st.executeQuery(query);

                while (rs.next()) {
                    JSONObject temp = new JSONObject();
                    temp.put("association_name", rs.getString("ASSOCIATION_NAME"));
                    temp.put("description", rs.getString("DESCRIPTION"));
                    temp.put("address", rs.getString("ADDRESS"));
                    temp.put("contact_info", rs.getString("CONTACT_INFO"));
                    temp.put("email", rs.getString("EMAIL"));
                    jo.put(rs.getString("ASSOCIATION_ID"), temp);
                }
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
