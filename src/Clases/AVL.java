package Clases;

import Excepciones.ExcepcionDatoNoExiste;
import Excepciones.ExcepcionDatoYaExiste;

public class AVL<T extends Comparable<T>> extends ArbolBinarioBusqueda<T> {
    private final static int RANGO_LIMITE = 1;

    @Override
    public void insertar(T datoAInsertar) throws ExcepcionDatoYaExiste {
        if (datoAInsertar == null) {
            throw new IllegalArgumentException("Dato insertado no puede ser nulo");
        }

        super.raiz = this.insertar(this.raiz, datoAInsertar);
    }

    private NodoBinario<T> insertar(NodoBinario<T> nodoEnTurno, T datoAInsertar)
            throws ExcepcionDatoYaExiste {
        if (NodoBinario.esNodoVacio(nodoEnTurno)) { // Caso base
            return new NodoBinario<>(datoAInsertar);
        }

        T datoDelNodoEnTurno = nodoEnTurno.getDato();
        if (datoAInsertar.compareTo(datoDelNodoEnTurno) < 0) {
            NodoBinario<T> supuestoNuevoHijoIzq = this.insertar(
                    nodoEnTurno.getHijoIzquierdo(), datoAInsertar);
            nodoEnTurno.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return this.balancear(nodoEnTurno);
        }
        if (datoAInsertar.compareTo(datoDelNodoEnTurno) > 0) {
            NodoBinario<T> supuestoNuevoHijoDer = this.insertar(
                    nodoEnTurno.getHijoDerecho(), datoAInsertar);
            nodoEnTurno.setHijoDerecho(supuestoNuevoHijoDer);
            return this.balancear(nodoEnTurno);
        }
        throw new ExcepcionDatoYaExiste("Dato ya existe");
    }

    /* Balancear
     * Primeramente necesitamos sabe la altura por izquierda y por derecha del nodoEnTurno
     * luego sacaremos su diferencia
     * Rotacion Simple:
     * Rotacion Simple por Izquierda
     * Rotacion Simple por Derecha
     * Rotacion Doble:
     * Rotacion Doble por Izquierda
     * Rotacion Doble por Derecha
     */
    private NodoBinario<T> balancear(NodoBinario<T> nodoEnTurno) {
        int alturaXIzq = super.altura(nodoEnTurno.getHijoIzquierdo());
        int alturaXDer = super.altura(nodoEnTurno.getHijoDerecho());
        int diferencia = alturaXIzq - alturaXDer;
        if (diferencia > RANGO_LIMITE) {
            /* El lado izquierdo es muy largo
             * por ende hay que rotar por derecha
             * ahora hay que verificar si es rotacion simple o doble
             * para ello, primeramente guardamos el hijoIzq,
             * ahora calculamos nuevamente la altura
             * SI LA ALTURA POR DERECHA ES MAYOR -> Rotacion Doble por Derecha
             * CASO CONTRARIO -> Rotacion Simpole por Derecha */
            NodoBinario<T> hijoIzqDelNodoEnTurno = nodoEnTurno.getHijoIzquierdo();
            alturaXIzq = super.altura(hijoIzqDelNodoEnTurno.getHijoIzquierdo());
            alturaXDer = super.altura(hijoIzqDelNodoEnTurno.getHijoDerecho());
            if (alturaXDer > alturaXIzq) {
                return rotacionDoblePorDerecha(nodoEnTurno);
            }
            return rotacionSimplePorDerecha(nodoEnTurno);
        } else if (diferencia < -RANGO_LIMITE) {
            /* El lado derecho es muy largo
             * por ende hay que rotar por izquierda
             * ahora hay que verificar si es rotacion simple o doble
             * para ello, primeramente guardamos el hijoDer,
             * ahora calculamos nuevamente la altura
             * SI LA ALTURA POR IZQUIERDA ES MAYOR -> Rotacion Doble por Izquierda
             * CASO CONTRARIO -> Rotacion Simple por Izquierda */
            NodoBinario<T> hijoDerDelNodoEnTurno = nodoEnTurno.getHijoDerecho();
            alturaXIzq = super.altura(hijoDerDelNodoEnTurno.getHijoIzquierdo());
            alturaXDer = super.altura(hijoDerDelNodoEnTurno.getHijoDerecho());
            if (alturaXIzq > alturaXDer) {
                return rotacionDoblePorIzquierda(nodoEnTurno);
            }
            return rotacionSimplePorIzquierda(nodoEnTurno);
        }
        return nodoEnTurno;
    }

    /* Rotacion Doble por Izquierda
     * Primero debemos obtener el nodo hijo derecho del nodoEnTurno
     * luego, debemos rotar (simple) por derecha y guardarlo en una variable
     * asignar como nuevo hijo derecho al nodoEnturno
     * retornar la rotacion simple por izquierda del nodoEnTurno */
    private NodoBinario<T> rotacionDoblePorIzquierda(NodoBinario<T> nodoEnTurno) {
        NodoBinario<T> nodoHijoDer = nodoEnTurno.getHijoDerecho();

        NodoBinario<T> nuevoHijoDer = this.rotacionSimplePorDerecha(nodoHijoDer);
        nodoEnTurno.setHijoDerecho(nuevoHijoDer);

        return this.rotacionSimplePorIzquierda(nodoEnTurno);
    }

    /* Rotacion Simple Por Izquierda
     * Primeramente obtenemos el hijo derecho del nodoEnTurno y lo asignamos en una
     * variable
     * Luego tenemos que obtener el hijo izquierdo del nodoQueSube y asignarlo en una
     * variable, por si es tiene algo
     * Asignamos el hijoDelNodoQueSube como hijo derecho del nodoEnTurno
     * Ahora sí, asignamos el nodoEnTurno como hijo derecho del nodoQueSube */
    private NodoBinario<T> rotacionSimplePorIzquierda(NodoBinario<T> nodoEnTurno) {
        NodoBinario<T> nodoQueSube = nodoEnTurno.getHijoDerecho();

        NodoBinario<T> hijoIzqDelNodoQueSube = nodoQueSube.getHijoIzquierdo();
        nodoEnTurno.setHijoDerecho(hijoIzqDelNodoQueSube);

        nodoQueSube.setHijoIzquierdo(nodoEnTurno);
        return nodoQueSube;
    }

    /* Rotacion Doble por Derecha
    * Primero debemos obtener el nodo hijo izquierdo del nodoEnTurno
    * luego, debemos rotar (simple) por izquierda y guardarlo en una variable
    * asignar como nuevo hijo izquierdo al nodoEnturno
    * retornar la rotacion simple por derecha del nodoEnTurno */
    private NodoBinario<T> rotacionDoblePorDerecha(NodoBinario<T> nodoEnTurno) {
        NodoBinario<T> nodoHijoIzq = nodoEnTurno.getHijoIzquierdo();

        NodoBinario<T> nuevoHijoIzq = rotacionSimplePorIzquierda(nodoHijoIzq);
        nodoEnTurno.setHijoIzquierdo(nuevoHijoIzq);

        return this.rotacionSimplePorDerecha(nodoEnTurno);
    }

    /* Rotacion Simple Por Derecha
    * Primeramente obtenemos el hijo izquierdo del nodoEnTurno y lo asignamos en una
    * variable
    * Luego tenemos que obtener el hijo derecho del nodoQueSube y asignarlo en una
    * variable, por si es tiene algo
    * Asignamos el hijoDelNodoQueSube como hijo izquierdo del nodoEnTurno
    * Ahora sí, asignamos el nodoEnTurno como hijo derecho del nodoQueSube */
    private NodoBinario<T> rotacionSimplePorDerecha(NodoBinario<T> nodoEnTurno) {
        NodoBinario<T> nodoQueSube = nodoEnTurno.getHijoIzquierdo();

        NodoBinario<T> hijoDerDelNodoQueSube = nodoQueSube.getHijoDerecho();
        nodoEnTurno.setHijoIzquierdo(hijoDerDelNodoQueSube);

        nodoQueSube.setHijoDerecho(nodoEnTurno);
        return nodoQueSube;
    }

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

        /* Esta parte busca el dato a eliminar y a la vez actualizará el nodo padre
         * de donde nos encontramos, aunque no haya cambiado */
        T datoDelNodoEnTurno = nodoEnTurno.getDato();
        if (datoAEliminar.compareTo(datoDelNodoEnTurno) < 0) {
            NodoBinario<T> supuestoNuevoHijoIzq =
                    this.eliminar(nodoEnTurno.getHijoIzquierdo(), datoAEliminar);
            nodoEnTurno.setHijoIzquierdo(supuestoNuevoHijoIzq);
            return balancear(nodoEnTurno);
        }

        if (datoAEliminar.compareTo(datoDelNodoEnTurno) > 0) {
            NodoBinario<T> supuestoNuevoHijoDer =
                    this.eliminar(nodoEnTurno.getHijoDerecho(), datoAEliminar);
            nodoEnTurno.setHijoDerecho(supuestoNuevoHijoDer);
            return balancear(nodoEnTurno);
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
            return balancear(nodoARetornar);
        }

        // Caso 2. b
        if (nodoEnTurno.esVacioHijoIzquierdo() &&
                !nodoEnTurno.esVacioHijoDerecho()) {
            NodoBinario<T> nodoARetornar = nodoEnTurno.getHijoDerecho();
            nodoEnTurno.setHijoDerecho(NodoBinario.nodoVacio());
            return balancear(nodoARetornar);
        }

        // Caso 3
        T reemplazo = super.buscarSucesorInOrden(nodoEnTurno.getHijoDerecho());
        NodoBinario<T> supuestoNuevoHijoDerecho = this.eliminar(
                nodoEnTurno.getHijoDerecho(), reemplazo);
        nodoEnTurno.setHijoDerecho(supuestoNuevoHijoDerecho);
        nodoEnTurno.setDato(reemplazo);
        return balancear(nodoEnTurno);
    }
}
