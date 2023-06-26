package com.lgiacchetta.dungeon;

import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.MenuType;
import com.almasb.fxgl.dsl.FXGL;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import static com.lgiacchetta.dungeon.Utils.*;

public class GameMenu extends FXGLMenu {
    public GameMenu() {
        super(MenuType.GAME_MENU);

        Rectangle bg = new Rectangle(FXGL.getAppWidth(), getAppHeight(),
                Color.color(0, 0, 0, 0.6));

        Rectangle bgMenu = new Rectangle(600.0, 500.0, Color.BLACK);
        bgMenu.setStroke(Color.YELLOW);
        bgMenu.setStrokeWidth(4.0);
        bgMenu.setEffect(new DropShadow(64.0, Color.color(0,0,0, 0.9)));

        Text textPause = new Text("Pause");
        textPause.setFont(Utils.UIFont.newFont(80.0));
        textPause.setFill(Color.WHITE);

        Pane buttonResume = createActionButton("Resume", 300.0, 64.0, 32.0,
                this::fireResume);
        Pane buttonQuit = createActionButton("Quit", 300.0, 64.0, 32.0,
                () -> {
                    FXGL.getGameController().gotoMainMenu();
                    FXGL.getAudioPlayer().stopMusic(musicGame);
                    FXGL.getAudioPlayer().loopMusic(musicMenu);
                });

        VBox vbox = new VBox(40.0,
                textPause,
                buttonResume,
                buttonQuit);
        vbox.setAlignment(Pos.CENTER);

        getContentRoot().getChildren().setAll(new StackPane(bg, bgMenu, vbox));
    }
}
