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

import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;


/**
 * The {@code RecipeViewer} class provides a read-only window
 * to display details of a {@link Recipe}. It prevents user editing
 * and is designed purely for viewing stored recipe information.
 * <p>
 * The window displays:
 * <ul>
 *     <li>Recipe name</li>
 *     <li>Category (Breakfast, Lunch, Dinner, or N/A)</li>
 *     <li>Time required (minutes)</li>
 *     <li>Serving size</li>
 *     <li>Calories</li>
 *     <li>Protein</li>
 *     <li>List of ingredients</li>
 *     <li>List of preparation steps</li>
 * </ul>
 * A close button is included to dismiss the window.
 * </p>
 *
 * <h2>UI Characteristics</h2>
 * <ul>
 *     <li>All {@code TextField}s are non-editable.</li>
 *     <li>{@code ComboBox} category selection is disabled to prevent modification.</li>
 *     <li>ListViews of ingredients and steps are non-interactive.</li>
 *     <li>Layout uses a combination of VBox, HBox, and GridPane with padding and alignment set for readability.</li>
 *     <li>Custom CSS styling is applied via {@code main.css}.</li>
 *     <li>The stage is maximized to fill the user’s screen.</li>
 * </ul>
 *
 * Example usage:
 * <pre>{@code
 * Recipe recipe = new Recipe("Pancakes", "Breakfast", 20, 4, 350, 12,
 *                            Arrays.asList(new Ingredient("Flour", "2", "cups")),
 *                            Arrays.asList("Mix ingredients", "Cook on pan"));
 * RecipeViewer viewer = new RecipeViewer(recipe);
 * viewer.show(primaryStage);
 * }</pre>
 */
public class RecipeViewerTab {
    private final Recipe recipe; // The recipe being displayed.

    /**
     * Constructs a new {@code RecipeViewer}.
     *
     * @param recipe the {@link Recipe} to display
     */
    public RecipeViewerTab(Recipe recipe) {
        this.recipe = recipe;
    }

    /**
     * Displays the recipe in a read-only JavaFX window.
     * <p>
     * The window includes recipe metadata (name, category, time,
     * servings, calories, protein) along with two scrollable
     * lists for ingredients and steps. A close button allows
     * the user to dismiss the stage.
     * </p>
     *
     * @param parentStage the parent {@link Stage} (not modified, but provided for context)
     */
    public void show(Stage parentStage) {
        Stage stage = new Stage();
        stage.setTitle("View Recipe");

        // Recipe Name
        TextField recipeNameField = new TextField(recipe.getRecipeName());
        recipeNameField.setEditable(false);

        // Category dropdown (disabled for editing)
        ComboBox<String> categoryDropDown = new ComboBox<>();
        categoryDropDown.setItems(FXCollections.observableArrayList("N/A", "Breakfast", "Lunch", "Dinner"));
        categoryDropDown.getSelectionModel().select(recipe.getRecipeCategory() != null ? recipe.getRecipeCategory() : "N/A");
        categoryDropDown.addEventFilter(javafx.scene.input.MouseEvent.ANY, javafx.event.Event::consume);
        categoryDropDown.addEventFilter(javafx.scene.input.KeyEvent.ANY, javafx.event.Event::consume);

        // Recipe details (non-editable fields)
        TextField recipeTimeTaken = new TextField(String.valueOf(recipe.getTimeTaken()));
        recipeTimeTaken.setEditable(false);

        TextField recipeServingSize = new TextField(String.valueOf(recipe.getServingSize()));
        recipeServingSize.setEditable(false);

        TextField recipeCalories = new TextField(String.valueOf(recipe.getRecipeCalories()));
        recipeCalories.setEditable(false);

        TextField recipeProtein = new TextField(String.valueOf(recipe.getRecipeProtein()));
        recipeProtein.setEditable(false);

        // Layout sections
        Insets padding = new Insets(15);

        VBox nameVBox = new VBox(5, new Label("Recipe Name:"), recipeNameField);
        nameVBox.setAlignment(Pos.CENTER);
        HBox nameBox = new HBox(10, nameVBox);
        nameBox.setAlignment(Pos.TOP_CENTER);
        nameBox.setPadding(padding);

        HBox timeCatSizeBox = new HBox(80);
        VBox timeBox = new VBox(5, new Label("Recipe Time:"), recipeTimeTaken);
        VBox categoryBox = new VBox(5, new Label("Recipe Category:"), categoryDropDown);
        VBox sizeBox = new VBox(5, new Label("Recipe Serving Size:"), recipeServingSize);
        timeBox.setAlignment(Pos.CENTER);
        categoryBox.setAlignment(Pos.CENTER);
        sizeBox.setAlignment(Pos.CENTER);
        timeCatSizeBox.getChildren().addAll(timeBox, categoryBox, sizeBox);
        timeCatSizeBox.setAlignment(Pos.CENTER);
        timeCatSizeBox.setPadding(padding);

        VBox caloriesBox = new VBox(5, new Label("Recipe Calories:"), recipeCalories);
        VBox proteinBox = new VBox(5, new Label("Recipe Protein:"), recipeProtein);
        caloriesBox.setAlignment(Pos.CENTER);
        proteinBox.setAlignment(Pos.CENTER);

        HBox calProBox = new HBox(40, caloriesBox, proteinBox);
        calProBox.setAlignment(Pos.CENTER);
        calProBox.setPadding(padding);

        // Ingredients section
        ListView<Ingredient> ingredientsListView = new ListView<>(FXCollections.observableArrayList(recipe.getRecipeIngredients()));
        ingredientsListView.setMouseTransparent(true);
        ingredientsListView.setFocusTraversable(false);

        Label ingredientsLabel = new Label("Ingredients:");
        ingredientsLabel.setStyle("-fx-background-color: #f4d9c6; -fx-padding: 6 10; -fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 16px;");

        VBox ingredientsSection = new VBox(10, ingredientsLabel, ingredientsListView);
        ingredientsSection.setAlignment(Pos.TOP_CENTER);
        ingredientsSection.setPadding(new Insets(10));
        ingredientsSection.setPrefHeight(300);
        ingredientsListView.setPrefHeight(200);

        // Steps section
        ListView<String> stepsListView = new ListView<>(FXCollections.observableArrayList(recipe.getRecipeSteps()));
        stepsListView.setMouseTransparent(true);
        stepsListView.setFocusTraversable(false);

        Label stepsLabel = new Label("Steps:");
        stepsLabel.setStyle("-fx-background-color: #f4d9c6; -fx-padding: 6 10; -fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 16px;");

        VBox stepsSection = new VBox(10, stepsLabel, stepsListView);
        stepsSection.setAlignment(Pos.TOP_CENTER);
        stepsSection.setPadding(new Insets(10));
        stepsSection.setPrefHeight(300);
        stepsListView.setPrefHeight(200);

        // Arrange ingredients & steps side by side
        GridPane ingredientStepGrid = new GridPane();
        ingredientStepGrid.setHgap(20);
        ingredientStepGrid.setPadding(new Insets(10));
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50);
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50);
        ingredientStepGrid.getColumnConstraints().addAll(col1, col2);
        ingredientStepGrid.add(ingredientsSection, 0, 0);
        ingredientStepGrid.add(stepsSection, 1, 0);

        // Close button
        Button closeButton = new Button("Close");
        closeButton.getStyleClass().add("main-button");
        closeButton.setOnAction(e -> stage.close());

        // Main layout
        VBox layout = new VBox(20, nameBox, timeCatSizeBox, calProBox, ingredientStepGrid, closeButton);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(15));
        layout.setSpacing(25);
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #f7e9dc, #fff3e6); -fx-border-color: #d4b483; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 20;");

        // Scene setup
        Scene scene = new Scene(layout, 1030, 1030);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        stage.setScene(scene);

        // Maximize to screen
        Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        // Show window
        stage.show();
        layout.requestFocus();
    }
}
