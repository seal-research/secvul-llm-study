package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-05/Cdsr92")
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

        String bar = doSomething(request, param);

        response.setHeader("X-XSS-Protection", "0");
        response.getWriter().print(bar);
    } // end doPost

    private static String doSomething(HttpServletRequest request, String param)
            throws ServletException, IOException {

        // Chain a bunch of propagators in sequence
        String a47309 = param; // assign
        StringBuilder b47309 = new StringBuilder(a47309); // stick in stringbuilder
        b47309.append(" SafeStuff"); // append some safe content
        b47309.replace(
                b47309.length() - "Chars".length(),
                b47309.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map47309 = new java.util.HashMap<String, Object>();
        map47309.put("key47309", b47309.toString()); // put in a collection
        String c47309 = (String) map47309.get("key47309"); // get it back out
        String d47309 = c47309.substring(0, c47309.length() - 1); // extract most of it
        String e47309 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d47309.getBytes()))); // B64 encode and decode it
        String f47309 = e47309.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String g47309 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
        String bar = thing.doSomething(g47309); // reflection

        return bar;
    }
}