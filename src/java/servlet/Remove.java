package servlet;

import control.BalanceControl;
import control.Option;
import control.ProductBean;
import control.ProductControl;
import control.STCardBean;
import control.StockControl;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import sun.natee.project.util.ThaiUtil;
import sun.natee.project.util.ToUTF8;

@WebServlet(name = "Remove", urlPatterns = {"/Remove"})
public class Remove extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String PCode = (String) request.getParameter("PCode");
        String R_Index = ToUTF8.toString((String) request.getParameter("R_Index"));
        String Prefix = (String) request.getParameter("prefix");

        StockControl stockControl = new StockControl();
        STCardBean stcBean = new STCardBean();
        double R_Quan;
        String username = "";

        HttpSession session = request.getSession();
        String R_ETD = (String) session.getAttribute("saleType");
        String MACNO = (String) session.getAttribute("macno");
        String R_Table = ToUTF8.toString((String) session.getAttribute("tableNo"));

        String stockPOS;
        ProductControl pControl = new ProductControl();
        SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss");

        String[] data;
        data = Option.splitPrice(PCode);
        R_Quan = -Double.parseDouble(data[1]);
        PCode = data[0];

        ProductBean productBean = pControl.getData(PCode);
        BalanceControl balance = new BalanceControl();

        /* สินค้านี้จัดทำสต็อกหรือไม่ */
        if (productBean.getPStock().equals("Y")) {
            System.out.println("เป็นสินค้าจัดทำสต็อก");

            stockPOS = stockControl.GET_STOCK_NAME(PCode, R_Table, MACNO);
            //Update ข้อมูลลงตาราง StkFile, Insert ข้อมูลลง StCard
            if (balance.checkQuantity(R_Table, PCode, R_Quan)) {
                //สำหรับเพิ่มรายการ
                if (R_Quan > 0) {
                    //update stkfile
                    stockControl.updateSTKFileAdd(PCode, stockPOS, (int) R_Quan);
                } //สำหรับลดรายการ หรือ Void
                else if (R_Quan < 0) {
                    //update stkfile
                    stockControl.updateSTKFileVoid(PCode, stockPOS, (int) R_Quan);
                }

                //insert stcard จะทำเหมือนกันทั้งการเพิ่มสินค้า หรือ void สินค้าออก                                    
                stcBean.setS_Rem("SAL");
                stcBean.setS_User(username);
                stcBean.setS_Stk(stockPOS);
                stcBean.setS_PCode(PCode);
                stcBean.setS_No("1" + s.format(new Date()));
                stcBean.setS_Que(0);
                stcBean.setS_Out(R_Quan);
                stcBean.setS_InCost(0);
                stcBean.setS_OutCost(R_Quan * productBean.getPPrice11());
                stcBean.setS_ACost(0);
                stcBean.setS_SubNo("");
                stcBean.setS_Link("");

                stockControl.saveSTCard(stcBean, R_ETD);
            }
        }

        if (balance.deleteItemBill(R_Index)) {
            BalanceControl bc = new BalanceControl();
            bc.updateProSerTable(R_Table, null);

            response.sendRedirect("Order.jsp?prefix=" + Prefix);
        } else {
            PrintWriter out = response.getWriter();
            out.print("ไม่สามารถลบข้อมูลการสั่งซื้อได้ !!!");
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
