# Inventory Java — Concurrencia y Seguridad por Diseño

Réplica de API de inventario en Java/Spring Boot, enfocada en demostrar y prevenir race conditions reales.

## Core

- Lock pesimista con `SELECT FOR UPDATE` para prevenir race conditions
- Tests de concurrencia con Testcontainers + PostgreSQL real
- CI con GitHub Actions en cada push

## Tests de Concurrencia

**Misil 1 — Race condition demostrada en H2 ✅**
- Stock inicial: 10, 2 threads simultáneos, resultado incorrecto demostrado

**Misil 2 — Race condition demostrada en PostgreSQL real ✅**
- Stock inicial: 1, 2 threads simultáneos, resultado incorrecto demostrado

**Defensa — SELECT FOR UPDATE ✅**
- 1000 threads simultáneos contra PostgreSQL real
- Ningún thread leyó stock inconsistente bajo carga

## Stack

Spring Boot · JPA · PostgreSQL · H2 · Testcontainers · Lombok · GitHub Actions

pd:
Este repo costó 2 fuentes (literalmente). 
