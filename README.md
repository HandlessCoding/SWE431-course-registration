# SWE431-course-registration
SWE431 group project — a Java course registration system with JUnit 5 tests for student ID and course code validation, enrollment eligibility, prerequisites, capacity limits, duplicate enrollment, withdrawals, and waitlist promotion.

## Run the tests

With JDK 17 and Maven installed, run `mvn test` from the repository root. Maven prints the summary in the terminal. Surefire writes detailed text and XML reports to `target/surefire-reports/`.

## Member 1 · Faris

`RegistrationInputValidator` in `edu.swe431.registration` provides `validateStudentId(String)` and `validateCourseCode(String)`. Call both methods before changing registration state when adding the registration entry point.

The assignment leaves formats unspecified. These rules are **provisional** pending group agreement.

- Student ID must be exactly eight ASCII digits. A leading zero is valid, as in `01234567`.
- Course code must be exactly three uppercase ASCII letters followed by three ASCII digits, as in `SWE431`.
- Both methods throw `IllegalArgumentException` for null, empty, malformed, or padded input.

### Test design

All test inputs are synthetic.

| Group | Student ID examples | Course code examples | Expected result and reason |
| --- | --- | --- | --- |
| Valid format | `01234567`, `87654321` | `SWE431`, `ABC007` | Pass because the character classes and lengths match. Numeric segments can start with zero. |
| One character short or long | `0123456`, `012345678` | `SWE43`, `SWE4310` | Throw because each field has a fixed length. |
| Invalid characters at the required length | `01234A67`, `0123456٧` | `sWE431`, `SWÉ431`, `SWE43١`, `SWE43A` | Throw because case, ASCII character classes, or character positions are wrong. |
| Missing or empty | `null`, `""` | `null`, `""` | Throw because both fields require a value. |
| Leading or trailing space | `" 01234567"`, `"01234567 "` | `" SWE431"`, `"SWE431 "` | Throw because spaces are outside the allowed formats. |
