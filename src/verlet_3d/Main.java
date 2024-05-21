package verlet_3d;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Sphere;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.paint.Color;
import javafx.scene.PerspectiveCamera;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        // Drawing a Sphere
        Sphere sphere = new Sphere();
        sphere.setRadius(50);
        sphere.setTranslateZ(100);

        // Creating a Group object
        Group root = new Group(sphere);

        // Creating a scene object
        Scene scene = new Scene(root, 1800, 950);

        // Setting material to the sphere
        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.BLUE);
        material.setSpecularColor(Color.LIGHTBLUE);
        sphere.setMaterial(material);

        // Creating and setting up the camera
        PerspectiveCamera camera = new PerspectiveCamera(true);
        camera.setTranslateX(0);
        camera.setTranslateY(0);
        camera.setTranslateZ(-1000); // Position the camera farther away from the sphere
        camera.setNearClip(0.1);
        camera.setFarClip(10000);
        scene.setCamera(camera);

        // Setting title to the Stage
        stage.setTitle("Drawing a Sphere - draw fill");

        // Adding scene to the stage
        stage.setScene(scene);

        // Displaying the contents of the stage
        stage.show();
    }

    public static void main(String args[]) {
        launch(args);
    }
}
