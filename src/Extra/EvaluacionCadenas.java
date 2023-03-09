package Extra;

import Arboles.ArbolBinario;
import Arboles.Transition;
import Paneles.Panel_Main;
import Ventanas.Main;
import java.util.ArrayList;
import java.util.Objects;

public class EvaluacionCadenas {

    public void CadenaPerteneciente(){
        for (Evaluacion eval : Main.lista_evaluaciones) {
            String key = eval.getKey();
            ArrayList tabla_transiciones = EncuentraTablaCorrespondiente(key);
            String cadena_a_evaluar = eval.getValue();
            cadena_a_evaluar = cadena_a_evaluar.substring(0, cadena_a_evaluar.length() - 1);
            cadena_a_evaluar = cadena_a_evaluar.replaceFirst("\"", "");
            if (isPerteneciente(cadena_a_evaluar, tabla_transiciones)){
                eval.setPerteneciente(true);
                String cadenita = "\nLa expresión: " + eval.getValue() + " es válida con la expresión regular: " + eval.getKey();
                Panel_Main.output_console.setText(Panel_Main.output_console.getText() + cadenita);
            }
            else{
                eval.setPerteneciente(false);
                String cadenita = "\nLa expresión: " + eval.getValue() + " no es válida con la expresión regular: " + eval.getKey();
                Panel_Main.output_console.setText(Panel_Main.output_console.getText() + cadenita);
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
        boolean cambio_hecho;
        boolean hallado = false;
        ArrayList current_state = (ArrayList) tabla_transiciones.get(0);
        int tamanio = ((ArrayList) current_state.get(2)).size();

            // itero sobre todas las transiciones del estado actual
            int i = 0;
            while (i < tamanio)  {
                hallado = false;
                cambio_hecho =false;
                // obtengo la transicion actual
                Transition transicion_actual = (Transition) ((ArrayList)((ArrayList) current_state.get(2))).get(i);
                // reviso si la transicion actual va con cadena o es conjunto
                if (transicion_actual.transition.startsWith("\"")){
                    // creo la subcadena para eliminarla de la cadena actual
                    String subcadena = transicion_actual.transition;
                    subcadena = subcadena.substring(0, subcadena.length() - 1);
                    subcadena = subcadena.replaceFirst("\"", "");
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
                            break;
                        }
                    }

                }
                else if (transicion_actual.transition.startsWith("\\\"")) {
                    // creo la subcadena para eliminarla de la cadena actual
                    String subcadena = "\\\"";
                    // reviso si mi cadena original empieza con la subcadena para eliminar esa subcadena
                    if (cadena.startsWith(subcadena)){
                        cadena = cadena.replaceFirst("\\\\\"", "");
                        String nuevo_estado = (transicion_actual.finalState.replace("S", ""));
                        int indice_nuevo_estado = Integer.parseInt(nuevo_estado);
                        current_state = (ArrayList) tabla_transiciones.get(indice_nuevo_estado);
                        tamanio = ((ArrayList) current_state.get(2)).size();
                        i = 0;
                        cambio_hecho =true;
                        if ((Boolean)current_state.get(3)  && cadena.equals("")){
                            hallado = true;
                            break;
                        }
                    }


                }
                else if (transicion_actual.transition.startsWith("\\n")) {
                    // creo la subcadena para eliminarla de la cadena actual
                    String subcadena = "\\n";
                    // reviso si mi cadena original empieza con la subcadena para eliminar esa subcadena
                    if (cadena.startsWith(subcadena)){
                        cadena = cadena.replaceFirst("\\\\n", "");
                        String nuevo_estado = (transicion_actual.finalState.replace("S", ""));
                        int indice_nuevo_estado = Integer.parseInt(nuevo_estado);
                        current_state = (ArrayList) tabla_transiciones.get(indice_nuevo_estado);
                        tamanio = ((ArrayList) current_state.get(2)).size();
                        i = 0;
                        cambio_hecho =true;
                        if ((Boolean)current_state.get(3)  && cadena.equals("")){
                            hallado = true;
                            break;
                        }
                    }

                }


                else if (transicion_actual.transition.startsWith("\\'")) {
                    // creo la subcadena para eliminarla de la cadena actual
                    String subcadena = "\\'";
                    // reviso si mi cadena original empieza con la subcadena para eliminar esa subcadena
                    if (cadena.startsWith(subcadena)){
                        cadena = cadena.replaceFirst("\\\\'", "");
                        String nuevo_estado = (transicion_actual.finalState.replace("S", ""));
                        int indice_nuevo_estado = Integer.parseInt(nuevo_estado);
                        current_state = (ArrayList) tabla_transiciones.get(indice_nuevo_estado);
                        tamanio = ((ArrayList) current_state.get(2)).size();
                        i = 0;
                        cambio_hecho =true;
                        if ((Boolean)current_state.get(3)  && cadena.equals("")){
                            hallado = true;
                            break;
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
                            break;
                        }

                    }
                }

                if ((Boolean)current_state.get(3)  && cadena.equals("")){
                    hallado = true;
                    break;
                }

                if (!cambio_hecho) {
                    i++; // Incremento el valor de i solo si no se ha hecho ningún cambio
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
