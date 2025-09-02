package Clases;

import Excepciones.ExcepcionDatoNoExiste;
import Excepciones.ExcepcionDatoYaExiste;

import java.util.*;

public class ArbolBinarioBusqueda<T extends Comparable<T>> implements IArbolBusqueda<T> {

    protected NodoBinario<T> raiz;

    public ArbolBinarioBusqueda() {
    }

    @Override
    public void insertar(T datoAInsertar) throws ExcepcionDatoYaExiste {
        if (datoAInsertar == null) {
            throw new IllegalArgumentException("Dato insertado no puede ser nulo");
        }

        if (this.esArbolVacio()) {
            this.raiz = new NodoBinario<>(datoAInsertar);
            return;
        }

        NodoBinario<T> nodoAnt = NodoBinario.nodoVacio();
        NodoBinario<T> nodoAux = this.raiz;
        do {
            T datoDelNodoAuxiliar = nodoAux.getDato();
            nodoAnt = nodoAux;
            if (datoAInsertar.compareTo(datoDelNodoAuxiliar) < 0) {
                nodoAux = nodoAux.getHijoIzquierdo();
            } else if (datoAInsertar.compareTo(datoDelNodoAuxiliar) > 0) {
                nodoAux = nodoAux.getHijoDerecho();
            } else {
                throw new ExcepcionDatoYaExiste("El dato ya existe");
            }
        } while (!NodoBinario.esNodoVacio(nodoAux));

        NodoBinario<T> nuevoNodo = new NodoBinario<>(datoAInsertar);
        T datoDelNodoAnterior = nodoAnt.getDato();
        if (datoAInsertar.compareTo(datoDelNodoAnterior) < 0) {
            nodoAnt.setHijoIzquierdo(nuevoNodo);
        } else {
            nodoAnt.setHijoDerecho(nuevoNodo);
        }
    }

    /* Eliminar en un ABB
     * El metodo eliminar en un ABB consta de tres casos:
     * Caso 1: Cuando el dato a eliminar esta en una hoja
     * Caso 2: Cuando el dato a eliminar esta en un nodo con 1 solo hijo no vacio
     * Caso 3: Cuando el dato a eliminar esta en un nodo con 2 hijos no vacios
     * Primeramente hay que buscar el nodo que queremos eliminar para poder luego
     * verificar con que caso */
    @Override
    public void eliminar(T datoAEliminar) throws ExcepcionDatoNoExiste {
        if (datoAEliminar == null) {
            throw new IllegalArgumentException("Dato a eliminar no puede ser nulo");
        }
        this.raiz = this.eliminar(this.raiz, datoAEliminar);
    }

    private NodoBinario<T> eliminar(NodoBinario<T> nodoEnTurno, T datoAEliminar)
            throws ExcepcionDatoNoExiste {
        if (NodoBinario.esNodoVacio(nodoEnTurno)) {
            throw new ExcepcionDatoNoExiste("Su dato no existe");
        }

        /* Esta parte busca el dato a eliminar y a la vez actualizara el nodo padre
        * de donde nos encontramos, aunque no haya cambiado */
        T datoDelNodoEnTurno = nodoEnTurno.getDato();
        if (datoAEliminar.compareTo(datoDelNodoEnTurno) < 0) {
            NodoBinario<T> supuestoNuevoHijoIzq =
                    this.eliminar(nodoEnTurno.getHijoIzquierdo(), datoAEliminar);
            nodoEnTurno.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return nodoEnTurno;
        }

        if (datoAEliminar.compareTo(datoDelNodoEnTurno) > 0) {
            NodoBinario<T> supuestoNuevoHijoDer =
                    this.eliminar(nodoEnTurno.getHijoDerecho(), datoAEliminar);
            nodoEnTurno.setHijoDerecho(supuestoNuevoHijoDer);
            return nodoEnTurno;
        }

        /* Si llego aca sabemos el nodo en turno tiene el dato a eliminar */
        // Caso 1
        if (nodoEnTurno.esHoja()) {
            return NodoBinario.nodoVacio();
        }

        // Caso 2. a
        if (!nodoEnTurno.esVacioHijoIzquierdo() &&
                nodoEnTurno.esVacioHijoDerecho()) {
            NodoBinario<T> nodoARetornar = nodoEnTurno.getHijoIzquierdo();
            nodoEnTurno.setHijoIzquierdo(NodoBinario.nodoVacio());
            return nodoARetornar;
        }

        // Caso 2. b
        if (nodoEnTurno.esVacioHijoIzquierdo() &&
                !nodoEnTurno.esVacioHijoDerecho()) {
            NodoBinario<T> nodoARetornar = nodoEnTurno.getHijoDerecho();
            nodoEnTurno.setHijoDerecho(NodoBinario.nodoVacio());
            return nodoARetornar;
        }

        // Caso 3
        T reemplazo = this.buscarSucesorInOrden(nodoEnTurno.getHijoDerecho());
        NodoBinario<T> supuestoNuevoHijoDerecho = this.eliminar(nodoEnTurno.getHijoDerecho(),
                reemplazo);
        nodoEnTurno.setHijoDerecho(supuestoNuevoHijoDerecho);
        nodoEnTurno.setDato(reemplazo);
        return nodoEnTurno;
    }

    protected T buscarSucesorInOrden(NodoBinario<T> nodoAuxiliar) {
        while (!nodoAuxiliar.esVacioHijoIzquierdo()) {
            nodoAuxiliar = nodoAuxiliar.getHijoIzquierdo();
        }
        return nodoAuxiliar.getDato();
    }

    @Override
    public void vaciar() {
        this.raiz = NodoBinario.nodoVacio();
    }

    @Override
    public T buscar(T datoABuscar) {
        NodoBinario<T> nodoAux = this.raiz;
        do {
            T datoDelNodoAuxiliar = nodoAux.getDato();

            if (datoABuscar.compareTo(datoDelNodoAuxiliar) < 0) {
                nodoAux = nodoAux.getHijoIzquierdo();
            } else if (datoABuscar.compareTo(datoDelNodoAuxiliar) > 0) {
                nodoAux = nodoAux.getHijoDerecho();
            } else {
                return datoDelNodoAuxiliar;
            }
        } while (!NodoBinario.esNodoVacio(nodoAux));
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

    /* Hacer este metodo */
    public int sizeIt() {
        return 0;
    }

    @Override
    public int size() {
        return size(this.raiz);
    }

    private int size(NodoBinario<T> nodoEnTurno) {
        if (NodoBinario.esNodoVacio(nodoEnTurno)) {
            return 0;
        }

        int sizeXIzq = this.size(nodoEnTurno.getHijoIzquierdo());
        int sizeXDer = this.size(nodoEnTurno.getHijoDerecho());
        return sizeXIzq + sizeXDer + 1;
    }

    /* Hacer este metodo */
    public int alturaIt() {
        return 0;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    protected int altura(NodoBinario<T> nodoEnTurno) {
        if (NodoBinario.esNodoVacio(nodoEnTurno)) {
            return 0;
        }
        int alturaXIzq = this.altura(nodoEnTurno.getHijoIzquierdo());
        int alturaXDer = this.altura(nodoEnTurno.getHijoDerecho());
        if (alturaXIzq > alturaXDer) {
            return alturaXIzq + 1;
        }
        return alturaXDer + 1;
    }

    /* Hacer este metodo */
    @Override
    public int nivel(T dato) {
        return 0;
    }

    /* InOrden Iterativo ---------------------------------------------------------
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
        a un nodoEnTurno
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
                }
                if (!nodoEnTurno.esVacioHijoIzquierdo()) {
                    pilaDeNodos.push(nodoEnTurno.getHijoIzquierdo());
                }
            } while (!pilaDeNodos.isEmpty());
        }
        return listaDeRecorrido;
    } // Fin Del Recorrido en PreOrden iterativo ---------------------------------


    /* PostOrden Iterativo ---------------------------------------------------------
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
