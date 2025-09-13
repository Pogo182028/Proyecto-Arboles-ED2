package Clases;
import Excepciones.ExcepcionDatoYaExiste;
import Excepciones.ExcepcionDatoNoExiste;
import java.util.List;

public interface IArbolBusqueda<T extends Comparable<T>> {
    void insertar(T dato) throws ExcepcionDatoYaExiste;
    void eliminar(T dato) throws ExcepcionDatoNoExiste;
    void vaciar();

    T buscar(T dato);
    boolean esArbolVacio();
    boolean contiene(T dato);

    int size();
    int altura();
    int nivel(T dato);
    List<T> recorridoEnInOrden();
    List<T> recorridoEnPreOrden();
    List<T> recorridoEnPostOrden();
    List<T> recorridoPorNiveles();
    // Ejercicios
    public int cuantosNodosHojas();
}
