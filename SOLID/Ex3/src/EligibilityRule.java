public interface EligibilityRule {
    /**
     * @return failure reason if the rule fails; otherwise null.
     */
    String evaluate(StudentProfile s, RuleInput input);
}

