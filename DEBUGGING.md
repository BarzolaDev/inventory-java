## Lo que aprendí hoy

### El problema
- Docker 29 cambió la API mínima de 1.24 a 1.44
- Testcontainers 1.21.0 tiene 1.32 hardcodeado en zerodep
- Spring Boot 3.4.5 fuerza Testcontainers a 1.20.6

### La solución
- Agregar docker-java.properties con api.version=1.44
- Agregar dependencyManagement con testcontainers-bom 1.21.0

### Comandos clave de debugging
- Ir siempre al último `Caused by`
- `grep -A 3 "Caused by" target/surefire-reports/*.txt | tail -8`
- `./mvnw test 2>&1 | grep -A 5 "Attempted config"`

### Lo que probé
- Pessimistic locking con SELECT FOR UPDATE
- El lock funciona: "could not obtain lock on row" es el comportamiento esperado

