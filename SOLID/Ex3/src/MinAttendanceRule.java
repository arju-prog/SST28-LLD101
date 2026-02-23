public class MinAttendanceRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s, RuleInput input) {
        if (s.attendancePct < input.minAttendance) return "attendance below " + input.minAttendance;
        return null;
    }
}

