package mx.unam.ciencias.edd;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para diccionarios (<em>hash tables</em>). Un diccionario generaliza el
 * concepto de arreglo, mapeando un conjunto de <em>llaves</em> a una colección
 * de <em>valores</em>.
 */
public class Diccionario<K, V> implements Iterable<V> {

    /* Clase interna privada para entradas. */
    private class Entrada {

        /* La llave. */
        private K llave;
        /* El valor. */
        private V valor;

        /* Construye una nueva entrada. */
        private Entrada(K llave, V valor) {
            // Aquí va su código.
            this.llave = llave;
            this.valor = valor;
        }
    }

    /* Clase interna privada para iteradores. */
    private class Iterador {

        /* En qué lista estamos. */
        private int indice;
        /* Iterador auxiliar. */
        private Iterator<Entrada> iterador;

        /* Construye un nuevo iterador, auxiliándose de las listas del
         * diccionario. */
        private Iterador() {
            // Aquí va su código.
            //busca la primera lista distinta de nulo
            boolean hayLista = false;
            for (int i = 0; i < entradas.length; i++) {
                if (entradas[i] != null && entradas[i].getLongitud() > 0) {
                    this.indice = i;
                    this.iterador = entradas[i].iteradorLista();
                    hayLista = true;
                    break;
                }
            }
            //si ninguna lista es distinta de nulo el iterador es nulo
            if (!(hayLista)) {
                this.iterador = null;
            }
        }

        /* Nos dice si hay una siguiente entrada. */
        public boolean hasNext() {
            // Aquí va su código.
            //el iterador verifica que el iterador no sea nulo
            return iterador != null;
        }

        /* Regresa la siguiente entrada. */
        protected Entrada siguiente() {
            // Aquí va su código.
            //21.3 algoritmo para diccionarios
            //Si el iterador es ⌀ ocurrirá un error NoSuchElementException
            if (this.iterador == null) {
                throw new NoSuchElementException();
            }
            /*
             * En otro caso se guarda la entrada
             * siguiente del iterador; si el iterador ya no tiene siguiente elemento se
             * buscará la siguiente entrada en el arreglo con una lista distinta de ⌀ y se
             * actualizará el iterador al iterador de esa lista. Si ya no hay entradas en
             * el arreglo distintas de ⌀, haremos el iterador ⌀.
             */

            Entrada entrada = this.iterador.next();
            if (!(this.iterador.hasNext())) {
                boolean hayLista = false;
                for (int i = this.indice + 1; i < entradas.length; i++) {
                    if (entradas[i] != null && entradas[i].getLongitud() > 0) {
                        this.iterador = entradas[i].iteradorLista();
                        this.indice = i;
                        hayLista = true;
                        break;
                    }
                }
                if (!hayLista) {
                    this.iterador = null;
                }
            }
            return entrada;
        }

        /* Mueve el iterador a la siguiente entrada válida. */
        private void mueveIterador() {
            // Aquí va su código.
            boolean hayLista = false;
            for (int i = this.indice + 1; i < entradas.length; i++) {
                if (entradas[i] != null && entradas[i].getLongitud() > 0) {
                    this.iterador = entradas[i].iteradorLista();
                    this.indice = i;
                    hayLista = true;
                    break;
                }
            }
            if (!(hayLista)) {
                this.iterador = null;
            }
        }
    }

    /* Clase interna privada para iteradores de llaves. */
    private class IteradorLlaves extends Iterador
        implements Iterator<K> {

        /* Regresa el siguiente elemento. */
        @Override public K next() {
            // Aquí va su código.
            return siguiente().llave;
        }
    }

    /*
     * Las clases IteradorLlaves e IteradorValores son básicamente idénticas: ambas
     * llaman al constructor de Iterador y ambas en su método next() llaman al
     * método siguiente(). En IteradorLlaves regresaremos la llave de la entrada
     * regresada y en IteradorValores regresaremos el valor.
     */

    /* Clase interna privada para iteradores de valores. */
    private class IteradorValores extends Iterador
        implements Iterator<V> {

        /* Regresa el siguiente elemento. */
        @Override public V next() {
            // Aquí va su código.
            return siguiente().valor;
        }
    }

    /** Máxima carga permitida por el diccionario. */
    public static final double MAXIMA_CARGA = 0.72;

    /* Capacidad mínima; decidida arbitrariamente a 2^6. */
    private static final int MINIMA_CAPACIDAD = 64;

    /* Dispersor. */
    private Dispersor<K> dispersor;
    /* Nuestro diccionario. */
    private Lista<Entrada>[] entradas;
    /* Número de valores. */
    private int elementos;

    /* Truco para crear un arreglo genérico. Es necesario hacerlo así por cómo
       Java implementa sus genéricos; de otra forma obtenemos advertencias del
       compilador. */
    @SuppressWarnings("unchecked")
    private Lista<Entrada>[] nuevoArreglo(int n) {
        return (Lista<Entrada>[])Array.newInstance(Lista.class, n);
    }

    /**
     * Construye un diccionario con una capacidad inicial y dispersor
     * predeterminados.
     */
    public Diccionario() {
        this(MINIMA_CAPACIDAD, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial definida por el
     * usuario, y un dispersor predeterminado.
     * @param capacidad la capacidad a utilizar.
     */
    public Diccionario(int capacidad) {
        this(capacidad, (K llave) -> llave.hashCode());
    }

    /**
     * Construye un diccionario con una capacidad inicial predeterminada, y un
     * dispersor definido por el usuario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(Dispersor<K> dispersor) {
        this(MINIMA_CAPACIDAD, dispersor);
    }

    /**
     * Construye un diccionario con una capacidad inicial y un método de
     * dispersor definidos por el usuario.
     * @param capacidad la capacidad inicial del diccionario.
     * @param dispersor el dispersor a utilizar.
     */
    public Diccionario(int capacidad, Dispersor<K> dispersor) {
        // Aquí va su código.
        /*
         * El constructor inicializa el dispersor con el que se recibe. Si la
         * capacidad recibida es menor que la capacidad mínima la
         * incrementamos a ésta. Buscamos la primera potencia de 2 que sea
         * mayor o igual al doble de la capacidad y ésta será la capacidad del
         * arreglo. No tenemos una variable para la máscara porque es la
         * capacidad del arreglo menos 1.
         */
        this.dispersor = dispersor;
        this.entradas = nuevoArreglo(calculaCapacidad(capacidad));
        this.elementos = 0;
    }

    private int calculaCapacidad(int capacidad) {
        capacidad = (capacidad < 64) ? 64 : capacidad;
        int contador = 1;
        while (contador < capacidad * 2) {
            contador *= 2;
        }
        return contador;
    }

    /**
     * Agrega un nuevo valor al diccionario, usando la llave proporcionada. Si
     * la llave ya había sido utilizada antes para agregar un valor, el
     * diccionario reemplaza ese valor con el recibido aquí.
     * @param llave la llave para agregar el valor.
     * @param valor el valor a agregar.
     * @throws IllegalArgumentException si la llave o el valor son nulos.
     */
    public void agrega(K llave, V valor) {
        // Aquí va su código.

        /*
         * Si la llave o el valor recibidos son ⌀ ocurre un error.
         * Sea i la dispersión de la llave con la máscara aplicada; si el i-ésimo
         * elemento del arreglo es ⌀, creamos una lista de entradas y la ponemos
         * en el índice i del arreglo. Agregamos una nueva entrada con la llave y
         * el valor en esta lista e incrementamos el contador de elementos.
         * Si la lista en i sí existe, la recorremos para ver si existe una entrada con
         * la misma llave. Si éste es el caso, reemplazamos el valor de la entrada
         * con el nuevo valor. Si no es el caso, agregamos una nueva entrada con
         * la llave y el valor a la lista e incrementamos el contador de elementos.
         * Si la carga del diccionario alcanza o excede la carga máxima,
         * doblamos la capacidad del arreglo. Hay que volver a agregar todas las
         * entradas; no funciona nada más copiar las listas del viejo arreglo al
         * nuevo, porque al cambiar la capacidad también cambia la máscara y
         * entonces cada llave queda en entradas potencialmente distintas del
         * arreglo.
         */

        if (llave == null || valor == null) {
            throw new IllegalArgumentException();
        }
        int mascara = this.entradas.length - 1;
        int i = this.dispersor.dispersa(llave) & mascara;
        if (this.entradas[i] == null) {
            Lista<Entrada> nueva = new Lista<Entrada>();
            this.entradas[i] = nueva;
            Entrada nuevaEntrada = new Entrada(llave, valor);
            this.entradas[i].agregaFinal(nuevaEntrada);
            this.elementos += 1;
        } else {
            boolean hayLlave = false;
            for (int j = 0; j < this.entradas[i].getLongitud(); j++) {
                Entrada entrada = this.entradas[i].get(j);
                if (entrada.llave.equals(llave)) {
                    this.entradas[i].get(j).valor = valor;
                    hayLlave = true;
                }
            }
            if (!(hayLlave)) {
                Entrada nuevaEntrada = new Entrada(llave, valor);
                this.entradas[i].agregaFinal(nuevaEntrada);
                this.elementos += 1;
            }
        }
        if (this.carga() >= MAXIMA_CARGA) {
            Lista<Entrada>[] nuevo = nuevoArreglo(2 * this.entradas.length);
            int nuevaMascara = nuevo.length - 1;
            for (int j = 0; j < this.entradas.length; j++) {
                if (this.entradas[j] != null && this.entradas[j].getLongitud() > 0) {
                    for (int k = 0; k < this.entradas[j].getLongitud(); k++) {
                        int nuevoIndice = this.dispersor.dispersa(this.entradas[j].get(k).llave) & nuevaMascara;
                        if (nuevo[nuevoIndice] == null) {
                            Lista<Entrada> nuevaLista = new Lista<Entrada>();
                            nuevo[nuevoIndice] = nuevaLista;
                            nuevo[nuevoIndice].agregaFinal(this.entradas[j].get(k));
                        } else {
                            nuevo[nuevoIndice].agregaFinal(this.entradas[j].get(k));
                        }
                    }
                }
            }

            this.entradas = nuevo;
        }
    }

    /**
     * Regresa el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor.
     * @return el valor correspondiente a la llave.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no está en el diccionario.
     */
    public V get(K llave) {
        // Aquí va su código.

        /*
         * Si la llave es ⌀ ocurre un error.
         * Sea i la dispersión de la llave con la máscara aplicada; si el i-ésimo
         * elemento del arreglo es ⌀, ocurre un error. Si no buscamos la entrada en
         * la lista con una llave igual a la recibida.
         */

        if (llave == null) {
            throw new IllegalArgumentException();
        }
        int mascara = this.entradas.length - 1;
        int i = this.dispersor.dispersa(llave) & mascara;
        if (this.entradas[i] == null) {
            throw new NoSuchElementException();
        }
        for (int j = 0; j < this.entradas[i].getLongitud(); j++) {
            Entrada entrada = this.entradas[i].get(j);
            if (entrada.llave.equals(llave)) {
                return entrada.valor;
            }
        }
        //si la encontramos regresamos si no damos un error.
        throw new NoSuchElementException();
    }

    /**
     * Nos dice si una llave se encuentra en el diccionario.
     * @param llave la llave que queremos ver si está en el diccionario.
     * @return <code>true</code> si la llave está en el diccionario,
     *         <code>false</code> en otro caso.
     */
    public boolean contiene(K llave) {
        // Aquí va su código.
        if (llave == null) {
            return false;
        }
        int i = this.dispersor.dispersa(llave) & (this.entradas.length - 1);
        if (this.entradas[i] == null) {
            return false;
        }
        for (int j = 0; j < this.entradas[i].getLongitud(); j++) {
            if (this.entradas[i].get(j).llave.equals(llave)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Elimina el valor del diccionario asociado a la llave proporcionada.
     * @param llave la llave para buscar el valor a eliminar.
     * @throws IllegalArgumentException si la llave es nula.
     * @throws NoSuchElementException si la llave no se encuentra en
     *         el diccionario.
     */
    public void elimina(K llave) {
        // Aquí va su código.
        if (llave == null) {
            throw new IllegalArgumentException();
        }
        int i = this.dispersor.dispersa(llave) & (this.entradas.length - 1);
        if (this.entradas[i] == null) {
            throw new IllegalArgumentException();
        }
        boolean hayLlave = false;
        for (int j = 0; j < this.entradas[i].getLongitud(); j++) {
            if (this.entradas[i].get(j).llave.equals(llave)) {
                hayLlave = true;
                Entrada eliminar = this.entradas[i].get(j);
                this.entradas[i].elimina(eliminar);
                this.elementos -= 1;
            }
        }
        if (!(hayLlave)) {
            throw new NoSuchElementException();
        }      
    }

    /**
     * Nos dice cuántas colisiones hay en el diccionario.
     * @return cuántas colisiones hay en el diccionario.
     */
    public int colisiones() {
        // Aquí va su código.
        int cuentaColisiones = 0;
        for (int i = 0; i < this.entradas.length; i++) {
            if (this.entradas[i] != null && this.entradas[i].getLongitud() > 0) {
                cuentaColisiones += this.entradas[i].getLongitud() - 1;
            }
        }
        return cuentaColisiones;
    }

    /**
     * Nos dice el máximo número de colisiones para una misma llave que tenemos
     * en el diccionario.
     * @return el máximo número de colisiones para una misma llave.
     */
    public int colisionMaxima() {
        // Aquí va su código.
        int maxima = 0;
        int contador = 0;
        for (int i = 0; i < this.entradas.length; i++) {
            if (this.entradas[i] != null && this.entradas[i].getLongitud() > 0) {
                maxima = this.entradas[i].getLongitud();
                contador = i;
                break;
            }
        }
        for (int i = contador + 1; i < this.entradas.length; i++) {
            if (this.entradas[i] != null && this.entradas[i].getLongitud() > 0) {
                if (maxima < this.entradas[i].getLongitud()) {
                    maxima = this.entradas[i].getLongitud();
                }
            }
        }
        return maxima - 1;
    }

    /**
     * Nos dice la carga del diccionario.
     * @return la carga del diccionario.
     */
    public double carga() {
        // Aquí va su código.
        return (double) this.elementos / this.entradas.length;
    }

    /**
     * Regresa el número de entradas en el diccionario.
     * @return el número de entradas en el diccionario.
     */
    public int getElementos() {
        // Aquí va su código.
        return this.elementos;
    }

    /**
     * Nos dice si el diccionario es vacío.
     * @return <code>true</code> si el diccionario es vacío, <code>false</code>
     *         en otro caso.
     */
    public boolean esVacia() {
        // Aquí va su código.
        return this.elementos == 0;
    }

    /**
     * Limpia el diccionario de elementos, dejándolo vacío.
     */
    public void limpia() {
        // Aquí va su código.
        Lista<Entrada>[] yaLimpio = nuevoArreglo(this.entradas.length);
        this.elementos = 0;
        this.entradas = yaLimpio;
    }

    /**
     * Regresa una representación en cadena del diccionario.
     * @return una representación en cadena del diccionario.
     */
    @Override public String toString() {
        // Aquí va su código.
        if (this.elementos == 0) {
            return "{}";
        }
        String diccionario = "{ ";
        for (int i = 0; i < this.entradas.length; i++) {
            if (this.entradas[i] != null && this.entradas[i].getLongitud() > 0) {
                for (int j = 0; j < this.entradas[i].getLongitud(); j++) {
                    diccionario += "'" + this.entradas[i].get(j).llave + "': " + "'" + this.entradas[i].get(j).valor + "', "; 
                }
            }
        }
        return diccionario + "}";
    }

    /**
     * Nos dice si el diccionario es igual al objeto recibido.
     * @param o el objeto que queremos saber si es igual al diccionario.
     * @return <code>true</code> si el objeto recibido es instancia de
     *         Diccionario, y tiene las mismas llaves asociadas a los mismos
     *         valores.
     */
    @Override public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass())
            return false;
        @SuppressWarnings("unchecked") Diccionario<K, V> d =
            (Diccionario<K, V>)o;
        // Aquí va su código.
        if (this.elementos != d.elementos) {
            return false;
        }
        Iterator<K> iterador = this.iteradorLlaves();
        while (iterador.hasNext()) {
            K llave = iterador.next();
            if (!(d.contiene(llave))) {
                return false;
            }
            V valorD = d.get(llave);
            V valor = this.get(llave);
            if (!(valor.equals(valorD))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar las llaves del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar las llaves del diccionario.
     */
    public Iterator<K> iteradorLlaves() {
        return new IteradorLlaves();
    }

    /**
     * Regresa un iterador para iterar los valores del diccionario. El
     * diccionario se itera sin ningún orden específico.
     * @return un iterador para iterar los valores del diccionario.
     */
    @Override public Iterator<V> iterator() {
        return new IteradorValores();
    }
}
