import com.sun.net.httpserver.*;
import javax.xml.ws.spi.*;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import java.util.Map;
import java.util.HashMap;
import java.net.URI;
import java.util.Set;
import java.io.OutputStream;

public class EchoHeaderHandler extends PageHandler implements HttpHandler 
{
	public void handle(HttpExchange he) throws IOException {
		Headers headers = he.getRequestHeaders();
		Set<Map.Entry<String, List<String>>> entries = headers.entrySet();
		String response = "";
		for (Map.Entry<String, List<String>> entry : entries)
			  response += entry.toString() + "\n";
		he.sendResponseHeaders(200, response.length());
		OutputStream os = he.getResponseBody();
		os.write(response.toString().getBytes());
		os.close();
	}
}