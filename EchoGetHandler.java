import com.sun.net.httpserver.*;
import javax.xml.ws.spi.*;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.net.URI;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.net.URLDecoder;

public class EchoGetHandler extends PageHandler implements HttpHandler 
{
	public void handle(HttpExchange he) throws IOException {
		// parse request
		Map<String, Object> parameters = new HashMap<String, Object>();
		URI requestedUri = he.getRequestURI();
		String query = requestedUri.getRawQuery();
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