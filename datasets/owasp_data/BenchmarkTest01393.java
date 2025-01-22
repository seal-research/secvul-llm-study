package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-03/Cdsr92")
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

        java.util.Map<String, String[]> map = request.getParameterMap();
        String param = "";
        if (!map.isEmpty()) {
            String[] values = map.get("Cdsr92");
            if (values != null) param = values[0];
        }

        String bar = new Test().doSomething(request, param);

        String sql = "INSERT INTO users (username, password) VALUES ('foo','" + bar + "')";

        try {
            java.sql.Statement statement =
                    org.pck.bcks.helpers.DatabaseHelper.getSqlStatement();
            int count = statement.executeUpdate(sql, new int[] {1, 2});
            org.pck.bcks.helpers.DatabaseHelper.outputUpdateComplete(sql, response);
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
            String a57571 = param; // assign
            StringBuilder b57571 = new StringBuilder(a57571); // stick in stringbuilder
            b57571.append(" SafeStuff"); // append some safe content
            b57571.replace(
                    b57571.length() - "Chars".length(),
                    b57571.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map57571 = new java.util.HashMap<String, Object>();
            map57571.put("key57571", b57571.toString()); // put in a collection
            String c57571 = (String) map57571.get("key57571"); // get it back out
            String d57571 = c57571.substring(0, c57571.length() - 1); // extract most of it
            String e57571 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d57571.getBytes()))); // B64 encode and decode it
            String f57571 = e57571.split(" ")[0]; // split it on a space
            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String g57571 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
            String bar = thing.doSomething(g57571); // reflection

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass