package control;

import database.MySQLConnect;
import java.sql.ResultSet;
import sun.natee.project.util.ThaiUtil;

public class EmployControl {

    public boolean checkEmployUse() {
        try {
            String sql = "select P_EmpUse from posconfigsetup where P_EmpUse='Y';";
            ResultSet rs = MySQLConnect.getResultSet(sql);
            if (rs.next()) {
                rs.close();
                MySQLConnect.close();
                return true;
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public boolean isEmployExists(String empCode) {
        try {
            String sql = "select * from employ where code='" + empCode + "'";
            ResultSet rs = MySQLConnect.getResultSet(sql);
            if (rs.next()) {
                rs.close();
                MySQLConnect.close();
                return true;
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return false;
    }

    public String empName(String empCode) {
        String empName = "";
        try {
            String sql = "select name from employ where code='" + empCode + "'";
            ResultSet rs = MySQLConnect.connect.createStatement().executeQuery(sql);
            if (rs.next() && !rs.wasNull()) {
                empName = ThaiUtil.ASCII2Unicode(rs.getString("name"));
            }
            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return empName;
    }

}
