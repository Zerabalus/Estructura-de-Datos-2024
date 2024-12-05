package mx.unam.ciencias.edd;

import java.util.Comparator;
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
            this.elemento = elemento;
            // Aquí va su código.
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
            start();
            // Aquí va su código.
        }

        /* Nos dice si hay un elemento siguiente. */
        @Override public boolean hasNext() {
            return siguiente != null;
            // Aquí va su código.
        }

        /* Nos da el elemento siguiente. */
        @Override public T next() {
            if (siguiente == null)
                throw new NoSuchElementException();
            anterior = siguiente;
            siguiente = siguiente.siguiente;
            return anterior.elemento;
            // Aquí va su código.
        }

        /* Nos dice si hay un elemento anterior. */
        @Override public boolean hasPrevious() {
            return anterior != null;
            // Aquí va su código.
        }

        /* Nos da el elemento anterior. */
        @Override public T previous() {
            if (anterior == null)
                throw new NoSuchElementException();
            siguiente = anterior;
            anterior = anterior.anterior;
            return siguiente.elemento;
            // Aquí va su código.
        }

        /* Mueve el iterador al inicio de la lista. */
        @Override public void start() {
            siguiente = cabeza;
            anterior = null;
            // Aquí va su código.
        }

        /* Mueve el iterador al final de la lista. */
        @Override public void end() {
            anterior = rabo;
            siguiente = null;
            // Aquí va su código.
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
        return longitud;
        // Aquí va su código.
    }

    /**
     * Regresa el número elementos en la lista. El método es idéntico a {@link
     * #getLongitud}.
     * @return el número elementos en la lista.
     */
    @Override public int getElementos() {
        return getLongitud();
        // Aquí va su código.
    }

    /**
     * Nos dice si la lista es vacía.
     * @return <code>true</code> si la lista es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        return longitud == 0;
        // Aquí va su código.
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
        agregaFinal(elemento);
        // Aquí va su código.
    }

    /**
     * Agrega un elemento al final de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaFinal(T elemento) {
        if (elemento == null)
            throw new IllegalArgumentException();
        Nodo n = new Nodo(elemento);
        if (esVacia())
            cabeza = rabo = n;
        else {
            rabo.siguiente = n;
            n.anterior = rabo;
            rabo = n;
        }
        longitud++;
        // Aquí va su código.
    }

    /**
     * Agrega un elemento al inicio de la lista. Si la lista no tiene elementos,
     * el elemento a agregar será el primero y último.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si <code>elemento</code> es
     *         <code>null</code>.
     */
    public void agregaInicio(T elemento) {
        if (elemento ==  null)
            throw new IllegalArgumentException();
        Nodo n = new Nodo(elemento);
        if (esVacia())
            cabeza = rabo = n;
        else {
            cabeza.anterior = n;
            n.siguiente = cabeza;
            cabeza = n;
        }
        longitud++;
        // Aquí va su código.
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
        if (elemento == null)
            throw new IllegalArgumentException();
        if (i <= 0)
            agregaInicio(elemento);
        else if (i >= longitud)
            agregaFinal(elemento);
        else {
            Nodo b = cabeza;
            while (i-- > 0)
                b = b.siguiente;
            Nodo nuevo = new Nodo(elemento);
            Nodo a = b.anterior;
            a.siguiente = nuevo;
            nuevo.anterior = a;
            nuevo.siguiente = b;
            b.anterior = nuevo;
            longitud++;
        }
        // Aquí va su código.
    }

    /**
     * Elimina un elemento de la lista. Si el elemento no está contenido en la
     * lista, el método no la modifica.
     * @param elemento el elemento a eliminar.
     */
    @Override public void elimina(T elemento) {
        Nodo n = buscaNodo(elemento);
        if (n == null)
            return;
        if (n == cabeza)
            eliminaPrimero();
        else if (n == rabo)
            eliminaUltimo();
        else {
            Nodo a = n.anterior;
            Nodo b = n.siguiente;
            a.siguiente = b;
            b.anterior = a;
            longitud--;
        }
        // Aquí va su código.
    }

    /**
     * Elimina el primer elemento de la lista y lo regresa.
     * @return el primer elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaPrimero() {
        if (esVacia())
            throw new NoSuchElementException();
        T elem = cabeza.elemento;
        if (longitud == 1)
            limpia();
        else {
            cabeza = cabeza.siguiente;
            cabeza.anterior = null;
            longitud--;
        }
        return elem;
        // Aquí va su código.
    }

    /**
     * Elimina el último elemento de la lista y lo regresa.
     * @return el último elemento de la lista antes de eliminarlo.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T eliminaUltimo() {
        if (esVacia())
            throw new NoSuchElementException();
        T elem = rabo.elemento;
        if (longitud == 1)
            limpia();
        else {
            rabo = rabo.anterior;
            rabo.siguiente = null;
            longitud--;
        }
        return elem;
        // Aquí va su código.
    }

    /**
     * Nos dice si un elemento está en la lista.
     * @param elemento el elemento que queremos saber si está en la lista.
     * @return <code>true</code> si <code>elemento</code> está en la lista,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        return buscaNodo(elemento) != null;
        // Aquí va su código.
    }

    /**
     * Regresa la reversa de la lista.
     * @return una nueva lista que es la reversa la que manda llamar el método.
     */
    public Lista<T> reversa() {
        Lista<T> l = new Lista<>();
        for (T t : this)
            l.agregaInicio(t);
        return l;
        // Aquí va su código.
    }

    /**
     * Regresa una copia de la lista. La copia tiene los mismos elementos que la
     * lista que manda llamar el método, en el mismo orden.
     * @return una copiad de la lista.
     */
    public Lista<T> copia() {
        Lista<T> l = new Lista<>();
        for (T t : this)
            l.agregaFinal(t);
        return l;
        // Aquí va su código.
    }

    /**
     * Limpia la lista de elementos, dejándola vacía.
     */
    @Override public void limpia() {
        longitud = 0;
        cabeza = rabo = null;
        // Aquí va su código.
    }

    /**
     * Regresa el primer elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getPrimero() {
        if (esVacia())
            throw new NoSuchElementException();
        return cabeza.elemento;
        // Aquí va su código.
    }

    /**
     * Regresa el último elemento de la lista.
     * @return el primer elemento de la lista.
     * @throws NoSuchElementException si la lista es vacía.
     */
    public T getUltimo() {
        if (esVacia())
            throw new NoSuchElementException();
        return rabo.elemento;
        // Aquí va su código.
    }

    /**
     * Regresa el <em>i</em>-ésimo elemento de la lista.
     * @param i el índice del elemento que queremos.
     * @return el <em>i</em>-ésimo elemento de la lista.
     * @throws ExcepcionIndiceInvalido si <em>i</em> es menor que cero o mayor o
     *         igual que el número de elementos en la lista.
     */
    public T get(int i) {
        if (i < 0 || i >= longitud)
            throw new ExcepcionIndiceInvalido();
        IteradorLista<T> it = iteradorLista();
        T t = null;
        while(it.hasNext() && i-- >= 0)
            t = it.next();
        return t;
        // Aquí va su código.
    }

    /**
     * Regresa el índice del elemento recibido en la lista.
     * @param elemento el elemento del que se busca el índice.
     * @return el índice del elemento recibido en la lista, o -1 si el elemento
     *         no está contenido en la lista.
     */
    public int indiceDe(T elemento) {
        int i = 0;
        IteradorLista<T> it = iteradorLista();
        while (it.hasNext()){
            if (elemento.equals(it.next()))
                return i;
            i++;
        }
        return -1;
        // Aquí va su código.
    }

    /**
     * Regresa una representación en cadena de la lista.
     * @return una representación en cadena de la lista.
     */
    @Override public String toString() {
        if (esVacia())
            return "[]";
        String s = "[";
        IteradorLista<T> it = iteradorLista();
        s += it.next();
        while (it.hasNext()) {
            s += ", " + it.next();
        }
        return s + "]";

        // Aquí va su código.
    }

    /**
     * Nos dice si la lista es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la lista es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Lista<T> lista = (Lista<T>)objeto;
        if (longitud != lista.getLongitud())
            return false;
        IteradorLista<T> it1 = iteradorLista();
        IteradorLista<T> it2 = lista.iteradorLista();
        while (it1.hasNext()){
            if (!it1.next().equals(it2.next()))
                return false;
        }
        return true;
        // Aquí va su código.
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

    /* Método auxiliar para encontrar un nodo por elemento */
    private Nodo buscaNodo(T elem){
        Nodo n = cabeza;
        while (n != null){
            if (n.elemento.equals(elem))
                return n;
            n = n.siguiente;
        }
        return null;
    }

    /**
     * Regresa una copia de la lista, pero ordenada. Para poder hacer el
     * ordenamiento, el método necesita una instancia de {@link Comparator} para
     * poder comparar los elementos de la lista.
     * @param comparador el comparador que la lista usará para hacer el
     *                   ordenamiento.
     * @return una copia de la lista, pero ordenada.
     */
    public Lista<T> mergeSort(Comparator<T> comparador) {
        return mergeSortHelper(copia(), comparador);
        // Aquí va su código.
    }

    //Método auxiliar recursivo para hacer mergeSort
    private Lista<T> mergeSortHelper(Lista<T> l, Comparator<T> comparador){
        if (l.getLongitud()<= 1)
            return l;
        int mitad = l.getLongitud()/2;
        Lista<T> l1 = new Lista<>();
        while (mitad-- > 0)
            l1.agregaFinal(l.eliminaPrimero());
        return merge(mergeSortHelper(l1, comparador), mergeSortHelper(l, comparador), comparador);
    }

    // Método auxiliar para juntar 2 listas ya ordenadas:
    private Lista<T> merge(Lista<T> l1, Lista<T> l2, Comparator<T> comparador){
        Lista<T> l = new Lista<>();
        while(!l1.esVacia() || !l2.esVacia()){
            if (l1.esVacia())
                return concatenaLista(l, l2);
            if (l2.esVacia())
                return concatenaLista(l, l1);
            if (comparador.compare(l1.getPrimero(), l2.getPrimero()) <= 0)
                l.agrega(l1.eliminaPrimero());
            else
                l.agrega(l2.eliminaPrimero());
        }
        return l;
    }

    //Método auxiliar para concatenar 2 listas en una sola lista:
    private Lista<T> concatenaLista(Lista<T> l1, Lista<T> l2){
        for (T t: l2)
            l1.agrega(t);
        return l1;
    }
    /**
     * Regresa una copia de la lista recibida, pero ordenada. La lista recibida
     * tiene que contener nada más elementos que implementan la interfaz {@link
     * Comparable}.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista que se ordenará.
     * @return una copia de la lista recibida, pero ordenada.
     */
    public static <T extends Comparable<T>>
    Lista<T> mergeSort(Lista<T> lista) {
        return lista.mergeSort((a, b) -> a.compareTo(b));
    }

    /**
     * Busca un elemento en la lista ordenada, usando el comparador recibido. El
     * método supone que la lista está ordenada usando el mismo comparador.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador con el que la lista está ordenada.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public boolean busquedaLineal(T elemento, Comparator<T> comparador) {
        if (esVacia())
            return false;
        if (comparador.compare(elemento, cabeza.elemento) < 0)
            return false;
        if (comparador.compare(elemento, rabo.elemento) > 0)
            return false;
        return buscaNodo(elemento) != null;
        // Aquí va su código.
    }

    /**
     * Busca un elemento en una lista ordenada. La lista recibida tiene que
     * contener nada más elementos que implementan la interfaz {@link
     * Comparable}, y se da por hecho que está ordenada.
     * @param <T> tipo del que puede ser la lista.
     * @param lista la lista donde se buscará.
     * @param elemento el elemento a buscar.
     * @return <code>true</code> si el elemento está contenido en la lista,
     *         <code>false</code> en otro caso.
     */
    public static <T extends Comparable<T>>
    boolean busquedaLineal(Lista<T> lista, T elemento) {
        return lista.busquedaLineal(elemento, (a, b) -> a.compareTo(b));
    }
}
