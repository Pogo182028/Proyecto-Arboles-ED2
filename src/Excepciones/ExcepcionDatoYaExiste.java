package Excepciones;

public class ExcepcionDatoYaExiste extends RuntimeException {
  public ExcepcionDatoYaExiste(String message) {
    super(message);
  }
}
