package web;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.lsqt.components.springcloud.ExampleEurekaService;
import org.lsqt.components.springcloud.ExampleServiceBase;
import org.lsqt.components.springcloud.MyInstanceConfig;

import com.netflix.appinfo.ApplicationInfoManager;
import com.netflix.appinfo.MyDataCenterInstanceConfig;
import com.netflix.config.DynamicPropertyFactory;
import com.netflix.discovery.DefaultEurekaClientConfig;
import com.netflix.discovery.EurekaClient;


public class MyFilter implements Filter{
	ExampleServiceBase exampleServiceBase ;
	public void init(FilterConfig filterConfig) throws ServletException {
        DynamicPropertyFactory configInstance = com.netflix.config.DynamicPropertyFactory.getInstance();
        ApplicationInfoManager applicationInfoManager = ExampleEurekaService.initializeApplicationInfoManager(new MyDataCenterInstanceConfig());
        EurekaClient eurekaClient = ExampleEurekaService.initializeEurekaClient(applicationInfoManager, new DefaultEurekaClientConfig());

        exampleServiceBase = new ExampleServiceBase(applicationInfoManager, eurekaClient, configInstance,this);
       
        exampleServiceBase.start();
      //  exampleServiceBase.stop();// the stop calls shutdown on eurekaClient
		System.out.println("MyFilterxxxxxxxxxxx");
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		InputStream is = req.getInputStream();

		java.util.Enumeration<String> et = req.getParameterNames();
		while (et.hasMoreElements()) {
			System.out.println("请求参数:" + et.nextElement());
		}

		String util = IOUtils.toString(is, "utf-8");
		System.out.println(util);

		response.setCharacterEncoding("utf-8");
		PrintWriter p = response.getWriter();
		p.println("获取的请求参数:" + request.getParameter("name")+"<br/>Stream值：" + util);
		p.flush();
		
	}

	public void destroy() {
		exampleServiceBase.stop();
	}
}

