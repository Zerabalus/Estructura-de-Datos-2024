package mx.unam.ciencias.edd.proyecto2.SVG;
import mx.unam.ciencias.edd.Lista;

/**
 * Clase para representar Pila 
 */
public class PilaSVG {

  /** Los elementos de la Pila */
  private Lista<Integer> pilaSVG = new Lista<Integer>();
  /** El inicio del archivo SVG */
  private String inicio = "<?xml version = \'1.0\' encoding = \'utf-8\' ?> \n";

  /**
   * Constructor
   * @param pilaSVG La Lista de los elementos de la Pila.
   */
  public PilaSVG(Lista<Integer> pilaSVG) {
    this.pilaSVG = pilaSVG;
  }

  /**
   * setLongitud. Determina el ancho y la altura del archivo SVG.
   * @return El ancho y la altura del archivo SVG.
   */
  public String setLongitud() {
    int altura = (pilaSVG.getLongitud() * 26) - 2;
    String cadena = String.format("<svg width=\'101\' height=\'%d\'>",altura);
    return cadena += "\n <g>";
  }
    
  /**
    * cierre. Hace el cierre del archivo SVG.
    * @return La cadena que cierra el archivo SVG.
    */
  public String cierraSVG(){
    return "\n </g> \n</svg>";
  }
  
  /**
   * dibujaRectangulo. Dibuja los rectángulos de la lista.
   * @param y el entero desde donde vamos a empezar a dibujar el rectángulo.
   * @return el rectángulo en SVG.
   */
  public String dibujaRectangulo(int y){
    String rectangulo = String.format("\n<rect x=\'3\' y=\'%d\' rx=\'2\' ry =\'2\'" +
    " width=\'95\' height=\'25\' style=\'fill:white;" +
    "stroke:black;stroke-width:1;opacity:100\' />",y);
    return rectangulo;
  }

  /**
   * elementoPilaSVG. Dibuja el elemento en la Pila.
   * @param x el entero desde donde vamos a empezar a dibujar el elemento de la Pila.
   * @param elemento el elemento de la Pila.
   * @return el elemento en SVG.
   */
  public String elementoPilaSVG(int y, int elemento) {
    int x = 0;
    if(elemento < 10) {
      x = 48;
    } else if(elemento >= 100) {
      x = 42;
    } else {
      x = 45;
    }
    String texto = String.format("\n<text x=\'%s\' y=\'%s\'" +
    " font-family=\'Roboto-Regular\' font-size=\'10\' fill=\'black\'>" +
    "%d</text>",x,y,elemento);
    return texto;
  }
  
  /**
   * pilaEnSVG. Regresa una representación en SVG de una Pila.
   * @return La representación en SVG de una Pila.
   */
  public String pilaEnSVG() {
    pilaSVG = pilaSVG.reversa();
    String cadena = inicio + this.setLongitud();
    String pila = "";
    int y = 5;
    for(int entero : pilaSVG) {
      pila += dibujaRectangulo(y);
      pila += elementoPilaSVG(y + 16, entero);
      y += 25;
    }
    return cadena += pila + this.cierraSVG();
  }
}