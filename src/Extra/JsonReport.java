package Extra;

import Arboles.ArbolBinario;
import Ventanas.Main;

import java.util.ArrayList;
import java.util.Objects;

public class JsonReport {

    public void CadenaPerteneciente(){
        for (Evaluacion eval : Main.lista_evaluaciones) {
            String key = eval.getKey();
            ArrayList tabla_transiciones = EncuentraTablaCorrespondiente(key);
            String cadena_a_evaluar = (String) (eval.getValue());
            isPerteneciente(cadena_a_evaluar, tabla_transiciones);

        }
    }


    public ArrayList EncuentraTablaCorrespondiente(String name_arbol){
        for (ArbolBinario arbolito : Main.lista_arboles){
            if (Objects.equals(arbolito.getName(), name_arbol)){
                return arbolito.getTabla_transiciones();
            }
        }
        return null;
    }

    public void isPerteneciente(String cadena, ArrayList tabla_transiciones){
        System.out.println("a");
    }

}
