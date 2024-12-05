package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase para árboles binarios completos.</p>
 *
 * <p>Un árbol binario completo agrega y elimina elementos de tal forma que el
 * árbol siempre es lo más cercano posible a estar lleno.</p>
 */
public class ArbolBinarioCompleto<T> extends ArbolBinario<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Cola para recorrer los vértices en BFS. */
        private Cola<Vertice> cola;

        /* Inicializa al iterador. */
        private Iterador() {
            cola = new Cola<>();
            if (!esVacia())
                cola.mete(raiz);
            // Aquí va su código.
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return !cola.esVacia();
            // Aquí va su código.
        }

        /* Regresa el siguiente elemento en orden BFS. */
        @Override public T next() {
            if (!hasNext())
                throw new NoSuchElementException();
            Vertice tope = cola.saca();
            if (tope.hayIzquierdo())
                cola.mete(tope.izquierdo);
            if (tope.hayDerecho())
                cola.mete(tope.derecho);
            return tope.elemento;
            // Aquí va su código.
        }
    }

    /**
     * Constructor sin parámetros. Para no perder el constructor sin parámetros
     * de {@link ArbolBinario}.
     */
    public ArbolBinarioCompleto() { super(); }

    /**
     * Construye un árbol binario completo a partir de una colección. El árbol
     * binario completo tiene los mismos elementos que la colección recibida.
     * @param coleccion la colección a partir de la cual creamos el árbol
     *        binario completo.
     */
    public ArbolBinarioCompleto(Coleccion<T> coleccion) {
        super(coleccion);
    }

    /**
     * Agrega un elemento al árbol binario completo. El nuevo elemento se coloca
     * a la derecha del último nivel, o a la izquierda de un nuevo nivel.
     * @param elemento el elemento a agregar al árbol.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        Vertice nuevo = nuevoVertice(elemento);
        if (esVacia())
            raiz = nuevo;
        else {
            Cola<Vertice> cola = new Cola<>();
            cola.mete(raiz);
            while (!cola.esVacia()){
                Vertice v = cola.saca();
                if (v.hayIzquierdo())
                    cola.mete(v.izquierdo);
                else{
                    v.izquierdo = nuevo;
                    nuevo.padre = v;
                    break;
                }
                if (v.hayDerecho())
                    cola.mete(v.derecho);
                else {
                    v.derecho = nuevo;
                    nuevo.padre = v;
                    break;
                }
            }
        }
        elementos++;
        // Aquí va su código.
    }

    /**
     * Elimina un elemento del árbol. El elemento a eliminar cambia lugares con
     * el último elemento del árbol al recorrerlo por BFS, y entonces es
     * eliminado.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        if (esVacia() || elemento == null)
            return;
        if (elementos == 1){
            limpia();
            return;
        }
        Vertice elim = vertice(busca(elemento));
        if (elim == null)
            return;
        Vertice ultimo = raiz;
        Cola<Vertice> cola = new Cola<>();
        cola.mete(raiz);
        while (!cola.esVacia()){
            ultimo = cola.saca();
            if (ultimo.hayIzquierdo())
                cola.mete(ultimo.izquierdo);
            if (ultimo.hayDerecho())
                cola.mete(ultimo.derecho);
        }
        elim.elemento = ultimo.elemento;
        if (ultimo.padre.izquierdo == ultimo)
            ultimo.padre.izquierdo = null;
        else 
            ultimo.padre.derecho = null;
        elementos--;
        // Aquí va su código.
    }

    /**
     * Regresa la altura del árbol. La altura de un árbol binario completo
     * siempre es ⌊log<sub>2</sub><em>n</em>⌋.
     * @return la altura del árbol.
     */
    @Override public int altura() {
        if (esVacia())
            return -1;
        return (int)(Math.log(elementos)/Math.log(2));
        // Aquí va su código.
    }

    /**
     * Realiza un recorrido BFS en el árbol, ejecutando la acción recibida en
     * cada elemento del árbol.
     * @param accion la acción a realizar en cada elemento del árbol.
     */
    public void bfs(AccionVerticeArbolBinario<T> accion) {
        if (esVacia())
            return;
        Cola<VerticeArbolBinario<T>> cola = new Cola<>();
        cola.mete(raiz);
        while (!cola.esVacia()){
            VerticeArbolBinario<T> v = cola.saca();
            accion.actua(v);
            if (v.hayIzquierdo())
                cola.mete(v.izquierdo());
            if (v.hayDerecho())
                cola.mete(v.derecho());
        }
        // Aquí va su código.
    }

    /**
     * Regresa un iterador para iterar el árbol. El árbol se itera en orden BFS.
     * @return un iterador para iterar el árbol.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
