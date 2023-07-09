package com.lgiacchetta.dungeon.collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lgiacchetta.dungeon.EntityType;
import com.lgiacchetta.dungeon.component.PlayerComponent;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;

/**
 * Collision handler between player and ladder.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see CollisionHandler
 */
public class PlayerLadderHandler extends CollisionHandler {

    /**
     * Initializes PlayerLadderHandler.
     */
    public PlayerLadderHandler() {
        super(EntityType.PLAYER, EntityType.LADDER);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        if (!a.hasComponent(PlayerComponent.class)) return;
        getGameWorld().getEntitiesByType(EntityType.LADDER).stream()
                .filter(ladder -> !ladder.equals(b) &&
                        ladder.getProperties().getValue("connected") ==
                                b.getProperties().getValue("connected"))
                .forEach(ladder -> a.getComponent(PlayerComponent.class).teleport(ladder.getCenter()));
    }
}
