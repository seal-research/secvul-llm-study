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

        String sql = "SELECT userid from USERS where USERNAME='foo' and PASSWORD='" + bar + "'";
        try {
            // Long results =
            // org.pck.bcks.helpers.DatabaseHelper.JDBCtemplate.queryForLong(sql);
            Long results =
                    org.pck.bcks.helpers.DatabaseHelper.JDBCtemplate.queryForObject(
                            sql, Long.class);
            response.getWriter().println("Your results are: " + String.valueOf(results));
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

            org.pck.bcks.helpers.ThingInterface thing =
                    org.pck.bcks.helpers.ThingFactory.createThing();
            String bar = thing.doSomething(param);

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass