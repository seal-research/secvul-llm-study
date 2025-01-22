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

        org.pck.bcks.helpers.SeparateClassRequest scr =
                new org.pck.bcks.helpers.SeparateClassRequest(request);
        String param = scr.getTheValue("Cdsr92");

        String bar = new Test().doSomething(request, param);

        response.setHeader("X-XSS-Protection", "0");
        response.getWriter().println(bar);
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            // Chain a bunch of propagators in sequence
            String a58724 = param; // assign
            StringBuilder b58724 = new StringBuilder(a58724); // stick in stringbuilder
            b58724.append(" SafeStuff"); // append some safe content
            b58724.replace(
                    b58724.length() - "Chars".length(),
                    b58724.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map58724 = new java.util.HashMap<String, Object>();
            map58724.put("key58724", b58724.toString()); // put in a collection
            String c58724 = (String) map58724.get("key58724"); // get it back out
            String d58724 = c58724.substring(0, c58724.length() - 1); // extract most of it
            String e58724 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d58724.getBytes()))); // B64 encode and decode it
            String f58724 = e58724.split(" ")[0]; // split it on a space
            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String bar = thing.doSomething(f58724); // reflection

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass