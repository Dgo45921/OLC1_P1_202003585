/* 1. Package e importaciones */
package Analizadores;
import java_cup.runtime.*;

%%

/* 2. Configuraciones para el análisis (opciones y declaraciones) */

%{
// acá se agrega código en java


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
    yyline = 0;
    yychar = 0;
%init}


//Expresiones regulares
ESPACIOS = [\r|\f|\s|\t|\n]
CADENA = \"[^\"]\"
MULTILINEA = <![\s\S]*!>
UNA_LINEA = \/\/.*
LETRA = [A-Za-z]
NUMERO = \d+
ESPECIALES = \\n|\\\'|\\\"
SYMBOL = [!-$]|[&-)]|\/|-|[<->]|@|[\[-\`]
IDENTIFICADOR = [a-zA-Z_][a-zA-Z0-9_]*

%%
/* 3. Reglas semánticas */

"CONJ" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.reservada_conjunto, yyline, yycolumn, yytext());}
"{" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.llave_abre, yyline, yycolumn, yytext());}
"}" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.llave_cierra, yyline, yycolumn, yytext());}
":" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.dos_puntos, yyline, yycolumn, yytext());}
";" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.punto_y_coma, yyline, yycolumn, yytext());}
"%" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.porcentaje, yyline, yycolumn, yytext());}
"." {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.concat, yyline, yycolumn, yytext());}
"~" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.virgulilla, yyline, yycolumn, yytext());}
"," {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.coma, yyline, yycolumn, yytext());}
"->" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.flecha, yyline, yycolumn, yytext());}
"*" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.klenee, yyline, yycolumn, yytext());}
"|" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.or, yyline, yycolumn, yytext());}
"+" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.plus, yyline, yycolumn, yytext());}
"?" {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.interrogacion, yyline, yycolumn, yytext());   }


{LETRA}  {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.letra, yyline, yycolumn, yytext());}
{IDENTIFICADOR} {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.id, yyline, yycolumn, yytext());}
{CADENA} {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.cadena, yyline, yycolumn, yytext());}
{NUMERO} {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.numero, yyline, yycolumn, yytext());}
{SYMBOL} {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.symbol, yyline, yycolumn, yytext());}
{ESPECIALES} {System.out.println("Se encontró token en linea: "+ yyline+ " columna: "+ yycolumn+ " con valor : "+ yytext()); return new Symbol(sym.especial, yyline, yycolumn, yytext());}

{ESPACIOS}   {}
{MULTILINEA} {}
{UNA_LINEA} {}

. {
    //Aqui se debe guardar los valores (yytext(), yyline, yychar ) para posteriormente generar el reporte de errores Léxicos.

    System.out.println("Este es un error lexico: "+yytext()+ ", en la linea: "+yyline+", en la columna: "+yychar);
}




