public class Main {
    public static void main(String[] args) {
        System.out.println("=== Notification Demo ===");
        AuditLog audit = new AuditLog();

        Notification n = new Notification("Welcome", "Hello and welcome to SST!", "riya@sst.edu", "9876543210");

        NotificationSender email = new EmailSender(audit);
        NotificationSender sms = new SmsSender(audit);
        NotificationSender wa = new WhatsAppSender(audit);

        sendAndHandle(email, n, audit);
        sendAndHandle(sms, n, audit);
        sendAndHandle(wa, n, audit);

        System.out.println("AUDIT entries=" + audit.size());
    }

    private static void sendAndHandle(NotificationSender sender, Notification n, AuditLog audit) {
        SendResult r = sender.send(n);
        if (!r.ok) {
            System.out.println(sender.channel() + " ERROR: " + r.errorMessage);
            audit.add(sender.channel() + " failed");
        }
    }
}
