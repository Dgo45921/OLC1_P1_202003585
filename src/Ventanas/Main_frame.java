package Ventanas;

import Paneles.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class Main_frame extends JFrame implements ActionListener {
    // Instanciando los elementos que van a existir en nuestro menu
    JMenuItem open = new JMenuItem("Open");
    JMenuItem save = new JMenuItem("Save");
    JMenuItem save_as = new JMenuItem("Save as...");
    JMenuItem generate_afd = new JMenuItem("Generate AFD");
    JMenuItem analyze_input = new JMenuItem("Analyze input");
    // Instanciando los paneles que formaran parte de la ventana principal
    public Panel_Main panel_principal = new Panel_Main();

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
        file.add(open);
        file.add(save);
        file.add(save_as);
        reports.add(generate_afd);
        reports.add(analyze_input);
        // Agregando listeners a las opciones del menu file
        open.addActionListener(this);
        save.addActionListener(this);
        save_as.addActionListener(this);
        generate_afd.addActionListener(this);
        analyze_input.addActionListener(this);
        // Agregando el menu de barra a la ventana
        setJMenuBar(main_menu);
        add(panel_principal);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource()==open){
            System.out.println("yo abro el archivo");
        }

        if (e.getSource()==save){
            System.out.println("yo guardo el archivo");
        }

        if (e.getSource()==save_as){
            System.out.println("yo guardo como");
        }
        if (e.getSource()==generate_afd){
            System.out.println("creo afd");
            if (Objects.equals(panel_principal.input_texto.getText(), "")){
                JOptionPane.showMessageDialog(null, "El input no puede estar vacío");
            }
        }
        if (e.getSource()==analyze_input){
            if (Objects.equals(panel_principal.input_texto.getText(), "")){
                JOptionPane.showMessageDialog(null, "El input no puede estar vacío ");
            }
        }

    }
}
