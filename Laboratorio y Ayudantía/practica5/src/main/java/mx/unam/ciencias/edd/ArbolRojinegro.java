package mx.unam.ciencias.edd;

/**
 * Clase para árboles rojinegros. Un árbol rojinegro cumple las siguientes
 * propiedades:
 *
 * <ol>
 *  <li>Todos los vértices son NEGROS o ROJOS.</li>
 *  <li>La raíz es NEGRA.</li>
 *  <li>Todas las hojas (<code>null</code>) son NEGRAS (al igual que la raíz).</li>
 *  <li>Un vértice ROJO siempre tiene dos hijos NEGROS.</li>
 *  <li>Todo camino de un vértice a alguna de sus hojas descendientes tiene el
 *      mismo número de vértices NEGROS.</li>
 * </ol>
 *
 * Los árboles rojinegros se autobalancean.
 */
public class ArbolRojinegro<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeRojinegro extends Vertice {

        /** El color del vértice. */
        public Color color;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeRojinegro(T elemento) {
            // Aquí va su código.
            super(elemento);
            color = Color.NINGUNO;
        }

        /**
         * Regresa una representación en cadena del vértice rojinegro.
         * @return una representación en cadena del vértice rojinegro.
         */
        @Override public String toString() {
            // Aquí va su código.
            return String.format("%s{%s}", color == Color.ROJO ? "R" : "N", elemento.toString());
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeRojinegro}, su elemento es igual al elemento de
         *         éste vértice, los descendientes de ambos son recursivamente
         *         iguales, y los colores son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked")
                VerticeRojinegro vertice = (VerticeRojinegro)objeto;
                return (color == vertice.color && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolRojinegro() { super(); }

    /**
     * Construye un árbol rojinegro a partir de una colección. El árbol
     * rojinegro tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        rojinegro.
     */
    public ArbolRojinegro(Coleccion<T> coleccion) {
        // Aquí va su código.
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link
     * VerticeRojinegro}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice rojinegro con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeRojinegro(elemento);
    }
    private VerticeRojinegro verticeRojinegro(VerticeArbolBinario<T> vertice) {
        VerticeRojinegro v = (VerticeRojinegro)vertice;
        return v;
    }

    /**
     * Regresa el color del vértice rojinegro.
     * @param vertice el vértice del que queremos el color.
     * @return el color del vértice rojinegro.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         VerticeRojinegro}.
     */
    public Color getColor(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        return verticeRojinegro(vertice).color;
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol recoloreando
     * vértices y girando el árbol como sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        super.agrega(elemento);
    	VerticeRojinegro nuevo = (VerticeRojinegro)ultimoAgregado;
        nuevo.color = Color.ROJO;
        balanceo(nuevo); // Aquí va su código.
    }
    
    private void balanceo(VerticeRojinegro vertice){
        VerticeRojinegro padre = getPadre(vertice);
        if (padre == null)
            vertice.color = Color.NEGRO;
        if (!esRojo(padre))            
            return;
        VerticeRojinegro abuelo = getPadre(padre);
        VerticeRojinegro tio = getHermano(padre);
        if (esRojo(tio)&&esRojo(padre)) {
            padre.color = Color.NEGRO;
            tio.color = Color.NEGRO;
            abuelo.color = Color.ROJO;
            balanceo(abuelo);
            return;
        }
        if (esIzq(padre) && !esIzq(vertice)){
            super.giraIzquierda(padre);
            padre = vertice;
            abuelo = getPadre(padre);
            vertice = (VerticeRojinegro)padre.izquierdo;
        } else if (!esIzq(padre) && esIzq(vertice)){
            super.giraDerecha(padre);
            padre = vertice;
            abuelo = getPadre(padre);
            vertice = (VerticeRojinegro)padre.derecho;
        }
        padre.color = Color.NEGRO;  
        abuelo.color = Color.ROJO;
        if (esIzq(vertice))
            super.giraDerecha(abuelo);
        else
            super.giraIzquierda(abuelo);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y recolorea y gira el árbol como sea necesario para
     * rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        Vertice quita = vertice(busca(elemento));
        if (quita != null) {
            elementos--;
            if (quita.izquierdo != null && quita.derecho != null)
                quita = intercambiaEliminable(quita);
            elimina(quita);
        }
    }
    private void elimina(Vertice vertice) {
        VerticeRojinegro fantasma = null;
        if (vertice.izquierdo == null && vertice.derecho == null)
            fantasma = agregaFantasma(vertice);
        VerticeRojinegro hijo = (vertice.izquierdo != null) ? 
            (VerticeRojinegro)vertice.izquierdo : 
                    (VerticeRojinegro)vertice.derecho;

        eliminaVertice(vertice);
        if (esRojo(hijo))
            hijo.color = Color.NEGRO;
        else if (!esRojo((VerticeRojinegro)vertice))
            rebalanceo(hijo);
        if (fantasma != null)
            eliminaVertice(fantasma);
    }
    private void rebalanceo(VerticeRojinegro vertice) {
        VerticeRojinegro padre = getPadre(vertice);
        if (padre == null)
            return;
        VerticeRojinegro hermano = getHermano(vertice);
        if (esRojo(hermano)) {
            padre.color = Color.ROJO;
            hermano.color = Color.NEGRO;
        if (esIzq(vertice))
            super.giraIzquierda(padre);
        else
            super.giraDerecha(padre);
            hermano = getHermano(vertice);
        }
            
        if (!esRojo(hermano) && !esRojo(hermano.izquierdo) && !esRojo(hermano.derecho)) {
            if (!esRojo(padre)) {
                hermano.color = Color.ROJO;
                rebalanceo(padre);
                return;
            } else {
                hermano.color = Color.ROJO;
                padre.color = Color.NEGRO;
                return;
            }
        }
        
        if (esIzq(vertice)){
            if (esRojo(hermano.izquierdo)) {
                hermano.color = Color.ROJO;
                ((VerticeRojinegro)hermano.izquierdo).color = Color.NEGRO;
                super.giraDerecha(hermano);
            }
        } else {
            if (esRojo(hermano.derecho)) {
                hermano.color = Color.ROJO;
                ((VerticeRojinegro)hermano.derecho).color = Color.NEGRO;
                super.giraIzquierda(hermano);
            }
        }

        hermano = getHermano(vertice);
        hermano.color = getColor(padre);
        padre.color = Color.NEGRO;
        if (esIzq(vertice)){
            if (hermano.derecho != null)
                ((VerticeRojinegro)hermano.derecho).color = Color.NEGRO;
                super.giraIzquierda(padre);
        } else {
            if (hermano.izquierdo != null)
                ((VerticeRojinegro)hermano.izquierdo).color = Color.NEGRO;
                super.giraDerecha(padre);
        }
    }

    private boolean esIzq(VerticeRojinegro vertice) {
        return vertice.padre != null && vertice.padre.izquierdo == vertice;
    }
        
    private boolean esRojo(Vertice vertice) {
        return esRojo((VerticeRojinegro)vertice);
    }

    private boolean esRojo(VerticeRojinegro vertice) {
        return vertice != null && vertice.color == Color.ROJO;
    }

    private VerticeRojinegro getPadre(VerticeRojinegro vertice) {
        return (VerticeRojinegro)vertice.padre;
    }

    private VerticeRojinegro getHermano(VerticeRojinegro vertice) {
        return esIzq(vertice)? 
                (VerticeRojinegro)vertice.padre.derecho: (vertice.padre != null)? 
                    (VerticeRojinegro)vertice.padre.izquierdo: null;
    }
    
    private VerticeRojinegro agregaFantasma(Vertice vertice) {
        VerticeRojinegro fantasma = (VerticeRojinegro)nuevoVertice(null);
        vertice.izquierdo = fantasma;
        fantasma.padre = vertice;
        fantasma.color = Color.NEGRO;
        return fantasma;
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la izquierda por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no "  +
                                                "pueden girar a la izquierda " +
                                                "por el usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles
     * rojinegros no pueden ser girados a la derecha por los usuarios de la
     * clase, porque se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles rojinegros no " +
                                                "pueden girar a la derecha " +
                                                "por el usuario.");
    }
}
