package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class DungeonFactory implements EntityFactory {
    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.WALL)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }
/*
    @Spawns("orc_boss")
    public Entity newOrcBoss(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC); // AI controlled

        return entityBuilder(data)
                .type(EntityType.ORC_BOSS)
                .bbox(new HitBox(new Point2D(6, 10), BoundingShape.box(20, 26)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new MobComponent(
                        "orc/boss/ogre_idle_anim_f", 4,
                        "orc/boss/ogre_run_anim_f", 4
                ))
                .build();
    }

    @Spawns("orc_normal")
    public Entity newOrcNormal(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC); // AI controlled

        return entityBuilder(data)
                .type(EntityType.ORC_NORMAL)
                .bbox(new HitBox(new Point2D(4, 7), BoundingShape.box(10, 16)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new MobComponent(
                        "orc/normal/orc_warrior_idle_anim_f", 4,
                        "orc/normal/orc_warrior_run_anim_f", 4
                ))
                .build();
    }

    @Spawns("orc_tiny")
    public Entity newOrcTiny(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC); // AI controlled

        return entityBuilder(data)
                .type(EntityType.ORC_TINY)
                .bbox(new HitBox(new Point2D(4, 6), BoundingShape.box(9, 10)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new MobComponent(
                        "orc/tiny/goblin_idle_anim_f", 4,
                        "orc/tiny/goblin_run_anim_f", 4
                ))
                .build();
    }

    @Spawns("demon_boss")
    public Entity newDemonBoss(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC); // AI controlled

        return entityBuilder(data)
                .type(EntityType.DEMON_BOSS)
                .bbox(new HitBox(new Point2D(6, 10), BoundingShape.box(20, 26)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new MobComponent(
                        "demon/boss/big_demon_idle_anim_f", 4,
                        "demon/boss/big_demon_run_anim_f", 4
                ))
                .build();
    }

    @Spawns("demon_normal")
    public Entity newDemonNormal(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC); // AI controlled

        return entityBuilder(data)
                .type(EntityType.DEMON_NORMAL)
                .bbox(new HitBox(new Point2D(3, 7), BoundingShape.box(10, 16)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new MobComponent(
                        "demon/normal/chort_idle_anim_f", 4,
                        "demon/normal/chort_run_anim_f", 4
                ))
                .build();
    }

    @Spawns("demon_tiny")
    public Entity newDemonTiny(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC); // AI controlled

        return entityBuilder(data)
                .type(EntityType.DEMON_TINY)
                .bbox(new HitBox(new Point2D(4, 6), BoundingShape.box(9, 10)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new MobComponent(
                        "demon/tiny/imp_idle_anim_f", 4,
                        "demon/tiny/imp_run_anim_f", 4
                ))
                .build();
    }

    @Spawns("undead_boss")
    public Entity newUndeadBoss(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC); // AI controlled

        return entityBuilder(data)
                .type(EntityType.UNDEAD_BOSS)
                .bbox(new HitBox(new Point2D(7, 9), BoundingShape.box(18, 27)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new MobComponent(
                        "undead/boss/big_zombie_idle_anim_f", 4,
                        "undead/boss/big_zombie_run_anim_f", 4
                ))
                .build();
    }

    @Spawns("undead_normal")
    public Entity newUndeadNormal(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC); // AI controlled

        return entityBuilder(data)
                .type(EntityType.UNDEAD_NORMAL)
                .bbox(new HitBox(new Point2D(4, 1), BoundingShape.box(8, 15)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new MobComponent(
                        "undead/normal/zombie_anim_f", 4,
                        "undead/normal/zombie_anim_f", 4
                ))
                .build();
    }

    @Spawns("undead_tiny")
    public Entity newUndeadTiny(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC); // AI controlled

        return entityBuilder(data)
                .type(EntityType.UNDEAD_TINY)
                .bbox(new HitBox(new Point2D(4, 6), BoundingShape.box(9, 10)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new MobComponent(
                        "undead/tiny/tiny_zombie_idle_anim_f", 4,
                        "undead/tiny/tiny_zombie_run_anim_f", 4
                ))
                .build();
    }
*/
    @Spawns("door")
    public Entity newDoor(SpawnData data) {
        return FXGL.entityBuilder(data)
                .type(EntityType.DOOR)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with(new DoorComponent(data.get("connected")))
                .build();
    }
    @Spawns("plate")
    public Entity newPlate(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.PLATE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .with(new PlateComponent(data.get("color"), data.get("connected")))
                .build();
    }
    @Spawns("ladder")
    public Entity newLadder(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.LADDER)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .with(new LadderComponent(data.get("connected")))
                .build();
    }
    @Spawns("spike")
    public Entity newSpike(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.SPIKE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .with(new SpikeComponent())
                .build();
    }
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        PhysicsComponent physics = new PhysicsComponent();
        physics.setBodyType(BodyType.DYNAMIC); // user has the control

        return entityBuilder(data)
                .type(EntityType.PLAYER)
                .bbox(new HitBox(new Point2D(3, 8), BoundingShape.box(10, 20)))
                .with(physics)
                .with(new CollidableComponent(true))
                .with(new PlayerComponent(
                        FXGL.gets("idlePlayer" + data.getData().get("type")), 4,
                        FXGL.gets("walkPlayer" + data.getData().get("type")), 4))
                .build();
    }
}
