# Repsy Package Repository – Assignment

This repo is my submission for the "Junior Full-Stack Developer" assignment.  
It implements a minimal Maven-style package repository for the imaginary "Repsy" language, featuring two interchangeable storage back-ends (file-system and MinIO object storage).

The deliverables include:

- Spring Boot REST API
- PostgreSQL persistence for package metadata
- Strategy-pattern storage libraries (filesystem + MinIO) published to Repsy Maven repo
- Docker Compose stack for one-command local run
- Test script that exercises deploy, download, and negative test paths

---

## Quick start (Docker Compose)

git clone https://github.com/fringet/repsy-repo.git
cd repsy-repo
docker compose up --build

# Wait for all services to start, then run tests:
./scripts/test_api.sh
# Expected output:
# ✔ 201 Created
# ✔ content matches
# ✔ 404 Not Found

Services exposed locally:

- REST API: `http://localhost:8080`
- MinIO Console: `http://localhost:9001` (username: `minio`, password: `minio123`)
- PostgreSQL: `localhost:5432` (username/password/database: `repsy`)

---

## REST API Endpoints

- **Upload a package:**  
  `POST /{packageName}/{version}`  
  Multipart form-data with fields:  
  - `package.rep` (binary package file)  
  - `meta.json` (metadata file in JSON format)

- **Download a file:**  
  `GET /{packageName}/{version}/{fileName}`

Endpoints return standard HTTP response codes (`201 Created`, `200 OK`, `400 Bad Request`, `404 Not Found`).

---

## Environment Variables

You can customize the application via these environment variables:

| Variable                    | Default                              |
|-----------------------------|--------------------------------------|
| STORAGE_STRATEGY            | `object-storage` (in Docker Compose) |
| storage.fs.root             | `uploads`                            |
| storage.minio.endpoint      | `http://minio:9000`                  |
| storage.minio.accessKey     | `minio`                              |
| storage.minio.secretKey     | `minio123`                           |
| storage.minio.bucket        | `repsy`                              |
| SPRING_DATASOURCE_URL       | `jdbc:postgresql://db:5432/repsy`    |
| SPRING_DATASOURCE_USERNAME  | `repsy`                              |
| SPRING_DATASOURCE_PASSWORD  | `repsy`                              |

To switch storage strategies, run:

STORAGE_STRATEGY=file-system docker compose up

---

## Manual API Demonstration (without test script)

Deploy a package manually:

curl -F "package.rep=@test-data/mypackage.rep" \
     -F "meta.json=@test-data/meta.json" \
     http://localhost:8080/mypkg/1.0.0

Download the package file manually:

curl -o mypackage.rep http://localhost:8080/mypkg/1.0.0/package.rep

---

## Building from Source (Requires JDK 17+ and Maven 3.8+)

mvn clean package -DskipTests

Produces the following artifacts:

- `storage-common-1.0.0.jar`
- `storage-file-system-1.0.0.jar`
- `storage-object-storage-1.0.0.jar`
- `repo-app-1.0.0.jar` (Spring Boot executable JAR)

---

## Published Artifacts and Docker Image

- **Maven Repository (Storage Libraries):**  
  https://repo.repsy.io/mvn/fringet/libs

- **Docker Registry (Spring Boot App):**  
  `registry.repsy.io/fringet/repo-app:1.0.0`

The Spring Boot application resolves the storage libraries directly from the Repsy Maven repository, demonstrating external dependency usage.

---

## Additional Notes for Reviewers

- The API intentionally has no authentication or security constraints, in line with the assignment requirements.
- Uploaded `meta.json` is validated only for basic JSON syntax and correct filenames; `package.rep` files are treated as opaque, per the assignment specification.
- Storage strategy selection is dynamically handled via Spring's `@ConditionalOnProperty`.

Thank you for reviewing this assignment. 