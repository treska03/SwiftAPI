# SwiftAPI

A convenient way to access SWIFT codes of banks all around europe!

---

## Table of Contents

- [Features](#features)
- [Installation](#installation)
- [Tech Stack](#tech-stack)
- [Contact](#contact)

---

## Features

- Find information about given SWIFT code
- Get details about all SWIFT codes for banks located within given country
- Create new SWIFT codes
- Delete existing SWIFT codes

---

## Installation

### Prerequisites

- docker
- git (nice to have)

## Running locally

To get started using SwiftAPI locally, follow these steps:

1. **Clone the Repository** (or download zip of repository and unpack it)
   ```bash
   git clone https://github.com/treska03/SwiftAPI.git
   cd SwiftAPI
   ```
2. **Build docker container**
   ```bash
   docker compose -f .\docker\application-compose.yml up --build
   ```

When it finishes, your container should be up and running.

The api will be exposed under http://localhost:8080/

## Tech Stack

_Backend_

[![Kotlin](https://skillicons.dev/icons?i=kotlin)](https://kotlinlang.org) [![Spring boot](https://skillicons.dev/icons?i=spring)](https://spring.io/projects/spring-boot)

_Databases_

[![PostgreSQL](https://skillicons.dev/icons?i=postgres)](https://www.postgresql.org)

_Tools_

[![PostgreSQL](https://skillicons.dev/icons?i=git)](https://git-scm.com/) [![PostgreSQL](https://skillicons.dev/icons?i=docker)](https://www.docker.com/)

## Contact

You can contact me via mail bartlomiej.treska@gmail.com