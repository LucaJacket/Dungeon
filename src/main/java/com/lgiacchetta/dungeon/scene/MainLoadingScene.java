package com.lgiacchetta.dungeon.scene;

import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import java.util.Arrays;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.lgiacchetta.dungeon.Utils.*;

public class MainLoadingScene extends LoadingScene {
    public MainLoadingScene() {
        Text textLoading = getUIFactoryService().newText(
                "LOADING", Color.WHITE, 104.0);
        textLoading.setStroke(Color.BLACK);
        textLoading.setStrokeWidth(2.0);

        HBox hBox = new HBox(48.0);
        hBox.setAlignment(Pos.CENTER);
        Arrays.stream(new String[]{
                        "orc/boss/ogre_run_anim_f",
                        "demon/boss/big_demon_run_anim_f",
                        "undead/boss/big_zombie_run_anim_f",
                        "hero/knight_m_run_anim_f"})
                .map(asset -> new AnimatedTexture(getAnimation(asset, 4, 0.5)))
                .forEach(texture -> {
                    texture.loop();
                    this.addListener(texture);
                    texture.setScaleX(1.5);
                    texture.setScaleY(1.5);
                    hBox.getChildren().add(texture);
                });

        VBox vBox = new VBox(100.0,
                textLoading,
                hBox);
        vBox.setPrefSize(getAppWidth(), getAppHeight());
        vBox.setAlignment(Pos.CENTER);
        vBox.setBackground(new Background(new BackgroundImage(  // Image by upklyak on Freepik
                image("loading.jpg"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT)));

        getContentRoot().getChildren().addAll(vBox);
    }
}
