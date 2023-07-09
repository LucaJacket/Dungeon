package com.lgiacchetta.dungeon.collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lgiacchetta.dungeon.EntityType;
import com.lgiacchetta.dungeon.component.PlayerComponent;
import com.lgiacchetta.dungeon.component.SpikeComponent;

/**
 * Collision handler between player and spike.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see CollisionHandler
 */
public class PlayerSpikeHandler extends CollisionHandler {

    /**
     * Initializes PlayerSpikeHandler.
     */
    public PlayerSpikeHandler() {
        super(EntityType.PLAYER, EntityType.SPIKE);
    }

    @Override
    protected void onCollision(Entity a, Entity b) {
        if (!a.hasComponent(PlayerComponent.class) || !b.hasComponent(SpikeComponent.class)) return;
        if (b.getComponent(SpikeComponent.class).isDangerous()) a.getComponent(PlayerComponent.class).damage(0.5);
    }
}
