package AFND;

import Arboles.NodoArbol;
import Arboles.Transition;
import Arboles.Type;

import java.util.ArrayList;


public class AFND {
    private String Estado_inicial;
    private String Estado_final;
    private ArrayList<Transition> Transiciones;
    private int contador;


    public AFND(String estado_inicial, String estado_final, ArrayList<Transition> transiciones) {
        Estado_inicial = estado_inicial;
        Estado_final = estado_final;
        Transiciones = transiciones;
    }

    public ArrayList<Transition> getTransiciones() {
        return Transiciones;
    }

    public AFND generar_AFND(NodoArbol root) {
        ArrayList<Transition> transiciones = new ArrayList<>();
        AFND izq, der;
        String fin="";
        String estado_inicial = "S"+contador;
        switch (root.type){
            case HOJA:
                break;
            case AND:
                break;
            case OR:
                break;
            case PLUS:
                break;
            case KLEENE:
                break;
            case INTERROGACION:
                break;
        }



        return new AFND(estado_inicial, "S"+(contador+1), transiciones);
    }



}