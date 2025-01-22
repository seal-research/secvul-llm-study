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

        String param = "";
        boolean flag = true;
        java.util.Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements() && flag) {
            String name = (String) names.nextElement();
            String[] values = request.getParameterValues(name);
            if (values != null) {
                for (int i = 0; i < values.length && flag; i++) {
                    String value = values[i];
                    if (value.equals("Cdsr92")) {
                        param = name;
                        flag = false;
                    }
                }
            }
        }

        String bar = new Test().doSomething(request, param);

        String sql = "SELECT * from USERS where USERNAME='foo' and PASSWORD='" + bar + "'";
        try {
            java.util.List<java.util.Map<String, Object>> list =
                    org.pck.bcks.helpers.DatabaseHelper.JDBCtemplate.queryForList(sql);
            response.getWriter().println("Your results are: <br>");

            //		System.out.println("Your results are");

            for (Object o : list) {
                response.getWriter()
                        .println(
                                org.pck.esapi.ESAPI.encoder().encodeForHTML(o.toString())
                                        + "<br>");
                //			System.out.println(o.toString());
            }
        } catch (org.springframework.dao.EmptyResultDataAccessException e) {
            response.getWriter()
                    .println(
                            "No results returned for query: "
                                    + org.pck.esapi.ESAPI.encoder().encodeForHTML(sql));
        } catch (org.springframework.dao.DataAccessException e) {
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
            String a83916 = param; // assign
            StringBuilder b83916 = new StringBuilder(a83916); // stick in stringbuilder
            b83916.append(" SafeStuff"); // append some safe content
            b83916.replace(
                    b83916.length() - "Chars".length(),
                    b83916.length(),
                    "Chars"); // replace some of the end content
            java.util.HashMap<String, Object> map83916 = new java.util.HashMap<String, Object>();
            map83916.put("key83916", b83916.toString()); // put in a collection
            String c83916 = (String) map83916.get("key83916"); // get it back out
            String d83916 = c83916.substring(0, c83916.length() - 1); // extract most of it
            String e83916 =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            d83916.getBytes()))); // B64 encode and decode it
            String f83916 = e83916.split(" ")[0]; // split it on a space
            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String g83916 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
            String bar = thing.doSomething(g83916); // reflection

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass