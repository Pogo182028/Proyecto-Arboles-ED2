package Clases;

import java.util.*;

public class NodoMVias<T> {
    private List<T> listaDeDatos;
    private List<NodoMVias<T>> listaDeHijos;

    public static NodoMVias nodoVacio() {
        return null;
    }

    public static Object datoVacio() {
        return null;
    }

    public NodoMVias(int orden) {
        this.listaDeDatos = new ArrayList<T>();
        this.listaDeHijos = new ArrayList<NodoMVias<T>>();
        for (int i = 0; i < orden - 1; i++) {
            this.listaDeDatos.add((T)NodoMVias.datoVacio());
            this.listaDeHijos.add(NodoMVias.nodoVacio());
        }
        this.listaDeHijos.add(NodoMVias.nodoVacio());
    }

    public NodoMVias(int orden, T primerDato) {
        this(orden);
        this.listaDeDatos.set(0, primerDato);
    }

    public static boolean esNodoVacio(NodoMVias nodo) {
        return nodo == null;
    }

    public boolean esHijoVacio(int posicion) {
        return listaDeHijos.get(posicion) == NodoMVias.nodoVacio();
    }

    public boolean esDatoVacio(int posicion) {
        return listaDeDatos.get(posicion) == NodoMVias.nodoVacio();
    }

    public boolean esHoja() {
        for (int i = 0; i < listaDeHijos.size(); i++) {
            if (!esHijoVacio(i)) {
                return false;
            }
        }

        return true;
    }

    public void setHijo(int posicion, NodoMVias<T> nodo) {
        listaDeHijos.set(posicion, nodo);
    }

    public NodoMVias<T> getHijo(int posicion) {
        return listaDeHijos.get(posicion);
    }

    public void setDato(int posicion, T dato) {
        listaDeDatos.set(posicion, dato);
    }

    public T getDato(int posicion) {
        return listaDeDatos.get(posicion);
    }

    public int nroDeDatosNoVacios() {
        int cantidadDeDatos = 0;
        boolean encontreDatoVacio = false;
        for (int i = 0; i < listaDeDatos.size() &&
                !encontreDatoVacio; i++) {
            if (!esDatoVacio(i)) {
                cantidadDeDatos++;
            } else {
                encontreDatoVacio = true;
            }
        }
        return cantidadDeDatos;
    }

    public boolean estanDatosLlenos() {
        return nroDeDatosNoVacios() == listaDeDatos.size();
    }

    public String getDatosComoCadena() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (int i = 0; i < this.nroDeDatosNoVacios(); i++) {
            sb.append(this.getDato(i));
            if (i < this.nroDeDatosNoVacios() - 1) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }
}
