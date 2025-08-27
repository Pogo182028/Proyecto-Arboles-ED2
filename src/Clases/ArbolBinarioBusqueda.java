package Clases;

import Excepciones.ExcepcionDatoNoExiste;
import Excepciones.ExcepcionDatoYaExiste;

import java.util.List;

public class ArbolBinarioBusqueda<T extends Comparable<T>> implements IArbolBusqueda<T> {

    protected NodoBinario<T> raiz;

    @Override
    public void insertar(T dato) throws ExcepcionDatoYaExiste {

    }

    @Override
    public void eliminar(T dato) throws ExcepcionDatoNoExiste {

    }

    @Override
    public void vaciar() {

    }

    @Override
    public T buscar(T dato) {
        return null;
    }

    @Override
    public boolean esArbolVacio() {
        return false;
    }

    @Override
    public boolean contiene(T dato) {
        return false;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public int altura() {
        return 0;
    }

    @Override
    public int nivel(T dato) {
        return 0;
    }

    @Override
    public List<T> recorridoEnInOrden() {
        return List.of();
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        return List.of();
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        return List.of();
    }

    @Override
    public List<T> recorridoPorNiveles() {
        return List.of();
    }
}
