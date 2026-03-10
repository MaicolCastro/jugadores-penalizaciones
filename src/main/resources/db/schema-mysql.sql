CREATE TABLE IF NOT EXISTS users (
  id CHAR(36) PRIMARY KEY,
  username VARCHAR(40) NOT NULL UNIQUE,
  password_hash VARCHAR(120) NOT NULL,
  role VARCHAR(10) NOT NULL,
  created_at TIMESTAMP(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS jugadores (
  id CHAR(36) PRIMARY KEY,
  nombre VARCHAR(80) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  edad INT NOT NULL,
  created_at TIMESTAMP(6) NOT NULL
);

CREATE TABLE IF NOT EXISTS tarjetas_penalizacion (
  id CHAR(36) PRIMARY KEY,
  jugador_id CHAR(36) NOT NULL,
  tipo VARCHAR(20) NOT NULL,
  puntos INT NOT NULL,
  descripcion VARCHAR(200) NOT NULL,
  fecha TIMESTAMP(6) NOT NULL,
  CONSTRAINT fk_tarjeta_jugador
    FOREIGN KEY (jugador_id) REFERENCES jugadores(id) ON DELETE CASCADE
);

CREATE INDEX idx_tarjetas_jugador ON tarjetas_penalizacion (jugador_id);

