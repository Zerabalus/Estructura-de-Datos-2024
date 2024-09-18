import java.util.Stack;

public class Pilas{
    public static void main(String[] args){
        /**
         * Creamos una pila de estudiantes.
         */
        Stack<String> estudiantes = new Stack<>();
        /**
         * Agregamos elementos a la pila.
         */ 
        estudiantes.push("Armando");
        estudiantes.push("Dona");
        estudiantes.push("Memo");
        estudiantes.push("Santiago");
        estudiantes.push("Adrián");
        /**
         * Imprimimos la pila.
         */
        System.out.println(estudiantes);
        /**
         * Imprimimos el último elemento.
         */
        System.out.println("El último elemento de la pila es: " + estudiantes.peek());
        /**
         * Eliminamos un elemento.
         */
        System.out.println("Eliminamos el último elemento: " + estudiantes.pop());
        System.out.println(estudiantes);
        /**
         * Hacemos una búsqueda
         */
        System.out.println("Buscamos a Dona "+ estudiantes.search("Santiago"));
    }

}