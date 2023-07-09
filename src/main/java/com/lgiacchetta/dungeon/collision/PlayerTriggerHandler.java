package com.lgiacchetta.dungeon.collision;

import com.almasb.fxgl.animation.Animation;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lgiacchetta.dungeon.EntityType;
import com.lgiacchetta.dungeon.component.PlayerComponent;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Collision handler between player and trigger.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see CollisionHandler
 */
public class PlayerTriggerHandler extends CollisionHandler {

    /**
     * Initializes PlayerTriggerHandler.
     */
    public PlayerTriggerHandler() {
        super(EntityType.PLAYER, EntityType.TRIGGER);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        if (!a.hasComponent(PlayerComponent.class)) return;
        Text txt = getUIFactoryService().newText(
                b.getProperties().getString("message"), Color.WHITE, 32.0);
        txt.setWrappingWidth(280.0);
        txt.setTextAlignment(TextAlignment.CENTER);

        Rectangle bg = new Rectangle(400.0, 200.0, Color.BLACK);
        bg.setStroke(Color.YELLOW);
        bg.setStrokeWidth(4.0);
        bg.setEffect(new DropShadow(64.0, Color.color(0, 0, 0, 0.9)));

        Pane prompt = new StackPane(bg, txt);
        prompt.setTranslateX(getAppWidth() - 420.0);
        prompt.setTranslateY(getAppHeight() - 220.0);

        getGameScene().addUINode(prompt);

        Animation<?> animation = animationBuilder()
                .onFinished(() -> getGameScene().removeUINodes(prompt))
                .buildSequence(
                        animationBuilder()
                                .duration(Duration.seconds(0.5))
                                .fadeIn(prompt)
                                .build(),
                        animationBuilder() // (does nothing)
                                .duration(Duration.seconds(5))
                                .scale(prompt)
                                .build(),
                        animationBuilder()
                                .duration(Duration.seconds(0.5))
                                .fadeOut(prompt)
                                .build());
        getGameScene().addListener(animation);
        animation.start();

        b.removeFromWorld();
    }
}
