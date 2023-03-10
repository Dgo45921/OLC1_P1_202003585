package AFND;

import Arboles.NodoArbol;
import Arboles.Transition;
import Arboles.Type;

import java.util.ArrayList;
import java.util.Stack;

public class AFND {
    private AFND AutomataFinal;
    private NodoAFND EstadoInicial;
    private NodoAFND EstadoFinal;
    private ArrayList<Transition> Transiciones;
    private ArrayList<NodoAFND> Nodos;
    private int Vertices;
    private int CantTransiciones;
    private Stack<AFND> Pila = new Stack<>();
    private int contador = 0;
    private int ultimo_estado;


    public NodoAFND getEstadoInicial() {
        return EstadoInicial;
    }

    public void setEstadoInicial(NodoAFND estadoInicial) {
        EstadoInicial = estadoInicial;
    }

    public NodoAFND getEstadoFinal() {
        return EstadoFinal;
    }

    public void setEstadoFinal(NodoAFND estadoFinal) {
        EstadoFinal = estadoFinal;
    }

    public int getVertices() {
        return Vertices;
    }

    public void setVertices(int vertices) {
        Vertices = vertices;
    }

    public int getCantTransiciones() {
        return CantTransiciones;
    }

    public void setCantTransiciones(int cantTransiciones) {
        CantTransiciones = cantTransiciones;
    }

    public ArrayList<Transition> getTransiciones() {
        return Transiciones;
    }

    public void setTransiciones(ArrayList<Transition> transiciones) {
        Transiciones = transiciones;
    }

    public ArrayList<NodoAFND> getNodos() {
        return Nodos;
    }

    public void setNodos(ArrayList<NodoAFND> nodos) {
        Nodos = nodos;
    }

    public void GeneraAFND_Simple(NodoArbol nodo){
        AFND automata_simple = new AFND();
        Transition transicion = new Transition("S" + this.contador, nodo.lexema, "S" + (this.contador + 1));
        ArrayList<Transition> transiciones = new ArrayList<>();
        transiciones.add(transicion);
        automata_simple.setTransiciones(transiciones);
        automata_simple.ultimo_estado = this.contador+1;
        this.Pila.push(automata_simple);
        this.contador++;
    }

    public void Concatenar(AFND izquierdo, AFND derecho){
        AFND automata_concatenado = new AFND();
        ArrayList<Transition> transiciones_combinadas = new ArrayList<>();
        transiciones_combinadas.addAll(derecho.Transiciones);
        transiciones_combinadas.addAll(izquierdo.Transiciones);
        automata_concatenado.Transiciones = transiciones_combinadas;
        Pila.push(automata_concatenado);
        this.AutomataFinal = automata_concatenado;
    }

    public void OR(AFND izquierdo, AFND derecho){
        AFND automata_concatenado = new AFND();
        ArrayList<Transition> transiciones_combinadas = new ArrayList<>();
        transiciones_combinadas.addAll(derecho.Transiciones);
        transiciones_combinadas.addAll(izquierdo.Transiciones);
        automata_concatenado.Transiciones = transiciones_combinadas;
        Pila.push(automata_concatenado);
        this.AutomataFinal = automata_concatenado;
    }

    public void generar_AFND(NodoArbol root){
        if (root != null){
            generar_AFND(root.izquierda);
            generar_AFND(root.derecha);
            System.out.println(root.lexema);
            if (root.type == Type.Types.HOJA){
                GeneraAFND_Simple(root);
            }
            else if (root.type == Type.Types.AND){
                AFND izquierdo = this.Pila.pop();
                AFND derecho = this.Pila.pop();
                Concatenar(izquierdo, derecho);
            }

        }
    }



}
