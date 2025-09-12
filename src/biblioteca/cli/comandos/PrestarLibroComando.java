package biblioteca.cli.comandos;

import biblioteca.cli.Comando;
import biblioteca.cli.ContextoComando;
import biblioteca.servicio.Biblioteca;

public class PrestarLibroComando implements Comando {
    public String nombre() { return "prestar"; }
    public String descripcion() { return "Prestar un libro a un usuario"; }
    public void ejecutar(ContextoComando ctx, Biblioteca biblioteca) {
        String usuarioId = ctx.pedir("ID de usuario");
        String isbn      = ctx.pedir("ISBN");
        biblioteca.prestarLibro(usuarioId, isbn);
        ctx.out().println("✓ Préstamo realizado");
    }
}
