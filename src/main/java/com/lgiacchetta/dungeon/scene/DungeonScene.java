package com.lgiacchetta.dungeon.scene;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import javafx.geometry.Point2D;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

public abstract class DungeonScene extends SubScene {
    protected boolean isAnimationDone = false;
    protected List<Animation<?>> animations = new ArrayList<>();

    protected DungeonScene() {
        Pane pane = new StackPane(getShadow(), getBackground(), getContent());
        getContentRoot().getChildren().add(pane);
        animate(pane);
        addInput();
    }

    public final void push() {
        getSceneService().pushSubScene(this);
        onPush();
    }

    protected abstract void onPush();

    protected final void pop() {
        if (!isAnimationDone) return;
        getSceneService().popSubScene();
        onPop();
    }

    protected abstract void onPop();

    protected Rectangle getShadow() {
        return new Rectangle(getAppWidth(), getAppHeight(), Color.color(0, 0, 0, 0.6));
    }

    protected Rectangle getBackground() {
        Rectangle bg = new Rectangle(1000.0, 400.0, Color.BLACK);
        bg.setStroke(Color.YELLOW);
        bg.setStrokeWidth(4.0);
        bg.setEffect(new DropShadow(64.0, Color.color(0, 0, 0, 0.9)));
        return bg;
    }

    protected abstract Pane getContent();

    protected final void animate(Pane pane) {
        Animation<?> paneAnimation = animationBuilder()
                .duration(Duration.seconds(0.3))
                .onFinished(() -> {
                    for (Animation<?> animation : animations) animation.start();
                    isAnimationDone = true;
                })
                .fadeIn(pane)
                .from(0.0)
                .to(1.0)
                .build();
        this.addListener(paneAnimation);
        paneAnimation.start();
    }

    protected final void animateText(Text text) {
        Animation<?> animation = animationBuilder()
                .repeatInfinitely()
                .autoReverse(true)
                .scale(text)
                .from(new Point2D(1, 1))
                .to(new Point2D(1.1, 1.1))
                .build();
        this.addListener(animation);
        animations.add(animation);
    }

    protected final void addInput() {
        getInput().addAction(new UserAction("Continue") {
            @Override
            protected void onActionBegin() {
                pop();
            }
        }, KeyCode.ENTER);
    }
}
