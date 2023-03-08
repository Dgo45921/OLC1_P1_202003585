package Extra;

import Arboles.ArbolBinario;
import Arboles.Transition;
import Ventanas.Main;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class JsonReport {

    public void CadenaPerteneciente(){
        for (Evaluacion eval : Main.lista_evaluaciones) {
            String key = eval.getKey();
            ArrayList tabla_transiciones = EncuentraTablaCorrespondiente(key);
            String cadena_a_evaluar = eval.getValue();
            if (isPerteneciente(cadena_a_evaluar.replace("\"", ""), tabla_transiciones)){
                System.out.println("la cadena: "+ cadena_a_evaluar + "hizo match");
            }
            else{
                System.out.println("la cadena: "+ cadena_a_evaluar + "no hizo match");
            }

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

    public boolean isPerteneciente(String cadena, ArrayList tabla_transiciones){
        boolean cambio_hecho = false;
        boolean hallado = false;
        ArrayList current_state = (ArrayList) tabla_transiciones.get(0);
        int tamanio = ((ArrayList) current_state.get(2)).size();
        outerloop:
        while (!hallado){
            // itero sobre todas las transiciones del estado actual
            int i = 0;
            while (i < tamanio)  {

                cambio_hecho =false;
                // obtengo la transicion actual
                Transition transicion_actual = (Transition) ((ArrayList)((ArrayList) current_state.get(2))).get(i);
                // reviso si la transicion actual va con cadena o es conjunto
                if (transicion_actual.transition.startsWith("\\\"")){
                    // creo la subcadena para eliminarla de la cadena actual
                    String subcadena = transicion_actual.transition.replace("\\\"", "");
                    // reviso si mi cadena original empieza con la subcadena para eliminar esa subcadena
                    if (cadena.startsWith(subcadena)){
                        cadena = cadena.replaceFirst(subcadena, "");
                        String nuevo_estado = (transicion_actual.finalState.replace("S", ""));
                        int indice_nuevo_estado = Integer.parseInt(nuevo_estado);
                        current_state = (ArrayList) tabla_transiciones.get(indice_nuevo_estado);
                        tamanio = ((ArrayList) current_state.get(2)).size();
                        i = 0;
                        cambio_hecho =true;
                        if ((Boolean)current_state.get(3)  && cadena.equals("")){
                            hallado = true;
                            break outerloop;
                        }
                    }

                }
                else{
                    ArrayList conjunto_caracteres = (ArrayList) Main.conjuntos_valor.get(transicion_actual.transition.replace("{", "").replace("}", ""));
                    if (EsPerteneciente(conjunto_caracteres, cadena)){
                        cadena = nueva_cadena(conjunto_caracteres, cadena);

                        String nuevo_estado = (transicion_actual.finalState.replace("S", ""));
                        int indice_nuevo_estado = Integer.parseInt(nuevo_estado);
                        current_state = (ArrayList) tabla_transiciones.get(indice_nuevo_estado);
                        tamanio = ((ArrayList) current_state.get(2)).size();
                        i = 0;
                        cambio_hecho =true;
                        if ((Boolean)current_state.get(3)  && cadena.equals("")){
                            hallado = true;
                            break outerloop;
                        }

                    }
                }


                if ((Boolean)current_state.get(3)  && cadena.equals("")){
                    hallado = true;
                    break outerloop;
                }

                if (!cambio_hecho) {
                    i++; // Incremento el valor de i solo si no se ha hecho ningún cambio
                }


            }
        }

        return hallado;

    }


    public boolean EsPerteneciente(ArrayList caracteres, String cadena_a_evaluar){
        for (int i = 0; i <caracteres.size() ; i++) {
            String  caracter = (String) caracteres.get(i);
            if (cadena_a_evaluar.startsWith(caracter)){
                return true;
            }
        }

        return false;
    }

    public String nueva_cadena(ArrayList caracteres, String cadena_a_evaluar){
        for (int i = 0; i <caracteres.size() ; i++) {
            String  caracter = (String) caracteres.get(i);
            if (cadena_a_evaluar.startsWith(caracter)){
               cadena_a_evaluar = cadena_a_evaluar.replaceFirst(caracter, "");
               return cadena_a_evaluar;
            }
        }

        return cadena_a_evaluar;
    }

}
