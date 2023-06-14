package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;

import static com.lgiacchetta.dungeon.Utils.*;

public class LivingEntityComponent extends Component {
    private PhysicsComponent physics;
    private final AnimatedTexture texture;
    private final AnimationChannel idle, walk;
    private final double velocity;
    private final double maxHealth;
    private double health;
    private final double damageCooldown;
    private double lastDamaged;
    private final double teleportCooldown;
    private double lastTeleported;

    public LivingEntityComponent(String idleAsset, int idleFrames, String walkAsset,
                                 int walkFrames, double velocity, double maxHealth, double damageCooldown,
                                 double teleportCooldown) {
        idle = getAnimation(idleAsset, idleFrames, idleAnimationDuration);
        walk = getAnimation(walkAsset, walkFrames, walkAnimationDuration);

        texture = new AnimatedTexture(idle);
        texture.loop();

        this.velocity = velocity;
        this.health = this.maxHealth = maxHealth;
        this.damageCooldown = damageCooldown;
        this.teleportCooldown = teleportCooldown;
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

    public void decHealth(double damage) {
        double now = FXGL.getGameTimer().getNow();
        if (now - lastDamaged >= damageCooldown) {
            lastDamaged = now;
            health -= damage;
        }
    }

    public void incHealth(double restore) {
        health += restore;
    }

    public void resetHealth() {
        health = maxHealth;
    }

    public void teleport(Point2D location) {
        double now = FXGL.getGameTimer().getNow();
        if (now - lastTeleported >= teleportCooldown) {
            lastTeleported = now;
            physics.overwritePosition(location);
        }
    }
}
