package com.lgiacchetta.dungeon.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;
import javafx.geometry.Point2D;
import javafx.util.Duration;

import static com.almasb.fxgl.dsl.FXGL.getGameTimer;
import static com.almasb.fxgl.dsl.FXGL.play;
import static com.lgiacchetta.dungeon.Utils.getAnimationChannel;

/**
 * Player Component added to player entities.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see Component
 */
public class PlayerComponent extends Component {
    private static final double DAMAGE_COOLDOWN = 1.0;
    private static final double TELEPORT_COOLDOWN = 4.0;
    private final AnimatedTexture texture;
    private final AnimationChannel idle;
    private final AnimationChannel walk;
    private final double velocity = 100.0;
    /** Doesn't need to be initialized, it's added during entity generation. */
    private PhysicsComponent physics;
    /** Doesn't need to be initialized, it's added during entity generation. */
    private HealthDoubleComponent health;
    private double lastDamaged;
    private double lastTeleported;

    /**
     * Initializes PlayerComponent.
     *
     * @param idleAsset string to retrieve idle animated texture
     * @param walkAsset string to retrieve walk animated texture
     */
    public PlayerComponent(String idleAsset, String walkAsset) {
        lastDamaged = lastTeleported = getGameTimer().getNow();
        idle = getAnimationChannel(idleAsset, 4, 1.0);
        walk = getAnimationChannel(walkAsset, 4, 0.5);
        texture = new AnimatedTexture(idle);
        texture.loop();
    }

    /**
     * Adds animated texture when added.
     */
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    /**
     * Updates animated texture.
     *
     * @param tpf time per frame
     */
    @Override
    public void onUpdate(double tpf) {
        if (physics.isMovingX() || physics.isMovingY()) {
            if (texture.getAnimationChannel() != walk) texture.loopAnimationChannel(walk);
        } else {
            if (texture.getAnimationChannel() != idle) texture.loopAnimationChannel(idle);
        }
    }

    /**
     * Player moving upwards.
     */
    public void up() {
        physics.setVelocityY(-velocity);
    }

    /**
     * Player moving leftwards, rotates texture to left.
     */
    public void left() {
        getEntity().setScaleX(-1); // rotate to left
        physics.setVelocityX(-velocity);
    }

    /**
     * Player moving rightwards, rotates texture to right.
     */
    public void right() {
        getEntity().setScaleX(1); // rotate to right
        physics.setVelocityX(velocity);
    }

    /**
     * Player moving downwards.
     */
    public void down() {
        physics.setVelocityY(velocity);
    }

    /**
     * Player stopping.
     */
    public void stop() {
        physics.setVelocityX(0);
        physics.setVelocityY(0);
    }

    /**
     * Damages player with specified amount, only if not damaged in the last DAMAGE_COOLDOWN seconds, plays texture damage effect, plays damage sound.
     *
     * @param amount amount of damage inflicted to player
     * @see FXGL#getGameTimer()
     * @see FXGL#play(String)
     */
    public void damage(double amount) {
        double now = getGameTimer().getNow();
        if (now - lastDamaged >= DAMAGE_COOLDOWN) {
            lastDamaged = now;
            health.damage(amount);
            getGameTimer().runAtInterval(() -> texture.setVisible(!texture.isVisible()), Duration.seconds(0.1), 8);
            play("damage.wav");
        }
    }

    /**
     * Heals player with the specified amount, plays texture heal effect, plays heal sound.
     *
     * @param amount amount of health restored to player
     * @see FXGL#getGameTimer()
     * @see FXGL#play(String)
     */
    public void restore(double amount) {
        health.restore(amount);
        getGameTimer().runAtInterval(() -> texture.setVisible(!texture.isVisible()), Duration.seconds(0.1), 8);
        play("heal.wav");
    }

    /**
     * Teleports player to specified location, only if not teleported in the last TELEPORT_COOLDOWN seconds, plays teleport sound.
     *
     * @param location specified location
     */
    public void teleport(Point2D location) {
        double now = getGameTimer().getNow();
        if (now - lastTeleported >= TELEPORT_COOLDOWN) {
            lastTeleported = now;
            physics.overwritePosition(location);
            play("ladder.wav");
        }
    }
}
