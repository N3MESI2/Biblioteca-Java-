package biblioteca.cli;

import java.io.PrintStream;
import java.util.Scanner;

public class ContextoComando {
    private final Scanner in;
    private final PrintStream out;

    public ContextoComando(Scanner in, PrintStream out) {
        this.in = in; this.out = out;
    }

    public Scanner in() { return in; }
    public PrintStream out() { return out; }

    public String pedir(String etiqueta) {
        out.print(etiqueta + ": ");
        return in.nextLine().trim();
    }
}
