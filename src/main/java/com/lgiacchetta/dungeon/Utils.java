package com.lgiacchetta.dungeon;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.ui.FontFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final String[] heroes = {
            "hero/knight_m_idle_anim_f",
            "hero/knight_f_idle_anim_f",
            "hero/dwarf_m_idle_anim_f",
            "hero/dwarf_f_idle_anim_f",
            "hero/elf_m_idle_anim_f",
            "hero/elf_f_idle_anim_f",
            "hero/lizard_m_idle_anim_f",
            "hero/lizard_f_idle_anim_f",
            "hero/wizzard_m_idle_anim_f",
            "hero/wizzard_f_idle_anim_f",
    };
    public static final FontFactory UIFont = FXGL.getAssetLoader().loadFont("alagard.ttf");
    public static final Music musicGame = FXGL.getAssetLoader().loadMusic("melody.wav");
    public static final Music musicMenu = FXGL.getAssetLoader().loadMusic("menu.wav");

    public static AnimationChannel getAnimation(String filename, int nFrames, double seconds) {
        List<Image> list = new ArrayList<>();
        for (int i = 0; i < nFrames; i++) {
            list.add(FXGL.image(filename + i + ".png"));
        }
        return new AnimationChannel(list, Duration.seconds(seconds));
    }

    public static Pane createActionButton(String text, double width, double height, double fontSize,
                                          Runnable onClick) {
        Rectangle bg = new Rectangle(width, height, Color.BLACK);
        bg.setStroke(Color.YELLOW);
        bg.setStrokeWidth(4.0);
        bg.setEffect(new DropShadow(8.0, Color.color(0,0,0, 0.9)));

        Text txt = new Text(text);
        txt.setFont(Utils.UIFont.newFont(fontSize));
        txt.setFill(Color.WHITE);

        Pane pane = new StackPane(bg, txt);
        pane.setMaxSize(bg.getWidth(), bg.getHeight());
        pane.setOnMouseEntered(event -> bg.setStroke(Color.GREEN));
        pane.setOnMouseExited(event -> bg.setStroke(Color.YELLOW));
        pane.setOnMouseClicked(event -> onClick.run());

        return pane;
    }
}
