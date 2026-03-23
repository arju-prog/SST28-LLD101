package pen;

public class GelInkScribble implements ScribbleStrategy {
    @Override
    public void scribble() {
        System.out.println("Scribbling text leaving a thick gel trace.");
    }
}
