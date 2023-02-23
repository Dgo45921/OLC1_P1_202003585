package Funcionalidades;

import Ventanas.Main;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Funcion {

    private final Pattern patron_numero = Pattern.compile("\\d+");
    private final Pattern patron_letra = Pattern.compile("[A-Za-z]");

    public boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return patron_numero.matcher(strNum).matches();
    }

    public boolean isLetter(String strLett) {
        if (strLett == null) {
            return false;
        }
        return patron_letra.matcher(strLett).matches();
    }

    public boolean tieneLetra(String cadena){
        String text = "1ab,55";

        String regex = ".*[a-zA-Z].*";  // regex to check if string contains any letters
        Pattern pattern = Pattern.compile(regex);  // compiles the regex

        // find match between given string and pattern
        Matcher matcherText = pattern.matcher(cadena);

        // return true if the string matched the regex
        Boolean textMatches = matcherText.matches();

        return textMatches;
    }

    public boolean tieneNumero(String cadena){
        String text = "1ab,55";

        String regex = ".*\\d.*";  // regex to check if string contains any letters
        Pattern pattern = Pattern.compile(regex);  // compiles the regex

        // find match between given string and pattern
        Matcher matcherText = pattern.matcher(cadena);

        // return true if the string matched the regex
        Boolean textMatches = matcherText.matches();

        return textMatches;
    }



    public void guarda_rango(String ID, String rango){
        if (rango.contains("~")){
            System.out.println("encontre un rango llamado " + ID + " y tiene "+ rango);
            String [] limites = rango.split("~");
            String limite_inferior = limites[0];
            String limite_superior = limites[1];

            // Verifica que sean numeros los limites de los rangos (num~num)
            if(isNumeric(limite_inferior) && isNumeric(limite_superior)){
                ArrayList<Integer> conjunto = new ArrayList<Integer>();
                for (int i = Integer.parseInt(limite_inferior); i <=Integer.parseInt(limite_superior) ; i++) {
                    conjunto.add(i);
                }
                Main.variables_valor.put(ID, conjunto);
            }

            // verifica que sean letras los limites de los rangos (letra~letra)
            else if(isLetter(limite_inferior) && isLetter(limite_superior)){
                ArrayList<Character> conjunto = new ArrayList<Character>();
                for (char i = limite_inferior.charAt(0); i <= limite_superior.charAt(0) ; i++) {
                    conjunto.add(i);
                }
                Main.variables_valor.put(ID, conjunto);
            }
            // caso en el que sea un rango caracter~caracter
            else{
                ArrayList<Character> conjunto = new ArrayList<Character>();
                for (char i = limite_inferior.charAt(0); i <= limite_superior.charAt(0) ; i++) {
                    if (!Character.isDigit(i) && !Character.isDigit(i) && !Character.isLetter(i) && !Character.isLetter(i)){
                        conjunto.add(i);
                    }
                }
                Main.variables_valor.put(ID, conjunto);
            }
        }
        else{
            System.out.println("encontre una lista " + ID + " y tiene "+ rango);
            ArrayList<String> conjunto = new ArrayList<String>();
            String [] datos = rango.split(",");
            for (int i = 0; i <datos.length ; i++) {
                conjunto.add(datos[i]);
            }

            Main.variables_valor.put(ID, conjunto);

        }

        System.out.println("hey guarde rangos");
    }




}
