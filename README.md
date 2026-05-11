# Inventory Java — Concurrencia y Seguridad por Diseño
Réplica de API de inventario en Java/Spring Boot, enfocada en concurrencia.

## Core
- Lock pesimista con `SELECT FOR UPDATE` para prevenir race conditions
- Tests de concurrencia con Testcontainers + PostgreSQL real
- CI con GitHub Actions en cada push

## Tests de Concurrencia

### Misil 1 — Race condition demostrada en H2
- Stock inicial: 10, 2 threads restando 1 simultáneamente
- Sin lock, el stock quedó en **9** en vez de **8**
- Race condition demostrada ✅

### Por qué 2 threads alcanzan
Una race condition solo requiere 2 threads compitiendo por el mismo recurso al mismo tiempo.
Thread A lee stock=10, Thread B lee stock=10 antes de que A escriba — ambos restan 1, ambos escriben 9. Debería ser 8.
Más threads amplían el problema en producción, pero 2 es el mínimo para reproducirlo de forma confiable.

### Por qué H2 y no PostgreSQL
PostgreSQL tiene protecciones implícitas que ocultan race conditions en tests.
H2 no tiene esas protecciones — perfecto para demostrar el fallo.

### Misil 2 — Race condition demostrada en PostgreSQL real
- Stock inicial: 1, 2 threads restando 1 simultáneamente
- Sin lock, el stock quedó en **0** en vez de **-1**
- Race condition demostrada en PostgreSQL real con Testcontainers ✅

### Defensa — SELECT FOR UPDATE ✅
- `@Transactional` + `findByIdWithLock` con `PESSIMISTIC_WRITE`
- 1000 threads simultáneos contra PostgreSQL real con Testcontainers
- Ningún thread leyó stock inconsistente bajo carga
- Test verde en PostgreSQL real ✅
- Test verde en PostgreSQL real ✅

## Stack
Spring Boot · JPA · PostgreSQL · H2 · Testcontainers · Lombok · GitHub Actions
