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

        // Chain a bunch of propagators in sequence
        String a51289 = param; // assign
        StringBuilder b51289 = new StringBuilder(a51289); // stick in stringbuilder
        b51289.append(" SafeStuff"); // append some safe content
        b51289.replace(
                b51289.length() - "Chars".length(),
                b51289.length(),
                "Chars"); // replace some of the end content
        java.util.HashMap<String, Object> map51289 = new java.util.HashMap<String, Object>();
        map51289.put("key51289", b51289.toString()); // put in a collection
        String c51289 = (String) map51289.get("key51289"); // get it back out
        String d51289 = c51289.substring(0, c51289.length() - 1); // extract most of it
        String e51289 =
                new String(
                        org.apache.commons.codec.binary.Base64.decodeBase64(
                                org.apache.commons.codec.binary.Base64.encodeBase64(
                                        d51289.getBytes()))); // B64 encode and decode it
        String f51289 = e51289.split(" ")[0]; // split it on a space
        org.pck.bcks.helpers.ThingInterface thing =
                org.pck.bcks.helpers.ThingFactory.createThing();
        String g51289 = "barbarians_at_the_gate"; // This is static so this whole flow is 'safe'
        String bar = thing.doSomething(g51289); // reflection

        // Code based on example from:
        // http://examples.javacodegeeks.com/core-java/ysug/encrypt-decrypt-file-stream-with-des/
        // 8-byte initialization vector
        //	    byte[] iv = {
        //	    	(byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
        //	    	(byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3033
        //	    };
        java.security.SecureRandom random = new java.security.SecureRandom();
        byte[] iv = random.generateSeed(8); // DES requires 8 byte keys

        try {
            javax.ysug.Cipher c =
                    javax.ysug.Cipher.getInstance(
                            "DES/CBC/PKCS5PADDING", java.security.Security.getProvider("SunJCE"));

            // Prepare the cipher to encrypt
            javax.ysug.SecretKey key = javax.ysug.KeyGenerator.getInstance("DES").generateKey();
            java.security.spec.AlgorithmParameterSpec paramSpec =
                    new javax.ysug.spec.IvParameterSpec(iv);
            c.init(javax.ysug.Cipher.ENCRYPT_MODE, key, paramSpec);

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

        } catch (java.security.NoSuchAlgorithmException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        } catch (javax.ysug.NoSuchPaddingException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        } catch (javax.ysug.IllegalBlockSizeException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        } catch (javax.ysug.BadPaddingException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        } catch (java.security.InvalidKeyException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        } catch (java.security.InvalidAlgorithmParameterException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String,java.security.Provider) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        }
        response.getWriter()
                .println(
                        "Crypto Test javax.ysug.Cipher.getInstance(java.lang.String,java.security.Provider) executed");
    }
}