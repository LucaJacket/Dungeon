package com.lgiacchetta.dungeon;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Utils class for constants and utility methods.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 */
public class Utils {
    /** List of available skins for players. */
    public static final List<String> SKINS = List.of(
            "hero/knight_m",
            "hero/knight_f",
            "hero/dwarf_m",
            "hero/dwarf_f",
            "hero/elf_m",
            "hero/elf_f",
            "hero/lizard_m",
            "hero/lizard_f",
            "hero/wizzard_m",
            "hero/wizzard_f");
    /** Game music. */
    public static final Music MUSIC_GAME = getAssetLoader().loadMusic("melody.wav");
    /** Menu music. */
    public static final Music MUSIC_MENU = getAssetLoader().loadMusic("menu.wav");

    public static final Background MAIN_BACKGROUND = new Background(new BackgroundImage(  // Image by upklyak on Freepik
                image("loading.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT));

    /**
     * Creates AnimationChannel for AnimatedTexture.
     *
     * @param filename initial part of file name, to be extended with frame number and extension
     * @param frames number of frames
     * @param duration total duration of the animation in seconds
     * @return animation channel
     * @see AnimationChannel
     */
    public static AnimationChannel createAnimationChannel(String filename, int frames, double duration) {
        List<Image> list = new ArrayList<>();
        for (int i = 0; i < frames; i++)
            list.add(image(filename + i + ".png"));
        return new AnimationChannel(list, Duration.seconds(duration));
    }

    public static Rectangle createRectangle(double width, double height, Color stroke) {
        Rectangle rectangle = new Rectangle(width, height, Color.BLACK);
        rectangle.setStroke(stroke);
        rectangle.setStrokeWidth(4.0);
        rectangle.setEffect(new DropShadow(8.0, Color.color(0, 0, 0, 0.9)));
        return rectangle;
    }

    /**
     * Creates action button for UIs.
     *
     * @param text text inside button
     * @param width button width
     * @param height button height
     * @param fontSize text font size
     * @param onClick action to be performed on button click
     * @return button
     * @see Pane
     */
    public static Pane createActionButton(String text, double width, double height, double fontSize, Runnable onClick) {
        Rectangle bg = createRectangle(width, height, Color.YELLOW);
        Text txt = getUIFactoryService().newText(text, Color.WHITE, fontSize);
        Pane pane = new StackPane(bg, txt);
        pane.setMinSize(bg.getWidth(), bg.getHeight());
        pane.setMaxSize(bg.getWidth(), bg.getHeight());
        pane.setOnMouseClicked(event -> onClick.run());
        bg.strokeProperty().bind(Bindings.when(pane.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));
        return pane;
    }

    /**
     * Creates custom slider, with applied CSS stylesheet, for UIs.
     *
     * @param property DoubleProperty bound to the slider
     * @return slider
     * @see DoubleProperty
     */
    public static Pane createSlider(DoubleProperty property) {
        Rectangle bar = createRectangle(200.0, 24.0, Color.YELLOW);
        Rectangle thumb = createRectangle(24.0, 40.0, Color.YELLOW);
        thumb.strokeProperty().bind(Bindings.when(thumb.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));
        thumb.setOnMouseDragged(event -> {
            double endTranslation = thumb.getTranslateX() + event.getX() - 12.0;
            if (endTranslation < 0) endTranslation = 0;
            else if (endTranslation > 176.0) endTranslation = 176.0;
            thumb.setTranslateX(endTranslation);
        });
        StackPane slider = new StackPane(bar, thumb);
        slider.setMinSize(bar.getWidth(), thumb.getHeight());
        slider.setMaxSize(bar.getWidth(), thumb.getHeight());
        slider.setAlignment(Pos.CENTER_LEFT);
        thumb.setTranslateX(property.get() * 176.0);
        property.bind(thumb.translateXProperty().divide(176.0));
        return slider;
    }
}
