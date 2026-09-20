create database if not exists qts_biblioteca;
use qts_biblioteca;

-- Tabela Livro
create table Livro (
    idLivro int primary key auto_increment,
    Titulo_livro varchar(255) not null,
    Isbn varchar(17), 
    Ano_publicacao int, 
    Editora varchar(255),
    Quantidade int default 0,
    Quantidade_disponivel int default 0
);

INSERT INTO Livro
    (Titulo_livro, Isbn, Ano_publicacao, Editora, Quantidade, Quantidade_disponivel)
VALUES
    ('Harry Potter e a Pedra Filosofal', '9788532511010', 2000, 'Rocco', 5, 5),
    ('O Pequeno Príncipe', '9788522031440', 2009, 'Agir', 4, 4),
    ('Dom Casmurro', '9788520937119', 2016, 'Nova Fronteira', 3, 3),
    ('1984', '9788535914849', 2009, 'Companhia das Letras', 5, 5),
    ('O Hobbit', '9788595084742', 2019, 'HarperCollins Brasil', 4, 4),
    ('A Menina que Roubava Livros', '9788598078178', 2007, 'Intrínseca', 3, 3),
    ('Orgulho e Preconceito', '9788544001822', 2018, 'Martin Claret', 3, 3),
    ('Jogos Vorazes', '9788579800245', 2010, 'Rocco', 5, 5),
    ('Coraline', '9788551001205', 2018, 'Intrínseca', 4, 4),
    ('O Diário de Anne Frank', '9788501012540', 2019, 'Record', 3, 3);
    

-- Tabela Autor
create table Autor (
    idAutor int primary key auto_increment, -- Padronizado sem underline para igualar as FKs
    Nome_autor varchar(100) not null,
    Sobrenome_autor varchar(100),
    Nacionalidade_autor varchar(100)
);

INSERT INTO Autor
    (Nome_autor, Sobrenome_autor, Nacionalidade_autor)
VALUES
    ('J. K.', 'Rowling', 'Britânica'),
    ('Antoine', 'de Saint-Exupéry', 'Francesa'),
    ('Machado', 'de Assis', 'Brasileira'),
    ('George', 'Orwell', 'Britânica'),
    ('J. R. R.', 'Tolkien', 'Britânica'),
    ('Markus', 'Zusak', 'Australiana'),
    ('Jane', 'Austen', 'Britânica'),
    ('Suzanne', 'Collins', 'Norte-americana'),
    ('Neil', 'Gaiman', 'Britânica'),
    ('Anne', 'Frank', 'Alemã');

-- Tabela de Associação: Livro e Autor (N:M)
create table Livro_Autor (
    idAutor int not null,
    idLivro int not null,
    primary key (idAutor, idLivro), -- Chave primária composta para evitar duplicatas
    foreign key (idAutor) references Autor(idAutor) on delete cascade,
    foreign key (idLivro) references Livro(idLivro) on delete cascade
);

INSERT INTO Livro_Autor (idAutor, idLivro)
VALUES
    (1, 1), -- Harry Potter e a Pedra Filosofal - J. K. Rowling
    (2, 2), -- O Pequeno Príncipe - Antoine de Saint-Exupéry
    (3, 3), -- Dom Casmurro - Machado de Assis
    (4, 4), -- 1984 - George Orwell
    (5, 5), -- O Hobbit - J. R. R. Tolkien
    (6, 6), -- A Menina que Roubava Livros - Markus Zusak
    (7, 7), -- Orgulho e Preconceito - Jane Austen
    (8, 8), -- Jogos Vorazes - Suzanne Collins
    (9, 9), -- Coraline - Neil Gaiman
    (10, 10); -- O Diário de Anne Frank - Anne Frank

-- Tabela Categoria
create table Categoria (
    idCategoria int primary key auto_increment,
    nome_Categoria varchar(100) not null,
    Descricao_categoria text
);

INSERT INTO Categoria (nome_Categoria, Descricao_categoria)
VALUES
    ('Fantasia', 'Obras que apresentam elementos mágicos, sobrenaturais ou mundos imaginários.'),
    ('Literatura Infantil', 'Obras destinadas principalmente ao público infantil.'),
    ('Literatura Brasileira', 'Obras de autores brasileiros ou pertencentes à literatura brasileira.'),
    ('Ficção Científica', 'Obras que exploram ciência, tecnologia e possíveis futuros ou realidades alternativas.'),
    ('Ficção', 'Narrativas literárias de caráter imaginativo ou não baseadas em fatos reais.'),
    ('Romance', 'Obras centradas em relacionamentos, sentimentos ou conflitos amorosos.'),
    ('Distopia', 'Obras que apresentam sociedades imaginárias marcadas por condições opressivas ou problemáticas.'),
    ('Clássico', 'Obras reconhecidas por sua importância e influência na literatura.'),
    ('Biografia', 'Obras que apresentam a história da vida de uma pessoa.'),
    ('Literatura Juvenil', 'Obras destinadas principalmente ao público adolescente e jovem.');

-- Tabela de Associação: Livro e Categoria (N:M) - CORRIGIDA
create table Livro_Categoria (
    idCategoria int not null,
    idLivro int not null,
    primary key (idCategoria, idLivro), -- Chave primária composta
    foreign key (idCategoria) references Categoria(idCategoria) on delete cascade,
    foreign key (idLivro) references Livro(idLivro) on delete cascade
);

INSERT INTO Livro_Categoria (idCategoria, idLivro)
VALUES
    (1, 1),  -- Harry Potter e a Pedra Filosofal -> Fantasia
    (2, 1),  -- Harry Potter e a Pedra Filosofal -> Literatura Infantil

    (2, 2),  -- O Pequeno Príncipe -> Literatura Brasileira
    (5, 2),  -- O Pequeno Príncipe -> Ficção

    (3, 3),  -- Dom Casmurro -> Literatura Brasileira
    (8, 3),  -- Dom Casmurro -> Clássico

    (7, 4),  -- 1984 -> Distopia
    (5, 4),  -- 1984 -> Ficção

    (1, 5),  -- O Hobbit -> Fantasia
    (8, 5),  -- O Hobbit -> Clássico

    (5, 6),  -- A Menina que Roubava Livros -> Ficção
    (10, 6), -- A Menina que Roubava Livros -> Literatura Juvenil

    (6, 7),  -- Orgulho e Preconceito -> Romance
    (8, 7),  -- Orgulho e Preconceito -> Clássico

    (1, 8),  -- Jogos Vorazes -> Fantasia
    (7, 8),  -- Jogos Vorazes -> Distopia
    (10, 8), -- Jogos Vorazes -> Literatura Juvenil

    (1, 9),  -- Coraline -> Fantasia
    (10, 9), -- Coraline -> Literatura Juvenil

    (5, 10), -- O Diário de Anne Frank -> Ficção
    (8, 10); -- O Diário de Anne Frank -> Clássico

-- Tabela Usuário (Para completar o cenário citado)
create table Usuario (
    idUsuario int primary key auto_increment,
    nomeUsuario varchar(100) not null,
    sobrenomeUsuario varchar(100),
    emailUsuario varchar(150),
    telefoneUsuario varchar(20),
    dataCadastro date default (current_date),
    statusUsuario varchar(20) default 'Ativo'
);


INSERT INTO Usuario
    (nomeUsuario, sobrenomeUsuario, emailUsuario, telefoneUsuario, dataCadastro, statusUsuario)
VALUES
    ('Ana', 'Silva', 'ana.silva@email.com', '11987654321', '2026-08-01', 'Ativo'),
    ('Lucas', 'Oliveira', 'lucas.oliveira@email.com', '11976543210', '2026-08-05', 'Ativo'),
    ('Mariana', 'Santos', 'mariana.santos@email.com', '11965432109', '2026-08-10', 'Ativo'),
    ('Gabriel', 'Souza', 'gabriel.souza@email.com', '11954321098', '2026-08-12', 'Ativo'),
    ('Beatriz', 'Costa', 'beatriz.costa@email.com', '11943210987', '2026-08-15', 'Ativo'),
    ('Rafael', 'Pereira', 'rafael.pereira@email.com', '11932109876', '2026-08-18', 'Ativo'),
    ('Julia', 'Almeida', 'julia.almeida@email.com', '11921098765', '2026-08-20', 'Ativo'),
    ('Matheus', 'Rodrigues', 'matheus.rodrigues@email.com', '11910987654', '2026-08-22', 'Ativo');

-- Tabela Empréstimo (Associação entre Livro e Usuário)
create table Emprestimo (
    idEmprestimo int primary key auto_increment,
    idLivro int not null,
    idUsuario int not null,
    dataEmprestimo date not null,
    dataDevolucaoPrevista date not null,
    dataDevolucaoReal date,
    statusEmprestimo varchar(20) default 'Ativo',
    foreign key (idLivro) references Livro(idLivro),
    foreign key (idUsuario) references Usuario(idUsuario)
);


INSERT INTO Emprestimo
    (idLivro, idUsuario, dataEmprestimo, dataDevolucaoPrevista, dataDevolucaoReal, statusEmprestimo)
VALUES
    (1, 1, '2026-09-01', '2026-09-15', null, 'Ativo'),
    (4, 2, '2026-09-02', '2026-09-16', null, 'Ativo'),
    (5, 3, '2026-09-03', '2026-09-17', null, 'Ativo'),
    (8, 4, '2026-09-04', '2026-09-18', null, 'Ativo'),
    (3, 5, '2026-08-10', '2026-08-24', '2026-08-22', 'Devolvido'),
    (7, 6, '2026-08-12', '2026-08-26', '2026-08-25', 'Devolvido'),
    (9, 7, '2026-08-15', '2026-08-29', '2026-08-28', 'Devolvido'),
    (2, 8, '2026-08-20', '2026-09-03', '2026-09-02', 'Devolvido');


create table Funcionario (
    idFuncionario int primary key auto_increment,
    nomeFuncionario varchar(100) not null,
    sobrenomeFuncionario varchar(100) not null,
    codigoFuncionario varchar(30) not null unique,
    senhaFuncionario varchar(255) not null
);
    
    SELECT * 
FROM Funcionario;

-- drop table Funcionario;
    
    UPDATE Livro
SET Quantidade_disponivel = Quantidade_disponivel - 1
WHERE idLivro IN (1, 4, 5, 8);

SELECT * 
FROM livro;