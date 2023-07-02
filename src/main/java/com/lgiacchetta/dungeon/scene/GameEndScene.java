package com.lgiacchetta.dungeon.scene;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.lgiacchetta.dungeon.Utils.*;

public class GameEndScene extends SubScene {
    private final BooleanProperty isAnimationDone;

    public GameEndScene() {
        Rectangle shadow = new Rectangle(getAppWidth(), getAppHeight(),
                Color.color(0, 0, 0, 0.6));

        Rectangle bg = new Rectangle(1000.0, 400.0, Color.BLACK);
        bg.setStroke(Color.YELLOW);
        bg.setStrokeWidth(4.0);
        bg.setEffect(new DropShadow(64.0, Color.color(0,0,0, 0.9)));

        Text textCongratulations = getUIFactoryService().newText(
                "CONGRATULATIONS", Color.YELLOW, 104.0);
        Text textCompleted = getUIFactoryService().newText(
                "You completed the game", Color.WHITE, 32.0);
        Text textThanks = getUIFactoryService().newText(
                "Thank you for playing", Color.WHITE, 32.0);
        Text textGameDevelopers = getUIFactoryService().newText(
                "developed by Sofia Vita & Luca Giacchetta", Color.WHITE, 20.0);
        Text textContinue = getUIFactoryService().newText(
                "Press Enter to go back to main menu",  Color.WHITE, 32.0);

        VBox vBox = new VBox(40.0,
                textCongratulations,
                textCompleted,
                textThanks,
                textGameDevelopers,
                textContinue);
        vBox.setPrefSize(getAppWidth(), getAppHeight());
        vBox.setAlignment(Pos.CENTER);

        StackPane pane = new StackPane(shadow, bg, vBox);
        getContentRoot().getChildren().addAll(pane);

        Animation<?> animationTextContinue = animationBuilder()
                .repeatInfinitely()
                .autoReverse(true)
                .scale(textContinue)
                .from(new Point2D(1, 1))
                .to(new Point2D(1.1, 1.1))
                .build();
        this.addListener(animationTextContinue);

        isAnimationDone = new SimpleBooleanProperty(false);

        Animation<?> animation = animationBuilder()
                .duration(Duration.seconds(0.3))
                .onFinished(() -> {
                    isAnimationDone.set(true);
                    animationTextContinue.start();
                })
                .fadeIn(pane)
                .from(0.0)
                .to(1.0)
                .build();
        this.addListener(animation);
        animation.start();

        getInput().addAction(new UserAction("Game End") {
            @Override
            protected void onActionBegin() {
                if (isAnimationDone.get()) {
                    getSceneService().popSubScene();
                    getGameController().gotoMainMenu();
                    getAudioPlayer().loopMusic(musicMenu);
                }
            }
        }, KeyCode.ENTER);
    }

    public void onGameEnd() {
        getSceneService().pushSubScene(this);
        getAudioPlayer().stopMusic(musicGame);
        play("level-win.wav");
    }
}
