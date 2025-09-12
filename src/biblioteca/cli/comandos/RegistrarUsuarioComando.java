package biblioteca.cli.comandos;

import biblioteca.cli.Comando;
import biblioteca.cli.ContextoComando;
import biblioteca.servicio.Biblioteca;
import biblioteca.dominio.Usuario;

public class RegistrarUsuarioComando implements Comando {
    public String nombre() { return "registrar"; }
    public String descripcion() { return "Registrar usuario"; }
    public void ejecutar(ContextoComando ctx, Biblioteca biblioteca) {
        String id = ctx.pedir("ID de usuario");
        String nombre = ctx.pedir("Nombre");
        biblioteca.registrarUsuario(new Usuario(id, nombre));
        ctx.out().println("✓ Usuario registrado");
    }
}
