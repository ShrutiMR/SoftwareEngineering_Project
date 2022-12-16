/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package post_requests_handlers;

import com.sun.net.httpserver.HttpExchange;
import java.io.OutputStream;
import java.sql.Statement;
import java.util.HashMap;
import org.json.JSONObject;
import static routers.UsersRequestRouter.connection;

/**
 *
 * @author mkonidala
 */
public class UsersPostRequestHandler {

    private HttpExchange httpExchange;

    public UsersPostRequestHandler(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    public void processPostRequest(HashMap urlBody) {

        JSONObject jo = new JSONObject();
        OutputStream outputStream = httpExchange.getResponseBody();

        try {

            if (urlBody.get("user_code") != null && urlBody.get("user_name") != null
                    && urlBody.get("password") != null && urlBody.get("first_name") != null
                    && urlBody.get("last_name") != null
                    && urlBody.get("email") != null) {
                //Rest call to create a new User 
                Statement st = connection.createStatement();
                String query = "insert into users (USER_CODE, USER_NAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL)  "
                        + "values(" + urlBody.get("user_code") + ", '"
                        + urlBody.get("user_name") + "', '" + urlBody.get("password") + "', '" + urlBody.get("first_name") + "', '" + urlBody.get("last_name") + "', '" + urlBody.get("email") + "')";
                
                st.executeUpdate(query);
                st.close();

                //Setting success status in the response
                jo.put("isSuccess", true);
            } else {
                jo.put("isSuccess", false);
            }

            //Building Response
            String resp = jo.toString();
            byte[] b = resp.getBytes("UTF-8");
            httpExchange.sendResponseHeaders(200, b.length);

            outputStream.write(b);
            outputStream.flush();
            outputStream.close();

        } catch (Exception e) {

            //Logging exception 
            System.out.println(e.getMessage());

            //Setting success status to false if the above code doesn't exacute 
            jo.put("isSuccess", false);

            try {
                String resp = jo.toString();
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
