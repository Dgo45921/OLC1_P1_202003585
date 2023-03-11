package AFND;

import Arboles.NodoArbol;
import Arboles.Transition;
import Arboles.Type;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Stack;

public class AFND {
    private String type;
    private AFND AutomataFinal;
    private ArrayList<Transition> Transiciones;
    private ArrayList<EstadoAFND> Nodos;
    private Stack<AFND> Pila = new Stack<>();
    private int contador = 0;
    private int primer_estado = 0;
    private int ultimo_estado = 0;


    public void setTransiciones(ArrayList<Transition> transiciones) {
        Transiciones = transiciones;
    }

    public ArrayList<EstadoAFND> getNodos() {
        return Nodos;
    }

    public Transition FindTransicionAEliminar(AFND afnd){
        for (Transition trans:afnd.Transiciones){
            if (Objects.equals(trans.initialState, "S" + afnd.primer_estado) && Objects.equals(trans.finalState, "S" + afnd.primer_estado)){
                return trans;
            }
        }
        return null;
    }

    public void GeneraAFND_Simple(NodoArbol nodo) {
        AFND automata_simple = new AFND();
        automata_simple.type = "simple";
        Transition transicion = new Transition("S" + (contador), nodo.lexema, "S" + (contador + 1));
        ArrayList<Transition> transiciones = new ArrayList<>();
        transiciones.add(transicion);
        automata_simple.setTransiciones(transiciones);
        automata_simple.ultimo_estado = contador + 1;
        automata_simple.primer_estado = contador;
        Pila.push(automata_simple);
        contador = contador + 2;
    }

    public void Concatenar(AFND izquierdo, AFND derecho) {
        AFND automata_concatenado = new AFND();
        automata_concatenado.type = "concat";
        Transition transicion_a_eliminar = FindTransicionAEliminar(izquierdo);
        ArrayList<Transition> transiciones_combinadas = new ArrayList<>();
        transiciones_combinadas.addAll(derecho.Transiciones);
        transiciones_combinadas.addAll(izquierdo.Transiciones);
        transiciones_combinadas.remove(transicion_a_eliminar);
        automata_concatenado.Transiciones = transiciones_combinadas;
        Pila.push(automata_concatenado);
        AutomataFinal = automata_concatenado;
        ultimo_estado = izquierdo.ultimo_estado;
    }

    public void OR(AFND izquierdo, AFND derecho) {
        AFND automata_or = new AFND();
        automata_or.ultimo_estado = ultimo_estado;
        contador++;
        Transition transicionEpsilon1 = new Transition("S" + ultimo_estado, "ε", "S" + izquierdo.primer_estado);
        Transition transicionEpsilon2 = new Transition("S" + ultimo_estado, "ε", "S" + derecho.primer_estado);
        Transition transicionEpsilon3 = new Transition("S" + izquierdo.ultimo_estado, "ε", "S" + contador);
        Transition transicionEpsilon4 = new Transition("S" + derecho.ultimo_estado, "ε", "S" + contador);
        ArrayList<Transition> transiciones_combinadas = new ArrayList<>();
        transiciones_combinadas.addAll(derecho.Transiciones);
        transiciones_combinadas.addAll(izquierdo.Transiciones);
        transiciones_combinadas.add(transicionEpsilon1);
        transiciones_combinadas.add(transicionEpsilon2);
        transiciones_combinadas.add(transicionEpsilon3);
        transiciones_combinadas.add(transicionEpsilon4);
        automata_or.setTransiciones(transiciones_combinadas);
        automata_or.primer_estado = ultimo_estado;
        automata_or.ultimo_estado = contador;
        Pila.push(automata_or);
    }


    public void KLEENE(AFND automata) {
        contador++;
        AFND automata_kleene = new AFND();
        automata_kleene.primer_estado = contador + 1;
        automata_kleene.ultimo_estado = contador + 2;
        ArrayList<Transition> transiciones_combinadas = new ArrayList<>();
        Transition transicionEpsilon1 = new Transition("S" + automata.primer_estado, "ε", "S" + contador);
        Transition transicionEpsilon2 = new Transition("S" + contador, "ε", "S" + (contador + 1));
        Transition transicionEpsilon3 = new Transition("S" + automata.ultimo_estado, "ε", "S" + (contador + 1));
        Transition transicionEpsilon4 = new Transition("S" + automata.ultimo_estado, "ε", "S" + automata.primer_estado);
        transiciones_combinadas.add(transicionEpsilon1);
        transiciones_combinadas.add(transicionEpsilon2);
        transiciones_combinadas.add(transicionEpsilon3);
        transiciones_combinadas.add(transicionEpsilon4);
        transiciones_combinadas.addAll(automata.Transiciones);
        automata_kleene.setTransiciones(transiciones_combinadas);
        Pila.push(automata_kleene);
        contador = contador + 2;
    }

    public void generar_AFND(NodoArbol root) {
        if (root != null) {
            generar_AFND(root.izquierda);
            generar_AFND(root.derecha);
            System.out.println(root.lexema);
            if (root.type == Type.Types.HOJA) {
                GeneraAFND_Simple(root);
            } else if (root.type == Type.Types.AND) {
                AFND izquierdo = this.Pila.pop();
                AFND derecho = this.Pila.pop();
                Concatenar(izquierdo, derecho);
            } else if (root.type == Type.Types.OR) {
                AFND izquierdo = this.Pila.pop();
                AFND derecho = this.Pila.pop();
                OR(izquierdo, derecho);
            } else if (root.type == Type.Types.KLEENE) {
                AFND automata = this.Pila.pop();
                KLEENE(automata);
            }

        }
    }



}