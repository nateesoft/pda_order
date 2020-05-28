/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author Dell-Softpos
 */
public class CharactorCheck {

    public String charEngCheck(String table) {
        if (table.matches(".*[a-z].*")) {
            // Do something
            table = table.toUpperCase();
            System.out.println(table);
        }
        return table;
    }

}
