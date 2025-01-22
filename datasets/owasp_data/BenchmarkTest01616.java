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

        String[] values = request.getParameterValues("Cdsr92");
        String param;
        if (values != null && values.length > 0) param = values[0];
        else param = "";

        String bar = new Test().doSomething(request, param);

        // javax.servlet.http.HttpSession.putValue(java.lang.String,java.lang.Object^)
        request.getSession().putValue("userid", bar);

        response.getWriter()
                .println(
                        "Item: 'userid' with value: '"
                                + org.pck.bcks.helpers.Utils.encodeForHTML(bar)
                                + "' saved in session.");
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            // Chain a bunch of propagators in sequence
            String a53857 = param; // assign
            StringBuilder b53857 = new StringBuilder(a53857); // stick in stringbuilder
            b53857.append(" SafeStuff"); // append some safe content
            b53857.replace(
                    b53857.length() - "Chars".length(),
                    b53857.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map53857 = new java.util.HashMap<String, Object>();
            map53857.put("key53857", b53857.toString()); // put in a collection
            String c53857 = (String) map53857.get("key53857"); // get it back out
            String d53857 = c53857.substring(0, c53857.length() - 1); // extract most of it
            String e53857 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d53857.getBytes()))); // B64 encode and decode it
            String f53857 = e53857.split(" ")[0]; // split it on a space
            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String bar = thing.doSomething(f53857); // reflection

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass