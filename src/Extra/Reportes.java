package Extra;

import AFND.AFND;
import Arboles.NodoArbol;
import Arboles.Transition;
import Ventanas.Main;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class Reportes {
    public String codigo_arbol = "digraph G\n" +
            "{\n" +
            "fontsize=\"5\"" +
            "node [shape = record];";

    public int id = 0;


    public void dotTree(NodoArbol root) {
        if (root != null) {
            root.lexema = root.lexema.replace("\"", "\\\"").replace("", "");
            if (root.lexema.equals("|")) {
                root.lexema = "\\|";
            }
            if (root.lexema.equals("\\\\\"")) {
                root.lexema = "\\\\\\\"";
            }
            if (root.lexema.equals("\\'")) {
                root.lexema = "\\\\'";
            }
            if (root.lexema.equals("\\n")) {
                root.lexema = "\\\\n";
            }

            codigo_arbol += "nodo" + root.id_2 + " [label=\"{Primeros\\n" + root.primeros + "}|" + "{"+ root.lexema +"|" + root.anulable +"}|" +"{ultimos\\n" + root.ultimos + "}" + "\"];\n"; // crear el nodo con el id y el texto
            if (root.izquierda != null) {
                codigo_arbol += "nodo" + root.id_2 + " -> " + "nodo" + root.izquierda.id_2 + "[minlen=3 ]" + ";\n"; // agregar la conexión con el hijo izquierdo
            }
            if (root.derecha != null) {
                codigo_arbol += "nodo" + root.id_2 + " -> " + "nodo" + root.derecha.id_2 + "[minlen=3 ]" + ";\n"; // agregar la conexión con el hijo derecho
            }
            dotTree(root.izquierda);
            dotTree(root.derecha);
        }
    }

    public void generate_tree(String name) {
        String dotFilePath = "ARBOLES_202003585/" + "Arbol_" + name + ".dot";
        String pngFilePath = "ARBOLES_202003585/" + "Arbol_" + name + ".png";
        codigo_arbol += "}";
        try {
            FileWriter archivo = new FileWriter(dotFilePath);
            PrintWriter pw = new PrintWriter(archivo);
            pw.println(codigo_arbol);
            archivo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Process p = Runtime.getRuntime().exec("dot -Tpng " + dotFilePath + " -o " + pngFilePath);
            p.waitFor();
            System.out.println("Arbol generado exitosamente en: " + pngFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }



    public void Generate_SigPosTable(ArrayList<ArrayList> table, String name) {
        String texto = "digraph G {\n" +
                "    rankdir=LR\n" +
                "    node [shape=none fontname=Helvetica]\n" +
                "\n" +
                "    A [label=<\n" +
                "      <TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\">\n" +
                "       <TR PORT=\"header\">\n" +
                "        <TD BGCOLOR=\"#d23939\" COLSPAN=\"3\">TABLA DE SIGUIENTES</TD>\n" +
                "       </TR>\n" +
                "       <TR>\n" +
                "        <TD BGCOLOR=\"#ff6363\">Num_hoja</TD>\n" +
                "        <TD BGCOLOR=\"#ff6363\">Simbolo</TD>\n" +
                "        <TD BGCOLOR=\"#ff6363\">Siguientes</TD>\n" +
                "       </TR>";


        for (int i = 0; i < table.size() - 1; i++) {
            for (int j = 0; j < table.size() - i - 1; j++) {
                int num_hoja1 = (int) ((ArrayList) ((ArrayList) table).get(j)).get(0);
                int num_hoja2 = (int) ((ArrayList) ((ArrayList) table).get(j + 1)).get(0);
                if (num_hoja1 > num_hoja2) {
                    ArrayList aux2 = table.get(j);
                    table.set(j, table.get(j + 1));
                    table.set(j + 1, aux2);

                }
            }
        }


        for (ArrayList item : table) {
            texto += "<TR>\n";
            texto += "<TD>" + item.get(0) + "</TD>" + "\n";
            texto += "<TD>" + item.get(1) + "</TD>" + "\n";
            texto += "<TD>" + item.get(2) + "</TD>" + "\n";
            texto += "</TR>\n";
        }
        texto += "</TABLE>\n" + "    >];\n" + "}";


        String dotFilePath = "SIGUIENTES_202003585/" + "siguientes_" + name + ".dot";
        String pngFilePath = "SIGUIENTES_202003585/" + "siguientes_" + name + ".png";

        try {
            FileWriter archivo = new FileWriter(dotFilePath);
            PrintWriter pw = new PrintWriter(archivo);
            pw.println(texto);
            archivo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            Process p = Runtime.getRuntime().exec("dot -Tpng " + dotFilePath + " -o " + pngFilePath);
            p.waitFor();
            System.out.println("Se generó la tabla de siguientes: " + pngFilePath);


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }

    public void generateTransitionTable(ArrayList<ArrayList> estados, ArrayList<NodoArbol> hojas, String name) {
        ArrayList<String> encabezados = new ArrayList<>();
        encabezados.add("Estados/Terminales");
        String texto = "digraph G {\n" +
                "    rankdir=LR\n" +
                "    node [shape=none fontname=Helvetica]\n" +
                "\n" +
                "    A [label=<\n" +
                "      <TABLE BORDER=\"0\" CELLBORDER=\"1\" CELLSPACING=\"0\">\n" +
                "       <TR>\n";


        for (NodoArbol hojita : hojas) {
            if (!Objects.equals(hojita.lexema, "#") && !checkRepetido(encabezados, hojita.lexema.replace("\\\"", "\""))) {
                encabezados.add(hojita.lexema.replace("\\\"", "\""));
            }
        }
        for (String terminal : encabezados) {
            texto += "<TD BGCOLOR=\"#ff6363\">" + terminal + "</TD>\n";
        }

        texto += "</TR>\n";


        for (int i = 0; i < estados.size(); i++) {
            texto += "<TR>\n";
            ArrayList state = estados.get(i);

            String sigpos_estado = "";
            for (int j = 0; j < ((ArrayList) state.get(1)).size(); j++) {
                Object tr = (ArrayList) state.get(1);
                Integer t = (Integer) ((ArrayList) tr).get(j);
                if (j != ((ArrayList<?>) state.get(1)).size() - 1) {
                    sigpos_estado += t.toString() + ",";
                } else {
                    sigpos_estado += t.toString();
                }

            }
            if ((Boolean) state.get(3)) {
                texto += "<TD>" + state.get(0) + "* {" + sigpos_estado + "}" + "</TD>\n";
            } else {
                texto += "<TD>" + state.get(0) + " {" + sigpos_estado + "}" + "</TD>\n";
            }


            for (int j = 1; j < encabezados.size(); j++) {
                String respuesta = findFinalState(encabezados.get(j).replace("\\\"", "\"").replace("\\\\'", "\\'").replace("\\\\n", "\\n"), (ArrayList) state.get(2));
                texto += "<TD>" + respuesta + "</TD>\n";
            }


            texto += "</TR>\n";
        }
        texto += "</TABLE>\n" + "    >];\n" + "}";

        // metodos para generar el .dot

        String dotFilePath = "TRANSICIONES_202003585/" + "Transicion_" + name + ".dot";
        String pngFilePath = "TRANSICIONES_202003585/" + "Transicion_" + name + ".png";
        try {
            FileWriter archivo = new FileWriter(dotFilePath);
            PrintWriter pw = new PrintWriter(archivo);
            pw.println(texto);
            archivo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Process p = Runtime.getRuntime().exec("dot -Tpng " + dotFilePath + " -o " + pngFilePath);
            p.waitFor();
            System.out.println("Tabla de transiciones generada exitosamente en: " + pngFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


    public String findFinalState(String terminal, ArrayList transiciones) {
        for (Object transicion : transiciones) {
            if (Objects.equals(terminal, ((Transition) transicion).transition)) {
                return ((Transition) transicion).finalState;
            }

        }

        return "-";
    }


    public Boolean checkRepetido(ArrayList<String> lista, String terminal) {

        for (int i = 0; i < lista.size(); i++) {
            if (terminal.equals(lista.get(i))) {
                return true;
            }
        }

        return false;
    }

    public void generateErrorReport() {
        try {
            FileWriter archivo = new FileWriter("ERRORES_202003585/errores.html");
            PrintWriter pw = new PrintWriter(archivo);
            pw.println("<!DOCTYPE html>\n");
            pw.println("<html>\n");
            pw.println("<head>\n");
            pw.println("<meta charset=\"UTF-8\">");
            pw.println("<title>Reporte de errores</title>\n");
            pw.println("<link rel=\"stylesheet\" href=\"../style.css\">");
            pw.println("</head>\n");
            pw.println("<body>\n");
            pw.println("<h1>Reporte de errores</h1>");
            pw.println("<table>\n");
            pw.println("<tr>\n");
            pw.println("<th>Tipo</th>\n");
            pw.println("<th>Lexema</th>\n");
            pw.println("<th>Fila</th>\n");
            pw.println("<th>Columna</th>\n");
            pw.println("</tr>\n");
            for (Errores err : Main.lista_errores) {
                pw.println("<tr>\n");
                pw.println("<td>" + err.getTipo() + "</td>");
                pw.println("<td>" + "\"" + err.getLexema() + "\"" + "</td>");
                pw.println("<td>" + err.getFila() + "</td>");
                pw.println("<td>" + err.getColumna() + "</td>");
                pw.println("</tr>\n");
            }
            pw.println("</table>\n");
            pw.println("</body>\n");
            pw.println("</html>\n");
            archivo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generate_AFD(ArrayList<ArrayList> estados,String name) {
        String texto = "digraph AFD {\n" +
                "labelloc=\"t\";\n" +
                "    label=\""+ name +"\";"+
                "\tfontname=\"Helvetica,Arial,sans-serif\"\n" +
                "\tnode [fontname=\"Helvetica,Arial,sans-serif\"]\n" +
                "\tedge [fontname=\"Helvetica,Arial,sans-serif\"]\n" +
                "\trankdir=LR;\n" +
                "begin[shape=none label=\"\"];\n" +
                "node[shape=circle]" +
                "begin->" + "S0" + "; \n"+
        "\tnode [shape = doublecircle]; ";
        // definiendo que nodos serán los que serán de aceptación en el grafo
        for (int i = 0; i < estados.size(); i++) {
            ArrayList estado = estados.get(i);
            if ((Boolean) estado.get(3)) {
                if (i != estados.size() - 1) {
                    texto += estado.get(0) + ",";
                } else {
                    texto += estado.get(0) + "; \n";
                }
            }
        }

        texto += "node [shape = circle]; \n";
        texto = texto.replace(",node", " node");

        // haciendo las conexiones entre los nodos

        for (int i = 0; i < estados.size(); i++) {
            ArrayList estado = estados.get(i);
            ArrayList transiciones = (ArrayList) estado.get(2);
            for (int j = 0; j < transiciones.size(); j++) {
                Transition trans = (Transition) transiciones.get(j);
                String x = trans.transition.replace("\"", "\\\"").replace("", "");
                if (x.equals("\\\\\"")) {
                    x = "\\\\\\\"";
                } else if (x.equals("\\'")) {
                    x = "\\\\'";
                } else if (x.equals("\\n")) {
                    x = "\\\\n";
                }
                texto += trans.initialState + "->" + trans.finalState + " [label = \"" + x + "\"];\n";
            }

        }


        texto += "}";
        //System.out.println(texto);
        String dotFilePath = "AFD_202003585/" + "AFD_" + name + ".dot";
        String pngFilePath = "AFD_202003585/" + "AFD_" + name + ".png";
        try {
            FileWriter archivo = new FileWriter(dotFilePath);
            PrintWriter pw = new PrintWriter(archivo);
            pw.println(texto);
            archivo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Process p = Runtime.getRuntime().exec("dot -Tpng " + dotFilePath + " -o " + pngFilePath);
            p.waitFor();
            System.out.println("AFD generado exitosamente en: " + pngFilePath);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }


    }


    public void generate_Json() {
        Date d = new Date();
        Gson gson = new GsonBuilder()
                .setExclusionStrategies()
                .setPrettyPrinting() // Agregar esta línea
                .create();

        String json = gson.toJson(Main.lista_evaluaciones);
        try {
            FileWriter writer = new FileWriter("SALIDAS_202003585/salida" + d.getHours() + "_" + d.getMinutes() + "_" + d.getSeconds() + "_" + ".json");
            writer.write(json);
            writer.close();
            System.out.println("El archivo se ha guardado correctamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al guardar el archivo.");
            e.printStackTrace();
        }
    }


    public AFND generar_AFND(NodoArbol root, int indice) {
        ArrayList<Transition> transiciones = new ArrayList<>();
        AFND afnd1, afnd2;
        String estado_inicial = "S" + indice;
        String estado_final = "";
        switch (root.type) {
            case HOJA:
                String lexema_hoja = root.lexema;
                transiciones.add(new Transition(estado_inicial, lexema_hoja, "S" + (indice + 1)));
                return new AFND(estado_inicial, "S" + (indice + 1), transiciones);
            case AND:
                afnd1 = generar_AFND(root.izquierda, indice);
                transiciones.addAll(afnd1.getTransiciones());
                afnd2 = generar_AFND(root.derecha, Integer.parseInt(afnd1.Estado_final.replace("S", "")));
                transiciones.addAll(afnd2.getTransiciones());
                estado_final = "S" + (Integer.parseInt(afnd2.Estado_final.replace("S", "")));
                break;
            case OR:
                afnd1 = generar_AFND(root.izquierda, indice + 1);
                transiciones.add(new Transition(estado_inicial, "ε", afnd1.Estado_inicial));
                transiciones.addAll(afnd1.getTransiciones());
                afnd2 = generar_AFND(root.derecha, Integer.parseInt(afnd1.Estado_final.replace("S", "")) + 1);
                transiciones.add(new Transition(estado_inicial, "ε", afnd2.Estado_inicial));
                transiciones.addAll(afnd2.getTransiciones());
                estado_final = "S" + (Integer.parseInt(afnd2.Estado_final.replace("S", "")) + 1);
                transiciones.add(new Transition(afnd1.Estado_final, "ε", estado_final));
                transiciones.add(new Transition(afnd2.Estado_final, "ε", estado_final));
                break;
            case PLUS:
                afnd1 = generar_AFND(root.izquierda, indice + 1);
                transiciones.add(new Transition(estado_inicial, "ε", afnd1.Estado_inicial));
                transiciones.addAll(afnd1.getTransiciones());
                transiciones.add(new Transition(afnd1.Estado_final, "ε", afnd1.Estado_inicial));
                estado_final = "S" + (Integer.parseInt(afnd1.Estado_final.replace("S", "")) + 1);
                transiciones.add(new Transition(afnd1.Estado_final, "ε", estado_final));
                break;
            case KLEENE:
                afnd1 = generar_AFND(root.izquierda, indice + 1);
                transiciones.add(new Transition(estado_inicial, "ε", afnd1.Estado_inicial));
                transiciones.addAll(afnd1.getTransiciones());
                transiciones.add(new Transition(afnd1.Estado_final, "ε", afnd1.Estado_inicial));
                estado_final = "S" + (Integer.parseInt(afnd1.Estado_final.replace("S", "")) + 1);
                transiciones.add(new Transition(afnd1.Estado_final, "ε", estado_final));
                transiciones.add(new Transition(estado_inicial, "ε", estado_final));
                break;
            case INTERROGACION:
                afnd1 = generar_AFND(root.izquierda, indice + 1);
                transiciones.add(new Transition(estado_inicial, "ε", afnd1.Estado_inicial));
                transiciones.addAll(afnd1.getTransiciones());
                estado_final = "S" + (Integer.parseInt(afnd1.Estado_final.replace("S", "")) + 1);
                transiciones.add(new Transition(afnd1.Estado_final, "ε", estado_final));
                transiciones.add(new Transition(estado_inicial, "ε", estado_final));
                break;
        }


        return new AFND(estado_inicial, estado_final, transiciones);

    }

    public void toDotAFND(String name, AFND afnd) {
        String afnd_text = "digraph G{\n" +
                "\tfontname=\"Helvetica,Arial,sans-serif\"\n" +
                "\tnode [fontname=\"Helvetica,Arial,sans-serif\"]\n" +
                "\tedge [fontname=\"Helvetica,Arial,sans-serif\"]\n" +
                "labelloc=\"t\";\n" +
                "    label=\""+ name +"\";"+
                "node [shape=doublecircle]; " + afnd.Estado_final + ";" + "rankdir=LR;\n" +
                "begin[shape=none label=\"\"];\n" +
                "node[shape=circle]" +
                "begin->" + afnd.Estado_inicial + "; \n";

        afnd_text+= "node[shape=circle]";

        for (Transition transicion : afnd.getTransiciones()) {
            afnd_text += transicion.initialState + "->" + transicion.finalState + "[label=" + "\"" + transicion.transition + "\"];\n";
        }

        afnd_text+= "}";


        String dotFilePath = "AFND_202003585/" + "AFND_" + name + ".dot";
        String pngFilePath = "AFND_202003585/" + "AFND_" + name + ".png";
        try {
            FileWriter archivo = new FileWriter(dotFilePath);
            PrintWriter pw = new PrintWriter(archivo);
            pw.println(afnd_text);
            archivo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Process p = Runtime.getRuntime().exec("dot -Tpng " + dotFilePath + " -o " + pngFilePath);
            p.waitFor();
            System.out.println("Arbol generado exitosamente en: " + pngFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }


}