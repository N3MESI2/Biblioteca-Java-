package biblioteca.dominio;

import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

public class Libro {
    private String titulo;
    private String autor;
    private String isbn;
    private EstadoLibro estado;
    private LocalDate fechaVencimiento;
    private String reservadoPorUsuarioId;

    private static final Pattern ISBN_SIMPLE = Pattern.compile("^[0-9Xx\\-]{10,17}$");

    public Libro(String titulo, String autor, String isbn) {
        setTitulo(titulo);
        setAutor(autor);
        setIsbn(isbn);
        this.estado = EstadoLibro.DISPONIBLE;
    }

    public String getTitulo() { return titulo; }
    public String getAutor() { return autor; }
    public String getIsbn() { return isbn; }
    public EstadoLibro getEstado() { return estado; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public Optional<String> getReservadoPorUsuarioId() { return Optional.ofNullable(reservadoPorUsuarioId); }

    public final void setTitulo(String titulo) {
        if (titulo == null || titulo.isBlank()) throw new IllegalArgumentException("El título no puede estar vacío.");
        this.titulo = titulo.trim();
    }
    public final void setAutor(String autor) {
        if (autor == null || autor.isBlank()) throw new IllegalArgumentException("El autor no puede estar vacío.");
        this.autor = autor.trim();
    }
    public final void setIsbn(String isbn) {
        if (isbn == null || isbn.isBlank() || !ISBN_SIMPLE.matcher(isbn).matches())
            throw new IllegalArgumentException("ISBN inválido (formato simple).");
        this.isbn = isbn.trim();
    }

    public void prestar(LocalDate vence, String usuarioId) {
        if (estado == EstadoLibro.PRESTADO) throw new IllegalStateException("El libro ya está prestado.");
        if (estado == EstadoLibro.RESERVADO && (reservadoPorUsuarioId == null || !reservadoPorUsuarioId.equals(usuarioId)))
            throw new IllegalStateException("El libro está reservado por otro usuario.");
        if (vence == null || !vence.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("La fecha de vencimiento debe ser futura.");
        this.estado = EstadoLibro.PRESTADO;
        this.fechaVencimiento = vence;
        if (reservadoPorUsuarioId != null && reservadoPorUsuarioId.equals(usuarioId)) reservadoPorUsuarioId = null;
    }

    public void devolver() {
        if (estado != EstadoLibro.PRESTADO) throw new IllegalStateException("El libro no está prestado.");
        this.estado = EstadoLibro.DISPONIBLE;
        this.fechaVencimiento = null;
    }

    public void reservar(String usuarioId) {
        if (estado != EstadoLibro.DISPONIBLE) throw new IllegalStateException("Solo se puede reservar un libro disponible.");
        if (reservadoPorUsuarioId != null) throw new IllegalStateException("El libro ya está reservado.");
        if (usuarioId == null || usuarioId.isBlank()) throw new IllegalArgumentException("Usuario inválido para reservar.");
        this.estado = EstadoLibro.RESERVADO;
        this.reservadoPorUsuarioId = usuarioId;
    }

    public void cancelarReserva(String usuarioId) {
        if (estado != EstadoLibro.RESERVADO) throw new IllegalStateException("El libro no está reservado.");
        if (reservadoPorUsuarioId == null || !reservadoPorUsuarioId.equals(usuarioId))
            throw new IllegalStateException("Solo puede cancelar la reserva quien la hizo.");
        this.estado = EstadoLibro.DISPONIBLE;
        this.reservadoPorUsuarioId = null;
    }

    public boolean estaVencido(LocalDate fecha) {
        return estado == EstadoLibro.PRESTADO && fechaVencimiento != null && fecha.isAfter(fechaVencimiento);
    }

    @Override
    public String toString() {
        String venc = (fechaVencimiento != null) ? " | Vence: " + fechaVencimiento : "";
        return "%s — %s (%s) [%s]%s".formatted(titulo, autor, isbn, estado.etiqueta(), venc);
    }
}
