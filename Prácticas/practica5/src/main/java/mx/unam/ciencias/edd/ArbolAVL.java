package mx.unam.ciencias.edd;

/**
 * <p>Clase para árboles AVL.</p>
 *
 * <p>Un árbol AVL cumple que para cada uno de sus vértices, la diferencia entre
 * la áltura de sus subárboles izquierdo y derecho está entre -1 y 1.</p>
 */
public class ArbolAVL<T extends Comparable<T>>
    extends ArbolBinarioOrdenado<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class VerticeAVL extends Vertice {

        /** La altura del vértice. */
        public int altura;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        public VerticeAVL(T elemento) {
            // Aquí va su código.
            super(elemento);
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            // Aquí va su código.
            return altura;
        }

        /**
         * Regresa una representación en cadena del vértice AVL.
         * @return una representación en cadena del vértice AVL.
         */
        @Override public String toString() {
            // Aquí va su código.
            /* 16.2 del libro: Además de la representación en cadena del elemento en el vértice,
            también le concatenaremos la altura del vértice, una diagonal y el
            balance de vértice, que será la diferencia de las alturas de sus hijos. */
            return elemento.toString() + " "  + altura+"/"+getBalance(this);

        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link VerticeAVL}, su elemento es igual al elemento de éste
         *         vértice, los descendientes de ambos son recursivamente
         *         iguales, y las alturas son iguales; <code>false</code> en
         *         otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") VerticeAVL vertice = (VerticeAVL)objeto;
            // Aquí va su código.
            return (altura == vertice.altura && super.equals(objeto));
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinarioOrdenado}.
     */
    public ArbolAVL() {
        // Aquí va su código.
        super();
    }

    /**
     * Construye un árbol AVL a partir de una colección. El árbol AVL tiene los
     * mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol AVL.
     */
    public ArbolAVL(Coleccion<T> coleccion) {
        // Aquí va su código.
        super(coleccion);
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link VerticeAVL}.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    @Override protected Vertice nuevoVertice(T elemento) {
        // Aquí va su código.
        return new VerticeAVL(elemento);
    }

    /**
     * Agrega un nuevo elemento al árbol. El método invoca al método {@link
     * ArbolBinarioOrdenado#agrega}, y después balancea el árbol girándolo como
     * sea necesario.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        super.agrega(elemento);
        VerticeAVL vertice = (VerticeAVL)ultimoAgregado.padre;
        rebalanceo(vertice);
    }

    /**
     * Elimina un elemento del árbol. El método elimina el vértice que contiene
     * el elemento, y gira el árbol como sea necesario para rebalancearlo.
     * @param elemento el elemento a eliminar del árbol.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        VerticeAVL elimina = (VerticeAVL)busca(elemento);
        Vertice padre;
        if(elimina == null)
            return;
        if(elimina.hayDerecho()&& elimina.hayIzquierdo()){
            Vertice intercambiar = intercambiaEliminable(elimina);
            padre = intercambiar.padre;
            eliminaVertice(intercambiar);
        }else{
            padre = elimina.padre;
            eliminaVertice(elimina);
        }
        elementos--;
        rebalanceo((VerticeAVL)padre);
    }

    private int max(int i, int j){
        if(i > j)
            return i;
        return j;
    }

    private void rebalanceo(VerticeAVL vertice){
        if(vertice == null)
            return;

        actualizarAltura(vertice);

        int balanceo = getBalance(vertice);

        if(balanceo == 2){
            VerticeAVL izquierdo = (VerticeAVL)vertice.izquierdo;
            if(getBalance(izquierdo) == -1){ 
                super.giraIzquierda(izquierdo);
                actualizarAltura(izquierdo);
            }
            super.giraDerecha(vertice);
            actualizarAltura(izquierdo);
            actualizarAltura(vertice);
        }

        if(balanceo == -2){
            VerticeAVL derecho = (VerticeAVL)vertice.derecho;
            if(getBalance(derecho) == 1){ 
                super.giraDerecha(derecho);
                actualizarAltura(derecho);
            }
            super.giraIzquierda(vertice);
            actualizarAltura(derecho);
            actualizarAltura(vertice);
        }

        rebalanceo((VerticeAVL)vertice.padre);
    }

    private void actualizarAltura(Vertice vertice) {
        if (vertice == null) {
            return;
        }

        ((VerticeAVL) vertice).altura = 1 + max(vertice.hayIzquierdo() ? ((VerticeAVL) vertice.izquierdo).altura : -1,
                vertice.hayDerecho() ? ((VerticeAVL) vertice.derecho).altura : -1);

    }

    private int getBalance(Vertice vertice) {
        int ai = vertice.hayIzquierdo() ? ((VerticeAVL) vertice.izquierdo).altura : -1;
        int ad = vertice.hayDerecho() ? ((VerticeAVL) vertice.derecho).altura : -1;

        return ai - ad; //definicion de balanceo de vértices
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la derecha por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraDerecha(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la izquierda por el " +
                                                "usuario.");
    }

    /**
     * Lanza la excepción {@link UnsupportedOperationException}: los árboles AVL
     * no pueden ser girados a la izquierda por los usuarios de la clase, porque
     * se desbalancean.
     * @param vertice el vértice sobre el que se quiere girar.
     * @throws UnsupportedOperationException siempre.
     */
    @Override public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        throw new UnsupportedOperationException("Los árboles AVL no  pueden " +
                                                "girar a la derecha por el " +
                                                "usuario.");
    }
}
