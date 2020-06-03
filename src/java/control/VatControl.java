package control;

import database.MySQLConnect;

public class VatControl {

    private String vatType = "";
    private String tableNo = "";
    TableFileControl tfControl = null;
    TableFileBean tBean = null;
    private double Vat = 0.00;

    public VatControl(String tableNo) {

        this.tableNo = tableNo;
        vatType = POSConfigSetup.Bean().getP_VatType();
        Vat = POSConfigSetup.Bean().getP_Vat();
        tfControl = new TableFileControl();
        tBean = tfControl.getData(tableNo);
    }

    public void updateVat() {
        if (vatType.equals("I")) {
            updateVatInclude();
        } else if (vatType.equals("E")) {
            updateVatExclude();
        }
    }

    public void updateVatInclude() {
        System.out.println("Include Vat.");

        double TAmount = tBean.getTAmount();
        double ServiceAmt = tBean.getServiceAmt();
        double ProDiscAmt = tBean.getProDiscAmt();
        double NetTotal = (TAmount+ServiceAmt)-ProDiscAmt;  //ตอนคำนวน Vat ลืมลบ ProDiscAmt

        try {
            String sql = "update tablefile "
                    + "set NetTotal='" + NetTotal + "' "
                    + "where TCode='" + tableNo + "'";
            MySQLConnect.exeUpdate(sql);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateVatExclude() {
        System.out.println("Exclude Vat.");

        double TAmount = tBean.getTAmount();
        double ServiceAmt = tBean.getServiceAmt();
        double total = TAmount+ServiceAmt;
        double NetTotal = (total*Vat/100)+total;
        
        try {
            String sql = "update tablefile "
                    + "set NetTotal='" + NetTotal + "' "
                    + "where TCode='" + tableNo + "'";
            MySQLConnect.exeUpdate(sql);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}
