package com.lgiacchetta.dungeon;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.beans.binding.Bindings;
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

import static com.almasb.fxgl.dsl.FXGL.*;

public class Utils {
    public static final Music musicGame = getAssetLoader().loadMusic("melody.wav");
    public static final Music musicMenu = getAssetLoader().loadMusic("menu.wav");

    public static AnimationChannel getAnimation(String filename, int nFrames, double seconds) {
        List<Image> list = new ArrayList<>();
        for (int i = 0; i < nFrames; i++)
            list.add(image(filename + i + ".png"));
        return new AnimationChannel(list, Duration.seconds(seconds));
    }

    public static Pane createActionButton(String text, double width, double height, double fontSize,
                                          Runnable onClick) {
        Rectangle bg = new Rectangle(width, height, Color.BLACK);
        bg.setStrokeWidth(4.0);
        bg.setEffect(new DropShadow(8.0, Color.color(0,0,0, 0.9)));
        Text txt = getUIFactoryService().newText(text, Color.WHITE, fontSize);

        Pane pane = new StackPane(bg, txt);
        pane.setPrefSize(bg.getWidth(), bg.getHeight());
        pane.setOnMouseClicked(event -> onClick.run());

        bg.strokeProperty().bind(
                Bindings.when(pane.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));

        return pane;
    }
}
