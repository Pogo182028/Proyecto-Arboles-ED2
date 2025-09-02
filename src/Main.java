import Clases.ArbolBinarioBusqueda;
import Clases.IArbolBusqueda;

public class Main {
    public static void main(String[] args) {
        IArbolBusqueda<Integer> arbolDeEnteros = new ArbolBinarioBusqueda<>();
        arbolDeEnteros.insertar(60);
        arbolDeEnteros.insertar(50);
        arbolDeEnteros.insertar(56);
        arbolDeEnteros.insertar(80);
        arbolDeEnteros.insertar(30);
        arbolDeEnteros.insertar(20);
        arbolDeEnteros.insertar(25);
        arbolDeEnteros.insertar(22);
        arbolDeEnteros.insertar(27);
        arbolDeEnteros.insertar(26);
        arbolDeEnteros.insertar(70);
        arbolDeEnteros.insertar(65);
        arbolDeEnteros.insertar(74);
        arbolDeEnteros.insertar(76);

        System.out.println(arbolDeEnteros);
        System.out.println("\n\n");
        System.out.println("Recorrido por Niveles: " + arbolDeEnteros.recorridoPorNiveles());
        System.out.println("Recorrido en PreOrden: " + arbolDeEnteros.recorridoEnPreOrden());
        System.out.println("Size: " + arbolDeEnteros.size());
        System.out.println("Altura: " + arbolDeEnteros.altura());

        System.out.println("Eliminando el 50");
        arbolDeEnteros.eliminar(50);
        System.out.println(arbolDeEnteros);
        System.out.println("Eliminando el 80");
        arbolDeEnteros.eliminar(80);
        System.out.println(arbolDeEnteros);
    }
}