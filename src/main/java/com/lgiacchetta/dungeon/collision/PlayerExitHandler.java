package com.lgiacchetta.dungeon.collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.lgiacchetta.dungeon.EntityType;
import com.lgiacchetta.dungeon.scene.LevelEndScene;

/**
 * Collision handler between player and exit door.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see CollisionHandler
 */
public class PlayerExitHandler extends CollisionHandler {
    /**
     * Initializes PlayerExitHandler.
     */
    public PlayerExitHandler() {
        super(EntityType.PLAYER, EntityType.EXIT);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        new LevelEndScene().push();
    }
}
