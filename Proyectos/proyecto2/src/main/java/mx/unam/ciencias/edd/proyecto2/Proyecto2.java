package mx.unam.ciencias.edd.proyecto2;

public class Proyecto2 {

    public static Graficador input;

    /**
     * Proyecto 2: Programa para graficar las estructuras de datos que hemos
     * cubierto a lo largo del curso utilizando SVG
     */
    public static void main(String[] args) {
        input = new Graficador();
        input.leeInput(args);   
    }

}