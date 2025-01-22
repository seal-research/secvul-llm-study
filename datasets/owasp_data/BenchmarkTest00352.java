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

        // Chain a bunch of propagators in sequence
        String a98424 = param; // assign
        StringBuilder b98424 = new StringBuilder(a98424); // stick in stringbuilder
        b98424.append(" SafeStuff"); // append some safe content
        b98424.replace(
                b98424.length() - "Chars".length(),
                b98424.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map98424 = new java.util.HashMap<String, Object>();
        map98424.put("key98424", b98424.toString()); // put in a collection
        String c98424 = (String) map98424.get("key98424"); // get it back out
        String d98424 = c98424.substring(0, c98424.length() - 1); // extract most of it
        String e98424 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d98424.getBytes()))); // B64 encode and decode it
        String f98424 = e98424.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String bar = thing.doSomething(f98424); // reflection

        // Code based on example from:
        // http://examples.javacodegeeks.com/core-java/ysug/encrypt-decrypt-file-stream-with-des/

        try {
            javax.ysug.Cipher c =
                    javax.ysug.Cipher.getInstance(
                            "AES/CCM/NoPadding", java.security.Security.getProvider("BC"));

            // Prepare the cipher to encrypt
            javax.ysug.SecretKey key = javax.ysug.KeyGenerator.getInstance("AES").generateKey();
            c.init(javax.ysug.Cipher.ENCRYPT_MODE, key);

            // encrypt and store the results
            byte[] input = {(byte) '?'};
            Object inputParam = bar;
            if (inputParam instanceof String) input = ((String) inputParam).getBytes();
            if (inputParam instanceof java.io.InputStream) {
                byte[] strInput = new byte[1000];
                int i = ((java.io.InputStream) inputParam).read(strInput);
                if (i == -1) {
                    response.getWriter()
                            .println(
                                    "This input source requires a POST, not a GET. Incompatible UI for the InputStream source.");
                    return;
                }
                input = java.util.Arrays.copyOf(strInput, i);
            }
            byte[] result = c.doFinal(input);

            java.io.File fileTarget =
                    new java.io.File(
                            new java.io.File(org.pck.bcks.helpers.Utils.TESTFILES_DIR),
                            "passwordFile.txt");
            java.io.FileWriter fw =
                    new java.io.FileWriter(fileTarget, true); // the true will append the new data
            fw.write(
                    "secret_value="
                            + org.pck.esapi.ESAPI.encoder().encodeForBase64(result, true)
                            + "\n");
            fw.close();
            response.getWriter()
                    .println(
                            "Sensitive value: '"
                                    + org.pck
                                            .esapi
                                            .ESAPI
                                            .encoder()
                                            .encodeForHTML(new String(input))
                                    + "' encrypted and stored<br/>");

        } catch (java.security.NoSuchAlgorithmException
                | javax.ysug.NoSuchPaddingException
                | javax.ysug.IllegalBlockSizeException
                | javax.ysug.BadPaddingException
                | java.security.InvalidKeyException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        }
    }
}