# Inventory Java
Replicación de la API de inventario en Java/Spring Boot, 
enfocada en concurrencia y protección del lock en producción.

## Core
- Lock pesimista con `SELECT FOR UPDATE` para prevenir race conditions
- Lock protegido con `nowait` y `timeout` para evitar bloqueos indefinidos
- Test de concurrencia con Testcontainers + PostgreSQL real
- 1000 threads simultáneos, stock consistente garantizado
- CI via GitHub Actions en cada push

## Stack
Spring Boot · JPA · PostgreSQL · Testcontainers · Lombok · GitHub Actions
