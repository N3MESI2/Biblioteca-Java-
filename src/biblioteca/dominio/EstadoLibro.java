package biblioteca.dominio;

public enum EstadoLibro {
    DISPONIBLE, PRESTADO, RESERVADO;

    public static EstadoLibro desdeTexto(String texto) {
        String t = texto.trim().toUpperCase();
        return switch (t) {
            case "DISPONIBLE" -> DISPONIBLE;
            case "PRESTADO"   -> PRESTADO;
            case "RESERVADO"  -> RESERVADO;
            default -> throw new IllegalArgumentException("Estado inválido. Use: Disponible | Prestado | Reservado");
        };
    }

    public String etiqueta() {
        return switch (this) {
            case DISPONIBLE -> "disponible";
            case PRESTADO   -> "prestado";
            case RESERVADO  -> "reservado";
        };
    }
}
