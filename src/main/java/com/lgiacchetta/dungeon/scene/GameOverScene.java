package com.lgiacchetta.dungeon.scene;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.dsl.FXGL;
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
import static com.lgiacchetta.dungeon.Utils.MUSIC_GAME;

/**
 * Scene that will be displayed when the players have died.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see SubScene
 */
public class GameOverScene extends SubScene {
    private final BooleanProperty isAnimationDone;

    /**
     * Initializes new GameOverScene.
     *
     * @param onPlayerDied called on scene pop
     */
    public GameOverScene(Runnable onPlayerDied) {
        Rectangle shadow = new Rectangle(getAppWidth(), getAppHeight(),
                Color.color(0, 0, 0, 0.6));

        Rectangle bg = new Rectangle(800.0, 400.0, Color.BLACK);
        bg.setStroke(Color.RED);
        bg.setStrokeWidth(4.0);
        bg.setEffect(new DropShadow(64.0, Color.color(0, 0, 0, 0.9)));

        Text textGameOver = getUIFactoryService().newText(
                "GAME OVER", Color.RED, 104.0);
        Text textContinue = getUIFactoryService().newText(
                "Press Enter to restart", Color.WHITE, 32.0);

        VBox vBox = new VBox(40.0, textGameOver, textContinue);
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

        getInput().addAction(new UserAction("Restart Level") {
            @Override
            protected void onActionBegin() {
                if (isAnimationDone.get()) {
                    getSceneService().popSubScene();
                    getAudioPlayer().loopMusic(MUSIC_GAME);
                    onPlayerDied.run();
                }
            }
        }, KeyCode.ENTER);
    }

    /**
     * Pushes GameOverScene, stops game music and plays game over sound.
     *
     * @see FXGL#getGameScene()
     * @see FXGL#getAudioPlayer()
     * @see FXGL#play(String)
     */
    public void onGameOver() {
        getSceneService().pushSubScene(this);
        getAudioPlayer().stopMusic(MUSIC_GAME);
        play("game-over.wav");
    }
}
