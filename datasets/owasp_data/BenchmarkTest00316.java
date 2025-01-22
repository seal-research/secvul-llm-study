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
        java.util.Enumeration<String> headers = request.getHeaders("Cdsr92");

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement(); // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = java.net.URLDecoder.decode(param, "UTF-8");

        // Chain a bunch of propagators in sequence
        String a78780 = param; // assign
        StringBuilder b78780 = new StringBuilder(a78780); // stick in stringbuilder
        b78780.append(" SafeStuff"); // append some safe content
        b78780.replace(
                b78780.length() - "Chars".length(),
                b78780.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map78780 = new java.util.HashMap<String, Object>();
        map78780.put("key78780", b78780.toString()); // put in a collection
        String c78780 = (String) map78780.get("key78780"); // get it back out
        String d78780 = c78780.substring(0, c78780.length() - 1); // extract most of it
        String e78780 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d78780.getBytes()))); // B64 encode and decode it
        String f78780 = e78780.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String bar = thing.doSomething(f78780); // reflection

        try {
            double rand = java.security.SecureRandom.getInstance("SHA1PRNG").nextDouble();

            String rememberMeKey =
                    Double.toString(rand).substring(2); // Trim off the 0. at the front.

            String user = "SafeDonna";
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