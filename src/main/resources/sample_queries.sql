-- CONSULTAS PARA EXEMPLO --

-- Total de Vendas por Cliente
SELECT
    c.id_cliente,
    c.nome,
    SUM(ip.quantidade * ip.preco_unitario) AS total_vendas
FROM Cliente c
         JOIN Pedido p ON c.id_cliente = p.id_cliente
         JOIN ItemPedido ip ON p.id_pedido = ip.id_pedido
WHERE p.status = 'Finalizado'
GROUP BY c.id_cliente, c.nome
ORDER BY total_vendas DESC;

-- Produtos com Baixo Estoque
SELECT
    p.id_produto,
    p.nome,
    e.quantidade_disponivel
FROM Produto p
         JOIN Estoque e ON p.id_produto = e.id_produto
WHERE e.quantidade_disponivel < 30
ORDER BY e.quantidade_disponivel;

-- Ranking dos Produtos Mais Vendidos
SELECT
    p.id_produto,
    p.nome,
    SUM(ip.quantidade) AS total_vendido
FROM Produto p
         JOIN ItemPedido ip ON p.id_produto = ip.id_produto
GROUP BY p.id_produto, p.nome
ORDER BY total_vendido DESC;

-- Pedidos Pendentes de Entrega
SELECT
    p.id_pedido,
    p.data,
    c.nome AS cliente,
    COALESCE(e.status, 'Sem entrega') AS status_entrega
FROM Pedido p
         JOIN Cliente c ON p.id_cliente = c.id_cliente
         LEFT JOIN Entrega e ON p.id_pedido = e.id_pedido
WHERE p.status <> 'Cancelado'
  AND (e.status IS NULL OR e.status <> 'Entregue')
ORDER BY p.data;

-- Faturamento Diário
SELECT
    p.data,
    SUM(ip.quantidade * ip.preco_unitario) AS faturamento_dia
FROM Pedido p
         JOIN ItemPedido ip ON p.id_pedido = ip.id_pedido
WHERE p.status = 'Finalizado'
GROUP BY p.data
ORDER BY p.data;

-- Detalhamento Completo dos Pedidos (OBS: No MySQL, acho que tem que substituir a função TO_CHAR para DATE_FORMAT(e.data_entrega, '%Y-%m-%d')

SELECT
    p.id_pedido,
    p.data,
    p.status AS status_pedido,
    c.nome AS cliente,
    COALESCE(TO_CHAR(e.data_entrega, 'YYYY-MM-DD'), 'Não agendado') AS data_entrega,
    COALESCE(e.status, 'Sem entrega') AS status_entrega
FROM Pedido p
         JOIN Cliente c ON p.id_cliente = c.id_cliente
         LEFT JOIN Entrega e ON p.id_pedido = e.id_pedido
ORDER BY p.data DESC;
