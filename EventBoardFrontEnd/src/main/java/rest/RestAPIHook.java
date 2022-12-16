// A Java program for a Client
package rest;

import java.net.*;
import java.io.*;
import org.json.JSONObject;
import java.util.HashMap;

public class RestAPIHook {
    
    // constructor to put ip address and port
    public JSONObject invokeGetMethod(String url) {
        JSONObject ret = null;
        try {
            URL url_obj = new URL(url);
            System.err.println(url);
            HttpURLConnection con = (HttpURLConnection) url_obj.openConnection();
            con.setRequestMethod("GET");
//con.setRequestProperty("Content-Type", "application/json");
            String contentType = con.getHeaderField("Content-Type");

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            ret = new JSONObject(content.toString());

            in.close();
            con.disconnect();
        } catch (Exception e) {

        }
        
        return ret;
    }

    public JSONObject invokePostMethod(String url_string, HashMap params) {
        JSONObject ret = null;
        try {
            URL url = new URL(url_string);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            JSONObject a = new JSONObject(params);
            con.setRequestProperty("Content-Type", "application/json");
            con.setRequestProperty("Accept", "application/json");
            con.setDoOutput(true);

            try ( OutputStream os = con.getOutputStream()) {
                byte[] input = a.toString().getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            String contentType = con.getHeaderField("Content-Type");
            System.out.println(contentType);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            ret = new JSONObject(content.toString());
            in.close();
            con.disconnect();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return ret;
    }
}
