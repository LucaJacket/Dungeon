package com.lgiacchetta.dungeon;

import com.almasb.fxgl.entity.Entity;

import static com.lgiacchetta.dungeon.Utils.*;

public class MobComponent extends LivingEntityComponent {
    private Entity player;
    private final double attackRange;
    public MobComponent(String idleAsset, int idleFrames, String walkAsset, int walkFrames) {
        super(idleAsset, idleFrames, walkAsset, walkFrames, mobVelocity, 0, 0, 0);
        this.attackRange = mobAttackRange;
    }

    @Override
    public void onUpdate(double tpf) {
        if (player == null)
            player = entity.getWorld().getSingletonOptional(EntityType.PLAYER).orElse(null);
        else
            moveAI();
    }

    private void moveAI() {
        Entity mob = entity;
        if (mob.isColliding(player)) {
            stop();
            // damage
        }
        else if (mob.distanceBBox(player) < attackRange) {
            if (mob.getX() > player.getX() + 16) // smooth
                left();
            else if (mob.getX() < player.getX() - 16)
                right();
            if (mob.getY() > player.getY() + 16)
                up();
            else if (mob.getY() < player.getY() - 16)
                down();
        }
        else {
            stop();
        }
    }
}
