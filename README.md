# Inventory Java — Concurrency & Security by Design
Inventory API replica in Java/Spring Boot, focused on concurrency.

## Core
- Pessimistic lock with `SELECT FOR UPDATE` to prevent race conditions
- Concurrency tests with Testcontainers + real PostgreSQL
- CI via GitHub Actions on every push

## Concurrency Tests

### Missile 1 — Race condition demonstrated
- Initial stock: 10, 2 threads subtracting 1 simultaneously
- Without lock, stock ended at **9** instead of **8**
- Race condition demonstrated ✅

### Why 2 threads are enough
A race condition only requires 2 threads competing for the same resource at the same time.
Thread A reads stock=10, Thread B reads stock=10 before A writes — both subtract 1, both write 9. Stock should be 8.
More threads amplify the problem in production, but 2 is the minimum to reproduce it reliably.

### Why H2 and not PostgreSQL
PostgreSQL has implicit protections that hide race conditions in tests.
H2 has no such protections — perfect for demonstrating the failure.

### Missile 2 — Race condition fixed
- Same scenario: 2 threads, same product
- With `SELECT FOR UPDATE`, the second thread waits until the first commits
- Stock ended at **8** as expected ✅

## Stack
Spring Boot · JPA · PostgreSQL · H2 · Testcontainers · Lombok · GitHub Actions
