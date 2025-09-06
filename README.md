# ARSW Lab3 - Middleware para Gestión de Planos Arquitectónicos

![Java](https://img.shields.io/badge/Java-8%2B-orange?style=flat-square&logo=java)
![Maven](https://img.shields.io/badge/Maven-3.6%2B-blue?style=flat-square&logo=apache-maven)
![Spring](https://img.shields.io/badge/Spring-Framework-green?style=flat-square&logo=spring)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

## 📋 Descripción

Sistema middleware desarrollado en Spring Framework para la gestión centralizada de blueprints (planos arquitectónicos). Implementa patrones de inyección de dependencias, persistencia en memoria, filtros configurables y arquitectura por capas, proporcionando una base sólida para aplicaciones de gestión documental técnica.

## 🏗️ Arquitectura

### Diagrama de Clases
![Class Diagram](img/ClassDiagram1.png)

### Componentes Principales

| Componente | Responsabilidad | Tipo |
|------------|----------------|------|
| `BlueprintsServices` | Lógica de negocio y orquestación | Service Layer |
| `BlueprintsPersistence` | Interfaz de persistencia | Data Access Layer |
| `InMemoryBlueprintPersistence` | Implementación de persistencia en memoria | Repository |
| `BlueprintFilter` | Interfaz para filtros de procesamiento | Filter Pattern |
| `RedundancyBlueprintFilter` | Eliminación de puntos redundantes | Filter Implementation |
| `SubsamplingBlueprintFilter` | Submuestreo de puntos | Filter Implementation |

## ✨ Características

- ✅ **Inyección de Dependencias** con Spring Framework
- ✅ **Persistencia en Memoria** thread-safe
- ✅ **Filtros Configurables** intercambiables
- ✅ **Arquitectura Desacoplada** siguiendo principios SOLID
- ✅ **Manejo de Excepciones** específicas del dominio
- ✅ **Pruebas Unitarias e Integración** completas
- ✅ **Configuración Flexible** mediante anotaciones

## 🚀 Inicio Rápido

### Prerrequisitos

```bash
Java 8 o superior
Maven 3.6 o superior
```

### Instalación

1. **Clonar el repositorio**
```bash
git clone <repository-url>
cd ARSW_Lab3
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

**Modo Demo (Aplicación de Consola):**
```bash
mvn exec:java -Dexec.mainClass="edu.eci.arsw.blueprints.MainApp"
```

**Ejecutar JAR generado:**
```bash
java -jar target/blueprints-middleware-0.0.1-SNAPSHOT.jar
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

### BlueprintsServices

| Método | Descripción | Parámetros | Retorna | Excepciones |
|--------|-------------|------------|---------|-------------|
| `addNewBlueprint()` | Registra un nuevo plano | `Blueprint` | `void` | `BlueprintPersistenceException` |
| `getAllBlueprints()` | Obtiene todos los planos filtrados | - | `Set<Blueprint>` | `BlueprintPersistenceException` |
| `getBlueprint()` | Obtiene plano por autor y nombre | `String, String` | `Blueprint` | `BlueprintNotFoundException` |
| `getBlueprintsByAuthor()` | Obtiene planos por autor | `String` | `Set<Blueprint>` | `BlueprintNotFoundException` |

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

```
src/
├── main/
│   ├── java/
│   │   └── edu/eci/arsw/blueprints/
│   │       ├── model/
│   │       │   ├── Blueprint.java
│   │       │   └── Point.java
│   │       ├── persistence/
│   │       │   ├── BlueprintsPersistence.java
│   │       │   └── impl/
│   │       │       └── InMemoryBlueprintPersistence.java
│   │       ├── services/
│   │       │   └── BlueprintsServices.java
│   │       ├── filters/
│   │       │   ├── BlueprintFilter.java
│   │       │   ├── RedundancyBlueprintFilter.java
│   │       │   └── SubsamplingBlueprintFilter.java
│   │       └── MainApp.java
│   └── resources/
│       └── applicationContext.xml
└── test/
    └── java/
        └── edu/eci/arsw/blueprints/
            ├── persistence/
            ├── services/
            └── filters/
```

## 🤝 Contribución

1. Fork el proyecto
2. Crea tu rama de feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## 📋 Preguntas del Laboratorio - Soluciones Implementadas

### 1. Configuración de Inyección de Dependencias
✅ **Implementado**: Configuración completa con Spring Framework utilizando anotaciones `@Component`, `@Autowired` y `@Qualifier` para gestión automática de dependencias.

### 2. Implementación de Operaciones CRUD
✅ **Implementado**: Métodos `getBluePrint()` y `getBlueprintsByAuthor()` completados en `InMemoryBlueprintPersistence` con manejo de excepciones apropiado.

### 3. Programa de Demostración
✅ **Implementado**: `MainApp` con interfaz de consola interactiva que demuestra todas las funcionalidades del sistema.

### 4. Sistema de Filtros
✅ **Implementado**: Arquitectura de filtros intercambiables con implementaciones para redundancia y submuestreo, aplicados automáticamente en consultas.

### 5. Pruebas y Configuración Dinámica
✅ **Implementado**: Suite completa de pruebas que valida la intercambiabilidad de filtros mediante cambio de configuración sin modificación de código.

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