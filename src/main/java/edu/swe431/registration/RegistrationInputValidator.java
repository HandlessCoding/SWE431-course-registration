package edu.swe431.registration;

import java.util.regex.Pattern;

public final class RegistrationInputValidator {
    private static final Pattern STUDENT_ID = Pattern.compile("[0-9]{9}");
    // Separator-delimited groups never need to backtrack. Possessive repetition avoids stack growth.
    private static final Pattern COURSE_CODE = Pattern.compile("[A-Z0-9]+(?:[.-][A-Z0-9]+)*+");

    private RegistrationInputValidator() {
    }

    public static void validateStudentId(String studentId) {
        if (studentId == null || !STUDENT_ID.matcher(studentId).matches()) {
            throw new IllegalArgumentException("Student ID must contain exactly nine ASCII digits.");
        }
    }

    public static void validateCourseCode(String courseCode) {
        if (courseCode == null || !COURSE_CODE.matcher(courseCode).matches()) {
            throw new IllegalArgumentException(
                    "Course code must contain uppercase ASCII letters or digits"
                            + " with optional single periods or hyphens between groups.");
        }
    }
}
