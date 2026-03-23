package pen;

public class FountainScribble implements ScribbleStrategy {
    @Override
    public void scribble() {
        System.out.println("Scribbling text with classic fountain ink flow.");
    }
}
