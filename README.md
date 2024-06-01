<h1 align="center"> Practicando Spring Boot: Challenge Literalura </h1>

Este desarrollo extrae datos de la API: gutendex.com/book/ , la cual traerá los libros que el usuario desee buscar.
luego de extraidos los libros, serán almacenados un una base de datos en PostgreSQL
Este almacenamiento permitirá consultar los datos por medio de Querys o consultas JPA Java

<h2>Implementos</h2>
1. Configuración del Ambiente Java:
- Implementación de Spring Boot Framework inicializado en Spring Inicializr
- conexión de dependencias '<Dependencies></Dependencies>' en el archivo pom.xml
- conexión  ala base de datos

2. Creación del Proyecto:
   - Se crearon diferentes clases para la manipulación de API
   - Se crearon diferentes records para la extracción de los datos Json de la API
   - Se organizó todo este proyecto en carpetas como: model, principal, repository, service
    
3. Análisis de la Respuesta JSON:
   - se manejo en Service: con el consumo de la API, convirtiendo los datos a los diferentes records para la manipulación de este mismo
     
6. Inserción y consulta en la base de datos:
   - insersión de datos por medio de JpsRepository


https://github.com/Ismael-Vasco/LiterAlura_Challenge/assets/157051386/41ad3b2a-dfe8-4c91-bd5b-73e9aa8140c3


7. Exibición de resultados a los usuarios;
   - por medio de Querys JPA se visualizan los datos requeridos desde la base de datos 

