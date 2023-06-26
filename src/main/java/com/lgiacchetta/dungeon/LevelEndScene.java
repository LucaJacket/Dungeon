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
import static com.almasb.fxgl.dsl.FXGL.setLevelFromMap;
import static com.lgiacchetta.dungeon.Utils.musicGame;
import static com.lgiacchetta.dungeon.Utils.musicMenu;

public class LevelEndScene extends SubScene {
    public LevelEndScene(Runnable onPop) {
        Rectangle bg = new Rectangle(FXGL.getAppWidth(), FXGL.getAppHeight(),
                Color.color(0, 0, 0, 0.6));

        Rectangle bgEndScene = new Rectangle(800.0, 400.0, Color.DARKGREEN);
        bgEndScene.setStroke(Color.RED);
        bgEndScene.setStrokeWidth(4.0);
        bgEndScene.setEffect(new DropShadow(64.0, Color.color(0, 0, 0, 0.9)));

        Text textEndScene = new Text("LEVEL PASSED");
        textEndScene.setFont(Utils.UIFont.newFont(104.0));
        textEndScene.setFill(Color.RED);

        Text textContinue = new Text("Press Enter to start the new level");
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

        VBox vbox = new VBox(48.0,
                textEndScene,
                textContinue);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(24.0));

        Pane pane = new StackPane(bg, bgEndScene, vbox);

        getContentRoot().getChildren().addAll(pane);

        getInput().addAction(new UserAction("Pass to the next Level") {
            @Override
            protected void onActionBegin() {
                FXGL.getSceneService().popSubScene();
                FXGL.getGameController().gotoPlay();
                FXGL.getAudioPlayer().loopMusic(musicGame);
                onPop.run();
            }
        }, KeyCode.ENTER);

    }
    public void onLevelEnd() {
        FXGL.getSceneService().pushSubScene(this);
        FXGL.getAudioPlayer().stopMusic(musicGame);
        FXGL.play("level-win.wav");
    }
}
