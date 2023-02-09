package Paneles;

import javax.swing.*;
import java.awt.*;

public class Panel_Main extends JPanel {
    // Instanciando areas de texto que van a servir para input e output
    public JTextArea input_texto = new JTextArea();
    public JTextArea output_console = new JTextArea();
    // Instanciando botón que va a mostrar imágenes
    JButton show = new JButton();
    // Instanciando Jlabel en donde irá la imagen
    JLabel label_picture = new JLabel();

    public Panel_Main() {
        // Definiendo propiedades del panel principal
        this.setBackground(Color.GRAY);
        this.setBounds(0, 0, 1300, 800);
        this.setLayout(null);
        // Definiendo propiedades del área de texto del input
        JScrollPane scroll_input = new JScrollPane(input_texto);
        scroll_input.setBounds(100, 50, 600, 400);
        // Definiendo propiedades del área de texto del output
        output_console.setEditable(false);
        JScrollPane scroll_output = new JScrollPane(output_console);
        scroll_output.setBounds(100, 550, 1100, 150);
        // Agregando componentes al panel
        this.add(scroll_input);
        this.add(scroll_output);

    }


}
