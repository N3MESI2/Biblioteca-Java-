package biblioteca.cli.comandos;

import biblioteca.cli.Comando;
import biblioteca.cli.ContextoComando;
import biblioteca.dominio.EstadoLibro;
import biblioteca.dominio.Libro;
import biblioteca.servicio.Biblioteca;

import java.util.List;

public class ListarPorEstadoComando implements Comando {
    public String nombre() { return "listar-por-estado"; }
    public String descripcion() { return "Listar libros por estado (disponible | prestado | reservado)"; }

    public void ejecutar(ContextoComando ctx, Biblioteca biblioteca) {
        String t = ctx.pedir("Estado (disponible | prestado | reservado)");
        EstadoLibro estado = EstadoLibro.desdeTexto(t);
        List<Libro> lista = biblioteca.listarPorEstado(estado);
        if (lista.isEmpty()) {
            ctx.out().println("No hay libros en estado: " + estado.etiqueta());
        } else {
            ctx.out().println("Libros en estado: " + estado.etiqueta());
            lista.forEach(l -> ctx.out().println(" - " + l));
        }
    }
}
