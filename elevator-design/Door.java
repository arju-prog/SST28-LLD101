public class Door {

    private boolean open;

    public Door() {
        this.open = false;
    }

    public void open() {
        if (!open) {
            open = true;
            System.out.println("  Door opened.");
        }
    }

    public void close() {
        if (open) {
            open = false;
            System.out.println("  Door closed.");
        }
    }

    public boolean isOpen() {
        return open;
    }
}
