package mx.unam.ciencias.edd.proyecto1;
import java.text.Normalizer;

/**
 * 
 * Clase que modifica el texto/archivo de entrada.
 * Requisitos del proyecto:
 * El archivo se ordena por líneas no por palabras
 * Las líneas vacías se ordenan antes que las no vacías
 * Que los espacios y carácteres distintos a letras son ignorados al ordenar pero preservados al imprimir.
 * Que los acentos, diéresis y eñes se toman como vocales y la letra n respectivamente.
 * 
 */
public class TextoPlano implements Comparable<TextoPlano>{

    private String textoOriginal;
    private String textoPlano;

    public TextoPlano(String s) {
        textoOriginal = s;
        textoPlano = TextoModificado();
    }

    private String TextoModificado(){
        //string a mínusculas, quita los espacios vacios
        String archivoModificado = Normalizer.normalize(textoOriginal.toLowerCase().trim() 
        //Quita todos los caracteres que no son letras o dígitos.
                                                 .replaceAll("[^a-zA-Z0-9]", ""),
        //Referencia que utilice para NFD https://docs.oracle.com/javase/tutorial/i18n/text/normalizerapi.html
                                                 Normalizer.Form.NFD);
        char[] out = new char[archivoModificado.length()];
        int j = 0;
        for (int i = 0, n = archivoModificado.length(); i < n; ++i) {
            char e = archivoModificado.charAt(i);
        //unicode
            if (e <= '\u007F') out[j++] = e;
        }
        return new String(out);
    }

    @Override
    public int compareTo(TextoPlano other) {
        return this.textoPlano.compareTo(other.textoPlano);
    }

    // Método que devuelve el texto modificado.

    public String getTextoModificado() {
        return textoPlano;
    }


    // Representación en cadena que devuelve el texto original.
    @Override
    public String toString() {
        return textoOriginal;
    }
}
