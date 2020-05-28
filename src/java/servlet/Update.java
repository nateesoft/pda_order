package servlet;

import control.OptionControl;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import sun.natee.project.util.ToUTF8;

@WebServlet(name = "Update", urlPatterns = {"/Update"})
public class Update extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String r_Index = ToUTF8.toString(request.getParameter("txtRIndex"));
        String type = request.getParameter("chkType");

        String opt = "";

        String[] chkOpt = (String[]) request.getParameterValues("chkOpt");
        if (chkOpt != null) {
            for (String chkOpt1 : chkOpt) {
                opt += ToUTF8.toString(chkOpt1) + ",";
            }
        }
        
        String optAdd1 = (String) request.getParameter("optAddNew");
        if (optAdd1 != null) {
            optAdd1 = ToUTF8.toString(optAdd1);
            opt += optAdd1 + ",";
        }

        OptionControl oControl = new OptionControl();

        //update option        
        if (oControl.updateOption(r_Index, opt, type)) {
            response.sendRedirect("Order.jsp?prefix=A");
        } else {
            out.println("ไม่สามารถอัพเดตรายการสินค้านี้ได้ !!!");
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

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
