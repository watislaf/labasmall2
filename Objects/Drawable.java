package Objects;


import java.awt.*;

public abstract class Drawable {
    Point position_;
    Integer height_;

    public Integer getHeight() {
        return height_;
    }

    Drawable(Point x, Integer height) {
        position_ = new Point(x.x, x.y);
        height_ = height;
    }

    public abstract void Draw(Graphics2D g2d);

    public abstract Double GetVolume();
}
