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
import static routers.UsersRequestRouter.connection;

/**
 *
 * @author mkonidala
 */
public class UsersGetRequestHandler {

    private HttpExchange httpExchange;

    public UsersGetRequestHandler(HttpExchange httpExchange) {
        this.httpExchange = httpExchange;
    }

    public void processGetRequest(HashMap urlParameters) {

        String user_name = (String) urlParameters.get("user_name");
        String password = (String) urlParameters.get("password");
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
            }

        } else {
            try {
                jo.put("isSuccess", false);
                resp = jo.toString();
                byte[] b = resp.getBytes("UTF-8");
                httpExchange.sendResponseHeaders(200, b.length);
                // htmlResponse.getBytes()
                outputStream.write(b);

                outputStream.flush();
                outputStream.close();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }

    }
}
