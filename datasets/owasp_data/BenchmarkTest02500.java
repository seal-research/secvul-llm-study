package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-05/Cdsr92")
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

        String[] values = request.getParameterValues("Cdsr92");
        String param;
        if (values != null && values.length > 0) param = values[0];
        else param = "";

        String bar = doSomething(request, param);

        double stuff = new java.util.Random().nextGaussian();
        String rememberMeKey = Double.toString(stuff).substring(2); // Trim off the 0. at the front.

        String user = "Gayle";
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

        String bar;

        // Simple if statement that assigns constant to bar on true condition
        int num = 86;
        if ((7 * 42) - num > 200) bar = "This_should_always_happen";
        else bar = param;

        return bar;
    }
}