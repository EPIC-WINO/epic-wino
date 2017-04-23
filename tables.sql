-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-04-20 16:11:42.77

-- tables
-- Table: Asignaturas
CREATE TABLE Asignaturas (
    id int  NOT NULL,
    nombre varchar(50)  NOT NULL,
    programa_id int  NOT NULL,
    CONSTRAINT Asignaturas_pk PRIMARY KEY (id)
);

-- Table: Clases
CREATE TABLE Clases (
    id int  NOT NULL,
    fecha date  NOT NULL,
    hora_inicio time  NOT NULL,
    hora_fin time  NOT NULL,
    materia_id varchar(4)  NOT NULL,
    CONSTRAINT Clases_pk PRIMARY KEY (id)
);

-- Table: Comites
CREATE TABLE Comites (
    id int  NOT NULL,
    nombre varchar(100)  NOT NULL,
    CONSTRAINT Comites_pk PRIMARY KEY (id)
);

-- Table: Materias
CREATE TABLE Materias (
    id varchar(4)  NOT NULL,
    nombre varchar(50)  NOT NULL,
    asignatura_id int  NOT NULL,
    creditos int  NOT NULL,
    descripcion varchar(400)  NOT NULL,
    profesor_id int  NOT NULL,
    periodo int  NOT NULL,
    cohorte int  NOT NULL,
    CONSTRAINT Materias_pk PRIMARY KEY (id)
);

-- Table: Profesores
CREATE TABLE Profesores (
    id int  NOT NULL,
    nombre varchar(100)  NOT NULL,
    CONSTRAINT Profesores_pk PRIMARY KEY (id)
);

-- Table: Profesores_Comite
CREATE TABLE Profesores_Comite (
    profesor_id int  NOT NULL,
    comite_id int  NOT NULL,
    CONSTRAINT Profesores_Comite_pk PRIMARY KEY (profesor_id,comite_id)
);

-- Table: Programas
CREATE TABLE Programas (
    id int  NOT NULL,
    nombre varchar(50)  NOT NULL,
    nivel varchar(16)  NOT NULL,
    duracion int  NOT NULL,
    jornada varchar(10)  NOT NULL,
    modalidad varchar(10)  NOT NULL,
    codigo_SNIES int  NOT NULL,
    CONSTRAINT Programas_pk PRIMARY KEY (id)
);

-- Table: Recursos
CREATE TABLE Recursos (
    id int  NOT NULL,
    nombre varchar(30)  NOT NULL,
    descripcion varchar(100)  NOT NULL,
    cantidad int  NOT NULL,
    categoria varchar(20)  NOT NULL,
    CONSTRAINT Recursos_pk PRIMARY KEY (id)
);

-- Table: Recursos_Concedidos
CREATE TABLE Recursos_Concedidos (
    clase_id int  NOT NULL,
    recurso_id int  NOT NULL,
    CONSTRAINT Recursos_concedidos_pk PRIMARY KEY (clase_id,recurso_id)
);

-- Table: Requisitos
CREATE TABLE Requisitos (
    materia_id varchar(4)  NOT NULL,
    requisito varchar(4)  NOT NULL,
    prerrequisito boolean  NOT NULL,
    CONSTRAINT Requisitos_pk PRIMARY KEY (materia_id,requisito)
);

-- Table: Reuniones
CREATE TABLE Reuniones (
    id int  NOT NULL,
    comite_id int  NOT NULL,
    fecha date  NOT NULL,
    hora_inicio time  NOT NULL,
    hora_fin time  NOT NULL,
    CONSTRAINT Reuniones_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: Asignatura_Programa (table: Asignaturas)
ALTER TABLE Asignaturas ADD CONSTRAINT Asignatura_Programa
    FOREIGN KEY (programa_id)
    REFERENCES Programas (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Clase_Materia (table: Clases)
ALTER TABLE Clases ADD CONSTRAINT Clase_Materia
    FOREIGN KEY (materia_id)
    REFERENCES Materias (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Clase_RecursoConcedido (table: Recursos_Concedidos)
ALTER TABLE Recursos_Concedidos ADD CONSTRAINT Clase_RecursoConcedido
    FOREIGN KEY (clase_id)
    REFERENCES Clases (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Comite_Reunion (table: Reuniones)
ALTER TABLE Reuniones ADD CONSTRAINT Comite_Reunion
    FOREIGN KEY (comite_id)
    REFERENCES Comites (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Materia_Asignatura (table: Materias)
ALTER TABLE Materias ADD CONSTRAINT Materia_Asignatura
    FOREIGN KEY (asignatura_id)
    REFERENCES Asignaturas (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Materia_Profesor (table: Materias)
ALTER TABLE Materias ADD CONSTRAINT Materia_Profesor
    FOREIGN KEY (profesor_id)
    REFERENCES Profesores (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Materia_Requisito (table: Requisitos)
ALTER TABLE Requisitos ADD CONSTRAINT Materia_Requisito
    FOREIGN KEY (requisito)
    REFERENCES Materias (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Profesor_Comite_Comite (table: Profesores_Comite)
ALTER TABLE Profesores_Comite ADD CONSTRAINT Profesor_Comite_Comite
    FOREIGN KEY (comite_id)
    REFERENCES Comites (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Profesor_Comite_Profesor (table: Profesores_Comite)
ALTER TABLE Profesores_Comite ADD CONSTRAINT Profesor_Comite_Profesor
    FOREIGN KEY (profesor_id)
    REFERENCES Profesores (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: RecursoConcedido_Recurso (table: Recursos_Concedidos)
ALTER TABLE Recursos_Concedidos ADD CONSTRAINT RecursoConcedido_Recurso
    FOREIGN KEY (recurso_id)
    REFERENCES Recursos (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Requisito_Materia (table: Requisitos)
ALTER TABLE Requisitos ADD CONSTRAINT Requisito_Materia
    FOREIGN KEY (materia_id)
    REFERENCES Materias (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- End of file.
