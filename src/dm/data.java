package dm;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author rabab
 */
public class data {

    ArrayList<items> itemes = new ArrayList<items>();
    ArrayList<ItemSet> Set = new ArrayList<>();
    ArrayList<String> AllItem = new ArrayList<String>();
    ArrayList<ItemSet> Set2 = new ArrayList<>();
    ArrayList<ItemSet> Set3 = new ArrayList<>();
    ArrayList<Conf> con = new ArrayList<>();
    String[] Record = null;
    String[] split = null;
    String[] split2 = null;

    void data(int support, int conf) throws FileNotFoundException, IOException {
        RandomAccessFile ob = new RandomAccessFile("DiseasesDataSet.txt", "r");
        String text = ob.readLine();
        int up = 0;
        int down = 0, k = 0;
        String disease = null;
        items item;
        ItemSet s1;

        while (text != null) {
            item= new items();
            Record = text.split("	");

            for (int i = 1; i < Record.length; i++) {
                if (Record[i].equals("UP")) {
                    up++;
                } else if (Record[i].equals("Down")) {
                    down++;
                } else {
                    disease = Record[i];
                }

            }
            item.setUp(String.valueOf("up" + up));
            item.setDown(String.valueOf("down" + down));
            item.setDisease(disease);
            itemes.add(item);

            up = down = 0;
            text = ob.readLine();
        }

        for (int i = 0; i < itemes.size(); i++) {
            AllItem.add(itemes.get(i).getUp());
            AllItem.add(itemes.get(i).getDown());
            AllItem.add(itemes.get(i).getDisease());
        }

        int u = 0;

        int sizee = Set.size();
        s1 = new ItemSet();
        s1.setItem(itemes.get(0).getUp());

        s1.setCount(1);
        Set.add(s1);

        s1 = new ItemSet();
        s1.setItem(itemes.get(0).getDown());

        s1.setCount(1);
        Set.add(s1);

        s1 = new ItemSet();
        s1.setItem(itemes.get(0).getDisease());
        s1.setCount(1);
        Set.add(s1);

        sizee = 3;
///////////////////////////////////////////////////////  1item set
        for (int i = 3; i <AllItem.size(); i++) {

            for (int j = 0; j < sizee; j++) {
  
               
               if (AllItem.get(i).equals(Set.get(j).getItem())) {
                    s1 = new ItemSet();
                    s1.setItem(AllItem.get(i));
                    k = Set.get(j).getCount();
                    s1.setCount(++k);
                    Set.remove(j);
                    Set.add(s1);
                    u = 1;
                    break;

                }
            }
            if (u == 0) {
                s1 = new ItemSet();
                s1.setItem(AllItem.get(i));
                s1.setCount(1);
                Set.add(s1);

            } else {
                u = 0;
            }

            sizee = Set.size();
        }
        /////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////remove < support
        int y = 0;
        while (y != Set.size()) {
            if (Set.get(y).getCount() < support) {
                Set.remove(y);
                y = 0;
            } else {
                y++;
            }

        }
        ///////////////////////////////////////////////////////////////////////2itemset

        int count = 0;
        for (int i = 0; i < Set.size(); i++) {
            for (int j = i + 1; j < Set.size(); j++) {
                s1 = new ItemSet();
                s1.setItem(Set.get(i).getItem() + "   " + Set.get(j).getItem());
                for (int l = 0; l < itemes.size(); l++) {

                    if ((Set.get(i).getItem().equals(itemes.get(l).getUp()) || Set.get(i).getItem().equals(itemes.get(l).getDown()) || Set.get(i).getItem().equals(itemes.get(l).getDisease())) && (Set.get(j).getItem().equals(itemes.get(l).getUp()) || Set.get(j).getItem().equals(itemes.get(l).getDown()) || Set.get(j).getItem().equals(itemes.get(l).getDisease()))) {
                        count++;
                    }

                }
                s1.setCount(count);
                Set2.add(s1);
                count = 0;

            }

        }////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        System.out.println("=========================================================================================================");

        y = 0;
        while (y != Set2.size()) {
            if (Set2.get(y).getCount() < support) {
                Set2.remove(y);
                y = 0;
            } else {
                y++;
            }

        }
        for (int i = 0; i < Set2.size(); i++) {
            for (int j = i + 1; j < Set2.size(); j++) {
                s1 = new ItemSet();

                split = Set2.get(i).getItem().split("   ");
                split2 = Set2.get(j).getItem().split("   ");
                if (!split[0].equals(split2[0]) && !split[1].equals(split2[0])) {
                    s1.setItem(split[0] + "   " + split[1] + "   " + split2[0]);
                    for (int l = 0; l < itemes.size(); l++) {

                        if ((split[0].equals(itemes.get(l).getUp()) || split[0].equals(itemes.get(l).getDown())
                                || split[0].equals(itemes.get(l).getDisease()))
                                && (split[1].equals(itemes.get(l).getUp()) || split[1].equals(itemes.get(l).getDown())
                                || split[1].equals(itemes.get(l).getDisease()))
                                && (split2[0].equals(itemes.get(l).getUp())
                                || split2[0].equals(itemes.get(l).getDown())
                                || split2[0].equals(itemes.get(l).getDisease()))) {

                            count++;
                        }

                    }
                    s1.setCount(count);
                    Set3.add(s1);
                    count = 0;

                }
                count = 0;
                if (!split[0].equals(split2[1]) && !split[1].equals(split2[1])) {
                    s1.setItem(split[0] + "   " + split[1] + "   " + split2[1]);
                    for (int l = 0; l < itemes.size(); l++) {

                        if ((split[0].equals(itemes.get(l).getUp()) || split[0].equals(itemes.get(l).getDown())
                                || split[0].equals(itemes.get(l).getDisease()))
                                && (split[1].equals(itemes.get(l).getUp()) || split[1].equals(itemes.get(l).getDown())
                                || split[1].equals(itemes.get(l).getDisease()))
                                && (split2[1].equals(itemes.get(l).getUp())
                                || split2[1].equals(itemes.get(l).getDown())
                                || split2[1].equals(itemes.get(l).getDisease()))) {
                            count++;
                        }

                    }
                    s1.setCount(count);
                    Set3.add(s1);
                    count = 0;

                }

            }
        }

        for (int i = 0; i < Set3.size(); i++) {
            if (Set3.get(i).getCount() < support) {
                Set3.remove(i);
                i--;
            }
           
        }

        for (int i = 0; i < Set3.size(); i++) {
            for (int j = i + 1; j < Set3.size(); j++) {
                if (Set3.get(i).getItem().equals(Set3.get(j).getItem())) {
                    Set3.remove(j);
                    j--;
                }

            }

        }
        for (int j = 0; j < Set3.size(); j++) {
            split = Set3.get(j).getItem().split("   ");
            if (split[1].equals("ColonCancer") || split[1].equals("BreastCancer")) {
                Set3.remove(j);
                j--;
            }
        }

        Conf conff;
        if (Set3.size() > 0) {
            for (int i = 0; i < Set3.size(); i++) {
                conff = new Conf();
                split = Set3.get(i).getItem().split("   ");

                conff.setX(split[0]);
                conff.setY(split[1] + "   " + split[2]);
                for (int j = 0; j < Set.size(); j++) {
                    if (split[0].equals(Set.get(j).getItem())) {
                        conff.setCon(((float) Set3.get(i).getCount() / (float) Set.get(j).getCount()) * 100);
                        con.add(conff);

                    }
                }
                conff = new Conf();

                conff.setX(split[1]);
                conff.setY(split[0] + "   " + split[2]);

                for (int j = 0; j < Set.size(); j++) {
                    if (split[1].equals(Set.get(j).getItem())) {
                        conff.setCon((float) Set3.get(i).getCount() / (float) Set.get(j).getCount() * 100);
                        con.add(conff);

                    }
                }
                conff = new Conf();
                conff.setY(split[0] + "   " + split[1]);
                conff.setX(split[2]);

                for (int j = 0; j < Set.size(); j++) {
                    if (split[0].equals(Set.get(j).getItem())) {
                        conff.setCon(((float) Set3.get(i).getCount() / (float) Set.get(j).getCount()) * 100);
                        con.add(conff);

                    }
                }
                conff = new Conf();
                conff.setX(split[1] + "   " + split[2]);
                conff.setY(split[0]);
                for (int j = 0; j < Set2.size(); j++) {
                    if (conff.getX().equals(Set2.get(j).getItem())) {
                        conff.setCon(((float) Set3.get(i).getCount() / (float) Set2.get(j).getCount()) * 100);
                        con.add(conff);
                    }

                }

                conff = new Conf();
                conff.setX(split[0] + "   " + split[2]);
                conff.setY(split[1]);
                for (int j = 0; j < Set2.size(); j++) {
                    if (conff.getX().equals(Set2.get(j).getItem())) {
                        conff.setCon(((float) Set3.get(i).getCount() / (float) Set2.get(j).getCount()) * 100);
                        con.add(conff);
                    }
                }
                conff = new Conf();
                conff.setX(split[0] + "   " + split[1]);
                conff.setY(split[2]);
                for (int j = 0; j < Set2.size(); j++) {
                    if (conff.getX().equals(Set2.get(j).getItem())) {
                        conff.setCon(((float) Set3.get(i).getCount() / (float) Set2.get(j).getCount()) * 100);
                        con.add(conff);

                    }
                }
            }

        }
        else if (Set2.size()>0){
        for (int i = 0; i < Set2.size(); i++) {
                conff = new Conf();
                split = Set2.get(i).getItem().split("   ");

                conff.setX(split[0]);
                conff.setY(split[1]);
                for (int j = 0; j < Set.size(); j++) {
                    if (split[0].equals(Set.get(j).getItem())) {
                        conff.setCon(((float) Set2.get(i).getCount() / (float) Set.get(j).getCount()) * 100);
                        con.add(conff);

                    }
                }
                conff = new Conf();
                conff.setX(split[1]);
                conff.setY(split[0]);

                for (int j = 0; j < Set.size(); j++) {
                    if (split[1].equals(Set.get(j).getItem())) {
                        conff.setCon((float) Set2.get(i).getCount() / (float) Set.get(j).getCount() * 100);
                        con.add(conff);

                    }
                }
                
            }
        
        }
//////////////////////remove conf < min conf
        for (int i = 0; i < con.size(); i++) {
            if (con.get(i).getCon() < conf) {
                con.remove(i);
                i--;
            }
        }

    }

    void display() {
        System.out.println("===================");
        System.out.println("the Orignal Data ");
        System.out.println("===================");
        for (int i = 0; i < itemes.size(); i++) {
            System.out.println((i + 1) + " -- " + itemes.get(i).getUp() + " " + itemes.get(i).getDown() + " " + itemes.get(i).getDisease());

        }
        if (Set.size() > 0) {
            System.out.println("===================");
            System.out.println("one Items Set :  ");
            System.out.println("===================");

            for (int i = 0; i < Set.size(); i++) {
                System.out.println((i + 1) + " -- " + Set.get(i).getItem() + "  count " + i + " --- " + Set.get(i).getCount());
            }
        }

        if (Set2.size() > 0) {
            System.out.println("===================");
            System.out.println("two  Items Set :  ");
            System.out.println("===================");

            for (int i = 0; i < Set2.size(); i++) {
                System.out.println((i + 1) + " -- " + Set2.get(i).getItem() + "  count " + i + " --- " + Set2.get(i).getCount());
            }

        }
        if (Set3.size() > 0) {
            System.out.println("===================");
            System.out.println("three Items Set :  ");
            System.out.println("===================");
            for (int i = 0; i < Set3.size(); i++) {
                System.out.println("Set " + i + " -- " + Set3.get(i).getItem() + "  count " + i + " --- " + Set3.get(i).getCount());
            }

        }
        System.out.println("===================");
        System.out.println("final confidance  :  ");
        System.out.println("===================");

        for (int i = 0; i < con.size(); i++) {
            System.out.println("Set " + i + " -- " + con.get(i).getX() + " ---> " + con.get(i).getY() + " = " + con.get(i).getCon());
        }

    }

}
