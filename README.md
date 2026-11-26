# SWE431-course-registration
SWE431 group project — a Java course registration system with JUnit 5 tests for student ID and course code validation, enrollment eligibility, prerequisites, capacity limits, duplicate enrollment, withdrawals, and waitlist promotion.

## Run the tests

With JDK 17 and Maven installed, run `mvn test` from the repository root. Maven prints the summary in the terminal. Surefire writes detailed text and XML reports to `target/surefire-reports/`.

## Member 1 · Faris

`RegistrationInputValidator` in `edu.swe431.registration` provides `validateStudentId(String)` and `validateCourseCode(String)`. Call both methods before changing registration state when adding the registration entry point.

- Student ID must be exactly nine ASCII digits. The first two digits are the last two digits of the university entry year. In the synthetic ID `230000000`, `23` represents 2023. The validator checks only the format and accepts any two-digit prefix.
- The provisional course code syntax is `[A-Z0-9]+(?:[.-][A-Z0-9]+)*`. Each nonempty group contains uppercase ASCII letters, ASCII digits, or both. A single period or hyphen may separate groups. This check validates syntax only. Course existence is a separate catalog check.
- Both methods throw `IllegalArgumentException` for null, empty, malformed, or padded input.

### Test design

Student ID examples are synthetic. The valid course code cases include eight observed codes and two synthetic one-character codes.

| Group | Student ID examples | Course code examples | Expected result and reason |
| --- | --- | --- | --- |
| Valid format | `230000000`, `000000000`, `990000000` | `DA529`, `CSR207`, `ECON301`, `IAED402`, `MED150-C4`, `METIK296`, `PREPB2-TR-16`, `PREPA1-GER.1`, `A`, `0` | Pass because each input matches its field's syntax. |
| One digit short or long | `23000000`, `2300000000` | | Throw because student IDs require exactly nine digits. |
| Invalid characters | `23000A000`, `23000000٧` | `da529`, `SWÉ431`, `SWE43١`, `MED150_C4` | Throw because the input contains characters outside the field's syntax. |
| Misplaced separators | | `-DA529`, `DA529.`, `MED150--C4`, `PREPA1..GER`, `MED150-.C4` | Throw because separators must appear individually between nonempty groups. |
| Missing or empty | `null`, `""` | `null`, `""` | Throw because both fields require a value. |
| Whitespace | `" 230000000"`, `"230000000 "`, `" "` | `" DA529"`, `"DA529 "`, `" "` | Throw because spaces are outside the allowed formats. |
