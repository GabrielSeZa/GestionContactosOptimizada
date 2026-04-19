# Gestión de Contactos (Java Swing)

## Descripción
Este proyecto corresponde a la mejora de una aplicación base proporcionada por el docente. La aplicación está desarrollada en Java utilizando la librería Swing y permite gestionar una lista de contactos mediante operaciones CRUD (Crear, Leer, Actualizar y Eliminar). Sobre la versión original se implementaron mejoras solicitadas, incluyendo nuevas funcionalidades, manejo de eventos y optimización de la interfaz.

## Mejoras implementadas

Sobre el código base entregado por el docente se realizaron las siguientes mejoras:

- Implementación de tabla (JTable) para visualización estructurada de contactos
- Ordenamiento de datos mediante TableRowSorter
- Búsqueda en tiempo real por nombre
- Implementación de eliminación mediante tecla Delete
- Menú contextual con clic derecho para eliminar registros
- Modificación de contactos desde la interfaz
- Estadísticas dinámicas (total, favoritos y categorías)
- Mejora en la organización de la interfaz mediante JTabbedPane
- Validaciones y control de errores al leer archivos

## Funcionalidades

- Agregar contactos
- Eliminar contactos (botón, menú contextual y teclado)
- Modificar contactos
- Búsqueda en tiempo real
- Marcar contactos como favoritos
- Visualización de estadísticas dinámicas
- Persistencia de datos mediante archivo CSV

## Interfaz

La aplicación cuenta con dos pestañas principales:

### Contactos
Permite:
- Ingresar datos (nombre, teléfono, email, categoría)
- Visualizar contactos en una tabla
- Buscar contactos en tiempo real
- Realizar operaciones CRUD

### Estadísticas
Muestra información en tiempo real sobre:
- Total de contactos registrados
- Cantidad de contactos favoritos
- Número de categorías diferentes

## Estructura del Proyecto

Se mantiene una organización basada en un modelo tipo MVC básico:

- modelo: Manejo de datos y persistencia (persona, personaDAO)
- vista: Interfaz gráfica (ventana)
- controlador: Lógica de la aplicación (logica_ventana)

## Almacenamiento de Datos

Los contactos se almacenan en un archivo CSV ubicado en:

C:/gestionContactos/datosContactos.csv

Formato del archivo:

NOMBRE;TELEFONO;EMAIL;CATEGORIA;FAVORITO

## Tecnologías utilizadas

- Java
- Java Swing
- Manejo de archivos (FileReader, FileWriter)
- Manejo de eventos (ActionListener, ItemListener, DocumentListener)

## Uso

1. Ingresar los datos del contacto en los campos disponibles.
2. Presionar el botón "Agregar" para registrar un nuevo contacto.
3. Seleccionar un contacto de la tabla para visualizar o modificar sus datos.
4. Utilizar el botón "Modificar" para actualizar la información.
5. Eliminar un contacto mediante el botón correspondiente, clic derecho o tecla Delete.
6. Utilizar el campo de búsqueda para filtrar contactos por nombre.

## Ejecución

1. Abrir el proyecto en Eclipse
2. Ejecutar la clase Main.java
3. Interactuar con la interfaz gráfica

## Nota

El proyecto parte de una base proporcionada por el docente. Las modificaciones realizadas se enfocan en mejorar la funcionalidad, usabilidad y cumplimiento de los requisitos planteados en la actividad.

## Autor

Desarrollo basado en código base del docente, con mejoras implementadas por el estudiante.