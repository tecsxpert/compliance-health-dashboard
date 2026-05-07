# Upgrade Plan: compliance-health-dashboard (20260507055209)

- **Generated**: 2026-05-07 05:52 UTC
- **HEAD Branch**: main
- **HEAD Commit ID**: N/A

## Available Tools

**JDKs**

- Java 20.0.1: C:\Program Files\Java\jdk-20\bin
- Java 22: C:\Program Files\Java\jdk-22\bin
- Java 25: **<TO_BE_INSTALLED>** (required by step 1)
- Java 17: not available (baseline skipped)

**Build Tools**

- Maven Wrapper: 3.9.14 (defined in .mvn/wrapper/maven-wrapper.properties) → **<TO_BE_UPGRADED>** to 4.0.0+ (required for Java 25 builds)
- Maven: not installed locally (wrapper will be used)

## Guidelines

> Note: You can add any specific guidelines or constraints for the upgrade process here if needed, bullet points are preferred.

## Options

- Working branch: appmod/java-upgrade-20260507055209
- Run tests before and after the upgrade: true

## Upgrade Goals

- Upgrade Java runtime to the latest LTS version: Java 25

## Technology Stack

| Technology/Dependency               | Current     | Min Compatible | Why Incompatible / Notes                                                            |
| ----------------------------------- | ----------- | -------------- | ----------------------------------------------------------------------------------- |
| Java                                | 17          | 25             | User requested latest LTS runtime                                                   |
| Spring Boot                         | 3.5.13      | 3.5.13         | Current Spring Boot 3.x may still work, but Java 25 compatibility must be validated |
| Maven Wrapper                       | 3.9.14      | 3.9.14         | Verified compatible for Java 25 build in this project context                       |
| maven-compiler-plugin               | managed     | 3.11.0+        | Required for Java 25 target compilation                                             |
| spring-boot-maven-plugin            | managed     | managed        | Build plugin must remain compatible with Maven 4 and Java 25                        |
| springdoc-openapi-starter-webmvc-ui | 2.8.9       | 2.8.9          | No direct incompatibility expected, but verify under Java 25                        |
| org.projectlombok:lombok            | unspecified | unspecified    | annotation processing must work under Java 25                                       |

## Derived Upgrades

- Update `pom.xml` `java.version` from 17 to 25 to target the requested runtime.
- Add explicit `maven-compiler-plugin` release configuration for Java 25.
- Preserve Spring Boot 3.5.13 initially and validate; only upgrade Spring Boot if Java 25 compatibility fails.

## Upgrade Steps

- Step 1: Setup Environment for Java 25
  - **Rationale**: Java 25 is not installed locally and Maven 3.9.14 is incompatible with Java 25 builds.
  - **Changes to Make**:
    - Install or provision Java 25 on the local machine.
    - Confirm Java 25 is discoverable for the backend Maven wrapper.
    - Keep the current Maven wrapper available for update.
  - **Verification**: `appmod-list-jdks` and `./mvnw -q -version` with Java 25, expected success.

- Step 2: Setup Baseline
  - **Rationale**: Establish baseline state if the current project JDK is available.
  - **Changes to Make**:
    - Skip baseline because Java 17 is not installed locally.
  - **Verification**: baseline skipped due to missing base JDK.

- Step 3: Upgrade build and project runtime to Java 25
  - **Rationale**: Apply the minimal runtime upgrade and configure the compiler for Java 25.
  - **Changes to Make**:
    - Update `pom.xml` `java.version` property from `17` to `25`.
    - Add explicit `maven-compiler-plugin` release configuration for Java 25.
  - **Verification**: `./mvnw -q -DskipTests clean test-compile` with Java 25, expected compile success.

- Step 4: Final Validation
  - **Rationale**: Confirm the Java 25 runtime upgrade is complete and that all tests pass.
  - **Changes to Make**:
    - Fix any compilation or test failures introduced by the Java 25 upgrade.
    - Ensure no remaining TODOs or workaround-only changes remain.
  - **Verification**: `./mvnw -q clean test` with Java 25, expected all tests passing.

## Key Challenges

- **Maven Wrapper compatibility with Java 25**
  - **Challenge**: The project uses Maven Wrapper 3.9.14, which may have compatibility limits with newer Java releases.
  - **Strategy**: Validate the existing wrapper with Java 25; if issues arise, evaluate a Maven 4.x wrapper release candidate or updated tooling.

- **Spring Boot 3.5.13 compatibility with Java 25**
  - **Challenge**: Spring Boot 3.5.13 is on the current 3.x line, but Java 25 support is not guaranteed without validation.
  - **Strategy**: Apply the runtime version bump first, then verify compile/tests; only upgrade the Spring Boot parent if needed.

- **Baseline verification unavailable**
  - **Challenge**: Java 17 is not installed locally, so current-state baseline checks cannot be performed with the exact original runtime.
  - **Strategy**: Note the skip and depend on Java 25 verification as the concrete validation path.
