package Funcionalidades;

import java.util.ArrayList;

public class Reportes {
    public void printTable(ArrayList<ArrayList> table){
        for(ArrayList item : table){
            System.out.println(item.get(0) + " - " + item.get(1) + " - " + item.get(2) );
        }
//        for (int i = 0; i < table.size() ; i++) {
//            for (int j = 0; j <table.get(0).size() ; j++) {
//                System.out.println(table.get(i).get(j));
//            }
//
//        }

    }
}
