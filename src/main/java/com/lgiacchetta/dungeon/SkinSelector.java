package com.lgiacchetta.dungeon;

import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.beans.binding.Bindings;
import javafx.beans.property.IntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;
import static com.lgiacchetta.dungeon.Utils.HEROES;
import static com.lgiacchetta.dungeon.Utils.getAnimationChannel;

/**
 * HBox to dynamically choose skin.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see HBox
 */
public class SkinSelector extends HBox {
    /**
     * Initializes SkinSelector.
     *
     * @param skinIndex initial index of chosen skin
     */
    public SkinSelector(IntegerProperty skinIndex) {
        super(40.0);

        AnimatedTexture texture = new AnimatedTexture(getAnimationChannel(HEROES.get(skinIndex.get()), 4, 0.5));
        texture.loop();
        texture.setScaleX(3.0);
        texture.setScaleY(3.0);
        getSceneService().getCurrentScene().addListener(texture);

        Polygon rightArrow = new Polygon(0.0, 0.0, 0.0, 20.0, 20.0, 10.0);
        rightArrow.fillProperty().bind(Bindings.when(rightArrow.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));
        rightArrow.setOnMouseClicked(event -> {
            skinIndex.set(skinIndex.get() + 1);
            if (skinIndex.greaterThanOrEqualTo(HEROES.size()).get()) skinIndex.set(0);
            texture.loopAnimationChannel(getAnimationChannel(HEROES.get(skinIndex.get()), 4, 0.5));
        });

        Polygon leftArrow = new Polygon(20.0, 0.0, 20.0, 20.0, 0.0, 10.0);
        leftArrow.fillProperty().bind(Bindings.when(leftArrow.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));
        leftArrow.setOnMouseClicked(event -> {
            skinIndex.set(skinIndex.get() - 1);
            if (skinIndex.lessThan(0).get()) skinIndex.set(HEROES.size() - 1);
            texture.loopAnimationChannel(getAnimationChannel(HEROES.get(skinIndex.get()), 4, 0.5));
        });

        getChildren().addAll(leftArrow, texture, rightArrow);
        setAlignment(Pos.CENTER);
    }
}
