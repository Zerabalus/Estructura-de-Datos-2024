package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios ordenados. Los árboles son genéricos, pero
 * acotados a la interfaz {@link Comparable}.</p>
 *
 * <p>Un árbol instancia de esta clase siempre cumple que:</p>
 * <ul>
 *   <li>Cualquier elemento en el árbol es mayor o igual que todos sus
 *       descendientes por la izquierda.</li>
 *   <li>Cualquier elemento en el árbol es menor o igual que todos sus
 *       descendientes por la derecha.</li>
 * </ul>
 */
public class ArbolBinarioOrdenado<T extends Comparable<T>>
    extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Pila para recorrer los vértices en DFS in-order. */
        private Pila<Vertice> pila;

        /* Inicializa al iterador. */
        private Iterador() {
            pila = new Pila<>();
            if (!esVacia())
                agregaRamaIzquierda(raiz);
            // Aquí va su código.
        }

        private void agregaRamaIzquierda(Vertice v){
            if (v == null)
                return;
            pila.mete(v);
            if (v.hayIzquierdo())
                agregaRamaIzquierda(v.izquierdo);
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !pila.esVacia();
            // Aquí va su código.
        }

        /* Regresa el siguiente elemento en orden DFS in-order. */
        @Override public T next() {
            if(!hasNext())
                throw new NoSuchElementException();
            Vertice v = pila.saca();
            T elem = v.elemento;
            if (v.hayDerecho())
                agregaRamaIzquierda(v.derecho);
            return elem;
            // Aquí va su código.
        }
    }

    /**
     * El vértice del último elemento agegado. Este vértice sólo se puede
     * garantizar que existe <em>inmediatamente</em> después de haber agregado
     * un elemento al árbol. Si cualquier operación distinta a agregar sobre el
     * árbol se ejecuta después de haber agregado un elemento, el estado de esta
     * variable es indefinido.
     */
    protected Vertice ultimoAgregado;

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioOrdenado() { super(); }

    /**
     * Construye un árbol binario ordenado a partir de una colección. El árbol
     * binario ordenado tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario ordenado.
     */
    public ArbolBinarioOrdenado(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un nuevo elemento al árbol. El árbol conserva su orden in-order.
     * @param elemento el elemento a agregar.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        Vertice n = nuevoVertice(elemento);
        if (esVacia())
            raiz = n;
        else {
            agregaRec(raiz, n);
        }
        ultimoAgregado = n;
        elementos++;

        // Aquí va su código.
    }

    private void agregaRec(Vertice v, Vertice nuevo){
        if (nuevo.elemento.compareTo(v.elemento) <= 0){
            if (!v.hayIzquierdo()){
                v.izquierdo = nuevo;
                nuevo.padre = v;
            } else 
                agregaRec(v.izquierdo, nuevo);
        } else {
            if (!v.hayDerecho()){
                v.derecho = nuevo;
                nuevo.padre = v;
            } else
                agregaRec(v.derecho, nuevo);
        }
    }

    private boolean esHoja(Vertice v){
        return !v.hayIzquierdo() && !v.hayDerecho();
    }

    private Vertice maxSubarbol(Vertice v){
        if (!v.hayDerecho())
            return v;
        return maxSubarbol(v.derecho);
    }

    /**
     * Elimina un elemento. Si el elemento no está en el árbol, no hace nada; si
     * está varias veces, elimina el primero que encuentre (in-order). El árbol
     * conserva su orden in-order.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Vertice quita = vertice(busca(elemento));
        if(quita != null){
            elementos--;
            if(quita.izquierdo != null && quita.derecho != null)
                quita = intercambiaEliminable(quita);
            eliminaVertice(quita);
        }
        // Aquí va su código.
    }

    // private void eliminaAux(Vertice v){
        //ESTO IBA ARRIBA:
        // Vertice v = vertice(busca(elemento));
        // //No está el elemento;
        // if (v == null)
        //     return;
        // eliminaAux(v);
        // elementos--;
        //HASTA AQUÍ
    //     //Eliminar una hoja
    //     if (esHoja(v)){
    //         if (v == raiz){
    //             raiz = null;
    //         }
    //         else if (v.padre.derecho == v){
    //             v.padre.derecho = null;
    //         } else 
    //             v.padre.izquierdo = null;
    //     }
    //     //Hijo único
    //     else if ((v.hayIzquierdo() && !v.hayDerecho()) || (v.hayDerecho() && !v.hayIzquierdo())){
    //         eliminaVertice(v);
    //     } 
    //     //Caso cuando hay ambos hijos
    //     else {
    //         Vertice u = maxSubarbol(v.izquierdo);
    //         v.elemento = u.elemento;
    //         eliminaAux(u);
    //     }
    // }

    /**
     * Intercambia el elemento de un vértice con dos hijos distintos de
     * <code>null</code> con el elemento de un descendiente que tenga a lo más
     * un hijo.
     * @param vertice un vértice con dos hijos distintos de <code>null</code>.
     * @return el vértice descendiente con el que vértice recibido se
     *         intercambió. El vértice regresado tiene a lo más un hijo distinto
     *         de <code>null</code>.
     */
    protected Vertice intercambiaEliminable(Vertice vertice) {
        Vertice max  = maximo(vertice.izquierdo);
        vertice.elemento = max.elemento;
        return max;
        // Aquí va su código.
    }
    private Vertice maximo(Vertice v){
        return (v.derecho == null) ?
            v : maximo(v.derecho);
    }

    /**
     * Elimina un vértice que a lo más tiene un hijo distinto de
     * <code>null</code> subiendo ese hijo (si existe).
     * @param vertice el vértice a eliminar; debe tener a lo más un hijo
     *                distinto de <code>null</code>.
     */
    protected void eliminaVertice(Vertice vertice) {
        Vertice t = (vertice.izquierdo != null)?
            vertice.izquierdo:
            vertice.derecho;
        if (vertice.padre != null) {
            if(esIzq(vertice))
                vertice.padre.izquierdo = t;
            else
                vertice.padre.derecho = t;
        } 
        else
            raiz = t;
        if (t != null)
            t.padre = vertice.padre;
        // if (vertice == raiz){
        //     if (vertice.hayIzquierdo()){
        //         vertice = vertice.izquierdo;
        //         vertice.padre = null;
        //         raiz = vertice;
        //     }
        //     else{
        //         vertice = vertice.derecho;
        //         vertice.padre = null;
        //         raiz = vertice;
        //     }
        //     return;
        // }
        // Vertice p = vertice.padre;
        // if (p.izquierdo == vertice){
        //     if (vertice.hayIzquierdo()){
        //         p.izquierdo = vertice.izquierdo;
        //         vertice.izquierdo.padre = p;
        //     } else {
        //         p.izquierdo = vertice.derecho;
        //         vertice.derecho.padre = p;
        //     }
        // } else {
        //     if (vertice.hayIzquierdo()){
        //         p.derecho = vertice.izquierdo;
        //         vertice.izquierdo.padre = p;
        //     } else {
        //         p.derecho = vertice.derecho;
        //         vertice.derecho.padre = p;
        //     }
        // }
        // Aquí va su código.
    }
    private boolean esIzq(Vertice vertice){
        return vertice.padre.izquierdo == vertice;
    }

    /**
     * Busca un elemento en el árbol recorriéndolo in-order. Si lo encuentra,
     * regresa el vértice que lo contiene; si no, regresa <code>null</code>.
     * @param elemento el elemento a buscar.
     * @return un vértice que contiene al elemento buscado si lo
     *         encuentra; <code>null</code> en otro caso.
     */
    @Override public VerticeArbolBinario<T> busca(T elemento) {
        if (!esVacia())
            return buscaAux(raiz, elemento);
        return null;
        // Aquí va su código.
    }

    private VerticeArbolBinario<T> buscaAux(Vertice v, T elemento){
        if (v == null)
            return null;
        if (v.elemento.compareTo(elemento) == 0)
            return v;
        else if (elemento.compareTo(v.elemento) < 0)
            return buscaAux(v.izquierdo, elemento);
        return buscaAux(v.derecho, elemento);
    }

    /**
     * Regresa el vértice que contiene el último elemento agregado al
     * árbol. Este método sólo se puede garantizar que funcione
     * <em>inmediatamente</em> después de haber invocado al método {@link
     * agrega}. Si cualquier operación distinta a agregar sobre el árbol se
     * ejecuta después de haber agregado un elemento, el comportamiento de este
     * método es indefinido.
     * @return el vértice que contiene el último elemento agregado al árbol, si
     *         el método es invocado inmediatamente después de agregar un
     *         elemento al árbol.
     */
    public VerticeArbolBinario<T> getUltimoVerticeAgregado() {
        return ultimoAgregado;
    }

    /**
     * Gira el árbol a la derecha sobre el vértice recibido. Si el vértice no
     * tiene hijo izquierdo, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraDerecha(VerticeArbolBinario<T> vertice) {
        if (esVacia() || vertice == null)
            return;
        Vertice q = vertice(vertice);
        if (!vertice.hayIzquierdo())
            return;
        Vertice p = q.izquierdo;
        Vertice r = p.izquierdo;
        Vertice s = p.derecho;
        Vertice t = q.derecho;
        Vertice a = null;
        boolean b = false;
        if (q != raiz)
            a = q.padre;
        if (a != null && a.derecho == q)
            b = true;

        p.derecho = q;
        q.padre = p;
        q.izquierdo = s;
        q.derecho = t;
        if (s != null)
            s.padre = q;
        if (t != null)
            t.padre = q;
        if (a != null){
            p.padre = a;
            if (b)
                a.derecho = p;
            else 
                a.izquierdo = p;
        } else {
            p.padre = null;
            raiz = p;
        }
        // Aquí va su código.
    }

    /**
     * Gira el árbol a la izquierda sobre el vértice recibido. Si el vértice no
     * tiene hijo derecho, el método no hace nada.
     * @param vertice el vértice sobre el que vamos a girar.
     */
    public void giraIzquierda(VerticeArbolBinario<T> vertice) {
        if (esVacia() || vertice == null)
            return;
        Vertice p = vertice(vertice);
        if (!vertice.hayDerecho())
            return;
        Vertice q = p.derecho;
        Vertice r = p.izquierdo;
        Vertice s = q.izquierdo;
        Vertice t = q.derecho;
        Vertice a = null;
        boolean b = false;
        if (p != raiz)
            a = p.padre;
        if (a != null && a.derecho == p)
            b = true;

        q.izquierdo = p;
        p.padre = q;
        p.izquierdo = r;
        p.derecho = s;
        if (r != null)
            r.padre = p;
        if (s != null)
            s.padre = p;
        if (a != null){
            q.padre = a;
            if (b)
                a.derecho = q;
            else 
                a.izquierdo = q;
        } else {
            q.padre = null;
            raiz = q;
        }
        // Aquí va su código.
    }

    /**
     * Realiza un recorrido DFS <em>pre-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPreOrder(AccionVerticeArbolBinario<T> accion) {
        if (esVacia())
            return;
        dfsPreOrderAux(accion, raiz);
        // Aquí va su código.
    }

    private void dfsPreOrderAux(AccionVerticeArbolBinario<T> a, Vertice v){
        if (v == null)
            return;
        a.actua(v);
        dfsPreOrderAux(a, v.izquierdo);
        dfsPreOrderAux(a, v.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>in-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsInOrder(AccionVerticeArbolBinario<T> accion) {
        if (esVacia())
            return;
        dfsInOrderAux(accion, raiz);
        // Aquí va su código.
    }

    private void dfsInOrderAux(AccionVerticeArbolBinario<T> a, Vertice v){
        if (v == null)
            return;
        dfsInOrderAux(a, v.izquierdo);
        a.actua(v);
        dfsInOrderAux(a, v.derecho);
    }

    /**
     * Realiza un recorrido DFS <em>post-order</em> en el árbol, ejecutando la
     * acción recibida en cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void dfsPostOrder(AccionVerticeArbolBinario<T> accion) {
        if (!esVacia())
            dfsPostOrderAux(accion, raiz);
        // Aquí va su código.
    }

    private void dfsPostOrderAux(AccionVerticeArbolBinario<T> a, Vertice v){
        if (v == null)
            return;
        dfsPostOrderAux(a, v.izquierdo);
        dfsPostOrderAux(a, v.derecho);
        a.actua(v);
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
