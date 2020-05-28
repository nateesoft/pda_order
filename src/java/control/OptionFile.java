package control;

import database.MySQLConnect;
import java.sql.ResultSet;
import sun.natee.project.util.ThaiUtil;

public class OptionFile {

    public static String[] getListOption(String PGroup) {
        String opt = "";
        try {
            String sql = "select * from optionfile where PGroup =  '" + PGroup + "';";
            ResultSet rs = MySQLConnect.getResultSet(sql);
            while (rs.next()) {
                opt += ThaiUtil.ASCII2Unicode(rs.getString("OptionName")) + ",";
            }
            rs.close();
            MySQLConnect.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return opt.split(",");
    }
}
