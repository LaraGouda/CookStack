module app.cookstack.cookstack {
        requires javafx.controls;
        requires javafx.fxml;
        requires javafx.graphics;
        requires javafx.base;

        opens app.cookstack.ui to javafx.fxml;
        exports app.cookstack.ui;

        exports app.cookstack.model;
        exports app.cookstack.utility;
        }
