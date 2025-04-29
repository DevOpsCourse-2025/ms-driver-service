---

# 📖 Manual de Usuario: Levantamiento de Contenedores y Configuración de Microservicios

Este manual explica los pasos necesarios para construir, levantar y conectar los microservicios y la base de datos de la aplicación, utilizando contenedores Docker.

---

## 1. Instalación previa: pre-commit

Este proyecto utiliza **Git Hooks** mediante la herramienta **pre-commit**.

### 1.1 Instalación de `pre-commit`

Primero instala `pre-commit` (se requiere tener **Python** instalado).

```bash
pip install pre-commit
```

**Una vez instalado**, dentro del proyecto ejecuta:

```bash
pre-commit install
```

Esto configurará automáticamente los hooks para que se ejecuten antes de cada commit.

---

## 2. Construcción de imágenes Docker

Genera las imágenes Docker de cada microservicio con el siguiente comando:

```bash
./gradlew dockerBuild
```

Este comando construye una imagen Docker específica para cada microservicio.

---

## 3. Levantamiento de contenedores

Una vez construidas las imágenes, debes levantar los contenedores.

### 3.1. Comando general para levantar un microservicio

```bash
docker run --name {nombre_del_contenedor} --network {nombre_de_la_network} -p 8080:8080 {nombre_de_imagen}
```

- `{nombre_del_contenedor}`: Nombre que le quieras dar al contenedor.
- `{nombre_de_la_network}`: Red de Docker común para todos los microservicios y la base de datos.
- `8080:8080`: Mapea el puerto 8080 (ajustar si es necesario).
- `{nombre_de_imagen}`: Nombre de la imagen generada.

**Importante:**  
Todos los microservicios y la base de datos deben estar conectados a **la misma red**.

---

### 3.2. Levantar el contenedor de la base de datos (MySQL)

Ejecuta:

```bash
docker run -d --name ms_db --network {nombre_de_la_network} \
-e MYSQL_ROOT_PASSWORD=rootpass \
-e MYSQL_DATABASE=invitation-service \
-e MYSQL_USER=admin \
-e MYSQL_PASSWORD=admin123 \
-v ms_db:/var/lib/mysql \
-p 3307:3307 \
mysql:8.4.0
```

- `-d`: Corre el contenedor en segundo plano.
- `-v ms_db:/var/lib/mysql`: Crea un volumen para persistir los datos.

---

## 4. Configuración inicial de la base de datos

Una vez que el contenedor de MySQL esté corriendo, conecta a la base de datos para crear las bases necesarias.

### 4.1. Conexión a MySQL

```bash
mysql -u root -p
```
- **Password:** `rootpass`

### 4.2. Creación de bases de datos

Ejecuta en MySQL:

```sql
CREATE DATABASE administrator_service;
CREATE DATABASE invitation_service;
CREATE DATABASE vehicle_service;
CREATE DATABASE driver_service;
CREATE DATABASE route_service;
```

### 4.3. Creación de usuario y asignación de permisos

```sql
CREATE USER 'admin'@'%' IDENTIFIED BY 'admin123';

GRANT ALL PRIVILEGES ON administrator_service.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON invitation_service.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON vehicle_service.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON driver_service.* TO 'admin'@'%';
GRANT ALL PRIVILEGES ON route_service.* TO 'admin'@'%';
```

> **Nota:** El nombre de la base de datos `invitation-service` debe escribirse como `invitation_service` (con guión bajo) para mantener consistencia.

---

## 5. Configuración de las URLs de conexión

En cada microservicio, en el archivo:

```
src/main/resources/application.properties
```

modifica las URLs para apuntar al **nombre del contenedor** en lugar de `localhost`.

Ejemplo para una base de datos:

```properties
spring.datasource.url=jdbc:mysql://ms_db:3306/invitation_service
```

Ejemplo para la conexión entre microservicios:

```properties
http://nombre_del_contenedor:8080
```

---

## 6. Pruebas finales

- La aplicación completa debe ser accedida principalmente desde el **puerto 8080** (donde corre el **API Gateway**).
- Verifica que todos los microservicios estén comunicándose correctamente entre sí.
- Valida que las bases de datos se conecten y almacenen datos correctamente.

Para ver los contenedores activos:

```bash
docker ps
```

Para ver los logs de un contenedor:

```bash
docker logs {nombre_del_contenedor}
```

Para inspeccionar la red y las conexiones:

```bash
docker network inspect {nombre_de_la_network}
```

---

# ✅ Recomendaciones finales

- Si haces cambios en los `application.properties`, reconstruye las imágenes de los microservicios afectados.
- Asegúrate que **todos los servicios** estén conectados a la **misma red**.
- Usa `pre-commit` antes de realizar commits para asegurar que se respetan las reglas de calidad del proyecto.
- Documenta cualquier error o excepción para poder solucionarlo rápidamente.

---

## Correr el sistema 
./gradlew run

## Micronaut 4.8.2 Documentation

- [User Guide](https://docs.micronaut.io/4.8.2/guide/index.html)
- [API Reference](https://docs.micronaut.io/4.8.2/api/index.html)
- [Configuration Reference](https://docs.micronaut.io/4.8.2/guide/configurationreference.html)
- [Micronaut Guides](https://guides.micronaut.io/index.html)
---

- [Shadow Gradle Plugin](https://gradleup.com/shadow/)
- [Micronaut Gradle Plugin documentation](https://micronaut-projects.github.io/micronaut-gradle-plugin/latest/)
- [GraalVM Gradle Plugin documentation](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
## Feature security-jwt documentation

- [Micronaut Security JWT documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)


## Feature jdbc-hikari documentation

- [Micronaut Hikari JDBC Connection Pool documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#jdbc)


## Feature http-client documentation

- [Micronaut HTTP Client documentation](https://docs.micronaut.io/latest/guide/index.html#nettyHttpClient)


## Feature object-storage-oracle-cloud documentation

- [Micronaut Object Storage - Oracle Cloud documentation](https://micronaut-projects.github.io/micronaut-object-storage/latest/guide/)

- [https://www.oracle.com/cloud/storage/object-storage/](https://www.oracle.com/cloud/storage/object-storage/)


## Feature hibernate-jpa documentation

- [Micronaut Hibernate JPA documentation](https://micronaut-projects.github.io/micronaut-sql/latest/guide/index.html#hibernate)


## Feature micronaut-aot documentation

- [Micronaut AOT documentation](https://micronaut-projects.github.io/micronaut-aot/latest/guide/)


## Feature oracle-cloud-sdk documentation

- [Micronaut Oracle Cloud SDK documentation](https://micronaut-projects.github.io/micronaut-oracle-cloud/latest/guide/)

- [https://docs.cloud.oracle.com/en-us/iaas/Content/API/SDKDocs/javasdk.htm](https://docs.cloud.oracle.com/en-us/iaas/Content/API/SDKDocs/javasdk.htm)


## Feature hibernate-validator documentation

- [Micronaut Hibernate Validator documentation](https://micronaut-projects.github.io/micronaut-hibernate-validator/latest/guide/index.html)


## Feature security documentation

- [Micronaut Security documentation](https://micronaut-projects.github.io/micronaut-security/latest/guide/index.html)


## Feature test-resources documentation

- [Micronaut Test Resources documentation](https://micronaut-projects.github.io/micronaut-test-resources/latest/guide/)


## Feature serialization-jackson documentation

- [Micronaut Serialization Jackson Core documentation](https://micronaut-projects.github.io/micronaut-serialization/latest/guide/)


