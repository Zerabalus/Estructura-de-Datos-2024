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
        quickSortHelper(arreglo, comparador, 0, arreglo.length);
        // Aquí va su código.
    }

    /*
     * Método auxiliar para poder ordenar recursivamente quicksort.
     */
    private static <T> void quickSortHelper(T[] arreglo, Comparator<T> comparador, int start, int end){
        if (start+1 >= end)
            return;
        int pivote = arrange(arreglo, comparador, start, end);
        quickSortHelper(arreglo, comparador, start, pivote);
        quickSortHelper(arreglo, comparador, pivote+1, end);
    }

    /*
     * Método privado el cual en un arreglo separa la lista en elementos más grandes a x
     * y menos a x. donde x es el primer elemento de la lista.
     */
    private static <T> int arrange(T[] arreglo, Comparator<T> comparador, int start, int end){
        int pivote = start;
        int index = start+1;
        for (int i = start+1; i < end; i++){
            if (comparador.compare(arreglo[i], arreglo[pivote]) < 0){
                swap(arreglo, index, i);
                index++;
            }
        }
        swap(arreglo, start, index-1);
        return index-1;
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
        for (int i = 0; i < arreglo.length-1; i++){
            int min = i;
            for (int j = i+1; j < arreglo.length; j++){
                if (comparador.compare(arreglo[j], arreglo[min]) < 0)
                    min = j;
            }
            swap(arreglo, i, min);
        }
        // Aquí va su código.
    }

    //Método auxiliar para poder intercambiar elementos en un arreglo
    private static <T> void swap(T[] arreglo, int a, int b){
        T temp = arreglo[a];
        arreglo[a] = arreglo[b];
        arreglo[b] = temp;
    }

    /**
     * Ordena el arreglo recibido usando SelectionSort.
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
        return busquedaBinariaHelper(arreglo, elemento, comparador, 0, arreglo.length-1);
        // Aquí va su código.
    }

    //Método privado para hacer búsqueda binaria recursivo.
    public static <T> int busquedaBinariaHelper(T[] arreglo, T elemento, Comparator<T> comparador, int start, int end){
        if (start > end)
            return -1;
        int mitad = (start+end)/2;
        if (comparador.compare(elemento, arreglo[mitad]) == 0)
            return mitad;
        if (comparador.compare(elemento, arreglo[mitad]) < 0)
            return busquedaBinariaHelper(arreglo, elemento, comparador, 0, mitad-1);
        return busquedaBinariaHelper(arreglo, elemento, comparador, mitad+1, end);
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
