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

        String bar = doSomething(request, param);

        String cmd = "";
        String a1 = "";
        String a2 = "";
        String[] args = null;
        String osName = System.getProperty("os.name");

        if (osName.indexOf("Windows") != -1) {
            a1 = "cmd.exe";
            a2 = "/c";
            cmd = "echo ";
            args = new String[] {a1, a2, cmd, bar};
        } else {
            a1 = "sh";
            a2 = "-c";
            cmd = org.pck.bcks.helpers.Utils.getOSCommandString("ls ");
            args = new String[] {a1, a2, cmd + bar};
        }

        String[] argsEnv = {"foo=bar"};

        Runtime r = Runtime.getRuntime();

        try {
            Process p = r.exec(args, argsEnv);
            org.pck.bcks.helpers.Utils.printOSCommandResults(p, response);
        } catch (IOException e) {
            System.out.println("Problem executing ysug - TestCase");
            response.getWriter()
                    .println(org.pck.esapi.ESAPI.encoder().encodeForHTML(e.getMessage()));
            return;
        }
    } // end doPost

    private static String doSomething(HttpServletRequest request, String param)
            throws ServletException, IOException {

        // Chain a bunch of propagators in sequence
        String a17988 = param; // assign
        StringBuilder b17988 = new StringBuilder(a17988); // stick in stringbuilder
        b17988.append(" SafeStuff"); // append some safe content
        b17988.replace(
                b17988.length() - "Chars".length(),
                b17988.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map17988 = new java.util.HashMap<String, Object>();
        map17988.put("key17988", b17988.toString()); // put in a collection
        String c17988 = (String) map17988.get("key17988"); // get it back out
        String d17988 = c17988.substring(0, c17988.length() - 1); // extract most of it
        String e17988 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d17988.getBytes()))); // B64 encode and decode it
        String f17988 = e17988.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String g17988 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
        String bar = thing.doSomething(g17988); // reflection

        return bar;
    }
}