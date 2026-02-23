import java.util.*;

public class OnboardingService {
    private final StudentRepository repository;

    public OnboardingService(StudentRepository repository) { 
        this.repository = repository; 
    }

    public void registerFromRawInput(String raw) {
        ConfirmationPrinter.printInput(raw);

        Map<String, String> kv = InputParser.parse(raw);
        String name = kv.getOrDefault("name", "");
        String email = kv.getOrDefault("email", "");
        String phone = kv.getOrDefault("phone", "");
        String program = kv.getOrDefault("program", "");

        List<String> errors = StudentValidator.validate(name, email, phone, program);
        if (!errors.isEmpty()) {
            ConfirmationPrinter.printErrors(errors);
            return;
        }

        String id = IdUtil.nextStudentId(repository.count());
        StudentRecord rec = new StudentRecord(id, name, email, phone, program);
        repository.save(rec);

        ConfirmationPrinter.printSuccess(id, repository.count(), rec);
    }
}
