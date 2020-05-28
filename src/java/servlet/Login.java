package servlet;

import control.BalanceBean;
import control.BalanceControl;
import control.CharactorCheck;
import control.EmployControl;
import control.TableFileControl;
import database.MySQLConnect;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sun.natee.project.util.ThaiUtil;
import sun.natee.project.util.ToUTF8;

@WebServlet(name = "Login", urlPatterns = {"/Login"})
public class Login extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            MySQLConnect mySQLConnect = new MySQLConnect();
            MySQLConnect.close();
        } catch (Exception e) {
            out.println(e.getMessage());
        }

        HttpSession session = request.getSession();

        String macNo = (String) session.getAttribute("macno");

        String empCode = (String) request.getParameter("txtEmpCode");
        String sEmpCode = (String) session.getAttribute("empCode");
        if (!sEmpCode.equals("")) {
            empCode = sEmpCode;
        } else if (empCode == null) {
            empCode = "";
        }

        CharactorCheck charEngCheck = new CharactorCheck();
        String tableNo = charEngCheck.charEngCheck(ToUTF8.toString((String) request.getParameter("txtTableCode")));
        String sTableNo = charEngCheck.charEngCheck(ToUTF8.toString((String) session.getAttribute("tableNo")));
        if (!sTableNo.equals("")) {
            tableNo = sTableNo;
        } else if (tableNo == null) {
            tableNo = "";
        }

        String custCount = (String) request.getParameter("txtCustCount");
        String sCustCount = (String) session.getAttribute("custCount");
        if (!sCustCount.equals("")) {
            custCount = sCustCount;
        } else if (custCount == null) {
            custCount = "1";
        }

        String saleType = (String) request.getParameter("chkType");
        String SSaleType = (String) session.getAttribute("saleType");
        if (!SSaleType.equals("")) {
            saleType = SSaleType;
        } else if (saleType == null) {
            saleType = "E";
        }

        String prefix = charEngCheck.charEngCheck(ToUTF8.toString((String) request.getParameter("prefix")));
        if (prefix == null) {
            prefix = "";
        } else {
            prefix = "?prefix=" + prefix;
        }

        //check table
        TableFileControl tfControl = new TableFileControl();
        EmployControl emControl = new EmployControl();

        if (emControl.checkEmployUse()) {
            if (empCode.equals("")) {
                out.println("<h1>ท่านต้องระบุรหัสบริกร ก่อนเปิดโต๊ะสั่งอาหาร!!!</h1>");
                out.println("<h3>เหตุผล</h3>");
                out.println("<h5>- เนื่องจากมีการกำหนดจากระบบให้มีการใช้งานรหัสบริกร</h5>");
                out.println("<a href=\"Welcome?macno=" + macNo + "\"><input type='button' style='height: 100px; width: 250px;' value='Back' /></a>");
            } else {

                // check employ
                if (!emControl.isEmployExists(empCode)) {
                    out.println("<h1>ท่านระบุรหัสบริกรไม่ถูกต้อง กรุณาตรวจสอบ!!!</h1>");
                    out.println("<h3>เหตุผล</h3>");
                    out.println("<h5>- เนื่องจากมีการกำหนดจากระบบให้มีการใช้งานรหัสบริกร</h5>");
                    out.println("<a href=\"Welcome?macno=" + macNo + "\"><input type='button' style='height: 100px; width: 250px;' value='Back' /></a>");
                } else {
                    int check = tfControl.checkTableRead(tableNo, empCode, macNo);
                    if (check == TableFileControl.TABLE_READY || check == TableFileControl.TABLE_EXIST_DATA) {
                        BalanceControl bControl = new BalanceControl();
                        ArrayList<BalanceBean> listBalance = bControl.getAllBalance(tableNo);

                        request.setAttribute("listBalance", listBalance);

                        session.setAttribute("tableNo", tableNo);
                        session.setAttribute("empCode", empCode);
                        session.setAttribute("custCount", custCount);
                        session.setAttribute("saleType", saleType);

                        String chkRem = (String) request.getParameter("chkRemember");
                        if (chkRem != null) {
                            //set cookie
                            Cookie c = new Cookie("c_empcode", empCode);
                            c.setMaxAge(60 * 60 * 8);
                            response.addCookie(c);
                        } else {
                            Cookie UIDCookie = new Cookie("c_empcode", "");
                            UIDCookie.setMaxAge(0);
                            response.addCookie(UIDCookie);
                        }

                        tfControl.updateTableActive(tableNo, custCount, empCode, macNo);

                        RequestDispatcher req = request.getRequestDispatcher("/main.jsp" + prefix);
                        req.forward(request, response);
                    } else if (check == TableFileControl.TABLE_EXIST_DATA_IS_ACTIVE) {
                        out.println("<h1>Employee : " + TableFileControl.USER_USE + " Ussing !!!</h1>");
                        out.println("<h3>Reason</h3>");
                        out.println("<h5>- Cannot Use 2 Employee</h5>");
                        out.println("<a href=\"Welcome?macno=" + macNo + "\"><input type='button' style='height: 100px; width: 250px;' value='Back' /></a>");
                    } else {
                        out.println("<h1>Please Check Config!!!</h1>");
                        out.println("<h3>Reason</h3>");
                        out.println("<h5>- Table Out of Control</h5>");
                        out.println("<a href=\"Welcome?macno=" + macNo + "\"><input type='button' style='height: 100px; width: 250px;' value='Back' /></a>");
                    }
                }
            }

            MySQLConnect.close();
        } else {
            int check;
            if (!prefix.equals("")) {
                check = 1;
            } else {
                check = tfControl.checkTableRead(tableNo, empCode, macNo);
            }

            if (check == TableFileControl.TABLE_READY || check == TableFileControl.TABLE_EXIST_DATA) {
                BalanceControl bControl = new BalanceControl();
                ArrayList<BalanceBean> listBalance = bControl.getAllBalance(tableNo);

                request.setAttribute("listBalance", listBalance);

                session.setAttribute("tableNo", tableNo);
                session.setAttribute("empCode", empCode);
                session.setAttribute("custCount", custCount);
                session.setAttribute("saleType", saleType);

                String chkRem = (String) request.getParameter("chkRemember");
                if (chkRem != null) {
                    //set cookie
                    Cookie c = new Cookie("c_empcode", empCode);
                    c.setMaxAge(60 * 60 * 8);
                    response.addCookie(c);
                } else {
                    Cookie UIDCookie = new Cookie("c_empcode", "");
                    UIDCookie.setMaxAge(0);
                    response.addCookie(UIDCookie);
                }

                tfControl.updateTableActive(tableNo, custCount, empCode, macNo);

                RequestDispatcher req = request.getRequestDispatcher("/main.jsp" + prefix);
                req.forward(request, response);
            } else if (check == TableFileControl.TABLE_EXIST_DATA_IS_ACTIVE) {
                out.println("<h1>พนักงาน คุณ_" + TableFileControl.USER_USE + " กำลังใช้งานโต๊ะนี้อยู่ !!!</h1>");
                out.println("<h3>เหตุผล</h3>");
                out.println("<h5>- ไม่สามารถเปิดโต๊ะใช้งานซ้ำกันได้</h5>");
                out.println("<a href=\"Welcome?macno=" + macNo + "\"><input type='button' style='height: 100px; width: 250px;' value='Back' /></a>");
            } else {
                out.println("<h1>โต๊ะที่ท่านเรียกใช้งาน ยังไม่ถูกตั้งค่าให้ใช้งานได้!!!</h1>");
                out.println("<h3>เหตุผล</h3>");
                out.println("<h5>- อาจเป็นโต๊ะที่ปิดปรับปรุง หรือไม่กำหนดให้ปิดชั่วคราว</h5>");
                out.println("<a href=\"Welcome?macno=" + macNo + "\"><input type='button' style='height: 100px; width: 250px;' value='Back' /></a>");
            }

            MySQLConnect.close();
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
