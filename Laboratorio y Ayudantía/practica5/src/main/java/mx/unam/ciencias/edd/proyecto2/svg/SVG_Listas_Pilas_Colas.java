package mx.unam.ciencias.edd.proyecto2.svg;

import mx.unam.ciencias.edd.Lista;
import mx.unam.ciencias.edd.proyecto2.EstructurasDeDatos;

/**
 * <p>Clase que implementa {@link SVG_Graficable} la cual dada una lista hace las operaciones necesarias para
 * que el en método {@link SVG_Graficable#drawSVG} devuelva un código SVG de una lista, pila o cola.</p>
 * 
 * NOTA: El paquete 'import mx.unam.ciencias.edd.proyecto2.EstructurasDeDatos;'
 * EstructurasDeDatos es una clase: EstructuraDeDatos.java en donde se implemeta una ENUMERACION
 * para poder graficar cada una de las estructuras de datos, pueden o NO implementarla, es cosa de cada quién
 * y de cómo está estructurado su código.
 */


/**
 * Aquí SVG_Grafica es una INTERFAZ, la vamos a implemetar para poder
 * dibujar las: listas, pilas y colas.
 */
public class SVG_Listas_Pilas_Colas<T> implements SVG_Grafica {
    private Lista<T> elements;
    private EstructurasDeDatos datos;
    /**
     * Constructor de mi clase
     * @param elements los elementos de la lista
     * @param datos la enumeración de los distintos tipos de datos.
     */
    SVG_Listas_Pilas_Colas(Lista<T> elements, EstructuraDeDatos datos) {
        this.elements = elements;
        this.datos = datos;
    }

    /**
     * Dibujamos las estructuras.
     * @return la estructura.
     */
    @Override public String dibujaSVG() {
        int i = 0;
        /** StringBuilder se utiliza para construir cadenas de manera más eficiente que la concatenación de cadenas utilizando el operador +. */
        StringBuilder builder = new StringBuilder();
        /** NOTA: SVG_Crea es una clase en donde van a implementar las constantes y
         * métodos necesarios para crear los svg.
         * XML_P es el xml, el utf-8 que se ocupa.
         */
        builder.append(SVG_Crea.XML_PROLOG);
        if (datos != EstructuraDeDatos.PILA) {
            /**
             * Aquí: TEXTO_FLECHA_DERECHA_IZQUIERDA es una variable final definida en la clase SVG_Crea.java su implementación sería:
             *          static final String TEXT_LEFT_RIGHT_ARROW = "↔";
             * De igual forma, TEXTO_FLECHA_DERECHA es una variable final en la clase SVG_Crea.java
             *          static final String TEXTO_FLECHA_DERECHA = "→";
             */
            String flecha = (datos == EstructuraDeDatos.LISTA) ? SVG_Crea.TEXTO_FLECHA_DERECHA_IZQUIERDA : SVG_Crea.TEXTO_FLECHA_DERECHA;
            /** Mandamos a llamar al método svgAlturaAnchura() para darle el formato correspondiente
             * Su implementación sería la siguiente:
             *         public static String svgAlturaAnchura(double altura, double anchura) {
             *              return String.format("<svg height='%f' width='%f'>\n", altura, anchura);
             *         }
            */
            builder.append(SVG_Crea.svgAlturaAnchura(60, (10 + 80 * elements.getElementos() - 10)));
            /** Definicion es una clase interna y tiene cómo método creaDef() que se encarga de 
             * crear la definición de lo que estamos dibujando.
             */
            builder.append(SVG_Crea.Definicion.creaDef(SVG_Crea.ID_DEF_FLECHA, SVG_Crea.dibujaFlecha(flecha)));
            /** "Abrimos" el dibujo */
            builder.append(SVG_Crea.ABRE);
            /** Dibujamos los recuadros de las listas: */
            for (T x : elements) {
                builder.append(SVG_Crea.dibujaRecuadro(10 + (80 * i), 10, 40, 60, x.toString(), datos));
                if (i != elements.getElementos() - 1) {
                    builder.append(SVG_Crea.Definicion.creaEtiqueta(SVG_Crea.ID_DEF_FLECHA, 80 + i * 80, 35));
                }
                i++;
            }
        } else {
            /** Hacemos caso contrario
             * Como hint, pueden usar reversa();
             */
        }

        builder.append(SVG_Crea.CIERRA);
        builder.append(SVG_Crea.cierraSVG());
        return builder.toString();
    }
}