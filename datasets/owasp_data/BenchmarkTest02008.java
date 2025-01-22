package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-04/Cdsr92")
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
        java.util.Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();

            if (org.pck.bcks.helpers.Utils.commonHeaders.contains(name)) {
                continue; // If standard header, move on to next one
            }

            java.util.Enumeration<String> values = request.getHeaders(name);
            if (values != null && values.hasMoreElements()) {
                param = name; // Grabs the name of the first non-standard header as the parameter
                // value
                break;
            }
        }
        // Note: We don't URL decode header names because people don't normally do that

        String bar = doSomething(request, param);

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

    private static String doSomething(HttpServletRequest request, String param)
            throws ServletException, IOException {

        // Chain a bunch of propagators in sequence
        String a97712 = param; // assign
        StringBuilder b97712 = new StringBuilder(a97712); // stick in stringbuilder
        b97712.append(" SafeStuff"); // append some safe content
        b97712.replace(
                b97712.length() - "Chars".length(),
                b97712.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map97712 = new java.util.HashMap<String, Object>();
        map97712.put("key97712", b97712.toString()); // put in a collection
        String c97712 = (String) map97712.get("key97712"); // get it back out
        String d97712 = c97712.substring(0, c97712.length() - 1); // extract most of it
        String e97712 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d97712.getBytes()))); // B64 encode and decode it
        String f97712 = e97712.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String g97712 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
        String bar = thing.doSomething(g97712); // reflection

        return bar;
    }
}