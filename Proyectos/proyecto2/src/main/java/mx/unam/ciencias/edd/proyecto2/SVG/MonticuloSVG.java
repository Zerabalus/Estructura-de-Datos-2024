package mx.unam.ciencias.edd.proyecto2.SVG;

import mx.unam.ciencias.edd.*;

/**
 * Clase para representar Árboles Binarios Completos.
 */
public class MonticuloSVG extends ArbolBinarioSVG {
    
    /** El Montículo Mínimo que representaremos */
    private ArbolBinarioCompleto<Integer> monticuloMinimo;
    /** Vertice que nos servirá para recorrer el árbol binario completo. */
    private VerticeArbolBinario<Integer> vertice;

    /**
     * Constructor
     * @param elementos La lista de elementos del Montículo Mínimo.
     */
    public MonticuloSVG(Lista<Integer> elementos) {
        elementos = MonticuloMinimo.heapSort(elementos);
        monticuloMinimo = new ArbolBinarioCompleto<Integer>(elementos);
    }


    /**
     * monticuloMinimoenSVG. Regresa una representación en SVG de Árbol Binario Completo.
     * @return La representación en SVG de Árbol Binario Completo.
     */
    public String monticuloMinimoEnSVG() {
        int ancho = monticuloMinimo.getElementos() * 80;
        int altura = monticuloMinimo.altura() * 80;
        String monticulo = "";
        monticulo += setLongitudArbol(ancho + 10, altura);
        vertice = monticuloMinimo.raiz();
        dibujaArbolBinario(vertice, 0, 0, ancho / 2);
        monticulo += arbolBinarioSVG();
        monticulo += cierre();
        return monticulo;
    }
}