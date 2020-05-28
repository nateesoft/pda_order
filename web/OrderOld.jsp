<%@page import="database.MySQLConnect"%>
<%@page import="control.ControlPrintCheckBill"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="control.BalanceControl"%>
<%@page import="control.BalanceBean"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript" src="jquery-latest.min.js"></script>
        <script type="text/javascript">
            function delData(prefix, r_index, pcode, qty) {
                $.get("Remove?prefix=" + prefix + "&R_Index=" + r_index + "&PCode=" + qty + "*" + pcode, function(responseJson) {

                    if (responseJson !== null) {
                        $.each(responseJson, function(key, value) {

                        });
                    }
                });
            }
            function detail(R_Index) {
                var text = R_Index.id;
                var prefix = document.getElementById("txtPrefix").value;
                var clickPrint = document.getElementById("print").value;
                window.location = "Order?R_Index=" + text + "&prefix=" + prefix + clickPrint;
            }
        </script>
        <link rel="stylesheet" type="text/css" href="pda.css">
    </head>
    <body>
        <%
            String prefix = (String) request.getParameter("prefix");
            if (prefix == null || prefix.equals("")) {
                prefix = "A";
            }
        %>
        <table width="100%" border="0" id="tableBill">
            <tr>
                <th width="40" bgcolor="#00CCFF">#</th>
                <th width="155" bgcolor="#00CCFF">Order Type</th>
                <th width="471" bgcolor="#00CCFF">List</th>
                <th width="57" bgcolor="#00CCFF">QTY</th>
                <th width="71" bgcolor="#00CCFF">Price</th>
                <th width="83" bgcolor="#00CCFF">Total</th>
                <th width="200" bgcolor="#00CCFF">Employee</th>
            </tr>
            <%
                BalanceControl bc = new BalanceControl();
                DecimalFormat dec = new DecimalFormat("#,##0.00");
                String table = (String) session.getAttribute("tableNo");
                if (table == null) {
                    table = "";
                }
                ArrayList<BalanceBean> listBalance = bc.getAllBalanceHold(table);
                if (listBalance == null) {
                    listBalance = new ArrayList();
                }
                int index = 0;
                for (int i = 0; i < listBalance.size(); i++) {
                    index++;
                    BalanceBean balanceBean = (BalanceBean) listBalance.get(i);
            %>
            <tr>
                <td height="55px" align="center"><%=index%></td>
                <td height="5%" align="center">
                    <%
                        if (balanceBean.getR_ETD().equals("E")) {
                            out.println("Dinning");
                        } else if (balanceBean.getR_ETD().equals("T")) {%>
                    <span style="background-color: #00ff99;">Take Away</span>
                    <%
                    } else if (balanceBean.getR_ETD().equals("D")) {%>
                    <span style="background-color: #FF9966;">Delivery</span>
                    <%} else {
                            out.println("Unknow");
                        }
                    %>
                </td>
                <td onclick="detail(this);" style="font-size: 28px;" id="<%=balanceBean.getR_Index()%>"><%=balanceBean.getR_PName()%><br />
                    <%

                        String opt1 = balanceBean.getR_Opt1();
                        String opt2 = balanceBean.getR_Opt2();
                        String opt3 = balanceBean.getR_Opt3();
                        String opt4 = balanceBean.getR_Opt4();
                        String opt5 = balanceBean.getR_Opt5();
                        String opt6 = balanceBean.getR_Opt6();
                        String opt7 = balanceBean.getR_Opt7();
                        String opt8 = balanceBean.getR_Opt8();

                        if (!opt1.equals("")) {
                            out.print("<u>" + opt1 + "</u>,");
                        }
                        if (!opt2.equals("")) {
                            out.print("<u>" + opt2 + "</u>,");
                        }
                        if (!opt3.equals("")) {
                            out.print("<u>" + opt3 + "</u>,");
                        }
                        if (!opt4.equals("")) {
                            out.print("<u>" + opt4 + "</u>,");
                        }
                        if (!opt5.equals("")) {
                            out.print("<u>" + opt5 + "</u>,");
                        }
                        if (!opt6.equals("")) {
                            out.print("<u>" + opt6 + "</u>,");
                        }
                        if (!opt7.equals("")) {
                            out.print("<u>" + opt7 + "</u>,");
                        }
                        if (!opt8.equals("")) {
                            out.print("<u>" + opt8 + "</u>,");
                        }
                    %>
                </td>
               
                <td align="right"><%=dec.format(balanceBean.getR_Quan())%></td>
                <td align="right"><%=dec.format(balanceBean.getR_Price())%></td>
                <td align="right"><%=dec.format(balanceBean.getR_Total())%></td>
                <td align="center"><%=balanceBean.getR_Emp()%></td>
            </tr>
            <% }%>
        </table>
        <p><a href="main.jsp?prefix=<%=prefix%>">                
                <input type="button" name="button" id="button" value="Back to Menu" style="width: 100%; height: 60px; font-size: 28px; background-color: #900; color: #FFF;">
            </a></p>

        <table width="100%" border="0" id="empty">
            <tr>
                <td width="76" align="center" heignt="100"><input type="button" name="button3" style="width: 100%; height: 60px; font-size: 24px; background-color: #FFE4B5; color: #fff; border-radius: 10px 0px 10px 0px; border: 1px solid;"></td>
            </tr>
        </table>
            <div align="center"> 
                <p><a href="printCheckBill.jsp?table=<%=table%>">
                        <td width="76" align="center">
                            <input type="button" name="print" id="print" value="Check Bill Print" onclick=""  style="width: 100%; height: 80px; font-size: 30px; background-color: #909; color: #fff; border-radius: 10px 0px 10px 0px; border: 1px solid;">
                        </td>
                </p>
            </div>
    </body>
</html>
