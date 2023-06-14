package com.lgiacchetta.dungeon;

import static com.lgiacchetta.dungeon.Utils.*;

public class PlayerComponent extends LivingEntityComponent {
    public PlayerComponent(String idleAsset, int idleFrames, String walkAsset, int walkFrames) {
        super(idleAsset, idleFrames, walkAsset, walkFrames, playerVelocity, playerHealth, playerDamageCooldown, playerTeleportCooldown);
    }
}
