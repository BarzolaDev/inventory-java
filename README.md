# Inventory Java — Concurrency & Security by Design

Inventory API replica in Java/Spring Boot, focused on concurrency.

## Core
- Pessimistic lock with `SELECT FOR UPDATE` to prevent race conditions
- Concurrency tests with Testcontainers + real PostgreSQL
- CI via GitHub Actions on every push

## Concurrency Tests

### Missile 1 — Race condition demonstrated
- Initial stock: 10, 11 threads subtracting 1 simultaneously
- Without lock, stock ended at **9** instead of **-1**
- Race condition demonstrated ✅

### Why H2 and not PostgreSQL
PostgreSQL has implicit protections that hide race conditions in tests.
H2 has no such protections — perfect for demonstrating the failure.

### Next
- Implement SELECT FOR UPDATE with Testcontainers + real PostgreSQL

## Stack
Spring Boot · JPA · PostgreSQL · H2 · Testcontainers · Lombok · GitHub Actions
