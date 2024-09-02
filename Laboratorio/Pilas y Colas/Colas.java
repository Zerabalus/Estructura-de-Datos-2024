import java.util.Queue;
import java.util.ArrayDeque;

public class Colas{
    public static void main(String[] args){
        /**
         * ERROR¡¡
         * Porque una cola es una interfaz¡
         */
        //Queue<String> e = new Queue<>();
        Queue<String> e = new ArrayDeque<>();
        e.add("Edrei");
        e.add("Alexis");
        e.add("Diego");
        System.out.println(e);
        e.poll();
        System.out.println(e);
    }    
}