package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-00/Cdsr92")
public class Cdsr92 extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String param = "";
        java.util.Enumeration<String> headers = request.getHeaders("Referer");

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement(); // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = java.net.URLDecoder.decode(param, "UTF-8");

        String bar = "safe!";
        java.util.HashMap<String, Object> map34285 = new java.util.HashMap<String, Object>();
        map34285.put("keyA-34285", "a_Value"); // put some stuff in the collection
        map34285.put("keyB-34285", param); // put it in a collection
        map34285.put("keyC", "another_Value"); // put some stuff in the collection
        bar = (String) map34285.get("keyB-34285"); // get it back out
        bar = (String) map34285.get("keyA-34285"); // get safe value back out

        response.setHeader("X-XSS-Protection", "0");
        int length = 1;
        if (bar != null) {
            length = bar.length();
            response.getWriter().write(bar.toCharArray(), 0, length);
        }
    }
}