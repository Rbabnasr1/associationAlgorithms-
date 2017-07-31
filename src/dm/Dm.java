/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dm;

import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author rabab
 */
public class Dm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        data d = new data();
        int s = 0, c = 0;
        Scanner n = new Scanner(System.in);
        System.out.println("==================================");
        System.out.println("Enter min Support Count ");
        System.out.println("==================================");
        s = n.nextInt();
        System.out.println("==================================");
        System.out.println("Enter min Confidance Count ");
        System.out.println("==================================");
        c = n.nextInt();
        d.data(s, c);
        d.display();

        //  d.display();
    }

}
