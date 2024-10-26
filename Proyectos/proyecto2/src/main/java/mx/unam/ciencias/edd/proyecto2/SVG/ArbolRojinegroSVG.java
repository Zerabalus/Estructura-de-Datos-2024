package mx.unam.ciencias.edd.proyecto2.SVG;

import mx.unam.ciencias.edd.*;

/**
 * Clase para representar Árboles Rojinegros.
 */
public class ArbolRojinegroSVG extends ArbolBinarioSVG {

    /** El árbol Rojinegro que representaremos */
    private ArbolRojinegro<Integer> arbolRojinegro;
    /** Vertice que nos servirá para recorrer el árbol Rojinegro. */
    private VerticeArbolBinario vertice;
    /** El árbol AVL en SVG */
    private static String arbolRN;

    /**
     * Contructor.
     * @param elementos La lista de elementos del Árbol Rojinegro.
     */
    public ArbolRojinegroSVG(Lista<Integer> elementos) {
        arbolRojinegro = new ArbolRojinegro<Integer>(elementos);
    }

    /**
     * dibujaverticeRojo. El vértice rojo del árbol Rojinegro.
     * @param cx La coordena del centro del vértice.
     * @param cy La coordena del centro del vértice
     * @return El vertice del árbol Rojinegro.
     */
    public String dibujaVerticeRojo(int cx, int cy) {
        String cadena = String.format("\n<circle cx=\'%s\' cy=\'%d\' r=\'25\' " +
        "stroke=\'firebrick\' stroke-width=\'2\' fill=\'firebrick\' />", cx, cy);
        return cadena;
    }

    /**
     * dibujaAristaRojinegro. Dibuja las aristas del árbol Rojinegro.
     * @param x1 Coordenada en x donde va a iniciar la línea.
     * @param y1 Coordenada en y donde va a iniciar la línea.
     * @param x2 Coordenada en x donde va a terminar la línea.
     * @param y2 Coordenada en y donde va a terminar la línea.
     * @return La arista del árbol Rojinegro en SVG.
     */
    public String dibujaAristaRojinegro(int x1, int y1, int x2, int y2) {
        String aristaRojinegro = String.format("\n<line x1=\'%d\' y1=\'%d\' x2=\'%d\' " +
        "y2=\'%d\' stroke=\'black\' stroke-width=\'2\' />", x1, y1, x2, y2);
        return aristaRojinegro;
    }

    /**
     * dibujaverticeNegro. El vértice negro del árbol Rojinegro.
     * @param cx La coordena del centro del vértice.
     * @param cy La coordena del centro del vértice
     * @return El vertice del árbol Rojinegro.
     */
    public String dibujaVerticeNegro(int cx, int cy) {
        String cadena = String.format("\n<circle cx=\'%s\' cy=\'%d\' r=\'25\' " +
        "stroke=\'black\' stroke-width=\'2\' fill=\'black\' />", cx, cy);
        return cadena;
    }

    /**
     * dibujaArbolRojinegro. Dibuja el Árbol Rojinegro.
     */
    public void dibujaArbolRojinegro(VerticeArbolBinario vertice, int x, int y, int z) {
        if (vertice.toString().substring(0,1).equals("R")) {
            arbolRN += dibujaVerticeRojo(z, y + 20);
        }
        if (vertice.toString().substring(0,1).equals("N")) {
            arbolRN += dibujaVerticeNegro(z, y + 20);
        }
        arbolRN += elementoArbolRojinegro(vertice, z, y + 30);
        if (vertice.hayIzquierdo()) {
            int izquierda = (z - x) / 2;
            arbolRN += dibujaAristaRojinegro(z - 25, y + 25, z - izquierda, y + 60);
            dibujaArbolRojinegro(vertice.izquierdo(), x, y + 45, z - izquierda);
        }
        if (vertice.hayDerecho()) {
            int derecha = (z - x) / 2;
            arbolRN += dibujaAristaRojinegro(z + 25, y + 25, z + derecha , y + 55);
            dibujaArbolRojinegro(vertice.derecho(), z, y + 45, z + derecha);
        }
    }

    /**
     * elementoArbolRojinegro. El elemento del árbol Rojinegro.
     * @param vertice El vértice donde está el elemento del árbol binario.
     * @param x el entero desde donde vamos a empezar a dibujar el elemento.
     * @param y el entero desde donde vamos a empezar a dibujar el elemento.
     * @return Representación del elemento del vértice en SVG.
     */
    public String elementoArbolRojinegro(VerticeArbolBinario vertice,
                                       int x,
                                       int y) {
        if (vertice.get().toString().length() == 1) {
            x -= 5;
        } else if (vertice.get().toString().length() == 2) {
            x -= 9;
        } else {
            x -= 14;
        }
        String elemento = String.format("\n<text x=\'%s\' y=\'%s\'" +
        " font-family=\'Roboto-Regular\' font-size=\'16\' fill=\'white\'>" +
        "%d</text>", x, y, vertice.get());
        return elemento;
    }

    /**
     * arbolRojinegroEnSVG. Regresa una representación en SVG de Árbol Rojinegro.
     * @return La representación en SVG de Árbol Rojinegro.
     */
    public String arbolRojinegroEnSVG() {
        int ancho = arbolRojinegro.getElementos() * 80;
        int altura = arbolRojinegro.altura() * 80;
        String arbol = "";
        arbol += setLongitudArbol(ancho + 15, altura);
        vertice = arbolRojinegro.raiz();
        dibujaArbolRojinegro(vertice, 0, 0, ancho / 2);
        arbol += arbolRN;
        arbol += cierre();
        return arbol;
    }


}