create table clientes (
    id bigserial primary key,
    uuid uuid not null unique,
    nome varchar(120) not null,
    cpf varchar(14) not null unique,
    email varchar(150) not null unique,
    telefone varchar(20) not null
);

create table veterinarios (
    id bigserial primary key,
    uuid uuid not null unique,
    nome varchar(120) not null,
    email varchar(150) not null unique
);

create table pets (
    id bigserial primary key,
    uuid uuid not null unique,
    nome varchar(80) not null,
    especie varchar(30) not null,
    raca varchar(80),
    cliente_id bigint not null,
    constraint fk_pets_cliente foreign key (cliente_id) references clientes(id)
);

create table consultas (
    id bigserial primary key,
    uuid uuid not null unique,
    data_hora timestamp not null,
    motivo varchar(255) not null,
    diagnostico text,
    valor numeric(10, 2) not null,
    status varchar(20) not null,
    pet_id bigint not null,
    veterinario_id bigint not null,
    constraint fk_consultas_pet foreign key (pet_id) references pets(id),
    constraint fk_consultas_veterinario foreign key (veterinario_id) references veterinarios(id)
);
