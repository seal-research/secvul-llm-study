package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-06/Cdsr92")
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

        String sql = "SELECT * from USERS where USERNAME=? and PASSWORD='" + bar + "'";

        try {
            java.sql.Connection connection =
                    org.pck.bcks.helpers.DatabaseHelper.getSqlConnection();
            java.sql.PreparedStatement statement =
                    connection.prepareStatement(sql, new String[] {"Column1", "Column2"});
            statement.setString(1, "foo");
            statement.execute();
            org.pck.bcks.helpers.DatabaseHelper.printResults(statement, sql, response);
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
        String a81593 = param; // assign
        StringBuilder b81593 = new StringBuilder(a81593); // stick in stringbuilder
        b81593.append(" SafeStuff"); // append some safe content
        b81593.replace(
                b81593.length() - "Chars".length(),
                b81593.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map81593 = new java.util.HashMap<String, Object>();
        map81593.put("key81593", b81593.toString()); // put in a collection
        String c81593 = (String) map81593.get("key81593"); // get it back out
        String d81593 = c81593.substring(0, c81593.length() - 1); // extract most of it
        String e81593 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d81593.getBytes()))); // B64 encode and decode it
        String f81593 = e81593.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String g81593 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
        String bar = thing.doSomething(g81593); // reflection

        return bar;
    }
}