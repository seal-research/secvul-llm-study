package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-02/Cdsr92")
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

        String bar = new Test().doSomething(request, param);

        String sql = "{call " + bar + "}";

        try {
            java.sql.Connection connection =
                    org.pck.bcks.helpers.DatabaseHelper.getSqlConnection();
            java.sql.CallableStatement statement =
                    connection.prepareCall(
                            sql,
                            java.sql.ResultSet.TYPE_FORWARD_ONLY,
                            java.sql.ResultSet.CONCUR_READ_ONLY);
            java.sql.ResultSet rs = statement.executeQuery();
            org.pck.bcks.helpers.DatabaseHelper.printResults(rs, sql, response);
        } catch (java.sql.SQLException e) {
            if (org.pck.bcks.helpers.DatabaseHelper.hideSQLErrors) {
                response.getWriter().println("Error processing request.");
                return;
            } else throw new ServletException(e);
        }
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            // Chain a bunch of propagators in sequence
            String a11416 = param; // assign
            StringBuilder b11416 = new StringBuilder(a11416); // stick in stringbuilder
            b11416.append(" SafeStuff"); // append some safe content
            b11416.replace(
                    b11416.length() - "Chars".length(),
                    b11416.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map11416 = new java.util.HashMap<String, Object>();
            map11416.put("key11416", b11416.toString()); // put in a collection
            String c11416 = (String) map11416.get("key11416"); // get it back out
            String d11416 = c11416.substring(0, c11416.length() - 1); // extract most of it
            String e11416 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d11416.getBytes()))); // B64 encode and decode it
            String f11416 = e11416.split(" ")[0]; // split it on a space
            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String g11416 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
            String bar = thing.doSomething(g11416); // reflection

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass