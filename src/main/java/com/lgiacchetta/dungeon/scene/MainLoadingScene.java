package com.lgiacchetta.dungeon.scene;

import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.stream.Stream;

import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static com.lgiacchetta.dungeon.Utils.MAIN_BACKGROUND;
import static com.lgiacchetta.dungeon.Utils.createAnimationChannel;

/**
 * Scene that will be displayed when the game is loading.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see LoadingScene
 */
public class MainLoadingScene extends LoadingScene {
    /**
     * Initializes new MainLoadingScene.
     */
    public MainLoadingScene() {
        VBox vBox = new VBox(100.0, getTextLoading(), getRunningCharacters());
        vBox.setMinSize(getAppWidth(), getAppHeight());
        vBox.setMaxSize(getAppWidth(), getAppHeight());
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(MAIN_BACKGROUND);
        getContentRoot().getChildren().addAll(vBox);
    }

    private Text getTextLoading() {
        Text textLoading = getUIFactoryService().newText("LOADING", Color.WHITE, 104.0);
        textLoading.setStroke(Color.BLACK);
        textLoading.setStrokeWidth(2.0);
        return textLoading;
    }

    private Pane getRunningCharacters() {
        HBox hBox = new HBox(48.0);
        hBox.setAlignment(Pos.CENTER);
        Stream.of("orc/boss/ogre_run_anim_f",
                "demon/boss/big_demon_run_anim_f",
                "undead/boss/big_zombie_run_anim_f",
                "hero/knight_m_run_anim_f")
                .map(asset -> new AnimatedTexture(createAnimationChannel(asset, 4, 0.5)))
                .forEach(texture -> {
                    texture.loop();
                    this.addListener(texture);
                    texture.setScaleX(1.5);
                    texture.setScaleY(1.5);
                    hBox.getChildren().add(texture);
                });
        return hBox;
    }
}
