package mx.unam.ciencias.edd.proyecto2.SVG;

import mx.unam.ciencias.edd.*;

/**
 * Clase para representar Árboles Binarios Ordenados.
 */
public class ArbolBinarioOrdenadoSVG extends ArbolBinarioSVG {

    /** El árbol binario ordenado que representaremos */
    private ArbolBinarioOrdenado<Integer> arbolOrdenado;
    /** Vertice que nos servirá para recorrer el árbol binario ordenado. */   
    private VerticeArbolBinario vertice;

    /**
     * Constructor
     * @param elementos La lista de elementos del Árbol Binario Ordenado.
     */
    public ArbolBinarioOrdenadoSVG(Lista<Integer> elementos) {
        arbolOrdenado = new ArbolBinarioOrdenado<Integer>(elementos);
    }

    /**
     * arbolBinarioOrdenadoenSVG. Regresa una representación en SVG de Árbol Binario Ordenado.
     * @return La representación en SVG de Árbol Binario Ordenado.
     */
    public String arbolBinarioOrdenadoenSVG() {
        int ancho = arbolOrdenado.getElementos() * 100;
        int altura = arbolOrdenado.altura() * 80;
        String arbol = "";
        arbol += setLongitudArbol(ancho + 10, altura);
        vertice = arbolOrdenado.raiz();
        dibujaArbolBinario(vertice, 0 , 0 , ancho / 2 );
        arbol += arbolBinarioSVG();
        arbol += cierre();
        return arbol;
    }

}