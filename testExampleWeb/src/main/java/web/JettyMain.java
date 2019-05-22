package web;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.EventListener;

import javax.servlet.DispatcherType;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.util.component.LifeCycle;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyMain {
	public static void main(String[] args) throws Exception {
	    long start = System.currentTimeMillis();
		Server server = new Server(8088);
		//WebAppContext webappHandler = new WebAppContext("src/main/webapp", "/");
		
		//webappHandler.addBean(new JspStarter(webappHandler)); 
		//WebAppContext webappHandler = new WebAppContext();
		//webappHandler.setContextPath("/");
		 
		
		//webappHandler.setResourceBase("./");
		//webappHandler.addServlet(MyServlet.class, "*."); 	
		
		ServletContextHandler hd = new ServletContextHandler(ServletContextHandler.SESSIONS);
		
		//EnumSet<DispatcherType>  es = EnumSet.allOf(DispatcherType.class);
		hd.addFilter(MyFilter.class, "/*",null);
		hd.addServlet(MyServlet.class, "/*");
		
 
		
	
	 
		//hd.addServlet(MyServlet.class, "/");
		
		server.setHandler(hd);
		server.start();
		long end = System.currentTimeMillis();
		System.out.println("jetty+webapp startup cost->"+(end-start)+"(ms)");
		server.join();
		server.stop();
		
		
	}
}

