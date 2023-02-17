package Paneles;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Panel_Main extends JPanel implements ActionListener {
    // Instanciando areas de texto que van a servir para input e output
    public static JTextArea input_texto = new JTextArea();
    public static JTextArea output_console = new JTextArea();
    // Instanciando botón que va a mostrar imágenes
    JButton show;
    // Instanciando Jlabel en donde irá la imagen
    JLabel label_picture = new JLabel();
    // Instanciando JComboBox que almacenará rutas de archivos
    JComboBox<String> combo_rutas = new JComboBox<>();
    JComboBox<String> combo_imagenes = new JComboBox<>();

    public Panel_Main() {
        // Definiendo propiedades del panel principal
        this.setBackground(Color.decode("#202020"));
        this.setBounds(0, 0, 1500, 800);
        this.setLayout(null);
        // Creando y definiendo labels para indicar la funcionalidad de las textarea
        JLabel input_label = new JLabel("Input");
        input_label.setFont(new Font("Verdana", Font.BOLD, 18));
        input_label.setForeground(Color.WHITE);
        input_label.setBounds(100,60,80,30);

        JLabel output_label = new JLabel("Consola");
        output_label.setFont(new Font("Verdana", Font.BOLD, 18));
        output_label.setForeground(Color.WHITE);
        output_label.setBounds(100,515,80,30);

        // Definiendo propiedades del área de texto del input
        JScrollPane scroll_input = new JScrollPane(input_texto);
        scroll_input.setBounds(100, 100, 600, 400);
        // Definiendo propiedades del área de texto del output e input
        output_console.setEditable(false);
        output_console.setBackground(Color.decode("#fbfbfb"));
        output_console.setFont(new Font("Verdana", Font.PLAIN, 18));
        input_texto.setBackground(Color.decode("#fbfbfb"));
        input_texto.setFont(new Font("Verdana", Font.PLAIN, 18));
        JScrollPane scroll_output = new JScrollPane(output_console);
        scroll_output.setBounds(100, 550, 1325, 150);
        // Creando botones
        show = new JButton();
        show.setText("Mostrar");
        show.setFont(new Font("Verdana", Font.BOLD, 15));
        show.setBackground(Color.decode("#fbfbfb"));
        show.setBorder(BorderFactory.createLineBorder(Color.decode("#d9faea"), 3));
        show.setFocusable(false);
        show.addActionListener(this);
        show.setBounds(1320, 50, 100, 40);
        // Creando JComboBoxes
        final String[] opciones_imagenes = {"AFD", "AFND", "ARBOLES", "SIGUIENTES", "TRANSICIONES"};
        combo_imagenes.setModel(new DefaultComboBoxModel<>(opciones_imagenes));
        combo_imagenes.setBounds(835, 50, 130, 40);
        combo_rutas.setBounds(990, 50, 300, 40);
        combo_imagenes.addActionListener(this);
        //Creando JLABEL para fotos
        label_picture.setBounds(720, 100, 700, 400);
        label_picture.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));

        // Agregando componentes al panel
        hallar_archivos("AFD_202003585");
        this.add(output_label);
        this.add(input_label);
        this.add(scroll_input);
        this.add(scroll_output);
        this.add(label_picture);
        this.add(show);
        this.add(combo_imagenes);
        this.add(combo_rutas);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == show) {
            if (combo_rutas.getSelectedIndex() == -1) {
                JOptionPane.showMessageDialog(null, "Por favor selecciona una imagen");
                return;
            }
            if (combo_imagenes.getSelectedIndex() == 0) {
                System.out.println("AFD");
                ImageIcon imagen = new ImageIcon("AFD_202003585/" + combo_rutas.getSelectedItem());
                Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(label_picture.getWidth(), label_picture.getHeight(), Image.SCALE_SMOOTH));
                label_picture.setIcon(icono);
            } else if (combo_imagenes.getSelectedIndex() == 1) {
                System.out.println("AFND");
                ImageIcon imagen = new ImageIcon("AFND_202003585/" + combo_rutas.getSelectedItem());
                Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(label_picture.getWidth(), label_picture.getHeight(), Image.SCALE_SMOOTH));
                label_picture.setIcon(icono);


            } else if (combo_imagenes.getSelectedIndex() == 2) {
                System.out.println("arboles");
                ImageIcon imagen = new ImageIcon("ARBOLES_202003585/" + combo_rutas.getSelectedItem());
                Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(label_picture.getWidth(), label_picture.getHeight(), Image.SCALE_SMOOTH));
                label_picture.setIcon(icono);



            } else if (combo_imagenes.getSelectedIndex() == 3) {
                System.out.println("siguientes");
                ImageIcon imagen = new ImageIcon("SIGUIENTES_202003585/" + combo_rutas.getSelectedItem());
                Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(label_picture.getWidth(), label_picture.getHeight(), Image.SCALE_SMOOTH));
                label_picture.setIcon(icono);


            } else if (combo_imagenes.getSelectedIndex() == 4) {
                System.out.println("transiciones");
                ImageIcon imagen = new ImageIcon("TRANSICIONES_202003585/" + combo_rutas.getSelectedItem());
                Icon icono = new ImageIcon(imagen.getImage().getScaledInstance(label_picture.getWidth(), label_picture.getHeight(), Image.SCALE_SMOOTH));
                label_picture.setIcon(icono);

            }


        }
        if (e.getSource() == combo_imagenes) {
            if (combo_imagenes.getSelectedIndex() == 0) {
                System.out.println("AFD");
                hallar_archivos("AFD_202003585");

            } else if (combo_imagenes.getSelectedIndex() == 1) {
                System.out.println("AFND");
                hallar_archivos("AFND_202003585");

            } else if (combo_imagenes.getSelectedIndex() == 2) {
                System.out.println("arboles");
                hallar_archivos("ARBOLES_202003585");

            } else if (combo_imagenes.getSelectedIndex() == 3) {
                System.out.println("siguientes");
                hallar_archivos("SIGUIENTES_202003585");

            } else if (combo_imagenes.getSelectedIndex() == 4) {
                System.out.println("transiciones");
                hallar_archivos("TRANSICIONES_202003585");

            }


        }
    }

    public void hallar_archivos(String ruta) {

        File folder = new File(ruta);
        File[] listOfFiles = folder.listFiles();
        String[] pathnames = new String[listOfFiles.length];

        for (int i = 0; i < pathnames.length; i++) {
            if (listOfFiles[i].isFile()) {
                pathnames[i] = listOfFiles[i].getName();
            }
        }

        combo_rutas.setModel(new DefaultComboBoxModel<>(pathnames));
    }



}
