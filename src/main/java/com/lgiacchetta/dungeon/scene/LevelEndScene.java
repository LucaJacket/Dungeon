package com.lgiacchetta.dungeon.scene;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import com.lgiacchetta.dungeon.component.PlayerComponent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.lgiacchetta.dungeon.Utils.*;

public class LevelEndScene extends SubScene {
    private final BooleanProperty isAnimationDone;

    public LevelEndScene(Runnable onLevelEnded) {
        Rectangle shadow = new Rectangle(getAppWidth(), getAppHeight(),
                Color.color(0, 0, 0, 0.6));

        Rectangle bg = new Rectangle(1000.0, 400.0, Color.BLACK);
        bg.setStroke(Color.YELLOW);
        bg.setStrokeWidth(4.0);
        bg.setEffect(new DropShadow(64.0, Color.color(0,0,0, 0.9)));

        Text textEndScene = getUIFactoryService().newText(
                "LEVEL COMPLETED", Color.YELLOW, 104.0);
        Text textContinue = getUIFactoryService().newText(
                "Press Enter to start the new level", Color.WHITE, 32.0);

        int points = 0;
        points += getGameWorld().getEntitiesByComponent(PlayerComponent.class).stream()
                .filter(entity -> entity.getComponent(PlayerComponent.class).getHealth() == 3.0)
                .count();
        if (getd("levelTime") < 180) points++;

        HBox hBox = new HBox(100.0);
        hBox.setPadding(new Insets(40.0));
        hBox.setAlignment(Pos.CENTER);
        for (int i = 1; i <= 3; i++) {
            AnimationChannel open = getAnimation("chest/chest_full_open_anim_f", 3, 1.0);
            AnimationChannel closed = getAnimation("chest/chest_full_open_anim_f", 1, 1.0);
            AnimatedTexture texture = new AnimatedTexture(i <= points ? open : closed);
            this.addListener(texture);
            Animation<?> animation = animationBuilder()
                    .duration(Duration.seconds(0.5))
                    .delay(Duration.seconds(0.2 * i + 0.3))
                    .interpolator(Interpolators.ELASTIC.EASE_OUT())
                    .onFinished(texture::play)
                    .scale(texture)
                    .from(new Point2D(0.0, 0.0))
                    .to(new Point2D(5.0, 5.0))
                    .build();
            this.addListener(animation);
            animation.start();
            hBox.getChildren().add(texture);
        }

        VBox vBox = new VBox(40.0, textEndScene, hBox, textContinue);
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
                    animationTextContinue.start();
                    isAnimationDone.set(true);
                })
                .fadeIn(pane)
                .from(0.0)
                .to(1.0)
                .build();
        this.addListener(animation);
        animation.start();

        getInput().addAction(new UserAction("Go to the next Level") {
            @Override
            protected void onActionBegin() {
                if (isAnimationDone.get()) {
                    getSceneService().popSubScene();
                    getAudioPlayer().loopMusic(musicGame);
                    onLevelEnded.run();
                }
            }
        }, KeyCode.ENTER);

    }

    public void onLevelEnd() {
        getSceneService().pushSubScene(this);
        getAudioPlayer().stopMusic(musicGame);
        play("level-win.wav");
    }
}
