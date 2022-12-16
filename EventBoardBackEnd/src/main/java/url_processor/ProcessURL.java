/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package url_processor;

import com.sun.net.httpserver.HttpExchange;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import org.json.JSONObject;

/**
 *
 * @author mkonidala
 */
public class ProcessURL {
    private HttpExchange httpExchange;
    
    public ProcessURL(HttpExchange httpExchange){
        this.httpExchange = httpExchange;
    }
    public HashMap getURLParameters() {
        HashMap<String, String> parameters = new HashMap();
        System.out.println("I am here inside");
        String[] parser = httpExchange.
                getRequestURI()
                .toString()
                .split("\\?");
        System.out.println(parser[0]);
        if (parser.length > 1) {
            parser = parser[1].split("[=&]");
        } else {
            return parameters;
        }

        System.out.println("I am here inside 1");

        for (int i = 0; i < parser.length - 1; i = i + 2) {
            parameters.put(parser[i], parser[i + 1]);
        }
        return parameters;

    }
    
    public HashMap getURLBody() {
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = httpExchange.getRequestBody();
        int i;
        JSONObject jo = null;
        HashMap parameters = null;
        try {
            System.out.println("I am here");
            while ((i = inputStream.read()) != -1) {
                sb.append((char) i);
            }
            System.out.println(sb.toString());
            jo = new JSONObject(sb.toString());
            System.out.println(jo);
            Iterator<String> keys = jo.keys();
            parameters = new HashMap();

            while (keys.hasNext()) {
                String key = keys.next();
                parameters.put(key, jo.get(key));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        System.out.println(parameters);
        return parameters;

    }
}
