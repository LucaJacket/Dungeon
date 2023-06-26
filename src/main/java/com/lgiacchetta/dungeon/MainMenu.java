package com.lgiacchetta.dungeon;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import kotlin.jvm.functions.Function3;

import static com.lgiacchetta.dungeon.Utils.*;

public class MainMenu extends FXGLMenu {
    private int player1Skin = 0;
    private int player2Skin = 1;
    private final Function3<Integer, String, String, Void> setupGame;

    public MainMenu(Function3<Integer, String, String, Void> setupGame) {
        super(MenuType.MAIN_MENU);
        this.setupGame = setupGame;

        goToMainMenu();
    }

    public void goToMainMenu() {
        getRoot().setBackground(new Background(new BackgroundImage(  // Image by upklyak on Freepik
                FXGL.image("loading.jpg"), null, null, null, null)));

        Text textTitle = new Text("Dungeon");
        textTitle.setFont(Utils.UIFont.newFont(104.0));
        textTitle.setFill(Color.WHITE);
        textTitle.setStroke(Color.BLACK);
        textTitle.setStrokeWidth(2.0);

        Pane buttonStartGame = createActionButton("Start Game", 400.0, 100.0, 48.0,
                () -> {
                    setupGame.invoke(1, heroes[player1Skin], heroes[player2Skin]);
                    fireNewGame();
        });
        Pane buttonStartTutorial = createActionButton("Start Tutorial", 400.0, 100.0, 48.0,
                () -> {
                    setupGame.invoke(0, heroes[player1Skin], heroes[player2Skin]);
                    fireNewGame();
        });
        Pane buttonSelectHeroes = createActionButton("Select Heroes",  400.0, 100.0, 48.0,
                this::goToSelectHeroes);

        VBox vboxMenu = new VBox(40.0,
                textTitle,
                buttonStartGame,
                buttonStartTutorial,
                buttonSelectHeroes);
        vboxMenu.setMinSize(FXGL.getAppWidth(), FXGL.getAppHeight());
        vboxMenu.setAlignment(Pos.CENTER);

        getContentRoot().getChildren().setAll(vboxMenu);
    }

    public void goToSelectHeroes() {
        getRoot().setBackground(new Background(new BackgroundImage(  // Image by upklyak on Freepik
                FXGL.image("loading.jpg"), null, null, null, null)));

        Text textTitle = new Text("Select Heroes");
        textTitle.setFont(Utils.UIFont.newFont(104.0));
        textTitle.setFill(Color.WHITE);
        textTitle.setStroke(Color.BLACK);
        textTitle.setStrokeWidth(2.0);

        Text textPlayer1 = new Text("Player 1");
        textPlayer1.setFont(Utils.UIFont.newFont(48.0));
        textPlayer1.setFill(Color.WHITE);

        SkinSelector skinSelector1 = new SkinSelector(player1Skin, heroes);

        Text textPlayer2 = new Text("Player 2");
        textPlayer2.setFont(Utils.UIFont.newFont(48.0));
        textPlayer2.setFill(Color.WHITE);

        SkinSelector skinSelector2 = new SkinSelector(player2Skin, heroes);

        Pane buttonGoBack = createActionButton("Go Back", 200.0, 100.0, 48.0,
                this::goToMainMenu);
        Pane buttonSave = createActionButton("Save", 200.0, 100.0, 48.0,
                () -> {
                    player1Skin = skinSelector1.getSkinIndex();
                    player2Skin = skinSelector2.getSkinIndex();
                    goToMainMenu();
        });

        HBox hBox = new HBox(20.0, buttonGoBack, buttonSave);
        hBox.setPadding(new Insets(20.0));
        hBox.setAlignment(Pos.CENTER);

        VBox vboxMenu = new VBox(40.0,
                textTitle,
                textPlayer1,
                skinSelector1,
                textPlayer2,
                skinSelector2,
                hBox);
        vboxMenu.setMinSize(FXGL.getAppWidth(), FXGL.getAppHeight());
        vboxMenu.setAlignment(Pos.CENTER);

        getContentRoot().getChildren().setAll(vboxMenu);
    }
}