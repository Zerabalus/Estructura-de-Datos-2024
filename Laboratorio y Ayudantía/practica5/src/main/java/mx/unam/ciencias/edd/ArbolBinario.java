package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * <p>Clase abstracta para árboles binarios genéricos.</p>
 *
 * <p>La clase proporciona las operaciones básicas para árboles binarios, pero
 * deja la implementación de varias en manos de las subclases concretas.</p>
 */
public abstract class ArbolBinario<T> implements Coleccion<T> {

    /**
     * Clase interna protegida para vértices.
     */
    protected class Vertice implements VerticeArbolBinario<T> {

        /** El elemento del vértice. */
        protected T elemento;
        /** El padre del vértice. */
        protected Vertice padre;
        /** El izquierdo del vértice. */
        protected Vertice izquierdo;
        /** El derecho del vértice. */
        protected Vertice derecho;

        /**
         * Constructor único que recibe un elemento.
         * @param elemento el elemento del vértice.
         */
        protected Vertice(T elemento) {
            this.elemento = elemento;
            // Aquí va su código.
        }

        /**
         * Nos dice si el vértice tiene un padre.
         * @return <code>true</code> si el vértice tiene padre,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayPadre() {
            return padre != null;
            // Aquí va su código.
        }

        /**
         * Nos dice si el vértice tiene un izquierdo.
         * @return <code>true</code> si el vértice tiene izquierdo,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayIzquierdo() {
            return izquierdo != null;
            // Aquí va su código.
        }

        /**
         * Nos dice si el vértice tiene un derecho.
         * @return <code>true</code> si el vértice tiene derecho,
         *         <code>false</code> en otro caso.
         */
        @Override public boolean hayDerecho() {
            return derecho != null;
            // Aquí va su código.
        }

        /**
         * Regresa el padre del vértice.
         * @return el padre del vértice.
         * @throws NoSuchElementException si el vértice no tiene padre.
         */
        @Override public VerticeArbolBinario<T> padre() {
            if (!hayPadre())
                throw new NoSuchElementException();
            return padre;
            // Aquí va su código.
        }

        /**
         * Regresa el izquierdo del vértice.
         * @return el izquierdo del vértice.
         * @throws NoSuchElementException si el vértice no tiene izquierdo.
         */
        @Override public VerticeArbolBinario<T> izquierdo() {
            if (!hayIzquierdo())
                throw new NoSuchElementException();
            return izquierdo;
            // Aquí va su código.
        }

        /**
         * Regresa el derecho del vértice.
         * @return el derecho del vértice.
         * @throws NoSuchElementException si el vértice no tiene derecho.
         */
        @Override public VerticeArbolBinario<T> derecho() {
            if (!hayDerecho())
                throw new NoSuchElementException();
            return derecho;
            // Aquí va su código.
        }

        /**
         * Regresa la altura del vértice.
         * @return la altura del vértice.
         */
        @Override public int altura() {
            if (izquierdo == null && derecho == null)
                return 0;
            else if (izquierdo == null && derecho != null)
                return 1 + derecho.altura();
            else if (izquierdo != null && derecho == null)
                return 1 + izquierdo.altura();
            return 1 + Math.max(izquierdo.altura(), derecho.altura());
            // Aquí va su código.
        }

        /**
         * Regresa la profundidad del vértice.
         * @return la profundidad del vértice.
         */
        @Override public int profundidad() {
            if (padre == null)
                return 0;
            return 1 + padre.profundidad();
            // Aquí va su código.
        }

        /**
         * Regresa el elemento al que apunta el vértice.
         * @return el elemento al que apunta el vértice.
         */
        @Override public T get() {
            return elemento;
            // Aquí va su código.
        }

        /**
         * Compara el vértice con otro objeto. La comparación es
         * <em>recursiva</em>. Las clases que extiendan {@link Vertice} deben
         * sobrecargar el método {@link Vertice#equals}.
         * @param objeto el objeto con el cual se comparará el vértice.
         * @return <code>true</code> si el objeto es instancia de la clase
         *         {@link Vertice}, su elemento es igual al elemento de éste
         *         vértice, y los descendientes de ambos son recursivamente
         *         iguales; <code>false</code> en otro caso.
         */
        @Override public boolean equals(Object objeto) {
            if (objeto == null || getClass() != objeto.getClass())
                return false;
            @SuppressWarnings("unchecked") Vertice vertice = (Vertice)objeto;
            if (!elemento.equals(vertice.elemento))
                return false;
            if (!hayIzquierdo() && !vertice.hayIzquierdo() && !hayDerecho() && !vertice.hayDerecho())
                return true;
            if (hayDerecho() && hayIzquierdo() && vertice.hayDerecho() && vertice.hayIzquierdo())
                return izquierdo.equals(vertice.izquierdo) && derecho.equals(vertice.derecho);
            if ((!hayIzquierdo() && vertice.hayIzquierdo()) || (hayIzquierdo() && !vertice.hayIzquierdo()))
                return false;
            if ((!hayDerecho() && vertice.hayDerecho()) || (hayDerecho() && !vertice.hayDerecho()))
                return false;
            return true;
            // Aquí va su código.
        }

        /**
         * Regresa una representación en cadena del vértice.
         * @return una representación en cadena del vértice.
         */
        @Override public String toString() {
            return elemento.toString();
            // Aquí va su código.
        }
    }

    /** La raíz del árbol. */
    protected Vertice raiz;
    /** El número de elementos */
    protected int elementos;

    /**
     * Constructor sin parámetros. Tenemos que definirlo para no perderlo.
     */
    public ArbolBinario() {}

    /**
     * Construye un árbol binario a partir de una colección. El árbol binario
     * tendrá los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario.
     */
    public ArbolBinario(Coleccion<T> coleccion) {
        for (T t: coleccion)
            agrega(t);
        // Aquí va su código.
    }

    /**
     * Construye un nuevo vértice, usando una instancia de {@link Vertice}. Para
     * crear vértices se debe utilizar este método en lugar del operador
     * <code>new</code>, para que las clases herederas de ésta puedan
     * sobrecargarlo y permitir que cada estructura de árbol binario utilice
     * distintos tipos de vértices.
     * @param elemento el elemento dentro del vértice.
     * @return un nuevo vértice con el elemento recibido dentro del mismo.
     */
    protected Vertice nuevoVertice(T elemento) {
        return new Vertice(elemento);
        // Aquí va su código.
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol es la altura de su
     * raíz.
     * @return la altura del árbol.
     */
    public int altura() {
        if (esVacia())
            return -1;
        return raiz.altura();
        // Aquí va su código.
    }

    /**
     * Regresa el número de elementos que se han agregado al árbol.
     * @return el número de elementos en el árbol.
     */
    @Override public int getElementos() {
        return elementos;
        // Aquí va su código.
    }

    /**
     * Nos dice si un elemento está en el árbol binario.
     * @param elemento el elemento que queremos comprobar si está en el árbol.
     * @return <code>true</code> si el elemento está en el árbol;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return busca(elemento) != null;
        // Aquí va su código.
    }

    /**
     * Busca el vértice de un elemento en el árbol. Si no lo encuentra regresa
     * <code>null</code>.
     * @param elemento el elemento para buscar el vértice.
     * @return un vértice que contiene el elemento buscado si lo encuentra;
     *         <code>null</code> en otro caso.
     */
    public VerticeArbolBinario<T> busca(T elemento) {
        if (esVacia())
            return null;
        if (raiz.elemento.equals(elemento))
            return raiz();
        return buscaAux(raiz.izquierdo, raiz.derecho, elemento);
        // Aquí va su código.
    }

    private VerticeArbolBinario<T> buscaAux(Vertice i, Vertice d, T elemento){
        if (i == null && d == null)
            return null;
        if (i == null && d != null){
            if (d.elemento.equals(elemento))
                return d;
            return buscaAux(d.izquierdo, d.derecho, elemento);
        }
        if (i != null && d == null){
            if (i.elemento.equals(elemento))
                return i;
            else
                return buscaAux(i.izquierdo, i.derecho, elemento);
        }
        if (i.elemento.equals(elemento))
            return i;
        if (d.elemento.equals(elemento))
            return d;
        return buscaAux(vertice(buscaAux(i.izquierdo, i.derecho, elemento)), vertice(buscaAux(d.izquierdo, d.derecho, elemento)), elemento);
        
    }

    /**
     * Regresa el vértice que contiene la raíz del árbol.
     * @return el vértice que contiene la raíz del árbol.
     * @throws NoSuchElementException si el árbol es vacío.
     */
    public VerticeArbolBinario<T> raiz() {
        if (esVacia())
            throw new NoSuchElementException();
        return raiz;
        // Aquí va su código.
    }

    /**
     * Nos dice si el árbol es vacío.
     * @return <code>true</code> si el árbol es vacío, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return elementos == 0;
        // Aquí va su código.
    }

    /**
     * Limpia el árbol de elementos, dejándolo vacío.
     */
    @Override public void limpia() {
        raiz = null;
        elementos = 0;
        // Aquí va su código.
    }

    /**
     * Compara el árbol con un objeto.
     * @param objeto el objeto con el que queremos comparar el árbol.
     * @return <code>true</code> si el objeto recibido es un árbol binario y los
     *         árboles son iguales; <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked")
            ArbolBinario<T> arbol = (ArbolBinario<T>)objeto;
        if (esVacia() && arbol.esVacia())
            return true;
        if (elementos != arbol.elementos)
            return false;
        if (!esVacia())
            return raiz.equals(arbol.raiz);
        return false;
        // Aquí va su código.
    }

    /**
     * Regresa una representación en cadena del árbol.
     * @return una representación en cadena del árbol.
     */
    @Override public String toString() {
        if(this.raiz == null)
            return "";
        int a = altura()+1;
        boolean[] boo = new boolean[a];
        for(int i = 0; i < a; i++)
            boo[i] = false;
        return toString(this.raiz, 0, boo);
    }

    private String dibujaEspacios(int l,boolean[] a) {
        String s = "";
        for(int i = 0; i <= l-1; i++) {
            if(a[i] == true)
                s = s + "│  ";
            else
                s = s + "   ";
        }
        return s;
    }

    private String toString(Vertice v, int l, boolean[] a) {
        String s;
        s = v.toString() + "\n";
        a[l] = true;
        if(v.izquierdo != null && v.derecho != null) {
            s = s + dibujaEspacios(l,a);
            s = s + "├─›";
            s = s + toString(v.izquierdo, l+1, a);
            s = s + dibujaEspacios(l,a);
            s = s + "└─»";
            a[l] = false;
            s = s + toString(v.derecho,l+1,a);
        }else if(v.izquierdo != null){
            s = s + dibujaEspacios(l,a);
            s = s + "└─›";
            a[l] = false;
            s = s + toString(v.izquierdo, l+1,a);
        }else if(v.derecho != null){
            s = s + dibujaEspacios(l,a);
            s = s + "└─»";
            a[l] = false;
            s = s + toString(v.derecho,l+1,a);
        } 
        return s;
    }

    /**
     * Convierte el vértice (visto como instancia de {@link
     * VerticeArbolBinario}) en vértice (visto como instancia de {@link
     * Vertice}). Método auxiliar para hacer esta audición en un único lugar.
     * @param vertice el vértice de árbol binario que queremos como vértice.
     * @return el vértice recibido visto como vértice.
     * @throws ClassCastException si el vértice no es instancia de {@link
     *         Vertice}.
     */
    protected Vertice vertice(VerticeArbolBinario<T> vertice) {
        // Aquí va su código.
        return (Vertice)vertice;
    }
}
