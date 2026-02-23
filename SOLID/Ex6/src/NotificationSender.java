public abstract class NotificationSender {
    protected final AuditLog audit;
    protected NotificationSender(AuditLog audit) { this.audit = audit; }

    /**
     * Base contract:
     * - Never throws for validation issues; returns {@link SendResult} instead.
     * - Accepts null fields in {@link Notification}; normalizes them to empty strings.
     */
    public final SendResult send(Notification n) {
        if (n == null) return SendResult.error("notification cannot be null");
        Notification normalized = normalize(n);
        return doSend(normalized);
    }

    public abstract String channel();

    protected abstract SendResult doSend(Notification n);

    private Notification normalize(Notification n) {
        String subject = n.subject == null ? "" : n.subject;
        String body = n.body == null ? "" : n.body;
        String email = n.email == null ? "" : n.email;
        String phone = n.phone == null ? "" : n.phone;
        return new Notification(subject, body, email, phone);
    }
}
