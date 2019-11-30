/*
    Class: MainStage
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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainStage extends Application {

    // main layout container for the main stage
    private BorderPane borderPane;

    // control box in the right side for shape transformations inside the subscene.
    // Shape transformations include: Rotate, Translate, Scale, and Change Colour.
    private VBox controlBox;
    private Label rotateLabel, translateXLabel, translateYLabel, scaleLabel, changeColorLabel;
    private Slider rotateSlider;
    private TextField translateXTextField, translateYTextField;
    private Slider scaleSlider;
    private TextField changeColorTextField;

    // SubScene that has the shapes inside in 3D
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

    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {

        /***************** defining the variables *****************/

        // layout that is the root of everything else
        borderPane = new BorderPane();

        // Control box and its controls and labels
        controlBox = new VBox(20);
        controlBox.setPadding(new Insets( 50, 10, 50,10));
        rotateLabel = new Label("Rotate: ");
        translateXLabel = new Label("Translate X:");
        translateYLabel = new Label("Translate Y:");
        scaleLabel = new Label("Change Scale: ");
        changeColorLabel = new Label("Change Colour: ");
        rotateSlider = new Slider();
        translateXTextField = new TextField();
        translateYTextField = new TextField();
        scaleSlider = new Slider();
        changeColorTextField = new TextField();

        // subscene that contains the shapes
        subScene = new SubScene(new VBox(), subSceneWidth = 800, subSceneHeight = 600); // TODO what is root supposed to be, the first param?

        // menu bar and its items
        menuBar = new MenuBar();
        fileMenu = new Menu("File");
        saveMenuItem = new MenuItem("Save");
        openMenuItem = new MenuItem("Open");

        // button to add shapes into the subscene
        addShapeButton = new Button("Add Shape");
        HBox buttonBox = new HBox(addShapeButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets( 50, 0, 50, 0));

        /***************** Adding Controls to control box *****************/
        controlBox.getChildren().addAll(new HBox(rotateLabel, rotateSlider),
                                        new HBox(translateXLabel, translateXTextField),
                                        new HBox(translateYLabel, translateYTextField),
                                        new HBox(scaleLabel, scaleSlider),
                                        new HBox(changeColorLabel, changeColorTextField) );
        controlBox.setAlignment(Pos.CENTER);

        /***************** Adding menu items where they belong *****************/
        menuBar.getMenus().add(fileMenu);
        fileMenu.getItems().addAll(saveMenuItem,openMenuItem);

        /***************** Adding subscene, button, and control box to the gridpane *****************/
        borderPane.setCenter(subScene);
        borderPane.setBottom(buttonBox);
        borderPane.setRight(controlBox);
        borderPane.setTop(menuBar);

        Scene scene = new Scene(borderPane);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
