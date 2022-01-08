CREATE TABLE evento (
    nome varchar(128),
    data varchar(128)
);

CREATE TABLE participante (
    dorsal SERIAL, 
    nome varchar(128), 
    genero varchar(128), 
    escalao varchar(128), 
    evento varchar(128)
);

CREATE TABLE tempo (
    dorsal integer,
    evento varchar(128),
    tempo integer,
    genero varchar(128), 
    escalao varchar(128) 
);