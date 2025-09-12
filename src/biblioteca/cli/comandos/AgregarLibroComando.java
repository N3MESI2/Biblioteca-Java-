package biblioteca.cli.comandos;

import biblioteca.cli.Comando;
import biblioteca.cli.ContextoComando;
import biblioteca.servicio.Biblioteca;
import biblioteca.dominio.Libro;

public class AgregarLibroComando implements Comando {
    public String nombre() { return "agregar"; }
    public String descripcion() { return "Agregar libro"; }
    public void ejecutar(ContextoComando ctx, Biblioteca biblioteca) {
        String titulo = ctx.pedir("Título");
        String autor  = ctx.pedir("Autor");
        String isbn   = ctx.pedir("ISBN");
        biblioteca.agregarLibro(new Libro(titulo, autor, isbn));
        ctx.out().println("✓ Libro agregado");
    }
}
