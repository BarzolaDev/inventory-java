# Inventory Java — Concurrency & Safety by Design

Most inventory bugs don't show up in unit tests.
They show up at 3am on Black Friday. This project makes them visible first.

Replica of an inventory API in Java/Spring Boot, focused on demonstrating
and preventing real race conditions.

## Core

- Pessimistic locking with `SELECT FOR UPDATE` to prevent race conditions
- Concurrency tests with Testcontainers + real PostgreSQL
- CI with GitHub Actions on every push

> Pessimistic locking chosen intentionally to demonstrate the mechanism.
> Production-scale systems would favor optimistic locking or event queues.

## Concurrency Tests

**Strike 1 — Race condition demonstrated on H2 ✅**
- Initial stock: 10, 2 simultaneous threads, incorrect result demonstrated

**Strike 2 — Race condition demonstrated on real PostgreSQL ✅**
- Initial stock: 1, 2 simultaneous threads, incorrect result demonstrated

**Defense — SELECT FOR UPDATE ✅**
- 1000 simultaneous threads against real PostgreSQL
- No thread read inconsistent stock under load

## Stack

Spring Boot · JPA · PostgreSQL · H2 · Testcontainers · Lombok · GitHub Actions

---
pd: This repo claimed 2 power supplies. Development ongoing.
