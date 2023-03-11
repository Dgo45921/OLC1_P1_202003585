package AFND;

import Arboles.Transition;

import java.util.ArrayList;

public class EstadoAFND {
    private String name;
    private ArrayList<Transition> transiciones;
    private boolean aceptacion;

    public EstadoAFND(String name, ArrayList<Transition> transiciones, boolean aceptacion) {
        this.name = name;
        this.transiciones = transiciones;
        this.aceptacion = aceptacion;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Transition> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<Transition> transiciones) {
        this.transiciones = transiciones;
    }

    public boolean isAceptacion() {
        return aceptacion;
    }

    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }
}
