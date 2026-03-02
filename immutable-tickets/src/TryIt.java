import com.example.tickets.IncidentTicket;
import com.example.tickets.TicketService;

import java.util.List;

/**
 * Demo that shows the benefits of immutability and the new Builder API.
 */
public class TryIt {

    public static void main(String[] args) {
        TicketService service = new TicketService();

        // 1. Create a ticket through service (uses Builder internally)
        IncidentTicket t1 = service.createTicket("TCK-1001", "reporter@example.com", "Payment failing on checkout");
        System.out.println("Created T1: " + t1);

        // 2. "Update" through service (returns a NEW instance)
        IncidentTicket t2 = service.assign(t1, "agent@example.com");
        IncidentTicket t3 = service.escalateToCritical(t2);

        System.out.println("\nAfter service updates:");
        System.out.println("T1 (original) : " + t1);
        System.out.println("T2 (assigned) : " + t2);
        System.out.println("T3 (escalated): " + t3);

        System.out.println("\nAre T1 and T3 different objects? " + (t1 != t3));

        // 3. Verify external immutability of tags
        try {
            List<String> tags = t3.getTags();
            tags.add("HACKED_FROM_OUTSIDE");
        } catch (UnsupportedOperationException e) {
            System.out.println("\nSuccessfully blocked external mutation of tags list!");
        }

        // 4. Manual usage of Builder
        IncidentTicket manual = IncidentTicket.builder()
                .id("TCK-2002")
                .reporterEmail("user@test.com")
                .title("Manual Ticket")
                .priority("HIGH")
                .build();
        System.out.println("\nManually built: " + manual);

        // 5. Validation check (will throw exception)
        try {
            IncidentTicket.builder()
                    .id("INVALID ID!")
                    .reporterEmail("not-an-email")
                    .title("")
                    .build();
        } catch (IllegalArgumentException e) {
            System.out.println("\nSuccessfully caught validation error: " + e.getMessage());
        }
    }
}
