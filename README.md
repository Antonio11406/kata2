#Kata 2: Entrada, salida y carga de datos

##Objetivo de la entrega:
El objetivo de esta práctica es construir un programa capaz de leer un conjunto de datos desde múltiples orígenes a través de la abstracción InputStream (ficheros locales y URLs), transformar esos datos en entidades de dominio (Employee) y volcar los resultados en múltiples destinos a través de un OutputStream (consola y nuevos ficheros). Todo ello garantizando una clara separación de responsabilidades y un control de errores robusto (sin fallos opacos ni retornos nulos destructivos).

##Dependencias y versión de JDK:
JDK: Java 25 (OpenJDK 25) Dependencias: Ninguna librería externa. Todo el procesamiento (parseo de texto, Streams, gestión de E/S) se ha realizado utilizando las bibliotecas estándar de Java (Core Java).

##Estructura de la entrega y clases principales:
El proyecto respeta la separación de responsabilidades mediante los siguientes paquetes:
software.ulpgc.katas.model (Dominio)
Employee: Entidad (Record) que representa la información que nos interesa del dataset (nombre, edad, departamento, género).
Gender: Enumerado simple para el género.
software.ulpgc.katas.io (Entrada / Salida)
EmployeeReader / EmployeeWriter: Interfaces que definen los contratos para leer y escribir listas de empleados.
FileEmployeeReader: Implementación que lee el dataset inyectando un FileInputStream.
UrlEmployeeReader: Implementación que lee el dataset remotamente inyectando URL.openStream().
TsvEmployeeWriter: Implementación que escribe los empleados en un OutputStream en formato tabular.
software.ulpgc.katas (Orquestador)
Main: Punto de entrada que inyecta las dependencias de E/S y demuestra los casos de uso sin contener la lógica de transformación.

##Vídeo explicativo: https://drive.google.com/file/d/11JPRJpK9xElMUcNiupIjqrR2qwWIoOvp/view?usp=drive_link

Repetición deliberada y variaciones:
Durante el proceso de aprendizaje, esta kata se ha repetido varias veces para ganar soltura con los flujos de E/S. Repetición 1: Implementación base centrada en leer desde disco y parsear los datos sin validaciones estrictas. Repetición 2 (Variación de Origen): Se introdujo la abstracción de interfaces y se añadió la variación para extraer los datos desde internet mediante una URL remota, ajustando las expresiones regulares del lector. Repetición 3 (Variación de Destino y Refinamiento): Se añadió el escritor (TsvEmployeeWriter) para permitir volcar el resultado a un nuevo fichero TSV en lugar de solo imprimirlo por consola. Además, se refinó la transformación usando Streams de Java (.lines().skip().map().filter()).

##Evidencias de verificación y pruebas realizadas:
Se ha verificado el correcto funcionamiento del sistema ante los siguientes escenarios: Prueba con fichero correcto: Se lee correctamente employees.tsv con FileInputStream y se extraen los objetos esperados. Prueba con URL: Se extraen exactamente los mismos datos consumiendo la URL cruda de GitHub. Prueba de escritura dual: Los datos se han logrado mostrar por System.out y volcar correctamente a FileOutputStream generando el archivo empleados_extraidos.tsv. Prueba con ruta inexistente: Se ha forzado un error cambiando el nombre a archivoFalso.tsv. El programa captura el FileNotFoundException y muestra un mensaje de aviso limpio. Prueba con fila mal formada: Se verificó que, al llegar a la fila del empleado "George" (que carece del dato de género en la columna 5), el programa lanza un aviso descriptivo por System.err, omite solo esa entidad, y continúa el procesamiento con normalidad.
