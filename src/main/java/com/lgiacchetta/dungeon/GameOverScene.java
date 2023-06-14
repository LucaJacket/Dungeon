package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.scene.SubScene;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.*;

public class GameOverScene extends SubScene {
    public GameOverScene() {
        Rectangle bg = new Rectangle(FXGL.getAppWidth(), getAppHeight(),
                Color.color(0, 0, 0, 0.6));

        Rectangle bgGameOver = new Rectangle(600.0, 400.0, Color.BLACK);
        bgGameOver.setStroke(Color.RED);
        bgGameOver.setStrokeWidth(4.0);
        bgGameOver.setEffect(new DropShadow(64.0, Color.color(0,0,0, 0.9)));

        Text textGameOver = new Text("GAME OVER");
        textGameOver.setFont(Utils.UIFont.newFont(104.0));
        textGameOver.setFill(Color.RED);

        Text textContinue = new Text("Press Enter to restart");
        textContinue.setFont(Utils.UIFont.newFont(46.0));
        textContinue.setFill(Color.WHITE);

        VBox vbox = new VBox(48.0, textGameOver, textContinue);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(24.0));

        Pane pane = new StackPane(bg, bgGameOver, vbox);

        getContentRoot().getChildren().addAll(pane);

        getInput().addAction(new UserAction("Restart") {
            @Override
            protected void onActionBegin() {
                FXGL.getSceneService().popSubScene();
            }
        }, KeyCode.ENTER);
    }

    public void onGameOver() {
        FXGL.getSceneService().pushSubScene(this);
    }
}
