package Extra;

public class Errores {
    private int fila;
    private int columna;
    private String lexema;
    private String tipo;
    public Errores(int fila, int columna, String lexema, String tipo){
        this.fila = fila;
        this.columna = columna;
        this.lexema = lexema;
        this.tipo = tipo;
    }


    public String getFila() {
        return Integer.toString(fila);
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public String getColumna() {
        return Integer.toString(columna);
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
