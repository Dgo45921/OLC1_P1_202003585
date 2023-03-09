package Arboles;

import java.util.ArrayList;






public class TablaT {
    public  ArrayList<ArrayList> estados = new ArrayList<>();
    public  int contador;
    public  ArrayList estado_inicial =new ArrayList();  // lista que contendrá la información del estado

    public TablaT(ArbolBinario arbolito){
        NodoArbol root = arbolito.getRaiz(); // obtenemos la raiz del árbol para luego hallar los primeros de la raiz y así comenzar con las transiciones
        ArrayList<ArrayList> tabla = arbolito.getTabla_sig_pos(); // obtenemos la tabla de siguientes posiciones del respectivo árbol
        ArrayList<NodoArbol> hojas = arbolito.getHojas(); // obtenemos las hojas del árbol
        inicializar_primerEstado(root);


        for (int i = 0; i <this.estados.size() ; i++) { // recorriendo cada fila de la tabla de estados
            ArrayList estado_actual = this.estados.get(i); // obtenemos la fila del estado actual
            ArrayList<Integer> sig_posActual = (ArrayList) estado_actual.get(1); // obtenemos las siguientes posiciones del estado actual
            for(int id_hoja: sig_posActual){ // empieza a recorrer los siguientes del estado actual
                TablaSigPos nuevaTablaSigPos = new TablaSigPos();
                ArrayList SigPos =(ArrayList)nuevaTablaSigPos.next(id_hoja, tabla).clone(); // se encarga de obtener las siguientes posiciones de la hoja que estamos analizando
                ArrayList SigPos2 = (ArrayList) SigPos.clone();

                if (verifica_TransicionesDuplicadas((ArrayList) estado_actual.get(2), SigPos2)){

                    //agrega_alFinal(SigPos2, estado_actual);

                    for (Object obj:((ArrayList)((ArrayList)estado_actual).get(2)) ){
                        if (((Transition)obj).transition.equals(SigPos.get(0))){
                            ArrayList final_state =((ArrayList) search_state(((Transition) obj).finalState));
                            ArrayList concat = new ArrayList();

                            for (Object obj2:((ArrayList)(ArrayList)final_state.get(1))){
                                concat.add(obj2);
                            }

                            if (!((ArrayList)SigPos).get(1).equals(((ArrayList)final_state).get(1))){

                                for (Object obj2:((ArrayList)(ArrayList)SigPos.get(1))){
                                    if (!concat.contains(obj2)){
                                        concat.add(obj2);
                                    }
                                }
                            }



                            ArrayList nuevo_estado = new ArrayList();
                            nuevo_estado.add("S"+contador);
                            nuevo_estado.add(concat);
                            nuevo_estado.add(new ArrayList<>());
                            nuevo_estado.add(false);
                            if (!stateExists(nuevo_estado)){
                                if (check_uses(((Transition)obj).finalState)){
                                    this.estados.add(nuevo_estado);
                                    ((ArrayList)SigPos).set(1, concat);
                                    Transition transicion = new Transition((String) estado_actual.get(0),  SigPos.get(0) + "", "S"+contador);
                                    ((ArrayList)((ArrayList)estado_actual).get(2)).add(transicion);
                                    contador++;
                                }
                                else {
                                    //System.out.println("x");
                                    final_state.set(1, concat);

                                }


                            }
                            else{
                                String res = getStateName(concat);
                                Transition transicion = new Transition((String) estado_actual.get(0),  SigPos.get(0) + "", res);
                                ((ArrayList)((ArrayList)estado_actual).get(2)).add(transicion);
                            }

                            break;
                        }

                    }


                    //deletetrans((ArrayList) estado_actual.get(2), SigPos);
                    for (int k = 0; k <((ArrayList)(ArrayList) estado_actual.get(2)).size() ; k++) {
                        if (((Transition)((ArrayList)((ArrayList)estado_actual.get(2))).get(k)).transition.equals(SigPos.get(0))){
                            ((ArrayList)(ArrayList) estado_actual.get(2)).remove(k);
                            break;
                        }
                    }


                    Hoja hojita =new Hoja();
                    if (hojita.es_aceptacion(id_hoja, hojas)){
                        estado_actual.set(3, true);
                    }
                    if (SigPos2.get(0).equals("")){
                        continue;
                    }
                    continue;
                }
                else{
                    boolean existente = false;
                    String lexema_found = "";
                    for (ArrayList e : this.estados){ // verifica si un estado ya existe, revisa la lista de estados y compara el conjunto de transiciones de cada uno de los estados
                        if (e.get(1).equals(SigPos.get(1))){
                            existente = true;
                            lexema_found = (String) e.get(0);
                            break;
                        }
                    }

                    if (!existente){
                        Hoja hojita =new Hoja();
                        if (hojita.es_aceptacion(id_hoja, hojas)){
                            estado_actual.set(3, true);
                        }
                        if (SigPos.get(0).equals("")){
                            continue;
                        }


                        ArrayList nuevo_estado = new ArrayList();
                        nuevo_estado.add("S"+contador);
                        nuevo_estado.add(SigPos.get(1));
                        nuevo_estado.add(new ArrayList());
                        nuevo_estado.add(false);


                        if (!verifica_TransicionesDuplicadas((ArrayList) estado_actual.get(2), SigPos)){
                            Transition transicion = new Transition((String) estado_actual.get(0),  SigPos.get(0) + "", (String) nuevo_estado.get(0));
                            ((ArrayList)estado_actual.get(2)).add(transicion);
                            contador ++;
                            estados.add(nuevo_estado);
                        }
                        else{

                            contador ++;
                            ((ArrayList)((ArrayList)nuevo_estado).get(1)).addAll((ArrayList)sig_posActual);
                            estados.add(nuevo_estado);
                            deleteDuplicates((ArrayList) estado_actual.get(2), SigPos, nuevo_estado);
                            // acá debo de agregar un nuevo estado con uniones de los duplicados
                        }

                    }
                    else{
                        Hoja hojaa = new Hoja();
                        if(hojaa.es_aceptacion(id_hoja, hojas)){
                            estado_actual.set(3, true);
                        }

                        boolean trans_exist = false;

                        for(Object trans : (ArrayList)estado_actual.get(2)){
                            Transition t = (Transition) trans;
                            if(t.comparar(estado_actual.get(0) + "", sig_posActual.get(0) + "")){
                                trans_exist = true;
                                break;
                            }
                        }
                        if(!trans_exist){
                            Transition trans = new Transition(estado_actual.get(0) + "", SigPos.get(0) + "", lexema_found + "");((ArrayList)estado_actual.get(2)).add(trans);

                        }

                    }
                }




            }
        }

    }


    public void inicializar_primerEstado(NodoArbol root ){
        estado_inicial.add("S0"); // primero agregamos el nombre del estado [0]
        estado_inicial.add(root.primeros); // acá agregamos los id de las hojas con las que S0 tiene transición [1]
        estado_inicial.add(new ArrayList<>()); // acá agregamos las transiciones que tiene S0 [2]
        estado_inicial.add(false); // definimos que no es un estado de aceptación [3]

        this.estados.add(estado_inicial); // a la tabla de estados agregamos una fila estado, en este caso el estado inicial
        this.contador = 1;
    }

    public void impTable(){
        for(ArrayList state : this.estados){
            String tran = "[";
            for(Object tr : (ArrayList)state.get(2)){
                Transition t = (Transition) tr;
                tran += t.toString() + ", ";
            }
            tran += "]";

            tran = tran.replace(", ]", "]");

            System.out.println(state.get(0) + " " + state.get(1) + " " + tran + " " + state.get(3));
        }
    }

    public boolean verifica_TransicionesDuplicadas(ArrayList transiciones, ArrayList SigPos){
        for(Object t:  transiciones){
            if (((Transition)t).transition.equals(SigPos.get(0))){
               // System.out.println("el estado: "+ ((Transition) t).initialState+ " tiene conflicto con la transicion: "+ ((Transition) t).transition);
                if (((Transition) t).initialState.equals("S0")){
                    System.out.println("a");
                }
                return true;
                }
            }
        return false;
        }


    public void deleteDuplicates(ArrayList transiciones, ArrayList SigPos, ArrayList nuevo_estado){
        for (Object t : transiciones){
            if (((Transition)t).transition.equals(SigPos.get(0))){
                ((Transition)t).finalState = nuevo_estado.get(0).toString();
            }
        }

    }


    public boolean stateExists(ArrayList estado){
        for (ArrayList e : this.estados){ // verifica si un estado ya existe, revisa la lista de estados y compara el conjunto de transiciones de cada uno de los estados
           if (e.get(1).equals(estado.get(1))){
               return true;
            }
        }
        return false;
    }

    public Object search_state(String id_state){
        for (Object obj:this.estados){
            if (((ArrayList)obj).get(0).equals(id_state)){
                return obj;
            }
        }
        return null;
    }


    public ArrayList<ArrayList> getEstados() {
        return estados;
    }

    public String getStateName(ArrayList concat){
        String nombre = "";
        for (ArrayList e : this.estados){ // verifica si un estado ya existe, revisa la lista de estados y compara el conjunto de transiciones de cada uno de los estados
            if (e.get(1).equals(concat)){
                nombre =(String) e.get(0);
            }
        }
        return  nombre;
    }

    public boolean check_uses(String name_estado){
        int contador = 0;
        for (ArrayList e : this.estados){ // verifica si un estado ya existe, revisa la lista de estados y compara el conjunto de transiciones de cada uno de los estados
            ArrayList transiciones = (ArrayList) e.get(2);
            for (int i = 0; i <transiciones.size() ; i++) {
                Transition t = (Transition) transiciones.get(i);
                if (t.finalState.equals(name_estado)){
                    contador++;
                }
            }
        }

        if (contador >1){
            return true;
        }

        return false;
    }

}

