INSERT INTO tb_telefone(id, ddd, numero, tipo, created_at, updated_at, deleted_at) values
('66e3ad4c-dc01-4908-aeec-7ea73ecd3315', '43', '998452534', 'CELULAR', CURRENT_TIMESTAMP, NULL, NULL),
('2fc644fb-c098-49f1-a491-76cc6de92f6e', '44', '994856142', 'CELULAR', CURRENT_TIMESTAMP, NULL, NULL),
('c58edfd3-3ac9-43bf-8947-92dd84189328', '11', '994564854', 'CELULAR', CURRENT_TIMESTAMP, NULL, NULL) ON CONFLICT DO NOTHING;

INSERT INTO tb_endereco(id, logradouro, numero, cep, cidade, estado, complemento, created_at, updated_at, deleted_at) values
('fa8c0a64-625c-4917-85e1-f32291a0d739', 'Rua Javali', '22', '86707000', 'Arapongas', 'PR', null, CURRENT_TIMESTAMP, NULL, NULL),
('356fc596-5587-4ecf-95a3-a178fc10f85f', 'Rua Jaburu', '584', '56808000', 'Curitiba', 'PR', null, CURRENT_TIMESTAMP, NULL, NULL),
('3e6ecfc7-e7da-4eb2-9398-4b36337536a6', 'Rua Jaburu', '584', '56808000', 'Curitiba', 'PR', null, CURRENT_TIMESTAMP, NULL, NULL) ON CONFLICT DO NOTHING;

INSERT INTO tb_cliente(id, nome, cpf, telefone_id, endereco_id, data_nascimento, created_at, updated_at, deleted_at) values
('0542a825-57f0-4fc9-ab97-fc652263cd97', 'Leonardo Arruda', '57242635055', '66e3ad4c-dc01-4908-aeec-7ea73ecd3315', 'fa8c0a64-625c-4917-85e1-f32291a0d739', '05-08-1998', CURRENT_TIMESTAMP, NULL, NULL),
('3100be3a-9d80-423c-b16c-749ec749b5af', 'Victor Pietro', '61659915058', '2fc644fb-c098-49f1-a491-76cc6de92f6e', '356fc596-5587-4ecf-95a3-a178fc10f85f', '05-04-1995', CURRENT_TIMESTAMP, NULL, NULL),
('29006e7b-4d54-404a-902b-87364a4a59e8', 'Olavo Wilke', '24433917010', 'c58edfd3-3ac9-43bf-8947-92dd84189328', '3e6ecfc7-e7da-4eb2-9398-4b36337536a6', '05-08-1996', CURRENT_TIMESTAMP, NULL, NULL) ON CONFLICT DO NOTHING;

INSERT INTO tb_livro(id, titulo, autor_id, data_publicacao, editora, genero_literario, isbn, created_at, updated_at, deleted_at) values
('3b792c7a-c9bc-4e0d-8834-f3414a4ed3ad', 'Mein Kempf', '3404b937-e7f9-49bc-8230-da5f2b8c41f0', '1925-07-18', 'Eher Verlag', 'Doido', 'testeisbn', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL),
('5882eecd-d5f0-4c78-8544-e8c778c1f229', 'O Capital', '411a5759-08b2-4b32-a9cf-a368a4e68e5c', '1867-09-14','Editora', 'Comédia', 'testeisbn', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL ),
('79ecf4c4-bc61-4367-8ad4-10b60ab315ee', 'Como viver com testosterona baixa: um guia', 'bf5f2972-8d89-447d-8805-b8fea4051abd', '1969-03-08', 'LowT', 'Comédia',  'testeisbn', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, NULL) ON CONFLICT DO NOTHING;
