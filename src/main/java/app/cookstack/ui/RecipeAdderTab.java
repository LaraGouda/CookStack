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
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;


/**
 * The {@code RecipeAdder} class is responsible for displaying a form-based
 * user interface that allows users to create and add a new {@link Recipe}.
 * <p>
 * It includes inputs for:
 * <ul>
 *     <li>Recipe name, category, and cooking time</li>
 *     <li>Serving size, calories, and protein content</li>
 *     <li>A list of ingredients (name, quantity, unit)</li>
 *     <li>A list of cooking steps (instructions)</li>
 * </ul>
 * Once validated, the recipe is saved into a provided {@link ObservableList}
 * of recipes. An optional {@code onSaveCallback} can also be run when the
 * recipe is successfully saved.
 */
public class RecipeAdderTab {
    private ObservableList<Recipe> recipes; // The list of recipes where new recipes will be stored.
    private Runnable onSaveCallback; // Optional callback that runs after successfully saving a recipe.

    /**
     * Constructs a RecipeAdder with a list of recipes and an optional callback.
     *
     * @param recipes        the list where the new recipe will be added
     * @param onSaveCallback callback to execute after saving (can be {@code null})
     */
    public RecipeAdderTab(ObservableList<Recipe> recipes, Runnable onSaveCallback) {
        this.recipes = recipes;
        this.onSaveCallback = onSaveCallback;
    }

    /**
     * Constructs a RecipeAdder without a callback.
     *
     * @param recipes the list where the new recipe will be added
     */
    public RecipeAdderTab(ObservableList<Recipe> recipes) {
        this.recipes = recipes;
    }

    /**
     * Utility method to check if a string contains only digits.
     *
     * @param input the string to check
     * @return {@code true} if the string is a non-empty sequence of digits, {@code false} otherwise
     */
    public static boolean intChecker(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        for (int i = 0; i < input.length(); i++) {
            if (!Character.isDigit(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Displays a new window (stage) for creating a recipe.
     * <p>
     * The window includes:
     * <ul>
     *     <li>Text fields for recipe metadata (name, time, serving size, calories, protein)</li>
     *     <li>A category dropdown (Breakfast, Lunch, Dinner, etc.)</li>
     *     <li>Ingredient management (add/remove ingredient rows)</li>
     *     <li>Step management (add/remove cooking steps)</li>
     *     <li>A save button to validate input and add the recipe</li>
     * </ul>
     *
     * @param parentStage the parent stage (used mainly for positioning)
     */
    public void show(Stage parentStage) {
        Stage stage = new Stage();
        stage.setTitle("Add New Recipe");

        // --- Recipe Basic Information ---
        TextField recipeNameField = new TextField();
        recipeNameField.setPromptText("Recipe Name");

        // Dropdown for categories
        ComboBox<String> categoryDropDown = new ComboBox<String>();
        ObservableList<String> items = FXCollections.observableArrayList(
                "N/A", "Breakfast", "Lunch", "Dinner"
        );

        categoryDropDown.setItems(items);
        categoryDropDown.getSelectionModel().selectFirst();

        TextField recipeTimeTaken = new TextField();
        recipeTimeTaken.setPromptText("Recipe Time (in minutes)");
        TextField recipeServingSize = new TextField();
        recipeServingSize.setPromptText("Recipe Serving Size");
        TextField recipeCalories = new TextField();
        recipeCalories.setPromptText("Recipe Calories");
        TextField recipeProtein = new TextField();
        recipeProtein.setPromptText("Recipe Protein");
        Insets padding = new Insets(15);

        // Layout for recipe name
        VBox nameVBox = new VBox(5, new Label("Recipe Name:"), recipeNameField);
        nameVBox.setAlignment(Pos.CENTER);
        HBox nameBox = new HBox(10, nameVBox);
        nameBox.setAlignment(Pos.TOP_CENTER);
        nameBox.setPadding(padding);

        // Track user category selection
        ArrayList<String> userCategory = new ArrayList<>();
        categoryDropDown.valueProperty().addListener((obs, oldValue, newValue) -> {
            System.out.println("Selected: " + newValue);
            userCategory.add(newValue);
        });

        // Layout for time, category, and serving size
        HBox timeCatSizeBox = new HBox(80);
        VBox timeBox = new VBox(5, new Label("Recipe Time:"), recipeTimeTaken);
        timeBox.setAlignment(Pos.CENTER);
        VBox categoryBox = new VBox(5, new Label("Recipe Category:"), categoryDropDown);
        categoryBox.setAlignment(Pos.CENTER);
        VBox sizeBox = new VBox(5, new Label("Recipe Serving Size:"), recipeServingSize);
        sizeBox.setAlignment(Pos.CENTER);
        timeCatSizeBox.getChildren().addAll(timeBox, categoryBox, sizeBox);
        timeCatSizeBox.setAlignment(Pos.CENTER);
        timeCatSizeBox.setPadding(padding);

        // Layout for calories and protein
        VBox caloriesBox = new VBox(5, new Label("Recipe Calories:"), recipeCalories);
        caloriesBox.setAlignment(Pos.CENTER);
        VBox proteinBox = new VBox(5, new Label("Recipe Protein:"), recipeProtein);
        proteinBox.setAlignment(Pos.CENTER);
        HBox calProBox = new HBox(40, caloriesBox, proteinBox);
        calProBox.setAlignment(Pos.CENTER);
        calProBox.setPadding(padding);

        // --- Ingredient Section ---
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

        ingredientNameField.setMaxWidth(Double.MAX_VALUE);
        ingredientQuantityField.setMaxWidth(Double.MAX_VALUE);
        ingredientUnitField.setMaxWidth(Double.MAX_VALUE);

        HBox.setHgrow(ingredientNameField, Priority.ALWAYS);
        HBox.setHgrow(ingredientQuantityField, Priority.ALWAYS);
        HBox.setHgrow(ingredientUnitField, Priority.ALWAYS);

        ListView<Ingredient> ingredientsListView = new ListView<>();

        // Ingredient controls
        HBox ingredientInputBox = new HBox(10,
                ingredientNameField,
                ingredientQuantityField,
                ingredientUnitField,
                addIngredientBtn,
                removeIngredientBtn
        );

        // Ingredient button functionality
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

        removeIngredientBtn.setOnAction(e -> {
            int selectedIndex = ingredientsListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                ingredientsListView.getItems().remove(selectedIndex);
            }
        });

        // --- Steps Section ---
        TextField stepInput = new TextField();
        stepInput.setPromptText("Enter a cooking step");
        Button addStepBtn = new Button("Add");
        ListView<String> stepsListView = new ListView<>();
        Button removeStepBtn = new Button("Remove");

        HBox stepButtonsBox = new HBox(10, addStepBtn, removeStepBtn);

        // Step button functionality
        addStepBtn.setOnAction(e -> {
            String step = stepInput.getText().trim();
            if (!step.isEmpty()) {
                stepsListView.getItems().add(step);
                stepInput.clear();
            }
        });

        stepInput.setMaxWidth(Double.MAX_VALUE);
        HBox.setHgrow(stepInput, Priority.ALWAYS);

        removeStepBtn.setOnAction(e -> {
            int selectedIndex = stepsListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                stepsListView.getItems().remove(selectedIndex);
            }
        });
        stepInput.setPrefWidth(285);

        Label ingredientsLabel = new Label("Ingredients:");
        ingredientsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        HBox ingredientControls = new HBox(10,
                ingredientNameField,
                ingredientQuantityField,
                ingredientUnitField,
                addIngredientBtn,
                removeIngredientBtn
        );
        ingredientControls.setAlignment(Pos.CENTER);

        VBox ingredientsSection = new VBox(10,
                ingredientsLabel,
                ingredientControls,
                ingredientsListView
        );

        ingredientsSection.setAlignment(Pos.TOP_CENTER);
        ingredientsSection.setPadding(new Insets(10));
        ingredientsSection.setPrefHeight(300);
        ingredientsListView.setPrefHeight(200);

        Label stepsLabel = new Label("Steps:");
        stepsLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        HBox stepControls = new HBox(10, stepInput, addStepBtn, removeStepBtn);
        stepControls.setAlignment(Pos.CENTER);

        VBox stepsSection = new VBox(10,
                stepsLabel,
                stepControls,
                stepsListView
        );
        stepsSection.setAlignment(Pos.TOP_CENTER);
        stepsSection.setPadding(new Insets(10));
        stepsSection.setPrefHeight(300);
        stepsListView.setPrefHeight(200);

        GridPane ingredientStepGrid = new GridPane();
        ingredientStepGrid.setHgap(20);
        ingredientStepGrid.setPadding(new Insets(10));

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(50); // Ingredients column
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50); // Steps column
        ingredientStepGrid.getColumnConstraints().addAll(col1, col2);

        ingredientStepGrid.add(ingredientsSection, 0, 0);
        ingredientStepGrid.add(stepsSection, 1, 0);

        ingredientsLabel.setStyle("-fx-background-color: #f4d9c6; -fx-padding: 6 10; "
                + "-fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 16px;");

        stepsLabel.setStyle("-fx-background-color: #f4d9c6; -fx-padding: 6 10; "
                + "-fx-background-radius: 8; -fx-font-weight: bold; -fx-font-size: 16px;");

        addIngredientBtn.prefHeightProperty().bind(ingredientNameField.heightProperty());
        removeIngredientBtn.prefHeightProperty().bind(ingredientNameField.heightProperty());

        addStepBtn.prefHeightProperty().bind(stepInput.heightProperty());
        removeStepBtn.prefHeightProperty().bind(stepInput.heightProperty());

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

        // --- Save Button ---
        Button saveButton = new Button("Save Recipe");
        saveButton.setOnAction(e -> {
            String name = recipeNameField.getText().trim();
            String category;
            if (userCategory.size() == 0) {
                category = "N/A";
            }
            else {
                category = userCategory.get(userCategory.size()-1);
            }
            String time = recipeTimeTaken.getText().trim();
            String servingSize = recipeServingSize.getText().trim();
            String calories = recipeCalories.getText().trim();
            String protein = recipeProtein.getText().trim();

            // Validation
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

            if ( !RecipeAdderTab.intChecker(time) || !RecipeAdderTab.intChecker(calories) || !RecipeAdderTab.intChecker(protein) || !RecipeAdderTab.intChecker(servingSize)) {
                new Alert(Alert.AlertType.WARNING, "Please enter numbers only for time, serving size, calories and protein.").showAndWait();
                return;
            }

            try {
                Recipe newRecipe = new Recipe(
                        name,
                        new ArrayList<>(ingredientsListView.getItems()),
                        new ArrayList<>(stepsListView.getItems()),
                        Integer.parseInt(time),
                        Integer.parseInt(servingSize),
                        category,
                        Integer.parseInt(calories),
                        Integer.parseInt(protein)
                );

                recipes.add(newRecipe);
                if (onSaveCallback != null) onSaveCallback.run();

                stage.close();
            } catch (NumberFormatException ex) {
                new Alert(Alert.AlertType.ERROR, "Time, Serving Size, Calories, and Protein must be numbers!").showAndWait();
            }
        });

        // --- Final Layout ---
        ingredientsListView.getStyleClass().add("input-field");
        stepsListView.getStyleClass().add("input-field");

        recipeNameField.getStyleClass().add("input-field");
        recipeTimeTaken.getStyleClass().add("input-field");
        recipeServingSize.getStyleClass().add("input-field");
        recipeCalories.getStyleClass().add("input-field");
        recipeProtein.getStyleClass().add("input-field");
        stepInput.getStyleClass().add("input-field");
        ingredientUnitField.getStyleClass().add("input-field");
        ingredientNameField.getStyleClass().add("input-field");
        ingredientQuantityField.getStyleClass().add("input-field");

        saveButton.getStyleClass().addAll("main-button", "save-button");

        addIngredientBtn.getStyleClass().add("action-button");
        removeIngredientBtn.getStyleClass().add("action-button");
        addStepBtn.getStyleClass().add("action-button");
        removeStepBtn.getStyleClass().add("action-button");

        VBox layout = new VBox(20,
                nameBox,
                timeCatSizeBox,
                calProBox,
                ingredientStepGrid,
                saveButton
        );

        layout.setAlignment(Pos.TOP_CENTER);
        layout.setPadding(new Insets(15));
        layout.setStyle("-fx-background-color: #faf3e0;");

        layout.setStyle("-fx-background-color: linear-gradient(to bottom, #f7e9dc, #fff3e6); "
                + "-fx-border-color: #d4b483; "
                + "-fx-border-radius: 12; "
                + "-fx-background-radius: 12; "
                + "-fx-padding: 20;");

        layout.setSpacing(25);
        timeCatSizeBox.setSpacing(50);
        calProBox.setSpacing(40);
        ingredientControls.setSpacing(15);
        stepControls.setSpacing(15);

        Scene scene = new Scene(layout, 1030, 1030);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        stage.setScene(scene);

        // Make the stage fullscreen on the primary screen
        Rectangle2D screenBounds = javafx.stage.Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        stage.show();
    }
}