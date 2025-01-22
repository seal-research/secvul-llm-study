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

        response.setHeader("X-XSS-Protection", "0");
        Object[] obj = {"a", "b"};
        response.getWriter().printf(bar, obj);
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            // Chain a bunch of propagators in sequence
            String a1207 = param; // assign
            StringBuilder b1207 = new StringBuilder(a1207); // stick in stringbuilder
            b1207.append(" SafeStuff"); // append some safe content
            b1207.replace(
                    b1207.length() - "Chars".length(),
                    b1207.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map1207 = new java.util.HashMap<String, Object>();
            map1207.put("key1207", b1207.toString()); // put in a collection
            String c1207 = (String) map1207.get("key1207"); // get it back out
            String d1207 = c1207.substring(0, c1207.length() - 1); // extract most of it
            String e1207 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d1207.getBytes()))); // B64 encode and decode it
            String f1207 = e1207.split(" ")[0]; // split it on a space
            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String g1207 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
            String bar = thing.doSomething(g1207); // reflection

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass