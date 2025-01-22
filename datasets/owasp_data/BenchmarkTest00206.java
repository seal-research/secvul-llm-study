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
        String a13396 = param; // assign
        StringBuilder b13396 = new StringBuilder(a13396); // stick in stringbuilder
        b13396.append(" SafeStuff"); // append some safe content
        b13396.replace(
                b13396.length() - "Chars".length(),
                b13396.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map13396 = new java.util.HashMap<String, Object>();
        map13396.put("key13396", b13396.toString()); // put in a collection
        String c13396 = (String) map13396.get("key13396"); // get it back out
        String d13396 = c13396.substring(0, c13396.length() - 1); // extract most of it
        String e13396 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d13396.getBytes()))); // B64 encode and decode it
        String f13396 = e13396.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String g13396 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
        String bar = thing.doSomething(g13396); // reflection

        String sql = "INSERT INTO users (username, password) VALUES ('foo','" + bar + "')";

        try {
            java.sql.Statement statement =
                    org.pck.bcks.helpers.DatabaseHelper.getSqlStatement();
            int count = statement.executeUpdate(sql, new String[] {"USERNAME", "PASSWORD"});
            org.pck.bcks.helpers.DatabaseHelper.outputUpdateComplete(sql, response);
        } catch (java.sql.SQLException e) {
            if (org.pck.bcks.helpers.DatabaseHelper.hideSQLErrors) {
                response.getWriter().println("Error processing request.");
                return;
            } else throw new ServletException(e);
        }
    }
}