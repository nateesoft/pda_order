<%@page import="control.TableFileBean"%>
<%@page import="control.TableFileControl"%>
<%@page import="control.EmployControl"%>
<%@page import="database.MySQLConnect"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>PDA-SOFTPOS</title>
        <style type="text/css">
            .el08 {
                width:4em;
                height:4em;
            }
        </style>
        <script type="text/javascript" src="jquery-latest.min.js"></script>
        <script type="text/javascript">
            function ready() {
                var idTableCode = document.getElementById("txtTableCode");

                //focust table no
                idTableCode.focus();

                var idEmpCode = document.getElementById("txtEmpCode");
                if (idEmpCode.value === "") {
                    idEmpCode.focus();
                } else {
                    idTableCode.focus();
                }
            }
            function valid() {
                var tcode = document.getElementById("txtTableCode");
                var tcust = document.getElementById("txtCustCount");
                var custCount = parseInt(tcust.value);
                if (tcode.value === "") {
                    alert("Please Type Table !!!");
                    tcode.focus();
                    return false;
                } else if (tcust.value === "") {
                    alert("Please Type Qty Customer !!!");
                    tcust.focus();
                    return false;
                } else if (custCount <= 0) {
                    alert("Please Input Customer more than 1 !!!");
                    tcust.focus();
                    return false;
                } else {
                    return true;
                }
            }
            function show1() {
                document.getElementById("radio1").checked = "CHECKED";
            }

            function show2() {
                document.getElementById("radio2").checked = "CHECKED";
            }
            function show3() {
                document.getElementById("radio3").checked = "CHECKED";
            }

            function loadCustomer() {
                $.get("GetCustCount",{tableNo: $("#txtTableCode").val()}, function(data, textStatus) {
                    document.getElementById("txtCustCount").value = parseInt(data);
                }, "text");
            }
            
            function loadCust(evt){
                if(evt===13){
                    loadCustomer();
                }
            }

        </script>
    </head>
    <body onload="ready();" style="background-color: #FF9;">
        <%
            String macNo = (String) session.getAttribute("macno");
            if (macNo == null || macNo.equals("")) {
                out.println("Please Setting Macno <br />(ฐานข้อมูล: " + MySQLConnect.DB + ")");
            } else {
        %>
        <form action="Login" method="post">
            <table width="100%" style="position:absolute; top:10%; border: 1px solid; background-color: #FFF;">
                <tr>
                    <td height="79" colspan="3" align="center" bgcolor="#FF6699" style="font-size: 28px; font-weight: bold; color: #FFF;">Login Database (<%=MySQLConnect.server%> <%=MySQLConnect.DB%>)
                        <br /><span style="font-size: 12px;">SoftPOS - PDA V.Eng.1.0 :[19-12-2015]</span>
                    </td>
                </tr>
                <tr>
                    <td width="595" height="79" align="right" bgcolor="#0099FF" style="font-size: 28px;">Macno</td>
                    <td width="461" style="font-size: 28px;"><%=macNo%>
                        <input type="hidden" name="txtMacNo" style="font-size: 28px;" value="<%=macNo%>" />
                    </td>
                    <td width="481"></td>
                </tr>
                <%
                    EmployControl empCon = new EmployControl();
                    if (empCon.checkEmployUse()) {
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
                %>
                <tr>
                    <td width="595" height="78" align="right" bgcolor="#0099FF" style="font-size: 28px;">Employee</td>
                    <td colspan="2">
                        <input type="number" style="font-size: 28px; width: 150px;" name="txtEmpCode" id="txtEmpCode" autocomplete="off" value="<%=cEmpCode%>" />
                        <input name="chkRemember" type="checkbox" id="chkRemember" checked="checked">
                        Remember</td>
                </tr>

                <% }%>
                <tr>
                    <td height="72" align="right" bgcolor="#0099FF" style="font-size: 28px;">Table No.</td>
                    <td>
                        <input type="text<%=MySQLConnect.inputTable%>" name="txtTableCode" style="font-size: 28px; width: 95px;" id="txtTableCode" autocomplete="off" onblur="loadCustomer();" onkeypress="loadCust(event);" />
                    </td>
                    <td></td>
                </tr>
                <tr>
                    <td height="63" align="right" bgcolor="#0099FF" style="font-size: 28px;">ลูกค้า</td>
                    <td>
                        <input type="number" id="txtCustCount" style="font-size: 28px;text-align: right; width: 95px;" name="txtCustCount" autocomplete="off" value="1" />
                        <span style="font-size: 28px;">Guest</span>
                    </td>
                    <td style="font-size: 28px;"></td>
                </tr>
                <tr>
                    <td height="56" align="right" bgcolor="#0099FF" style="font-size: 28px;">Order Type</td>
                    <td colspan="2" bgcolor="#FFFFFF">
                        <input type="radio" name="chkType" id="radio1" value="E" checked="CHECKED">
                        <input type="button" style="font-size: 28px; width: 250px;" onclick="show1();" value="Dinning"><br />
                        <input type="radio" name="chkType" id="radio2" value="T">
                        <input type="button" style="font-size: 28px; width: 250px;" onclick="show2();" value="Take Away"><br />
                        <input type="radio" name="chkType" id="radio3" value="D">
                        <input type="button" style="font-size: 28px; width: 250px;" onclick="show3();" value="Delivery"><br />
                    </td>
                </tr>
                <tr>
                    <td colspan="3" align="right" bgcolor="#FFFFFF">
                        <input name="Submit" type="submit" style="font-size: 28px; height: 80px; width: 100%; background-color: #390; color: #FFF;" value="Order" onClick="return valid();" />
                    </td>
                </tr>
            </table>

        </form>
        <% }%>
    </body>
</html>
