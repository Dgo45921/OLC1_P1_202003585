package Ventanas;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static Main_frame principal;
    public static void main(String[] args) throws IOException {
        Files.createDirectories(Paths.get("ARBOLES_202003585"));
        Files.createDirectories(Paths.get("AFD_202003585"));
        Files.createDirectories(Paths.get("AFND_202003585"));
        Files.createDirectories(Paths.get("SIGUIENTES_202003585"));
        Files.createDirectories(Paths.get("ERRORES_202003585"));
        Files.createDirectories(Paths.get("SALIDAS_202003585"));

        principal = new Main_frame();
    }
}