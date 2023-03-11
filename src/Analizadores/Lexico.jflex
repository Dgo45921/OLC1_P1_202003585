/* 1. Package e importaciones */
package Analizadores;
import java_cup.runtime.*;
import Extra.Errores;
import Ventanas.Main;

%%

/* 2. Configuraciones para el análisis (opciones y declaraciones) */

%{
// aca se definen variables
String comentario = "";
boolean flag = false;
%}

//Directivas

%class Lexico
%public
%cup
%char
%column
%full
%line
%unicode


//Inicializar el contador de columna y fila con 1
%init{
    yyline = 1;
    yycolumn = 1;
    yychar = 1;
%init}


//Expresiones regulares
MENOR = <
ESPACIOS = [\r|\f|\s|\t|\n]
CADENA = \"(\\.|[^\"])*\"
UNA_LINEA = \/\/.*
LETRA = [A-Za-z]
NUMERO = \d+
ESPECIALES = \\n|\\\'|\\\"
SYMBOL = [!-$]|[&-)]|\/|-|[=->]|@|[\[-\`]
IDENTIFICADOR = [a-zA-Z][a-zA-Z0-9_]*
FLECHA = -\s*>

%state YYINITIAL
%state MULTI
%state VERIFICAR


%%
/* 3. Reglas semánticas */

<YYINITIAL> "CONJ" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.reservada_conjunto, yyline, yycolumn, yytext());}
<YYINITIAL> "{" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.llave_abre, yyline, yycolumn, yytext());}
<YYINITIAL> "}" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.llave_cierra, yyline, yycolumn, yytext());}
<YYINITIAL> ":" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.dos_puntos, yyline, yycolumn, yytext());}
<YYINITIAL> ";" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.punto_y_coma, yyline, yycolumn, yytext());}
<YYINITIAL> "%" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.porcentaje, yyline, yycolumn, yytext());}
<YYINITIAL> "." {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.concat, yyline, yycolumn, yytext());}
<YYINITIAL> "~" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.virgulilla, yyline, yycolumn, yytext());}
<YYINITIAL> "," {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.coma, yyline, yycolumn, yytext());}
<YYINITIAL> "*" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.klenee, yyline, yycolumn, yytext());}
<YYINITIAL> "|" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.or, yyline, yycolumn, yytext());}
<YYINITIAL> "+" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.plus, yyline, yycolumn, yytext());}
<YYINITIAL> "?" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.interrogacion, yyline, yycolumn, yytext());   }

<YYINITIAL> {FLECHA} {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.flecha, yyline, yycolumn, yytext());}
<YYINITIAL> {LETRA}  {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.letra, yyline, yycolumn, yytext());}
<YYINITIAL> {IDENTIFICADOR} {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.id, yyline, yycolumn, yytext());}
<YYINITIAL> {CADENA} {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.cadena, yyline, yycolumn, yytext());}
<YYINITIAL> {NUMERO} {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.numero, yyline, yycolumn, yytext());}
<YYINITIAL> {MENOR}  {yybegin(VERIFICAR); comentario=yytext();}
<YYINITIAL> {SYMBOL} {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.symbol, yyline, yycolumn, yytext());}
<YYINITIAL> {ESPECIALES} {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.especial, yyline, yycolumn, yytext());}
<YYINITIAL> {UNA_LINEA} {}
<YYINITIAL> {ESPACIOS}   {}

<VERIFICAR>{

        [^!] {

                yybegin(YYINITIAL);
                yypushback(1);
                //System.out.println("que onda");
                System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ "<");
                return new Symbol(sym.symbol, yyline, yycolumn, "<");

                }
        [\!] {
                yybegin(MULTI);

                }

          }

<MULTI>{

     \! {flag=true;}
      \> {
              if(flag){
                  yybegin(YYINITIAL);
              }
          }
      [^\!\>] {}

}




. {
    //Aqui se debe guardar los valores (yytext(), yyline, yychar ) para posteriormente generar el reporte de errores Léxicos.

    System.out.println("Este es un error lexico: "+yytext()+ ", en la linea: "+yyline+", en la columna: "+yychar);
    Errores error = new Errores(yyline, yycolumn, yytext(), "Léxico");
    Main.lista_errores.add(error);
}




