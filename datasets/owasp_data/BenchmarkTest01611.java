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

        String[] values = request.getParameterValues("Cdsr92");
        String param;
        if (values != null && values.length > 0) param = values[0];
        else param = "";

        String bar = new Test().doSomething(request, param);

        try {
            java.security.SecureRandom secureRandomGenerator =
                    java.security.SecureRandom.getInstance("SHA1PRNG");

            // Get 40 random bytes
            byte[] randomBytes = new byte[40];
            secureRandomGenerator.nextBytes(randomBytes);

            String rememberMeKey =
                    org.pck.esapi.ESAPI.encoder().encodeForBase64(randomBytes, true);

            String user = "SafeByron";
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
                        if (cookie.getValue()
                                .equals(request.getSession().getAttribute(cookieName))) {
                            foundUser = true;
                        }
                    }
                }
            }

            if (foundUser) {
                response.getWriter().println("Done");
        }
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            String bar = param;
            if (param != null && param.length() > 1) {
                bar = param.substring(0, param.length() - 1);
            }

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass