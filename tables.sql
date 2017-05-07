- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-05-07 01:21:04.67

-- tables
-- Table: asignaturas
CREATE TABLE asignaturas (
    id int  NOT NULL,
    nombre varchar(50)  NOT NULL,
    programa_id int  NOT NULL,
    CONSTRAINT asignaturas_pk PRIMARY KEY (id)
);

-- Table: asignaturas_grupos
CREATE TABLE asignaturas_grupos (
    asignaturas_id int  NOT NULL,
    materia_id varchar(4)  NOT NULL,
    periodo int  NOT NULL,
    cohorte int  NOT NULL,
    CONSTRAINT asignaturas_grupos_pk PRIMARY KEY (asignaturas_id,materia_id,periodo)
);

-- Table: clases
CREATE TABLE clases (
    id int  NOT NULL,
    fecha date  NOT NULL,
    hora_inicio time  NOT NULL,
    hora_fin time  NOT NULL,
    materia_id varchar(4)  NOT NULL,
    periodo int  NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);

-- Table: comites
CREATE TABLE comites (
    id int  NOT NULL,
    nombre varchar(100)  NOT NULL,
    CONSTRAINT comites_pk PRIMARY KEY (id)
);

-- Table: gruposdematerias
CREATE TABLE gruposdematerias (
    materia_id varchar(4)  NOT NULL,
    periodo int  NOT NULL,
    profesor_id int  NOT NULL,
    CONSTRAINT gruposdematerias_pk PRIMARY KEY (materia_id,periodo)
);

-- Table: horario_profesores
CREATE TABLE horario_profesores (
    dia date  NOT NULL,
    profesor_id int  NOT NULL,
    hora_inicio time  NOT NULL,
    hora_fin time  NOT NULL,
    CONSTRAINT horario_profesores_pk PRIMARY KEY (dia,profesor_id,hora_inicio)
);

-- Table: materias
CREATE TABLE materias (
    id varchar(4)  NOT NULL,
    nombre varchar(50)  NOT NULL,
    creditos int  NOT NULL,
    descripcion varchar(400)  NOT NULL,
    asignaturas_id int  NOT NULL,
    CONSTRAINT materias_pk PRIMARY KEY (id)
);

-- Table: profesores
CREATE TABLE profesores (
    id int  NOT NULL,
    nombre varchar(100)  NOT NULL,
    tipo varchar(2)  NOT NULL,
    correo varchar(100)  NOT NULL,
    telefono varchar(30)  NOT NULL,
    CONSTRAINT profesores_pk PRIMARY KEY (id)
);

-- Table: profesores_comite
CREATE TABLE profesores_comite (
    profesor_id int  NOT NULL,
    comite_id int  NOT NULL,
    CONSTRAINT profesores_comite_pk PRIMARY KEY (profesor_id,comite_id)
);

-- Table: programas
CREATE TABLE programas (
    id int  NOT NULL,
    nombre varchar(50)  NOT NULL,
    nivel varchar(16)  NOT NULL,
    duracion int  NOT NULL,
    jornada varchar(10)  NOT NULL,
    modalidad varchar(10)  NOT NULL,
    codigo_SNIES int  NOT NULL,
    CONSTRAINT id_programa PRIMARY KEY (id)
);

-- Table: recursos
CREATE TABLE recursos (
    id int  NOT NULL,
    nombre varchar(30)  NOT NULL,
    descripcion varchar(100)  NOT NULL,
    cantidad int  NOT NULL,
    categoria varchar(20)  NOT NULL,
    CONSTRAINT recursos_pk PRIMARY KEY (id)
);

-- Table: recursos_concedidos
CREATE TABLE recursos_concedidos (
    clase_id int  NOT NULL,
    recurso_id int  NOT NULL,
    CONSTRAINT id_clase PRIMARY KEY (clase_id,recurso_id)
);

-- Table: requisitos
CREATE TABLE requisitos (
    materia_id varchar(4)  NOT NULL,
    requisito varchar(4)  NOT NULL,
    prerrequisito boolean  NOT NULL,
    CONSTRAINT requisitos_pk PRIMARY KEY (materia_id,requisito)
);

-- Table: reuniones
CREATE TABLE reuniones (
    id int  NOT NULL,
    comite_id int  NOT NULL,
    fecha date  NOT NULL,
    hora_inicio time  NOT NULL,
    hora_fin time  NOT NULL,
    CONSTRAINT reuniones_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: Asignatura_Programa (table: asignaturas)
ALTER TABLE asignaturas ADD CONSTRAINT Asignatura_Programa
    FOREIGN KEY (programa_id)
    REFERENCES programas (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Asignaturas_Grupos_Asignaturas (table: asignaturas_grupos)
ALTER TABLE asignaturas_grupos ADD CONSTRAINT Asignaturas_Grupos_Asignaturas
    FOREIGN KEY (asignaturas_id)
    REFERENCES asignaturas (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Asignaturas_Grupos_GrupoDeMateria (table: asignaturas_grupos)
ALTER TABLE asignaturas_grupos ADD CONSTRAINT Asignaturas_Grupos_GrupoDeMateria
    FOREIGN KEY (materia_id, periodo)
    REFERENCES gruposdematerias (materia_id, periodo)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Clase_RecursoConcedido (table: recursos_concedidos)
ALTER TABLE recursos_concedidos ADD CONSTRAINT Clase_RecursoConcedido
    FOREIGN KEY (clase_id)
    REFERENCES clases (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Clases_GrupoDeMateria (table: clases)
ALTER TABLE clases ADD CONSTRAINT Clases_GrupoDeMateria
    FOREIGN KEY (materia_id, periodo)
    REFERENCES gruposdematerias (materia_id, periodo)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Comite_Reunion (table: reuniones)
ALTER TABLE reuniones ADD CONSTRAINT Comite_Reunion
    FOREIGN KEY (comite_id)
    REFERENCES comites (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: GrupoDeMateria_Materias (table: gruposdematerias)
ALTER TABLE gruposdematerias ADD CONSTRAINT GrupoDeMateria_Materias
    FOREIGN KEY (materia_id)
    REFERENCES materias (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: GrupoDeMateria_Profesores (table: gruposdematerias)
ALTER TABLE gruposdematerias ADD CONSTRAINT GrupoDeMateria_Profesores
    FOREIGN KEY (profesor_id)
    REFERENCES profesores (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: HorarioProfesores_Profesores (table: horario_profesores)
ALTER TABLE horario_profesores ADD CONSTRAINT HorarioProfesores_Profesores
    FOREIGN KEY (profesor_id)
    REFERENCES profesores (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Materia_Requisito (table: requisitos)
ALTER TABLE requisitos ADD CONSTRAINT Materia_Requisito
    FOREIGN KEY (requisito)
    REFERENCES materias (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Materias_Asignaturas (table: materias)
ALTER TABLE materias ADD CONSTRAINT Materias_Asignaturas
    FOREIGN KEY (asignaturas_id)
    REFERENCES asignaturas (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Profesor_Comite_Comite (table: profesores_comite)
ALTER TABLE profesores_comite ADD CONSTRAINT Profesor_Comite_Comite
    FOREIGN KEY (comite_id)
    REFERENCES comites (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Profesor_Comite_Profesor (table: profesores_comite)
ALTER TABLE profesores_comite ADD CONSTRAINT Profesor_Comite_Profesor
    FOREIGN KEY (profesor_id)
    REFERENCES profesores (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: RecursoConcedido_Recurso (table: recursos_concedidos)
ALTER TABLE recursos_concedidos ADD CONSTRAINT RecursoConcedido_Recurso
    FOREIGN KEY (recurso_id)
    REFERENCES recursos (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;

-- Reference: Requisito_Materia (table: requisitos)
ALTER TABLE requisitos ADD CONSTRAINT Requisito_Materia
    FOREIGN KEY (materia_id)
    REFERENCES materias (id)  
    NOT DEFERRABLE 
    INITIALLY IMMEDIATE
;