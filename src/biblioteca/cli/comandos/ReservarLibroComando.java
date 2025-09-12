package biblioteca.cli.comandos;

import biblioteca.cli.Comando;
import biblioteca.cli.ContextoComando;
import biblioteca.servicio.Biblioteca;

public class ReservarLibroComando implements Comando {
    public String nombre() { return "reservar"; }
    public String descripcion() { return "Reservar un libro disponible"; }
    public void ejecutar(ContextoComando ctx, Biblioteca biblioteca) {
        String usuarioId = ctx.pedir("ID de usuario");
        String isbn      = ctx.pedir("ISBN");
        biblioteca.reservarLibro(usuarioId, isbn);
        ctx.out().println("✓ Reserva realizada");
    }
}
