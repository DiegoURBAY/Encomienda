/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.net.*;
import org.apache.commons.codec.binary.Base64;

public class ReverseServlet extends HttpServlet {

    public static void main(String[] args) throws Exception {

        String stringUrl = "https://api.backendless.com/C15968F9-DB43-E739-FF98-F9B58EBF2C00/DC16621A-87C2-4BBD-B5EA-D742515D1771/services/Product/All";
        URL url;
        try {
            url = new URL(stringUrl);
            URLConnection uc;
            uc = url.openConnection();

            uc.setRequestProperty("X-Requested-With", "Curl");

            String userpass = "admin" + ":" + "xpertium sac";
            String basicAuth = "Basic " + new String(new Base64().encode(userpass.getBytes()));
            uc.setRequestProperty("Authorization", basicAuth);

            BufferedReader reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            StringBuilder builder = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                    builder.append(line);
                    builder.append(System.getProperty("line.separator"));

            }
            String result = builder.toString();
            System.out.println(result);
        } catch (IOException e) {
                e.printStackTrace();
        }        
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ReverseServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ReverseServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
    }
    
  private static String message = "Error during Servlet processing";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try {
            int len = req.getContentLength();
            byte[] input = new byte[len];
         
            ServletInputStream sin = req.getInputStream();
            int c, count = 0 ;
            while ((c = sin.read(input, count, input.length-count)) != -1) {
                count +=c;
            }
            sin.close();
         
            String inString = new String(input);
            int index = inString.indexOf("=");
            if (index == -1) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(message);
                resp.getWriter().close();
                return;
            }
            String value = inString.substring(index + 1);
             
            //decode application/x-www-form-urlencoded string
            String decodedString = URLDecoder.decode(value, "UTF-8");
             
            //reverse the String
            String reverseStr = (new StringBuffer(decodedString)).reverse().toString();
             
            // set the response code and write the response data
            resp.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(resp.getOutputStream());
             
            writer.write(reverseStr);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            try{
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                resp.getWriter().print(e.getMessage());
                resp.getWriter().close();
            } catch (IOException ioe) {
                throw ioe;
            }
        }
        
        
    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
