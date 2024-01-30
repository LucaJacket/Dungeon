package com.lgiacchetta.dungeon.menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.lgiacchetta.dungeon.Utils.*;

/**
 * Menu that will be displayed then firing game pause.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see FXGLMenu
 */
public class GameMenu extends FXGLMenu {
    private final DoubleProperty musicVolume;
    private final DoubleProperty soundVolume;

    /**
     * Initializes GameMenu.
     */
    public GameMenu() {
        super(MenuType.GAME_MENU);
        musicVolume = new SimpleDoubleProperty(getSettings().getGlobalMusicVolume());
        soundVolume = new SimpleDoubleProperty(getSettings().getGlobalSoundVolume());
        Pane pane = new StackPane(getShadow(), getBackground(), getContent());
        getContentRoot().getChildren().add(pane);
    }

    private Rectangle getShadow() {
        return new Rectangle(getAppWidth(), getAppHeight(), Color.color(0, 0, 0, 0.6));
    }

    private Rectangle getBackground() {
        Rectangle bg = createRectangle(1000.0, 600.0, Color.YELLOW);
        bg.setEffect(new DropShadow(64.0, Color.color(0, 0, 0, 0.9)));
        return bg;
    }

    private Pane getContent() {
        Text textPaused = getUIFactoryService().newText("Paused", Color.WHITE, 80.0);
        Text textMusic = getUIFactoryService().newText("Music", Color.WHITE, 40.0);
        Pane musicSlider = createSlider(musicVolume);
        Text textSounds = getUIFactoryService().newText("Sounds", Color.WHITE, 40.0);
        Pane soundSlider = createSlider(soundVolume);
        Pane buttonResume = createActionButton("Apply & Resume", 300.0, 64.0, 32.0, () -> {
            getSettings().setGlobalMusicVolume(musicVolume.get());
            getSettings().setGlobalSoundVolume(soundVolume.get());
            fireResume();
        });
        Pane buttonQuit = createActionButton("Quit", 300.0, 64.0, 32.0, () -> {
            getAudioPlayer().stopMusic(MUSIC_GAME);
            getAudioPlayer().loopMusic(MUSIC_MENU);
            getGameController().gotoMainMenu();
        });

        VBox vBox = new VBox(24.0, textPaused, textMusic, musicSlider, textSounds, soundSlider, buttonResume, buttonQuit);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(getAppWidth(), getAppHeight());
        return vBox;
    }
}
