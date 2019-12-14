/*
    Class: MainStageStageStageStageStageStage
    Contains main method and the primary stage for the application.

    TODO
    menu logic
    button logic
    adding shapes
    controlling shapes
    make things look pretty

*/

package homework4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Point3D;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Material;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.scene.shape.Cylinder;
import javafx.scene.shape.Shape3D;
import javafx.scene.shape.Sphere;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Scale;
import javafx.scene.transform.Translate;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class MainStage extends Application {

    // main layout container for the main stage
    private BorderPane borderPane;

    // control box in the right side for shape transformations inside the subscene.
    // Shape transformations include: Rotate, Translate, Scale, and Change Colour.
    private VBox controlBox;
    private Label controlBoxLabel, rotateXLabel, rotateYLabel, rotateZLabel, translateXLabel, translateYLabel, translateZLabel, scaleLabel, changeBgColorLabel, changeColorLabel;
    private Slider rotateSliderX, rotateSliderY, rotateSliderZ;
    private TextField translateXTextField, translateYTextField, translateZTextField;
    private Button translateButton; // once this is clicked, it gets the values of the text fields // TODO valid input checking or just warn user
    private Slider scaleSlider;
    final ColorPicker colorPicker = new ColorPicker();
    final ColorPicker bgColorPicker = new ColorPicker();

    private Shape3D thisShape; // TODO still not sure what this is for

    // SubScene that has the shapes inside in 3D
    private Pane pane;
    private SubScene subScene;
    private Group shapesGroup;
    private PerspectiveCamera camera;

    // Menu bar that contains "file" menu
    private MenuBar menuBar;

    // "file" menu that contains menu items, "save" and "open"
    private Menu fileMenu;

    private MenuItem saveMenuItem;
    private MenuItem openMenuItem;

    // Button to add the shape into the sub-scene
    private Button addShapeButton;

    // Shape attributes
    private double width, height, depth, radius;
    private boolean selected = false;
    private Shape3D selectedShape; // this is the selected shape
    private Material selectedShapeMaterial; // this is the selecte shape's material


    public static void main(String[] args)
    {
        launch(args);
        MainStage MS = new MainStage();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        /***************** Defining the variables *****************/

        // layout that is the root of everything else
        borderPane = new BorderPane();

        // ShapeEditor and its labels
        controlBox = new VBox(20);
        controlBoxLabel = new Label("Shape Editor");
        rotateXLabel = new Label("Rotate X: ");
        rotateYLabel = new Label("Rotate Y: ");
        rotateZLabel = new Label("Rotate Z: ");
        translateXLabel = new Label("Translate X: ");
        translateYLabel = new Label("Translate Y: ");
        translateZLabel = new Label("Translate Z: ");
        scaleLabel = new Label("Change Scale: ");
        changeColorLabel = new Label("Change Shape Color: ");
        changeBgColorLabel = new Label("Change Background Color: ");

        // Sliders
        rotateSliderX = new Slider(0, 359, 0);
        rotateSliderY = new Slider(0, 359, 0);
        rotateSliderZ = new Slider(0, 359, 0);
        scaleSlider = new Slider(0.5, 2.0, 1.0);

        rotateSliderX.setShowTickMarks(true);
        rotateSliderY.setShowTickMarks(true);
        rotateSliderZ.setShowTickMarks(true);
        scaleSlider.setShowTickMarks(true);

        // Translate TextFields and Button
        translateXTextField = new TextField();
        translateYTextField = new TextField();
        translateZTextField = new TextField();
        translateButton = new Button("Translate");

        controlBox.setStyle("-fx-background-color: lightsteelblue");
        controlBox.setPadding(new Insets(35, 25, 20, 25));

        disableControls(true);

        // sub-scene that contains the shapes (original: 800, 600)
        pane = new Pane();
        shapesGroup = new Group();
        subScene = new SubScene(pane, 700, 500);
        camera = new PerspectiveCamera();
        camera.getTransforms().add(new Translate(0, 0, 0));

        pane.getChildren().add(shapesGroup);
        subScene.setCamera(camera);

        pane.setStyle("-fx-border-style: solid; -fx-border-width: 2px; -fx-border-color: lightgray; -fx-background-color: white");

        bgColorPicker.setOnAction(e -> {
            pane.setStyle("-fx-border-style: solid; -fx-border-width: 2px; -fx-border-color: lightgray; -fx-background-color: #" + colorToHex(bgColorPicker.getValue()));
        });

        // menu bar and its items
        menuBar = new MenuBar();
        fileMenu = new Menu("File");
        saveMenuItem = new MenuItem("Save");
        openMenuItem = new MenuItem("Open");
        
        saveMenuItem.setOnAction(event->{
        	save();
        });
        
        openMenuItem.setOnAction(event->{
        	load(primaryStage);
        });

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
        controlBox.getChildren().addAll(new HBox(40, rotateXLabel, rotateSliderX),
                                        new HBox(40, rotateYLabel, rotateSliderY),
                                        new HBox(40, rotateZLabel, rotateSliderZ),
                                        new HBox(25, translateXLabel, translateXTextField),
                                        new HBox(25, translateYLabel, translateYTextField),
                                        new HBox(25, translateZLabel, translateZTextField),
                                        new HBox(25, translateButton),
                                        new HBox(10, scaleLabel, scaleSlider),
                                        new HBox(5, changeColorLabel, colorPicker),
                                        new HBox(5, changeBgColorLabel, bgColorPicker));

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

        /********** Controls for the Selected Shape Transformations **********/

        // rotate sliders
        rotateSliderX.valueProperty().addListener((observable, oldValue, newValue) -> { // TODO reset the sliders
            if(selectedShape.equals(null))
                return;
            selectedShape.getTransforms().addAll(new Rotate((Double) newValue, Rotate.X_AXIS));
        });
        rotateSliderY.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(selectedShape.equals(null))
                return;
            selectedShape.getTransforms().addAll(new Rotate((Double) newValue, Rotate.Y_AXIS));
        });
        rotateSliderZ.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(selectedShape.equals(null))
                return;
            selectedShape.getTransforms().addAll(new Rotate((Double) newValue, Rotate.Z_AXIS));
        });

        // Translate the XYZ position of the shape
        translateButton.setOnAction(actionEvent -> {
            if(selectedShape.equals(null))
                return;
            selectedShape.setTranslateX(Double.parseDouble(translateXTextField.getText()));
            selectedShape.setTranslateY(Double.parseDouble(translateYTextField.getText()));
            selectedShape.setTranslateZ(Double.parseDouble(translateZTextField.getText()));
        });

        // Change the color of the selected shape
        colorPicker.setOnAction(actionEvent -> {
            if(selectedShape.equals(null))
                return;
            selectedShape.setMaterial(new PhongMaterial(colorPicker.getValue()));
        });

        /********** primary stage set scene **********/
        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Shape Editor");
        primaryStage.show();

    } /********** End of start() Method **********/


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
        Label heightLabel = new Label("Height: ");

        TextField shapeWidth = new TextField();
        TextField shapeHeight = new TextField();

        // For boxes and cylinders only
        Label depthLabel = new Label("Depth: ");
        TextField shapeDepth = new TextField();

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
                        new HBox(20, heightLabel, shapeHeight),
                        new HBox(20, depthLabel, shapeDepth),
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
                radius = Double.parseDouble(shapeRadius.getText());
                Sphere sphere = new Sphere(radius);
                translate(sphere, x, y, z);
                changeColor(sphere);
                pane.getChildren().add(sphere);
                changeProperties(sphere);
                stage.close();
            }
            else if (selectedShape == 1)
            {
                width = Double.parseDouble(shapeWidth.getText());
                height = Double.parseDouble(shapeHeight.getText());
                depth = Double.parseDouble(shapeDepth.getText());
                Box box = new Box(width, height, depth);
                translate(box, x, y, z);
                changeColor(box);
                pane.getChildren().add(box);
                changeProperties(box);
                stage.close();
            }
            else if (selectedShape == 2) {
                radius = Double.parseDouble(shapeRadius.getText());
                height = Double.parseDouble(shapeHeight.getText());
                Cylinder cylinder = new Cylinder(radius, height);
                translate(cylinder, x, y, z);
                changeColor(cylinder);
                pane.getChildren().add(cylinder);
                changeProperties(cylinder);
                stage.close();
            }
            else
            {
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

    private Shape3D changeProperties(Shape3D shape) {

        /********* Select the topmost shape in the pane inside the subscene *********/
        shape.setOnMousePressed(e -> {

            // if this was selected already, unselect it
            if(shape.equals(selectedShape)) {
                shape.setMaterial(selectedShapeMaterial);

                selectedShape = null;
                selectedShapeMaterial = null;

                disableControls(true);
                return;
            }

            // the previous selected shape should be changed back, should it exist
            if(selectedShape != null)
                selectedShape.setMaterial(selectedShapeMaterial);

            // TODO make controls actually change the objects
            // selected = true;
            selectedShape = shape;
            selectedShapeMaterial = shape.getMaterial();
            disableControls(false); // TODO removed variable selected
            shape.setMaterial(new PhongMaterial(Color.LIGHTCYAN));
            thisShape = shape; // TODO not sure what this is for
            System.out.println("FOO");
        });

//        pane.setOnMousePressed(e -> { // TODO this gets clicked as well when clicking a shape,
//                                      // TODO i've changed it so that clicking the previous shape unselects it
//            selected = false;
//            selectedShape.setMaterial(selectedShapeMaterial);
//            enableControls(selected);
//            shape.setMaterial(new PhongMaterial(colorPicker.getValue()));
//            System.out.println("Bar");
//        });
        System.out.println("added shape");
        return thisShape;
    }

    private void disableControls(boolean boo) {
        rotateSliderX.setDisable(boo);
        rotateSliderY.setDisable(boo);
        rotateSliderZ.setDisable(boo);
        scaleSlider.setDisable(boo);
        translateXTextField.setDisable(boo);
        translateYTextField.setDisable(boo);
        translateZTextField.setDisable(boo);
        translateButton.setDisable(boo);
        colorPicker.setDisable(boo);
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
        Scale scale = new Scale(xFactor, yFactor, zFactor);
        shape.getTransforms().add(scale);
    }

    private void changeColor(Shape3D shape) {
        bgColorPicker.setOnAction(e -> {
            shape.setMaterial(new PhongMaterial(colorPicker.getValue()));
        });
    }

    public void save()
    {
        try
        {
            PrintWriter writer = new PrintWriter(new File("SaveFile.txt"));
            writer.append("Hello World");
            writer.close();

        }
        catch(FileNotFoundException FNFE)
        {

        }
    }

    public void load(Stage stage)
    {
        FileChooser FC = null; 
    	try
        {
            FC = new FileChooser();
            File saveFile = FC.showOpenDialog(stage);
            
            
            Scanner reader = new Scanner(saveFile);
            String line;
            while(reader.hasNext())
            {
                line = reader.nextLine();
                System.out.println(line);
            }

        }
        catch(NullPointerException NPE)
        {
        	System.out.println("Load File Aborted");
        } 
    	catch (FileNotFoundException e) 
    	{
    		System.out.println("File Not Found");
		}
    }
    
    public String printShapeData(Shape3D shape)
    {
    	String deliverables = "";
    	deliverables = deliverables.concat(Double.toString(shape.getTranslateX()) + Double.toString(shape.getTranslateY()) + Double.toString(shape.getTranslateZ()) +
    			Double.toString(shape.getScaleX()) + Double.toString(shape.getScaleY()) + Double.toString(shape.getScaleZ()));
    	if(shape instanceof Box)
    	{
    		Box B = (Box)shape;
    		deliverables = deliverables.concat(Double.toString(B.getWidth()) + Double.toString(B.getHeight()) + Double.toString(B.getDepth()));
    	}
    	else if(shape instanceof Sphere)
    	{
    		Sphere S = (Sphere)shape;
    		deliverables = deliverables.concat(Double.toString(S.getRadius()));
    	}
    	else if(shape instanceof Cylinder)
    	{
    		Cylinder C = (Cylinder)shape;
    		deliverables = deliverables.concat(Double.toString(C.getRadius()) + Double.toString(C.getHeight()));
    	}
		return deliverables;
    }

    String colorToHex(Color color) {
        String hex1;
        String hex2;

        hex1 = Integer.toHexString(color.hashCode()).toUpperCase();

        switch (hex1.length()) {
            case 2:
                hex2 = "000000";
                break;
            case 3:
                hex2 = String.format("00000%s", hex1.substring(0,1));
                break;
            case 4:
                hex2 = String.format("0000%s", hex1.substring(0,2));
                break;
            case 5:
                hex2 = String.format("000%s", hex1.substring(0,3));
                break;
            case 6:
                hex2 = String.format("00%s", hex1.substring(0,4));
                break;
            case 7:
                hex2 = String.format("0%s", hex1.substring(0,5));
                break;
            default:
                hex2 = hex1.substring(0, 6);
                break;
        }
        return hex2;
    }
}
