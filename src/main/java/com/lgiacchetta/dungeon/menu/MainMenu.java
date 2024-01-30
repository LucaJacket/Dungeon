package com.lgiacchetta.dungeon.menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;
import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static com.lgiacchetta.dungeon.Utils.*;

/**
 * Menu that will be displayed when starting the application.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see FXGLMenu
 */
public class MainMenu extends FXGLMenu {
    private final IntegerProperty texturePlayer1;
    private final IntegerProperty texturePlayer2;
    private final IntegerProperty chosenLevel;

    /**
     * Initializes MainMenu
     *
     * @param texturePlayer1 texture of player 1
     * @param texturePlayer2 texture of player 2
     * @param chosenLevel    chosen level
     */
    public MainMenu(IntegerProperty texturePlayer1, IntegerProperty texturePlayer2, IntegerProperty chosenLevel) {
        super(MenuType.MAIN_MENU);
        this.texturePlayer1 = texturePlayer1;
        this.texturePlayer2 = texturePlayer2;
        this.chosenLevel = chosenLevel;
        getContentRoot().setBackground(MAIN_BACKGROUND);
        goToMainMenu();
    }

    private Text getTitle(String text) {
        Text textTitle = getUIFactoryService().newText(text, Color.WHITE, 104.0);
        textTitle.setStroke(Color.BLACK);
        textTitle.setStrokeWidth(2.0);
        return textTitle;
    }

    private void goToMainMenu() {
        Text textTitle = getTitle("Dungeon");

        Pane buttonStartGame = createActionButton("Start Game", 400.0, 100.0, 48.0, this::fireNewGame);
        Pane buttonStartTutorial = createActionButton("Select Level", 400.0, 100.0, 48.0, this::goToSelectLevel);
        Pane buttonSelectHeroes = createActionButton("Select Heroes", 400.0, 100.0, 48.0, this::goToSelectHeroes);

        VBox vBox = new VBox(40.0, textTitle, buttonStartGame, buttonStartTutorial, buttonSelectHeroes);
        vBox.setMinSize(getAppWidth(), getAppHeight());
        vBox.setMaxSize(getAppWidth(), getAppHeight());
        vBox.setAlignment(Pos.CENTER);

        getContentRoot().getChildren().setAll(vBox);
    }

    private Pane createLevelButton(String label, int level) {
        return createActionButton(label, 400.0, 100.0, 48.0, () -> {
            chosenLevel.set(level);
            fireNewGame();
        });
    }

    private void goToSelectLevel() {
        Text textTitle = getTitle("Select Level");

        GridPane grid = new GridPane();
        grid.addRow(0, createLevelButton("Tutorial", 0), createLevelButton("Level 1", 1));
        grid.addRow(1, createLevelButton("Level 2", 2), createLevelButton("Level 3", 3));
        grid.setAlignment(Pos.CENTER);

        Pane buttonBack = createActionButton("Back", 400.0, 100.0, 48.0, this::goToMainMenu);

        VBox vBox = new VBox(20.0, textTitle, grid, buttonBack);
        vBox.setMinSize(getAppWidth(), getAppHeight());
        vBox.setMaxSize(getAppWidth(), getAppHeight());
        vBox.setAlignment(Pos.CENTER);
        getContentRoot().getChildren().setAll(vBox);
    }

    private void goToSelectHeroes() {
        Text textTitle = getTitle("Select Heroes");
        Text textPlayer1 = getUIFactoryService().newText("Player 1", Color.WHITE, 48.0);
        Text textPlayer2 = getUIFactoryService().newText("Player 2", Color.WHITE, 48.0);
        Pane buttonSave = createActionButton("Save", 400.0, 100.0, 48.0, this::goToMainMenu);
        buttonSave.setPadding(new Insets(40.0));

        VBox vBox = new VBox(40.0, textTitle, textPlayer1, createSkinSelector(texturePlayer1), textPlayer2, createSkinSelector(texturePlayer2), buttonSave);
        vBox.setPrefSize(getAppWidth(), getAppHeight());
        vBox.setAlignment(Pos.CENTER);
        getContentRoot().getChildren().setAll(vBox);
    }


    private Pane createSkinSelector(IntegerProperty index) {
        AnimatedTexture texture = new AnimatedTexture(createAnimationChannel(SKINS.get(index.get()) + "_idle_anim_f", 4, 0.5));
        texture.loop();
        texture.setScaleX(3.0);
        texture.setScaleY(3.0);
        getSceneService().getCurrentScene().addListener(texture);
        index.addListener((observable, oldValue, newValue) -> texture.loopAnimationChannel(createAnimationChannel(SKINS.get(index.get()) + "_idle_anim_f", 4, 0.5)));

        Polygon leftArrow = new Polygon(20.0, 0.0, 20.0, 20.0, 0.0, 10.0);
        leftArrow.fillProperty().bind(Bindings.when(leftArrow.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));
        leftArrow.setOnMouseClicked(event -> index.set((index.get() + SKINS.size() - 1) % SKINS.size()));

        Polygon rightArrow = new Polygon(0.0, 0.0, 0.0, 20.0, 20.0, 10.0);
        rightArrow.fillProperty().bind(Bindings.when(rightArrow.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));
        rightArrow.setOnMouseClicked(event -> index.set((index.get() + 1) % SKINS.size()));

        HBox hBox = new HBox(40.0, leftArrow, texture, rightArrow);
        hBox.setAlignment(Pos.CENTER);
        return hBox;
    }
}