package com.lgiacchetta.dungeon.collision;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lgiacchetta.dungeon.EntityType;
import com.lgiacchetta.dungeon.component.PlayerComponent;


public class PlayerLadderHandler extends CollisionHandler {
    public PlayerLadderHandler() {
        super(EntityType.PLAYER, EntityType.LADDER);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        if (!a.hasComponent(PlayerComponent.class)) return;
        FXGL.getGameWorld().getEntitiesByType(EntityType.LADDER).stream()
                .filter(ladder -> !ladder.equals(b) &&
                        ladder.getProperties().getValue("connected") ==
                        b.getProperties().getValue("connected"))
                .forEach(ladder -> a.getComponent(PlayerComponent.class).teleport(ladder.getCenter()));
    }
}
