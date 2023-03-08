package Extra;


public class Evaluacion {
    private String key;
    private String value;
    private boolean perteneciente;

    public Evaluacion(String key, String value){
        this.value = value;
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public void setPerteneciente(boolean perteneciente) {
        this.perteneciente = perteneciente;
    }

    public boolean isPerteneciente() {
        return perteneciente;
    }

    public String getValue() {
        return value;
    }


}
