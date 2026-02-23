public class MinCgrRule implements EligibilityRule {
    @Override
    public String evaluate(StudentProfile s, RuleInput input) {
        if (s.cgr < input.minCgr) return "CGR below " + input.minCgr;
        return null;
    }
}

