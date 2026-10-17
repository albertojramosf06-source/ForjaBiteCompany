# Project Instructions

This repository contains a university software engineering project for a housing rental platform.

## Technology

- Java
- Maven
- Spring Boot
- Spring Data JPA
- JUnit

## Architecture

Prefer a layered architecture:

Controller -> Service -> Repository -> Persistence

Business logic should not be placed directly in controllers.

## Development process

Before implementing a task:

1. Read the related issue and its acceptance criteria.
2. Inspect the relevant repository files.
3. Propose an implementation plan.
4. Identify affected components and possible risks.
5. Do not make changes until the plan has been reviewed.

After implementation:

1. Build the project.
2. Execute the relevant tests.
3. Explain the changes performed.
4. Report any unresolved problem or assumption.

## Restrictions

- Do not invent requirements.
- Do not silently change architecture.
- Do not remove tests to make the build pass.
- Do not integrate or approve changes.

## Review persistence implementation

When reviewing persistence, inspect entities, repositories, services and controllers.
Check generated identifiers, nullability, unique constraints and any relationships
against the issue. Keep business logic outside controllers. Review JPA/H2
dependencies and configuration. Do not invent relationships or expand scope.
Report verification results and limitations. The human team runs verify, reviews
the diff and decides; the agent must not approve or integrate its own changes.
