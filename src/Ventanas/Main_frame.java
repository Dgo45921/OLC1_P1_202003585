package Ventanas;

import Analizadores.Lexico;
import Analizadores.Sintactico;
import Arboles.ArbolBinario;
import Arboles.NodoArbol;
import Paneles.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;

public class Main_frame extends JFrame implements ActionListener {
    public boolean guardado = false;
    // Instanciando los paneles que formaran parte de la ventana principal
    public Panel_Main panel_principal = new Panel_Main();
    // Instanciando los elementos que van a existir en nuestro menu
    JMenuItem new_option = new JMenuItem("New");
    JMenuItem open = new JMenuItem("Open");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem save_as = new JMenuItem("Save as...");
    JMenuItem generate_afd = new JMenuItem("Generate AFD");
    JMenuItem analyze_input = new JMenuItem("Analyze input");

    public Main_frame() {
        // Definiendo propiedades de la ventana
        setSize(1500, 800);
        setBackground(Color.decode("#202020"));
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ExRegan");


        // Creando menu de barra
        JMenuBar main_menu = new JMenuBar();
        // Creando pestañas del menu de barra
        JMenu file = new JMenu("File");
        JMenu reports = new JMenu("Reportes");
        // Creando y agregando sub opciones a la pestaña file
        main_menu.add(file);
        main_menu.add(reports);
        file.add(new_option);
        file.add(open);
        file.add(save);
        file.add(save_as);
        reports.add(generate_afd);
        reports.add(analyze_input);
        // Agregando listeners a las opciones del menu file
        new_option.addActionListener(this);
        open.addActionListener(this);
        save.addActionListener(this);
        save_as.addActionListener(this);
        generate_afd.addActionListener(this);
        analyze_input.addActionListener(this);
        // Agregando el menu de barra a la ventana
        setJMenuBar(main_menu);
        add(panel_principal);

    }

    public String get_file_path() {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("OLC FILES", "olc", "olc");
        File selectedFile;
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(filter);
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            return selectedFile.getAbsolutePath();
        } else {
            return "";
        }

    }

    public void set_input_text(String ruta) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(ruta));
        String texto = new String(encoded, StandardCharsets.UTF_8);
        Panel_Main.input_texto.setText(texto);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == new_option) {
            System.out.println("inicializo un nuevo archivo");
            Panel_Main.input_texto.setText("");
        }

        if (e.getSource() == open) {
            System.out.println("yo abro el archivo");
            String path = get_file_path();
            if (!Objects.equals(path, "")) {
                try {
                    set_input_text(path);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }

        if (e.getSource() == save) {
            System.out.println("yo guardo el archivo");
        }

        if (e.getSource() == save_as) {
            System.out.println("yo guardo como");
        }
        if (e.getSource() == generate_afd) {
            System.out.println("creo afd");
            if (Objects.equals(Panel_Main.input_texto.getText(), "")) {
                JOptionPane.showMessageDialog(null, "El input no puede estar vacío");
            }
            else{
                Main.conjuntos_valor = new Hashtable<String, Object>();
                Main.regex_valor = new Hashtable<String, Object>();
                Main.lista_evaluaciones = new Hashtable<String, Object>();
                Lexico scaner = new Lexico(new BufferedReader(new StringReader(Panel_Main.input_texto.getText())));
                Sintactico parser = new Sintactico(scaner);
                try {
                    parser.parse();
                    System.out.println("funciona");
                    genera_arboles();

                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }
            }
      }
        if (e.getSource() == analyze_input) {
            if (Objects.equals(Panel_Main.input_texto.getText(), "")) {
                JOptionPane.showMessageDialog(null, "El input no puede estar vacío ");
            }
        }

    }

    public void genera_arboles(){
        for (Map.Entry<String,Object> mapElement : Main.regex_valor.entrySet()) {
            String key = mapElement.getKey();

            String regex_value = (String) (mapElement.getValue());
            System.out.println(key + " : " + regex_value);
            ArbolBinario arbolito = new ArbolBinario(regex_value);
            Main.lista_arboles.add(arbolito);
        }

        for (int i =0; i< Main.lista_arboles.size(); i++) {
            ArbolBinario arbolito = Main.lista_arboles.get(i);
            NodoArbol raiz = arbolito.getRaiz();
            raiz.inicializa_propiedades_nodo();
            raiz.ultima_pos();
        }

    }


}
