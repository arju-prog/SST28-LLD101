public class DisciplinaryFlagRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s, RuleInput input) {
        if (s.disciplinaryFlag != LegacyFlags.NONE) return "disciplinary flag present";
        return null;
    }
}

