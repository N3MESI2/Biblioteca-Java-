package biblioteca.cli;

import biblioteca.servicio.Biblioteca;

public interface Comando {
    String nombre();
    String descripcion();
    void ejecutar(ContextoComando ctx, Biblioteca biblioteca) throws Exception;
}
