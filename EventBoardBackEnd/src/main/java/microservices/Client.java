// A Java program for a Client
package microservices;

import java.net.*;
import java.io.*;
import org.json.JSONObject;
public class Client
{
	// initialize socket and input output streams
	private Socket socket		 = null;
	private DataInputStream input = null;
	private DataOutputStream out	 = null;

	// constructor to put ip address and port
	
	public static void main(String args[])
	{
            try{
		URL url = new URL("http://localhost:9000/products/hi/?product_name=mkonidala&param2=Sailaja@007");
HttpURLConnection con = (HttpURLConnection) url.openConnection();
con.setRequestMethod("POST");
JSONObject a = new JSONObject("{\"user_id\":15}");
con.setRequestProperty("Content-Type", "application/json");
con.setRequestProperty("Accept", "application/json");
con.setDoOutput(true);

try(OutputStream os = con.getOutputStream()) {
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
                System.out.println(content.toString());
in.close();
con.disconnect();
            } catch(Exception e){
                System.out.println(e.getMessage());
            }
	}
}
