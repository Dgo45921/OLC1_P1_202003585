package Arboles;

import java.util.ArrayList;

public class TablaSigPos {
    public void agregar_tabla_sigPos(int id, String lexema, ArrayList lista_siguientes, ArrayList<ArrayList> tabla){
        for (ArrayList elemento: tabla) {
            if( (int) elemento.get(0) == id && elemento.get(1) == lexema ){
                for (Object flwItem : lista_siguientes){
                    if(! ((ArrayList)elemento.get(2)).contains((int)flwItem) ){
                        ((ArrayList)elemento.get(2)).add(flwItem);
                    }
                }
                return;
            }
        }

        ArrayList dato = new ArrayList();
        dato.add(id);
        dato.add(lexema);
        dato.add(lista_siguientes);

        tabla.add(dato);

    }

    public ArrayList next(int numNode, ArrayList<ArrayList> table){
        ArrayList result = new ArrayList();
        for(ArrayList item : table){
            if( (int) item.get(0) == numNode ){
                result.add(item.get(1));
                result.add(((ArrayList)item.get(2)).clone());
                return result;
            }
        }
        result.add("");
        result.add(new ArrayList());
        return result;
    }


}
