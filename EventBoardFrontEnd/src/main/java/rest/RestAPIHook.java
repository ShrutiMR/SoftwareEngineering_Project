// A Java program for a Client
package rest;

import java.net.*;
import java.io.*;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.IOException;
import java.net.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.net.*;
import java.io.*;
import org.json.JSONObject;

public class RestAPIHook
{
	// initialize socket and input output streams
	private Socket socket		 = null;
	private DataInputStream input = null;
	private DataOutputStream out	 = null;

	// constructor to put ip address and port
	
	public JSONObject restCall(String url)
	{
            JSONObject ret = null;
            try{
		URL url_obj = new URL(url);
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
            } catch(Exception e){
                
            }
            
            return ret;
	}
}
