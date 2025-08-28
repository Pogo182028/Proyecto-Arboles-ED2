import Clases.ArbolBinarioBusqueda;

public class Main {
    public static void main(String[] args) {
        ArbolBinarioBusqueda<Integer> arbolDeEnteros = new ArbolBinarioBusqueda<>();
        arbolDeEnteros.insertar(49);
        arbolDeEnteros.insertar(25);
        arbolDeEnteros.insertar(10);
        arbolDeEnteros.insertar(15);
        arbolDeEnteros.insertar(60);
        arbolDeEnteros.insertar(50);
        arbolDeEnteros.insertar(100);

        System.out.println(arbolDeEnteros);
        System.out.println("\n\n");
        System.out.println(arbolDeEnteros.recorridoPorNiveles());
        System.out.println(arbolDeEnteros.recorridoEnPreOrden());
    }
}