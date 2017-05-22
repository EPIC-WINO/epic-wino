-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2017-05-22 01:05:46.364

-- tables
-- Table: asignaturas
CREATE TABLE asignaturas (
    id int  NOT NULL,
    nombre varchar(50)  NOT NULL,
    CONSTRAINT asignaturas_pk PRIMARY KEY (id)
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

COMMENT ON TABLE materias IS 'El id es el mnemonico de la materia';

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

COMMENT ON TABLE programas IS 'La duración se mide en meses ';

-- Table: programas_asignaturas
CREATE TABLE programas_asignaturas (
    programa_id int  NOT NULL,
    asignatura_id int  NOT NULL,
    CONSTRAINT programas_asignaturas_pk PRIMARY KEY (programa_id,asignatura_id)
);

-- Table: programas_grupos
CREATE TABLE programas_grupos (
    programa_id int  NOT NULL,
    materia_id varchar(4)  NOT NULL,
    periodo int  NOT NULL,
    cohorte int  NOT NULL,
    CONSTRAINT programas_grupos_pk PRIMARY KEY (programa_id,materia_id,periodo)
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
    asignatura_id int  NOT NULL,
    requisito int  NOT NULL,
    prerrequisito boolean  NOT NULL,
    CONSTRAINT requisitos_pk PRIMARY KEY (asignatura_id,requisito)
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
-- Reference: Asignaturas_Grupos_GrupoDeMateria (table: programas_grupos)
ALTER TABLE programas_grupos ADD CONSTRAINT Asignaturas_Grupos_GrupoDeMateria
    FOREIGN KEY (materia_id, periodo)
    REFERENCES gruposdematerias (materia_id, periodo)
;

-- Reference: Clase_RecursoConcedido (table: recursos_concedidos)
ALTER TABLE recursos_concedidos ADD CONSTRAINT Clase_RecursoConcedido
    FOREIGN KEY (clase_id)
    REFERENCES clases (id)  
;

-- Reference: Clases_GrupoDeMateria (table: clases)
ALTER TABLE clases ADD CONSTRAINT Clases_GrupoDeMateria
    FOREIGN KEY (materia_id, periodo)
    REFERENCES gruposdematerias (materia_id, periodo)  
;

-- Reference: Comite_Reunion (table: reuniones)
ALTER TABLE reuniones ADD CONSTRAINT Comite_Reunion
    FOREIGN KEY (comite_id)
    REFERENCES comites (id)  
;

-- Reference: GrupoDeMateria_Materias (table: gruposdematerias)
ALTER TABLE gruposdematerias ADD CONSTRAINT GrupoDeMateria_Materias
    FOREIGN KEY (materia_id)
    REFERENCES materias (id)  
;

-- Reference: GrupoDeMateria_Profesores (table: gruposdematerias)
ALTER TABLE gruposdematerias ADD CONSTRAINT GrupoDeMateria_Profesores
    FOREIGN KEY (profesor_id)
    REFERENCES profesores (id)
;

-- Reference: HorarioProfesores_Profesores (table: horario_profesores)
ALTER TABLE horario_profesores ADD CONSTRAINT HorarioProfesores_Profesores
    FOREIGN KEY (profesor_id)
    REFERENCES profesores (id)
;

-- Reference: Materias_Asignaturas (table: materias)
ALTER TABLE materias ADD CONSTRAINT Materias_Asignaturas
    FOREIGN KEY (asignaturas_id)
    REFERENCES asignaturas (id)
;

-- Reference: Profesor_Comite_Comite (table: profesores_comite)
ALTER TABLE profesores_comite ADD CONSTRAINT Profesor_Comite_Comite
    FOREIGN KEY (comite_id)
    REFERENCES comites (id)
;

-- Reference: Profesor_Comite_Profesor (table: profesores_comite)
ALTER TABLE profesores_comite ADD CONSTRAINT Profesor_Comite_Profesor
    FOREIGN KEY (profesor_id)
    REFERENCES profesores (id)
;

-- Reference: RecursoConcedido_Recurso (table: recursos_concedidos)
ALTER TABLE recursos_concedidos ADD CONSTRAINT RecursoConcedido_Recurso
    FOREIGN KEY (recurso_id)
    REFERENCES recursos (id)
;

-- Reference: Requisito_asignaturas (table: requisitos)
ALTER TABLE requisitos ADD CONSTRAINT Requisito_asignaturas
    FOREIGN KEY (asignatura_id)
    REFERENCES asignaturas (id)
;

-- Reference: Requisito_asignaturas2 (table: requisitos)
ALTER TABLE requisitos ADD CONSTRAINT Requisito_asignaturas2
    FOREIGN KEY (requisito)
    REFERENCES asignaturas (id)
;

-- Reference: asignaturas_grupos_programas (table: programas_grupos)
ALTER TABLE programas_grupos ADD CONSTRAINT asignaturas_grupos_programas
    FOREIGN KEY (programa_id)
    REFERENCES programas (id)
;

-- Reference: programas_asignaturas_asignaturas (table: programas_asignaturas)
ALTER TABLE programas_asignaturas ADD CONSTRAINT programas_asignaturas_asignaturas
    FOREIGN KEY (asignatura_id)
    REFERENCES asignaturas (id)
;

-- Reference: programas_asignaturas_programas (table: programas_asignaturas)
ALTER TABLE programas_asignaturas ADD CONSTRAINT programas_asignaturas_programas
    FOREIGN KEY (programa_id)
    REFERENCES programas (id)
;

-- End of file.