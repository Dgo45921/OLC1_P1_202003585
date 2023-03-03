package Funcionalidades;

import Arboles.NodoArbol;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Reportes {
    public String codigo = "digraph G\n" +
            "{\n" +
            "fontsize=\"5\""+
            "node [  ];";



    public void dotTree(NodoArbol root) {
        if (root != null){
            root.lexema = root.lexema.replace("\"", "\\\"");
            if (root.lexema == "|"){
                root.lexema = "\\|";
            }
            codigo += "nodo" + root.id_2 + " [label=\"" + root.lexema + "\"];\n"; // crear el nodo con el id y el texto
            if (root.izquierda != null) {
                codigo += "nodo" + root.id_2 + " -> " + "nodo" + root.izquierda.id_2 + "[minlen=3 ]"+ ";\n"; // agregar la conexión con el hijo izquierdo
            }
            if (root.derecha != null) {
                codigo += "nodo" + root.id_2 + " -> " + "nodo" + root.derecha.id_2 + "[minlen=3 ]"+ ";\n"; // agregar la conexión con el hijo derecho
            }
            dotTree(root.izquierda);
            dotTree(root.derecha);
        }
    }

    public void generate_tree(int iterable){
        String dotFilePath = "ARBOLES_202003585/" + "Arbol_" +iterable + ".dot";
        String pngFilePath = "ARBOLES_202003585/" + "Arbol_" +iterable + ".png";
        codigo += "}";
        try{
            FileWriter archivo = new FileWriter(dotFilePath);
            PrintWriter pw  = new PrintWriter(archivo);
            pw.println(codigo);
            archivo.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            Process p = Runtime.getRuntime().exec("dot -Tpng " + dotFilePath + " -o " + pngFilePath);
            p.waitFor();
            System.out.println("Archivo .png creado exitosamente en " + pngFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


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


    public void Generate_SigPosTable(ArrayList<ArrayList> table, int iterable) throws IOException {
        String texto = "digraph G {\n" +
                "    rankdir=LR\n" +
                "    node [shape=none fontname=Helvetica]\n" +
                "\n" +
                "    A [label=<\n" +
                "      <TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\">\n" +
                "       <TR PORT=\"header\">\n" +
                "        <TD BGCOLOR=\"#d23939\" COLSPAN=\"3\">TABLA DE SIGUIENTES</TD>\n" +
                "       </TR>\n" +
                "       <TR>\n" +
                "        <TD BGCOLOR=\"#ff6363\">Num_hoja</TD>\n" +
                "        <TD BGCOLOR=\"#ff6363\">Simbolo</TD>\n" +
                "        <TD BGCOLOR=\"#ff6363\">Siguientes</TD>\n" +
                "       </TR>";

        for(ArrayList item : table){
            texto += "<TR>\n";
            texto += "<TD>" +item.get(0) + "</TD>" + "\n";
            texto += "<TD>" +item.get(1) + "</TD>" + "\n";
            texto += "<TD>" +item.get(2) + "</TD>" + "\n";
            texto += "</TR>\n";
        }
        texto+="</TABLE>\n" + "    >];\n" + "}";


        String dotFilePath = "SIGUIENTES_202003585/" + "siguientes_" +iterable + ".dot";
        String pngFilePath = "SIGUIENTES_202003585/" + "siguientes_" + iterable + ".png";

        try{
            FileWriter archivo = new FileWriter(dotFilePath);
            PrintWriter pw  = new PrintWriter(archivo);
            pw.println(texto);
            archivo.close();
        }catch (Exception e){
            e.printStackTrace();
        }


        try {
            Process p = Runtime.getRuntime().exec("dot -Tpng " + dotFilePath + " -o " + pngFilePath);
            p.waitFor();
            System.out.println("aaa");


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }



}
