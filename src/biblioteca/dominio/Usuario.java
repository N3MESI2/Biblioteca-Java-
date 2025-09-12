package biblioteca.dominio;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Usuario {
    private final String id;
    private String nombre;
    private final Set<String> librosPrestados = new HashSet<>();
    private int limitePrestamos = 3;

    public Usuario(String id, String nombre) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("ID inválido.");
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre inválido.");
        this.id = id.trim();
        this.nombre = nombre.trim();
    }

    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) {
        if (nombre == null || nombre.isBlank()) throw new IllegalArgumentException("Nombre inválido.");
        this.nombre = nombre.trim();
    }
    public int getLimitePrestamos() { return limitePrestamos; }
    public void setLimitePrestamos(int limite) {
        if (limite <= 0) throw new IllegalArgumentException("El límite debe ser > 0.");
        this.limitePrestamos = limite;
    }

    public void validarPuedePrestar() {
        if (librosPrestados.size() >= limitePrestamos)
            throw new IllegalStateException("Alcanzó el límite de préstamos (" + limitePrestamos + ").");
    }

    public void registrarPrestamo(String isbn) { librosPrestados.add(Objects.requireNonNull(isbn)); }
    public void registrarDevolucion(String isbn) { librosPrestados.remove(isbn); }
}
