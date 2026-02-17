
-- Inserindo os dados

--Cargos
INSERT INTO cargos (id_cargo, nome_cargo) VALUES (1, 'Analista');
INSERT INTO cargos (id_cargo, nome_cargo) VALUES (2, 'Desenvolvedor(a)');

--Setores
INSERT INTO setores (id_setor, nome_setor) VALUES (1, 'Contabilidade');
INSERT INTO setores (id_setor, nome_setor) VALUES (2, 'TI');

--Cidades
INSERT INTO cidades (id_cidade, nome_cidade) VALUES (1, 'Brasília');
INSERT INTO cidades (id_cidade, nome_cidade) VALUES (2, 'Goiás');

--Funcionários
INSERT INTO funcionarios (id_funcionario, nome_funcionario, ativo_funcionario, fk_cargo_funcionario, fk_setor_funcionario, date_create_funcionario, date_update_funcionario)
VALUES (1, 'Gabriel', false, 1, 1, EXTRACT(EPOCH FROM NOW())::BIGINT, EXTRACT(EPOCH FROM NOW())::BIGINT);

--Endereços
INSERT INTO enderecos (id_endereco, cep_endereco, fk_cidade_endereco, bairro_endereco, complemento_endereco, fk_funcionario_endereco)
VALUES (1, 14580000, 1, 'Guará', 'Rua m, casa 756', 1);


-- Alterando SEQUENCE
ALTER SEQUENCE cargo_sequence RESTART with 3;
ALTER SEQUENCE setor_sequence RESTART with 3;
ALTER SEQUENCE cidade_sequence RESTART with 3;
ALTER SEQUENCE funcionario_sequence RESTART with 2;
ALTER SEQUENCE endereco_sequence RESTART with 2;