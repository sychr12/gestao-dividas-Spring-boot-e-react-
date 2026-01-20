-- database/init.sql

-- =========================
-- TABELA DE USUÁRIOS
-- =========================
CREATE TABLE IF NOT EXISTS "user" (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    username VARCHAR(100) NOT NULL UNIQUE,
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- =========================
-- TABELA DE DÍVIDAS
-- =========================
CREATE TABLE IF NOT EXISTS debt (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    description VARCHAR(255) NOT NULL,
    amount NUMERIC(10,2) NOT NULL,
    due_date DATE NOT NULL,
    paid BOOLEAN DEFAULT FALSE,
    user_id UUID NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_user
      FOREIGN KEY(user_id)
      REFERENCES "user"(id)
      ON DELETE CASCADE
);

-- =========================
-- DADOS DE TESTE
-- =========================
INSERT INTO "user" (username, email, password)
VALUES ('admin', 'admin@email.com', '123456')
ON CONFLICT (email) DO NOTHING;
