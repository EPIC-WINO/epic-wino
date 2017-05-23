**EPIC-WINO**
=========
#### **Periodo Académico: 2017-1**
#### **Curso: Proceso De Desarrollo De Software**
#### **Integrantes:**
 - Maria Camila Gomez Ramirez - ***Scrum team member***
 - Fabian Augusto Ardila Rodriguez - ***Scrum team member***
 - Alejandro Anzola Avila - ***Scrum Master***
 - Juan Jose Andrade Pardo - ***Scrum team member***
 - Jossie Esteban Murcia Triviño - ***Scrum team member***
#### **Profesor:**
 - Hector Fabio Cadavid Rengifo - ***Dueño de Producto***
#### **Descripción del Producto:**
> *Este producto esta enfocado a resolver un problema real que posee la Unidad de Proyectos de la Escuela Colombiana de Ingeniería Julio Garavito, respecto a la generación de una plataforma de programación de clases y gestión de recursos de programas de postgrado, donde simplifica la labor de programación de cursos cada periodo académico y la consolidación de datos históricos, con el fin de generar reportes estratégicos para los procesos de mejoramiento continuo de esta unidad, con las presentes funcionalidades.*

> **Funcionalidades**:
> - En esta funcionalidad, el usuario puede acceder a cualquier funcionalidad de la plataforma, hay varios botones los cuales al darles *click* los dirige a la respectiva vista.
![](Inicio.PNG)
> - En esta funcionalidad se pueden consultar todas las asignaturas de un programa (para el que se elige el programa y el nivel, ya sea de Maestría o de Especialización) en determinado periodo (para el cual se debe indicar el año y el semestre). Así mismo para cada asignatura se despliega su lista de materias correspondientes.
![](MateriasRegistradas.PNG)
> - En esta, se registra una nueva materia eligiendo el programa y asignatura al cual va a pertenecer, así mismo se podrá ver que quedo registrada al dar *click* en el botón de Registrar Materia el cual conduce
a las Materias Registradas o se puede cancelar el registro
![](RegistrarMateria.PNG)
> - En la siguiente funcionalidad, se puede ver un reporte de un programa dado en un determinado periodo, para esto se verán sus asignaturas con sus respectivas clases, cohortes, profesores, horas y sesiones así como un reporte por cada materia de las clases programadas en el periodo.
![](ReporteProgramacion.PNG)
> - En la siguiente funcionalidad,  se pueden consultar todos los recursos programados en un determinado periodo, se mostraran los horarios y fechas en los cuales estarán en uso.
![](ReporteRecursos.PNG)

#### **Arquitectura y Diseño:**
> *El producto se realiza con los lenguajes Java, SQL, XML y HTML, mediante el uso de un stack de tecnologias; Apache Shiru, PrimeFaces, Guice y Postgres SQL, donde su desarrollo y construcción son dirigidos por medio de capas. Estas capas son:*
>
 - Presentación: Ofrece una pagina como plataforma de interacción gráfica con las funcionalidades y los datos
 - Logica: Dispone de la implementación y programación de todas las funcionalidades que se ofrecen vinculandose con los datos
 - Persistencia: Mantiene una base de datos, donde permite manipular los datos y enviarle la información que necesita la capa lógica

- **Modelo de Base de Datos:**
![](BaseDeDatos.PNG)
- **Modelo de Base de Datos:**
![](DiagramaDeClases.PNG)
- **Aplicación:**
http://epic-wino.herokuapp.com/
#### **Descripción del Proceso:**