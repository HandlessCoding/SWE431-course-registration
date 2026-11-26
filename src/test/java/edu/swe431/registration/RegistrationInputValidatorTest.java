package edu.swe431.registration;

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
    @ValueSource(strings = {"SWE431", "ABC007"})
    void acceptsCourseCodesWithThreeUppercaseAsciiLettersAndThreeAsciiDigits(String courseCode) {
        assertDoesNotThrow(() -> RegistrationInputValidator.validateCourseCode(courseCode));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "SWE43", "SWE4310", "sWE431", "SW\u00C9431", "SWE43\u0661", "SWE43A", " SWE431", "SWE431 "
    })
    void rejectsMissingOrMalformedCourseCodes(String courseCode) {
        assertThrows(IllegalArgumentException.class,
                () -> RegistrationInputValidator.validateCourseCode(courseCode));
    }
}
