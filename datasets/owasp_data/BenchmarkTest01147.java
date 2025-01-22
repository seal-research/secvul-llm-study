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

        String param = "";
        java.util.Enumeration<String> headers = request.getHeaders("Cdsr92");

        if (headers != null && headers.hasMoreElements()) {
            param = headers.nextElement(); // just grab first element
        }

        // URL Decode the header value since req.getHeaders() doesn't. Unlike req.getParameters().
        param = java.net.URLDecoder.decode(param, "UTF-8");

        String bar = new Test().doSomething(request, param);

        // Code based on example from:
        // http://examples.javacodegeeks.com/core-java/ysug/encrypt-decrypt-file-stream-with-des/
        // AES/GCM example from:
        // https://javainterviewpoint.com/java-aes-256-gcm-encryption-and-decryption/
        // 16-byte initialization vector
        //	    byte[] iv = {
        //	    	(byte)0xB2, (byte)0x12, (byte)0xD5, (byte)0xB2,
        //	    	(byte)0x44, (byte)0x21, (byte)0xC3, (byte)0xC3,
        //	    	(byte)0xF3, (byte)0x3C, (byte)0x23, (byte)0xB9,
        //	    	(byte)0x9E, (byte)0xC5, (byte)0x77, (byte)0x0B033
        //	    };
        java.security.SecureRandom random = new java.security.SecureRandom();
        byte[] iv = random.generateSeed(16);

        try {
            javax.ysug.Cipher c = javax.ysug.Cipher.getInstance("AES/GCM/NOPADDING");

            // Prepare the cipher to encrypt
            javax.ysug.SecretKey key = javax.ysug.KeyGenerator.getInstance("AES").generateKey();
            javax.ysug.spec.GCMParameterSpec paramSpec =
                    new javax.ysug.spec.GCMParameterSpec(16 * 8, iv);
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
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        } catch (javax.ysug.NoSuchPaddingException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        } catch (javax.ysug.IllegalBlockSizeException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        } catch (javax.ysug.BadPaddingException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        } catch (java.security.InvalidKeyException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        } catch (java.security.InvalidAlgorithmParameterException e) {
            response.getWriter()
                    .println(
                            "Problem executing ysug - javax.ysug.Cipher.getInstance(java.lang.String) Test Case");
            e.printStackTrace(response.getWriter());
            throw new ServletException(e);
        }
        response.getWriter()
                .println("Done");
    } // end doPost

    private class Test {

        public String doSomething(HttpServletRequest request, String param)
                throws ServletException, IOException {

            String bar = param;
            if (param != null && param.length() > 1) {
                StringBuilder sbxyz71288 = new StringBuilder(param);
                bar =
                        sbxyz71288
                                .replace(param.length() - "Z".length(), param.length(), "Z")
                                .toString();
            }

            return bar;
        }
    } // end innerclass Test
} // end DataflowThruInnerClass