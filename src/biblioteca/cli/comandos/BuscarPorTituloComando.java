package biblioteca.cli.comandos;

import biblioteca.cli.Comando;
import biblioteca.cli.ContextoComando;
import biblioteca.dominio.Libro;
import biblioteca.servicio.Biblioteca;

import java.util.List;

public class BuscarPorTituloComando implements Comando {
    public String nombre() { return "buscar-titulo"; }
    public String descripcion() { return "Buscar libros por título (contiene)"; }
    public void ejecutar(ContextoComando ctx, Biblioteca biblioteca) {
        String q = ctx.pedir("Fragmento del título");
        List<Libro> res = biblioteca.buscarPorTitulo(q);
        if (res.isEmpty()) ctx.out().println("No se encontraron coincidencias.");
        else res.forEach(l -> ctx.out().println(" - " + l));
    }
}
