package com.lgiacchetta.dungeon.scene;

import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import static com.almasb.fxgl.dsl.FXGL.getUIFactoryService;
import static com.almasb.fxgl.dsl.FXGL.image;
import static com.lgiacchetta.dungeon.Utils.MAIN_LOADING_CHARACTERS;
import static com.lgiacchetta.dungeon.Utils.getAnimationChannel;

public class MainLoadingScene extends LoadingScene {
    public MainLoadingScene() {
        Text textLoading = getUIFactoryService().newText(
                "LOADING", Color.WHITE, 104.0);
        textLoading.setStroke(Color.BLACK);
        textLoading.setStrokeWidth(2.0);

        HBox hBox = new HBox(48.0);
        hBox.setAlignment(Pos.CENTER);
        MAIN_LOADING_CHARACTERS.stream()
                .map(asset -> new AnimatedTexture(getAnimationChannel(asset, 4, 0.5)))
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
