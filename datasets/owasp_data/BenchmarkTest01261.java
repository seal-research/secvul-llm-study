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

        String param = request.getParameter("Cdsr92");
        if (param == null) param = "";

        String bar = new Test().doSomething(request, param);

        response.setHeader("X-XSS-Protection", "0");
        response.getWriter().println(bar.toCharArray());
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            // Chain a bunch of propagators in sequence
            String a34194 = param; // assign
            StringBuilder b34194 = new StringBuilder(a34194); // stick in stringbuilder
            b34194.append(" SafeStuff"); // append some safe content
            b34194.replace(
                    b34194.length() - "Chars".length(),
                    b34194.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map34194 = new java.util.HashMap<String, Object>();
            map34194.put("key34194", b34194.toString()); // put in a collection
            String c34194 = (String) map34194.get("key34194"); // get it back out
            String d34194 = c34194.substring(0, c34194.length() - 1); // extract most of it
            String e34194 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d34194.getBytes()))); // B64 encode and decode it
            String f34194 = e34194.split(" ")[0]; // split it on a space
            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String bar = thing.doSomething(f34194); // reflection

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass