package mx.unam.ciencias.edd.proyecto2.SVG;

import mx.unam.ciencias.edd.*;

/**
 * Clase de Árboles AVL en SVG.
 */
public class ArbolAVLSVG extends ArbolBinarioSVG {

    private ArbolAVL<Integer> arbolAVL;
    /** Vertice para recorrer el árbol AVL. */
    private VerticeArbolBinario vertice;
    /** El árbol AVL en SVG */
    private static String arbol = "";

    /**
     * Constructor
     * 
     * @param elementos La lista de elementos del Árbol AVL.
     */
    public ArbolAVLSVG(Lista<Integer> elementos) {
        arbolAVL = new ArbolAVL<Integer>(elementos);
    }

    /**
     * balanceVerticeAVL. Regresa el balance del vértice.
     * 
     * @param vertice El vértice del que se va a obtener el balance.
     * @param x       el entero desde donde vamos a empezar a graficar el balance
     *                del vértice.
     * @param y       el entero desde donde vamos a empezar a graficar el balance
     *                del vértice.
     * @return El balance del vértice.
     */
    public String balanceVerticeAVL(VerticeArbolBinario vertice, int x, int y) {
        if (vertice.get().toString().length() == 1) {
            x -= 5;
        } else if (vertice.get().toString().length() == 2) {
            x -= 9;
        } else {
            x -= 14;
        }
        String balanceVertice = String.format("<text x=\"%d\" y=\"%d\" font-family=\"Roboto-Regular\""
                + " font-size=\"18\" fill=\"blue\"" +
                ">%s</text>\n", x, y + 12, vertice.get().toString());
        int indice = 0;
        if (vertice.get().toString().length() <= 2) {
            indice = 2;
        } else if (vertice.get().toString().length() >= 3) {
            indice = 3;
        }
        balanceVertice += String.format("<text x=\"%d\" y=\"%d\" font-family=\"Roboto-Regular\""
                + " font-size=\"12\" fill=\"black\"" +
                ">{%s}</text>\n", x + 35, y, vertice.toString().substring(indice, vertice.toString().length()));
        return balanceVertice;
    }

    /**
     * graficaArbolAVL. Grafica el Árbol AVL.
     */
    public void graficaArbolAVL(VerticeArbolBinario vertice, int x, int y, int z) {
        z += 10;
        x += 10;
        arbol += verticeArbol(z, y + 25);
        arbol += balanceVerticeAVL(vertice, z, y + 20);
        if (vertice.hayIzquierdo()) {
            int izquierdo = (z - x) / 2;
            arbol += aristaAb(z - 25, y + 20, z - izquierdo, y + 55);
            graficaArbolAVL(vertice.izquierdo(), x, y + 40, z - izquierdo);
        }
        if (vertice.hayDerecho()) {
            int derecho = (z - x) / 2;
            arbol += aristaAb(z + 25, y + 20, z + derecho, y + 50);
            graficaArbolAVL(vertice.derecho(), z, y + 40, z + derecho);
        }
    }

    /**
     * arbolAVLenSVG. Regresa una representación en SVG de Árbol AVL.
     */
    public String arbolAVLenSVG() {
        int ancho = arbolAVL.getElementos() * 85;
        int altura = arbolAVL.altura() * 80;
        String avl = "";
        avl += setLongitudArbol(ancho + 18, altura);
        vertice = arbolAVL.raiz();
        graficaArbolAVL(vertice, 0, 0, ancho / 2);
        avl += arbol;
        avl += finaliza();
        return avl;
    }

}