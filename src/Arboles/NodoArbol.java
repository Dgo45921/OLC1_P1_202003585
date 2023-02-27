package Arboles;

import java.util.ArrayList;

public class NodoArbol {
    ArrayList<Integer> primeros;
    ArrayList<Integer> ultimos;
    boolean anulable;

    String lexema;
    Type.Types type;
    int id;

    boolean aceptacion;

    NodoArbol izquierda;
    NodoArbol derecha;

    ArrayList<NodoArbol> hojas;
    ArrayList<ArrayList> tabla;

    public NodoArbol(String lexema, Type.Types type, int id, NodoArbol izquierda, NodoArbol derecha, ArrayList<NodoArbol> hojas, ArrayList<ArrayList> tabla) {
        primeros = new ArrayList<>();
        ultimos = new ArrayList<>();
        anulable = true;
        aceptacion = "#".equals(this.lexema);

        this.lexema = lexema;
        this.type = type;
        this.id = id;


        this.izquierda = izquierda;
        this.derecha = derecha;

        this.hojas = hojas;
        this.tabla = tabla;
    }

    public NodoArbol getNode(){
        Object leftNode = this.izquierda != null ? ((NodoArbol) this.izquierda).getNode():null;
        Object rightNode = this.derecha != null ? ((NodoArbol) this.derecha).getNode() : null;

        if (this.type != null){
            switch (this.type){
                case HOJA:
                    this.anulable = false;
                    this.primeros.add(this.id);
                    this.ultimos.add(this.id);
                    break;
                case AND:
                    if (leftNode != null && rightNode != null){
                        this.anulable = ((NodoArbol)leftNode).anulable && ((NodoArbol)rightNode).anulable;
                        this.primeros.addAll(((NodoArbol)leftNode).primeros);
                        if (((NodoArbol)leftNode).anulable){
                            this.primeros.addAll(((NodoArbol)rightNode).ultimos);
                        }
                        if (((NodoArbol)rightNode).anulable){
                            this.ultimos.addAll(((NodoArbol)leftNode).ultimos);
                        }
                        this.ultimos.addAll(((NodoArbol)rightNode).ultimos);
                    }
                    break;
                case OR:
                    if (leftNode != null && rightNode != null){
                        this.anulable = ((NodoArbol)leftNode).anulable || ((NodoArbol)rightNode).anulable;
                        this.primeros.addAll(((NodoArbol)leftNode).primeros);
                        this.primeros.addAll(((NodoArbol)rightNode).primeros);


                        this.ultimos.addAll(((NodoArbol)leftNode).ultimos);
                        this.ultimos.addAll(((NodoArbol)rightNode).ultimos);
                    }
                    break;
                case KLEENE:
                    if(leftNode != null){
                        this.anulable = true;
                        this.primeros.addAll(((NodoArbol)leftNode).primeros);
                        this.ultimos.addAll(((NodoArbol)leftNode).ultimos);
                    }
                    break;
                default:
                    break;
            }


        }
        return this;

    }


    public Object siguientes(){
        Object izquierda_siguiente = this.izquierda != null ? ((NodoArbol)this.izquierda).siguientes():null;
        Object derecha_siguiente = this.derecha != null ? ((NodoArbol)this.derecha).siguientes():null;

        if (this.type != null){
            switch (this.type){
                case AND:
                    for (int elemento : ((NodoArbol)izquierda_siguiente).ultimos) {
                        Hoja hoja = new Hoja();
                        NodoArbol nodo = hoja.get_hoja(elemento, hojas);
                        TablaSiguientes table= new TablaSiguientes();
                        table.agregar(nodo.id, nodo.lexema, ((NodoArbol) derecha_siguiente).primeros, tabla);
                    }
                    break;
                case KLEENE:
                    for (int item : ((NodoArbol)izquierda_siguiente).ultimos) {
                        Hoja hoja = new Hoja();
                        NodoArbol nodo = hoja.get_hoja(item, hojas);
                        TablaSiguientes table = new TablaSiguientes();
                        table.agregar(nodo.id, nodo.lexema, ((NodoArbol) izquierda_siguiente).primeros, tabla);
                    }
                    break;
                default:
                    break;
            }
        }
        return this;

    }


}
