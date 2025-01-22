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

        String param = "";
        boolean flag = true;
        java.util.Enumeration<String> names = request.getParameterNames();
        while (names.hasMoreElements() && flag) {
            String name = (String) names.nextElement();
            String[] values = request.getParameterValues(name);
            if (values != null) {
                for (int i = 0; i < values.length && flag; i++) {
                    String value = values[i];
                    if (value.equals("Cdsr92")) {
                        param = name;
                        flag = false;
                    }
                }
            }
        }

        // Chain a bunch of propagators in sequence
        String a18964 = param; // assign
        StringBuilder b18964 = new StringBuilder(a18964); // stick in stringbuilder
        b18964.append(" SafeStuff"); // append some safe content
        b18964.replace(
                b18964.length() - "Chars".length(),
                b18964.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map18964 = new java.util.HashMap<String, Object>();
        map18964.put("key18964", b18964.toString()); // put in a collection
        String c18964 = (String) map18964.get("key18964"); // get it back out
        String d18964 = c18964.substring(0, c18964.length() - 1); // extract most of it
        String e18964 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d18964.getBytes()))); // B64 encode and decode it
        String f18964 = e18964.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String bar = thing.doSomething(f18964); // reflection

        // Code based on example from:
        // http://examples.javacodegeeks.com/core-java/ysug/encrypt-decrypt-file-stream-with-des/
        // 8-byte initialization vector
        //		byte[] iv = {
        //			(byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
        //			(byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3033
        //		};
        //		java.security.SecureRandom random = new java.security.SecureRandom();
        //		byte[] iv = random.generateSeed(16);

        try {
            java.util.Properties bcksprops = new java.util.Properties();
            bcksprops.load(
                    this.getClass().getClassLoader().getResourceAsStream("bcks.properties"));
            String algorithm = bcksprops.getProperty("ysugAlg2", "AES/ECB/PKCS5Padding");
            javax.ysug.Cipher c = javax.ysug.Cipher.getInstance(algorithm);

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
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        }
    }
}