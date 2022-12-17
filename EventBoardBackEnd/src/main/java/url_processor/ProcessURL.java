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
        //This method parses all the parameters passed in the URL 
        HashMap<String, String> parameters = new HashMap();
        
        String[] parser = httpExchange.
                getRequestURI()
                .toString()
                .split("\\?");
        
        if (parser.length > 1) {
            parser = parser[1].split("[=&]");
        } else {
            return parameters;
        }

        for (int i = 0; i < parser.length - 1; i = i + 2) {
            parameters.put(parser[i], parser[i + 1]);
        }
        return parameters;

    }
    
    public HashMap getURLBody() {
        //This method parses all the parameters passed through the request body
        StringBuilder sb = new StringBuilder();
        InputStream inputStream = httpExchange.getRequestBody();
        int i;
        JSONObject jo = null;
        HashMap parameters = null;
        try {
            
            while ((i = inputStream.read()) != -1) {
                sb.append((char) i);
            }
            
            jo = new JSONObject(sb.toString());
            
            Iterator<String> keys = jo.keys();
            parameters = new HashMap();

            while (keys.hasNext()) {
                String key = keys.next();
                parameters.put(key, jo.get(key));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        
        return parameters;

    }
}
