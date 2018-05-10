import com.sun.net.httpserver.*;
import javax.xml.ws.spi.*;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RootHandler extends PageHandler implements HttpHandler 
{	
	public void handle(HttpExchange he) throws IOException 
	{
		String[][] args = {};
		sendPage(he, "index.html",args);
	}
}