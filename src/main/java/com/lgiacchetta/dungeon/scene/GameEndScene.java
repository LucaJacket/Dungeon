package com.lgiacchetta.dungeon.scene;

import com.almasb.fxgl.scene.SubScene;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.lgiacchetta.dungeon.Utils.MUSIC_GAME;
import static com.lgiacchetta.dungeon.Utils.MUSIC_MENU;

/**
 * Scene that will be displayed when the players have completed the final level.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see SubScene
 */
public class GameEndScene extends DungeonScene {

    @Override
    protected void onPush() {
        getAudioPlayer().stopMusic(MUSIC_GAME);
        play("level-win.wav");
    }

    @Override
    protected void onPop() {
        getAudioPlayer().loopMusic(MUSIC_MENU);
        getGameController().gotoMainMenu();
    }

    @Override
    protected Pane getContent() {
        Text textCongratulations = getUIFactoryService().newText("CONGRATULATIONS", Color.YELLOW, 104.0);
        Text textCompleted = getUIFactoryService().newText("You completed the game", Color.WHITE, 32.0);
        Text textThanks = getUIFactoryService().newText("Thank you for playing", Color.WHITE, 32.0);
        Text textGameDevelopers = getUIFactoryService().newText("developed by Sofia Vita & Luca Giacchetta", Color.WHITE, 16.0);
        Text textContinue = getUIFactoryService().newText("Press Enter to go back to Main Menu", Color.WHITE, 32.0);
        animateText(textContinue);

        VBox vBox = new VBox(32.0, textCongratulations, textCompleted, textThanks, textGameDevelopers, textContinue);
        vBox.setPrefSize(getAppWidth(), getAppHeight());
        vBox.setAlignment(Pos.CENTER);
        return vBox;
    }
}
