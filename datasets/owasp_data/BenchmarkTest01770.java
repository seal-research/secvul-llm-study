package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-03/Cdsr92")
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

        org.pck.bcks.helpers.SeparateClassRequest scr =
                new org.pck.bcks.helpers.SeparateClassRequest(request);
        String param = scr.getTheValue("Cdsr92");

        String bar = new Test().doSomething(request, param);

        response.setHeader("X-XSS-Protection", "0");
        Object[] obj = {"a", "b"};
        response.getWriter().printf(bar, obj);
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            StringBuilder sbxyz41282 = new StringBuilder(param);
            String bar = sbxyz41282.append("_SafeStuff").toString();

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass