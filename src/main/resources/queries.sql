-- Total de Vendas por Cliente (Compras finalizadas)
SELECT
    cli.id_cliente,
    cli.nome,
    SUM(ic.quantidade * ic.preco_unitario) AS total_vendas
FROM cliente cli
         JOIN compra cmp ON cli.id_cliente = cmp.id_cliente
         JOIN item_compra ic ON cmp.id_compra = ic.id_compra
WHERE cmp.status = 'Finalizado'
GROUP BY cli.id_cliente, cli.nome
ORDER BY total_vendas DESC;

-- Produtos com baixo estoque
SELECT
    id_produto,
    nome,
    estoque
FROM produto
WHERE estoque < 30
ORDER BY estoque;


-- Ranking dos Produtos mais vendidos
SELECT
    p.id_produto,
    p.nome,
    SUM(ic.quantidade) AS total_vendido
FROM produto p
         JOIN item_compra ic ON p.id_produto = ic.id_produto
GROUP BY p.id_produto, p.nome
ORDER BY total_vendido DESC;


-- Compras pendentes de entrega
SELECT
    cmp.id_compra,
    cmp.data_pedido AS data,
    cli.nome AS cliente,
    CASE
        WHEN ee.id_compra IS NULL THEN 'Sem entrega'
        ELSE 'Agendado'
        END AS entrega_status
FROM compra cmp
         JOIN cliente cli ON cmp.id_cliente = cli.id_cliente
         LEFT JOIN endereco_entrega ee ON cmp.id_compra = ee.id_compra
WHERE cmp.status NOT IN ('Cancelado', 'Devolvido')
ORDER BY cmp.data_pedido;

-- Faturamento Diário (Compras finalizadas)
SELECT
    cmp.data_pedido AS data,
    SUM(ic.quantidade * ic.preco_unitario) AS faturamento_dia
FROM compra cmp
         JOIN item_compra ic ON cmp.id_compra = ic.id_compra
WHERE cmp.status = 'Finalizado'
GROUP BY cmp.data_pedido
ORDER BY cmp.data_pedido;

-- Detalhamento Completo das Compras
SELECT
    cmp.id_compra,
    cmp.data_pedido AS data,
    cmp.status AS status_compra,
    cli.nome AS cliente,
    COALESCE(TO_CHAR(cmp.data_entrega, 'YYYY-MM-DD'), 'Não agendado') AS data_entrega,
    CASE
        WHEN ee.id_compra IS NULL THEN 'Sem entrega'
        ELSE 'Agendado'
        END AS entrega_status
FROM compra cmp
         JOIN cliente cli ON cmp.id_cliente = cli.id_cliente
         LEFT JOIN endereco_entrega ee ON cmp.id_compra = ee.id_compra
ORDER BY cmp.data_pedido DESC;
