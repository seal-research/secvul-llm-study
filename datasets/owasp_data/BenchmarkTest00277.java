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
        java.util.Enumeration<String> headers = request.getHeaders("Referer");

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement(); // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = java.net.URLDecoder.decode(param, "UTF-8");

        // Chain a bunch of propagators in sequence
        String a54571 = param; // assign
        StringBuilder b54571 = new StringBuilder(a54571); // stick in stringbuilder
        b54571.append(" SafeStuff"); // append some safe content
        b54571.replace(
                b54571.length() - "Chars".length(),
                b54571.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map54571 = new java.util.HashMap<String, Object>();
        map54571.put("key54571", b54571.toString()); // put in a collection
        String c54571 = (String) map54571.get("key54571"); // get it back out
        String d54571 = c54571.substring(0, c54571.length() - 1); // extract most of it
        String e54571 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d54571.getBytes()))); // B64 encode and decode it
        String f54571 = e54571.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String g54571 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
        String bar = thing.doSomething(g54571); // reflection

        response.setHeader("X-XSS-Protection", "0");
        response.getWriter().print(bar);
    }
}