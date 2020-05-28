package control;

import chocolate.control.CompanyMenu;
import database.MySQLConnect;
import java.sql.ResultSet;
import java.util.ArrayList;
import sun.natee.project.util.ThaiUtil;

public class ControlMenu {
    
    private ArrayList<CompanyMenu> companyMenu;
    int size = 0;
    
    public ControlMenu(){
        companyMenu = new ArrayList<>();
    }

    public ArrayList<MenuSetup> getDataMenu(String prefix) {
        ArrayList<MenuSetup> listMenu = new ArrayList<>();
        MenuSetup menu;
        String search;

        for (int i = 0; i < 28; i++) {
            int index = i + 1;
            if (index < 10) {
                search = prefix + "0" + index;
            } else {
                search = prefix + index;
            }

            try {
                String sql = "select Code_ID, ShortName,Code_Type,PCode "
                        + "from menusetup "
                        + "where Code_ID='" + search + "'";
                //System.out.println(sql);
                ResultSet rs = MySQLConnect.getResultSet(sql);
                if (rs.next()) {
                    menu = new MenuSetup();
                    menu.setCode_ID(rs.getString("Code_ID"));
                    menu.setCode_Type(rs.getString("Code_Type"));
                    menu.setShortName(ThaiUtil.ASCII2Unicode(rs.getString("ShortName")));
                    menu.setPCode(rs.getString("PCode"));

                    String test = menu.getShortName();
                    String[] t = test.split(" ");
                    String plus = "";
                    for (int aa = 0; aa < t.length; aa++) {
                        if (t[aa].equals("")) {

                        } else {
                            if (aa == t.length - 1) {
                                plus += t[aa];
                            } else {
                                plus += t[aa] + "<br />";
                            }
                        }
                    }

                    menu.setShortName(plus);

                    if (!menu.getPCode().equals("")) {
                        try {
                            String sql2 = "select pcode,pstatus "
                                    + "from product "
                                    + "where pstatus='s' "
                                    + "and pcode='" + menu.getPCode() + "';";
                            ResultSet rs2 = MySQLConnect.getResultSet(sql2);
                            if (rs2.next()) {
                                menu = new MenuSetup();
                                menu.setCode_ID(search);
                                menu.setShortName("");
                                menu.setCode_Type("");
                                listMenu.add(menu);
                            } else {
                                listMenu.add(menu);
                            }
                        } catch (Exception e) {
                            System.err.println(e.getMessage());
                        }
                    } else {
                        listMenu.add(menu);
                    }
                } else {
                    menu = new MenuSetup();
                    menu.setCode_ID(search);
                    menu.setShortName("");
                    menu.setCode_Type("");
                    listMenu.add(menu);
                }

                rs.close();

            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }

        MySQLConnect.close();

        return listMenu;
    }

    public ArrayList<MenuSetup> getDataMenuSearch(String search) {
        ArrayList<MenuSetup> listBean = new ArrayList<>();
        try {
            String sql = "select m.* "
                    + "from product p,menusetup m "
                    + "where p.pcode=m.pcode "
                    + "and PFix='F' "
                    + "and PStatus='P' "
                    + "and PActive='Y' "
                    + "and m.pcode<>'' "
                    + "and shortname like '%" + ThaiUtil.Unicode2ASCII(search) + "%';";
            ResultSet rs = MySQLConnect.getResultSet(sql);
            while (rs.next()) {
                MenuSetup m = new MenuSetup();
                m.setPCode(rs.getString("PCode"));
                m.setShortName(ThaiUtil.ASCII2Unicode(rs.getString("ShortName")));

                listBean.add(m);
            }

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return listBean;
    }
    public ArrayList<MenuSetup> getDataMenuSearchByCode(String search) {
        ArrayList<MenuSetup> listBean = new ArrayList<>();
        try {
            String sql = "select m.* "
                    + "from product p,menusetup m "
                    + "where p.pcode=m.pcode "
                    + "and PFix='F' "
                    + "and PStatus='P' "
                    + "and PActive='Y' "
                    + "and m.pcode<>'' "
                    + "and m.pcode like '%" + ThaiUtil.Unicode2ASCII(search) + "%';";
            ResultSet rs = MySQLConnect.getResultSet(sql);
            while (rs.next()) {
                MenuSetup m = new MenuSetup();
                m.setPCode(rs.getString("PCode"));
                m.setShortName(ThaiUtil.ASCII2Unicode(rs.getString("ShortName")));

                listBean.add(m);
            }

            rs.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }

        return listBean;
    }

    public String[] getHeaderMenu() {
        String hMenu = "";
        try {
            String sqlHead = "select head1, head2, head3, head4 from headmenu";
            ResultSet rs = MySQLConnect.getResultSet(sqlHead);
            if (rs.next()) {
                hMenu += ThaiUtil.ASCII2Unicode(rs.getString("head1")) + ",";
                hMenu += ThaiUtil.ASCII2Unicode(rs.getString("head2")) + ",";
                hMenu += ThaiUtil.ASCII2Unicode(rs.getString("head3")) + ",";
                hMenu += ThaiUtil.ASCII2Unicode(rs.getString("head4"));
            }

            rs.close();
            MySQLConnect.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        return hMenu.split(",", hMenu.length());
    }
    
    public ArrayList<CompanyMenu> getAllMenu() {
        String sqlHead = "select head1, head2, head3, head4 from headmenu";

        try {
            ResultSet rs = MySQLConnect.getResultSet(sqlHead);
            if(rs!=null){
                if (rs.next()) {
                    String head1 = ThaiUtil.ASCII2Unicode(rs.getString("head1"));
                    String head2 = ThaiUtil.ASCII2Unicode(rs.getString("head2"));
                    String head3 = ThaiUtil.ASCII2Unicode(rs.getString("head3"));
                    String head4 = ThaiUtil.ASCII2Unicode(rs.getString("head4"));
                    String sql;
                    CompanyMenu headMenu;
                    String[] head = (head1 + "," + head2 + "," + head3 + "," + head4).split(",");
                    String[] mmenu = ("A,B,C,D").split(",");
                    int index = 0;

                    for (String h : head) {
                        if (h != null) {
                            headMenu = new CompanyMenu();
                            headMenu.setHeadName(h.trim());

                            sql = "select * from menusetup "
                                    + "where code_id like '" + mmenu[index] + "%' "
                                    + "and Code_Type='" + CompanyMenu.TYPE_GROUP + "' "
                                    + "group by Code_ID";
    //                            System.out.println("head by: "+sql);
                            ResultSet rs1 = MySQLConnect.getResultSet(sql);
                            while (rs1.next()) {
                                MenuSetup menu = new MenuSetup();
                                menu.setCode_ID(rs1.getString("Code_ID"));
                                menu.setCode_Type(rs1.getString("Code_Type"));
                                menu.setPCode(rs1.getString("PCode"));
                                menu.setShortName(ThaiUtil.ASCII2Unicode(rs1.getString("ShortName")));
                                menu.setPPathName(ThaiUtil.ASCII2Unicode(rs1.getString("PPathName")));

                                String sqlProduct = "select * from menusetup "
                                        + "where Code_Id like '" + menu.getCode_ID() + "%' "
                                        + "and Code_Type='" + CompanyMenu.TYPE_PRODUCT + "' "
                                        + "and shortName<>'' "
                                        + "group by Code_ID";
    //                                System.out.println("product by : "+sqlProduct);
                                ResultSet rs2 = MySQLConnect.getResultSet(sqlProduct);
                                while (rs2.next()) {
                                    ProductBean product = new ProductBean();
                                    product.setPCode(rs2.getString("Code_ID"));
                                    product.setPDesc(ThaiUtil.ASCII2Unicode(rs2.getString("ShortName")));

                                    menu.addProduct(product);
                                }

                                rs2.close();
                                headMenu.addMenuSetup(menu);
                            }
                            rs1.close();
                            companyMenu.add(headMenu);
                            size++;
                        }
                        index++;
                    }
                }
                rs.close();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return companyMenu;
    }
}
