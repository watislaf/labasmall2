package Objects;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class SmallBall extends Drawable {
    public SmallBall(Point x, Integer height) {
        super(x, height);
    }

    @Override
    public void Draw(Graphics2D g2d) {
        Shape theCircle;
        theCircle = new Ellipse2D.Double(position_.x ,
                position_.y , 2.0 * 40, 2.0 * 40);
        g2d.fill(theCircle);

    }

    @Override
    public Double GetVolume() {
        return (double) height_ * height_ * height_ * Math.PI * 4 / 3;
    }


}
