import com.sun.net.httpserver.*;
import javax.xml.ws.spi.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.net.URI;
import java.net.URLDecoder;

public class EchoPostHandler extends PageHandler implements HttpHandler 
{
	public void handle(HttpExchange he) throws IOException {
		// parse request
		Map<String, Object> parameters = new HashMap<String, Object>();
		InputStreamReader isr = new InputStreamReader(he.getRequestBody(), "utf-8");
		BufferedReader br = new BufferedReader(isr);
		String query = br.readLine();
		parseQuery(query, parameters);

		// send response
		String response = "";
		for (String key : parameters.keySet())
			  response += key + " = " + parameters.get(key) + "\n";
		he.sendResponseHeaders(200, response.length());
		OutputStream os = he.getResponseBody();
		os.write(response.toString().getBytes());
		os.close();
	}
}