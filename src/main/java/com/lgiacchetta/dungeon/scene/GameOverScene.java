package com.lgiacchetta.dungeon.scene;

import com.almasb.fxgl.scene.SubScene;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.lgiacchetta.dungeon.Utils.MUSIC_GAME;

/**
 * Scene that will be displayed when the players have died.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see SubScene
 */
public class GameOverScene extends DungeonScene {

    @Override
    protected void onPush() {
        getAudioPlayer().pauseMusic(MUSIC_GAME);
        play("game-over.wav");
    }

    @Override
    protected void onPop() {
        getAudioPlayer().resumeMusic(MUSIC_GAME);
        inc("deaths", 1);
    }

    protected Rectangle getBackground() {
        Rectangle bg = super.getBackground();
        bg.setStroke(Color.RED);
        return bg;
    }

    @Override
    protected Pane getContent() {
        Text textGameOver = getUIFactoryService().newText("GAME OVER", Color.RED, 104.0);
        Text textRestart = getUIFactoryService().newText("Press Enter to Restart", Color.WHITE, 32.0);
        animateText(textRestart);

        VBox vBox = new VBox(32.0, textGameOver, textRestart);
        vBox.setPrefSize(getAppWidth(), getAppHeight());
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}
