import com.sun.net.httpserver.*;
import javax.xml.ws.spi.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class FormHandler extends PageHandler implements HttpHandler 
{	
	public void handle(HttpExchange he) throws IOException 
	{
		System.out.println(he.getRequestURI());
		if(he.getRequestURI().getQuery() != null)
		{
			Map <String,String> params = queryToMap(he.getRequestURI().getQuery());
			String firstName = params.get("firstname");
			String lastName = params.get("lastname");
			String[][] args = {{"${firstname}",firstName},
							   {"${lastname}",lastName}};
			sendPage(he, "confirmation.html",args);
		}
		else
		{
			String[][] args = {};
			sendPage(he, "form.html", args);
		}
	}
}