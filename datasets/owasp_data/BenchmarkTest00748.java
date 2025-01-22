package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-01/Cdsr92")
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

        // Chain a bunch of propagators in sequence
        String a43085 = param; // assign
        StringBuilder b43085 = new StringBuilder(a43085); // stick in stringbuilder
        b43085.append(" SafeStuff"); // append some safe content
        b43085.replace(
                b43085.length() - "Chars".length(),
                b43085.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map43085 = new java.util.HashMap<String, Object>();
        map43085.put("key43085", b43085.toString()); // put in a collection
        String c43085 = (String) map43085.get("key43085"); // get it back out
        String d43085 = c43085.substring(0, c43085.length() - 1); // extract most of it
        String e43085 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d43085.getBytes()))); // B64 encode and decode it
        String f43085 = e43085.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String g43085 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
        String bar = thing.doSomething(g43085); // reflection

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
    }

    void getNextNumber(java.util.Random generator, byte[] barray) {
        generator.nextBytes(barray);
    }
}