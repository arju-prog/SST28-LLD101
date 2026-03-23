package pen;

public class BallPointScribble implements ScribbleStrategy {
    @Override
    public void scribble() {
        System.out.println("Scribbling text using a basic ball-point tip.");
    }
}
