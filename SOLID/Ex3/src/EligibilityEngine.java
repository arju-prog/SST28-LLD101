import java.util.*;

public class EligibilityEngine {
    private final FakeEligibilityStore store;
    private final List<EligibilityRule> rules;
    private final RuleInput input;

    public EligibilityEngine(FakeEligibilityStore store) {
        this(store, new RuleInput(), defaultRules());
    }

    public EligibilityEngine(FakeEligibilityStore store, RuleInput input, List<EligibilityRule> rules) {
        this.store = store;
        this.input = input;
        this.rules = rules;
    }

    public void runAndPrint(StudentProfile s) {
        ReportPrinter p = new ReportPrinter();
        EligibilityEngineResult r = evaluate(s); // giant conditional inside
        p.print(s, r);
        store.save(s.rollNo, r.status);
    }

    public EligibilityEngineResult evaluate(StudentProfile s) {
        List<String> reasons = new ArrayList<>();
        for (EligibilityRule rule : rules) {
            String failureReason = rule.evaluate(s, input);
            if (failureReason != null) {
                reasons.add(failureReason); // preserve reason order + single-failure behavior
                return new EligibilityEngineResult("NOT_ELIGIBLE", reasons);
            }
        }
        return new EligibilityEngineResult("ELIGIBLE", reasons);
    }

    private static List<EligibilityRule> defaultRules() {
        return List.of(
                new DisciplinaryFlagRule(),
                new MinCgrRule(),
                new MinAttendanceRule(),
                new MinCreditsRule()
        );
    }
}

class EligibilityEngineResult {
    public final String status;
    public final List<String> reasons;
    public EligibilityEngineResult(String status, List<String> reasons) {
        this.status = status;
        this.reasons = reasons;
    }
}
