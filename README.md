# SWE431-course-registration
SWE431 group project — a Java course registration system with JUnit 5 tests for student ID and course code validation, enrollment eligibility, prerequisites, capacity limits, duplicate enrollment, withdrawals, and waitlist promotion.

## Run the tests

With JDK 17 and Maven installed, run `mvn test` from the repository root. Maven prints the summary in the terminal. Surefire writes detailed text and XML reports to `target/surefire-reports/`.

## Member 1 · Faris

`RegistrationInputValidator` in `edu.swe431.registration` provides `validateStudentId(String)` and `validateCourseCode(String)`. Call both methods before changing registration state when adding the registration entry point.

- Student ID must be exactly nine ASCII digits. The first two digits are the last two digits of the university entry year. In the synthetic ID `230000000`, `23` represents 2023. The validator checks only the format and accepts any two-digit prefix.
- The provisional course code format requires exactly three uppercase ASCII letters followed by three ASCII digits, as in `SWE431`.
- Both methods throw `IllegalArgumentException` for null, empty, malformed, or padded input.

### Test design

All test inputs are synthetic.

| Group | Student ID examples | Course code examples | Expected result and reason |
| --- | --- | --- | --- |
| Valid format | `230000000`, `000000000`, `990000000` | `SWE431`, `ABC007` | Pass because the character classes and lengths match. Numeric segments can start with zero. |
| One character short or long | `23000000`, `2300000000` | `SWE43`, `SWE4310` | Throw because each field has a fixed length. |
| Invalid characters at the required length | `23000A000`, `23000000٧` | `sWE431`, `SWÉ431`, `SWE43١`, `SWE43A` | Throw because case, ASCII character classes, or character positions are wrong. |
| Missing or empty | `null`, `""` | `null`, `""` | Throw because both fields require a value. |
| Whitespace | `" 230000000"`, `"230000000 "`, `" "` | `" SWE431"`, `"SWE431 "` | Throw because spaces are outside the allowed formats. |
