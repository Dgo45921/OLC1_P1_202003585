package Ventanas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static Main_frame principal;
    public static void main(String[] args) throws IOException {
        inicializar_directorios();
        generate_parser("src/Analizadores");
       // generate_lexer("src/Analizadores/Lexico.jflex");
        principal = new Main_frame();
    }

    public static void inicializar_directorios() throws IOException {
        Files.createDirectories(Paths.get("ARBOLES_202003585"));
        Files.createDirectories(Paths.get("TRANSICIONES_202003585"));
        Files.createDirectories(Paths.get("AFD_202003585"));
        Files.createDirectories(Paths.get("AFND_202003585"));
        Files.createDirectories(Paths.get("SIGUIENTES_202003585"));
        Files.createDirectories(Paths.get("ERRORES_202003585"));
        Files.createDirectories(Paths.get("SALIDAS_202003585"));
    }

    public static void generate_lexer(String path)
    {
        File file=new File(path);
        jflex.Main.generate(file);
    }

    public static void generate_parser(String path){
        String opciones[] = new String[7];

        //Seleccionamos la opción de dirección de destino
        opciones[0] = "-destdir";

        //Le damos la dirección, carpeta donde se va a generar el parser.java & el simbolosxxx.java
        opciones[1] = path;

        //Seleccionamos la opción de nombre de archivo simbolos
        opciones[2] = "-symbols";

        //Le damos el nombre que queremos que tenga
        opciones[3] = "sym";

        //Seleccionamos la opcion de clase parser
        opciones[4] = "-parser";

        //Le damos nombre a esa clase del paso anterior
        opciones[5] = "Sintactico";

        //Le decimos donde se encuentra el archivo .cup
        opciones[6] = "src/Analizadores/Sintactico.cup";
        try
        {
            java_cup.Main.main(opciones);
        }
        catch (Exception ex)
        {
            System.out.print(ex);
        }
    }

}