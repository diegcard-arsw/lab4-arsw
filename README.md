# ARSW Lab4 - API REST para Gestión de Planos Arquitectónicos

![Java](https://img.shields.io/badge/Java-8%2B-orange?style=flat-square&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue?style=flat-square&logo=apache-maven)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-2.7.14-green?style=flat-square&logo=spring)
![Spring MVC](https://img.shields.io/badge/Spring%20MVC-REST%20API-brightgreen?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

## 📋 Descripción

API REST desarrollado con Spring Boot y Spring MVC para la gestión centralizada de blueprints (planos arquitectónicos). Proporciona un medio estandarizado e independiente de la plataforma para gestionar planos de forma centralizada, con soporte completo para operaciones CRUD, filtros configurables y manejo de concurrencia thread-safe.

## 🏗️ Arquitectura

### Diagrama de Clases
![Class Diagram](img/ClassDiagram1.png)

### Componentes Principales

| Componente | Responsabilidad | Tipo |
|------------|----------------|------|
| `BlueprintAPIController` | Controlador REST para endpoints HTTP | REST Controller |
| `BlueprintsServices` | Lógica de negocio y orquestación | Service Layer |
| `BlueprintsPersistence` | Interfaz de persistencia | Data Access Layer |
| `InMemoryBlueprintPersistence` | Implementación de persistencia thread-safe | Repository |
| `BlueprintFilter` | Interfaz para filtros de procesamiento | Filter Pattern |
| `RedundancyBlueprintFilter` | Eliminación de puntos redundantes | Filter Implementation |
| `SubsamplingBlueprintFilter` | Submuestreo de puntos | Filter Implementation |
| `BlueprintsApplication` | Aplicación principal Spring Boot | Main Application |

## ✨ Características

- ✅ **API REST Completa** con Spring Boot y Spring MVC
- ✅ **Endpoints CRUD** para gestión de blueprints
- ✅ **Inyección de Dependencias** con Spring Framework
- ✅ **Persistencia Thread-Safe** con ConcurrentHashMap
- ✅ **Filtros Configurables** intercambiables
- ✅ **Manejo de Concurrencia** sin degradación de rendimiento
- ✅ **Códigos de Estado HTTP** apropiados (200, 201, 404, etc.)
- ✅ **Serialización JSON** automática
- ✅ **Arquitectura Desacoplada** siguiendo principios SOLID
- ✅ **Manejo de Excepciones** específicas del dominio
- ✅ **Documentación de Concurrencia** completa

## 🚀 Inicio Rápido

### Prerrequisitos

```bash
Java 8 o superior
Maven 3.6 o superior
```

### Instalación

1. **Clonar el repositorio**
```bash
git clone https://github.com/diegcard-arsw/lab4-arsw.git
cd lab4-arsw
```

2. **Compilar el proyecto**
```bash
mvn clean compile
```

3. **Ejecutar pruebas**
```bash
mvn test
```

4. **Generar artefacto**
```bash
mvn clean package
```

### Ejecución

**API REST (Spring Boot - Recomendado):**
```bash
mvn spring-boot:run
```

La aplicación se iniciará en `http://localhost:8080`

**Modo Demo (Aplicación de Consola):**
```bash
mvn exec:java -Dexec.mainClass="edu.eci.arsw.blueprints.MainApp"
```

**Ejecutar JAR generado:**
```bash
java -jar target/blueprints-middleware-0.0.1-SNAPSHOT.jar
```

## 🌐 API REST Endpoints

### Obtener Todos los Blueprints
```http
GET /blueprints
```

**Respuesta exitosa (202):**
```json
[
  {
    "author": "john",
    "name": "house_plan",
    "points": [
      {"x": 140, "y": 140},
      {"x": 115, "y": 115}
    ]
  }
]
```

### Obtener Blueprints por Autor
```http
GET /blueprints/{author}
```

**Ejemplo:**
```bash
curl -i http://localhost:8080/blueprints/john
```

**Respuesta exitosa (202):** Lista de blueprints del autor
**Error (404):** Autor no encontrado

### Obtener Blueprint Específico
```http
GET /blueprints/{author}/{blueprintName}
```

**Ejemplo:**
```bash
curl -i http://localhost:8080/blueprints/john/house_plan
```

### Crear Nuevo Blueprint
```http
POST /blueprints
Content-Type: application/json
```

**Ejemplo:**
```bash
curl -i -X POST -H "Content-Type:application/json" \
     -d '{"author":"maria","name":"new_design","points":[{"x":10,"y":20},{"x":30,"y":40}]}' \
     http://localhost:8080/blueprints
```

**Respuesta exitosa (201):** Blueprint creado
**Error (403):** Blueprint ya existe

### Actualizar Blueprint
```http
PUT /blueprints/{author}/{blueprintName}
Content-Type: application/json
```

**Ejemplo:**
```bash
curl -i -X PUT -H "Content-Type:application/json" \
     -d '{"points":[{"x":50,"y":60},{"x":70,"y":80}]}' \
     http://localhost:8080/blueprints/john/house_plan
```

## 🔧 Configuración

### Cambio de Filtros

Para alternar entre filtros, modifica la anotación `@Qualifier` en `BlueprintsServices`:

```java
@Autowired
@Qualifier("redundancy")  // Filtro de redundancia
// @Qualifier("subsampling")  // Filtro de submuestreo
private BlueprintFilter blueprintFilter;
```

### Configuración de Beans

```java
@Component("redundancy")
public class RedundancyBlueprintFilter implements BlueprintFilter {
    // Implementación de filtro de redundancia
}

@Component("subsampling") 
public class SubsamplingBlueprintFilter implements BlueprintFilter {
    // Implementación de filtro de submuestreo
}
```

## 📖 API Reference

### REST Endpoints

| Endpoint | Método | Descripción | Códigos de Respuesta |
|----------|--------|-------------|---------------------|
| `/blueprints` | GET | Obtiene todos los blueprints | 202: Éxito, 404: Error |
| `/blueprints/{author}` | GET | Obtiene blueprints por autor | 202: Éxito, 404: Autor no encontrado |
| `/blueprints/{author}/{name}` | GET | Obtiene blueprint específico | 202: Éxito, 404: No encontrado |
| `/blueprints` | POST | Crea nuevo blueprint | 201: Creado, 403: Ya existe |
| `/blueprints/{author}/{name}` | PUT | Actualiza blueprint | 202: Actualizado, 404: No encontrado |

### BlueprintsServices

| Método | Descripción | Parámetros | Retorna | Excepciones |
|--------|-------------|------------|---------|-------------|
| `addNewBlueprint()` | Registra un nuevo plano | `Blueprint` | `void` | `BlueprintPersistenceException` |
| `getAllBlueprints()` | Obtiene todos los planos filtrados | - | `Set<Blueprint>` | `BlueprintPersistenceException` |
| `getBlueprint()` | Obtiene plano por autor y nombre | `String, String` | `Blueprint` | `BlueprintNotFoundException` |
| `getBlueprintsByAuthor()` | Obtiene planos por autor | `String` | `Set<Blueprint>` | `BlueprintNotFoundException` |
| `updateBlueprint()` | Actualiza un blueprint existente | `String, String, Blueprint` | `void` | `BlueprintNotFoundException` |

### Blueprint

```java
public class Blueprint {
    private String author;
    private String name;
    private List<Point> points;
    
    // Constructor, getters, setters...
}
```

## 🧪 Testing

### Cobertura de Pruebas

- **Pruebas Unitarias**: Filtros individuales
- **Pruebas de Integración**: Servicios completos
- **Pruebas de Persistencia**: Operaciones CRUD

### Ejecutar Suite Completa

```bash
mvn test
```

### Ejecutar Pruebas Específicas

```bash
mvn test -Dtest=InMemoryPersistenceTest
mvn test -Dtest=RedundancyBlueprintFilterTest
mvn test -Dtest=SubsamplingBlueprintFilterTest
```

## 📁 Estructura del Proyecto

```text
src/
├── main/
│   ├── java/
│   │   └── edu/eci/arsw/blueprints/
│   │       ├── BlueprintsApplication.java        # Aplicación Spring Boot
│   │       ├── controllers/
│   │       │   └── BlueprintAPIController.java   # Controlador REST
│   │       ├── model/
│   │       │   ├── Blueprint.java
│   │       │   └── Point.java
│   │       ├── persistence/
│   │       │   ├── BlueprintsPersistence.java
│   │       │   └── impl/
│   │       │       └── InMemoryBlueprintPersistence.java  # Thread-safe
│   │       ├── services/
│   │       │   └── BlueprintsServices.java
│   │       ├── filters/
│   │       │   ├── BlueprintFilter.java
│   │       │   ├── RedundancyBlueprintFilter.java
│   │       │   └── SubsamplingBlueprintFilter.java
│   │       ├── MainApp.java                      # Demo de consola
│   │       └── AppConfig.java                    # Configuración Spring
│   └── resources/
│       └── applicationContext.xml
├── test/
│   └── java/
│       └── edu/eci/arsw/blueprints/
│           ├── persistence/
│           ├── services/
│           └── filters/
├── ANALISIS_CONCURRENCIA.txt                     # Análisis de concurrencia
└── pom.xml                                       # Configuración Spring Boot
```

## 🔒 Análisis de Concurrencia

### Problemas Identificados y Soluciones

**1. HashMap No Thread-Safe → ConcurrentHashMap**
- **Problema**: HashMap puede corromperse con acceso concurrent
- **Solución**: Uso de `ConcurrentHashMap` para operaciones thread-safe
- **Beneficio**: Lecturas concurrentes sin bloqueos, mejor rendimiento

**2. Condición de Carrera en saveBlueprint()**
- **Problema**: Verificación + inserción no atómica
- **Solución**: Uso de `putIfAbsent()` para operación atómica
- **Beneficio**: Elimina completamente la condición de carrera

**3. Estrategia de Alto Rendimiento**
- **Evitado**: Sincronización completa que degradaría el rendimiento
- **Implementado**: Operaciones atómicas específicas de ConcurrentHashMap
- **Resultado**: Thread-safety sin pérdida de concurrencia beneficiosa

Ver `ANALISIS_CONCURRENCIA.txt` para análisis detallado.

## 🤝 Contribución

1. Fork el proyecto
2. Crea tu rama de feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📋 Laboratorio 4 - Implementaciones Completadas

### Parte I - Integración y Configuración

✅ **1. Integración de Beans del Lab3**: Todos los beans del laboratorio anterior integrados correctamente con anotaciones `@Service`, `@Repository`, `@Autowired` y `@Component`.

✅ **2. Datos de Prueba**: `InMemoryBlueprintPersistence` inicializado con 4 planos por defecto:
- 2 planos del autor "john" (house_plan, office_building)  
- 1 plano del autor "maria" (school_design)
- 1 plano del autor "carlos" (park_layout)

✅ **3. Controlador REST**: `BlueprintAPIController` implementado con endpoint GET `/blueprints` que retorna JSON con todos los planos filtrados.

✅ **4. Endpoint por Autor**: GET `/blueprints/{author}` implementado con manejo de error 404 para autores inexistentes.

✅ **5. Endpoint Específico**: GET `/blueprints/{author}/{bpname}` implementado con manejo de error 404.

### Parte II - Operaciones de Escritura

✅ **6. Endpoint POST**: Implementado `/blueprints` para crear nuevos planos con:
- Manejo de JSON en el cuerpo de la petición
- Código 201 (CREATED) en éxito
- Código 403 (FORBIDDEN) si el plano ya existe

✅ **7. Endpoint PUT**: Implementado `/blueprints/{author}/{bpname}` para actualizar planos con:
- Código 202 (ACCEPTED) en éxito  
- Código 404 (NOT_FOUND) si el plano no existe

### Parte III - Análisis de Concurrencia

✅ **8. Identificación de Condiciones de Carrera**:
- HashMap no thread-safe identificado como problema principal
- Operación verificación + inserción no atómica en saveBlueprint()
- Problemas de visibilidad entre hilos

✅ **9. Soluciones Thread-Safe Implementadas**:
- **ConcurrentHashMap** en lugar de HashMap para thread-safety
- **putIfAbsent()** para operaciones atómicas de inserción
- Eliminación completa de condiciones de carrera sin degradación de rendimiento

✅ **10. Documentación**: Archivo `ANALISIS_CONCURRENCIA.txt` con análisis detallado, alternativas consideradas y soluciones implementadas.

### Criterios de Evaluación - Estado

| Criterio | Estado | Descripción |
|----------|---------|-------------|
| **Diseño** | ✅ COMPLETO | Inyección correcta, recursos en un Bean, códigos HTTP apropiados |
| **Funcionalidad** | ✅ COMPLETO | Todos los endpoints REST implementados y funcionales |
| **Concurrencia** | ✅ ÓPTIMO | ConcurrentHashMap + putIfAbsent() (Evaluación: **B**) |

### Pruebas de Funcionalidad

La aplicación puede probarse ejecutando:

```bash
mvn spring-boot:run
```

Y luego utilizando los ejemplos de curl proporcionados en la sección de API REST.

## 👨‍💻 Autor

**Diego Cardenas**  
*Estudiante de Ingeniería de Sistemas*

## 📄 Licencia

Este proyecto está bajo la Licencia MIT. Ver el archivo [LICENSE](LICENSE) para más detalles.

---

<div align="center">
  
**[⬆ Volver al inicio](#arsw-lab3---middleware-para-gestión-de-planos-arquitectónicos)**

*Desarrollado con ❤️ para ARSW - Arquitecturas de Software*

</div>