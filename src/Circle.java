import javax.swing.*;
import java.awt.*;

public class Circle extends JComponent {
    public int radius = 10;
    public Color color = Color.BLUE;
    public Vector2 position = new Vector2(0, 0);
    public Vector2 oldPosition = new Vector2(0, 0);
    public Vector2 acceleration = new Vector2(0, 0);

    public Circle(Vector2 pos, Color color, int radius) {
        this.position = pos;
        this.oldPosition = new Vector2(pos.x, pos.y);
        this.color = color;
        this.radius = radius;
    }

    public Circle() {
        this.position = new Vector2(0, 0);
        this.color = Color.BLUE;
        this.radius = 10;
        this.oldPosition = new Vector2(0, 0);
    }

    public void Draw(Graphics g) {
        g.setColor(color);
        g.fillOval((int) position.x - this.radius, (int) position.y - this.radius, radius * 2, radius * 2);
    }

    public void updatePosition(float dt) {
        Vector2 velocity = position.subtract(oldPosition);
        oldPosition = new Vector2(position.x, position.y);

        // Update position with Verlet integration
        double newX = position.x + velocity.x + 0.5 * acceleration.x * dt * dt;
        double newY = position.y + velocity.y + 0.5 * acceleration.y * dt * dt;

        // Set new position
        position = new Vector2((float)newX, (float)newY);

        // Reset acceleration
        acceleration = new Vector2(0, 0);
    }

    public void accelerate(Vector2 acceleration) {
        this.acceleration = this.acceleration.add(acceleration);
    }
}
