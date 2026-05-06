# Inventory Java

Replicación de la API de inventario en Java/Spring Boot.

## Core
- Lock pesimista con `SELECT FOR UPDATE` para prevenir race conditions
- Test de concurrencia con Testcontainers + PostgreSQL real
- 10 threads simultáneos, stock consistente garantizado

## Stack
Spring Boot · JPA · PostgreSQL · Testcontainers · Lombok
