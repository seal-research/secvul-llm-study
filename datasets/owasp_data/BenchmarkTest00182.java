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
        if (request.getHeader("Cdsr92") != null) {
            param = request.getHeader("Cdsr92");
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = java.net.URLDecoder.decode(param, "UTF-8");

        // Chain a bunch of propagators in sequence
        String a48649 = param; // assign
        StringBuilder b48649 = new StringBuilder(a48649); // stick in stringbuilder
        b48649.append(" SafeStuff"); // append some safe content
        b48649.replace(
                b48649.length() - "Chars".length(),
                b48649.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map48649 = new java.util.HashMap<String, Object>();
        map48649.put("key48649", b48649.toString()); // put in a collection
        String c48649 = (String) map48649.get("key48649"); // get it back out
        String d48649 = c48649.substring(0, c48649.length() - 1); // extract most of it
        String e48649 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d48649.getBytes()))); // B64 encode and decode it
        String f48649 = e48649.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String g48649 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
        String bar = thing.doSomething(g48649); // reflection

        try {
            double stuff = java.security.SecureRandom.getInstance("SHA1PRNG").nextGaussian();
            String rememberMeKey =
                    Double.toString(stuff).substring(2); // Trim off the 0. at the front.

            String user = "SafeGayle";
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
}