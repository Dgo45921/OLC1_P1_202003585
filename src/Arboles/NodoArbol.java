package Arboles;

import java.util.ArrayList;
import java.util.Objects;

public class NodoArbol implements Cloneable {
    public ArrayList<Integer> primeros;
    public ArrayList<Integer> ultimos;
    public boolean anulable;

    public String lexema;
    public Type.Types type;
    public int id;
    public int id_2;

    boolean aceptacion;

    public NodoArbol izquierda;
    public NodoArbol derecha;

    ArrayList<NodoArbol> hojas;
    ArrayList<ArrayList> tabla;

    public NodoArbol(String lexema, Type.Types type, int id, NodoArbol izquierda, NodoArbol derecha, ArrayList<NodoArbol> hojas, ArrayList<ArrayList> tabla, int id_2) {
        primeros = new ArrayList<>();
        ultimos = new ArrayList<>();
        anulable = true;
        aceptacion = false;

        this.lexema = lexema;
        this.type = type;
        this.id = id;


        this.izquierda = izquierda;
        this.derecha = derecha;

        this.hojas = hojas;
        this.tabla = tabla;

        this.id_2 = id_2;
    }

    public NodoArbol inicializa_propiedades_nodo(){
        Object leftNode = this.izquierda != null ? ((NodoArbol) this.izquierda).inicializa_propiedades_nodo():null;
        Object rightNode = this.derecha != null ? ((NodoArbol) this.derecha).inicializa_propiedades_nodo() : null;

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
                        if (!(((NodoArbol) leftNode).anulable)){
                            this.primeros.addAll(((NodoArbol)leftNode).primeros);
                        }
                        else {
                            this.primeros.addAll(((NodoArbol)leftNode).primeros);
                            this.primeros.addAll(((NodoArbol)rightNode).primeros);
                        }
                        if (!((NodoArbol)rightNode).anulable){
                            this.ultimos.addAll(((NodoArbol)rightNode).ultimos);
                        }
                        else{
                            this.ultimos.addAll(((NodoArbol)leftNode).ultimos);
                            this.ultimos.addAll(((NodoArbol)rightNode).ultimos);
                        }

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
                case INTERROGACION:
                    if(leftNode != null){
                        this.anulable = true;
                        this.primeros.addAll(((NodoArbol)leftNode).primeros);
                        this.ultimos.addAll(((NodoArbol)leftNode).ultimos);
                    }
                    break;
                case PLUS:
                    if(leftNode != null){
                        this.anulable = false;
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


    public Object sig_pos(){
        Object izquierda_siguiente = this.izquierda != null ? ((NodoArbol)this.izquierda).sig_pos():null;
        Object derecha_siguiente = this.derecha != null ? ((NodoArbol)this.derecha).sig_pos():null;

         if (this.type != null){
            switch (this.type){
                case AND:
                    for (int elemento : ((NodoArbol)izquierda_siguiente).ultimos) {
                        Hoja hoja = new Hoja();
                        NodoArbol nodo = hoja.get_hoja(elemento, hojas);
                        TablaSigPos table= new TablaSigPos();
                        table.agregar_tabla_sigPos(nodo.id, nodo.lexema, (ArrayList) ((NodoArbol) derecha_siguiente).primeros.clone(), tabla);
                    }
                    break;
                case KLEENE:
                case PLUS:
                    for (int item : ((NodoArbol)izquierda_siguiente).ultimos) {
                        Hoja hoja = new Hoja();
                        NodoArbol nodo = hoja.get_hoja(item, hojas);
                        TablaSigPos table = new TablaSigPos();
                        table.agregar_tabla_sigPos(nodo.id, nodo.lexema, (ArrayList) ((NodoArbol) izquierda_siguiente).primeros.clone(), tabla);
                    }
                    break;

                default:
                    break;
            }
        }
        return this;

    }

    public void setAceptacion(boolean aceptacion) {
        this.aceptacion = aceptacion;
    }
}
