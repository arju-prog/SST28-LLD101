import java.util.List;

public class ConfirmationPrinter {
    public static void printInput(String raw) {
        System.out.println("INPUT: " + raw);
    }

    public static void printErrors(List<String> errors) {
        System.out.println("ERROR: cannot register");
        for (String e : errors) System.out.println("- " + e);
    }

    public static void printSuccess(String id, int totalCount, StudentRecord rec) {
        System.out.println("OK: created student " + id);
        System.out.println("Saved. Total students: " + totalCount);
        System.out.println("CONFIRMATION:");
        System.out.println(rec);
    }
}
