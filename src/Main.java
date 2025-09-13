import Clases.AVL;
import Clases.ArbolBinarioBusqueda;
import Clases.IArbolBusqueda;

public class Main {
    public static void main(String[] args) {
        IArbolBusqueda<Integer> arbolDeEnteros = new ArbolBinarioBusqueda<>();
        arbolDeEnteros.insertar(80);
        arbolDeEnteros.insertar(30);
        arbolDeEnteros.insertar(20);
        arbolDeEnteros.insertar(25);
        arbolDeEnteros.insertar(65);
        arbolDeEnteros.insertar(74);
        arbolDeEnteros.insertar(76);
        System.out.println(arbolDeEnteros);
        System.out.println("Nodos Hoja: " + arbolDeEnteros.cuantosNodosHojas());
    }
}