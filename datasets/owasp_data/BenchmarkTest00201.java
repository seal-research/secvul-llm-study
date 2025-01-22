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

        // Chain a bunch of propagators in sequence
        String a60073 = param; // assign
        StringBuilder b60073 = new StringBuilder(a60073); // stick in stringbuilder
        b60073.append(" SafeStuff"); // append some safe content
        b60073.replace(
                b60073.length() - "Chars".length(),
                b60073.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map60073 = new java.util.HashMap<String, Object>();
        map60073.put("key60073", b60073.toString()); // put in a collection
        String c60073 = (String) map60073.get("key60073"); // get it back out
        String d60073 = c60073.substring(0, c60073.length() - 1); // extract most of it
        String e60073 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d60073.getBytes()))); // B64 encode and decode it
        String f60073 = e60073.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String g60073 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
        String bar = thing.doSomething(g60073); // reflection

        try {
            String sql = "SELECT * from USERS where USERNAME='foo' and PASSWORD='" + bar + "'";

            org.pck.bcks.helpers.DatabaseHelper.JDBCtemplate.batchUpdate(sql);
            response.getWriter()
                    .println(
                            "No results can be displayed for query: "
                                    + org.pck.esapi.ESAPI.encoder().encodeForHTML(sql)
                                    + "<br>"
                                    + " because the Spring batchUpdate method doesn't return results.");
        } catch (org.springframework.dao.DataAccessException e) {
            if (org.pck.bcks.helpers.DatabaseHelper.hideSQLErrors) {
                response.getWriter().println("Error processing request.");
            } else throw new ServletException(e);
        }
    }
}