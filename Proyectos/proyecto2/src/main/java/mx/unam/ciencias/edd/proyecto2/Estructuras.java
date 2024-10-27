package mx.unam.ciencias.edd.proyecto2;

/**
 * Clase para representar las Estructuras De Datos.
 */
public class Estructuras {

  /** Estructura de Datos */
  private String estructura;

  /**
   * Estructuras. Construye la edd.
   * @param estructura la estructura
   */
  public Estructuras(String estructura) {
    this.estructura = estructura;
  }

  /**
   * getEstructura. Regresa la Estructura De Datos.
   * @return estructura.
   */
  public String getEstructura() {
    return this.estructura;
  }

  /**
   * esLista. Determina si la Estructura de Datos es una Lista.
   * @param estructura La Estructura de Datos.
   */
  public boolean esLista(String estructura) {
    return (estructura.equals("Lista"));
  }

  /**
   * esPila. Determina si la Estructura de Datos es una Pila.
   * @param estructura La Estructura de Datos.
   */
  public boolean esPila(String estructura) {
    return estructura.equals("Pila");
  }

  /**
   * esCola. Determina si la Estructura de Datos es una Cola.
   * @param estructura La Estructura de Datos.
   */
  public boolean esCola(String estructura) {
    return estructura.equals("Cola");
  }

  /**
   * esArbolBinarioCompleto. Determina si la Estructura de Datos es una Arbol Binario Completo.
   * @param estructura La Estructura de Datos.
   */
  public boolean esArbolBinarioCompleto(String estructura) {
    return estructura.equals("ArbolBinarioCompleto");
  }

  /**
   * esArbolBinarioOrdenado. Determina si la Estructura de Datos es una Arbol Binario Ordenado.
   * @param estructura La Estructura de Datos.
   */
  public boolean esArbolBinarioOrdenado(String estructura) {
    return estructura.equals("ArbolBinarioOrdenado");
  }

  /**
   * esArbolRojinegro. Determina si la Estructura de Datos es una Arbol Rojinegro.
   * @param estructura La Estructura de Datos.
   */
  public boolean esArbolRojinegro(String estructura) {
    return estructura.equals("ArbolRojinegro");
  }

  /**
   * esArbolAVL. Determina si la Estructura de Datos es una Arbol AVL.
   * @param estructura La Estructura de Datos.
   */
  public boolean esArbolAVL(String estructura) {
    return estructura.equals("ArbolAVL");
  }

  /**
   * esGrafica. Determina si la Estructura de Datos es una Grafica.
   * @param estructura La Estructura de Datos.
   */
  public boolean esGrafica(String estructura) {
    return estructura.equals("Grafica");
  }

  /**
   * esMonticuloMinimo. Determina si la Estructura de Datos es una Monticulo Minimo.
   * @param estructura La Estructura de Datos.
   */
  public boolean esMonticuloMinimo(String estructura) {
    return estructura.equals("MonticuloMinimo");
  }

  /**
   * estructuraValida. Determina sí la Estructura de Datos es válida.
   */
  public boolean estructuraValida() {
    return (
      esLista(estructura) ||
      esPila(estructura) ||
      esCola(estructura) ||
      esArbolBinarioCompleto(estructura) ||
      esArbolBinarioOrdenado(estructura) ||
      esArbolRojinegro(estructura) ||
      esArbolAVL(estructura) ||
      esGrafica(estructura) ||
      esMonticuloMinimo(estructura)
    );
  }
}
