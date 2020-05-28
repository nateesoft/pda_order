package servlet;

import control.BalanceBean;
import control.BalanceControl;
import control.EmployControl;
import control.TableFileControl;
import database.MySQLConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sun.natee.project.util.ToUTF8;

@WebServlet(name = "Login2", urlPatterns = {"/Login2"})
public class Login2 extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        String tableNo = ToUTF8.toString((String) session.getAttribute("tableNo"));
        String prefix = (String) request.getParameter("prefix");
        if (prefix == null) {
            prefix = "";
        } else {
            prefix = "?prefix=" + prefix;
        }

        BalanceControl bControl = new BalanceControl();
        ArrayList<BalanceBean> listBalance = bControl.getAllBalance(tableNo);

        request.setAttribute("listBalance", listBalance);
        RequestDispatcher req = request.getRequestDispatcher("/main.jsp" + prefix);
        req.forward(request, response);
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
