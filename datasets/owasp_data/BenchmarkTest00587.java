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
        boolean flag = true;
        java.util.Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements() && flag) {
            String name = (String) names.nextElement();
            String[] values = request.getParameterValues(name);
            if (values != null) {
                for (int i = 0; i < values.length && flag; i++) {
                    String value = values[i];
                    if (value.equals("Cdsr92")) {
                        param = name;
                        flag = false;
                    }
                }
            }
        }

        String bar = "safe!";
        java.util.HashMap<String, Object> map44465 = new java.util.HashMap<String, Object>();
        map44465.put("keyA-44465", "a-Value"); // put some stuff in the collection
        map44465.put("keyB-44465", param); // put it in a collection
        map44465.put("keyC", "another-Value"); // put some stuff in the collection
        bar = (String) map44465.get("keyB-44465"); // get it back out

        // javax.servlet.http.HttpSession.setAttribute(java.lang.String^,java.lang.Object)
        request.getSession().setAttribute(bar, "10340");

        response.getWriter()
                .println(
                        "Item: '"
                                + org.pck.bcks.helpers.Utils.encodeForHTML(bar)
                                + "' with value: '10340' saved in session.");
    }
}