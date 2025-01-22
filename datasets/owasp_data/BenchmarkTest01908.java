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
        if (request.getHeader("Cdsr92") != null) {
            param = request.getHeader("Cdsr92");
        }

        // URL Decode the header value since req.getHeader() doesn't. Unlike req.getParameter().
        param = java.net.URLDecoder.decode(param, "UTF-8");

        String bar = doSomething(request, param);

        String fileName = org.pck.bcks.helpers.Utils.TESTFILES_DIR + bar;
        java.io.InputStream is = null;

        try {
            java.nio.file.Path path = java.nio.file.Paths.get(fileName);
            is = java.nio.file.Files.newInputStream(path, java.nio.file.StandardOpenOption.READ);
            byte[] b = new byte[1000];
            int size = is.read(b);
            response.getWriter()
                    .println(
                            "The beginning of file: '"
                                    + org.pck.esapi.ESAPI.encoder().encodeForHTML(fileName)
                                    + "' is:\n\n");
            response.getWriter()
                    .println(org.pck.esapi.ESAPI.encoder().encodeForHTML(new String(b, 0, size)));
            is.close();
        } catch (Exception e) {
            System.out.println("Couldn't open InputStream on file: '" + fileName + "'");
            response.getWriter()
                    .println(
                            "Problem getting InputStream: "
                                    + org.pck
                                            .esapi
                                            .ESAPI
                                            .encoder()
                                            .encodeForHTML(e.getMessage()));
        } finally {
            if (is != null) {
                try {
                    is.close();
                    is = null;
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
            java.util.List<String> valuesList = new java.util.ArrayList<String>();
            valuesList.add("safe");
            valuesList.add(param);
            valuesList.add("moresafe");

            valuesList.remove(0); // remove the 1st safe value

            bar = valuesList.get(0); // get the param value
        }

        return bar;
    }
}