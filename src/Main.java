import processing.core.PApplet;
import processing.core.PVector;

public class Main extends PApplet
{
    public Glimmer[] mainArray;
    public static void main(String[] args){
        PApplet.main("Main", args);
    }

    public PVector center() {
        return new PVector(this.width / 2.0f, this.height / 2.0f);
    }

    @Override
    public void settings() {
        size(1000, 500);
    }

    @Override
    public void setup() {
        this.mainArray = new Glimmer[1];
        for(int i=0; i< this.mainArray.length; i++) {
            this.mainArray[i] = new Glimmer(this);
        }
        PVector a = new PVector(0, 0);
        PVector b = new PVector(0, 1);
        PVector c = new PVector(1, 0);
        PVector vector1 = b.copy().sub(a);
        PVector vector2 = c.copy().sub(a);
        float angle = (float) Math.acos(vector1.dot(vector2) / vector1.mag() / vector2.mag());
//        PApplet.print(angle * 57.2957795f + "\n\n");

//        background(0);
//        for (Glimmer glimmer : mainArray) {
//            glimmer.move(this);
//            glimmer.draw(this);
//        }
    }

    @Override
    public void draw() {
        background(0);
        for (Glimmer glimmer : mainArray) {
            glimmer.move(this);
            glimmer.draw(this);
        }
    }
}
