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

-- Tabela Autor
create table Autor (
    idAutor int primary key auto_increment, -- Padronizado sem underline para igualar as FKs
    Nome_autor varchar(100) not null,
    Sobrenome_autor varchar(100),
    Nacionalidade_autor varchar(100)
);

-- Tabela de Associação: Livro e Autor (N:M)
create table Livro_Autor (
    idAutor int not null,
    idLivro int not null,
    primary key (idAutor, idLivro), -- Chave primária composta para evitar duplicatas
    foreign key (idAutor) references Autor(idAutor) on delete cascade,
    foreign key (idLivro) references Livro(idLivro) on delete cascade
);

-- Tabela Categoria
create table Categoria (
    idCategoria int primary key auto_increment,
    nome_Categoria varchar(100) not null,
    Descricao_categoria text
);

-- Tabela de Associação: Livro e Categoria (N:M) - CORRIGIDA
create table Livro_Categoria (
    idCategoria int not null,
    idLivro int not null,
    primary key (idCategoria, idLivro), -- Chave primária composta
    foreign key (idCategoria) references Categoria(idCategoria) on delete cascade,
    foreign key (idLivro) references Livro(idLivro) on delete cascade
);

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