package edu.swe431.registration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationInputValidatorTest {
    @ParameterizedTest
    @ValueSource(strings = {"230000000", "000000000", "990000000"})
    void acceptsStudentIdsWithExactlyNineAsciiDigits(String studentId) {
        assertDoesNotThrow(() -> RegistrationInputValidator.validateStudentId(studentId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "23000000", "2300000000", "23000A000", "23000000\u0667", " 230000000", "230000000 ", " "
    })
    void rejectsMissingOrMalformedStudentIds(String studentId) {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> RegistrationInputValidator.validateStudentId(studentId));
        assertEquals("Student ID must contain exactly nine ASCII digits.", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {
            "DA529", "CSR207", "ECON301", "IAED402", "MED150-C4", "METIK296", "PREPB2-TR-16", "PREPA1-GER.1",
            "A", "0"
    })
    void acceptsCourseCodesMatchingProvisionalSyntax(String courseCode) {
        assertDoesNotThrow(() -> RegistrationInputValidator.validateCourseCode(courseCode));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            " DA529", "DA529 ", " ", "da529", "MED150-c4", "SW\u00C9431", "SWE43\u0661", "MED150_C4",
            "-DA529", ".DA529", "DA529-", "DA529.", "MED150--C4", "PREPA1..GER", "MED150-.C4"
    })
    void rejectsMissingOrMalformedCourseCodes(String courseCode) {
        assertThrows(IllegalArgumentException.class,
                () -> RegistrationInputValidator.validateCourseCode(courseCode));
    }

    @Test
    void validatesCourseCodesWithManySegments() {
        String courseCode = "A-".repeat(10_000) + "A";

        assertDoesNotThrow(() -> RegistrationInputValidator.validateCourseCode(courseCode));
        assertThrows(IllegalArgumentException.class,
                () -> RegistrationInputValidator.validateCourseCode(courseCode + "-"));
    }
}
