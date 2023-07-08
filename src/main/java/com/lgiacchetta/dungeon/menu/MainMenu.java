package com.lgiacchetta.dungeon.menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.lgiacchetta.dungeon.SkinSelector;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static com.almasb.fxgl.dsl.FXGL.image;
import static com.lgiacchetta.dungeon.Utils.createActionButton;

public class MainMenu extends FXGLMenu {
    private final static int LEVELS = 4;
    private final IntegerProperty texturePlayer1;
    private final IntegerProperty texturePlayer2;
    private final IntegerProperty chosenLevel;

    public MainMenu(IntegerProperty texturePlayer1, IntegerProperty texturePlayer2, IntegerProperty chosenLevel) {
        super(MenuType.MAIN_MENU);
        this.texturePlayer1 = texturePlayer1;
        this.texturePlayer2 = texturePlayer2;
        this.chosenLevel = chosenLevel;

        getContentRoot().setBackground(new Background(new BackgroundImage(  // Image by upklyak on Freepik
                image("loading.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));
        goToMainMenu();
    }

    private void goToMainMenu() {
        Text textTitle = getUIFactoryService().newText("Dungeon", Color.WHITE, 104.0);
        textTitle.setStroke(Color.BLACK);
        textTitle.setStrokeWidth(2.0);

        Pane buttonStartGame = createActionButton("Start Game", 400.0, 100.0, 48.0,
                this::fireNewGame);
        Pane buttonStartTutorial = createActionButton("Select Level", 400.0, 100.0, 48.0,
                this::goToSelectLevel);
        Pane buttonSelectHeroes = createActionButton("Select Heroes", 400.0, 100.0, 48.0,
                this::goToSelectHeroes);

        VBox vBox = new VBox(40.0, textTitle, buttonStartGame, buttonStartTutorial, buttonSelectHeroes);
        vBox.setPrefSize(getAppWidth(), getAppHeight());
        vBox.setAlignment(Pos.CENTER);

        getContentRoot().getChildren().setAll(vBox);
    }

    private void goToSelectLevel() {
        Text textTitle = getUIFactoryService().newText("Select Level", Color.WHITE, 104.0);
        textTitle.setStroke(Color.BLACK);
        textTitle.setStrokeWidth(2.0);

        VBox vBox = new VBox(20.0);
        vBox.setPrefSize(getAppWidth(), getAppHeight());
        vBox.setAlignment(Pos.CENTER);

        vBox.getChildren().add(textTitle);
        for (int i = 0; i < Math.ceil(LEVELS / 2.0); i++) {
            HBox hBox = new HBox(20.0);
            hBox.setAlignment(Pos.CENTER);
            for (int j = 0; j < 2; j++) {
                int level = i * 2 + j;
                String label = level == 0 ? "Tutorial" : "Level " + level;
                Pane button = createActionButton(label, 400.0, 100.0, 48.0, () -> {
                    chosenLevel.set(level);
                    goToMainMenu();
                    fireNewGame();
                });
                hBox.getChildren().add(button);
            }
            vBox.getChildren().add(hBox);
        }

        HBox backHBox = new HBox(20.0, createActionButton("Back", 400.0, 100.0, 48.0,
                this::goToMainMenu));
        backHBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(backHBox);

        getContentRoot().getChildren().setAll(vBox);
    }

    private void goToSelectHeroes() {
        Text textTitle = getUIFactoryService().newText("Select Heroes", Color.WHITE, 104.0);
        textTitle.setStroke(Color.BLACK);
        textTitle.setStrokeWidth(2.0);

        Text textPlayer1 = getUIFactoryService().newText("Player 1", Color.WHITE, 48.0);
        SkinSelector skinSelector1 = new SkinSelector(texturePlayer1);

        Text textPlayer2 = getUIFactoryService().newText("Player 2", Color.WHITE, 48.0);
        SkinSelector skinSelector2 = new SkinSelector(texturePlayer2);

        Pane buttonSave = createActionButton("Save", 400.0, 100.0, 48.0, this::goToMainMenu);
        buttonSave.setPadding(new Insets(40.0));

        VBox vBox = new VBox(40.0, textTitle, textPlayer1, skinSelector1, textPlayer2, skinSelector2, buttonSave);
        vBox.setPrefSize(getAppWidth(), getAppHeight());
        vBox.setAlignment(Pos.CENTER);

        getContentRoot().getChildren().setAll(vBox);
    }
}