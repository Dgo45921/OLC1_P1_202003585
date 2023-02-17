package Ventanas;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static Main_frame principal;
    public static void main(String[] args) throws IOException {
        principal = new Main_frame();
        inicializar_directorios();
        //generate_lexer("src/Analizadores/Lexico.jflex");
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

}