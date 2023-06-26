package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static com.lgiacchetta.dungeon.Utils.getAnimation;

public class SkinSelector extends HBox {
    private final String[] assets;
    private int skinIndex;
    private final AnimatedTexture texture;

    public SkinSelector(int skinIndex, String[] assets) {
        super(40.0);
        this.skinIndex = skinIndex;
        this.assets = assets;

        super.setAlignment(Pos.CENTER);

        texture = new AnimatedTexture(getAnimation(assets[skinIndex], 4, 0.5));
        texture.loop();
        FXGL.getSceneService().getCurrentScene().addListener(texture);
        texture.setScaleX(3.0);
        texture.setScaleY(3.0);

        Polygon rightArrow = new Polygon(0.0, 0.0, 0.0, 20.0, 20.0, 10.0);
        rightArrow.setFill(Color.YELLOW);

        rightArrow.setOnMouseClicked(event -> next());
        rightArrow.setOnMouseEntered(event -> rightArrow.setFill(Color.GREEN));
        rightArrow.setOnMouseExited(event -> rightArrow.setFill(Color.YELLOW));

        Polygon leftArrow = new Polygon(20.0, 0.0, 20.0, 20.0, 0.0, 10.0);
        leftArrow.setFill(Color.YELLOW);

        leftArrow.setOnMouseClicked(event -> prev());
        leftArrow.setOnMouseEntered(event -> leftArrow.setFill(Color.GREEN));
        leftArrow.setOnMouseExited(event -> leftArrow.setFill(Color.YELLOW));

        super.getChildren().addAll(leftArrow, texture, rightArrow);
    }

    private void next() {
        skinIndex = (skinIndex + 1) % assets.length;
        texture.loopAnimationChannel(getAnimation(assets[skinIndex], 4, 0.5));
    }

    private void prev() {
        skinIndex = (skinIndex + (assets.length - 1)) % assets.length;
        texture.loopAnimationChannel(getAnimation(assets[skinIndex], 4, 0.5));
    }

    public int getSkinIndex() {
        return skinIndex;
    }
}
