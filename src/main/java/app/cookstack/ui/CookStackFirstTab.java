/**
 * CookStack Project
 *
 * Author: Lara Gouda
 * Created: 2025
 * Version: 1.0
 *
 * Credits:
 * - JavaFX framework
 * - Icons & Images: Freepik, Retropi
 *
 * All rights reserved.
 */

package app.cookstack.ui;
import app.cookstack.model.*;
import app.cookstack.utility.*;

import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.DirectoryChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
/**
 * The {@code CookStackWindow} class represents the main entry point of the
 * CookStack application. It displays the home screen with:
 * <ul>
 *   <li>A title and logo</li>
 *   <li>A button to create a new save file</li>
 *   <li>A button to load an existing save file</li>
 *   <li>A credits screen</li>
 * </ul>
 *
 * <p>The window is styled with animations (fade, scale, color pulse)
 * and CSS defined in {@code main.css}. The app uses serialization to
 * save and load {@code CookBook} objects.</p>
 *
 * <p>This class extends {@link Application}, making it runnable
 * as a JavaFX program.</p>
 */
public class CookStackFirstTab extends Application {

    /**
     * The main entry point for all JavaFX applications. The start method is
     * called after the JavaFX runtime is initialized.
     *
     * @param primaryStage the primary window (stage) where the UI will be displayed
     */
    @Override
    public void start(Stage primaryStage) {

        // Adjust window to fill the entire screen
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX(screenBounds.getMinX());
        primaryStage.setY(screenBounds.getMinY());
        primaryStage.setWidth(screenBounds.getWidth());
        primaryStage.setHeight(screenBounds.getHeight());

        // Title text with gradient and shadow effects
        Text title = new Text("CookStack");
        double titleFontSize = screenBounds.getHeight() * 0.10;
        title.setFont(Font.font("Segoe UI Semibold", FontWeight.BOLD, titleFontSize));
        title.setStyle(
                "-fx-fill: linear-gradient(to bottom, #f7c46c, #e6a74f);" +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 4, 0.5, 2, 2);"
        );

        // Logo image
        ImageView logoView = new ImageView(new Image(getClass().getResource("/images/logo.png").toExternalForm()));
        logoView.setPreserveRatio(true);
        logoView.setFitWidth(screenBounds.getWidth() * 0.3);
        logoView.setOpacity(0.7);

        /**
         * -------------------
         * New Save File Button
         * -------------------
         */
        Button newFileButton = new Button("New Save File");
        newFileButton.getStyleClass().add("main-button");
        newFileButton.setOnAction(e -> {
            DirectoryChooser dirChooser = new DirectoryChooser();
            dirChooser.setTitle("Select Folder to Save File");
            File selectedDir = dirChooser.showDialog(primaryStage);

            if (selectedDir != null) {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("New File");
                dialog.setHeaderText("Enter name for new file:");
                dialog.setContentText("Your name:");

                // Custom logo for dialog
                Image customImage = new Image(getClass().getResource("/images/logo.png").toExternalForm());
                ImageView imageView = new ImageView(customImage);
                imageView.setFitHeight(75);
                imageView.setPreserveRatio(true);
                imageView.setOpacity(0.5);
                dialog.setGraphic(imageView);

                // Apply CSS styles
                DialogPane dialogPane = dialog.getDialogPane();
                dialogPane.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
                dialogPane.getStyleClass().add("custom-dialog");

                // Style dialog buttons
                dialogPane.lookupButton(ButtonType.OK).getStyleClass().add("main-button");
                dialogPane.lookupButton(ButtonType.CANCEL).getStyleClass().add("main-button");

                // Disable OK until text is entered
                Node okButton = dialog.getDialogPane().lookupButton(ButtonType.OK);
                TextField textField = dialog.getEditor();
                okButton.setDisable(true);

                // Process result if user entered a filename
                textField.textProperty().addListener((obs, oldValue, newValue) -> {
                    okButton.setDisable(newValue.trim().isEmpty());
                });

                Optional<String> result = dialog.showAndWait();
                result.ifPresent(fileName -> {
                    fileName = fileName.trim();

                    try {
                        Path newFilePath = selectedDir.toPath().resolve(fileName + ".ser");

                        if (!Files.exists(newFilePath)) {
                            Files.createFile(newFilePath);
                        }
                        RecipeBook newCookBook = new RecipeBook(fileName);
                        saveRecipeBookToFile(newCookBook, newFilePath.toFile());
                        CookStackSecondTab mainWindow = new CookStackSecondTab(newFilePath.toFile(), newCookBook);
                        mainWindow.start(new Stage());
                        primaryStage.close();

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });
            }
        });

        /**
         * -------------------
         * Load Save File Button
         * -------------------
         */
        Button loadFileButton = new Button("Load Save File");
        loadFileButton.getStyleClass().add("main-button");
        loadFileButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Previous Save File");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Save Files", "*.ser")
            );
            File file = fileChooser.showOpenDialog(primaryStage);
            if (file != null) {
                try {
                    RecipeBook loadedCookBook = loadRecipeBookFromFile(file);
                    CookStackSecondTab mainWindow = new CookStackSecondTab(file, loadedCookBook);
                    mainWindow.start(new Stage());
                    primaryStage.close();

                } catch (IOException | ClassNotFoundException ex) {
                    ex.printStackTrace();
                }
            }
        });

        /**
         * -------------------
         * Credits Button
         * -------------------
         */
        Button creditsButton = new Button("Credits");
        creditsButton.getStyleClass().add("main-button");
        creditsButton.setOnAction(e -> {
            Stage creditsStage = new Stage();
            creditsStage.setTitle("Credits");

            // Header strip
            Rectangle headerStrip = new Rectangle(350, 50);
            headerStrip.setFill(Color.web("#b08d6c"));
            headerStrip.setArcWidth(15);
            Text headerText = new Text("Credits");
            headerText.setFill(Color.WHITE);
            headerText.setFont(Font.font("Arial", FontWeight.BOLD, 18));
            StackPane header = new StackPane(headerStrip, headerText);
            header.setAlignment(Pos.CENTER);

            // Credits content
            Text logoSection = new Text("Logo & Icons:");
            logoSection.getStyleClass().add("credits-section");
            logoSection.setUnderline(true);

            Text chefHat = new Text("• Chef Hat Art: Freepik");
            Text pancake = new Text("• Pancake Art: Retropi");

            Text codeSection = new Text("Development:");
            codeSection.getStyleClass().add("credits-section");
            codeSection.setUnderline(true);
            Text myName = new Text("• Code written by Lara Gouda");

            Button closeBtn = new Button("Close");
            closeBtn.getStyleClass().add("close-button");
            closeBtn.setOnAction(ev -> creditsStage.close());

            VBox creditsRoot = new VBox(20);
            creditsRoot.setAlignment(Pos.TOP_CENTER);
            creditsRoot.setPadding(new Insets(0));
            creditsRoot.getChildren().addAll(
                    header,
                    logoSection, chefHat, pancake,
                    codeSection, myName,
                    closeBtn
            );

            creditsRoot.setStyle("-fx-background-color: #fff2e0;");
            header.getStyleClass().add("credits-header");

            Scene creditsScene = new Scene(creditsRoot, 350, 310);
            creditsScene.getStylesheets().add(
                    getClass().getResource("/styles/main.css").toExternalForm()
            );

            // Fade-in animation for credits window
            FadeTransition fadeIn = new FadeTransition(Duration.millis(500), creditsRoot);
            fadeIn.setFromValue(0);
            fadeIn.setToValue(1);
            fadeIn.play();

            creditsStage.setScene(creditsScene);
            creditsStage.show();
        });

        // Style and animate main buttons
        Button[] buttons = new Button[]{newFileButton, loadFileButton, creditsButton};
        double btnFontSize = screenBounds.getHeight() * 0.03;
        double btnWidth = screenBounds.getWidth() * 0.27;

        for (Button btn : buttons) {
            btn.setStyle("-fx-font-size: " + btnFontSize + "px;");
            btn.getStyleClass().add("main-button");
            btn.setPrefWidth(btnWidth);
            btn.setAlignment(Pos.CENTER);
        }

        // Logo animations: fade, scale up, breathing effect
        FadeTransition logoFadeIn = new FadeTransition(Duration.millis(1200), logoView);
        logoFadeIn.setFromValue(0);
        logoFadeIn.setToValue(0.7);

        ScaleTransition logoScaleUp = new ScaleTransition(Duration.millis(1200), logoView);
        logoScaleUp.setFromX(0.8);
        logoScaleUp.setFromY(0.8);
        logoScaleUp.setToX(1);
        logoScaleUp.setToY(1);

        logoFadeIn.play();
        logoScaleUp.play();

        ScaleTransition logoBreath = new ScaleTransition(Duration.seconds(3), logoView);
        logoBreath.setFromX(1);
        logoBreath.setFromY(1);
        logoBreath.setToX(1.05);
        logoBreath.setToY(1.05);
        logoBreath.setAutoReverse(true);
        logoBreath.setCycleCount(ScaleTransition.INDEFINITE);
        logoBreath.play();

        // Title fade-in and color pulse
        title.setOpacity(0);
        FadeTransition titleFade = new FadeTransition(Duration.millis(1200), title);
        titleFade.setFromValue(0);
        titleFade.setToValue(1);
        titleFade.play();

        Timeline colorPulse = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(title.fillProperty(), Color.web("#f7c46c"))),
                new KeyFrame(Duration.seconds(2), new KeyValue(title.fillProperty(), Color.web("#e6a74f")))
        );
        colorPulse.setAutoReverse(true);
        colorPulse.setCycleCount(Timeline.INDEFINITE);
        colorPulse.play();

        // Animate buttons appearing with delay and hover scaling
        double delay = 0;
        for (Button btn : buttons) {
            btn.setOpacity(0);
            FadeTransition btnFade = new FadeTransition(Duration.millis(800), btn);
            btnFade.setFromValue(0);
            btnFade.setToValue(1);
            btnFade.setDelay(Duration.millis(delay));
            btnFade.play();
            delay += 200;

            btn.setOnMouseEntered(e -> {
                btn.setScaleX(1.05);
                btn.setScaleY(1.05);
            });
            btn.setOnMouseExited(e -> {
                btn.setScaleX(1);
                btn.setScaleY(1);
            });
        }

        // Layout arrangement: logo + buttons
        VBox rightVBox = new VBox(screenBounds.getHeight() * 0.03, title, newFileButton, loadFileButton, creditsButton); // spacing scales
        rightVBox.setAlignment(Pos.CENTER);
        rightVBox.setPadding(new Insets(screenBounds.getHeight() * 0.02, 0,
                screenBounds.getHeight() * 0.02, 0));


        HBox mainLayout = new HBox(20, logoView, rightVBox);
        mainLayout.setAlignment(Pos.CENTER);
        mainLayout.getStyleClass().add("main-layout");
        mainLayout.setPadding(new Insets(50, 0, 50, -10));

        // Slight zoom-out effect
        double zoomFactor = 0.80;
        mainLayout.setScaleX(zoomFactor);
        mainLayout.setScaleY(zoomFactor);

        // Background wrapper
        StackPane wrapper = new StackPane();
        wrapper.setStyle("-fx-background-color: linear-gradient(to bottom, #fff2e0, #ffe8cc);");
        wrapper.getChildren().add(mainLayout);
        StackPane.setAlignment(mainLayout, Pos.CENTER);

        // Final scene setup
        Scene scene = new Scene(wrapper, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.png")));
        primaryStage.setScene(scene);
        primaryStage.setTitle("CookStack");
        primaryStage.show();
    }

    /**
     * Saves a {@link RecipeBook} object to a file using serialization.
     *
     * @param cookbook the {@code CookBook} instance to save
     * @param file     the target file
     * @throws IOException if an I/O error occurs during writing
     */
    private void saveRecipeBookToFile(RecipeBook cookbook, File file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(cookbook);
        }
    }

    /**
     * Loads a {@link RecipeBook} object from a file using deserialization.
     *
     * @param file the file containing the serialized {@code CookBook}
     * @return the deserialized {@code CookBook} instance
     * @throws IOException            if an I/O error occurs during reading
     * @throws ClassNotFoundException if the class of the serialized object is not found
     */
    private RecipeBook loadRecipeBookFromFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            return (RecipeBook) ois.readObject();
        }
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args command-line arguments passed to the program
     */
    public static void main(String[] args) {
        launch(args);
    }
}

