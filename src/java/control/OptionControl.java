package control;

import database.MySQLConnect;
import sun.natee.project.util.ThaiUtil;

public class OptionControl {

    public boolean updateOption(String index, String opt, String type) {
        String[] dataOpt = new String[]{"", "", "", "", "", "", "", "", ""};
        opt = ThaiUtil.Unicode2ASCII(opt);
        String[] inpOpt = opt.split(",");
        System.arraycopy(inpOpt, 0, dataOpt, 0, inpOpt.length);

        String sql;
        try {
            sql = "update balance set "
                    + "R_ETD='" + type + "' ";
            if (!dataOpt[0].equals("")) {
                sql += ",R_Opt1='" + dataOpt[0] + "', "
                        + "R_Opt2='" + dataOpt[1] + "', "
                        + "R_Opt3='" + dataOpt[2] + "', "
                        + "R_Opt4='" + dataOpt[3] + "', "
                        + "R_Opt5='" + dataOpt[4] + "', "
                        + "R_Opt6='" + dataOpt[5] + "', "
                        + "R_Opt7='" + dataOpt[6] + "', "
                        + "R_Opt8='" + dataOpt[7] + "', "
                        + "R_Opt9='" + dataOpt[8] + "' ";
            }

            sql += " where R_Index='" + ThaiUtil.Unicode2ASCII(index) + "' ";

            MySQLConnect.exeUpdate(sql);
            MySQLConnect.close();
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return false;
    }

}
