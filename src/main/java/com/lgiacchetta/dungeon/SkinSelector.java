package com.lgiacchetta.dungeon;

import com.almasb.fxgl.texture.AnimatedTexture;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;
import static com.lgiacchetta.dungeon.Utils.HEROES;
import static com.lgiacchetta.dungeon.Utils.getAnimationChannel;

public class SkinSelector extends HBox {
    private final AnimatedTexture texture;
    private final StringProperty asset;
    private int index;

    public SkinSelector(StringProperty asset) {
        super(40.0);
        this.asset = asset;
        this.index = HEROES.indexOf(asset.get());

        texture = new AnimatedTexture(getAnimationChannel(asset.get(), 4, 0.5));
        texture.loop();
        texture.setScaleX(3.0);
        texture.setScaleY(3.0);
        getSceneService().getCurrentScene().addListener(texture);

        Polygon rightArrow = new Polygon(0.0, 0.0, 0.0, 20.0, 20.0, 10.0);
        rightArrow.fillProperty().bind(
                Bindings.when(rightArrow.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));
        rightArrow.setOnMouseClicked(event -> next());

        Polygon leftArrow = new Polygon(20.0, 0.0, 20.0, 20.0, 0.0, 10.0);
        leftArrow.fillProperty().bind(
                Bindings.when(leftArrow.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));
        leftArrow.setOnMouseClicked(event -> prev());

        getChildren().addAll(leftArrow, texture, rightArrow);
        setAlignment(Pos.CENTER);
    }

    private void next() {
        index = (index + 1) % HEROES.size();
        asset.set(HEROES.get(index));
        texture.loopAnimationChannel(getAnimationChannel(asset.get(), 4, 0.5));
    }

    private void prev() {
        index = (index + HEROES.size() - 1) % HEROES.size();
        asset.set(HEROES.get(index));
        texture.loopAnimationChannel(getAnimationChannel(asset.get(), 4, 0.5));
    }
}
