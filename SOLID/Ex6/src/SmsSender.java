public class SmsSender extends NotificationSender {
    public SmsSender(AuditLog audit) { super(audit); }

    @Override
    public String channel() { return "SMS"; }

    @Override
    protected SendResult doSend(Notification n) {
        // Ignores subject; base type doesn't clarify expectations (smell)
        System.out.println("SMS -> to=" + n.phone + " body=" + n.body);
        audit.add("sms sent");
        return SendResult.ok();
    }
}
