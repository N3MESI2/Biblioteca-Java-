package biblioteca.cli.comandos;

import biblioteca.cli.Comando;
import biblioteca.cli.ContextoComando;
import biblioteca.servicio.Biblioteca;

public class ConfigurarDiasPrestamoComando implements Comando {
    public String nombre() { return "config-dias"; }
    public String descripcion() { return "Configurar días de préstamo por defecto"; }
    public void ejecutar(ContextoComando ctx, Biblioteca biblioteca) {
        int dias = Integer.parseInt(ctx.pedir("Días de préstamo"));
        biblioteca.setDiasPrestamoPorDefecto(dias);
        ctx.out().println("✓ Días de préstamo: " + biblioteca.getDiasPrestamoPorDefecto());
    }
}
