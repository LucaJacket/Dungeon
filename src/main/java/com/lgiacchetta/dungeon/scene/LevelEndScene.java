package com.lgiacchetta.dungeon.scene;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.scene.SubScene;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Duration;


import static com.almasb.fxgl.dsl.FXGL.*;
import static com.lgiacchetta.dungeon.Utils.MUSIC_GAME;
import static com.lgiacchetta.dungeon.Utils.createAnimationChannel;

/**
 * Scene that will be displayed when the players have completed the level, except for the final level.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see SubScene
 */
public class LevelEndScene extends DungeonScene {
    @Override
    protected void onPush() {
        getAudioPlayer().pauseMusic(MUSIC_GAME);
        play("level-win.wav");
    }

    @Override
    protected void onPop() {
        getAudioPlayer().resumeMusic(MUSIC_GAME);
        set("levelTime", 0.0);
        inc("level", 1);
    }

    private AnimatedTexture getPointIcon(int points, int count) {
        AnimatedTexture texture = new AnimatedTexture(points >= count ?
                createAnimationChannel("chest/chest_full_open_anim_f", 3, 1.0) :
                createAnimationChannel("chest/chest_full_open_anim_f", 1, 1.0));
        this.addListener(texture);
        return texture;
    }

    private HBox getPoints() {
        HBox hBox = new HBox(100.0);
        hBox.setPadding(new Insets(40.0));
        hBox.setAlignment(Pos.CENTER);
        int points = geti("points");
        for (int i = 1; i <= 3; i++) {
            AnimatedTexture pointIcon = getPointIcon(points, i);
            animatePointIcon(pointIcon, i);
            hBox.getChildren().add(pointIcon);
        }
        return hBox;
    }

    @Override
    protected Pane getContent() {
        Text textLevelCompleted = getUIFactoryService().newText("LEVEL COMPLETED", Color.YELLOW, 104.0);
        Text textContinue = getUIFactoryService().newText("Press Enter to continue", Color.WHITE, 32.0);
        animateText(textContinue);

        VBox vBox = new VBox(32.0, textLevelCompleted, getPoints(), textContinue);
        vBox.setAlignment(Pos.CENTER);
        vBox.setPrefSize(getAppWidth(), getAppHeight());
        return vBox;
    }

    private void animatePointIcon(AnimatedTexture texture, int count) {
        Animation<?> animation = animationBuilder()
                .duration(Duration.seconds(0.5))
                .delay(Duration.seconds(0.2 * count))
                .interpolator(Interpolators.ELASTIC.EASE_OUT())
                .onFinished(texture::play)
                .scale(texture)
                .from(new Point2D(0.0, 0.0))
                .to(new Point2D(5.0, 5.0))
                .build();
        this.addListener(animation);
        animations.add(animation);
    }
}
