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
    private final Runnable onLevelEnded;

    /**
     * Initializes PlayerExitHandler.
     *
     * @param onLevelEnded called when collision begins
     */
    public PlayerExitHandler(Runnable onLevelEnded) {
        super(EntityType.PLAYER, EntityType.EXIT);
        this.onLevelEnded = onLevelEnded;
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        new LevelEndScene(onLevelEnded).onLevelEnd();
    }
}
