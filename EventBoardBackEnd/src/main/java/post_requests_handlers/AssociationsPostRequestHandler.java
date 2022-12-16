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
import static routers.AssociationsRequestRouter.connection;

/**
 *
 * @author mkonidala
 */
public class AssociationsPostRequestHandler {

    private HttpExchange httpExchange;

    public AssociationsPostRequestHandler(HttpExchange httpExchange) {
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
        String approval = (String) urlParameters.get("approval");
        String follow = (String) urlParameters.get("follow");
        String association_id = (String) urlParameters.get("association_id");
        String user_id = (String) urlParameters.get("user_id");

        try {
            Statement st = connection.createStatement();
            if (approval != null && association_id != null && checkInteger(association_id)) {

                //Rest call made by admin to approve a association
                if ("Y".equals(approval)) {

                    //Approve an Association 
                    st.executeUpdate("UPDATE ASSOCIATIONS SET APPROVAL_STATUS='Y' WHERE ASSOCIATION_ID = " + association_id);

                    //Setting success status in the response
                    jo.put("isSuccess", true);

                    //Building Response
                    resp = jo.toString();
                    byte[] b = resp.getBytes("UTF-8");
                    httpExchange.sendResponseHeaders(200, b.length);

                    outputStream.write(b);
                    outputStream.flush();
                    outputStream.close();

                } else if ("N".equals(approval)) {

                    //Rest call made by admin to reject an association
                    //Finding the user_id of that association
                    rs = st.executeQuery("SELECT USER_ID FROM ASSOCIATIONS WHERE ASSOCIATION_ID = " + association_id);
                    String user_id_of_this_association = null;
                    if (rs.next()) {
                        user_id_of_this_association = (String) rs.getString("USER_ID");
                    }

                    //Deleting the association from the Associations table
                    st.executeUpdate("DELETE FROM ASSOCIATIONS WHERE ASSOCIATION_ID = " + association_id);

                    //Deleting the corresponding USER row in the USERS table
                    st.executeUpdate("DELETE FROM USERS WHERE USER_ID = " + user_id_of_this_association);

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

                    //Setting success status to false if approval is not Y or N
                    jo.put("isSuccess", false);

                    //Building Response
                    resp = jo.toString();
                    byte[] b = resp.getBytes("UTF-8");
                    httpExchange.sendResponseHeaders(200, b.length);

                    outputStream.write(b);
                    outputStream.flush();
                    outputStream.close();
                }

            } else if (follow != null && user_id != null && association_id != null
                    && checkInteger(user_id) && checkInteger(association_id)) {

                //Rest servive for an user to follow and unfollow an event
                if ("Y".equals(follow)) {
                    //User following an association
                    st.executeUpdate("INSERT INTO FOLLOW_ASSOCIATIONS  (ASSOCIATION_ID, USER_ID) VALUES ( " + association_id + "  , " + user_id + ")");
                    jo.put("isSuccess", true);

                    resp = jo.toString();
                    byte[] b = resp.getBytes("UTF-8");
                    httpExchange.sendResponseHeaders(200, b.length);

                    outputStream.write(b);
                    outputStream.flush();
                    outputStream.close();
                } else if ("N".equals(follow)) {
                    //User unfollowing an association
                    st.executeUpdate("DELETE FROM FOLLOW_ASSOCIATIONS WHERE ASSOCIATION_ID = " + association_id + " AND USER_ID = " + user_id);
                    jo.put("isSuccess", true);
                    resp = jo.toString();
                    byte[] b = resp.getBytes("UTF-8");
                    httpExchange.sendResponseHeaders(200, b.length);

                    outputStream.write(b);
                    outputStream.flush();
                    outputStream.close();
                } else {
                    jo.put("isSuccess", false);
                    resp = jo.toString();
                    byte[] b = resp.getBytes("UTF-8");
                    httpExchange.sendResponseHeaders(200, b.length);

                    outputStream.write(b);
                    outputStream.flush();
                    outputStream.close();
                }
            } else {

                //Create a new association
                String user_name = (String) urlBody.get("user_name");
                String password = (String) urlBody.get("password");
                String description = (String) urlBody.get("description");
                String association_name = (String) urlBody.get("association_name");

                if (user_name != null && password != null && description != null && association_name != null) {
                    if (association_name == null) {
                        association_name = "";
                    } else {
                        association_name = "'" + association_name + "'";
                    }
                    String email = (String) urlBody.get("email");
                    String email_user_table = null;
                    if (email == null) {
                        email = "NULL";
                        email_user_table = "'test@umass.edu'";
                    } else {
                        email = "'" + email + "'";
                        email_user_table = "'" + email + "'";
                    }
                    String tag_id = (String) urlBody.get("tag_id");
                    if (tag_id == null) {
                        tag_id = "NULL";
                    }

                    if (description == null) {
                        description = "NULL";
                    } else {
                        description = "'" + description + "'";
                    }
                    String contact_info = (String) urlBody.get("contact_info");
                    if (contact_info == null) {
                        contact_info = "NULL";
                    } else {
                        contact_info = "'" + contact_info + "'";
                    }
                    String address = (String) urlBody.get("address");
                    if (address == null) {
                        address = "NULL";
                    } else {
                        address = "'" + address + "'";
                    }

                    String new_user_id = null;

                    //Create a new row for this association in the USERS table
                    String query = "INSERT INTO USERS (USER_CODE, USER_NAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL)  "
                            + "values(" + "1" + ", '"
                            + user_name + "', '" + password + "', "
                            + association_name + ", '" + "Association"
                            + "', " + email_user_table + ")";

                    st.executeUpdate(query);

                    //Find the new USER_ID of the association
                    rs = st.executeQuery("SELECT USER_ID FROM USERS WHERE USER_NAME = '" + user_name + "' AND PASSWORD = '" + password + "'");
                    if (rs.next()) {
                        new_user_id = rs.getString("USER_ID");
                    }

                    //Insert a new row into the assciation
                    query = "INSERT INTO ASSOCIATIONS (USER_ID, ASSOCIATION_NAME, TAG_ID, DESCRIPTION, ADDRESS, CONTACT_INFO, EMAIL, APPROVAL_STATUS) VALUES ("
                            + new_user_id + ", " + association_name + "," + tag_id + "," + description + "," + address + "," + contact_info + "," + email + ", 'N')";
                    st.executeUpdate(query);
                    jo.put("isSuccess", true);

                    resp = jo.toString();
                    byte[] b = resp.getBytes("UTF-8");
                    httpExchange.sendResponseHeaders(200, b.length);

                    outputStream.write(b);
                    outputStream.flush();
                    outputStream.close();
                } else {
                    jo.put("isSuccess", false);
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
            System.out.println(e.getMessage());
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
