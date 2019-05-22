package web;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

@SuppressWarnings("serial")
//@WebServlet(name = "servlet1", urlPatterns = { "/url1", "/url2", "url3/*" }, loadOnStartup = 1, initParams = {@WebInitParam(name = "name", value = "hotusm") })
public class MyServlet extends HttpServlet {
    
    private String name;

    public MyServlet() {
        System.out.println("load on startup");
    }

    @Override
    public void init() throws ServletException {
        super.init();
        name= this.getInitParameter("name");
    }

    @Override
    protected void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        System.out.println(session.getId());
        System.out.println("MyServlet.doGet ---> name:"+name);
       
        HttpServletRequest request =  (HttpServletRequest) req;
        System.out.println(request.getParameter("name"));
        
        resp.getOutputStream().print("MyServlet.doGet ---> aaaaaaaaaaaaaaaa"+request.getParameter("name"));

    }
}
