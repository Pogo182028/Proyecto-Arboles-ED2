package Clases;

import Excepciones.ExcepcionDatoNoExiste;
import Excepciones.ExcepcionDatoYaExiste;

import java.util.*;

public class ArbolBinarioBusqueda<T extends Comparable<T>> implements IArbolBusqueda<T> {

    protected NodoBinario<T> raiz;

    @Override
    public void insertar(T datoAInsertar) throws ExcepcionDatoYaExiste {
        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(datoAInsertar);
            return;
        }

        NodoBinario<T> nodoAnterior = NodoBinario.nodoVacio();
        NodoBinario<T> nodoAuxiliar = this.raiz;

        while (nodoAuxiliar != null) {
            T datoNodoAux = nodoAuxiliar.getDato();
            nodoAnterior = nodoAuxiliar;

            if (datoAInsertar.compareTo(datoNodoAux) < 0) {
                nodoAuxiliar = nodoAuxiliar.getHijoIzquierdo();
            } else if (datoAInsertar.compareTo(datoNodoAux) > 0) {
                nodoAuxiliar = nodoAuxiliar.getHijoDerecho();
            } else {
                throw new ExcepcionDatoYaExiste("Dato ya existe");
            }
        }

        NodoBinario<T> nodoNuevo = new NodoBinario<>(datoAInsertar);
        if (datoAInsertar.compareTo(nodoNuevo.getDato()) < 0) {
            nodoAnterior.setHijoIzquierdo(nodoNuevo);
        } else {
            nodoAnterior.setHijoDerecho(nodoNuevo);
        }
    }

    @Override
    public void eliminar(T dato) throws ExcepcionDatoNoExiste {

    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public T buscar(T dato) {
        NodoBinario<T> nodoAuxiliar = this.raiz;
        while (nodoAuxiliar != null) {
            T datoDelNodoAux = nodoAuxiliar.getDato();

            if (dato.compareTo(datoDelNodoAux) < 0) {
                nodoAuxiliar = nodoAuxiliar.getHijoIzquierdo();
            } else if (dato.compareTo(datoDelNodoAux) > 0) {
                nodoAuxiliar = nodoAuxiliar.getHijoDerecho();
            } else {
                return datoDelNodoAux;
            }
        }
        return null;
    }

    @Override
    public boolean esArbolVacio() {
        return NodoBinario.esNodoVacio(this.raiz);
    }

    @Override
    public boolean contiene(T dato) {
        return this.buscar(dato) != null;
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

    /* PreOrden Iterativo ---------------------------------------------------------
    * Se define una listaDeRecorrido generica
    * Cuando el arbol no sea vacio
    * Se define una pila de nodos binarios
    * PASO INICIAL:
      Codigo abajo:
    */
    @Override
    public List<T> recorridoEnInOrden() {
        return List.of();
    } // Fin Del Recorrido en InOrden iterativo ----------------------------------

    /* PreOrden Iterativo ---------------------------------------------------------
    * Se define una listaDeRecorrido generica
    * Cuando el arbol no sea vacio
    * Se define una pila de nodos binarios
    * PASO INICIAL: agregamos la raiz a la pila
    * En un proceso iterativo mientras la pila
      no sea vacia:
      * Sacamos el nodo que toca sacar de la otra pila y lo asignamos
        al nodoEnTurno
      * Agregamos a la lista del recorrido el dato del nodoEnTurno
      * Si el hijoDerecho del nodoEnTurno no es vacio lo agrega a la pila
      * Si el hijoIzquierdo del nodoEnTurno no es vacio lo agrega a la pila
      Codigo abajo:
    */
    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> listaDeRecorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Stack<NodoBinario<T>> pilaDeNodos = new Stack<>();
            pilaDeNodos.push(this.raiz);
            do {
                NodoBinario<T> nodoEnTurno = pilaDeNodos.pop();
                listaDeRecorrido.add(nodoEnTurno.getDato());

                if (!nodoEnTurno.esVacioHijoDerecho()) {
                    pilaDeNodos.push(nodoEnTurno.getHijoDerecho());
                } else if (!nodoEnTurno.esVacioHijoIzquierdo()) {
                    pilaDeNodos.push(nodoEnTurno.getHijoIzquierdo());
                }
            } while (!pilaDeNodos.isEmpty());
        }
        return listaDeRecorrido;
    } // Fin Del Recorrido en PreOrden iterativo ---------------------------------


    /* PreOrden Iterativo ---------------------------------------------------------
    * Se define una listaDeRecorrido generica
    * Cuando el arbol no sea vacio
    * Se define una pila de nodos binarios
    * PASO INICIAL:
      Codigo abajo:
    */
    @Override
    public List<T> recorridoEnPostOrden() {
        return List.of();
    } // Fin del Recorrido en PostOrden Iterativo --------------------------------

    /* Recorrido por Niveles ----------------------------------------------------------
     * Se define una listaDeRecorrido generica
     * Cuando el arbol no sea vacio
     * Se define una cola de nodos binarios
     * PASO INICIAL: Agregamos la raiz a la cola
     * En un proceso iterativo mientras la cola no sea vacia
       * Sacamos el nodo que tocda sacar y lo asignamos al nodoEnTurno
       * Agregamos el dato del nodoEnTurno a la listaDeRecorrido
       * Si el hijoIzquierdo del nodoEnTurno no es vacio se agrega a la cola
       * Si el hijoDerecho del nodoEnTurno no es vacio se agrega a la cola
     Codigo abajo:
     */
    @Override
    public List<T> recorridoPorNiveles() {
        List<T> listaDeRecorrido = new ArrayList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoBinario<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(this.raiz);
            do {
                NodoBinario<T> nodoEnTurno = colaDeNodos.poll();
                listaDeRecorrido.add(nodoEnTurno.getDato());

                if (!nodoEnTurno.esVacioHijoIzquierdo()) {
                    colaDeNodos.offer(nodoEnTurno.getHijoIzquierdo());
                }
                if (!nodoEnTurno.esVacioHijoDerecho()) {
                    colaDeNodos.offer(nodoEnTurno.getHijoDerecho());
                }
            } while (!colaDeNodos.isEmpty());
        }
        return listaDeRecorrido;
    } // Fin del Recorrido por Niveles -------------------------------------------------


    // Representacion del arbol
    @Override
    public String toString() {
        return generarCadenaDeArbol(this.raiz, "", true);
    }

    private String generarCadenaDeArbol(NodoBinario<T> nodo, String prefijo, boolean esUltimo) {
        if (NodoBinario.esNodoVacio(nodo)) {
            return prefijo + (esUltimo ? "└── " : "├── ") + "∅\n";
        }

        StringBuilder sb = new StringBuilder();
        sb.append(prefijo).append(esUltimo ? "└── " : "├── ").append(nodo.getDato()).append("\n");

        // Determinar si hay hijos para seguir
        boolean tieneHijoIzq = !NodoBinario.esNodoVacio(nodo.getHijoIzquierdo());
        boolean tieneHijoDer = !NodoBinario.esNodoVacio(nodo.getHijoDerecho());

        if (tieneHijoIzq || tieneHijoDer) {
            sb.append(generarCadenaDeArbol(nodo.getHijoIzquierdo(), prefijo + (esUltimo ? "    " : "│   "), false));
            sb.append(generarCadenaDeArbol(nodo.getHijoDerecho(), prefijo + (esUltimo ? "    " : "│   "), true));
        }

        return sb.toString();
    }
}
