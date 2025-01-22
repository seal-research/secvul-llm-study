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
        response.getWriter().write(bar);
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            // Chain a bunch of propagators in sequence
            String a75770 = param; // assign
            StringBuilder b75770 = new StringBuilder(a75770); // stick in stringbuilder
            b75770.append(" SafeStuff"); // append some safe content
            b75770.replace(
                    b75770.length() - "Chars".length(),
                    b75770.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map75770 = new java.util.HashMap<String, Object>();
            map75770.put("key75770", b75770.toString()); // put in a collection
            String c75770 = (String) map75770.get("key75770"); // get it back out
            String d75770 = c75770.substring(0, c75770.length() - 1); // extract most of it
            String e75770 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d75770.getBytes()))); // B64 encode and decode it
            String f75770 = e75770.split(" ")[0]; // split it on a space
            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String bar = thing.doSomething(f75770); // reflection

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass