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
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.ArrayList;


/**
 * The {@code RecipeEditor} class provides a JavaFX-based editor window
 * that allows users to update the details of an existing {@link Recipe}.
 * <p>
 * This editor supports editing:
 * <ul>
 *     <li>Recipe metadata (name, category, cooking time, serving size)</li>
 *     <li>Nutritional information (calories, protein)</li>
 *     <li>Recipe ingredients (with add/remove support)</li>
 *     <li>Cooking steps (with add/remove support)</li>
 * </ul>
 * Once the recipe is modified and saved, an optional callback function
 * can be triggered to refresh the calling UI.
 */
public class RecipeEditorTab {
    private Recipe recipe;
    private Runnable onSaveCallback;

    /**
     * Creates a new RecipeEditor instance for the given recipe.
     *
     * @param recipe the {@link Recipe} object to edit
     * @param onSaveCallback a {@link Runnable} to run after saving changes,
     *                       can be {@code null}
     */
    public RecipeEditorTab(Recipe recipe, Runnable onSaveCallback) {
        this.recipe = recipe; // The recipe being edited.
        this.onSaveCallback = onSaveCallback;  // A callback executed when the recipe is successfully saved.
    }

    /**
     * Builds and displays the recipe editor window.
     *
     * @param parentStage the parent {@link Stage} that opened this editor
     * @return the editor {@link Stage} instance
     */
    public Stage show(Stage parentStage) {
        Stage stage = new Stage();
        stage.setTitle("Edit Recipe");

        /*
         * ========== Recipe Metadata ==========
         * Text fields for name, category, cooking time, serving size,
         * calories, and protein. These are pre-filled with the current
         * recipe values for editing.
         */
        TextField recipeNameField = new TextField(recipe.getRecipeName());
        recipeNameField.setPromptText("Recipe Name");
        ComboBox<String> categoryDropDown = new ComboBox<>();
        categoryDropDown.setItems(FXCollections.observableArrayList("N/A", "Breakfast", "Lunch", "Dinner"));
        categoryDropDown.getSelectionModel().select(recipe.getRecipeCategory() != null ? recipe.getRecipeCategory() : "N/A");
        TextField recipeTimeTaken = new TextField(String.valueOf(recipe.getTimeTaken()));
        recipeTimeTaken.setPromptText("Recipe Time (in minutes)");
        TextField recipeServingSize = new TextField(String.valueOf(recipe.getServingSize()));
        recipeServingSize.setPromptText("Recipe Serving Size");
        TextField recipeCalories = new TextField(String.valueOf(recipe.getRecipeCalories()));
        recipeCalories.setPromptText("Recipe Calories");
        TextField recipeProtein = new TextField(String.valueOf(recipe.getRecipeProtein()));
        recipeProtein.setPromptText("Recipe Protein");

        Insets padding = new Insets(15);

        // UI layout for metadata (name, time, category, size, calories, protein)
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

        /*
         * ========== Ingredients Section ==========
         * Input fields for ingredient name, quantity, and unit,
         * with add/remove buttons and a list view.
         */
        TextField ingredientNameField = new TextField();
        ingredientNameField.setPromptText("Name");
        TextField ingredientQuantityField = new TextField();
        ingredientQuantityField.setPromptText("Quantity");
        TextField ingredientUnitField = new TextField();
        ingredientUnitField.setPromptText("Unit");
        Button addIngredientBtn = new Button("Add");
        Button removeIngredientBtn = new Button("Remove");

        ingredientNameField.setPrefWidth(120);
        ingredientQuantityField.setPrefWidth(100);
        ingredientUnitField.setPrefWidth(60);
        addIngredientBtn.setPrefWidth(70);
        removeIngredientBtn.setPrefWidth(140);

        HBox.setHgrow(ingredientNameField, Priority.ALWAYS);
        HBox.setHgrow(ingredientQuantityField, Priority.ALWAYS);
        HBox.setHgrow(ingredientUnitField, Priority.ALWAYS);

        ListView<Ingredient> ingredientsListView = new ListView<>(FXCollections.observableArrayList(recipe.getRecipeIngredients()));

        // Add ingredient action
        addIngredientBtn.setOnAction(e -> {
            String name = ingredientNameField.getText().trim();
            String quantity = ingredientQuantityField.getText().trim();
            String unit = ingredientUnitField.getText().trim();

            if (name.isEmpty() || quantity.isEmpty() || unit.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please fill all ingredient fields!").showAndWait();
                return;
            }
            ingredientsListView.getItems().add(new Ingredient(name, quantity, unit));
            ingredientNameField.clear();
            ingredientQuantityField.clear();
            ingredientUnitField.clear();
        });

        // Remove ingredient action
        removeIngredientBtn.setOnAction(e -> {
            int selectedIndex = ingredientsListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                ingredientsListView.getItems().remove(selectedIndex);
            }
        });

        /*
         * ========== Steps Section ==========
         * Input field for cooking steps, add/remove buttons, and a list view.
         */
        TextField stepInput = new TextField();
        stepInput.setPromptText("Enter a cooking step");
        Button addStepBtn = new Button("Add");
        ListView<String> stepsListView = new ListView<>(FXCollections.observableArrayList(recipe.getRecipeSteps()));
        Button removeStepBtn = new Button("Remove");

        addStepBtn.setOnAction(e -> {
            String step = stepInput.getText().trim();
            if (!step.isEmpty()) {
                stepsListView.getItems().add(step);
                stepInput.clear();
            }
        });

        removeStepBtn.setOnAction(e -> {
            int selectedIndex = stepsListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                stepsListView.getItems().remove(selectedIndex);
            }
        });

        stepInput.setPrefWidth(285);

        Label ingredientsLabel = new Label("Ingredients:");
        Label stepsLabel = new Label("Steps:");
        ingredientsLabel.setStyle("-fx-background-color: #f4d9c6; -fx-padding: 6 10; -fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 16px;");
        stepsLabel.setStyle("-fx-background-color: #f4d9c6; -fx-padding: 6 10; -fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 16px;");

        HBox ingredientControls = new HBox(10, ingredientNameField, ingredientQuantityField, ingredientUnitField, addIngredientBtn, removeIngredientBtn);
        ingredientControls.setAlignment(Pos.CENTER);

        VBox ingredientsSection = new VBox(10, ingredientsLabel, ingredientControls, ingredientsListView);
        ingredientsSection.setAlignment(Pos.TOP_CENTER);
        ingredientsSection.setPadding(new Insets(10));
        ingredientsSection.setPrefHeight(300);
        ingredientsListView.setPrefHeight(200);

        HBox stepControls = new HBox(10, stepInput, addStepBtn, removeStepBtn);
        stepControls.setAlignment(Pos.CENTER);

        VBox stepsSection = new VBox(10, stepsLabel, stepControls, stepsListView);
        stepsSection.setAlignment(Pos.TOP_CENTER);
        stepsSection.setPadding(new Insets(10));
        stepsSection.setPrefHeight(300);
        stepsListView.setPrefHeight(200);

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

        addIngredientBtn.prefHeightProperty().bind(ingredientNameField.heightProperty());
        removeIngredientBtn.prefHeightProperty().bind(ingredientNameField.heightProperty());
        addIngredientBtn.prefWidthProperty().bind(removeIngredientBtn.widthProperty());
        removeIngredientBtn.prefWidthProperty().bind(addIngredientBtn.widthProperty());
        addIngredientBtn.setMinWidth(80);
        removeIngredientBtn.setMinWidth(80);

        addStepBtn.prefHeightProperty().bind(stepInput.heightProperty());
        removeStepBtn.prefHeightProperty().bind(stepInput.heightProperty());
        addStepBtn.prefWidthProperty().bind(removeStepBtn.widthProperty());
        removeStepBtn.prefWidthProperty().bind(addStepBtn.widthProperty());
        addStepBtn.setMinWidth(80);
        removeStepBtn.setMinWidth(80);

        /*
         * ========== Save Button ==========
         * Updates the recipe with new values and closes the editor.
         * Performs validation for required fields and numeric inputs.
         */
        Button saveButton = new Button("Save Changes");
        saveButton.setOnAction(e -> {
            String name = recipeNameField.getText().trim();
            String category = categoryDropDown.getValue();
            String time = recipeTimeTaken.getText().trim();
            String servingSize = recipeServingSize.getText().trim();
            String calories = recipeCalories.getText().trim();
            String protein = recipeProtein.getText().trim();

            // Validation checks
            if (ingredientsListView.getItems().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please add at least one ingredient!").showAndWait();
                return;
            }
            if (stepsListView.getItems().isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Please add at least one cooking step!").showAndWait();
                return;
            }
            if (name.isEmpty() || time.isEmpty() || servingSize.isEmpty() || calories.isEmpty() || protein.isEmpty()) {
                new Alert(Alert.AlertType.WARNING, "Fields cannot be empty!").showAndWait();
                return;
            }

            if (!RecipeAdderTab.intChecker(time) || !RecipeAdderTab.intChecker(calories) || !RecipeAdderTab.intChecker(protein) || !RecipeAdderTab.intChecker(servingSize)) {
                new Alert(Alert.AlertType.WARNING, "Please enter numbers only for time, serving size, calories and protein.").showAndWait();
                return;
            }

            try {
                // Update recipe with user input
                recipe.setRecipeName(name);
                recipe.setRecipeCategory(category);
                recipe.setTimeTaken(Integer.parseInt(time));
                recipe.setServingSize(Integer.parseInt(servingSize));
                recipe.setRecipeCalories(Integer.parseInt(calories));
                recipe.setRecipeProtein(Integer.parseInt(protein));
                recipe.setRecipeIngredients(new ArrayList<>(ingredientsListView.getItems()));
                recipe.setRecipeSteps(new ArrayList<>(stepsListView.getItems()));

                if (onSaveCallback != null) onSaveCallback.run();
                stage.close();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Time, Serving Size, Calories, and Protein must be numbers!").showAndWait();
            }
        });

        String addRemoveBtnStyle = "-fx-background-color: #f4d9c6; -fx-text-fill: #3e2a1f; "
                + "-fx-font-weight: bold; -fx-background-radius: 8;";

        addIngredientBtn.setStyle(addRemoveBtnStyle);
        removeIngredientBtn.setStyle(addRemoveBtnStyle);

        addStepBtn.setStyle(addRemoveBtnStyle);
        removeStepBtn.setStyle(addRemoveBtnStyle);

        stepInput.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(stepInput, Priority.ALWAYS);

        stepControls.setAlignment(Pos.CENTER);
        stepInput.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(stepInput, Priority.ALWAYS);

        saveButton.getStyleClass().add("main-button"); // same CSS class
        saveButton.setStyle("-fx-font-size: 18px; -fx-background-radius: 12; -fx-padding: 12 25;");

        addIngredientBtn.getStyleClass().add("add-remove-button");
        removeIngredientBtn.getStyleClass().add("add-remove-button");
        addStepBtn.getStyleClass().add("add-remove-button");
        removeStepBtn.getStyleClass().add("add-remove-button");

        saveButton.setStyle("-fx-font-size: 18px; -fx-background-radius: 12; -fx-padding: 12 25;");

        /*
         * ========== Layout and Scene ==========
         * Assembles the input sections and buttons into a styled VBox layout.
         */
        VBox layout = new VBox(20, nameBox, timeCatSizeBox, calProBox, ingredientStepGrid, saveButton);
        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #f7e9dc, #fff3e6); -fx-border-color: #d4b483; -fx-border-radius: 12; -fx-background-radius: 12; -fx-padding: 20;");
        layout.setSpacing(25);
        timeCatSizeBox.setSpacing(50);
        calProBox.setSpacing(40);
        ingredientControls.setSpacing(15);
        stepControls.setSpacing(15);

        Scene scene = new Scene(layout, 1030, 1030);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        stage.setScene(scene);

        // Make stage fullscreen
        Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        stage.show();
        layout.requestFocus();
        return stage;
    }
}


