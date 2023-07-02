package com.lgiacchetta.dungeon.menu;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import javafx.geometry.Pos;
import javafx.scene.control.Slider;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.lgiacchetta.dungeon.Utils.*;

public class GameMenu extends FXGLMenu {

    public GameMenu() {
        super(MenuType.GAME_MENU);

        Rectangle shadow = new Rectangle(getAppWidth(), getAppHeight(),
                Color.color(0, 0, 0, 0.6));

        Rectangle bg = new Rectangle(800.0, 600.0, Color.BLACK);
        bg.setStroke(Color.YELLOW);
        bg.setStrokeWidth(4.0);
        bg.setEffect(new DropShadow(64.0, Color.color(0,0,0, 0.9)));

        Text textPaused = getUIFactoryService().newText("Paused", Color.WHITE, 80.0);

        Text textMusic = getUIFactoryService().newText("Music", Color.WHITE, 40.0);
        Slider sliderMusic = createSlider(getSettings().getGlobalMusicVolume());
        HBox hBoxMusic = new HBox(20.0, textMusic, sliderMusic);
        hBoxMusic.setAlignment(Pos.CENTER);

        Text textSounds = getUIFactoryService().newText("Sounds", Color.WHITE, 40.0);
        Slider sliderSounds = createSlider(getSettings().getGlobalMusicVolume());
        HBox hBoxSounds = new HBox(20.0, textSounds, sliderSounds);
        hBoxSounds.setAlignment(Pos.CENTER);

        Pane buttonResume = createActionButton("Apply & Resume", 300.0, 64.0, 32.0,
                () -> {
                    getSettings().setGlobalMusicVolume(sliderMusic.getValue());
                    getSettings().setGlobalSoundVolume(sliderSounds.getValue());
                    fireResume();
                });
        Pane buttonQuit = createActionButton("Quit", 300.0, 64.0, 32.0,
                () -> {
                    getGameController().gotoMainMenu();
                    getAudioPlayer().stopMusic(musicGame);
                    getAudioPlayer().loopMusic(musicMenu);
                });

        VBox vBox = new VBox(40.0,
                textPaused,
                hBoxMusic,
                hBoxSounds,
                buttonResume,
                buttonQuit);
        vBox.setAlignment(Pos.CENTER);

        getContentRoot().getChildren().addAll(new StackPane(shadow, bg, vBox));
    }

    private Slider createSlider(double value) {
        Slider slider = new Slider(0.0, 1.0, value);
        slider.getStylesheets().add("assets/style.css");
        return slider;
    }
}
