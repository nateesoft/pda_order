<%-- 
    Document   : printCheckBill
    Created on : Oct 2, 2017, 6:49:52 PM
    Author     : Dell-Softpos
--%>

<%@page import="control.EmployControl"%>
<%@page import="control.ControlPrintCheckBill"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="jquery-latest.min.js"></script>
        <script type="text/javascript">
                    function printClick() {
                    var table = document.getElementById.("txtTableNo").value;
                            var prefix = document.getElementById("txtPrefix").value;
                            var table = (String) session.getAttribute("tableNo");
                            var clickPrint = document.getElementById("print").value;
            <%
                EmployControl empCon = new EmployControl();
                Cookie[] cookies = request.getCookies();
                String cEmpCode = "";
                if (cookies != null) {
                    for (int i = 0; i < cookies.length; i++) {
                        Cookie c = (Cookie) cookies[i];
                        if (c.getName().equals("c_empcode")) {
                            cEmpCode = c.getValue();
                            break;
                        }
                    }
                }
                ControlPrintCheckBill cb = new ControlPrintCheckBill();
                String tableNo = (String) session.getAttribute("tableNo");
                cb.PrintCheckBill(tableNo, true, cEmpCode);
            %>
                    }
        </script>
    </head>

    <body>
        <%
            String prefix = (String) request.getParameter("prefix");
            if (prefix == null || prefix.equals("")) {
                prefix = "A";
            }
        %>
        <p> 
        <div align="center"> 
            <p><a href="main.jsp?prefix=<%=prefix%>">
                    <td width="76" align="center">
                        <input type="button" name="print" id="print" value="Print OK!" onclick=""  style="width: 100%; height: 80px; font-size: 30px; background-color:#B22222; color: #fff; border-radius: 10px 0px 10px 0px; border: 1px solid;">
                    </td>
            </p>
        </div>
    </body>
</html>
