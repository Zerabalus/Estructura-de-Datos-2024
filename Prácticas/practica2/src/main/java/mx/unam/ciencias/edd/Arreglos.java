package mx.unam.ciencias.edd;

import java.util.Comparator;

/**
 * Clase para ordenar y buscar arreglos genéricos.
 */
public class Arreglos {

    /* Constructor privado para evitar instanciación. */
    private Arreglos() {}

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordenar el arreglo.
     */
    public static <T> void
    quickSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        quickSort(arreglo, 0, arreglo.length-1, comparador);
    }

    public static <T> void quickSort(T[] arreglo, int ini, int fin, Comparator<T> comparador) {
        if(fin<= ini){
            return;
        }
        int i = ini +1;
        int j = fin;
        while(i<j){
            if((comparador.compare(arreglo[i], arreglo[ini])>0) && comparador.compare(arreglo[j], arreglo[ini])<=0){
                intercambia(arreglo, i, j);
                i++;
                j--;
            }
            else if(comparador.compare(arreglo[i], arreglo[ini])<=0){
                i++;
            }
            else{
                j--;
            }
        }
        if(comparador.compare(arreglo[i], arreglo[ini])>0){
            i--;
        }
        intercambia(arreglo, ini, i);
        quickSort(arreglo, ini, i-1, comparador);
        quickSort(arreglo, i+1, fin, comparador);
    }

    /**
     * Ordena el arreglo recibido usando QickSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    quickSort(T[] arreglo) {
        quickSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo a ordenar.
     * @param comparador el comparador para ordernar el arreglo.
     */
    public static <T> void
    selectionSort(T[] arreglo, Comparator<T> comparador) {
        // Aquí va su código.
        int m;
        for (int i = 0; i < arreglo.length; i++) {
            m = i;
            for (int j = i + 1; j < arreglo.length; j++) {
                if (comparador.compare(arreglo[j], arreglo[m]) < 0) {
                    m = j;
                }
            }
            intercambia(arreglo, i, m);
        }
    }

    private static <T> void intercambia(T[] arreglo, int a, int b) {
        T elemento1 = arreglo[a];
        T elemento2 = arreglo[b];
        arreglo[a] = elemento2;
        arreglo[b] = elemento1;
    }

    /**
     * Ordena el arreglo recibid                                                                                                 o usando SelectionSort.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     */
    public static <T extends Comparable<T>> void
    selectionSort(T[] arreglo) {
        selectionSort(arreglo, (a, b) -> a.compareTo(b));
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo el arreglo dónde buscar.
     * @param elemento el elemento a buscar.
     * @param comparador el comparador para hacer la búsqueda.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T> int
    busquedaBinaria(T[] arreglo, T elemento, Comparator<T> comparador) {
        // Aquí va su código.
        return busquedaBinaria(arreglo, elemento, 0, arreglo.length - 1, comparador);
    }

    public static <T> int busquedaBinaria(T[] arreglo, T elemento, int ini, int fin,
            Comparator<T> comparador) {
        if (fin < ini) {
            return -1;
        }
        int medio = ((fin - ini) / 2) + ini;
        if (comparador.compare(elemento, arreglo[medio]) == 0)
            return medio;
        else if (comparador.compare(elemento, arreglo[medio]) < 0) {
            return busquedaBinaria(arreglo, elemento, ini, medio - 1, comparador);
        } else {
            return busquedaBinaria(arreglo, elemento, medio + 1, fin, comparador);
        }
    }

    /**
     * Hace una búsqueda binaria del elemento en el arreglo. Regresa el índice
     * del elemento en el arreglo, o -1 si no se encuentra.
     * @param <T> tipo del que puede ser el arreglo.
     * @param arreglo un arreglo cuyos elementos son comparables.
     * @param elemento el elemento a buscar.
     * @return el índice del elemento en el arreglo, o -1 si no se encuentra.
     */
    public static <T extends Comparable<T>> int
    busquedaBinaria(T[] arreglo, T elemento) {
        return busquedaBinaria(arreglo, elemento, (a, b) -> a.compareTo(b));
    }
}
