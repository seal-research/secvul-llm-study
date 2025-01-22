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
        // some code
        response.setContentType("text/html;charset=UTF-8");

        java.util.Map<String, String[]> map = request.getParameterMap();
        String param = "";
        if (!map.isEmpty()) {
            String[] values = map.get("Cdsr92");
            if (values != null) param = values[0];
        }

        String sql = "SELECT  * from USERS where USERNAME='foo' and PASSWORD='" + param + "'";
        try {
            org.springframework.jdbc.support.rowset.SqlRowSet results =
                    org.pck.bcks.helpers.DatabaseHelper.JDBCtemplate.queryForRowSet(sql);
            response.getWriter().println("Your results are: ");

            //		System.out.println("Your results are");
            while (results.next()) {
                response.getWriter()
                        .println(
                                org.pck
                                                .esapi
                                                .ESAPI
                                                .encoder()
                                                .encodeForHTML(results.getString("USERNAME"))
                                        + " ");
                //			System.out.println(results.getString("USERNAME"));
            }
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
    }
}