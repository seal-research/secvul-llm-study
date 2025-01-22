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

        String param = "";
        java.util.Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();

            if (org.pck.bcks.helpers.Utils.commonHeaders.contains(name)) {
                continue; // If standard header, move on to next one
            }

            java.util.Enumeration<String> values = request.getHeaders(name);
            if (values != null && values.hasMoreElements()) {
                param = name; // Grabs the name of the first non-standard header as the parameter
                // value
                break;
            }
        }
        // Note: We don't URL decode header names because people don't normally do that

        String bar = doSomething(request, param);

        String fileName = null;
        java.io.FileInputStream fis = null;

        try {
            fileName = org.pck.bcks.helpers.Utils.TESTFILES_DIR + bar;
            fis = new java.io.FileInputStream(new java.io.File(fileName));
            byte[] b = new byte[1000];
            int size = fis.read(b);
            response.getWriter()
                    .println(
                            "The beginning of file: '"
                                    + org.pck.esapi.ESAPI.encoder().encodeForHTML(fileName)
                                    + "' is:\n\n"
                                    + org.pck
                                            .esapi
                                            .ESAPI
                                            .encoder()
                                            .encodeForHTML(new String(b, 0, size)));
        } catch (Exception e) {
            System.out.println("Couldn't open FileInputStream on file: '" + fileName + "'");
            response.getWriter()
                    .println(
                            "Problem getting FileInputStream: "
                                    + org.pck
                                            .esapi
                                            .ESAPI
                                            .encoder()
                                            .encodeForHTML(e.getMessage()));
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                    fis = null;
                } catch (Exception e) {
                    // we tried...
                }
            }
        }
    } // end doPost

    private static String doSomething(HttpServletRequest request, String param)
            throws ServletException, IOException {

        String bar = "";
        if (param != null) {
            bar =
                    new String(
                            org.apache.commons.codec.binary.Base64.decodeBase64(
                                    org.apache.commons.codec.binary.Base64.encodeBase64(
                                            param.getBytes())));
        }

        return bar;
    }
}