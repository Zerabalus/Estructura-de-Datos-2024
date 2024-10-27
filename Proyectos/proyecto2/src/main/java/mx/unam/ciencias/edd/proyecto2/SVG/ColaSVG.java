package mx.unam.ciencias.edd.proyecto2.SVG;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase para representar Cola 
 */
public class ColaSVG {

    /** Los elementos de la Cola */
    Lista<Integer> colaSVG = new Lista<Integer>();
    /** El inicio del archivo SVG */
    private String inicio = "<?xml version = \'1.0\' encoding = \'utf-8\' ?> \n";

    /**
    * Constructor
    * @param colaSVG La Lista de los elementos de la Cola.
    */
    public ColaSVG(Lista<Integer> colaSVG) {
        this.colaSVG = colaSVG;
    }

    /**
    * setLongitud. Determina el ancho y la altura del archivo SVG.
    * @return El ancho y la altura del archivo SVG.
    */
    public String setLongitud() {
        int longitud = colaSVG.getLongitud();
        int ancho = (longitud * 36) + (longitud * 7) - 2;
        String cadena = String.format("<svg width=\'%d\' height=\'95\'>", ancho);
        return cadena += "\n <g>";
    }


    /**
    * graficaRectangulo. Grafica los rectángulos de la lista.
    * @param x el entero desde donde vamos a empezar a graficar el rectángulo.
    * @return el rectángulo en SVG.
    */
    public String graficaRectangulo(int x) {
        String rectangulo = String.format("\n<rect x=\'%s\' y=\'8\' rx=\'5\' ry =\'5\'" +
        " width=\'35\' height=\'25\'" +
        " style=\'fill:white;stroke:black;stroke-width:2;opacity:100\' />", x);
        return rectangulo;
    }


    /**
    * elementoPilaSVG. Grafica el elemento en la Cola.
    * @param x el entero desde donde vamos a empezar a graficar el elemento de la Cola.
    * @param elemento el elemento de la Cola.
    * @return el elemento en SVG.
    */
    public String elementoColaSVG(int x, int elemento) {
        String cadena = String.format("\n<text x=\'%s\' y=\'25\'" +
        " font-family=\'Roboto-Regular\' font-size=\'10\' fill=\'black\'>" +
        "%d</text>", x , elemento);
        return cadena;
    }

    /**
    * finaliza. Termina el archivo SVG.
    * @return La cadena que termina el archivo SVG.
    */
    public String cierraSVG() {
        return "\n </g> \n</svg>";
    }

    /**
    * graficaFlechaDoblementeLigada. Grafica las conexiones de la Cola.
    * @param x el entero desde donde vamos a empezar a graficar la flecha.
    * @return La flecha.
    */
    public String graficaFlecha(int x) {
        String flecha = String.format("\n<text x=\'%d\' y=\'20\' " +
        "font-family=\'Roboto-Regular\' font-size=\'10\' fill=\'black\'>←</text>", x);
        return flecha;
    }


    /**
    * colaEnSVG. Regresa una representación en SVG de una Cola.
    * @return La representación en SVG de una Cola.
    */
    public String colaEnSVG() {
        String cadena = inicio + setLongitud();
        String cola = "";
        int x = 2;
        int w;
      for (int i = 0; i < colaSVG.getLongitud(); i++) {
          int m = i;
          cola += graficaRectangulo(x);
          if (colaSVG.get(i) < 10) {
              cola += elementoColaSVG(x + 14,colaSVG.get(i));
            } else if (colaSVG.get(i) >= 10 && colaSVG.get(i) < 100) {
                cola += elementoColaSVG(x + 12,colaSVG.get(i));
            } else {
                cola += elementoColaSVG(x + 8, colaSVG.get(i));
            }
            x += 35 + 8;
            w = x - 8;
            if (m < colaSVG.getLongitud() - 1) {
                cola += graficaFlecha(w);    
            }
        }
        return cadena += cola + cierraSVG();
        
    }
}