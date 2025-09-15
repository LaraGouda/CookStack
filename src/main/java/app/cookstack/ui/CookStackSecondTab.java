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

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Main application window that displays the list of recipes inside a cookbook.
 * Provides functionality for:
 * <ul>
 *     <li>Viewing recipes as cards</li>
 *     <li>Searching recipes by name</li>
 *     <li>Sorting recipes by various attributes</li>
 *     <li>Adding, editing, and deleting recipes</li>
 *     <li>Saving updates to a persistent file</li>
 * </ul>
 *
 * This class manages both the UI and interactions with the underlying {@link RecipeBook}.
 */
public class CookStackSecondTab {
    private final File saveFile; // The file where the current cookbook is stored.
    private RecipeBook cookbook; //The loaded cookbook data model.
    private ObservableList<Recipe> recipes; //The observable list of recipes shown in the UI.
    private final ArrayList<Recipe> originalOrder = new ArrayList<>(); //The original order of recipes (used for restoring "Date Added" sorting).

    /**
     * Constructs a new MainWindow instance with a save file and cookbook.
     *
     * @param saveFile the file containing the serialized cookbook data
     * @param cookbook the loaded {@link RecipeBook} instance
     */
    public CookStackSecondTab(File saveFile, RecipeBook cookbook) {
        this.saveFile = saveFile;
        this.cookbook = cookbook;
        this.recipes = FXCollections.observableArrayList(cookbook.getRecipeList());
        originalOrder.addAll(cookbook.getRecipeList()); //
    }

    /**
     * Saves the current state of the cookbook to disk.
     * Updates the recipe list inside the {@link RecipeBook} object before saving.
     */
    private void saveCookBook() {
        try {
            cookbook.setRecipeList(new ArrayList<>(recipes));
            cookbook.saveToFile(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Extracts a filename without its extension and capitalizes the first letter.
     *
     * @param fileName the full file name
     * @return the file name without extension, capitalized
     */
    public static String getFileNameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        String nameWithoutExt = dotIndex == -1 ? fileName : fileName.substring(0, dotIndex);

        if (!nameWithoutExt.isEmpty()) {
            nameWithoutExt = nameWithoutExt.substring(0, 1).toUpperCase() + nameWithoutExt.substring(1);
        }
        return nameWithoutExt;
    }


    /**
     * Initializes and displays the main window of the application.
     * Sets up:
     * <ul>
     *     <li>Header with title and back button</li>
     *     <li>Search bar, sort options, and add recipe button</li>
     *     <li>Recipe container with scroll support</li>
     * </ul>
     *
     * @param stage the {@link Stage} to display the UI on
     */
    public void start(Stage stage) {
        stage.setTitle(CookStackSecondTab.getFileNameWithoutExtension(saveFile.getName()) + "'s Cook Book");

        // Setup screen bounds and stage size
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        stage.setX(screenBounds.getMinX());
        stage.setY(screenBounds.getMinY());
        stage.setWidth(screenBounds.getWidth());
        stage.setHeight(screenBounds.getHeight());

        // --- Header section setup (logo, title, back button) ---
        ImageView logoView = new ImageView(getClass().getResource("/images/logo.png").toExternalForm());
        logoView.setFitWidth(50);
        logoView.setFitHeight(50);
        logoView.setPreserveRatio(true);
        logoView.setOpacity(0.5);

        Label titleLabel = new Label(getFileNameWithoutExtension(saveFile.getName()) + "'s Cook Book");
        titleLabel.setStyle("-fx-font-size: 40px; -fx-font-weight: bold; -fx-text-fill: white;");
        titleLabel.getStyleClass().add("title-text");


        Button backBtn = new Button("← Back");
        backBtn.setStyle("-fx-font-size: 24px; -fx-background-color: transparent; -fx-text-fill: white;");
        backBtn.setOnAction(e -> {
            Stage cookStackStage = new Stage();
            try {
                new CookStackFirstTab().start(cookStackStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            stage.close();
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);


        backBtn.setOnMouseEntered(ev -> backBtn.setScaleX(1.1));
        backBtn.setOnMouseExited(ev -> backBtn.setScaleX(1));

        HBox header = new HBox(10, logoView, titleLabel, spacer, backBtn);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(20, 20, 20, 30));
        header.setStyle("-fx-background-color: #c69c6d;");

        // --- Search and controls bar ---
        TextField searchField = new TextField();
        searchField.setPromptText("Search recipes...");
        searchField.setPrefWidth(400);
        Button searchBtn = new Button();
        Image searchImage = new Image(getClass().getResource("/images/icons/search.png").toExternalForm(),
                25, 25, true, true);
        ImageView searchIcon = new ImageView(searchImage);
        searchBtn.setGraphic(searchIcon);
        searchBtn.setStyle("-fx-background-color: transparent;");

        ComboBox<String> sortCombo = new ComboBox<>();
        sortCombo.getItems().addAll("Name", "Steps", "Protein", "Calories", "Time", "Ingredients", "Date Added");
        sortCombo.setPromptText("Sort by...");
        sortCombo.setStyle("-fx-background-color: #f9e0c7; -fx-font-size: 14px;");

        Button toggleOrderBtn = new Button("↑↓");
        toggleOrderBtn.setStyle("-fx-font-size: 16px; -fx-background-color: transparent;");
        toggleOrderBtn.setOnAction(e -> {
            FXCollections.reverse(recipes); // reverses the current list
            refreshRecipeContainer(
                    (VBox)((ScrollPane)((BorderPane)stage.getScene().getRoot()).getCenter()).getContent(),
                    stage, true
            );
        });

        searchField.setMinWidth(200);
        HBox.setHgrow(searchField, Priority.ALWAYS);

        Button addRecipeBtn = new Button("+ Add Recipe");
        addRecipeBtn.getStyleClass().add("main-button");
        addRecipeBtn.setOnAction(e -> openRecipeAdder(stage));

        addRecipeBtn.setOnMousePressed(e -> addRecipeBtn.setScaleX(0.95));
        addRecipeBtn.setOnMouseReleased(e -> {
            addRecipeBtn.setScaleX(1);
        });

        HBox searchBox = new HBox(10, addRecipeBtn, searchField, searchBtn, sortCombo, toggleOrderBtn);
        searchBox.setPadding(new Insets(15));
        searchBox.setAlignment(Pos.CENTER_LEFT);
        searchBox.setStyle("-fx-background-color: #f9e0c7;");

        searchField.prefHeightProperty().bind(addRecipeBtn.heightProperty());
        searchField.getStyleClass().add("search-input");
        searchBtn.getStyleClass().add("search-button");

        sortCombo.getStyleClass().add("sort-button");
        toggleOrderBtn.getStyleClass().add("order-button");

        // --- Recipe container setup ---
        VBox recipeContainer = new VBox(15);
        recipeContainer.setPadding(new Insets(20));
        recipeContainer.setStyle("-fx-background-color: #fff2e0;");

        refreshRecipeContainer(recipeContainer, stage, true);

        // --- Search filter logic ---
        searchField.textProperty().addListener((obs, oldVal, newVal) -> {
            String query = newVal.trim().toLowerCase();

            if (query.isEmpty()) {
                recipes.setAll(cookbook.getRecipeList());
            } else {
                Recipe exactMatch = cookbook.getRecipeByName(query);
                if (exactMatch != null) {
                    recipes.setAll(exactMatch);
                } else {
                    ArrayList<Recipe> filtered = new ArrayList<>();
                    for (Recipe r : cookbook.getRecipeList()) {
                        if (r.getRecipeName().toLowerCase().contains(query)) {
                            filtered.add(r);
                        }
                    }
                    recipes.setAll(filtered);
                }
            }

            refreshRecipeContainer(recipeContainer, stage, false);
        });

        // --- Sorting logic ---
        sortCombo.setOnAction(e -> {
            String selected = sortCombo.getValue();
            if (selected == null) return;

            switch (selected) {
                case "Name":
                    FXCollections.sort(recipes, new NameComparator());
                    break;
                case "Steps":
                    FXCollections.sort(recipes, new StepsTakenComparator());
                    break;
                case "Protein":
                    FXCollections.sort(recipes, new ProteinNumberComparator());
                    break;
                case "Calories":
                    FXCollections.sort(recipes, new CaloriesNumberComparator());
                    break;
                case "Time":
                    FXCollections.sort(recipes, new TimeTakenComparator());
                    break;
                case "Ingredients":
                    FXCollections.sort(recipes, new IngredientNumberComparator());
                    break;
                case "Date Added":
                    recipes.setAll(originalOrder);
                    break;
            }

            refreshRecipeContainer(
                    (VBox)((ScrollPane)((BorderPane)stage.getScene().getRoot()).getCenter()).getContent(),
                    stage, true
            );
        });

        toggleOrderBtn.getStyleClass().add("icon-button");

        // --- Final layout ---
        ScrollPane scrollPane = new ScrollPane(recipeContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background-color: transparent;");

        BorderPane root = new BorderPane();
        root.setTop(new VBox(header, searchBox));
        root.setCenter(scrollPane);
        scrollPane.getStyleClass().add("loaded");

        scrollPane.viewportBoundsProperty().addListener((obs, oldVal, newVal) -> {
            recipeContainer.setPrefHeight(newVal.getHeight());
        });

        Scene scene = new Scene(root, 1000, 700);
        scene.getStylesheets().add(getClass().getResource("/styles/main.css").toExternalForm());
        stage.setScene(scene);
        stage.show();

        header.getStyleClass().add("loaded");
    }

    /**
     * Refreshes the recipe container UI by clearing and reloading all recipe cards.
     * Optionally animates the cards when they appear.
     *
     * @param recipeContainer the container to hold recipe cards
     * @param stage           the current stage
     * @param animate         whether to animate card appearance
     */
    private void refreshRecipeContainer(VBox recipeContainer, Stage stage, boolean animate) {
        recipeContainer.getChildren().clear();

        if (recipes.isEmpty()) {
            Label emptyLabel = new Label("No recipes yet. Add your first recipe!");
            emptyLabel.setStyle("-fx-font-size: 16px; -fx-text-fill: #5a4633;");
            HBox emptyBox = new HBox(emptyLabel);
            emptyBox.setAlignment(Pos.CENTER);
            emptyBox.setPadding(new Insets(20));
            recipeContainer.getChildren().add(emptyBox);
        } else {
            for (Recipe recipe : recipes) {
                VBox recipeCard = createRecipeCard(recipe, stage);
                recipeContainer.getChildren().add(recipeCard);
                recipeCard.getStyleClass().add("loaded");

                if (animate) { // only animate if flag is true
                    FadeTransition fade = new FadeTransition(Duration.millis(300), recipeCard);
                    fade.setFromValue(0);
                    fade.setToValue(1);

                    TranslateTransition slide = new TranslateTransition(Duration.millis(300), recipeCard);
                    slide.setFromY(20);
                    slide.setToY(0);

                    ParallelTransition animation = new ParallelTransition(fade, slide);
                    animation.play();
                }
            }
        }
    }

    /**
     * Creates a UI card representing a single recipe with details and actions.
     *
     * @param recipe the recipe to display
     * @param stage  the current stage
     * @return a VBox representing the recipe card
     */
    private VBox createRecipeCard(Recipe recipe, Stage stage) {
        VBox card = new VBox();
        card.setSpacing(10);
        card.setPadding(new Insets(15));
        card.getStyleClass().add("recipe-card");
        card.setCursor(Cursor.HAND);

        // Recipe name
        Label nameLabel = new Label(recipe.getRecipeName());
        nameLabel.setFont(new Font(18));
        nameLabel.setStyle("-fx-text-fill: #3e3e3e; -fx-font-weight: bold;");

        // Info row (calories, protein, time, etc.)
        GridPane infoRow = new GridPane();
        infoRow.setHgap(20);
        infoRow.setAlignment(Pos.CENTER_LEFT);

        Label caloriesLabel = new Label("Calories: " + recipe.getRecipeCalories());
        Label proteinLabel = new Label("Protein: " + recipe.getRecipeProtein() + "g");
        Label timeLabel = new Label("Time: " + recipe.getTimeTaken() + " min");
        Label stepsLabel = new Label("Steps: " + recipe.getRecipeSteps().size());
        Label ingredientsLabel = new Label("Ingredients: " + recipe.getRecipeIngredients().size());
        Label categoryLabel = new Label("Category: " + recipe.getRecipeCategory());
        Label servingSizeLabel = new Label("Serving Size: " + recipe.getServingSize());
        for (Label lbl : new Label[]{caloriesLabel, proteinLabel, timeLabel, stepsLabel, ingredientsLabel, servingSizeLabel, categoryLabel}) {
            lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #5a4633;");
        }

        infoRow.add(caloriesLabel, 0, 0);
        infoRow.add(proteinLabel, 1, 0);
        infoRow.add(timeLabel, 2, 0);
        infoRow.add(stepsLabel, 3, 0);
        infoRow.add(ingredientsLabel, 4, 0);
        infoRow.add(servingSizeLabel, 5, 0);
        infoRow.add(categoryLabel, 6, 0);

        for (int i = 0; i < 7; i++) {
            ColumnConstraints column = new ColumnConstraints();
            column.setPercentWidth(100.0 / 7);
            infoRow.getColumnConstraints().add(column);
        }

        // Options menu
        Button optionsBtn = new Button("⋮");
        optionsBtn.setStyle("-fx-font-size: 18px; -fx-background-color: transparent;");
        ContextMenu menu = new ContextMenu();
        menu.setAutoHide(true);

        MenuItem view = new MenuItem("View");
        MenuItem edit = new MenuItem("Edit");
        MenuItem delete = new MenuItem("Delete");

        menu.getItems().addAll(view, edit, delete);

        view.setOnAction(ev -> new RecipeViewerTab(recipe).show(stage));

        edit.setOnAction(ev -> {
            RecipeEditorTab editor = new RecipeEditorTab(recipe, () -> {
                saveCookBook();
                refreshRecipeContainer(
                        (VBox)((ScrollPane)((BorderPane)stage.getScene().getRoot()).getCenter()).getContent(),
                        stage, true
                );
            });
            editor.show(stage);
        });


        delete.setOnAction(ev -> {
            recipes.remove(recipe);
            originalOrder.remove(recipe);
            saveCookBook();
            refreshRecipeContainer(
                    (VBox)((ScrollPane)((BorderPane)stage.getScene().getRoot()).getCenter()).getContent(),
                    stage, true
            );
        });


        optionsBtn.setOnAction(event -> {
            if (menu.isShowing()) {
                menu.hide();
            } else {
                menu.show(optionsBtn, javafx.geometry.Side.BOTTOM, 0, 0);
            }
        });

        // Click and hover effects
        card.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 1) { // Double-click detected
                new RecipeViewerTab(recipe).show(stage);
            }
        });

        card.setOnMouseEntered(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), card);
            st.setToX(1.02);
            st.setToY(1.02);
            st.play();
        });
        card.setOnMouseExited(e -> {
            ScaleTransition st = new ScaleTransition(Duration.millis(150), card);
            st.setToX(1);
            st.setToY(1);
            st.play();
        });

        HBox infoAndOptions = new HBox();
        infoAndOptions.setAlignment(Pos.CENTER_LEFT);
        infoAndOptions.setSpacing(10);
        infoAndOptions.setHgrow(infoRow, Priority.ALWAYS);
        infoAndOptions.getChildren().addAll(infoRow, optionsBtn);

        card.getChildren().addAll(nameLabel, infoAndOptions);

        return card;
    }

    /**
     * Opens the recipe adder window. After adding, moves the new recipe to the top of the list
     * and updates both the {@link RecipeBook} and the UI.
     *
     * @param stage the current stage
     */
    private void openRecipeAdder(Stage stage) {
        RecipeAdderTab recipeAdder = new RecipeAdderTab(recipes, () -> {
            Recipe newRecipe = recipes.remove(recipes.size() - 1);
            recipes.add(0, newRecipe);

            originalOrder.add(0, newRecipe);

            saveCookBook();
            refreshRecipeContainer(
                    (VBox)((ScrollPane)((BorderPane)stage.getScene().getRoot()).getCenter()).getContent(),
                    stage, true
            );
        });
        recipeAdder.show(stage);
    }
}
