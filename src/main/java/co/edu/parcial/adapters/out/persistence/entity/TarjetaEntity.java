package co.edu.parcial.adapters.out.persistence.entity;

import co.edu.parcial.domain.model.TipoPenalizacion;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;

@Table("tarjetas_penalizacion")
public class TarjetaEntity implements Persistable<String> {
    @Id
    private String id;
    @Column("jugador_id")
    private String jugadorId;
    @Transient
    private boolean isNew;
    private TipoPenalizacion tipo;
    private Integer puntos;
    private String descripcion;
    private Instant fecha;

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public void setNew(boolean aNew) { isNew = aNew; }

    @Override
    public boolean isNew() {
        return isNew;
    }

    public String getJugadorId() { return jugadorId; }
    public void setJugadorId(String jugadorId) { this.jugadorId = jugadorId; }

    public TipoPenalizacion getTipo() { return tipo; }
    public void setTipo(TipoPenalizacion tipo) { this.tipo = tipo; }

    public Integer getPuntos() { return puntos; }
    public void setPuntos(Integer puntos) { this.puntos = puntos; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public Instant getFecha() { return fecha; }
    public void setFecha(Instant fecha) { this.fecha = fecha; }
}

