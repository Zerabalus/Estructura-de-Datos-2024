package mx.unam.ciencias.edd;

import java.util.NoSuchElementException;

/**
 * Clase para montículos de Dijkstra con arreglos.
 */
public class MonticuloArreglo<T extends ComparableIndexable<T>>
    implements MonticuloDijkstra<T> {

    /* Número de elementos en el arreglo. */
    private int elementos;
    /* Usamos un truco para poder utilizar arreglos genéricos. */
    private T[] arreglo;

    /* Truco para crear arreglos genéricos. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked") private T[] nuevoArreglo(int n) {
        return (T[])(new ComparableIndexable[n]);
    }

    /**
     * Constructor para montículo de Dijkstra con un arreglo a partir de una
     * colección.
     * @param coleccion la colección a partir de la cuál queremos construir el
     *                  montículo.
     */
    public MonticuloArreglo(Coleccion<T> coleccion) {
        this(coleccion, coleccion.getElementos());
    }

    /**
     * Construye un nuevo para montículo de Dijkstra con arreglo a partir de un
     * iterable.
     * @param iterable el iterable a partir de la cual construir el montículo.
     * @param n el número de elementos en el iterable.
     */
    public MonticuloArreglo(Iterable<T> iterable, int n) {
        // Aquí va su código.

        /* Creamos un nuevo arreglo usando nuevoArreglo() con la n recibida y
        agregamos los elementos del iterable en el arreglo, definiendo los índices
        de los mismos. La variable elementos se inicializa con el tamaño del
        arreglo. */

        arreglo = nuevoArreglo(n);
        int i = 0;
        for (T elemento : iterable) {
            arreglo[i] = elemento;
            arreglo[i].setIndice(i);
            i++;
        }
        elementos = n;
    }

    /**
     * Elimina el elemento mínimo del montículo.
     * @return el elemento mínimo del montículo.
     * @throws IllegalStateException si el montículo es vacío.
     */
    @Override public T elimina() {
        // Aquí va su código.

        /* 18.6 del libro:
        Si el número de elementos en el montículo es 0 (en otras palabras, la
        variable de clase elementos es 0), ocurre un error.
        Si no recorremos el arreglo buscando el mínimo elemento en el mismo.
        Anulamos la entrada en el arreglo que le corresponde, le definimos su
        índice como −1, decrementamos el número de elementos y regresamos el
        elemento mínimo.
        Podríamos intercambiar el último elemento del arreglo (usando la
        variable elementos) con el eliminado, para no tener hoyos anulados; pero
        es realizar una operación más con la que no ganamos realmente nada. */

        if (elementos == 0)
            throw new IllegalStateException("El monticulo es vacio");

        T minimo = arreglo[0];

        for (T elemento : arreglo) 
            if (minimo == null && elemento != null)
                minimo = elemento;
            else if (elemento != null && elemento.compareTo(minimo) <= 0)
                minimo = elemento;

        int indice = minimo.getIndice();
        minimo.setIndice(-1);
        arreglo[indice] = null;
        elementos--;

        return minimo;
    }

    /**
     * Regresa el <i>i</i>-ésimo elemento del arreglo.
     * @param i el índice del elemento que queremos.
     * @return el <i>i</i>-ésimo elemento del arreglo.
     * @throws NoSuchElementException si i es menor que cero, o mayor o igual
     *         que el número de elementos.
     */
    @Override public T get(int i) {
        // Aquí va su código.
        if (i < 0)
            throw new NoSuchElementException("No puedes ingresar indices menores a 0");
        if (i >= elementos)
            throw new NoSuchElementException("No puedes ingresar indices mayores a la longitud del arreglo");

        return arreglo[i];
    }

    /**
     * Nos dice si el montículo es vacío.
     * @return <code>true</code> si ya no hay elementos en el montículo,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean esVacia() {
        return elementos == 0;
    }

    /**
     * Regresa el número de elementos en el montículo.
     * @return el número de elementos en el montículo.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return elementos;
    }
}
