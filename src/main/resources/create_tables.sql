-- ================================
-- CRIAÇÃO DAS TABELAS
-- ================================

-- 1) Tabela Fornecedor
CREATE TABLE Fornecedor (
    id_fornecedor       INT             NOT NULL,
    nome                VARCHAR(100),
    cnpj                VARCHAR(20),
    contato             VARCHAR(100),
    PRIMARY KEY (id_fornecedor)
);

-- 2) Tabela Cliente
CREATE TABLE Cliente (
    id_cliente          INT             NOT NULL,
    nome                VARCHAR(100),
    email               VARCHAR(100),
    telefone            VARCHAR(20),
    endereco            VARCHAR(200),
    PRIMARY KEY (id_cliente)
);

-- 3) Tabela Produto (relaciona com Fornecedor)
CREATE TABLE Produto (
    id_produto          INT             NOT NULL,
    nome                VARCHAR(100),
    preco               DECIMAL(10,2),
    descricao           VARCHAR(200),
    categoria           VARCHAR(100),
    id_fornecedor       INT             NOT NULL,
    PRIMARY KEY (id_produto),
    CONSTRAINT fk_produto_fornecedor
        FOREIGN KEY (id_fornecedor)
        REFERENCES Fornecedor(id_fornecedor)
);

-- 4) Tabela Estoque (1:1 com Produto, para controlar quantidades)
CREATE TABLE Estoque (
    id_produto          INT             NOT NULL,
    quantidade_disponivel INT           NOT NULL,
    PRIMARY KEY (id_produto),
    CONSTRAINT fk_estoque_produto
        FOREIGN KEY (id_produto)
        REFERENCES Produto(id_produto)
);

-- 5) Tabela Pedido (relaciona com Cliente)
CREATE TABLE Pedido (
    id_pedido           INT             NOT NULL,
    data                DATE,
    status              VARCHAR(50),
    id_cliente          INT             NOT NULL,
    PRIMARY KEY (id_pedido),
    CONSTRAINT fk_pedido_cliente
        FOREIGN KEY (id_cliente)
        REFERENCES Cliente(id_cliente)
);

-- 6) Tabela ItemPedido (tabela associativa entre Pedido e Produto)
CREATE TABLE ItemPedido (
    id_pedido           INT             NOT NULL,
    id_produto          INT             NOT NULL,
    quantidade          INT             NOT NULL,
    preco_unitario      DECIMAL(10,2),
    PRIMARY KEY (id_pedido, id_produto),
    CONSTRAINT fk_itempedido_pedido
        FOREIGN KEY (id_pedido)
        REFERENCES Pedido(id_pedido),
    CONSTRAINT fk_itempedido_produto
        FOREIGN KEY (id_produto)
        REFERENCES Produto(id_produto)
);

-- 7) Tabela Entrega (relaciona com Pedido)
CREATE TABLE Entrega (
    id_entrega          INT             NOT NULL,
    id_pedido           INT             NOT NULL,
    data_entrega        DATE,
    status              VARCHAR(50),
    PRIMARY KEY (id_entrega),
    CONSTRAINT fk_entrega_pedido
        FOREIGN KEY (id_pedido)
        REFERENCES Pedido(id_pedido)
);
