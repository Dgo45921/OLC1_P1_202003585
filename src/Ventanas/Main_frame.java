package Ventanas;
import Paneles.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main_frame extends JFrame implements ActionListener {

    public Main_frame(){
        // Definiendo propiedades de la ventana
        setSize(1300,800);
        setLocationRelativeTo(null);
        setLayout(null);
        getContentPane().setBackground(Color.white);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("ExRegan");
        // Definiendo los paneles que van a formar parte de la ventana principal
        Panel_Main panel_principal = new Panel_Main();

        // Agregando los componentes a la ventana principal
        add(panel_principal);

    }

    private void inicializar_componentes(){

    }



    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }
}
