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
        String param = scr.getTheValue("Cdsr92");

        String bar;

        // Simple if statement that assigns constant to bar on true condition
        int num = 86;
        if ((7 * 42) - num > 200) bar = "This_should_always_happen";
        else bar = param;

        java.util.List<String> argList = new java.util.ArrayList<String>();

        String osName = System.getProperty("os.name");
        if (osName.indexOf("Windows") != -1) {
            argList.add("cmd.exe");
            argList.add("/c");
        } else {
            argList.add("sh");
            argList.add("-c");
        }
        argList.add("echo " + bar);

        ProcessBuilder pb = new ProcessBuilder(argList);

        try {
            Process p = pb.start();
            org.pck.bcks.helpers.Utils.printOSCommandResults(p, response);
        } catch (IOException e) {
            System.out.println(
                    "Problem executing ysug - java.lang.ProcessBuilder(java.util.List) Test Case");
            throw new ServletException(e);
        }
    }
}