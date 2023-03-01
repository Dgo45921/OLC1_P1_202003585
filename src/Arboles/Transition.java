package Arboles;


public class Transition {

    public String initialState;
    public String transition;
    public String finalState;

    public Transition( String initialState, String transition, String finalState ) {
        this.initialState = initialState;
        this.transition = transition;
        this.finalState = finalState;
    }

    public boolean comparar(String initialState, String transition){

        return this.initialState.equals(initialState) && this.transition.equals(transition);
    }

    @Override
    public String toString(){
        return this.initialState + " -> " + this.transition + " -> " + this.finalState;
    }
}