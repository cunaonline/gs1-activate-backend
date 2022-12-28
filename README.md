## Activate Backend
Usa el Framework de Sring Boot para el backend de la aplicación Activate y se conecta al claster de MongoDB, el mismo cuenta con un conjunto de endpoints que se usan para el Frontend.

Previo:

- Instalar JDK8: https://www.oracle.com/java/technologies/javase/javase-jdk8-downloads.html
- Instalar MongoDB: https://www.mongodb.com/try/download/community

**Ejecutar el ambiente de Local**

- Editar el archivo application.properties para adecuarlo al ambiente actual para el despliegue sea correcto, ejemplo:

```json
  # Configuración básica del archivo de propiedades  

  server.port=8080
  management.server.port=8081
  
  #Maximo tamano para los archivos a subir:
  spring.servlet.multipart.max-file-size=10MB
  spring.servlet.multipart.max-request-size=10MB
  
  spring.datasource.testOnBorrow=true
  
  security.jwt.token.secret-key=Xn2r5u8x/A?D*G-KaPdSgVkYp3s6v9y$
  
  #Errors
  server.error.include-message=always 
  server.error.include-stacktrace=never
  
  # Actuator
  management.endpoint.health.show-details=always
  
  #joda time
  spring.jpa.properties.jadira.usertype.autoRegisterUserTypes = true
  
  spring.main.allow-bean-definition-overriding=true
  
  # Sincronizador
  #Empresas que no se van a sincronizar
  sincronizador.no.sincronizar.empresas=216993640019
  
  # Habilitar la compresión de las respuestas REST 
  server.compression.enabled=true
  
  # The comma-separated list of mime types that should be compressed
  server.compression.mime-types=text/html,text/xml,text/plain,text/css,text/javascript,application/javascript,application/json
  
  # Compress the response only if the response size is at least 1KB
  server.compression.min-response-size=10
  
  # MongoDB Local
  spring.data.mongodb.auto-index-creation=true
  spring.data.mongodb.host=localhost
  spring.data.mongodb.port=27017
  spring.data.mongodb.database=activate
 
  #Springdoc OpenApi (Swagger)
  springdoc.swagger-ui.doc-expansion=none

```

**Ejecutar el  ambiente de desarrollo**

- Editar el archivo application.properties para adecuarlo al ambiente actual para el despliegue sea correcto,  ejemplo:

```json
Nota: - Para otros ambiente que no son el Local debe conectarse al cluster de mongoDB para usar la siguente configiración:
      - Debe eliminar la configuración de conección a una base de datos local (   # MongoDB Local ).
```
```json
{
 #MongoDBDesarrollo
   spring.data.mongodb.uri=mongodb+srv://<user>:<password>@clustercatalogoypedidos.ssqe8.mongodb.net/catalogo?retryWrites=true
 # Nota: Tenga en cuenta que en la ru ta se en cuenta que en nombre de la base de datos a la cual nos queremos conectar.
   
}
```

**Ejecutar el  ambiente de SQA**

- Editar el archivo application.properties para adecuarlo al ambiente actual para el despliegue sea correcto,  ejemplo:

```json
Nota: - Para otros ambiente que no son el Local debe conectarse al cluster de mongoDB para usar la siguente configiración:
      - Debe eliminar la configuración de conección a una base de datos local (   # MongoDB Local ).
```
```json
{
 #MongoDB SQA
   spring.data.mongodb.uri=mongodb+srv://<user>:<password>@clustercatalogoypedidos.ssqe8.mongodb.net/catalogo_sqa?retryWrites=true
 # Nota: Tenga en cuenta que en la ruta se en cuenta que en nombre de la base de datos a la cual nos queremos conectar.
   
}
```
```json
{
 #MongoDB UAT
   spring.data.mongodb.uri=mongodb+srv://<user>:<password>@clustercatalogoypedidos.ssqe8.mongodb.net/catalogo_test?retryWrites=true   
}
```

 `# Nota: Tenga en cuenta que en la ruta se en cuenta que en nombre de la base de datos a la cual nos queremos conectar.`

- Parado en la terminal en la carpeta "catalogo-backend
  `mvn package`


# template
Repositorio template
