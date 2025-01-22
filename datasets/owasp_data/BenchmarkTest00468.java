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

        java.util.Map<String, String[]> map = request.getParameterMap();
        String param = "";
        if (!map.isEmpty()) {
            String[] values = map.get("Cdsr92");
            if (values != null) param = values[0];
        }

        String bar = "safe!";
        java.util.HashMap<String, Object> map50379 = new java.util.HashMap<String, Object>();
        map50379.put("keyA-50379", "a_Value"); // put some stuff in the collection
        map50379.put("keyB-50379", param); // put it in a collection
        map50379.put("keyC", "another_Value"); // put some stuff in the collection
        bar = (String) map50379.get("keyB-50379"); // get it back out
        bar = (String) map50379.get("keyA-50379"); // get safe value back out

        response.setHeader("X-XSS-Protection", "0");
        Object[] obj = {"a", "b"};
        response.getWriter().format(java.util.Locale.US, bar, obj);
    }
}