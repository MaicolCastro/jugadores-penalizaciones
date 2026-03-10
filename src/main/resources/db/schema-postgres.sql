CREATE TABLE IF NOT EXISTS users (
  id VARCHAR(36) PRIMARY KEY,
  username VARCHAR(40) NOT NULL UNIQUE,
  password_hash VARCHAR(120) NOT NULL,
  role VARCHAR(10) NOT NULL,
  created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS jugadores (
  id VARCHAR(36) PRIMARY KEY,
  nombre VARCHAR(80) NOT NULL,
  email VARCHAR(120) NOT NULL UNIQUE,
  edad INT NOT NULL,
  created_at TIMESTAMPTZ NOT NULL
);

CREATE TABLE IF NOT EXISTS tarjetas_penalizacion (
  id VARCHAR(36) PRIMARY KEY,
  jugador_id VARCHAR(36) NOT NULL,
  tipo VARCHAR(20) NOT NULL,
  puntos INT NOT NULL,
  descripcion VARCHAR(200) NOT NULL,
  fecha TIMESTAMPTZ NOT NULL,
  CONSTRAINT fk_tarjeta_jugador
    FOREIGN KEY (jugador_id) REFERENCES jugadores(id) ON DELETE CASCADE
);

CREATE INDEX IF NOT EXISTS idx_tarjetas_jugador ON tarjetas_penalizacion (jugador_id);

