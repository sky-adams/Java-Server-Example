import com.sun.net.httpserver.*;
import javax.xml.ws.spi.*;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;
import java.net.URI;
import java.net.URLDecoder;
import java.util.List;
import java.util.ArrayList;
import java.lang.StringBuilder;
import java.io.InputStreamReader;
import java.io.BufferedReader;

public abstract class PageHandler implements HttpHandler
{
	private static byte[] readFile(String path) throws IOException 
	{
	  return Files.readAllBytes(Paths.get(path));
	}
	
	public static void sendPage(HttpExchange he, String page, String[][] args) throws IOException
	{
		byte[] response1 = readFile(page); 
		String resp = new String(response1);
		//replace place holders with values
		for(String[] pair: args)
		{
			//replace each occurrence of pair[0] with pair[1]
			String start = "";
			String end = resp;
			int index = end.indexOf(pair[0]);
			while(index != -1)
			{
				start = start + end.substring(0, index) + pair[1];
				end = end.substring(index + pair[0].length());
				index = end.indexOf(pair[0]);
			}
			resp = start+end;
		}
		byte[] response2 = resp.getBytes(); 
		//info on first parameter, the response code: https://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml
		//info on second parameter, from docs: responseLength - if > 0, specifies a fixed response body length and that exact number of bytes must be written to the stream acquired from getResponseBody(), or else if equal to 0, then chunked encoding is used, and an arbitrary number of bytes may be written. if <= -1, then no response body length is specified and no response body may be written.
		he.sendResponseHeaders(200, response2.length);//must call sendResponseHeaders before calling getResponseBody
		OutputStream os = he.getResponseBody();
		os.write(response2);
		os.close();
	}
	
	public static Map<String, String> queryToMap(String query)
	{
		Map<String, String> result = new HashMap<String, String>();
		for (String param : query.split("&")) 
		{
			String pair[] = param.split("=");
			if (pair.length>1) {
				result.put(pair[0], pair[1]);
			}else{
				result.put(pair[0], "");
			}
		}
		return result;
  }

	
	public static void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException 
	{
		if (query != null) 
		{
			String pairs[] = query.split("[&]");
			for (String pair : pairs) 
			{
				String param[] = pair.split("[=]");
				String key = null;
				String value = null;
				if (param.length > 0) 
				{
					key = URLDecoder.decode(param[0], 
					System.getProperty("file.encoding"));
				}

				if (param.length > 1) 
				{
					value = URLDecoder.decode(param[1], 
					System.getProperty("file.encoding"));
				}
				if (parameters.containsKey(key)) 
				{
					Object obj = parameters.get(key);
					if (obj instanceof List<?>) 
					{
						List<String> values = (List<String>) obj;
						values.add(value);
					} else if (obj instanceof String) 
					{
						List<String> values = new ArrayList<String>();
						values.add((String) obj);
						values.add(value);
						parameters.put(key, values);
					}
				} else 
				{
					parameters.put(key, value);
				}
			}
		}
	}
}