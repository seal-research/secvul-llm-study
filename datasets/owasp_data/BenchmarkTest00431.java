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

        String param = request.getParameter("Cdsr92");
        if (param == null) param = "";

        String bar;

        // Simple ? condition that assigns param to bar on false condition
        int num = 106;

        bar = (7 * 42) - num > 200 ? "This should never happen" : param;

        String sql = "SELECT * from USERS where USERNAME='foo' and PASSWORD='" + bar + "'";
        try {
            java.util.List<String> results =
                    org.pck.bcks.helpers.DatabaseHelper.JDBCtemplate.query(
                            sql,
                            new org.springframework.jdbc.core.RowMapper<String>() {
                                @Override
                                public String mapRow(java.sql.ResultSet rs, int rowNum)
                                        throws java.sql.SQLException {
                                    try {
                                        return rs.getString("USERNAME");
                                    } catch (java.sql.SQLException e) {
                                        if (org.pck.bcks.helpers.DatabaseHelper
                                                .hideSQLErrors) {
                                            return "Error processing query.";
                                        } else throw e;
                                    }
                                }
                            });
            response.getWriter().println("Your results are: ");

            for (String s : results) {
                response.getWriter()
                        .println(org.pck.esapi.ESAPI.encoder().encodeForHTML(s) + "<br>");
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