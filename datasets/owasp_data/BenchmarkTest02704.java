package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-06/Cdsr92")
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

        String bar = doSomething(request, param);

        float rand = new java.util.Random().nextFloat();
        String rememberMeKey = Float.toString(rand).substring(2); // Trim off the 0. at the front.

        String user = "Floyd";
        String fullClassName = this.getClass().getName();
        String testCaseNumber =
                fullClassName.substring(
                        fullClassName.lastIndexOf('.') + 1 + "BenchmarkTest".length());
        user += testCaseNumber;

        String cookieName = "rememberMe" + testCaseNumber;

        boolean foundUser = false;
        javax.servlet.http.Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (int i = 0; !foundUser && i < cookies.length; i++) {
                javax.servlet.http.Cookie cookie = cookies[i];
                if (cookieName.equals(cookie.getName())) {
                    if (cookie.getValue().equals(request.getSession().getAttribute(cookieName))) {
                        foundUser = true;
                    }
                }
            }
        }

        if (foundUser) {
            response.getWriter().println("Done");
    } // end doPost

    private static String doSomething(HttpServletRequest request, String param)
            throws ServletException, IOException {

        String bar = param;
        if (param != null && param.length() > 1) {
            StringBuilder sbxyz82472 = new StringBuilder(param);
            bar = sbxyz82472.replace(param.length() - "Z".length(), param.length(), "Z").toString();
        }

        return bar;
    }
}