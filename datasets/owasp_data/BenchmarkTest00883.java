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

        org.pck.bcks.helpers.SeparateClassRequest scr =
                new org.pck.bcks.helpers.SeparateClassRequest(request);
        String param = scr.getTheValue("Cdsr92");

        // Chain a bunch of propagators in sequence
        String a69063 = param; // assign
        StringBuilder b69063 = new StringBuilder(a69063); // stick in stringbuilder
        b69063.append(" SafeStuff"); // append some safe content
        b69063.replace(
                b69063.length() - "Chars".length(),
                b69063.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map69063 = new java.util.HashMap<String, Object>();
        map69063.put("key69063", b69063.toString()); // put in a collection
        String c69063 = (String) map69063.get("key69063"); // get it back out
        String d69063 = c69063.substring(0, c69063.length() - 1); // extract most of it
        String e69063 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d69063.getBytes()))); // B64 encode and decode it
        String f69063 = e69063.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String bar = thing.doSomething(f69063); // reflection

        response.setHeader("X-XSS-Protection", "0");
        Object[] obj = {"a", bar};
        response.getWriter().printf(java.util.Locale.US, "Formatted like: %1$s and %2$s.", obj);
    }
}