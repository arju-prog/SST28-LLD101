public class MinCreditsRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s, RuleInput input) {
        if (s.earnedCredits < input.minCredits) return "credits below " + input.minCredits;
        return null;
    }
}

