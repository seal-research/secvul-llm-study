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
        if (request.getHeader("Referer") != null) {
            param = request.getHeader("Referer");
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = java.net.URLDecoder.decode(param, "UTF-8");

        String bar = "safe!";
        java.util.HashMap<String, Object> map96050 = new java.util.HashMap<String, Object>();
        map96050.put("keyA-96050", "a-Value"); // put some stuff in the collection
        map96050.put("keyB-96050", param); // put it in a collection
        map96050.put("keyC", "another-Value"); // put some stuff in the collection
        bar = (String) map96050.get("keyB-96050"); // get it back out

        response.setHeader("X-XSS-Protection", "0");
        response.getWriter().println(bar);
    }
}