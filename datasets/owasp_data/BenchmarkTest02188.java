package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-04/Cdsr92")
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

        String param = request.getParameter("Cdsr92");
        if (param == null) param = "";

        String bar = doSomething(request, param);

        String sql = "SELECT * from USERS where USERNAME='foo' and PASSWORD='" + bar + "'";

        try {
            java.sql.Statement statement =
                    org.pck.bcks.helpers.DatabaseHelper.getSqlStatement();
            java.sql.ResultSet rs = statement.executeQuery(sql);
            org.pck.bcks.helpers.DatabaseHelper.printResults(rs, sql, response);
        } catch (java.sql.SQLException e) {
            if (org.pck.bcks.helpers.DatabaseHelper.hideSQLErrors) {
                response.getWriter().println("Error processing request.");
                return;
            } else throw new ServletException(e);
        }
    } // end doPost

    private static String doSomething(HttpServletRequest request, String param)
            throws ServletException, IOException {

        // Chain a bunch of propagators in sequence
        String a16667 = param; // assign
        StringBuilder b16667 = new StringBuilder(a16667); // stick in stringbuilder
        b16667.append(" SafeStuff"); // append some safe content
        b16667.replace(
                b16667.length() - "Chars".length(),
                b16667.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map16667 = new java.util.HashMap<String, Object>();
        map16667.put("key16667", b16667.toString()); // put in a collection
        String c16667 = (String) map16667.get("key16667"); // get it back out
        String d16667 = c16667.substring(0, c16667.length() - 1); // extract most of it
        String e16667 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d16667.getBytes()))); // B64 encode and decode it
        String f16667 = e16667.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String g16667 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
        String bar = thing.doSomething(g16667); // reflection

        return bar;
    }
}