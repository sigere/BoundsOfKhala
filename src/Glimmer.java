import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

import java.util.Random;

class Glimmer
{
    public PVector position;
    float radius;
    float step;
    float direction;
    float padding;
    Color color;

    public Glimmer(Main map) {
        Random random = new Random();

        this.radius = 5.0f;
        this.step = 2.0f;
        this.direction = random.nextFloat(2.0f * (float) Math.PI);
        this.padding = 1.0f;
        this.color = Color.white();
        this.insertOnMap(map);
    }

    private void insertOnMap(Main map) {
        Random random = new Random();

        do {
            float angle = random.nextFloat(2.0f * (float) Math.PI);
            // calculate max radius for given angle
            float horizontalRadius = map.width / 2.0f;
            float verticalRadius = map.height / 2.0f;
            float maxRadius = horizontalRadius * verticalRadius;
            maxRadius /=  (Math.sqrt(
                    Math.pow(horizontalRadius, 2) * Math.pow(Math.sin(angle), 2)
                  + Math.pow(verticalRadius, 2) * Math.pow(Math.cos(angle), 2)
            ));
            maxRadius -= this.radius;

            float x = horizontalRadius + random.nextFloat(maxRadius) * (float) Math.cos(angle);
            float y = verticalRadius + random.nextFloat(maxRadius) * (float) Math.sin(angle);

            this.position = new PVector(x, y);
        } while (this.collidesWithAnyOther(map.mainArray));
    }

    private boolean collidesWithAnyOther(Glimmer[] array){
        for (Glimmer glimmer : array) {
            if (glimmer != null && glimmer != this && this.collidesWithOther(glimmer)) {
                return true;
            }
        }
        return false;
    }

    private boolean collidesWithOther(Glimmer glimmer) {
        return this.position.dist(glimmer.position) <= this.radius + glimmer.radius;
    }

    public void move(Main map) {
        PVector startingPosition = this.position.copy();
        this.position.add(new PVector(
           this.step * (float) Math.cos(this.direction),
           this.step * (float) Math.sin(this.direction)
        ));

        if (this.isOutsideMap(map)) {
            this.position = startingPosition;
            PVector fromCenter = this.position.copy().sub(map.center());
            PVector current = PVector.fromAngle(this.direction);
            float angle = (float) Math.acos(current.dot(fromCenter) / current.mag() / fromCenter.mag());
//            PApplet.print("current: " + current.heading() * 57.2957795f + "\n");
//            PApplet.print("this.position: " + this.position.x + ", " + this.position.y + "\n");
//            PApplet.print("mapCenter: " + map.center().x + ", " + map.center().y + "\n");
//            PApplet.print("fromCenter: " + fromCenter.x + ", " + fromCenter.y + "\n");
//            PApplet.print("angle: " + angle * 57.2957795f + "\n\n");
            this.direction -= Math.PI - 2.0f * angle;

            this.position.add(new PVector(
                    this.step * (float) Math.cos(this.direction),
                    this.step * (float) Math.sin(this.direction)
            ));
        }
    }

    private boolean isOutsideMap(Main map) {
        return (Math.pow(this.position.x - map.width / 2.0f, 2) / Math.pow(map.width / 2.0f - this.radius, 2)) +
                (Math.pow(this.position.y - map.height / 2.0f, 2) / Math.pow(map.height / 2.0f - this.radius, 2)) >= 1.0f;
    }

    public void draw(Main map) {
        map.fill(this.color.red, this.color.green, this.color.blue);
        map.ellipseMode(PConstants.CENTER);
        map.ellipse(
                this.position.x,
                this.position.y,
                2.0f * this.radius - padding,
                2.0f * this.radius - padding
        );
    }
}
