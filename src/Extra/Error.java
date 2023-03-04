package Extra;

public class Error {
    private int fila;
    private int columna;
    private String lexema;

    public Error(int fila, int columna, String lexema){
        this.fila = fila;
        this.columna = columna;
        this.lexema = lexema;
    }


    public int getFila() {
        String x = "\"";
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
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
}
