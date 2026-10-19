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

## Skill S5 - Review change before integration

Use this skill before proposing that a Pull Request be integrated into main.
The purpose is to prepare a rigorous human review, not to replace the reviewer.

Checks before integration

1. Read the linked issue and its acceptance criteria. Identify what the change must accomplish and verify that the proposed change addresses that scope.
2. Inspect the Pull Request diff. Confirm that it is limited to the issue, that relevant files have been reviewed and that unrelated or temporary files are not included. Flag unexpected architectural or dependency changes.
3. Check build verification using the project's Maven Wrapper   (.\mvnw.cmd clean verify on Windows or ./mvnw clean verify on Linux/macOS).
    Report the actual command and outcome. If verification was not executed or evidence is missing, state that explicitly; never assume BUILD SUCCESS.
4. Inspect affected documentation, including the README.md, technical decisions and diagrams when relevant. Identify any required updates.
5. Identify technical debt, known limitations, unresolved defects and dependencies on other work. Do not hide or silently dismiss failures.
6. Provide a concise review report covering the issue, acceptance criteria, files and scope reviewed, build evidence, documentation, risks, limitations
    and any actions required before integration
  

   Mandatory boundaries

- Do not approve Pull Requests or act as the human reviewer.
- Do not merge changes into main.
- Do not conceal failures or report unperformed checks as successful.
- Do not change code or expand the issue scope merely to make the review pass.
- Leave approval and integration decisions to the human team after it reviews the diff and verifies the evidence.
