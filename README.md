**EPIC-WINO**
=========
#### **Periodo Acad�mico: 2017-1**
#### **Curso: Proceso De Desarrollo De Software**
#### **Integrantes:**
 - Maria Camila Gomez Ramirez - ***Scrum team member***
 - Fabian Augusto Ardila Rodriguez - ***Scrum team member***
 - Alejandro Anzola Avila - ***Scrum Master***
 - Juan Jose Andrade Pardo - ***Scrum team member***
 - Jossie Esteban Murcia Trivi�o - ***Scrum team member***
#### **Profesor:**
 - Hector Fabio Cadavid Rengifo - ***Due�o de Producto***
#### **Descripci�n del Producto:**
> *Este producto esta enfocado a resolver un problema real que posee la Unidad de Proyectos de la Escuela Colombiana de Ingenier�a Julio Garavito, respecto a la generaci�n de una plataforma de programaci�n de clases y gesti�n de recursos de programas de postgrado, donde simplifica la labor de programaci�n de cursos cada periodo acad�mico y la consolidaci�n de datos hist�ricos, con el fin de generar reportes estrat�gicos para los procesos de mejoramiento continuo de esta unidad, con las presentes funcionalidades.*

> **Funcionalidades**:
> - En esta funcionalidad, el usuario puede acceder a cualquier funcionalidad de la plataforma, hay varios botones los cuales al darles *click* los dirige a la respectiva vista.
![](Inicio.PNG)
> - En esta funcionalidad se pueden consultar todas las asignaturas de un programa (para el que se elige el programa y el nivel, ya sea de Maestr�a o de Especializaci�n) en determinado periodo (para el cual se debe indicar el a�o y el semestre). As� mismo para cada asignatura se despliega su lista de materias correspondientes.
![](MateriasRegistradas.PNG)
> - En esta, se registra una nueva materia eligiendo el programa y asignatura al cual va a pertenecer, as� mismo se podr� ver que quedo registrada al dar *click* en el bot�n de Registrar Materia el cual conduce
a las Materias Registradas o se puede cancelar el registro
![](RegistrarMateria.PNG)
> - En la siguiente funcionalidad, se puede ver un reporte de un programa dado en un determinado periodo, para esto se ver�n sus asignaturas con sus respectivas clases, cohortes, profesores, horas y sesiones as� como un reporte por cada materia de las clases programadas en el periodo.
![](ReporteProgramacion.PNG)
> - En la siguiente funcionalidad,  se pueden consultar todos los recursos programados en un determinado periodo, se mostraran los horarios y fechas en los cuales estar�n en uso.
![](ReporteRecursos.PNG)

#### **Arquitectura y Dise�o:**
> *El producto se realiza con los lenguajes Java, SQL, XML y HTML, mediante el uso de un stack de tecnologias; Apache Shiru, PrimeFaces, Guice y Postgres SQL, donde su desarrollo y construcci�n son dirigidos por medio de capas. Estas capas son:*
>
 - Presentaci�n: Ofrece una pagina como plataforma de interacci�n gr�fica con las funcionalidades y los datos
 - Logica: Dispone de la implementaci�n y programaci�n de todas las funcionalidades que se ofrecen vinculandose con los datos
 - Persistencia: Mantiene una base de datos, donde permite manipular los datos y enviarle la informaci�n que necesita la capa l�gica

- **Modelo de Base de Datos:**
![](BaseDeDatos.PNG)
- **Modelo de Base de Datos:**
![](DiagramaDeClases.PNG)
- **Aplicaci�n:**
http://epic-wino.herokuapp.com/
#### **Descripci�n del Proceso:**