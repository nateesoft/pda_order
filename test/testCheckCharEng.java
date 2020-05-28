/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Dell-Softpos
 */
public class testCheckCharEng {

    public void charEngCheck() {
        String s = "สด2a1s";
        if (s.matches(".*[a-z].*")) {
            // Do something
            s = s.toUpperCase();
            System.out.println(s);

        }
    }

    public static void main(String[] args) {
        testCheckCharEng chE = new testCheckCharEng();
        chE.charEngCheck();
    }

}
