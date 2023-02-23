rm Lexico.java
rm Sintactico.java
rm sym.java
java -jar jflex-full-1.7.0.jar Lexico.jflex
java -jar java-cup-11b.jar -parser Sintactico -symbols sym Sintactico.cup
