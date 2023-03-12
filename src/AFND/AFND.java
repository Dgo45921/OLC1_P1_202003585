package AFND;
import Arboles.Transition;
import java.util.ArrayList;


public class AFND {
    public String Estado_inicial;
    public String Estado_final;
    private ArrayList<Transition> Transiciones;


    public AFND(String estado_inicial, String estado_final, ArrayList<Transition> transiciones) {
        Estado_inicial = estado_inicial;
        Estado_final = estado_final;
        Transiciones = transiciones;
    }

    public ArrayList<Transition> getTransiciones() {
        return Transiciones;
    }




}