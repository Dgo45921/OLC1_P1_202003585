package Arboles;

import java.util.ArrayList;
import java.util.Stack;

public class ArbolBinario {
    private NodoArbol raiz;
    private ArrayList<NodoArbol> hojas = new ArrayList<NodoArbol>();
    private ArrayList<ArrayList> tabla = new ArrayList<ArrayList>();
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
        NumHoja numhoja = new NumHoja(this.regex);
        Stack pila = new Stack();
        char [] new_regex = reverse_string(this.regex);
        for (int i = 0; i<new_regex.length; i++) {
            if (new_regex[i]=='.'){
                System.out.println("concat");
                NodoArbol izquierdo = (NodoArbol) pila.pop();
                NodoArbol derecho = (NodoArbol) pila.pop();
                NodoArbol nuevo = new NodoArbol(".", Type.Types.AND, 0, izquierdo, derecho, hojas, tabla);
                pila.push(nuevo);

            }
            else if(new_regex[i]=='|'){
                System.out.println("or");
                NodoArbol izquierdo = (NodoArbol) pila.pop();
                NodoArbol derecho = (NodoArbol) pila.pop();
                NodoArbol nuevo = new NodoArbol("|", Type.Types.OR, 0, izquierdo, derecho, hojas, tabla);
                pila.push(nuevo);
            }
            else if(new_regex[i] == '*'){
                System.out.println("kleene");
                NodoArbol nodito = (NodoArbol) pila.pop();
                NodoArbol nuevo = new NodoArbol("*", Type.Types.KLEENE, 0, nodito, null, hojas, tabla);
                pila.push(nuevo);
            }
            else if(new_regex[i]=='?'){
                System.out.println("cero o una incidencias");
                NodoArbol nodito = (NodoArbol) pila.pop();
                NodoArbol nuevo = new NodoArbol("?", Type.Types.INTERROGACION, 0, nodito, null, hojas, tabla);
                pila.push(nuevo);
            }
            else if(new_regex[i]=='+'){
                System.out.println("plus");
                NodoArbol nodito = (NodoArbol) pila.pop();
                NodoArbol nuevo = new NodoArbol("+", Type.Types.PLUS, 0, nodito, null, hojas, tabla);
                pila.push(nuevo);

            }
            else{
                StringBuilder posible_id = new StringBuilder();
                if (new_regex[i] == '#'){
                    // creo nodo
                    NodoArbol nodo = new NodoArbol(Character.toString(new_regex[i]), Type.Types.HOJA, numhoja.getNum(), null, null, hojas, tabla);
                    pila.push(nodo);
                    hojas.add(nodo);
                }
                else if (new_regex[i] == '"'){
                    // Acá agregaremos nodos hoja que tengan como lexeman un valor entre comillas
                    posible_id.append(new_regex[i]);
                    for (int j = i+1; j <new_regex.length ; j++) {
                        posible_id.append(new_regex[j]);
                        if (new_regex[j] == '"'){
                            // aqui se guarda el nodo con el lexema que incluye a una cadena. Por ejemplo : "holaaa"
                            posible_id.reverse();
                            System.out.println(posible_id);
                            i = j;
                            NodoArbol nodo = new NodoArbol(posible_id.toString(), Type.Types.HOJA, numhoja.getNum(), null, null, hojas, tabla);
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
                            System.out.println(posible_id);
                            i = j;
                            NodoArbol nodo = new NodoArbol(posible_id.toString(), Type.Types.HOJA, numhoja.getNum(), null, null, hojas, tabla);
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


    }





