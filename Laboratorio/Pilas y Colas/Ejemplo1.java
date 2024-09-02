import java.util.Stack;
import java.lang.StringBuilder;
/**
 * Clase Ejemplo 1.
 * Damos la reversa de una cadena implementando una pila.
 */
public class Ejemplo1{
    /**
     * Método que nos da la reversa de una cadena.
     * Hacemos uso de pilas.
     * @param cadena la cadena a la que aplicaremos la reversa.
     */
    public static String reversa(String cadena){
        Stack<Character> p = new Stack<>();
        /**
         * Agregamos caracter a caracter en la pila.
         */
        for(int i = 0; i < cadena.length(); i++){
            p.push(cadena.charAt(i));
        }
        /**
         * Para que nos imprima la cadena completa
         * y NO caracter por caracter la guardamos.
         */
        StringBuilder sb = new StringBuilder();
        //while(p != null){ esto causa una excepcion¡
        while(!p.isEmpty()){
            sb.append(p.pop());
        }
        return sb.toString();
    }
    /**
     * Método main
     * @param args
     */
    public static void main(String[] args){
        String c = "miriam";
        Ejemplo1 e1 = new Ejemplo1();
        System.out.println(e1.reversa(c));
    }
}