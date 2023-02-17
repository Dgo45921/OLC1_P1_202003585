/* 1. Package e importaciones */
package Analizadores;
import java_cup.runtime.Symbol;

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

%%
/* 3. Reglas semánticas */

"CONJ" {return new Symbol(sym.reservada_conjunto, yyline, yycolumn, yytext());}
"{" {return new Symbol(sym.llave_abre, yyline, yycolumn, yytext());}
"}"  {return new Symbol(sym.llave_cierra, yyline, yycolumn, yytext());}
":"  {return new Symbol(sym.dos_puntos, yyline, yycolumn, yytext());}
";"  {return new Symbol(sym.punto_y_coma, yyline, yycolumn, yytext());}
"%"  {return new Symbol(sym.porcentaje, yyline, yycolumn, yytext());}
"."  {return new Symbol(sym.concat, yyline, yycolumn, yytext());}
"~"  {return new Symbol(sym.virgulilla, yyline, yycolumn, yytext());}
","  {return new Symbol(sym.coma, yyline, yycolumn, yytext());}
"->"  {return new Symbol(sym.flecha, yyline, yycolumn, yytext());}
"*"  {return new Symbol(sym.klenee, yyline, yycolumn, yytext());}
"|" {return new Symbol(sym.or, yyline, yycolumn, yytext());}
"+" {return new Symbol(sym.plus, yyline, yycolumn, yytext());}
"?" {return new Symbol(sym.interrogacion, yyline, yycolumn, yytext());}




{CADENA}} {return new Symbol(sym.cadena, yyline, yycolumn, yytext());}
{LETRA} {return new Symbol(sym.letra, yyline, yycolumn, yytext());}
{NUMERO} {return new Symbol(sym.numero, yyline, yycolumn, yytext());}
{SYMBOL} {return new Symbol(sym.symbol, yyline, yycolumn, yytext());}
{ESPECIALES} {return new Symbol(sym.especial, yyline, yycolumn, yytext());}

{ESPACIOS}   {}
{MULTILINEA} {}
{UNA_LINEA} {}

. {
    //Aqui se debe guardar los valores (yytext(), yyline, yychar ) para posteriormente generar el reporte de errores Léxicos.

    System.out.println("Este es un error lexico: "+yytext()+ ", en la linea: "+yyline+", en la columna: "+yychar);
}




