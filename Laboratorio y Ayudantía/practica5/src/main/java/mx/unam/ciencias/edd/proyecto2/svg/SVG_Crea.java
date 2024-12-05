package mx.unam.ciencias.edd.proyecto2.svg;

import mx.unam.ciencias.edd.proyecto2.EstructuraDeDatos;

/**
 * <p>Clase que tiene solamente constantes y métodos estáticos necesarios para generar los SVG.</p>
 * <p>Dentro de este existen metodos y constantes estaticas por lo que no es necesario instanciar la clase.</p>
 *
 */
class SVG_Crea {

    static final String XML_PROLOG = "<?xml version='1.0' encoding='UTF-8' ?>\n";

    static final String ABRE = "<g>\n";
    static final String CIERRA = "</g>\n";
    static final String ABRE_DEF = "<defs>\n";
    static final String CIERRA_DEF = "</defs>\n";

    static final String ID_DEF_FLECHA = "flecha";

    static final int TAMAÑO_FLECHA = 21;
    static final String TEXTO_FLECHA_DERECHA_IZQUIERDA = "↔";
    static final String TEXTO_FLECHA_DERECHA = "→";

    static final int FONT_BALANCE_SIZE_TEXT = 12;

    static final int STOKE_WIDTH = 2;
    static final int STOKE_LINE_WIDTH = 2;

    static final String COLOR_NEGRO = "black";


    static String svgAlturaAnchura(double altura, double anchura) {
        return String.format("<svg height='%f' width='%f'>\n", altura, anchura);
    }

    static String cierraSVG() {
        return "</svg>\n";
    }

    /**
     * NOTA: Falta definir más formatos como la anchura, etc.
     */
    static String dibujaFlecha(String flecha) {
        return String.format("<text x='%d' y='%d' fill='%s' font-family='%s' font-size='%d' " +
                        "text-anchor='%s'>%s</text>\n",
                0,
                0,
                COLOR_NEGRO,
                TAMAÑO_FLECHA,
                flecha);
    }

    static String dibujaRecuadro(int x, int y, int altura, int anchura, String texto, EstructuraDeDatos datos) {
        final String LINEA = String.format("<rect " +
                        "x='%d' y='%d' height='%d' width='%d' fill='%s' stroke='%s' stroke-width='%d'/>\n",
                x, y,
                altura,
                anchura,
        /*Aquí le ponemos el formato */);

        final String TEXTO = String.format("<text " +
                        "x='%d' y='%d' fill='%s' font-family='%s' font-size='%d' text-anchor='%s'>%s</text>\n",
                datos != EstructuraDeDatos.PILA ? x + 30 : 40,
                datos != EstructuraDeDatos.PILA ? 35 : y + 25,
                /** Damos formato */);

        return LINEA + TEXTO;
    }
    static String abrimosId(String id) {
        return String.format("<g id='%s'>\n", id);
    }


    static class Defs {
        static String creaDef(String id, String inside) {
            return ABRE_DEF +
                    abrimosId(id) +
                    inside +
                    CIERRA +
                    CIERRA_DEF;
        }

        static String creaEtiqueta(String id, int x, int y) {
            return String.format("<use xlink:href='#%s' x='%d' y='%d' />\n", id, x, y);
        }
    }

}