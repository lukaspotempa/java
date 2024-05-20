import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.awt.event.KeyListener;
import java.awt.MouseInfo;
import java.util.concurrent.CopyOnWriteArrayList;


public class Main {
    private static int colorCounter = 0;
    private static int colorCounter2 = 0;

    private static int frameSizeX = 1600;
    private static int frameSizeY = 800;
    private static float radiusConstraint = 300;

    private static Vector2 gravity = new Vector2(0, 1000f);

    private static Circle circle2;
    private static Vector2 CirclePos = new Vector2(frameSizeX/2, frameSizeY/2);

    private static CopyOnWriteArrayList<Circle> circles = new CopyOnWriteArrayList<>();
    private static JFrame frame = new JFrame("Circle Collisions");
    private static CustomPanel panel = new CustomPanel();

    private static long lastUpdateTime;

    public static void main(String[] args) {
        // Set up the frame
        frame.setSize(frameSizeX, frameSizeY);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);

        // Add a circle to the list
        Circle circle = new Circle(new Vector2(frameSizeX/2, frameSizeY/2), Color.BLUE, 10);
        circle2 = new Circle(new Vector2(CirclePos.x, CirclePos.y), Color.GRAY, (int) (radiusConstraint));
        circles.add(circle);
        handleKeyPress();

        // Initialize lastUpdateTime
        lastUpdateTime = System.nanoTime();

        // Create and start the game loop thread
        Thread gameLoopThread = new Thread(Main::gameLoop);
        gameLoopThread.start();
    }

    private static Color mapColor() {
        int r = 0;
        int g = 0;
        int b = 0;
        int addition = 2;
        int threshold = 40;
        if (colorCounter <= threshold) {
            r = 255 - (colorCounter % threshold);
            System.out.print(r);
            colorCounter += addition;
        }
        if (colorCounter > threshold && colorCounter <= threshold * 2) {
            r = 255;
            g = 255 - (colorCounter % threshold);
            colorCounter += addition;
        }
        if (colorCounter > threshold * 2 && colorCounter <= threshold * 3) {
            g = 255 - (colorCounter % threshold);
            colorCounter += addition;
        }
        if (colorCounter > threshold * 3 && colorCounter <= threshold * 4) {
            g = 255;
            b = 255 - (colorCounter % threshold);
            colorCounter += addition;
        }
        if (colorCounter > threshold * 4 && colorCounter <= threshold * 5) {
            b = 255 - (colorCounter % threshold);
            colorCounter += addition;
        }
        if (colorCounter > threshold * 5 && colorCounter <= threshold * 6) {
            r = 255;
            b = 255 - (colorCounter % threshold);
            colorCounter += addition;
        }
        if(colorCounter > threshold * 6) {
            colorCounter = 0;
        }
        return new Color(r, g, b);
    }

    private static void gameLoop() {
        while (true) {
            long currentTime = System.nanoTime();
            float dt = (currentTime - lastUpdateTime) / 1e9f; // Convert nanoseconds to seconds
            lastUpdateTime = currentTime;

            update(dt);
            panel.repaint();

            // Sleep for a short duration to yield CPU time
            try {
                Thread.sleep(5); // Sleep for 5 milliseconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void update(float dt) {
        int substeps = 8;
        float sub_dt = dt / substeps;
        for (int i = substeps; i > 0; i--) {
            applyGravity();
            applyConstraint();
            updatePositions(sub_dt);
            checkCollision(sub_dt);
        }
    }

    public static void applyGravity() {
        for (Circle circle : circles) {
            circle.accelerate(gravity);
        }
    }

    public static void updatePositions(float dt) {
        for (Circle circle : circles) {
            circle.updatePosition(dt);
        }
    }

    public static void applyConstraint() {
        Vector2 position = CirclePos;
        for (Circle circle : circles) {
            Vector2 toObj = circle.position.subtract(position);
            float dist = toObj.magnitude();

            if (dist > (radiusConstraint - circle.radius)) {
                Vector2 n = new Vector2(toObj.x / dist, toObj.y / dist);
                circle.position = new Vector2(position.x + n.x * (radiusConstraint - circle.radius),
                        position.y + n.y * (radiusConstraint - circle.radius));
            }
        }
    }

    public static void checkCollision(float dt) {
        for (int i = 0; i < circles.size(); ++i) {
            for (int j = i+1; j < circles.size(); ++j) {
                Circle circle1 = circles.get(i);
                Circle circle2 = circles.get(j);
                Vector2 collision_axis = circle1.position.subtract(circle2.position);
                float dist = collision_axis.magnitude();
                float biggerRadius = circle1.radius + circle2.radius;
                if (dist < biggerRadius) {
                    Vector2 n = new Vector2(collision_axis.x / dist, collision_axis.y / dist);
                    float delta = biggerRadius - dist;
                    circle1.position = new Vector2(circle1.position.x + 0.5 * delta * n.x, circle1.position.y + 0.5 * delta * n.y);
                    circle2.position = new Vector2(circle2.position.x - 0.5 * delta * n.x, circle2.position.y - 0.5 * delta * n.y);
                }
            }
        }
    }

    private static class CustomPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // Enable anti-aliasing
            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY); // Enhance rendering quality
            g2d.setColor(Color.BLACK);
            g2d.fillRect(0, 0, getWidth(), getHeight());
            circle2.Draw(g2d); // Use g2d instead of g
            for (Circle circle : circles) {
                circle.Draw(g2d); // Use g2d instead of g
            }
        }
    }

    private static void handleKeyPress() {
        frame.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                Point point = MouseInfo.getPointerInfo().getLocation();
                Vector2 position = new Vector2(point.x, point.y);
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    Circle circle = new Circle(new Vector2(CirclePos.x, CirclePos.y),
                            mapColor(), 10);
                    circles.add(circle);
                }
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    Circle circle = new Circle(new Vector2(position.x, position.y),
                            mapColor(), 10);
                    circles.add(circle);
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}
