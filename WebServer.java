import com.sun.net.httpserver.*;
import javax.xml.ws.spi.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.net.InetSocketAddress;

public class WebServer
{
	public static void main(String[] args)
	{
		int port = 5000;
		HttpServer server;
		try
		{
			server = HttpServer.create(new InetSocketAddress(port), 0);
		}catch(Exception e)
		{
			System.out.println("Can't make server.");
			return;
		}
		System.out.println("server started at " + port);
		server.createContext("/", new RootHandler());
		server.createContext("/form", new FormHandler());
		server.createContext("/submitted", new FormHandler());
		server.createContext("/echoHeader", new EchoHeaderHandler());
		server.createContext("/echoGet", new EchoGetHandler());
		server.createContext("/echoPost", new EchoPostHandler());
		server.setExecutor(null);
		server.start();
	}
}