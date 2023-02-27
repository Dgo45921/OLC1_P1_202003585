package Arboles;

import java.util.ArrayList;

public class Hoja {


    public NodoArbol get_hoja(int numero_hoja, ArrayList<NodoArbol> hojas){
        for (NodoArbol elemento: hojas){
            if(elemento.id == numero_hoja){
                return elemento;
            }
        }
        return null;
    }


    public boolean es_aceptacion(int numero_hoja, ArrayList<NodoArbol> hojas){
        for (NodoArbol elemento:hojas){
            if (elemento.id==numero_hoja){
                return elemento.aceptacion;
            }
        }

        return false;
    }


}

