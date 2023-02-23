package Arboles;

import java.util.ArrayList;

public class Nodo {
    ArrayList<Integer> primeros;
    ArrayList<Integer> ultimos;
    boolean anulable;

    String lexema;
    Type.Types type;
    int id;

    boolean accept;

    Nodo izquierda;
    Nodo derecha;

    ArrayList<Nodo> hojas;
    ArrayList<ArrayList<Nodo>> tabla;

    public Nodo(String lexema, Type.Types type, int id, Nodo izquierda, Nodo derecha, ArrayList<Nodo> hojas, ArrayList<ArrayList<Nodo>> tabla) {
        primeros = new ArrayList<>();
        ultimos = new ArrayList<>();
        anulable = true;
        accept = "#".equals(this.lexema);

        this.lexema = lexema;
        this.type = type;
        this.id = id;


        this.izquierda = izquierda;
        this.derecha = derecha;

        this.hojas = hojas;
        this.tabla = tabla;
    }


}
