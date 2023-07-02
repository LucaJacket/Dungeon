package com.lgiacchetta.dungeon;

import com.almasb.fxgl.texture.AnimatedTexture;
import com.lgiacchetta.dungeon.circularlinkedlist.CircularList;
import com.lgiacchetta.dungeon.circularlinkedlist.CircularListNode;
import javafx.beans.binding.Bindings;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import static com.almasb.fxgl.dsl.FXGL.getSceneService;
import static com.lgiacchetta.dungeon.Utils.getAnimation;

public class SkinSelector extends HBox {
    private final AnimatedTexture texture;
    private CircularListNode<String> hero;

    public SkinSelector(StringProperty asset) {
        super(40.0);

        CircularList<String> heroes = new CircularList<>(
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

        hero = heroes.findNode(asset.get());

        texture = new AnimatedTexture(getAnimation(hero.getData(), 4, 0.5));
        texture.loop();
        texture.setScaleX(3.0);
        texture.setScaleY(3.0);
        getSceneService().getCurrentScene().addListener(texture);

        Polygon rightArrow = new Polygon(0.0, 0.0, 0.0, 20.0, 20.0, 10.0);
        rightArrow.fillProperty().bind(
                Bindings.when(rightArrow.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));
        rightArrow.setOnMouseClicked(event -> {
            hero = hero.getNext();
            texture.loopAnimationChannel(getAnimation(hero.getData(), 4, 0.5));
            asset.set(hero.getData());
        });

        Polygon leftArrow = new Polygon(20.0, 0.0, 20.0, 20.0, 0.0, 10.0);
        leftArrow.fillProperty().bind(
                Bindings.when(leftArrow.hoverProperty()).then(Color.GREEN).otherwise(Color.YELLOW));
        leftArrow.setOnMouseClicked(event -> {
            hero = hero.getPrev();
            texture.loopAnimationChannel(getAnimation(hero.getData(), 4, 0.5));
            asset.set(hero.getData());
        });

        getChildren().addAll(leftArrow, texture, rightArrow);
        setAlignment(Pos.CENTER);
    }
}
