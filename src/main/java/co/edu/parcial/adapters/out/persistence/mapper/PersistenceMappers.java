package co.edu.parcial.adapters.out.persistence.mapper;

import co.edu.parcial.adapters.out.persistence.entity.JugadorEntity;
import co.edu.parcial.adapters.out.persistence.entity.TarjetaEntity;
import co.edu.parcial.adapters.out.persistence.entity.UserEntity;
import co.edu.parcial.domain.model.AppUser;
import co.edu.parcial.domain.model.Jugador;
import co.edu.parcial.domain.model.TarjetaPenalizacion;

import java.util.UUID;

public final class PersistenceMappers {
    private PersistenceMappers() {}

    public static Jugador toDomain(JugadorEntity e) {
        return new Jugador(UUID.fromString(e.getId()), e.getNombre(), e.getEmail(), e.getEdad(), e.getCreatedAt());
    }

    public static JugadorEntity toEntity(Jugador d) {
        var e = new JugadorEntity();
        e.setId(d.id().toString());
        e.setNombre(d.nombre());
        e.setEmail(d.email());
        e.setEdad(d.edad());
        e.setCreatedAt(d.createdAt());
        return e;
    }

    public static TarjetaPenalizacion toDomain(TarjetaEntity e) {
        return new TarjetaPenalizacion(
                UUID.fromString(e.getId()),
                UUID.fromString(e.getJugadorId()),
                e.getTipo(),
                e.getPuntos(),
                e.getDescripcion(),
                e.getFecha()
        );
    }

    public static TarjetaEntity toEntity(TarjetaPenalizacion d) {
        var e = new TarjetaEntity();
        e.setId(d.id().toString());
        e.setJugadorId(d.jugadorId().toString());
        e.setTipo(d.tipo());
        e.setPuntos(d.puntos());
        e.setDescripcion(d.descripcion());
        e.setFecha(d.fecha());
        return e;
    }

    public static AppUser toDomain(UserEntity e) {
        return new AppUser(UUID.fromString(e.getId()), e.getUsername(), e.getPasswordHash(), e.getRole(), e.getCreatedAt());
    }

    public static UserEntity toEntity(AppUser d) {
        var e = new UserEntity();
        e.setId(d.id().toString());
        e.setUsername(d.username());
        e.setPasswordHash(d.passwordHash());
        e.setRole(d.role());
        e.setCreatedAt(d.createdAt());
        return e;
    }
}

