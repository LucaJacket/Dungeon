package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.BoundingShape;
import com.almasb.fxgl.physics.HitBox;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.BodyType;
import com.almasb.fxgl.texture.Texture;
import com.lgiacchetta.dungeon.component.PlayerComponent;
import com.lgiacchetta.dungeon.component.SpikeComponent;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import static com.almasb.fxgl.dsl.FXGL.*;

public class DungeonFactory implements EntityFactory {

    @Spawns("wall")
    public Entity newWall(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.WALL)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .build();
    }

    @Spawns("trigger")
    public Entity newTrigger(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.TRIGGER)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .build();
    }

    @Spawns("potion")
    public Entity newPotion(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.POTION)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .view("potion/flask_big_green.png")
                .build();
    }

    @Spawns("door")
    public Entity newDoor(SpawnData data) {
        Image open = image("door/doors_leaf_open.png");
        Image closed = image("door/doors_leaf_closed.png");
        Texture texture = new Texture(closed);

        return entityBuilder(data)
                .type(EntityType.DOOR)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new PhysicsComponent())
                .with("texture", texture)
                .with("open", open)
                .with("closed", closed)
                .view(texture)
                .build();
    }

    @Spawns("exit")
    public Entity newExit(SpawnData data) {
        Entity exit = newDoor(data);
        exit.setType(EntityType.EXIT);
        exit.addComponent(new CollidableComponent(true));
        return exit;
    }

    @Spawns("plate")
    public Entity newPlate(SpawnData data) {
        Image up = image("plate/button_" + data.get("color") + "_up.png");
        Image down = image("plate/button_" + data.get("color") + "_down.png");
        Texture texture = new Texture(up);

        return entityBuilder(data)
                .type(EntityType.PLATE)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .with("texture", texture)
                .with("up", up)
                .with("down", down)
                .with("playerPressing", 0)
                .view(texture)
                .build();
    }

    @Spawns("ladder")
    public Entity newLadder(SpawnData data) {
        return entityBuilder(data)
                .type(EntityType.LADDER)
                .bbox(new HitBox(BoundingShape.box(data.<Integer>get("width"), data.<Integer>get("height"))))
                .with(new CollidableComponent(true))
                .view("ladder/floor_ladder.png")
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
                .bbox(new HitBox(new Point2D(2, 12), BoundingShape.box(12, 16)))
                .with(new CollidableComponent(true))
                .with(physics)
                .with(new HealthDoubleComponent(3.0))
                .with(new PlayerComponent(
                        gets("idlePlayer" + data.getData().get("type")),
                        gets("walkPlayer" + data.getData().get("type"))))
                .build();
    }
}
