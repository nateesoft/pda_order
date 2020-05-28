package servlet;

import control.BalanceControl;
import control.CharactorCheck;
import control.TableFileControl;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sun.natee.project.util.ThaiUtil;
import sun.natee.project.util.ToUTF8;

@WebServlet(name = "Next", urlPatterns = {"/Next"})
public class Next extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        CharactorCheck charEngCheck = new CharactorCheck();
        String tableNo = (String) session.getAttribute("tableNo");       
//        String tableNo = ToUTF8.toString((String) session.getAttribute("tableNo"));       
        TableFileControl tfControl = new TableFileControl();
        tfControl.updateTableHold(ThaiUtil.Unicode2ASCII(charEngCheck.charEngCheck(tableNo)));
        session.setAttribute("tableNo", "");
        session.setAttribute("empCode", "");
        session.setAttribute("custCount", "");
        session.setAttribute("saleType", "");
        
        response.sendRedirect("login.jsp");
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
