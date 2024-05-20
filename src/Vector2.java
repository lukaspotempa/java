public class Vector2 {
    public double x;
    public double y;

    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 v) {
        return new Vector2(this.x + v.x, this.y + v.y);
    }

    public Vector2 subtract(Vector2 other) {
        return new Vector2(this.x - other.x, this.y - other.y);
    }

    public Vector2 scale(double scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public float magnitude() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    public double dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }

    public Vector2 normalize() {
        double mag = this.magnitude();
        if (mag == 0) {
            throw new ArithmeticException("Cannot normalize a zero vector");
        }
        return this.scale(1.0 / mag);
    }

    @Override
    public String toString() {
        return "Vector2(" + x + ", " + y + ")";
    }
}
