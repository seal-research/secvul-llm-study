package org.pck.bcks.taosk;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(value = "/ysug-01/Cdsr92")
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
        String param = scr.getTheParameter("Cdsr92");
        if (param == null) param = "";

        String bar = new Test().doSomething(request, param);

        // FILE URIs are tricky because they are different between Mac and Windows because of lack
        // of standardization.
        // Mac requires an extra slash for some reason.
        String startURIslashes = "";
        if (System.getProperty("os.name").indexOf("Windows") != -1)
            if (System.getProperty("os.name").indexOf("Windows") != -1) startURIslashes = "/";
            else startURIslashes = "//";

        try {
            java.net.URI fileURI =
                    new java.net.URI(
                            "file",
                            null,
                            startURIslashes
                                    + org.pck.bcks.helpers.Utils.TESTFILES_DIR
                                            .replace('\\', java.io.File.separatorChar)
                                            .replace(' ', '_')
                                    + bar,
                            null,
                            null);
            java.io.File fileTarget = new java.io.File(fileURI);
            response.getWriter()
                    .println(
                            "Access to file: '"
                                    + org.pck
                                            .esapi
                                            .ESAPI
                                            .encoder()
                                            .encodeForHTML(fileTarget.toString())
                                    + "' created.");
            if (fileTarget.exists()) {
                response.getWriter().println(" And file already exists.");
            } else {
                response.getWriter().println(" But file doesn't exist yet.");
            }
        } catch (java.net.URISyntaxException e) {
            throw new ServletException(e);
        }
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            String bar;
            String guess = "ABC";
            char switchTarget = guess.charAt(2);

            // Simple case statement that assigns param to bar on conditions 'A', 'C', or 'D'
            switch (switchTarget) {
                case 'A':
                    bar = param;
                    break;
                case 'B':
                    bar = "bobs_your_uncle";
                    break;
                case 'C':
                case 'D':
                    bar = param;
                    break;
                default:
                    bar = "bobs_your_uncle";
                    break;
            }

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass