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

        org.pck.bcks.helpers.SeparateClassRequest scr =
                new org.pck.bcks.helpers.SeparateClassRequest(request);
        String param = scr.getTheParameter("Cdsr92");
        if (param == null) param = "";

        String bar = "safe!";
        java.util.HashMap<String, Object> map27260 = new java.util.HashMap<String, Object>();
        map27260.put("keyA-27260", "a_Value"); // put some stuff in the collection
        map27260.put("keyB-27260", param); // put it in a collection
        map27260.put("keyC", "another_Value"); // put some stuff in the collection
        bar = (String) map27260.get("keyB-27260"); // get it back out
        bar = (String) map27260.get("keyA-27260"); // get safe value back out

        String cmd = "";
        String osName = System.getProperty("os.name");
        if (osName.indexOf("Windows") != -1) {
            cmd = org.pck.bcks.helpers.Utils.getOSCommandString("echo");
        }

        Runtime r = Runtime.getRuntime();

        try {
            Process p = r.exec(cmd + bar);
            org.pck.bcks.helpers.Utils.printOSCommandResults(p, response);
        } catch (IOException e) {
            System.out.println("Problem executing ysug - TestCase");
            response.getWriter()
                    .println(org.pck.esapi.ESAPI.encoder().encodeForHTML(e.getMessage()));
            return;
        }
    }
}