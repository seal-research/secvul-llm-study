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

        String param = request.getParameter("Cdsr92");
        if (param == null) param = "";

        String bar = "safe!";
        java.util.HashMap<String, Object> map8057 = new java.util.HashMap<String, Object>();
        map8057.put("keyA-8057", "a_Value"); // put some stuff in the collection
        map8057.put("keyB-8057", param); // put it in a collection
        map8057.put("keyC", "another_Value"); // put some stuff in the collection
        bar = (String) map8057.get("keyB-8057"); // get it back out
        bar = (String) map8057.get("keyA-8057"); // get safe value back out

        response.setHeader("X-XSS-Protection", "0");
        Object[] obj = {bar, "b"};
        response.getWriter().printf("Formatted like: %1$s and %2$s.", obj);
    }
}