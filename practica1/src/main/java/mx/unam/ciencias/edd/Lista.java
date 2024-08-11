package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * <p>Clase genérica para listas doblemente ligadas.</p>
 *
 * <p>Las listas nos permiten agregar elementos al inicio o final de la lista,
 * eliminar elementos de la lista, comprobar si un elemento está o no en la
 * lista, y otras operaciones básicas.</p>
 *
 * <p>Las listas no aceptan a <code>null</code> como elemento.</p>
 *
 * @param <T> El tipo de los elementos de la lista.
 */
public class Lista<T> implements Coleccion<T> {

    /* Clase interna privada para nodos. */
    private class Nodo {
        /* El elemento del nodo. */
        private T elemento;
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nodo con un elemento. */
        private Nodo(T elemento) {
            // Aquí va su código.
            this.elemento = elemento;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador implements IteradorLista<T> {
        /* El nodo anterior. */
        private Nodo anterior;
        /* El nodo siguiente. */
        private Nodo siguiente;

        /* Construye un nuevo iterador. */
        private Iterador() {
            // Aquí va su código.
            start();
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return siguiente != null;
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            // Aquí va su código.
            if (!hasNext())
                throw new NoSuchElementException();
            else {
                T elem = siguiente.elemento;
                anterior = siguiente;
                siguiente = siguiente.siguiente;
                return elem;
            }
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            // Aquí va su código.
            return anterior != null;
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            // Aquí va su código.
            if (!hasPrevious())
                throw new NoSuchElementException();
            else {
                T elem = anterior.elemento;
                siguiente = anterior;
                anterior = anterior.anterior;
                return elem;
            }            
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            // Aquí va su código.
            anterior = null;
            siguiente = cabeza;
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            // Aquí va su código.
            siguiente = null;
            anterior = rabo;
        }
    }

    /* Primer elemento de la lista. */
    private Nodo cabeza;
    /* Último elemento de la lista. */
    private Nodo rabo;
    /* Número de elementos en la lista. */
    private int longitud;

    /**
     * Regresa la longitud de la lista. El método es idéntico a {@link
     * #getElementos}.
     * @return la longitud de la lista, el número de elementos que contiene.
     */
    public int getLongitud() {
        // Aquí va su código.
        return longitud;
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return getLongitud();
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        if (cabeza == null && rabo == null && longitud == 0)
            return true;
        else
            return false;  
    }

    /**
     * Agrega un elemento a la lista. Si la lista no tiene elementos, el
     * elemento a agregar será el primero y último. El método es idéntico a
     * {@link #agregaFinal}.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if(elemento == null){
            throw new IllegalArgumentException();
        }
        Nodo n = new Nodo(elemento);
        if (esVacia()){
            cabeza = rabo = n;
            longitud = 1;
            return;
        }
        else {
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
            longitud++;
        }

    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        // Aquí va su código.
        agrega(elemento);
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        // Aquí va su código.
        if (elemento == null){
            throw new IllegalArgumentException("Elemento invalido");
        }
        Nodo n = new Nodo(elemento);
        if (esVacia()){
            cabeza = rabo = n;
            longitud = 1;
            return;
        }
        else {
            cabeza.anterior = n;
            n.siguiente = cabeza;
            cabeza = n;
            longitud++;
        }
    }

    /**
     * Inserta un elemento en un índice explícito.
     *
     * Si el índice es menor o igual que cero, el elemento se agrega al inicio
     * de la lista. Si el índice es mayor o igual que el número de elementos en
     * la lista, el elemento se agrega al fina de la misma. En otro caso,
     * después de mandar llamar el método, el elemento tendrá el índice que se
     * especifica en la lista.
     * @param i el índice dónde insertar el elemento. Si es menor que 0 el
     *          elemento se agrega al inicio de la lista, y si es mayor o igual
     *          que el número de elementos en la lista se agrega al final.
     * @param elemento el elemento a insertar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void inserta(int i, T elemento) {
        // Aquí va su código.
        if (elemento == null){
            throw new IllegalArgumentException();
        }

        else if (i <= 0){
            agregaInicio(elemento);
            return;
        }
        else if (longitud <= i){
            agregaFinal(elemento);
            return;
        }

        Iterador it = new Iterador();
        for (int nodo = 0; nodo < i; nodo++) {
            it.next();
        }

        Nodo nuevoNodo = new Nodo(elemento);
        Nodo aux = it.siguiente;
        nuevoNodo.anterior = aux.anterior;
        nuevoNodo.siguiente = aux;
        aux.anterior.siguiente = nuevoNodo;
        aux.anterior = nuevoNodo;
        longitud++;
    
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override
    public void elimina(T elemento) {
        // Aquí va su código.
        if (esVacia()) {
            return;
        }
        if (cabeza.elemento.equals(elemento)) {
            eliminaPrimero();
            return;
        }
        Iterador it= new Iterador();
        while (it.hasNext()) {
            if (it.siguiente == rabo && it.siguiente.elemento.equals(elemento)) {
                eliminaUltimo();
                return;
            }
            if (it.siguiente.elemento.equals(elemento)) {
                Nodo anterior = it.anterior;
                Nodo siguiente = it.siguiente.siguiente;
                anterior.siguiente = siguiente;
                siguiente.anterior = anterior;
                longitud--;
                return;
            }
            it.next();
        }
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        // Aquí va su código.
        if(esVacia()){
            throw new NoSuchElementException();
        }
        T cAnt = cabeza.elemento;
        if(cabeza.siguiente == null){
            limpia();
            return cAnt;
        }
        Nodo n = cabeza.siguiente;
        n.anterior = null;
        cabeza = n;
        longitud--;
        return cAnt;
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        // Aquí va su código.
        if(esVacia()){
            throw new NoSuchElementException();
        }
        T rAnt = rabo.elemento;
        if(rabo.anterior == null){
            limpia();
            return rAnt;
        }
        rabo = rabo.anterior;
        rabo.siguiente = null;
        longitud--;
        return rAnt;
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        Iterador it= new Iterador();
        while(it.hasNext()){
            if(it.next().equals(elemento)){
                return true;
            }
        }
        return false;
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        // Aquí va su código.
        Lista<T> lista = new Lista<T>();
        Iterador itResultado = new Iterador();
        itResultado.end();
        while(itResultado.hasPrevious()){
            lista.agrega(itResultado.previous());
        }
        return lista;
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
    // Aquí va su código.
    Lista<T> lista = new Lista<>();
        Iterador nuevo = new Iterador();
        while(nuevo.hasNext()){
            lista.agrega(nuevo.next());
        }
        return lista;
    }
        
    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        cabeza = rabo = null;
        longitud = 0;
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        // Aquí va su código.
        if(cabeza == null){
            throw new NoSuchElementException();
        }
        return (longitud != 0) ? cabeza.elemento : null;
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        // Aquí va su código.
        if(cabeza == null){
            throw new NoSuchElementException();
        }
        return rabo.elemento;
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        // Aquí va su código.
        if(longitud <= i || i < 0){
            throw new ExcepcionIndiceInvalido();
        }
        Iterador it = new Iterador();
        T elem = null;
        for(int n = 0; n<=i; n++){
            elem = it.next();
        }
        return elem;
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        // Aquí va su código.
        Iterador it = new Iterador();
        for(int i = 0; i<longitud; i++){
            if(it.next().equals(elemento)){
                return i;
            }
        }
        return -1;
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        // Aquí va su código.
        if (esVacia())
            return "[]";
        else {
            String s = "[";
            int indice = 0;
            return s + auxToString(indice) + "]";
        }
    }

    private String auxToString(int indice) {
        if (indice == longitud - 1)
            return String.format("%s", get(indice));
        return String.format("%s, ", get(indice)) + auxToString(indice + 1);
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * 
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override
    public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>) objeto;
        // Aquí va su código.
        if (lista == null)
            return false;
        else if (lista.getLongitud() != longitud)
            return false;
        else if (lista.getLongitud() == 0 && longitud == 0)
            return true;
        Nodo nodo = cabeza;
        int i = 0;
        while (nodo != null) {
            if (nodo.elemento.equals(lista.get(i)) == false)
                return false;
            nodo = nodo.siguiente;
            i++;
        }
        return true;
    }

    /**
     * Regresa un iterador para recorrer la lista en una dirección.
     * @return un iterador para recorrer la lista en una dirección.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }

    /**
     * Regresa un iterador para recorrer la lista en ambas direcciones.
     * @return un iterador para recorrer la lista en ambas direcciones.
     */
    public IteradorLista<T> iteradorLista() {
        return new Iterador();
    }
}
