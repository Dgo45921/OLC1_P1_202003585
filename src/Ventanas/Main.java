package Ventanas;


import Arboles.ArbolBinario;
import Extra.Errores;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;

public class Main {
    public static Main_frame principal;
    public static Hashtable<String, Object> conjuntos_valor = new Hashtable<String, Object>();
    public static Hashtable<String, Object> regex_valor = new Hashtable<String, Object>();
    public static Hashtable<String, Object> lista_evaluaciones = new Hashtable<String, Object>();
    public static ArrayList<ArbolBinario> lista_arboles = new ArrayList<>();
    public static ArrayList<Errores> lista_errores = new ArrayList<Errores>();
    public static void main(String[] args) throws IOException {
        inicializar_directorios();
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

}