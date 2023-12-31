package com.lgiacchetta.dungeon;

import com.almasb.fxgl.audio.Music;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Slider;
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

/**
 * Utils class for constants and utility methods.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 */
public class Utils {
    /** List of available skins for players. */
    public static final List<String> HEROES = List.of(
            "hero/knight_m_idle_anim_f",
            "hero/knight_f_idle_anim_f",
            "hero/dwarf_m_idle_anim_f",
            "hero/dwarf_f_idle_anim_f",
            "hero/elf_m_idle_anim_f",
            "hero/elf_f_idle_anim_f",
            "hero/lizard_m_idle_anim_f",
            "hero/lizard_f_idle_anim_f",
            "hero/wizzard_m_idle_anim_f",
            "hero/wizzard_f_idle_anim_f");
    /** List of textures used in the main loading scene. */
    public static final List<String> MAIN_LOADING_CHARACTERS = List.of(
            "orc/boss/ogre_run_anim_f",
            "demon/boss/big_demon_run_anim_f",
            "undead/boss/big_zombie_run_anim_f",
            "hero/knight_m_run_anim_f");
    /** Game music. */
    public static final Music MUSIC_GAME = getAssetLoader().loadMusic("melody.wav");
    /** Menu music. */
    public static final Music MUSIC_MENU = getAssetLoader().loadMusic("menu.wav");

    /**
     * Creates AnimationChannel for AnimatedTexture.
     *
     * @param filename initial part of file name
     * @param nFrames number of frames
     * @param seconds total duration of the animation in seconds
     * @return animation channel
     * @see AnimationChannel
     */
    public static AnimationChannel getAnimationChannel(String filename, int nFrames, double seconds) {
        List<Image> list = new ArrayList<>();
        for (int i = 0; i < nFrames; i++)
            list.add(image(filename + i + ".png"));
        return new AnimationChannel(list, Duration.seconds(seconds));
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
        Rectangle bg = new Rectangle(width, height, Color.BLACK);
        bg.setStrokeWidth(4.0);
        bg.setEffect(new DropShadow(8.0, Color.color(0, 0, 0, 0.9)));
        Text txt = getUIFactoryService().newText(text, Color.WHITE, fontSize);

        Pane pane = new StackPane(bg, txt);
        pane.setPrefSize(bg.getWidth(), bg.getHeight());
        pane.setOnMouseClicked(event -> onClick.run());

        bg.strokeProperty().bind(Bindings.when(pane.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));

        return pane;
    }

    /**
     * Creates custom slider, with applied CSS stylesheet, for UIs.
     *
     * @param value initial value for slider
     * @return slider
     * @see Slider
     */
    public static Slider createSlider(double value) {
        Slider slider = new Slider(0.0, 1.0, value);
        slider.getStylesheets().add("assets/style.css");
        return slider;
    }
}
