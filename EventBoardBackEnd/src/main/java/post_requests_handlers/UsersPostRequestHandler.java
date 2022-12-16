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

        System.out.println("Hello");
        
        JSONObject jo = new JSONObject();
        OutputStream outputStream = httpExchange.getResponseBody();

        try {
            Statement st = connection.createStatement();
            String query = "insert into users (USER_CODE, USER_NAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL)  "
                    + "values(" + urlBody.get("user_code") + ", '"
                    + urlBody.get("user_name") + "', '" + urlBody.get("password") + "', '" + urlBody.get("first_name") + "', '" + urlBody.get("last_name") + "', '" + urlBody.get("email") + "')";
            System.out.println(query);
            st.executeUpdate(query);
            jo.put("isSuccess", true);

            String resp = jo.toString();
            byte[] b = resp.getBytes("UTF-8");
            httpExchange.sendResponseHeaders(200, b.length);
            // htmlResponse.getBytes()
            outputStream.write(b);

            outputStream.flush();
            outputStream.close();
            st.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            jo.put("isSuccess", false);

            try {
                String resp = jo.toString();
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
