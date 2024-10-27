package mx.unam.ciencias.edd.proyecto2.SVG;

import mx.unam.ciencias.edd.*;

/**
 * Clase abstracta para definir las representaciones en SVG de Árboles Binarios.
 */
public abstract class ArbolBinarioSVG {

  /** El arbol que representaremos */
  private Lista<Integer> arbolSVG = new Lista<Integer>();
  /** Primer línea de SVG */
  private String inicioSVG = "<?xml version = \'1.0\' encoding = \'utf-8\' ?>\n";
  /** Representación del arbol en SVG */
  private static String arbol = "";

  /** Constructor vacío */
  public ArbolBinarioSVG() {
  }

  /**
   * finaliza. Termina el archivo SVG.
   * 
   * @return La cadena que termina el archivo SVG.
   */
  public String finaliza() {
    return "\n </g> \n</svg>";
  }

  /**
   * setLongitud. Determina el ancho y la altura del archivo SVG.
   * 
   * @param ancho  El ancho del archivo.
   * @param altura La altura del archivo.
   * @return El ancho y la altura del archivo SVG.
   */
  public String setLongitudArbol(int ancho, int altura) {
    String cadena = String.format(
        "<svg width=\'%d\'" + " height=\'%d\'>",
        ancho,
        altura);
    return inicioSVG + cadena + "\n <g>";
  }

  /**
   * verticeArbol. El vértice del árbol binario.
   * 
   * @param cx La coordenada del centro del vértice.
   * @param cy La coordenada del centro del vértice
   * @return El vertice del árbol binario.
   */
  public String verticeArbol(int cx, int cy) {
    String vertice = String.format(
        "\n<circle cx=\'%s\' cy=\'%d\' r=\'25\' " +
            "stroke=\'black\' stroke-width=\'2\' fill=\'white\' />",
        cx,
        cy);
    return vertice;
  }

  /**
   * elementoArbolBinario. El elemento del árbol binario.
   * 
   * @param vertice El vértice donde está el elemento del árbol binario.
   * @param x       el entero desde donde vamos a empezar a graficar el elemento.
   * @param y       el entero desde donde vamos a empezar a graficar el elemento.
   * @return Representación del elemento del vértice en SVG.
   */
  public String elementoArbolBinario(
      VerticeArbolBinario vertice,
      int x,
      int y) {
    if (vertice.get().toString().length() == 1) {
      x -= 5;
    } else if (vertice.get().toString().length() == 2) {
      x -= 9;
    } else {
      x -= 14;
    }
    String elemento = String.format(
        "\n<text x=\'%s\' y=\'%s\'" +
            " font-family=\'Roboto-Regular\' font-size=\'16\' fill=\'black\'>" +
            "%d</text>",
        x,
        y,
        vertice.get());
    return elemento;
  }

  /**
   * aristaAb. Grafica las aristas del árbol binario.
   * 
   * @param x1 Coordenada en x donde va a iniciar la línea.
   * @param y1 Coordenada en y donde va a iniciar la línea.
   * @param x2 Coordenada en x donde va a terminar la línea.
   * @param y2 Coordenada en y donde va a terminar la línea.
   * @return La arista del árbol binario en SVG.
   */
  public String aristaAb(int x1, int y1, int x2, int y2) {
    String arista = String.format(
        "\n<line x1=\'%d\' y1=\'%d\' x2=\'%d\' " +
            "y2=\'%d\' stroke=\'black\' stroke-width=\'2\' />",
        x1,
        y1,
        x2,
        y2);
    return arista;
  }

  /**
   * graficaArbolBinario. Grafica el árbol binario.
   */
  public void graficaArbolBinario(
      VerticeArbolBinario vertice,
      int x,
      int y,
      int z) {
    arbol += verticeArbol(z, y + 25);
    arbol += elementoArbolBinario(vertice, z, y + 30);
    if (vertice.hayIzquierdo()) {
      int izquierda = (z - x) / 2;
      arbol += aristaAb(z - 25, y + 25, z - izquierda, y + 60);
      graficaArbolBinario(vertice.izquierdo(), x, y + 40, z - izquierda);
    }
    if (vertice.hayDerecho()) {
      int derecha = (z - x) / 2;
      arbol += aristaAb(z + 25, y + 25, z + derecha, y + 55);
      graficaArbolBinario(vertice.derecho(), z, y + 40, z + derecha);
    }
  }

  /**
   * arbolBinarioSVG. Regresa el árbol binario en SVG.
   * 
   * @return La representación de un árbol binario en SVG.
   */
  public String arbolBinarioSVG() {
    return arbol;
  }
}
