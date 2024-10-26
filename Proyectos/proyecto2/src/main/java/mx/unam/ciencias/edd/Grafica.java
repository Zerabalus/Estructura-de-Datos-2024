package mx.unam.ciencias.edd;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Clase para gráficas. Una gráfica es un conjunto de vértices y aristas, tales
 * que las aristas son un subconjunto del producto cruz de los vértices.
 */
public class Grafica<T> implements Coleccion<T> {

    /* Clase interna privada para iteradores. */
    private class Iterador implements Iterator<T> {

        /* Iterador auxiliar. */
        private Iterator<Vertice> iterador;

        /* Construye un nuevo iterador, auxiliándose de la lista de vértices. */
        public Iterador() {
            // Aquí va su código.
            iterador = vertices.iterator(); // itera los vertices de la gráfica
        }

        /* Nos dice si hay un siguiente elemento. */
        @Override public boolean hasNext() {
            // Aquí va su código.
            return iterador.hasNext(); //se llama al método hasnext del iterador
        }

        /* Regresa el siguiente elemento. */
        @Override public T next() {
            // Aquí va su código.
            return iterador.next().elemento;//se regresa el elem de llamar next con el iterador
        }
    }

    /* Clase interna privada para vértices. */
    private class Vertice implements VerticeGrafica<T> {

        /* El elemento del vértice. */
        private T elemento;
        /* El color del vértice. */
        private Color color;
        /* La lista de vecinos del vértice. */
        private Lista<Vertice> vecinos;

        /* Crea un nuevo vértice a partir de un elemento. */
        public Vertice(T elemento) {
            // Aquí va su código.

            //17.5 ALGORITMOS PARA GRÁFICAS
            this.elemento=elemento; //constructor define el elemento del vértice como el elemento recibido
            this.color=Color.NINGUNO;//el color del vértice como Color.NINGUNO
            this.vecinos=new Lista<Vertice>();//se crea la lista de vértices vecinos
        }

        /* Regresa el elemento del vértice. */
        @Override public T get() {
            // Aquí va su código.
            return this.elemento; //regresa el elemento del vértice
        }

        /* Regresa el grado del vértice. */
        @Override public int getGrado() {
            // Aquí va su código.
            return vecinos.getLongitud();//longitud de la lista de vértices vecinos.
        }

        /* Regresa el color del vértice. */
        @Override public Color getColor() {
            // Aquí va su código.
            return color; //regresa el color el vértice
        }

        /* Regresa un iterable para los vecinos. */
        @Override public Iterable<? extends VerticeGrafica<T>> vecinos() {
            // Aquí va su código.
            return vecinos; //regresa vertices vecinos
        }
    }

    /* Vértices. */
    private Lista<Vertice> vertices;
    /* Número de aristas. */
    private int aristas;

    /**
     * Constructor único.
     */
    public Grafica() {
        // Aquí va su código.
        vertices=new Lista<Vertice>(); //guardamos los vertices en una lista, tal como dijo el profe :)
    }

    /**
     * Regresa el número de elementos en la gráfica. El número de elementos es
     * igual al número de vértices.
     * @return el número de elementos en la gráfica.
     */
    @Override public int getElementos() {
        // Aquí va su código.
        return vertices.getLongitud();
    }

    /**
     * Regresa el número de aristas.
     * @return el número de aristas.
     */
    public int getAristas() {
        // Aquí va su código.
        return aristas;//Regresa el contador interno de aristas.
    }

    /**
     * Agrega un nuevo elemento a la gráfica.
     * @param elemento el elemento a agregar.
     * @throws IllegalArgumentException si el elemento ya había sido agregado a
     *         la gráfica.
     */
    @Override public void agrega(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException();
        if (contiene(elemento))
            throw new IllegalArgumentException("El elemento ya ha sido agregado a la gráfica"); // si el elemento ya
                                                                                                // existe
        Vertice vertice = new Vertice(elemento);
        vertices.agrega(vertice);
    }

    /**
     * Conecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica. El peso de la arista que conecte a los elementos será 1.
     * @param a el primer elemento a conectar.
     * @param b el segundo elemento a conectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b ya están conectados, o si a es
     *         igual a b.
     */
    public void conecta(T a, T b) {
        // Aquí va su código.

        /*
         * Se buscan los vértices correspondientes a los elementos recibidos; si
         * alguno no está en la gráfica un error ocurre. Si los dos vértices son
         * iguales también ocurre un error porque no podemos conectar un vértice
         * a sí mismo.
         */
        if (a == b)
            throw new IllegalArgumentException("A no puede ser igual a B");

        Vertice uno = busca(a);
        Vertice dos = busca(b);

        /*
         * Si los vértices ya son vecinos (se puede reutilizar) también ocurrirá un
         * error; no permitiremos tratar de
         * conectar dos vértices ya conectados.
         */

        if (sonVecinos(a, b))
            throw new IllegalArgumentException("Los vertices ya están conectados");

        dos.vecinos.agrega(uno);
        uno.vecinos.agrega(dos);

        aristas++;
    }

    /**
     * Desconecta dos elementos de la gráfica. Los elementos deben estar en la
     * gráfica y estar conectados entre ellos.
     * @param a el primer elemento a desconectar.
     * @param b el segundo elemento a desconectar.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     * @throws IllegalArgumentException si a o b no están conectados.
     */
    public void desconecta(T a, T b) {
        // Aquí va su código.

        /*
         * Se buscan los vértices correspondientes a los elementos recibidos; si
         * alguno no está en la gráfica un error ocurre. Si los vértices no están
         * conectados (de nuevo podemos usar sonVecinos() un error ocurre.
         * Eliminamos el primer vértice de la lista de vecinos del segundo y
         * viceversa. Además decrementamos el contador interno de aristas.
         */

        if (a == b)
            throw new IllegalArgumentException("Un vertice no se puede conectar a sí mismo");

        Vertice vecino1 = busca(a);
        Vertice vecino2 = busca(b);

        if (!sonVecinos(a, b))
            throw new IllegalArgumentException("Los vertices no estan conectados");

        vecino2.vecinos.elimina(vecino1);
        vecino1.vecinos.elimina(vecino2);

        aristas--;
    }

    /**
     * Nos dice si el elemento está contenido en la gráfica.
     * @return <code>true</code> si el elemento está contenido en la gráfica,
     *         <code>false</code> en otro caso.
     */
    @Override public boolean contiene(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            return false;

        for (Vertice v : vertices)
            if (v.elemento.equals(elemento))
                return true;

        return false;
    }

    /**
     * Elimina un elemento de la gráfica. El elemento tiene que estar contenido
     * en la gráfica.
     * @param elemento el elemento a eliminar.
     * @throws NoSuchElementException si el elemento no está contenido en la
     *         gráfica.
     */
    @Override public void elimina(T elemento) {
        // Aquí va su código.
        if (elemento == null)
            throw new IllegalArgumentException("Elemento no valido");

        Vertice vertice = busca(elemento);
        vertices.elimina(vertice);

        for (Vertice vecino : vertice.vecinos) {
            vecino.vecinos.elimina(vertice);
            aristas--;
        }
    }

    /**
     * Nos dice si dos elementos de la gráfica están conectados. Los elementos
     * deben estar en la gráfica.
     * @param a el primer elemento.
     * @param b el segundo elemento.
     * @return <code>true</code> si a y b son vecinos, <code>false</code> en otro caso.
     * @throws NoSuchElementException si a o b no son elementos de la gráfica.
     */
    public boolean sonVecinos(T a, T b) {
        // Aquí va su código.
        /*
         * Se buscan los vértices correspondientes a los elementos recibidos; si
         * alguno no está en la gráfica un error ocurre. Se revisa que el primer
         * vértice esté en la lista de vecinos del segundo y viceversa.
         */
        Vertice uno = busca(a);
        Vertice dos = busca(b);

        return uno.vecinos.contiene(dos) && dos.vecinos.contiene(uno);
    }

    /**
     * Regresa el vértice correspondiente el elemento recibido.
     * @param elemento el elemento del que queremos el vértice.
     * @throws NoSuchElementException si elemento no es elemento de la gráfica.
     * @return el vértice correspondiente el elemento recibido.
     */
    public VerticeGrafica<T> vertice(T elemento) {
        // Aquí va su código.
        for (Vertice vertices : vertices)
            if (vertices.elemento.equals(elemento))
                return vertices;

        throw new NoSuchElementException("El elemento no es elemento de la gráfica");
    }

    /**
     * Define el color del vértice recibido.
     * @param vertice el vértice al que queremos definirle el color.
     * @param color el nuevo color del vértice.
     * @throws IllegalArgumentException si el vértice no es válido.
     */
    public void setColor(VerticeGrafica<T> vertice, Color color) {
        // Aquí va su código.

        /*
         * Hacemos una audición para tener el vértice como instancia de Vertice; si
         * el vértice no es instancia de Vertice (en Java usaremos el método
         * getClass() para verificarlo) un error ocurre.
         * Definimos el color del vértice como el color recibido por el método.
         */

        if (vertice.getClass() != Vertice.class)
            throw new IllegalArgumentException("El vertice no es valido");

        Vertice v = (Vertice) vertice;
        v.color = color;
    }

    /**
     * Nos dice si la gráfica es conexa.
     * @return <code>true</code> si la gráfica es conexa, <code>false</code> en
     *         otro caso.
     */
    public boolean esConexa() {
        // Aquí va su código
        Pila(vertices.getPrimero().elemento, e -> {}, new Cola<>());
        for (Vertice v : vertices)
            if (v.color != Color.NEGRO)
                return false;

        return true;
    }

    /**
     * Realiza la acción recibida en cada uno de los vértices de la gráfica, en
     * el orden en que fueron agregados.
     * @param accion la acción a realizar.
     */
    public void paraCadaVertice(AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        for(Vertice vertice: vertices)
            accion.actua(vertice);
          
        /*
         * realiza una acción sobre todos los vértices de la gráfica.
         * El método únicamente recorre la lista de vértices y ejecuta la acción
         * sobre cada uno de ellos.
         */
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por BFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void bfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        Pila(elemento, accion, new Cola<>());
        for (Vertice vertice : vertices)
            vertice.color = Color.NINGUNO;
    }

    private Vertice busca(T elemento) {

        for (Vertice vertice : vertices) {
            if (vertice.elemento.equals(elemento)) {
                return vertice;
            }
        }
        throw new NoSuchElementException("el elemento no está en la gráfica");
    }

    /**
     * Realiza la acción recibida en todos los vértices de la gráfica, en el
     * orden determinado por DFS, comenzando por el vértice correspondiente al
     * elemento recibido. Al terminar el método, todos los vértices tendrán
     * color {@link Color#NINGUNO}.
     * @param elemento el elemento sobre cuyo vértice queremos comenzar el
     *        recorrido.
     * @param accion la acción a realizar.
     * @throws NoSuchElementException si el elemento no está en la gráfica.
     */
    public void dfs(T elemento, AccionVerticeGrafica<T> accion) {
        // Aquí va su código.
        // 17.4.2 del libro DFS en gráficas explica el sig algoritmo
        Pila(elemento, accion, new Pila<>()); // creamos una pila en lugar de cola.
        for (Vertice vertice : vertices)
            vertice.color = Color.NINGUNO;
    }

    private void Pila(T elemento, AccionVerticeGrafica<T> accion, MeteSaca<Vertice> S) { // creamos una pila S

        Vertice verticeW = busca(elemento);
        for (Vertice v : vertices)
            v.color = Color.ROJO; // volvemos todos los vértice rojos

        verticeW.color = Color.NEGRO;// creamos el vertice w y lo metemos en S
        S.mete(verticeW);

        while (!S.esVacia()) { // mientras no sea vacía sacamos el vértice y guardamos en U
            Vertice verticeU = S.saca();
            accion.actua(verticeU);

            for (Vertice vecinoV : verticeU.vecinos) // para todo vecino v de u
                if (vecinoV.color == Color.ROJO) { // si v es rojo
                    vecinoV.color = Color.NEGRO; // lo coloreamos de negro
                    S.mete(vecinoV); // y lo metemos a S
                }
        }
    }

    /**
     * Nos dice si la gráfica es vacía.
     * @return <code>true</code> si la gráfica es vacía, <code>false</code> en
     *         otro caso.
     */
    @Override public boolean esVacia() {
        // Aquí va su código.
        return vertices.esVacia();
    }

    /**
     * Limpia la gráfica de vértices y aristas, dejándola vacía.
     */
    @Override public void limpia() {
        // Aquí va su código.
        vertices.limpia();
        aristas=0;
    }

    /**
     * Regresa una representación en cadena de la gráfica.
     * @return una representación en cadena de la gráfica.
     */
    @Override public String toString() {
        // Aquí va su código.
        String elementos="{";
        for(Vertice vertice: vertices)
            elementos+=vertice.elemento.toString()+", ";
        elementos+="}, ";

        String aristas="{";

        Lista<T> pasados=new Lista<>();
        for(Vertice vertice: vertices){
            for (Vertice vecino: vertice.vecinos) {
                if (!pasados.contiene(vecino.elemento))
                    aristas += "(" + vertice.elemento.toString() + ", " + vecino.elemento.toString() + "), ";
            }
            pasados.agrega(vertice.elemento);
        }
        aristas+="}";


        return elementos+aristas;
    }

    /**
     * Nos dice si la gráfica es igual al objeto recibido.
     * @param objeto el objeto con el que hay que comparar.
     * @return <code>true</code> si la gráfica es igual al objeto recibido;
     *         <code>false</code> en otro caso.
     */
    @Override public boolean equals(Object objeto) {
        if (objeto == null || getClass() != objeto.getClass())
            return false;
        @SuppressWarnings("unchecked") Grafica<T> grafica = (Grafica<T>)objeto;
        // Aquí va su código.
        if (getElementos() != grafica.getElementos() || aristas != grafica.aristas)
            return false;

        for (Vertice vertice : vertices) {

            Vertice verticeGrafica = grafica.busca(vertice.elemento);

            if (!grafica.contiene(vertice.elemento))
                return false;

            if (vertice.vecinos.getElementos() != verticeGrafica.vecinos.getElementos())
                return false;

            boolean tiene = false;

            for (Vertice vecino : vertice.vecinos)
                for (Vertice vecino2 : verticeGrafica.vecinos)
                    if (vecino.elemento.equals(vecino2.elemento))
                        tiene = true;

            if (!tiene)
                return false;
        }
        return true;
    }

    /**
     * Regresa un iterador para iterar la gráfica. La gráfica se itera en el
     * orden en que fueron agregados sus elementos.
     * @return un iterador para iterar la gráfica.
     */
    @Override public Iterator<T> iterator() {
        return new Iterador();
    }
}
