package com.lgiacchetta.dungeon.collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lgiacchetta.dungeon.EntityType;
import com.lgiacchetta.dungeon.component.PlayerComponent;

/**
 * Collision handler between player and player.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see CollisionHandler
 */
public class PlayerPlayerHandler extends CollisionHandler {

    /**
     * Initializes PlayerPlayerHandler.
     */
    public PlayerPlayerHandler() {
        super(EntityType.PLAYER, EntityType.PLAYER);
    }

    @Override
    protected void onCollisionEnd(Entity a, Entity b) {
        if (!a.hasComponent(PlayerComponent.class) || !b.hasComponent(PlayerComponent.class)) return;
        a.getComponent(PlayerComponent.class).stop();
        b.getComponent(PlayerComponent.class).stop();
    }
}
