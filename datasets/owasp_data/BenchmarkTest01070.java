package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-02/Cdsr92")
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

        String bar = new Test().doSomething(request, param);

        try {
            java.util.Random numGen = java.security.SecureRandom.getInstance("SHA1PRNG");

            // Get 40 random bytes
            byte[] randomBytes = new byte[40];
            getNextNumber(numGen, randomBytes);

            String rememberMeKey =
                    org.pck.esapi.ESAPI.encoder().encodeForBase64(randomBytes, true);

            String user = "SafeBystander";
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

    void getNextNumber(java.util.Random generator, byte[] barray) {
        generator.nextBytes(barray);
    }

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            // Chain a bunch of propagators in sequence
            String a85812 = param; // assign
            StringBuilder b85812 = new StringBuilder(a85812); // stick in stringbuilder
            b85812.append(" SafeStuff"); // append some safe content
            b85812.replace(
                    b85812.length() - "Chars".length(),
                    b85812.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map85812 = new java.util.HashMap<String, Object>();
            map85812.put("key85812", b85812.toString()); // put in a collection
            String c85812 = (String) map85812.get("key85812"); // get it back out
            String d85812 = c85812.substring(0, c85812.length() - 1); // extract most of it
            String e85812 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d85812.getBytes()))); // B64 encode and decode it
            String f85812 = e85812.split(" ")[0]; // split it on a space
            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String bar = thing.doSomething(f85812); // reflection

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass