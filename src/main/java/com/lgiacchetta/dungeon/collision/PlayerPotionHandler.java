package com.lgiacchetta.dungeon.collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lgiacchetta.dungeon.EntityType;
import com.lgiacchetta.dungeon.component.PlayerComponent;

/**
 * Collision handler between player and potion.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see CollisionHandler
 */
public class PlayerPotionHandler extends CollisionHandler {

    /**
     * Initializes PlayerPotionHandler.
     */
    public PlayerPotionHandler() {
        super(EntityType.PLAYER, EntityType.POTION);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        if (!a.hasComponent(PlayerComponent.class)) return;
        a.getComponent(PlayerComponent.class).restore(1.0);
        b.removeFromWorld();
    }
}
