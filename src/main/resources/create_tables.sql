CREATE TABLE cliente (
    id_cliente     SERIAL PRIMARY KEY,
    nome           VARCHAR(100) NOT NULL,
    telefone       VARCHAR(20),
    email          VARCHAR(150) NOT NULL UNIQUE,
    senha          VARCHAR(100) NOT NULL
);

CREATE TABLE endereco_cliente (
    id_endereco    SERIAL PRIMARY KEY,
    rua            VARCHAR(100),
    numero         VARCHAR(10),
    cidade         VARCHAR(50),
    estado         VARCHAR(30),
    cep            VARCHAR(15),
    complemento    VARCHAR(50),
    id_cliente     INT UNIQUE NOT NULL,
    CONSTRAINT fk_endereco_cliente
        FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

CREATE TABLE fornecedor (
    id_fornecedor  SERIAL PRIMARY KEY,
    nome           VARCHAR(100) NOT NULL,
    contato        VARCHAR(100)
);

CREATE TABLE categoria (
    id_categoria   SERIAL PRIMARY KEY,
    descricao      VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE produto (
    id_produto     SERIAL PRIMARY KEY,
    nome           VARCHAR(100) NOT NULL,
    preco          NUMERIC(10,2) NOT NULL CHECK (preco >= 0),
    estoque        INT NOT NULL DEFAULT 0 CHECK (estoque >= 0),
    descricao      TEXT,
    id_categoria   INT NOT NULL,
    id_fornecedor  INT NOT NULL,
    CONSTRAINT fk_produto_categoria
        FOREIGN KEY (id_categoria) REFERENCES categoria(id_categoria),
    CONSTRAINT fk_produto_fornecedor
        FOREIGN KEY (id_fornecedor) REFERENCES fornecedor(id_fornecedor)
);

CREATE TABLE compra (
    id_compra      SERIAL PRIMARY KEY,
    status         VARCHAR(50) NOT NULL DEFAULT 'Em aberto',
    valor_total    NUMERIC(10,2) NOT NULL DEFAULT 0 CHECK (valor_total >= 0),
    data_pedido    DATE NOT NULL DEFAULT CURRENT_DATE,
    data_entrega   DATE,
    id_cliente     INT NOT NULL,
    CONSTRAINT fk_compra_cliente
        FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

CREATE TABLE endereco_entrega (
    id_endereco    SERIAL PRIMARY KEY,
    rua            VARCHAR(100),
    numero         VARCHAR(10),
    cidade         VARCHAR(50),
    estado         VARCHAR(30),
    cep            VARCHAR(15),
    complemento    VARCHAR(50),
    id_compra      INT UNIQUE NOT NULL,
    CONSTRAINT fk_endereco_entrega_compra
        FOREIGN KEY (id_compra) REFERENCES compra(id_compra)
);

CREATE TABLE item_compra (
    id_compra      INT NOT NULL,
    id_produto     INT NOT NULL,
    quantidade     INT NOT NULL CHECK (quantidade > 0),
    preco_unitario NUMERIC(10,2) NOT NULL CHECK (preco_unitario >= 0),
    PRIMARY KEY (id_compra, id_produto),
    CONSTRAINT fk_item_compra_compra
        FOREIGN KEY (id_compra) REFERENCES compra(id_compra),
    CONSTRAINT fk_item_compra_produto
        FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

CREATE TABLE troca_devolucao (
    id_solicitacao   SERIAL PRIMARY KEY,
    data_solicitacao DATE NOT NULL DEFAULT CURRENT_DATE,
    motivo           TEXT,
    status           VARCHAR(50) NOT NULL DEFAULT 'Em análise',
    id_compra        INT NOT NULL,
    CONSTRAINT fk_troca_compra
        FOREIGN KEY (id_compra) REFERENCES compra(id_compra)
);

CREATE TABLE avaliacao (
    id_avaliacao SERIAL PRIMARY KEY,
    nota         INT NOT NULL CHECK (nota BETWEEN 1 AND 5),
    comentario   TEXT,
    data         DATE NOT NULL DEFAULT CURRENT_DATE,
    id_cliente   INT NOT NULL,
    id_compra    INT NOT NULL,
    CONSTRAINT fk_avaliacao_cliente
        FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
    CONSTRAINT fk_avaliacao_compra
        FOREIGN KEY (id_compra) REFERENCES compra(id_compra)
);

-- Função para deduzir o estoque ao inserir um item de compra
CREATE OR REPLACE FUNCTION fn_deduzir_estoque()
RETURNS TRIGGER AS $$
BEGIN
    -- Verifica se há estoque suficiente para o produto
    IF (SELECT estoque FROM produto WHERE id_produto = NEW.id_produto) < NEW.quantidade THEN
        RAISE EXCEPTION 'Estoque insuficiente para o produto %', NEW.id_produto;
    END IF;

    -- Deduz a quantidade do estoque
    UPDATE produto
       SET estoque = estoque - NEW.quantidade
     WHERE id_produto = NEW.id_produto;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para deduzir o estoque
CREATE TRIGGER trg_deduzir_estoque
AFTER INSERT ON item_compra
FOR EACH ROW
EXECUTE PROCEDURE fn_deduzir_estoque();

-- Função para recalcular o valor total da compra sempre que houver alteração nos itens
CREATE OR REPLACE FUNCTION fn_recalcular_total()
RETURNS TRIGGER AS $$
BEGIN
    UPDATE compra
       SET valor_total = (
         SELECT COALESCE(SUM(ic.quantidade * ic.preco_unitario), 0)
         FROM item_compra ic
         WHERE ic.id_compra = NEW.id_compra
       )
     WHERE id_compra = NEW.id_compra;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para recalcular o valor total da compra
CREATE TRIGGER trg_recalcular_total
AFTER INSERT OR UPDATE OR DELETE ON item_compra
FOR EACH ROW
EXECUTE PROCEDURE fn_recalcular_total();

-- Função para restituir o estoque se o status da compra for alterado para 'Cancelado' ou 'Devolvido'
CREATE OR REPLACE FUNCTION fn_restituir_estoque()
RETURNS TRIGGER AS $$
BEGIN
    IF NEW.status IN ('Cancelado', 'Devolvido') AND OLD.status NOT IN ('Cancelado', 'Devolvido') THEN
        -- Para cada item da compra, restitui o estoque
        UPDATE produto p
           SET estoque = estoque + ic.quantidade
          FROM item_compra ic
         WHERE ic.id_compra = OLD.id_compra
           AND ic.id_produto = p.id_produto;
    END IF;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

-- Trigger para restituir o estoque
CREATE TRIGGER trg_restituir_estoque
AFTER UPDATE ON compra
FOR EACH ROW
WHEN (OLD.status IS DISTINCT FROM NEW.status)
EXECUTE PROCEDURE fn_restituir_estoque();
