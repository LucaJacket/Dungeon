package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.lgiacchetta.dungeon.Utils.*;

public class PlayerComponent extends Component {
    private PhysicsComponent physics;
    private final AnimatedTexture texture;
    private final AnimationChannel idle;
    private final AnimationChannel walk;
    private final double velocity = 100.0;
    private double health = 3.0;
    private double lastDamaged;
    private double lastTeleported;

    public PlayerComponent(String idleAsset, String walkAsset) {
        idle = getAnimation(idleAsset, 4, 1.0);
        walk = getAnimation(walkAsset, 4, 0.5);

        texture = new AnimatedTexture(idle);
        texture.loop();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
        lastDamaged = lastTeleported = FXGL.getGameTimer().getNow();
    }

    @Override
    public void onUpdate(double tpf) {
        if (physics.isMovingX() || physics.isMovingY()) {
            if (texture.getAnimationChannel() != walk) {
                texture.loopAnimationChannel(walk);
            }
        } else {
            if (texture.getAnimationChannel() != idle) {
                texture.loopAnimationChannel(idle);
            }
        }
    }

    public void up() { physics.setVelocityY(-velocity); }

    public void left() {
        getEntity().setScaleX(-1); // rotate to left
        physics.setVelocityX(-velocity);
    }

    public void right() {
        getEntity().setScaleX(1); // rotate to right
        physics.setVelocityX(velocity);
    }

    public void down() { physics.setVelocityY(velocity); }

    public void stop() {
        physics.setVelocityX(0);
        physics.setVelocityY(0);
    }

    public double getHealth() {
        return health;
    }

    public void damage(double damage) {
        double now = FXGL.getGameTimer().getNow();
        if (now - lastDamaged >= 1.0) { // damage cooldown
            lastDamaged = now;
            health = Math.max(health - damage, 0.0);
            FXGL.getGameTimer().runAtInterval(() -> texture.setVisible(!texture.isVisible()),
                    Duration.seconds(0.1), 10);
            FXGL.play("damage.wav");
        }
    }

    public void teleport(Point2D location) {
        double now = FXGL.getGameTimer().getNow();
        if (now - lastTeleported >= 4.0) { // teleport cooldown
            lastTeleported = now;
            physics.overwritePosition(location);
            FXGL.play("ladder.wav");
        }
    }
}
