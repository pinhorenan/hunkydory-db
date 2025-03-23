-- ============================================================
-- DROP de tabelas existentes (na ordem adequada)
-- ============================================================
DROP TABLE IF EXISTS entrega CASCADE;
DROP TABLE IF EXISTS itempedido CASCADE;
DROP TABLE IF EXISTS pedido CASCADE;
DROP TABLE IF EXISTS estoque CASCADE;
DROP TABLE IF EXISTS produto CASCADE;
DROP TABLE IF EXISTS cliente CASCADE;
DROP TABLE IF EXISTS fornecedor CASCADE;

-- ============================================================
-- Criação das Tabelas
-- ============================================================

-- Tabela: fornecedor
-- Agora, usamos o CNPJ como chave primária, removendo o campo id_fornecedor
CREATE TABLE fornecedor (
                            cnpj VARCHAR(20) NOT NULL,
                            nome VARCHAR(100),
                            contato VARCHAR(100),
                            PRIMARY KEY (cnpj)
);

-- Tabela: cliente (permanece inalterada)
CREATE TABLE cliente (
                         id_cliente INT NOT NULL,
                         nome VARCHAR(100),
                         email VARCHAR(100),
                         telefone VARCHAR(20),
                         endereco VARCHAR(200),
                         PRIMARY KEY (id_cliente)
);

-- Tabela: produto
-- Alteramos a coluna de fornecedor para cnpj_fornecedor, que referencia a tabela fornecedor
CREATE TABLE produto (
                         id_produto INT NOT NULL,
                         nome VARCHAR(100),
                         preco DECIMAL(10,2),
                         descricao VARCHAR(200),
                         categoria VARCHAR(100),
                         cnpj_fornecedor VARCHAR(20) NOT NULL,
                         PRIMARY KEY (id_produto),
                         FOREIGN KEY (cnpj_fornecedor) REFERENCES fornecedor(cnpj)
);

-- Tabela: estoque
CREATE TABLE estoque (
                         id_produto INT NOT NULL,
                         quantidade_disponivel INT NOT NULL,
                         PRIMARY KEY (id_produto),
                         FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

-- Tabela: pedido
CREATE TABLE pedido (
                        id_pedido INT NOT NULL,
                        data DATE,
                        status VARCHAR(50),
                        id_cliente INT NOT NULL,
                        PRIMARY KEY (id_pedido),
                        FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente)
);

-- Tabela: itempedido
CREATE TABLE itempedido (
                            id_pedido INT NOT NULL,
                            id_produto INT NOT NULL,
                            quantidade INT NOT NULL,
                            preco_unitario DECIMAL(10,2),
                            PRIMARY KEY (id_pedido, id_produto),
                            FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido),
                            FOREIGN KEY (id_produto) REFERENCES produto(id_produto)
);

-- Tabela: entrega
CREATE TABLE entrega (
                         id_entrega INT NOT NULL,
                         id_pedido INT NOT NULL,
                         data_entrega DATE,
                         status VARCHAR(50),
                         PRIMARY KEY (id_entrega),
                         FOREIGN KEY (id_pedido) REFERENCES pedido(id_pedido)
);

-- ============================================================
-- Inserção de Dados Sintéticos
-- ============================================================

-- 1) Fornecedor (10 registros)
-- Agora, os valores são inseridos nas colunas (nome, cnpj, contato)
INSERT INTO fornecedor (nome, cnpj, contato) VALUES
                                                 ('Fornecedor Alpha', '00.111.222/0001-01', 'alpha@fornec.com'),
                                                 ('Fornecedor Beta', '00.111.222/0001-02', 'contato@beta.com'),
                                                 ('Fornecedor Gamma', '00.111.222/0001-03', 'gamma@fornec.com'),
                                                 ('Fornecedor Delta', '00.111.222/0001-04', 'delta@fornec.com'),
                                                 ('Fornecedor Epsilon', '00.111.222/0001-05', 'epsilon@fornec.com'),
                                                 ('Fornecedor Zeta', '00.111.222/0001-06', 'zeta@fornec.com'),
                                                 ('Fornecedor Eta', '00.111.222/0001-07', 'eta@fornec.com'),
                                                 ('Fornecedor Theta', '00.111.222/0001-08', 'theta@fornec.com'),
                                                 ('Fornecedor Iota', '00.111.222/0001-09', 'iota@fornec.com'),
                                                 ('Fornecedor Kappa', '00.111.222/0001-10', 'kappa@fornec.com');

-- 2) Cliente (20 registros)
INSERT INTO cliente (id_cliente, nome, email, telefone, endereco) VALUES
                                                                      (1,  'Alice Silva',         'alice.silva@example.com',    '(11) 91234-5678', 'Rua A, 123'),
                                                                      (2,  'Bruno Santos',        'bruno.santos@example.com',   '(11) 92345-6789', 'Av. B, 234'),
                                                                      (3,  'Carla Oliveira',      'carla.oliveira@example.com', '(11) 93456-7890', 'Rua C, 345'),
                                                                      (4,  'Diego Costa',         'diego.costa@example.com',    '(11) 94567-8901', 'Av. D, 456'),
                                                                      (5,  'Eduarda Lima',        'eduarda.lima@example.com',   '(11) 95678-9012', 'Rua E, 567'),
                                                                      (6,  'Fernando Rocha',      'fernando.rocha@example.com', '(11) 96789-0123', 'Av. F, 678'),
                                                                      (7,  'Gabriela Souza',      'gabriela.souza@example.com', '(11) 97890-1234', 'Rua G, 789'),
                                                                      (8,  'Henrique Martins',    'henrique.martins@example.com','(11) 98901-2345', 'Av. H, 890'),
                                                                      (9,  'Isabela Ferreira',    'isabela.ferreira@example.com','(11) 99012-3456', 'Rua I, 901'),
                                                                      (10, 'João Pereira',        'joao.pereira@example.com',   '(11) 90123-4567', 'Av. J, 101'),
                                                                      (11, 'Karina Alves',        'karina.alves@example.com',   '(11) 91234-5679', 'Rua K, 112'),
                                                                      (12, 'Leandro Carvalho',    'leandro.carvalho@example.com','(11) 92345-6780', 'Av. L, 123'),
                                                                      (13, 'Mariana Gomes',       'mariana.gomes@example.com',  '(11) 93456-7891', 'Rua M, 134'),
                                                                      (14, 'Nicolas Braga',       'nicolas.braga@example.com',  '(11) 94567-8902', 'Av. N, 145'),
                                                                      (15, 'Olivia Ribeiro',      'olivia.ribeiro@example.com', '(11) 95678-9013', 'Rua O, 156'),
                                                                      (16, 'Paulo Mendes',        'paulo.mendes@example.com',   '(11) 96789-0124', 'Av. P, 167'),
                                                                      (17, 'Quintina Silva',      'quintina.silva@example.com', '(11) 97890-1235', 'Rua Q, 178'),
                                                                      (18, 'Rafael Dias',         'rafael.dias@example.com',    '(11) 98901-2346', 'Av. R, 189'),
                                                                      (19, 'Sofia Teixeira',      'sofia.teixeira@example.com', '(11) 99012-3457', 'Rua S, 190'),
                                                                      (20, 'Tomás Almeida',       'tomas.almeida@example.com',  '(11) 90123-4568', 'Av. T, 201');

-- 3) Produto (30 registros)
-- Agora, o campo fornecedor passa a ser cnpj_fornecedor, utilizando os CNPJs definidos acima.
INSERT INTO produto (id_produto, nome, preco, descricao, categoria, cnpj_fornecedor) VALUES
                                                                                         (1,  'Camiseta Básica',           29.90,  'Camiseta de algodão básica',         'Roupas Masculinas', '00.111.222/0001-01'),
                                                                                         (2,  'Camiseta Estampada',        39.90,  'Camiseta com estampa moderna',       'Roupas Femininas', '00.111.222/0001-02'),
                                                                                         (3,  'Calça Jeans',               79.90,  'Calça jeans de corte reto',          'Roupas Masculinas', '00.111.222/0001-03'),
                                                                                         (4,  'Vestido Floral',            99.90,  'Vestido com estampa floral',         'Roupas Femininas', '00.111.222/0001-04'),
                                                                                         (5,  'Blusa de Frio',             69.90,  'Blusa de frio para dias frios',      'Agasalhos',        '00.111.222/0001-05'),
                                                                                         (6,  'Jaqueta de Couro',         149.90,  'Jaqueta de couro legítimo',          'Agasalhos',        '00.111.222/0001-06'),
                                                                                         (7,  'Saia Midi',                 59.90,  'Saia midi com corte evasê',          'Roupas Femininas', '00.111.222/0001-07'),
                                                                                         (8,  'Shorts Jeans',              49.90,  'Shorts jeans para o verão',          'Roupas Masculinas', '00.111.222/0001-08'),
                                                                                         (9,  'Calça de Moletom',          39.90,  'Calça de moletom confortável',       'Roupas Masculinas', '00.111.222/0001-09'),
                                                                                         (10, 'Legging Esportiva',         34.90,  'Legging para atividades físicas',    'Roupas Femininas', '00.111.222/0001-10'),
                                                                                         (11, 'Suéter de Tricô',           89.90,  'Suéter aconchegante de tricô',       'Agasalhos',        '00.111.222/0001-01'),
                                                                                         (12, 'Camisa Social',             79.90,  'Camisa social para ocasiões formais', 'Roupas Masculinas', '00.111.222/0001-02'),
                                                                                         (13, 'Blusa de Seda',            129.90,  'Blusa de seda elegante',             'Roupas Femininas', '00.111.222/0001-03'),
                                                                                         (14, 'Calça Legging',             59.90,  'Calça legging confortável',          'Roupas Femininas', '00.111.222/0001-04'),
                                                                                         (15, 'Camiseta Polo',             49.90,  'Camiseta polo clássica',             'Roupas Masculinas', '00.111.222/0001-05'),
                                                                                         (16, 'Camisa Jeans',             69.90,  'Camisa jeans casual',                'Roupas Masculinas', '00.111.222/0001-06'),
                                                                                         (17, 'Vestido Longo',            109.90,  'Vestido longo para festas',          'Roupas Femininas', '00.111.222/0001-07'),
                                                                                         (18, 'Blazer Casual',            119.90,  'Blazer casual para eventos',         'Roupas Masculinas', '00.111.222/0001-08'),
                                                                                         (19, 'Falda Midi',               54.90,  'Falda midi elegante',                'Roupas Femininas', '00.111.222/0001-09'),
                                                                                         (20, 'Macacão',                  89.90,  'Macacão confortável',                'Roupas Femininas', '00.111.222/0001-10'),
                                                                                         (21, 'Camiseta Polo Premium',    59.90,  'Camiseta polo de alta qualidade',      'Roupas Masculinas', '00.111.222/0001-01'),
                                                                                         (22, 'Casaco de Lã',            139.90,  'Casaco de lã para o inverno',         'Agasalhos',        '00.111.222/0001-02'),
                                                                                         (23, 'Regata Básica',            19.90,  'Regata básica para o verão',         'Roupas Masculinas', '00.111.222/0001-03'),
                                                                                         (24, 'Blusa Cropped',            44.90,  'Blusa cropped moderna',              'Roupas Femininas', '00.111.222/0001-04'),
                                                                                         (25, 'Calça Flare',              74.90,  'Calça flare estilosa',               'Roupas Femininas', '00.111.222/0001-05'),
                                                                                         (26, 'Shorts de Moletom',        39.90,  'Shorts confortáveis de moletom',     'Roupas Masculinas', '00.111.222/0001-06'),
                                                                                         (27, 'Suéter Oversized',         89.90,  'Suéter oversized para dias frios',   'Agasalhos',        '00.111.222/0001-07'),
                                                                                         (28, 'Camisa Polo Premium',      69.90,  'Camiseta polo premium',              'Roupas Masculinas', '00.111.222/0001-08'),
                                                                                         (29, 'Vestido Casual',           79.90,  'Vestido casual para o dia a dia',     'Roupas Femininas', '00.111.222/0001-09'),
                                                                                         (30, 'Blusa de Manga Longa',     49.90,  'Blusa com mangas longas',            'Roupas Femininas', '00.111.222/0001-10');

-- 4) Estoque (30 registros, um para cada produto)
INSERT INTO estoque (id_produto, quantidade_disponivel) VALUES
                                                            (1,  50),
                                                            (2,  30),
                                                            (3,  70),
                                                            (4,  40),
                                                            (5,  60),
                                                            (6,  20),
                                                            (7,  45),
                                                            (8,  80),
                                                            (9,  55),
                                                            (10, 65),
                                                            (11, 35),
                                                            (12, 75),
                                                            (13, 50),
                                                            (14, 60),
                                                            (15, 40),
                                                            (16, 70),
                                                            (17, 30),
                                                            (18, 55),
                                                            (19, 45),
                                                            (20, 50),
                                                            (21, 65),
                                                            (22, 40),
                                                            (23, 90),
                                                            (24, 35),
                                                            (25, 80),
                                                            (26, 60),
                                                            (27, 50),
                                                            (28, 45),
                                                            (29, 55),
                                                            (30, 70);

-- 5) Pedido (25 registros com datas e status variados)
INSERT INTO pedido (id_pedido, data, status, id_cliente) VALUES
                                                             (1,  '2025-03-01', 'Finalizado', 1),
                                                             (2,  '2025-03-02', 'Em aberto',  2),
                                                             (3,  '2025-03-03', 'Cancelado',  3),
                                                             (4,  '2025-03-04', 'Finalizado', 4),
                                                             (5,  '2025-03-05', 'Em aberto',  5),
                                                             (6,  '2025-03-06', 'Finalizado', 6),
                                                             (7,  '2025-03-07', 'Finalizado', 7),
                                                             (8,  '2025-03-08', 'Em aberto',  8),
                                                             (9,  '2025-03-09', 'Em aberto',  9),
                                                             (10, '2025-03-10', 'Finalizado', 10),
                                                             (11, '2025-03-11', 'Finalizado', 11),
                                                             (12, '2025-03-12', 'Em aberto',  12),
                                                             (13, '2025-03-13', 'Finalizado', 13),
                                                             (14, '2025-03-14', 'Cancelado',  14),
                                                             (15, '2025-03-15', 'Em aberto',  15),
                                                             (16, '2025-03-16', 'Finalizado', 16),
                                                             (17, '2025-03-17', 'Finalizado', 17),
                                                             (18, '2025-03-18', 'Em aberto',  18),
                                                             (19, '2025-03-19', 'Finalizado', 19),
                                                             (20, '2025-03-20', 'Finalizado', 20),
                                                             (21, '2025-03-21', 'Em aberto',  1),
                                                             (22, '2025-03-22', 'Finalizado', 2),
                                                             (23, '2025-03-23', 'Em aberto',  3),
                                                             (24, '2025-03-24', 'Finalizado', 4),
                                                             (25, '2025-03-25', 'Finalizado', 5);

-- 6) ItemPedido (aproximadamente 45 registros; 1 a 3 itens por pedido)
INSERT INTO itempedido (id_pedido, id_produto, quantidade, preco_unitario) VALUES
                                                                               (1,  1, 2,  29.90),
                                                                               (1,  3, 1,  79.90),
                                                                               (2,  2, 1,  39.90),
                                                                               (2,  5, 2,  69.90),
                                                                               (3,  4, 1,  99.90),
                                                                               (4,  6, 1, 149.90),
                                                                               (4,  7, 2,  59.90),
                                                                               (5,  8, 1,  49.90),
                                                                               (5,  9, 3,  39.90),
                                                                               (6, 10, 2,  34.90),
                                                                               (6, 12, 1,  79.90),
                                                                               (7, 11, 1,  89.90),
                                                                               (7, 13, 2, 129.90),
                                                                               (8, 14, 1,  59.90),
                                                                               (8, 15, 2,  49.90),
                                                                               (9, 16, 1,  69.90),
                                                                               (9, 17, 1, 109.90),
                                                                               (10, 18, 1, 119.90),
                                                                               (10, 19, 2,  54.90),
                                                                               (11, 20, 1,  89.90),
                                                                               (11, 21, 2,  59.90),
                                                                               (12, 22, 1, 139.90),
                                                                               (12, 23, 3,  19.90),
                                                                               (13, 24, 1,  44.90),
                                                                               (13, 25, 2,  74.90),
                                                                               (14, 26, 1,  39.90),
                                                                               (14, 27, 1,  89.90),
                                                                               (15, 28, 2,  69.90),
                                                                               (15, 29, 1,  79.90),
                                                                               (16, 30, 1,  49.90),
                                                                               (16,  1, 2,  29.90),
                                                                               (17,  2, 1,  39.90),
                                                                               (17,  3, 1,  79.90),
                                                                               (18,  4, 2,  99.90),
                                                                               (18,  5, 1,  69.90),
                                                                               (19,  6, 1, 149.90),
                                                                               (19,  7, 2,  59.90),
                                                                               (20,  8, 1,  49.90),
                                                                               (20,  9, 1,  39.90),
                                                                               (21, 10, 3,  34.90),
                                                                               (21, 11, 1,  89.90),
                                                                               (22, 12, 1,  79.90),
                                                                               (22, 13, 1, 129.90),
                                                                               (23, 14, 2,  59.90),
                                                                               (23, 15, 1,  49.90),
                                                                               (24, 16, 2,  69.90),
                                                                               (24, 17, 1, 109.90),
                                                                               (25, 18, 1, 119.90),
                                                                               (25, 19, 1,  54.90);

-- 7) Entrega (apenas para pedidos com status 'Finalizado')
INSERT INTO entrega (id_entrega, id_pedido, data_entrega, status) VALUES
                                                                      (1,  1,  '2025-03-03', 'Entregue'),
                                                                      (2,  4,  '2025-03-06', 'Entregue'),
                                                                      (3,  6,  '2025-03-08', 'Entregue'),
                                                                      (4,  7,  '2025-03-09', 'Entregue'),
                                                                      (5, 10,  '2025-03-12', 'Entregue'),
                                                                      (6, 11,  '2025-03-13', 'Entregue'),
                                                                      (7, 13,  '2025-03-15', 'Entregue'),
                                                                      (8, 16,  '2025-03-18', 'Entregue'),
                                                                      (9, 17,  '2025-03-19', 'Entregue'),
                                                                      (10, 19,  '2025-03-21', 'Entregue'),
                                                                      (11, 20,  '2025-03-22', 'Entregue'),
                                                                      (12, 22,  '2025-03-23', 'Entregue'),
                                                                      (13, 24,  '2025-03-25', 'Entregue'),
                                                                      (14, 25,  '2025-03-27', 'Entregue');
