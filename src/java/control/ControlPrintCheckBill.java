/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import database.MySQLConnect;
import sun.natee.project.util.ThaiUtil;

/**
 *
 * @author Dell-Softpos
 */
public class ControlPrintCheckBill {

    public void PrintCheckBill(String tableNO, boolean CheckBill, String emp) {
        MySQLConnect c = new MySQLConnect();
        EmployControl empc = new EmployControl();
        if (CheckBill == true) {
            emp = ThaiUtil.Unicode2ASCII(empc.empName(emp));
            try {
                String sql = "update balance set PDAPrintCheck='Y',pdaemp='" + emp + "' "
                        + "where r_table='" + tableNO.toUpperCase() + "' "
                        + "and trantype ='PDA';";
                String sql11 = "update tablefile set chkbill='Y' where tcode='" + tableNO + "'";
                c.getConnection().createStatement().executeUpdate(sql);
                MySQLConnect.connect.createStatement().executeUpdate(sql11);
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }
}
