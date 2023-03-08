package Extra;


import com.google.gson.annotations.SerializedName;

public class Evaluacion {
    @SerializedName("ExpresionRegular")
    private String key;
    @SerializedName("Valor")
    private String value;
    @SerializedName("Resultado")
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
