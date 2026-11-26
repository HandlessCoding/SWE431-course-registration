package edu.swe431.registration;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RegistrationInputValidatorTest {
    @ParameterizedTest
    @ValueSource(strings = {"01234567", "87654321"})
    void acceptsStudentIdsWithExactlyEightAsciiDigits(String studentId) {
        assertDoesNotThrow(() -> RegistrationInputValidator.validateStudentId(studentId));
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {
            "0123456", "012345678", "01234A67", "0123456\u0667", " 01234567", "01234567 "
    })
    void rejectsMissingOrMalformedStudentIds(String studentId) {
        assertThrows(IllegalArgumentException.class,
                () -> RegistrationInputValidator.validateStudentId(studentId));
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
