# Tabs — Guitar Tab & Chord Transposition API

[![CI](https://github.com/polyglotarist/tabs/actions/workflows/ci.yml/badge.svg)](https://github.com/polyglotarist/tabs/actions/workflows/ci.yml)

A Spring Boot REST API for browsing guitar tabs and chord sheets, inspired by Ultimate Guitar. Supports full CRUD on songs and artists, search, and on-the-fly chord transposition with sharp/flat spelling and slash-chord support.

## Tech Stack
- Java 21, Spring Boot 4.1.1, Spring Data JPA, Spring Web
- PostgreSQL 16 (Dockerized for local dev)
- Flyway for versioned schema migrations
- JUnit 5 + AssertJ for testing, JaCoCo for coverage
- Swagger/OpenAPI for live API documentation
- GitHub Actions for CI

## Key Feature: Chord Transposition
The standout piece of this project is `TransposeService` — a chromatic-scale transposition engine that:
- Shifts any chord up or down by semitones, with correct wraparound (e.g. `B` + 2 semitones → `C#`)
- Supports both sharp and flat spelling (`F#` vs `Gb`)
- Handles slash chords (`D/F#` → `E/G#`)
- Preserves chord quality (`Em7`, `Cmaj7`, `Bsus4`, etc.)
- Is covered by 17 unit tests, including parameterized edge-case tests for chromatic wraparound

## Running Locally
1. `docker run --name tabs-db -e POSTGRES_PASSWORD=devpass -e POSTGRES_DB=tabs -p 5432:5432 -d postgres:16`
2. `./mvnw spring-boot:run`
3. API docs: `http://localhost:8080/swagger-ui.html`

## API Overview
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/songs` | List/search songs by title or artist |
| GET | `/api/songs/{id}` | Get a song |
| POST | `/api/songs` | Create a song |
| PUT | `/api/songs/{id}` | Update a song |
| DELETE | `/api/songs/{id}` | Delete a song |
| GET | `/api/songs/{id}/transpose?semitones=2&useFlats=false` | Transpose a song's chords |
| GET/POST/PUT/DELETE | `/api/artists...` | Same CRUD pattern for artists |