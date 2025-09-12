package biblioteca.cli.comandos;

import biblioteca.cli.Comando;
import biblioteca.cli.ContextoComando;
import biblioteca.servicio.Biblioteca;

public class ConfigurarMultaComando implements Comando {
    public String nombre() { return "config-multa"; }
    public String descripcion() { return "Configurar multa por día de atraso"; }
    public void ejecutar(ContextoComando ctx, Biblioteca biblioteca) {
        double multa = Double.parseDouble(ctx.pedir("Multa por día ($)"));
        biblioteca.setMultaPorDia(multa);
        ctx.out().println("✓ Multa por día = $" + biblioteca.getMultaPorDia());
    }
}
