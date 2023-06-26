package com.lgiacchetta.dungeon;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.animationBuilder;
import static com.almasb.fxgl.dsl.FXGL.getAppHeight;
import static com.lgiacchetta.dungeon.Utils.musicGame;
import static com.lgiacchetta.dungeon.Utils.musicMenu;

public class GameEndScene extends SubScene {
    public GameEndScene() {
        Rectangle bg = new Rectangle(FXGL.getAppWidth(), getAppHeight(),
                Color.color(0, 0, 0, 0.6));

        Rectangle bgGameOver = new Rectangle(1000.0, 400.0, Color.BLACK);
        bgGameOver.setStroke(Color.YELLOW);
        bgGameOver.setStrokeWidth(4.0);
        bgGameOver.setEffect(new DropShadow(64.0, Color.color(0,0,0, 0.9)));

        Text textCongratulations = new Text("CONGRATULATIONS");
        textCongratulations.setFont(Utils.UIFont.newFont(104.0));
        textCongratulations.setFill(Color.YELLOW);

        Text textCompleted = new Text("You completed the game");
        textCompleted.setFont(Utils.UIFont.newFont(32.0));
        textCompleted.setFill(Color.YELLOW);

        Text textThanks = new Text("Thank you for playing");
        textThanks.setFont(Utils.UIFont.newFont(32.0));
        textThanks.setFill(Color.WHITE);

        Text textGameDevelopers = new Text("developed by Sofia Vita & Luca Giacchetta");
        textGameDevelopers.setFont(Utils.UIFont.newFont(20.0));
        textGameDevelopers.setFill(Color.WHITE);

        Text textContinue = new Text("Press Enter to go back to main menu");
        textContinue.setFont(Utils.UIFont.newFont(32.0));
        textContinue.setFill(Color.WHITE);

        Animation<?> animationTextContinue = animationBuilder()
                .repeatInfinitely()
                .autoReverse(true)
                .scale(textContinue)
                .from(new Point2D(1, 1))
                .to(new Point2D(1.1, 1.1))
                .build();
        animationTextContinue.start();
        this.addListener(animationTextContinue);

        VBox vbox = new VBox(24.0,
                textCongratulations,
                textCompleted,
                textThanks,
                textGameDevelopers,
                textContinue);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(24.0));

        Pane pane = new StackPane(bg, bgGameOver, vbox);

        getContentRoot().getChildren().addAll(pane);
        getInput().addAction(new UserAction("Game End") {
            @Override
            protected void onActionBegin() {
                FXGL.getSceneService().popSubScene();
                FXGL.getGameController().gotoMainMenu();
                FXGL.getAudioPlayer().loopMusic(musicMenu);
            }
        }, KeyCode.ENTER);
    }

    public void onGameEnd() {
        FXGL.getSceneService().pushSubScene(this);
        FXGL.getAudioPlayer().stopMusic(musicGame);
        FXGL.play("level-win.wav");
    }
}
