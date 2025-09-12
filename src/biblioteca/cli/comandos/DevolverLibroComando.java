package biblioteca.cli.comandos;

import biblioteca.cli.Comando;
import biblioteca.cli.ContextoComando;
import biblioteca.servicio.Biblioteca;

public class DevolverLibroComando implements Comando {
    public String nombre() { return "devolver"; }
    public String descripcion() { return "Devolver un libro prestado"; }
    public void ejecutar(ContextoComando ctx, Biblioteca biblioteca) {
        String usuarioId = ctx.pedir("ID de usuario");
        String isbn      = ctx.pedir("ISBN");
        double multa = biblioteca.devolverLibro(usuarioId, isbn);
        ctx.out().println(multa > 0 ? "⚠ Multa: $" + multa : "✓ Devolución registrada sin multa");
    }
}
