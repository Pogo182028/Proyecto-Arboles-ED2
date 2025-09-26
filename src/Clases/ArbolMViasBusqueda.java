package Clases;

import Excepciones.ExcepcionDatoNoExiste;
import Excepciones.ExcepcionDatoYaExiste;
import Excepciones.ExcepcionOrdenInvalido;

import java.util.*;

public class ArbolMViasBusqueda<T extends Comparable<T>>
        implements IArbolBusqueda<T> {
    protected NodoMVias<T> raiz;
    protected int orden;
    protected static final int POSICION_INVALIDA = -1;
    protected static final int ORDEN_MINIMO = 3;

    public ArbolMViasBusqueda() {
        this.orden = ArbolMViasBusqueda.ORDEN_MINIMO;
    }

    public ArbolMViasBusqueda(int orden) throws ExcepcionOrdenInvalido {
        if (orden < ArbolMViasBusqueda.ORDEN_MINIMO) {
            throw new ExcepcionOrdenInvalido("El orden debe ser mayor que el valor");
        }

        this.orden = orden;
    }

    @Override
    public void insertar(T datoAInsertar) throws ExcepcionDatoYaExiste {
        if (datoAInsertar == null) {
            throw new IllegalArgumentException("No se permite datos vacios");
        }

        if (esArbolVacio()) {
            this.raiz = new NodoMVias<>(this.orden, datoAInsertar);
            return;
        }
        NodoMVias<T> nodoAux = this.raiz;
        do {
            int posicionDeDatoAInsertar = buscarPosicionDeDatoEnNodo(nodoAux, datoAInsertar);
            if (posicionDeDatoAInsertar != POSICION_INVALIDA) {
                throw new ExcepcionDatoYaExiste("El dato a insertar ya existe");
            }
            if (nodoAux.esHoja()) {
                // Si es hoja
                if (nodoAux.estanDatosLlenos()) {
                    int posicionPorDondeBajar = obtenerPosicionPorDondeBajar(nodoAux, datoAInsertar);
                    NodoMVias<T> nuevoNodo = new NodoMVias<>(orden, datoAInsertar);
                    nodoAux.setHijo(posicionPorDondeBajar, nuevoNodo);
                } else {
                    insertarDatoEnNodoOrdenado(nodoAux, datoAInsertar);
                }
                nodoAux = NodoMVias.nodoVacio();
            } else {
                // No es hoja
                int posicionPorDondeBajar = obtenerPosicionPorDondeBajar(nodoAux, datoAInsertar);
                if (nodoAux.esHijoVacio(posicionPorDondeBajar)) {
                    NodoMVias<T> nuevoNodo = new NodoMVias<>(orden, datoAInsertar);
                    nodoAux.setHijo(posicionPorDondeBajar, nuevoNodo);
                    nodoAux = NodoMVias.nodoVacio();
                } else {
                    nodoAux = nodoAux.getHijo(posicionPorDondeBajar);
                }
            }
        } while (!NodoMVias.esNodoVacio(nodoAux));
    }

    protected int buscarPosicionDeDatoEnNodo(NodoMVias<T> nodoAux, T datoAInsertar) {
        for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
            if (datoAInsertar.compareTo(nodoAux.getDato(i)) == 0) {
                return i;
            }
        }
        return POSICION_INVALIDA;
    }

    protected int obtenerPosicionPorDondeBajar(NodoMVias<T> nodoAux, T datoAInsertar) {
        for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
            if (datoAInsertar.compareTo(nodoAux.getDato(i)) < 0) {
                return i;
            }
        }
        return nodoAux.nroDeDatosNoVacios();
    }

    protected void insertarDatoEnNodoOrdenado(NodoMVias<T> nodoAux, T datoAInsertar) {
        int i = 0;
        while (i < nodoAux.nroDeDatosNoVacios() && datoAInsertar.compareTo(nodoAux.getDato(i)) > 0) {
            i++;
        }

        for (int j = nodoAux.nroDeDatosNoVacios(); j > i; j--) {
            nodoAux.setDato(j, nodoAux.getDato(j - 1));
        }

        nodoAux.setDato(i, datoAInsertar);
    }

    @Override
    public void eliminar(T datoAEliminar) throws ExcepcionDatoNoExiste {
        if (datoAEliminar == null) {
            throw new IllegalArgumentException("No se permite datos vacios");
        }

        this.raiz = eliminar(this.raiz, datoAEliminar);
    }

    private NodoMVias<T> eliminar(NodoMVias<T> nodoEnTurno, T datoAEliminar)
            throws ExcepcionDatoNoExiste {

        if (NodoMVias.esNodoVacio(nodoEnTurno)) {
            throw new ExcepcionDatoNoExiste("El dato a eliminar no existe");
        }

        // Buscando el dato y actualizando por si hay cambios
        int posicionDeDatoAEliminar = buscarPosicionDeDatoEnNodo(nodoEnTurno, datoAEliminar);
        if (posicionDeDatoAEliminar == POSICION_INVALIDA) {
            int posicionPorDondeBajar = obtenerPosicionPorDondeBajar(nodoEnTurno, datoAEliminar);
            NodoMVias<T> supuestoNuevoHijo = eliminar(nodoEnTurno.getHijo(posicionPorDondeBajar), datoAEliminar);
            nodoEnTurno.setHijo(posicionPorDondeBajar, supuestoNuevoHijo);
            return nodoEnTurno;
        }

        // Caso 1 -> si es hoja
        if (nodoEnTurno.esHoja()) {
            eliminarDatoDePosicion(nodoEnTurno, posicionDeDatoAEliminar);
            if (nodoEnTurno.nroDeDatosNoVacios() == 0) {
                return NodoMVias.nodoVacio();
            }
            return nodoEnTurno;
        }

        T datoDeReemplazo;
        if (existenHijosDespuesDePosicion(nodoEnTurno, posicionDeDatoAEliminar)) {
            datoDeReemplazo = buscarSucesorInOrden(nodoEnTurno, posicionDeDatoAEliminar);
        } else {
            datoDeReemplazo = buscarPredecesorInOrden(nodoEnTurno, posicionDeDatoAEliminar);
        }

        nodoEnTurno = eliminar(nodoEnTurno, datoDeReemplazo);
        nodoEnTurno.setDato(posicionDeDatoAEliminar, datoDeReemplazo);
        return nodoEnTurno;
    }

    // todo: IMPLEMENTAR EL "buscarSucesorInOrden" Y "buscarPredecesorInOrden"
    private T buscarPredecesorInOrden(NodoMVias<T> nodoEnTurno, int posicionDeDatoAEliminar) {
        return null;
    }

    private T buscarSucesorInOrden(NodoMVias<T> nodoEnTurno, int posicionDeDatoAEliminar) {
        return null;
    }
    // fin del todo: -----------------------------------------------------------------------------------

    protected void eliminarDatoDePosicion(NodoMVias<T> nodoEnTurno, int posicion) {
        for (int i = posicion; i < nodoEnTurno.nroDeDatosNoVacios() - 1; i++) {
            nodoEnTurno.setDato(i, nodoEnTurno.getDato(i + 1));
        }
        nodoEnTurno.setDato(nodoEnTurno.nroDeDatosNoVacios() - 1, null);
    }

    private boolean existenHijosDespuesDePosicion(NodoMVias<T> nodoEnTurno, int posicion) {
        for (int i = posicion + 1; i <= orden - 1; i++) {
            if (!nodoEnTurno.esHijoVacio(i)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void vaciar() {
        this.raiz = NodoMVias.nodoVacio();
    }

    @Override
    public T buscar(T datoABuscar) {
        if (!this.esArbolVacio()) {
            NodoMVias<T> nodoAux = this.raiz;
            do {
                boolean cambioDeNodoAux = false;
                for (int i = 0; i < nodoAux.nroDeDatosNoVacios() &&
                        !cambioDeNodoAux; i++) {
                    T datoNodoAux = nodoAux.getDato(i);
                    if (datoABuscar.compareTo(datoNodoAux) == 0) {
                        return datoABuscar;
                    }
                    if (datoABuscar.compareTo(datoNodoAux) < 0) {
                        nodoAux = nodoAux.getHijo(i);
                        cambioDeNodoAux = true;
                    }
                }
                if (!cambioDeNodoAux) {
                    nodoAux = nodoAux.getHijo(nodoAux.nroDeDatosNoVacios());
                }
            } while (!NodoMVias.esNodoVacio(nodoAux));
        }
        return null;
    }

    @Override
    public boolean esArbolVacio() {
        return NodoMVias.esNodoVacio(raiz);
    }

    @Override
    public boolean contiene(T dato) {
        return buscar(dato) != null;
    }

    // todo: PROBAR SI FUNCIONA EL SIZE Y LA ALTURA
    @Override
    public int size() {
        return size(this.raiz);
    }

    private int size(NodoMVias<T> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }

        int sizeAcum = 0;
        for (int i = 0; i <= nodoActual.nroDeDatosNoVacios(); i++) {
            sizeAcum += size(nodoActual.getHijo(i));
        }
        return sizeAcum + 1;
    }

    @Override
    public int altura() {
        return altura(this.raiz);
    }

    private int altura(NodoMVias<T> nodoActual) {
        if (NodoMVias.esNodoVacio(nodoActual)) {
            return 0;
        }

        int alturaMay = 0;
        for (int i = 0; i <= nodoActual.nroDeDatosNoVacios(); i++) {
            int alturaDeHijo = altura(nodoActual.getHijo(i));
            if (alturaDeHijo > alturaMay) {
                alturaMay = alturaDeHijo;
            }
        }
        return alturaMay + 1;
    }
    // Fin del todo: -----------------------------------------------------------------------

    @Override
    public int nivel(T dato) {
        if (!this.esArbolVacio()) {
            return -1;
        }

        NodoMVias<T> nodoAux = this.raiz;
        int nivelActual = 1;
        do {
            boolean cambioDeNodoAux = false;
            for (int i = 0; i < nodoAux.nroDeDatosNoVacios() &&
                    !cambioDeNodoAux; i++) {
                T datoNodoAux = nodoAux.getDato(i);
                if (dato.compareTo(datoNodoAux) == 0) {
                    return nivelActual;
                }
                if (dato.compareTo(datoNodoAux) < 0) {
                    nodoAux = nodoAux.getHijo(i);
                    cambioDeNodoAux = true;
                }
            }
            if (!cambioDeNodoAux) {
                nodoAux = nodoAux.getHijo(nodoAux.nroDeDatosNoVacios());
            }

            nivelActual++;
        } while (!NodoMVias.esNodoVacio(nodoAux));

        return -1; // dato no encontrado
    }

    @Override
    public List<T> recorridoEnInOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoEnInOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnInOrden(NodoMVias<T> nodoAux, List<T> recorrido) {
        if (NodoMVias.esNodoVacio(nodoAux)) {
            return;
        }

        for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
            recorridoEnInOrden(nodoAux.getHijo(i), recorrido);
            recorrido.add(nodoAux.getDato(i));
        }
        recorridoEnInOrden(nodoAux.getHijo(nodoAux.nroDeDatosNoVacios()), recorrido);
    }

    @Override
    public List<T> recorridoEnPreOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoEnPreOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPreOrden(NodoMVias<T> nodoAux, List<T> recorrido) {
        if (NodoMVias.esNodoVacio(nodoAux)) {
            return;
        }

        for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
            recorrido.add(nodoAux.getDato(i));
            recorridoEnPreOrden(nodoAux.getHijo(i), recorrido);
        }
        recorridoEnPreOrden(nodoAux.getHijo(nodoAux.nroDeDatosNoVacios()), recorrido);
    }

    @Override
    public List<T> recorridoEnPostOrden() {
        List<T> recorrido = new ArrayList<>();
        recorridoEnPostOrden(this.raiz, recorrido);
        return recorrido;
    }

    private void recorridoEnPostOrden(NodoMVias<T> nodoAux, List<T> recorrido) {
        if (NodoMVias.esNodoVacio(nodoAux)) {
            return;
        }
        recorridoEnPostOrden(nodoAux.getHijo(0), recorrido);
        for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
            recorridoEnPostOrden(nodoAux.getHijo(i + 1), recorrido);
            recorrido.add(nodoAux.getDato(i));
        }
    }

    @Override
    public List<T> recorridoPorNiveles() {
        List<T> recorrido = new LinkedList<>();
        if (!this.esArbolVacio()) {
            Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
            colaDeNodos.offer(raiz);
            do {
                NodoMVias<T> nodoAux = colaDeNodos.poll();
                for (int i = 0; i < nodoAux.nroDeDatosNoVacios(); i++) {
                    recorrido.add(nodoAux.getDato(i));
                    if (!nodoAux.esHijoVacio(i)) {
                        colaDeNodos.offer(nodoAux.getHijo(i));
                    }
                } // Fin del for

                if (!nodoAux.esHijoVacio(nodoAux.nroDeDatosNoVacios())) {
                    colaDeNodos.offer(nodoAux.getHijo(nodoAux.nroDeDatosNoVacios()));
                }
            } while (!colaDeNodos.isEmpty());
        }
        return recorrido;
    }

    public int cantDeHijosNoVaciosAntesDeNivel(int nivel) {
        if (nivel <= -1) {
            throw new IllegalArgumentException("El nivel no puede ser negativo");
        }
        return cantDeHijosNoVaciosAntesDeNivel(this.raiz, nivel);
    }

    private int cantDeHijosNoVaciosAntesDeNivel(NodoMVias<T> nodoAux, int nivel) {
        if (NodoMVias.esNodoVacio(nodoAux)) {
            throw new IllegalArgumentException("El arbol es vacio");
        }

        if (nivel == 0) {
            return 0;
        }

        Queue<NodoMVias<T>> colaDeNodos = new LinkedList<>();
        colaDeNodos.offer(nodoAux);
        int nivelActual = 0;
        int cantHijosNoVacios = 0;

        while (nivelActual < nivel) {
            nodoAux = colaDeNodos.poll();
            for (int i = 0; i < nodoAux.nroDeDatosNoVacios() - 1; i++) {
                if (!nodoAux.esHijoVacio(i)) {
                    colaDeNodos.offer(nodoAux.getHijo(i));
                    cantHijosNoVacios++;
                }
            }
            if (!nodoAux.esHijoVacio(nodoAux.nroDeDatosNoVacios())) {
                colaDeNodos.offer(nodoAux.getHijo(nodoAux.nroDeDatosNoVacios()));
                cantHijosNoVacios++;
            }
            nivelActual++;
        }
        return cantHijosNoVacios;
    }

    @Override
    public String toString() {
        if (NodoMVias.esNodoVacio(this.raiz)) {
            return "Árbol vacío";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Raíz: ").append(this.raiz.getDatosComoCadena()).append("\n");
        sb.append(toStringRecursivo(this.raiz, "", true));
        return sb.toString();
    }

    private String toStringRecursivo(NodoMVias<T> nodo, String prefijo, boolean esUltimo) {
        StringBuilder sb = new StringBuilder();
        int cantidadDeDatos = nodo.nroDeDatosNoVacios();

        for (int i = 0; i <= cantidadDeDatos; i++) {
            NodoMVias<T> hijo = nodo.getHijo(i);
            boolean esUltimoHijo = (i == cantidadDeDatos);

            if (!NodoMVias.esNodoVacio(hijo)) {
                sb.append(prefijo)
                        .append(esUltimoHijo ? "└── " : "├── ")
                        .append(hijo.getDatosComoCadena())
                        .append("\n");
                sb.append(toStringRecursivo(hijo, prefijo + (esUltimoHijo ? "    " : "│   "), esUltimoHijo));
            } else {
                sb.append(prefijo)
                        .append(esUltimoHijo ? "└── " : "├── ")
                        .append("∅\n");
            }
        }

        return sb.toString();
    }
}
