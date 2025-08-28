package Clases;

public class NodoBinario<T> {
    private T dato;
    private NodoBinario<T> hijoIzquierdo;
    private NodoBinario<T> hijoDerecho;

    public NodoBinario(T dato) {
        this.dato = dato;
        this.hijoIzquierdo = null;
        this.hijoDerecho = null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public NodoBinario<T> getHijoIzquierdo() {
        return hijoIzquierdo;
    }

    public void setHijoIzquierdo(NodoBinario<T> hijoIzquierdo) {
        this.hijoIzquierdo = hijoIzquierdo;
    }

    public NodoBinario<T> getHijoDerecho() {
        return hijoDerecho;
    }

    public void setHijoDerecho(NodoBinario<T> hijoDerecho) {
        this.hijoDerecho = hijoDerecho;
    }

    public boolean esVacioHijoIzquierdo() {
        return esNodoVacio(this.hijoIzquierdo);
    }

    public boolean esVacioHijoDerecho() {
        return esNodoVacio(this.hijoDerecho);
    }

    public boolean esHoja() {
        return (hijoIzquierdo == null && hijoDerecho == null);
    }

    public static <T> boolean esNodoVacio(NodoBinario<T> nodo) {
        return nodo == null;
    }

    public static <T> NodoBinario<T> nodoVacio() {
        return null;
    }
}
