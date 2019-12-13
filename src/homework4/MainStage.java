/*
    Class: Main
    Contains main method and the primary stage for the application.

    TODO
    menu logic
    button logic
    adding shapes
    controlling shapes
    make things look pretty

*/

package homework4;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import javafx.stage.Stage;


public class MainStage extends Application {

    // main layout container for the main stage
    private BorderPane borderPane;

    // control box in the right side for shape transformations inside the subscene.
    // Shape transformations include: Rotate, Translate, Scale, and Change Colour.
    private VBox controlBox;
    private Label controlBoxLabel, rotateLabel, translateXLabel, translateYLabel, translateZLabel, scaleLabel, changeColorLabel;
    private Slider rotateSlider;
    private TextField translateXTextField, translateYTextField, translateZTextField;
    private Slider scaleSlider;
    private TextField changeColorTextField;

    // SubScene that has the shapes inside in 3D
    private Pane pane;
    private SubScene subScene;
    private double subSceneWidth;
    private double subSceneHeight;

    // Menu bar that contains "file" menu
    private MenuBar menuBar;

    // "file" menu that contains menu items, "save" and "open"
    private Menu fileMenu;

    private MenuItem saveMenuItem;
    private MenuItem openMenuItem;

    // Button to add the shape into the subscene
    private Button addShapeButton;

    // Shape attributes
    private double width, height, depth, radius;
    private boolean selected = false;

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {

        /***************** Defining the variables *****************/

        // layout that is the root of everything else
        borderPane = new BorderPane();

        // Control box and its labels
        controlBox = new VBox(20);
        controlBoxLabel = new Label("Shape Editor");
        rotateLabel = new Label("Rotate: ");
        translateXLabel = new Label("Translate X: ");
        translateYLabel = new Label("Translate Y: ");
        translateZLabel = new Label("Translate Z: ");
        scaleLabel = new Label("Change Scale: ");
        changeColorLabel = new Label("Change Colour: ");

        // Sliders
        rotateSlider = new Slider(0, 359, 0);
        scaleSlider = new Slider(0.5, 2.0, 1.0);
        rotateSlider.setShowTickMarks(true);
        scaleSlider.setShowTickMarks(true);

        translateXTextField = new TextField();
        translateYTextField = new TextField();
        translateZTextField = new TextField();

        changeColorTextField = new TextField();

        controlBox.setStyle("-fx-background-color: lightsteelblue");
        controlBox.setPadding(new Insets(35, 25, 20, 25));


        // Disable controls when no shape is selected
        if (!selected) {
            rotateSlider.isDisabled();
            scaleSlider.isDisabled();
            translateXTextField.isDisabled();
            translateYTextField.isDisabled();
            translateZTextField.isDisabled();
        }

        // sub-scene that contains the shapes (original: 800, 600)
        pane = new Pane(new Box());
        subScene = new SubScene(pane, subSceneWidth = 700, subSceneHeight = 500);

        pane.setStyle("-fx-border-style: solid; -fx-border-width: 2px; -fx-border-color: lightgray; -fx-background-color: white");

        // menu bar and its items
        menuBar = new MenuBar();
        fileMenu = new Menu("File");
        saveMenuItem = new MenuItem("Save");
        openMenuItem = new MenuItem("Open");

        // button to add shapes into the sub-scene
        addShapeButton = new Button("Add Shape");
        addShapeButton.setOnAction(e -> {
            showForm();
        });
        HBox buttonBox = new HBox(addShapeButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets( 50, 0, 50, 0));

        /***************** Adding Controls to control box *****************/
        controlBox.getChildren().add(controlBoxLabel);
        controlBox.getChildren().addAll(new HBox(40, rotateLabel, rotateSlider),
                new HBox(25, translateXLabel, translateXTextField),
                new HBox(25, translateYLabel, translateYTextField),
                new HBox(25, translateZLabel, translateZTextField),
                new HBox(10, scaleLabel, scaleSlider),
                new HBox(5, changeColorLabel, changeColorTextField) );

        controlBox.setMargin(controlBoxLabel, new Insets(0, 0, 15, 0));
        controlBox.setSpacing(25);
        controlBox.setAlignment(Pos.TOP_CENTER);
        controlBoxLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");

        /***************** Adding menu items where they belong *****************/
        menuBar.getMenus().add(fileMenu);
        fileMenu.getItems().addAll(saveMenuItem,openMenuItem);

        /***************** Adding sub-scene, button, and control box to the grid-pane *****************/
        borderPane.setCenter(subScene);
        borderPane.setBottom(buttonBox);
        borderPane.setRight(controlBox);
        borderPane.setTop(menuBar);

        borderPane.setMargin(borderPane.getTop(), new Insets(0, 0, 40, 0));
        borderPane.setMargin(borderPane.getCenter(), new Insets(0, 15, 10, 40));
        borderPane.setMargin(borderPane.getRight(), new Insets(0, 40, 10, 15));
        borderPane.setStyle("-fx-background-color: whitesmoke");

        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Shape Editor");
        primaryStage.show();
    }


    /***************** Opens new window with the add shape form *****************/
    private void showForm() {
        Stage stage = new Stage();

        // Labels & controls
        Label label = new Label("Add a Shape");
        label.setStyle("-fx-font-size: 18px; -fx-font-weight: bold");

        Button addButton = new Button("Add Shape");
        addButton.setPadding(new Insets(5, 50, 5, 50));

        Label shapeLabel = new Label("Shape: ");
        Label xLabel = new Label("X Position: ");
        Label yLabel = new Label("Y Position: ");
        Label zLabel = new Label("Z Position: ");

        TextField xPosition = new TextField();
        TextField yPosition = new TextField();
        TextField zPosition = new TextField();

        // For boxes only
        Label widthLabel = new Label("Width: ");
        Label lengthLabel = new Label("Length: ");

        TextField shapeWidth = new TextField();
        TextField shapeLength = new TextField();

        // For boxes and cylinders only
        Label heightLabel = new Label("Height: ");
        TextField shapeHeight = new TextField();

        // For spheres and cylinders only
        Label radiusLabel = new Label("Radius: ");
        TextField shapeRadius = new TextField();


        // ChoiceBox with list of shapes
        ChoiceBox<String> shapes = new ChoiceBox<>();
        shapes.getItems().addAll("Sphere", "Box", "Cylinder");

        VBox vbox = new VBox(20, label,
                new HBox(20, shapeLabel, shapes),
                new HBox(20, xLabel, xPosition),
                new HBox(20, yLabel, yPosition),
                new HBox(20, zLabel, zPosition),
                addButton);
        vbox.setPadding(new Insets(30));
        vbox.setAlignment(Pos.TOP_CENTER);


        // Shows specific scenes depending on item selected in shape ChoiceBox
        shapes.getSelectionModel().selectedIndexProperty().addListener((source, o, n) -> {
            vbox.getChildren().clear();
            if (n.equals(0)) {
                vbox.getChildren().addAll(label,
                        new HBox(20, shapeLabel, shapes),
                        new HBox(20, xLabel, xPosition),
                        new HBox(20, yLabel, yPosition),
                        new HBox(20, zLabel, zPosition),
                        new HBox(20, radiusLabel, shapeRadius),
                        addButton);
            }
            else if (n.equals(1)) {
                vbox.getChildren().addAll(label,
                        new HBox(20, shapeLabel, shapes),
                        new HBox(20, xLabel, xPosition),
                        new HBox(20, yLabel, yPosition),
                        new HBox(20, zLabel, zPosition),
                        new HBox(20, widthLabel, shapeWidth),
                        new HBox(20, lengthLabel, shapeLength),
                        new HBox(20, heightLabel, shapeHeight),
                        addButton);
            }
            else if (n.equals(2)) {
                vbox.getChildren().addAll(label,
                        new HBox(20, shapeLabel, shapes),
                        new HBox(20, xLabel, xPosition),
                        new HBox(20, yLabel, yPosition),
                        new HBox(20, zLabel, zPosition),
                        new HBox(20, radiusLabel, shapeRadius),
                        new HBox(20, heightLabel, shapeHeight),
                        addButton);
            }
            vbox.setPadding(new Insets(30));
            vbox.setAlignment(Pos.TOP_CENTER);
        });

        // Submit shape details
        addButton.setOnAction(e -> {
            int selectedShape = shapes.getSelectionModel().getSelectedIndex();
            double x = Double.parseDouble(xPosition.getText());
            double y = Double.parseDouble(yPosition.getText());
            double z = Double.parseDouble(zPosition.getText());

            if (selectedShape == 0) {
                radius = Integer.parseInt(shapeRadius.getText());
                Sphere sphere = new Sphere(radius);
                translate(sphere, x, y, z);
            }
            else if (selectedShape == 1) {
                width = Double.parseDouble(shapeWidth.getText());
                depth = Double.parseDouble(shapeLength.getText());
                height = Double.parseDouble(shapeLength.getText());
                Box box = new Box(width, height, depth);
                translate(box, x, y, z);
            }
            else if (selectedShape == 2) {
                radius = Integer.parseInt(shapeRadius.getText());
                height = Integer.parseInt(shapeLength.getText());
                Cylinder cylinder = new Cylinder(radius, height);
                translate(cylinder, x, y, z);
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No Shape Selected");
                alert.setContentText("Please select a shape.");
                alert.show();
            }
        });

        Scene scene = new Scene(vbox, 375, 500);
        stage.setScene(scene);
        stage.setTitle("Add a Shape");
        stage.show();
    }

    private void translate(Shape3D shape, double x, double y, double z) {
        Translate translate = new Translate(x, y, z);
        shape.getTransforms().add(translate);
    }

    private void rotate(Shape3D shape, double angle, Point3D axisOfRotation) {
        Rotate rotate = new Rotate(angle, axisOfRotation);
        shape.getTransforms().add(rotate);
    }

    private void scale(Shape3D shape, double xFactor, double yFactor, double zFactor) {

    }
}
