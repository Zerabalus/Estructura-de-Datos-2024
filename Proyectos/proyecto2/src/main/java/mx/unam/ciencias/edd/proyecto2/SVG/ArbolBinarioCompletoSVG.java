package mx.unam.ciencias.edd.proyecto2.SVG;

import mx.unam.ciencias.edd.*;

/**
 * Clase para representar Árboles Binarios Completos.
 */
public class ArbolBinarioCompletoSVG extends ArbolBinarioSVG {

    private ArbolBinarioCompleto<Integer> arbolBinarioCompleto;
    /** Vertice que nos servirá para recorrer el árbol binario completo. */
    private VerticeArbolBinario vertice;

    /**
     * Constructor
     * @param elementos La lista de elementos del Árbol Binario Completo.
     */
    public ArbolBinarioCompletoSVG(Lista<Integer> elementos) {
        this.arbolBinarioCompleto = new ArbolBinarioCompleto<Integer>(elementos);
    }

    /**
     * arbolBinarioCompletoenSVG. Regresa una representación en SVG del ABO.
     * @return La representación en SVG de ABO.
     */   
    public String arbolBinarioCompletoenSVG() {
        int ancho = arbolBinarioCompleto.getElementos() * 80;
        int altura = arbolBinarioCompleto.altura() * 80;
        String arbolCompleto = "";
        arbolCompleto += setLongitudArbol(ancho + 10, altura);
        vertice = arbolBinarioCompleto.raiz();
        graficaArbolBinario(vertice, 0, 0, ancho / 2);
        arbolCompleto += arbolBinarioSVG();
        arbolCompleto += finaliza();
        return arbolCompleto;
    }


}