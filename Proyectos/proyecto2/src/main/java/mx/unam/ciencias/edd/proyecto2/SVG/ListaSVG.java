package mx.unam.ciencias.edd.proyecto2.SVG;

import mx.unam.ciencias.edd.Lista;

/**
 * Clase para representar Listas doblemente ligada .
 */
public class ListaSVG {


  /** Los elementos que vna a ser visualizados */
  private Lista<Integer> elementos = new Lista<Integer>();

  /** Inicio de SVG */
  private String inicioSVG = "<?xml version = \'1.0\' encoding = \'utf-8\' ?> \n";

  /**
   * Contructor que recibe la lista con los elementos.
   * @param enteros Los elementos.
   */
  public ListaSVG(Lista<Integer> elementos){
    this.elementos = elementos;
  }

  /**
   * setLongitud. Longitud del archivo SVG.
   * @return La cadena con medidas del archivo SVG.
   */
  public String setLongitud() {
    int enteros = elementos.getElementos();
    int ancho = (enteros * 35) + (enteros - 1) * 20 - 2;//ancho del output
    String longitudSVG = String.format("<svg width=\'%d\' height=\'40\'>", ancho);
    return longitudSVG += "\n <g>";
  }

  /**
   * finaliza. Termina el archivo SVG.
   * @return La cadena que termina el archivo SVG.
   */
  public String finaliza() {
    return "\n </g> \n</svg>";
  }

  /**
   * graficaRectanguloLista. Grafica los rectángulos de la lista.
   * @param x el entero desde donde vamos a empezar a graficar el rectángulo.
   * @return el rectángulo en SVG.
   */
  public String graficaRectanguloLista(int x){
    String rectangulo = String.format("\n<rect x=\'%s\' y=\'8\' rx=\'5\' ry =\'5\'" +
     " width=\'35\' height=\'25\'" +
     " style=\'fill:white;stroke:black;stroke-width:2;opacity:100\' />", x);
    return rectangulo;
  }

  /**
   * elementoListaSVG. Grafica el elemento en la Lista.
   * @param x el entero desde donde vamos a empezar a graficar el elementos de la Lista.
   * @param elemento el elemento de la Lista.
   * @return el elemento en SVG.
   */
  public String elementoListaSVG(int x, int elemento){
    String texto = String.format("\n<text x=\'%s\' y=\'25\'" +
    " font-family=\'Roboto-Regular\' font-size=\'10\' fill=\'black\'>" +
    "%d</text>", x , elemento);
    return texto;
  }

  /**
   * graficaFlechaDoblementeLigada. Grafica las conexiones de la Lista.
   * @param x el entero desde donde vamos a empezar a graficar la flecha.
   * @return La flecha bidireccional.
   */
  public String graficaFlechaDoblementeLigada(int x){
    String dobleFlecha = String.format("\n<text x=\'%d\' y=\'20\' " +
    "font-family=\'Roboto-Regular\' font-size=\'10\' fill=\'black\'>⟷</text>", x);
    return dobleFlecha;
  }

  /**
   * listaSVG. Regresa la representación de una Lista en SVG
   * @return la Lista en SVG
   */
  public String listaEnSVG() {
      String cadena = inicioSVG + this.setLongitud();
      String lista = "";
      int x = 2;
      int w;
      for (int i = 0; i < elementos.getLongitud(); i++) {
          int m = i;
          lista += graficaRectanguloLista(x);
          if (elementos.get(i) < 10) {
              lista += elementoListaSVG(x + 14, elementos.get(i));
            } else if (elementos.get(i) >= 10 && elementos.get(i) < 100) {
                lista += elementoListaSVG(x + 12, elementos.get(i));
            } else {
                lista += elementoListaSVG(x + 8, elementos.get(i));
            }
            x += 35 + 14; //espacio entre lista
            w = x - 14; //flechas
            if (m < elementos.getLongitud() - 1) {
                lista += graficaFlechaDoblementeLigada(w);    
            }
        }
        return cadena += lista + this.finaliza();
  }
}