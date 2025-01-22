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

        String sql =
                "SELECT TOP 1 USERNAME from USERS where USERNAME='foo' and PASSWORD='" + bar + "'";
        try {
            Object results =
                    org.pck.bcks.helpers.DatabaseHelper.JDBCtemplate.queryForObject(
                            sql, new Object[] {}, String.class);
            response.getWriter().println("Your results are: ");

            //		System.out.println("Your results are");
            response.getWriter()
                    .println(org.pck.esapi.ESAPI.encoder().encodeForHTML(results.toString()));
            //		System.out.println(results.toString());
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            response.getWriter()
                    .println(
                            "No results returned for query: "
                                    + org.pck.esapi.ESAPI.encoder().encodeForHTML(sql));
        } catch (org.springframework.dao.DataAccessException e) {
            if (org.pck.bcks.helpers.DatabaseHelper.hideSQLErrors) {
                response.getWriter().println("Error processing request.");
            } else throw new ServletException(e);
        }
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            // Chain a bunch of propagators in sequence
            String a9334 = param; // assign
            StringBuilder b9334 = new StringBuilder(a9334); // stick in stringbuilder
            b9334.append(" SafeStuff"); // append some safe content
            b9334.replace(
                    b9334.length() - "Chars".length(),
                    b9334.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map9334 = new java.util.HashMap<String, Object>();
            map9334.put("key9334", b9334.toString()); // put in a collection
            String c9334 = (String) map9334.get("key9334"); // get it back out
            String d9334 = c9334.substring(0, c9334.length() - 1); // extract most of it
            String e9334 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d9334.getBytes()))); // B64 encode and decode it
            String f9334 = e9334.split(" ")[0]; // split it on a space
            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String g9334 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
            String bar = thing.doSomething(g9334); // reflection

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass