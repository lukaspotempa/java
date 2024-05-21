package verlet_3d;

import verlet_physics.Vector2;

public class Vector3 {
    public double x;
    public double y;
    public double z;

    public Vector3(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Vector3 add(Vector3 v) {
        return new Vector3(this.x + v.x, this.y + v.y, this.z + v.z);
    }

    public Vector3 subtract(Vector3 other) {
        return new Vector3(this.x - other.x, this.y - other.y, this.z - other.z);
    }

    public Vector3 scale(double scalar) {
        return new Vector3(this.x * scalar, this.y * scalar, this.z * scalar);
    }

    public float magnitude() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public double dot(Vector3 other) {
        return this.x * other.x + this.y * other.y + this.z * other.z;
    }

    public Vector3 normalize() {
        double mag = this.magnitude();
        if (mag == 0) {
            throw new ArithmeticException("Cannot normalize a zero vector");
        }
        return this.scale(1.0 / mag);
    }

    @Override
    public String toString() {
        return "Vector3(" + x + ", " + y + ", " + z + ")";
    }
}
