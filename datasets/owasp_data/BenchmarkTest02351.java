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

        String bar = doSomething(request, param);

        try {
            long l = java.security.SecureRandom.getInstance("SHA1PRNG").nextLong();
            String rememberMeKey = Long.toString(l);

            String user = "SafeLogan";
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
    } // end doPost

    private static String doSomething(HttpServletRequest request, String param)
            throws ServletException, IOException {

        // Chain a bunch of propagators in sequence
        String a14991 = param; // assign
        StringBuilder b14991 = new StringBuilder(a14991); // stick in stringbuilder
        b14991.append(" SafeStuff"); // append some safe content
        b14991.replace(
                b14991.length() - "Chars".length(),
                b14991.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map14991 = new java.util.HashMap<String, Object>();
        map14991.put("key14991", b14991.toString()); // put in a collection
        String c14991 = (String) map14991.get("key14991"); // get it back out
        String d14991 = c14991.substring(0, c14991.length() - 1); // extract most of it
        String e14991 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d14991.getBytes()))); // B64 encode and decode it
        String f14991 = e14991.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String bar = thing.doSomething(f14991); // reflection

        return bar;
    }
}