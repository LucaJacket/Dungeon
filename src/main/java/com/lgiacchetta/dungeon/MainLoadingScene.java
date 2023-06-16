package com.lgiacchetta.dungeon;

import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.ui.FontFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MainLoadingScene extends LoadingScene {
    public MainLoadingScene() {
        getRoot().setBackground(new Background(new BackgroundImage(  // Image by upklyak on Freepik
                FXGL.image("loading.jpg"), null, null, null, null)));

        Text textLoading = new Text("LOADING");
        textLoading.setFont(Utils.UIFont.newFont(104.0));
        textLoading.setFill(Color.WHITE);

        FXGL.centerText(textLoading, FXGL.getAppWidth() / 2.0, FXGL.getAppHeight() * 2.0 / 3.0);

        getContentRoot().getChildren().setAll(textLoading);

        String[] assets = {"orc/boss/ogre_run_anim_f", "demon/boss/big_demon_run_anim_f",
                "undead/boss/big_zombie_run_anim_f", "hero/knight_m_run_anim_f"};

        for (int i = 0; i < 4; i++) {
            AnimatedTexture texture = new AnimatedTexture(
                    Utils.getAnimation(assets[i], 4, 0.5)
            );
            texture.loop();
            this.addListener(texture);

            if (i == 3) { // just for the knight
                texture.setTranslateX(FXGL.getAppWidth() / 2.0 + 72.0);
                texture.setTranslateY(FXGL.getAppHeight() * 2.0 / 3.0 + 12.0);
            } else { // for everybody else
                texture.setTranslateX(FXGL.getAppWidth() / 2.0 + (i - 2) * 48.0);
                texture.setTranslateY(FXGL.getAppHeight() * 2.0 / 3.0);
            }
            texture.setScaleX(1.5);
            texture.setScaleY(1.5);

            getContentRoot().getChildren().add(texture);
        }
    }
}
