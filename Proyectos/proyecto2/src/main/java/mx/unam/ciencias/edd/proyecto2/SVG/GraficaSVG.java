package mx.unam.ciencias.edd.proyecto2.SVG;

import mx.unam.ciencias.edd.*;

/**
 * Clase para representar gráficas.
 */
public class GraficaSVG {

    /**
     * Clase interna privada para las coordenadas de los vértices.
     */
    private class VerticeGraficaSVG implements Comparable<VerticeGraficaSVG> {
        VerticeGrafica<Integer> vertice;
        double x;
        double y;
        
        /**
         * Creal el vertice para el cual se van a obtener las coordenadas.
         * @param vertice el vértice 
         * @param x Coordenada en x
         * @param y Coordenada en y
         */
        public VerticeGraficaSVG(VerticeGrafica<Integer> vertice, double x, double y) {
            this.vertice = vertice;
            this.x = x;
            this.y = y;
        }
        
        /**
         * Compara dos vértices
         */
        @Override public int compareTo(VerticeGraficaSVG vg) {
            return this.vertice.get().compareTo(vg.vertice.get());
        }
    
        /**
         * Determina si dos vértices son iguales.
         * @param vg el vértices a comparar 
         * @return true si son iguales, false en otro caso.
         */
        public boolean equals(VerticeGraficaSVG vg) {
            return vg.vertice.get().equals(this.vertice.get());
        }
    }

    /** El inicio del archivo SVG */
    private String inicio = "<?xml version = \'1.0\' encoding = \'utf-8\' ?>\n";
    /** Las aristas de la gráfica */
    private Lista<String> aristasGrafica;
    /** Los elementos de la gráfica */
    private Lista<Integer> elementosGrafica;
    /** La grafica que representaremos. */
    private Grafica<Integer> grafica;

    /**
     * llenaGrafica. Llena la gráfica y la devuelve.
     * @param elementosGrafica Los elementos de la gráfica
     * @param aristasGrafica Las aristas de la gráfica
     * @return grafica llena
     */
    public Grafica<Integer> llenaGrafica(Lista<Integer> elementosGrafica, Lista<String> aristasGrafica) {
        Grafica<Integer> grafica = new Grafica<Integer>();
        try {
            for (int elemento : elementosGrafica) {
                grafica.agrega(elemento);
            }
            for (int i = 0; i < aristasGrafica.getLongitud(); i++) {
                grafica.conecta(Integer.parseInt(aristasGrafica.get(i).substring(0,1)),Integer.parseInt(aristasGrafica.get(i).substring(0,1)));    
            }
        } catch (NumberFormatException nfe) {
            System.err.println("Ha ocurrido un error");
            System.exit(1);
        }
        return grafica;
    }

    /**
     * Obtiene los vértices de la gráfica.
     * @param grafica La gráfica de la cual de van a obtener los vértices
     * @param radioGrafica El radio de la gráfica
     * @param radio el radio del vértice
     * @param x Coordenada en x
     * @param y Coordenada en x
     * @return Los vértices y aristas de la gráfica.
     */
    public String getVerticesGrafica(Grafica<Integer> grafica, double radioGrafica, int radio, double x, double y) {
        String vertices = "";
        String aristas = "";
        double angulo = Math.toRadians(360 / grafica.getElementos());
        double anguloIzquierdo = 0;
        double xIzquierdo;
        double yIzquierdo;
        int indice = 0;
        VerticeGraficaSVG coordenadaIzquierda;
        VerticeGrafica<Integer> verticeIzquierdo = null;
        VerticeGraficaSVG[] coordenadas = new VerticeGraficaSVG[grafica.getElementos()];
        Arreglos arreglo = null;

        for (int vertice : grafica) {
            xIzquierdo = (radioGrafica) * (Math.cos(anguloIzquierdo));
            yIzquierdo = (radioGrafica) * (Math.sin(anguloIzquierdo));
            vertices +=  verticeGraficaElemento(vertice, x + xIzquierdo, y + yIzquierdo, radio);
            verticeIzquierdo = grafica.vertice(vertice);
            coordenadaIzquierda = new VerticeGraficaSVG(verticeIzquierdo, x + xIzquierdo, y + yIzquierdo);
            coordenadas[indice] = coordenadaIzquierda;
            anguloIzquierdo += angulo;
            indice += 1;
        }

        arreglo.quickSort(coordenadas);
        for(VerticeGraficaSVG v : coordenadas) {
            for (VerticeGrafica<Integer> vecino : v.vertice.vecinos()) {
                coordenadaIzquierda = new VerticeGraficaSVG(vecino, 0, 0);
                coordenadaIzquierda = coordenadas[arreglo.busquedaBinaria(coordenadas, coordenadaIzquierda)];
                aristas += aristaGrafica(v.x, v.y, coordenadaIzquierda.x, coordenadaIzquierda.y); 
            }
        }
        return aristas + vertices;
        
    }
    /** El elemento del vértice */
    public String elemento(int n, double x, double y, String elemento) {
		return "<text x='"+ x +"' y='"+ y +"' font-size='20' "+ elemento +">"+ n +"</text>\n";
	}

    /** Las aristas de la gráficas */
    public String aristaGrafica(double x1, double y1, double x2, double y2) {
        return "<line x1='"+ x1 +"' y1='"+ y1 +"' x2='"+ x2 +"' y2='"+ y2 +"' stroke='blue' stroke-width='2'/>\n";
    }

    /** El vértice de la gráfica */
    public String verticeGrafica(double radio, double x, double y) {
		return "<circle cx='"+ x +"' cy='"+ y +"' r='"+ radio +"' "+ "fill='white'" +" stroke='black' stroke-width='1'/>\n";
	}

    /** El vértice de la gráfica con el elemento */
    public String verticeGraficaElemento(int n, double x, double y, int radio) {
		return verticeGrafica(radio, x, y) + elemento(n, x, y+5, "text-anchor='middle' fill='black'");
	}

    /**
     * maximoEnGrafica. Obtiene el máximo de la gráficas.
     * @param grafica La gráfica de la cual se va a obtener el máximo
     * @return El elemento máximo.
     */
    private int maximoEnGrafica(Grafica<Integer> grafica) {
        int maximo = 0;
        for (int vertice : grafica) {
            maximo = vertice;
            break;
        }

        for (int elemento : grafica) {
            if (elemento > maximo) {
                maximo = elemento;
            }
        }
        return maximo;
    }

    /**
     * graficaEnSVG. Regresa una representación de la gráfica en SVG
     * @param grafica La gráfica que se va a representar.
     * @return La gráfica en SVG.
     */
    public String graficaEnSVG(Grafica<Integer> grafica) {
        String graficaSVG;
        int elemento = 15;
        int radio;
        int perimetro;
        int maximo;
        double radioGrafica;
        double ancho;
        double altura;
        maximo = maximoEnGrafica(grafica);
        radio = (longitudNumeros(maximo) * 10 + elemento * 2) / 2;
        perimetro = grafica.getElementos()*radio*3;
        radioGrafica = perimetro / 3.1416;
        ancho = altura = radioGrafica * 2 + radio*2.0*2.0;
        graficaSVG = getVerticesGrafica(grafica, radioGrafica, radio, ancho / 2, altura / 2);
        return inicio + "<svg width= '" + ancho + "' height='" + altura + "'> \n" + graficaSVG + "</svg>";
    }

    /**
     * longitudNumero. Obtiene el número de dígitos de un número
     * @param n El número
     * @return El número de dígitos de ún número.
     */
    public int longitudNumeros(int n) {
        int i = 1;
        while (n >= 10) {
            n /= 10;
            i++;
        }
        return i;
    }
}