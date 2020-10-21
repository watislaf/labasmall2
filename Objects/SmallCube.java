package Objects;


import java.awt.*;

public class SmallCube extends Drawable {
    public SmallCube(Point x, Integer height) {
        super(x,height);
    }

    @Override
    public void Draw(Graphics2D g2d) {
        g2d.drawRect(position_.x, position_.y, 80, 80);
    }

    @Override
    public Double GetVolume() {
        return (double) height_ * height_ * height_ * height_;
    }
}

