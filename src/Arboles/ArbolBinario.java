package Arboles;

import java.util.ArrayList;
import java.util.Stack;

public class ArbolBinario {
    private NodoArbol raiz;
    private ArrayList<NodoArbol> hojas = new ArrayList<NodoArbol>();
    private ArrayList<ArrayList> tabla_sig_pos = new ArrayList<ArrayList>();
    private ArrayList<ArrayList> tabla_transiciones = new ArrayList<ArrayList>();
    private String regex;

    public ArbolBinario(String regex){
       this.regex = regex;
        crear_nodos();
    }



    public char [] reverse_string(String cadena){
        char [] nueva_cadena = cadena.toCharArray();
        int L = nueva_cadena.length -1;
        for (int i = 0; i <L ; i++, L--) {
            char temp = nueva_cadena[i];
            nueva_cadena[i] = nueva_cadena[L];
            nueva_cadena[L] = temp;
        }
        return nueva_cadena;
    }

    public void crear_nodos(){
        
        this.regex = "." + this.regex +"#";
        int contador = count_terminales(this.regex);
        int contador2 = count_nodos(this.regex);
        //NumHoja numhoja = new NumHoja(this.regex);
        Stack pila = new Stack();
        char [] new_regex = reverse_string(this.regex);
        for (int i = 0; i<new_regex.length; i++) {
            if (new_regex[i]=='.'){
                NodoArbol izquierdo = (NodoArbol) pila.pop();
                NodoArbol derecho = (NodoArbol) pila.pop();
                NodoArbol nuevo = new NodoArbol(".", Type.Types.AND, -1, izquierdo, derecho, hojas, tabla_sig_pos, contador2--);
                pila.push(nuevo);

            }
            else if(new_regex[i]=='|'){
                NodoArbol izquierdo = (NodoArbol) pila.pop();
                NodoArbol derecho = (NodoArbol) pila.pop();
                NodoArbol nuevo = new NodoArbol("|", Type.Types.OR, -1, izquierdo, derecho, hojas, tabla_sig_pos, contador2--);
                pila.push(nuevo);
            }
            else if(new_regex[i] == '*'){
                NodoArbol nodito = (NodoArbol) pila.pop();
                NodoArbol nuevo = new NodoArbol("*", Type.Types.KLEENE, -1, nodito, null, hojas, tabla_sig_pos, contador2--);
                pila.push(nuevo);
            }
            else if(new_regex[i]=='?'){
                NodoArbol nodito = (NodoArbol) pila.pop();
                NodoArbol nuevo = new NodoArbol("?", Type.Types.INTERROGACION, -1, nodito, null, hojas, tabla_sig_pos, contador2--);
                pila.push(nuevo);
            }
            else if(new_regex[i]=='+'){
                NodoArbol nodito = (NodoArbol) pila.pop();
                NodoArbol nuevo = new NodoArbol("+", Type.Types.PLUS, -1, nodito, null, hojas, tabla_sig_pos, contador2--);
                pila.push(nuevo);

            }
            else{
                StringBuilder posible_id = new StringBuilder();
                if (new_regex[i] == '#'){
                    // creo nodo
                    NodoArbol nodo = new NodoArbol(Character.toString(new_regex[i]), Type.Types.HOJA, contador--, null, null, hojas, tabla_sig_pos, contador2--);
                    nodo.setAceptacion(true);
                    pila.push(nodo);
                    hojas.add(nodo);
                }
                else if (new_regex[i] == '"'){
                    // Acá agregaremos nodos hoja que tengan como lexeman un valor entre comillas
                    posible_id.append(new_regex[i]);
                    for (int j = i+1; j <new_regex.length ; j++) {
                        posible_id.append(new_regex[j]);
                        if (new_regex[j] == '"'){
                                if (new_regex[j] != '\\'){
                                    // aqui se guarda el nodo con el lexema que incluye a una cadena. Por ejemplo : "holaaa"
                                    posible_id.reverse();
                                    // System.out.println(posible_id);
                                    i = j;
                                    NodoArbol nodo = new NodoArbol(posible_id.toString(), Type.Types.HOJA, contador--, null, null, hojas, tabla_sig_pos, contador2--);
                                    pila.push(nodo);
                                    hojas.add(nodo);
                                    break;
                                }
                        }
                        else if (new_regex[j] == '\\'){
                            i = j;
                            NodoArbol nodo = new NodoArbol("\\\"", Type.Types.HOJA, contador--, null, null, hojas, tabla_sig_pos, contador2--);
                            pila.push(nodo);
                            hojas.add(nodo);
                            break;
                        }
                    }

                }
                else{
                    for (int j = i; j <new_regex.length ; j++) {
                        posible_id.append(new_regex[j]);
                        if (new_regex[j] == '{'){
                            // aqui se guarda el nodo con el lexema que incluye a los conjuntos. Por ejemplo : {numeros}
                            posible_id.reverse();
                           // System.out.println(posible_id);
                            i = j;
                            NodoArbol nodo = new NodoArbol(posible_id.toString(), Type.Types.HOJA, contador--, null, null, hojas, tabla_sig_pos, contador2--);
                            pila.push(nodo);
                            hojas.add(nodo);
                            break;
                        }
                    }

                }
                }

            }

        this.raiz = (NodoArbol) pila.pop();
        }


    public NodoArbol getRaiz() {
        return raiz;
    }


    public int count_terminales(String content){
        int contador = 0;
        content = content.replace(".", "").replace("|", "").replace("*", "").replace("?", "");
        char[] caracteres = content.toCharArray();
        for (int i = 0; i < caracteres.length ; i++) {
            if (caracteres[i] == '"'){
                i++;
                for (int j = i; j < caracteres.length ; j++) {
                    if (caracteres[j] == '"'){
                        i = j;
                        contador++;
                        break;
                    }

                }
            }
            if (caracteres[i] == '{'){
                for (int j = i; j < caracteres.length ; j++) {
                    if (caracteres[j] == '}'){
                        i = j;
                        contador++;
                        break;
                    }
                }
            }

            if (caracteres[i] == '\\'){
                if (i+1 != caracteres.length){
                    if (caracteres[i+1] == '"'){
                        i = i+1;
                        contador++;
                    }
                }
            }

            if (caracteres[i] == '\\'){
                if (i+1 != caracteres.length){
                    if (caracteres[i+1] == '\''){
                        i=1+i;
                        contador++;
                    }
                }
            }

            if (caracteres[i] == '\\'){
                if (i+1 != caracteres.length){
                    if (caracteres[i+1] == 'n'){
                        i=1+i;
                        contador++;
                    }
                }
            }

        }

        return contador;
    }

    public int count_nodos(String content){
        int contador = 0;
        char[] caracteres = content.toCharArray();
        for (int i = 0; i < caracteres.length ; i++) {
            if (caracteres[i] == '"'){
                i++;
                for (int j = i; j < caracteres.length ; j++) {
                    if (caracteres[j] == '"'){
                        i = j;
                        contador++;
                        break;
                    }
                }
            }
            else if (caracteres[i] == '{'){
                for (int j = i; j < caracteres.length ; j++) {
                    if (caracteres[j] == '}'){
                        i = j;
                        contador++;
                        break;
                    }
                }
            }
            else{
                contador++;
            }

        }

        return contador;
    }

    public ArrayList<ArrayList> getTabla_sig_pos() {
        return tabla_sig_pos;
    }

    public ArrayList<NodoArbol> getHojas() {
        return hojas;
    }

    public ArrayList<ArrayList> getTabla_transiciones() {
        return tabla_transiciones;
    }
}





