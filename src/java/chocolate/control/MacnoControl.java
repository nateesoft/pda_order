package chocolate.control;

import database.MySQLConnect;
import java.sql.ResultSet;

public class MacnoControl {
    
    public static int MACNO_AVAILABLE = 1;
    public static int MACNO_NOT_AVAILABLE = 2;
    public static int NO_CONNECTION = 3;
    public static int NOT_FOUND_MACNO = 4;
    
    public static int checkMacno(String macno){
        try {
            String sql = "select * "
                    + "from poshwsetup "
                    + "where Terminal='"+macno+"' ";
            ResultSet i = MySQLConnect.getResultSet(sql);
            if(i.next()){
                if(i.getString("OnAct").equals("Y")){
                    return MACNO_NOT_AVAILABLE;
                }else{
                    return MACNO_AVAILABLE;//Macno ready
                }
            }else{
                return NOT_FOUND_MACNO;//Macno not available
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return NO_CONNECTION;//No connect database
        }
    }
}
