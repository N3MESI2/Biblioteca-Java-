package biblioteca;

import biblioteca.cli.Comando;
import biblioteca.cli.ContextoComando;
import biblioteca.cli.comandos.*;
import biblioteca.dominio.EstadoLibro;
import biblioteca.dominio.Libro;
import biblioteca.servicio.Biblioteca;

import java.util.*;

public class Aplicacion {

    public static void main(String[] args) {
        Biblioteca biblioteca = new Biblioteca();
        precargar(biblioteca);

        // Mantenemos el orden de registro para numerar el menú
        Map<String, Comando> comandos = new LinkedHashMap<>();
        List<Comando> lista = new ArrayList<>();
        registrar(comandos, lista,
                new AgregarLibroComando(),
                new RegistrarUsuarioComando(),
                new PrestarLibroComando(),
                new DevolverLibroComando(),
                new ReservarLibroComando(),
                new CancelarReservaComando(),
                new ListarPorEstadoComando(),
                new BuscarPorTituloComando(),
                new ConfigurarDiasPrestamoComando(),
                new ConfigurarMultaComando()
        );

        Scanner in = new Scanner(System.in);
        ContextoComando ctx = new ContextoComando(in, System.out);

        while (true) {
            mostrarMenu(ctx, lista);

            String entrada = ctx.pedir("Seleccione opción (número) o escriba la orden que desea realizar");
            entrada = entrada.trim();

            // Salir rápido
            if (entrada.equalsIgnoreCase("salir") || entrada.equals("0")) {
                System.out.println("¡Hasta luego!");
                break;
            }
            // Mostrar menú sin ejecutar nada
            if (entrada.equalsIgnoreCase("ayuda") || entrada.equalsIgnoreCase("menu")) {
                // vuelve a mostrar el menú en el próximo ciclo
                continue;
            }

            Comando comando = null;

            // ¿Eligió número?
            if (entrada.matches("\\d+")) {
                int idx = Integer.parseInt(entrada);
                if (idx >= 1 && idx <= lista.size()) {
                    comando = lista.get(idx - 1);
                } else {
                    System.out.println("Opción inválida. Use un número del 1 al " + lista.size() + " o '0' para salir.");
                    continue;
                }
            } else {
                // Eligió por nombre
                comando = comandos.get(entrada.toLowerCase());
                if (comando == null) {
                    System.out.println("Comando desconocido. Escriba un nombre válido, un número, 'ayuda' o 'salir'.");
                    continue;
                }
            }

            try {
                comando.ejecutar(ctx, biblioteca);
            } catch (Exception e) {
                System.out.println("⚠ Error: " + e.getMessage());
            }
        }
    }

    // Registra comandos preservando orden (para numeración) y por nombre (para escritura)
    private static void registrar(Map<String, Comando> mapa, List<Comando> lista, Comando... cs) {
        for (Comando c : cs) {
            lista.add(c);
            mapa.put(c.nombre().toLowerCase(), c);
        }
    }

    // Encabezado + menú numerado
    private static void mostrarMenu(ContextoComando ctx, List<Comando> lista) {
        ctx.out().println();
        ctx.out().println("=== Programa Biblioteca ===");
        for (int i = 0; i < lista.size(); i++) {
            Comando c = lista.get(i);
            ctx.out().println((i + 1) + " - " + c.nombre() + " : " + c.descripcion());
        }
        ctx.out().println("0 - salir : Salir del programa");
        ctx.out().println("============================\n");
    }

    // Datos iniciales de ejemplo (opcional)
    private static void precargar(Biblioteca b) {
        b.agregarLibro(new Libro("Código Limpio", "Robert C. Martin", "978-0132350884"));
        b.agregarLibro(new Libro("Diseño Guiado por el Dominio", "Eric Evans", "978-0321125217"));
        b.listarPorEstado(EstadoLibro.DISPONIBLE); // fuerza el uso inicial (no imprime nada)
    }
}
