package biblioteca.servicio;

import biblioteca.dominio.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Biblioteca {
    private final Map<String, Libro> catalogo = new HashMap<>();
    private final Map<String, Usuario> usuarios = new HashMap<>();

    private int diasPrestamoPorDefecto = 14;
    private double multaPorDia = 50.0;

    public void setDiasPrestamoPorDefecto(int dias) {
        if (dias <= 0) throw new IllegalArgumentException("Los días deben ser > 0.");
        this.diasPrestamoPorDefecto = dias;
    }
    public void setMultaPorDia(double valor) {
        if (valor < 0) throw new IllegalArgumentException("La multa no puede ser negativa.");
        this.multaPorDia = valor;
    }
    public int getDiasPrestamoPorDefecto() { return diasPrestamoPorDefecto; }
    public double getMultaPorDia() { return multaPorDia; }

    public void agregarLibro(Libro l) {
        Objects.requireNonNull(l, "libro");
        if (catalogo.containsKey(l.getIsbn())) throw new IllegalStateException("Ya existe un libro con ese ISBN.");
        catalogo.put(l.getIsbn(), l);
    }
    public void registrarUsuario(Usuario u) {
        Objects.requireNonNull(u, "usuario");
        if (usuarios.containsKey(u.getId())) throw new IllegalStateException("Ya existe un usuario con ese ID.");
        usuarios.put(u.getId(), u);
    }

    public void reservarLibro(String usuarioId, String isbn) {
        Libro libro = obtenerLibro(isbn);
        obtenerUsuario(usuarioId);
        libro.reservar(usuarioId);
    }
    public void cancelarReserva(String usuarioId, String isbn) {
        Libro libro = obtenerLibro(isbn);
        obtenerUsuario(usuarioId);
        libro.cancelarReserva(usuarioId);
    }
    public void prestarLibro(String usuarioId, String isbn) { prestarLibro(usuarioId, isbn, LocalDate.now()); }
    public void prestarLibro(String usuarioId, String isbn, LocalDate hoy) {
        Usuario usuario = obtenerUsuario(usuarioId);
        Libro libro = obtenerLibro(isbn);
        usuario.validarPuedePrestar();
        LocalDate vence = hoy.plusDays(diasPrestamoPorDefecto);
        libro.prestar(vence, usuarioId);
        usuario.registrarPrestamo(isbn);
    }
    public double devolverLibro(String usuarioId, String isbn) { return devolverLibro(usuarioId, isbn, LocalDate.now()); }
    public double devolverLibro(String usuarioId, String isbn, LocalDate fechaDevolucion) {
        Usuario usuario = obtenerUsuario(usuarioId);
        Libro libro = obtenerLibro(isbn);
        double multa = calcularMulta(libro, fechaDevolucion);
        libro.devolver();
        usuario.registrarDevolucion(isbn);
        return multa;
    }

    public Optional<Libro> buscarPorIsbn(String isbn) { return Optional.ofNullable(catalogo.get(isbn)); }
    public Optional<Usuario> buscarUsuario(String id) { return Optional.ofNullable(usuarios.get(id)); }
    public List<Libro> listarPorEstado(EstadoLibro estado) {
        return catalogo.values().stream().filter(l -> l.getEstado() == estado).toList();
    }
    public List<Libro> buscarPorTitulo(String fragmento) {
        String f = fragmento.toLowerCase(Locale.ROOT);
        return catalogo.values().stream()
                .filter(l -> l.getTitulo().toLowerCase(Locale.ROOT).contains(f))
                .toList();
    }
    public double calcularMulta(Libro libro, LocalDate fecha) {
        if (!libro.estaVencido(fecha)) return 0.0;
        long diasTarde = Math.max(0, ChronoUnit.DAYS.between(libro.getFechaVencimiento(), fecha));
        return diasTarde * multaPorDia;
    }

    private Libro obtenerLibro(String isbn) {
        return buscarPorIsbn(isbn).orElseThrow(() -> new NoSuchElementException("No existe libro con ISBN: " + isbn));
    }
    private Usuario obtenerUsuario(String id) {
        return buscarUsuario(id).orElseThrow(() -> new NoSuchElementException("No existe usuario con ID: " + id));
    }
}
