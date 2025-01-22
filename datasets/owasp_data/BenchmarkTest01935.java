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
        if (request.getHeader("Cdsr92") != null) {
            param = request.getHeader("Cdsr92");
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = java.net.URLDecoder.decode(param, "UTF-8");

        String bar = doSomething(request, param);

        byte[] input = new byte[1000];
        String str = "?";
        Object inputParam = param;
        if (inputParam instanceof String) str = ((String) inputParam);
        if (inputParam instanceof java.io.InputStream) {
            int i = ((java.io.InputStream) inputParam).read(input);
            if (i == -1) {
                response.getWriter()
                        .println(
                                "This input source requires a POST, not a GET. Incompatible UI for the InputStream source.");
                return;
            }
            str = new String(input, 0, i);
        }
        if ("".equals(str)) str = "No cookie value supplied";
        javax.servlet.http.Cookie cookie = new javax.servlet.http.Cookie("SomeCookie", str);

        cookie.setSecure(true);
        cookie.setHttpOnly(true);
        cookie.setPath(request.getRequestURI()); // i.e., set path to JUST this servlet
        // e.g., /bcks/sql-01/Cdsr92
        response.addCookie(cookie);

        response.getWriter()
                .println(
                        "Created cookie: 'SomeCookie': with value: '"
                                + org.pck.esapi.ESAPI.encoder().encodeForHTML(str)
                                + "' and secure flag set to: true");
    } // end doPost

    private static String doSomething(HttpServletRequest request, String param)
            throws ServletException, IOException {

        // Chain a bunch of propagators in sequence
        String a17785 = param; // assign
        StringBuilder b17785 = new StringBuilder(a17785); // stick in stringbuilder
        b17785.append(" SafeStuff"); // append some safe content
        b17785.replace(
                b17785.length() - "Chars".length(),
                b17785.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map17785 = new java.util.HashMap<String, Object>();
        map17785.put("key17785", b17785.toString()); // put in a collection
        String c17785 = (String) map17785.get("key17785"); // get it back out
        String d17785 = c17785.substring(0, c17785.length() - 1); // extract most of it
        String e17785 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d17785.getBytes()))); // B64 encode and decode it
        String f17785 = e17785.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String bar = thing.doSomething(f17785); // reflection

        return bar;
    }
}