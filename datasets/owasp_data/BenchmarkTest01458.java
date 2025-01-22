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
        boolean flag = true;
        java.util.Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements() && flag) {
            String name = (String) names.nextElement();
            String[] values = request.getParameterValues(name);
            if (values != null) {
                for (int i = 0; i < values.length && flag; i++) {
                    String value = values[i];
                    if (value.equals("Cdsr92")) {
                        param = name;
                        flag = false;
                    }
                }
            }
        }

        String bar = new Test().doSomething(request, param);

        // javax.servlet.http.HttpSession.setAttribute(java.lang.String^,java.lang.Object)
        request.getSession().setAttribute(bar, "10340");

        response.getWriter()
                .println(
                        "Item: '"
                                + org.pck.bcks.helpers.Utils.encodeForHTML(bar)
                                + "' with value: '10340' saved in session.");
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            // Chain a bunch of propagators in sequence
            String a34936 = param; // assign
            StringBuilder b34936 = new StringBuilder(a34936); // stick in stringbuilder
            b34936.append(" SafeStuff"); // append some safe content
            b34936.replace(
                    b34936.length() - "Chars".length(),
                    b34936.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map34936 = new java.util.HashMap<String, Object>();
            map34936.put("key34936", b34936.toString()); // put in a collection
            String c34936 = (String) map34936.get("key34936"); // get it back out
            String d34936 = c34936.substring(0, c34936.length() - 1); // extract most of it
            String e34936 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d34936.getBytes()))); // B64 encode and decode it
            String f34936 = e34936.split(" ")[0]; // split it on a space
            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String bar = thing.doSomething(f34936); // reflection

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass