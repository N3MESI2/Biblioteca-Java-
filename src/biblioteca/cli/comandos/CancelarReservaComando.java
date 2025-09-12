package biblioteca.cli.comandos;

import biblioteca.cli.Comando;
import biblioteca.cli.ContextoComando;
import biblioteca.servicio.Biblioteca;

public class CancelarReservaComando implements Comando {
    public String nombre() { return "cancelar-reserva"; }
    public String descripcion() { return "Cancelar la reserva de un libro"; }
    public void ejecutar(ContextoComando ctx, Biblioteca biblioteca) {
        String usuarioId = ctx.pedir("ID de usuario");
        String isbn      = ctx.pedir("ISBN");
        biblioteca.cancelarReserva(usuarioId, isbn);
        ctx.out().println("✓ Reserva cancelada");
    }
}
