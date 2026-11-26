package edu.swe431.registration;

import java.util.regex.Pattern;

public final class RegistrationInputValidator {
    private static final Pattern STUDENT_ID = Pattern.compile("[0-9]{8}");
    private static final Pattern COURSE_CODE = Pattern.compile("[A-Z]{3}[0-9]{3}");

    private RegistrationInputValidator() {
    }

    public static void validateStudentId(String studentId) {
        if (studentId == null || !STUDENT_ID.matcher(studentId).matches()) {
            throw new IllegalArgumentException("Student ID must contain exactly eight ASCII digits.");
        }
    }

    public static void validateCourseCode(String courseCode) {
        if (courseCode == null || !COURSE_CODE.matcher(courseCode).matches()) {
            throw new IllegalArgumentException(
                    "Course code must contain three uppercase ASCII letters followed by three ASCII digits.");
        }
    }
}
