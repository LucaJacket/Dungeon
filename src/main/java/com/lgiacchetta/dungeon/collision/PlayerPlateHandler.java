package com.lgiacchetta.dungeon.collision;

import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.physics.CollisionHandler;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.physics.box2d.dynamics.Body;
import com.almasb.fxgl.texture.Texture;
import com.lgiacchetta.dungeon.EntityType;
import com.lgiacchetta.dungeon.component.PlayerComponent;

import static com.almasb.fxgl.dsl.FXGL.getGameWorld;
import static com.almasb.fxgl.dsl.FXGL.play;

/**
 * Collision handler between player and plate.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see CollisionHandler
 */
public class PlayerPlateHandler extends CollisionHandler {

    /**
     * Initializes PlayerPlateHandler.
     */
    public PlayerPlateHandler() {
        super(EntityType.PLAYER, EntityType.PLATE);
    }

    @Override
    protected void onCollisionBegin(Entity a, Entity b) {
        assert a.hasComponent(PlayerComponent.class);
        press(b);
    }

    @Override
    protected void onCollisionEnd(Entity a, Entity b) {
        assert a.hasComponent(PlayerComponent.class);
        unpress(b);
    }

    private void press(Entity plate) {
        if (plate.getProperties().getValue("playerPressing").equals(0)) { // press
            Texture texture = plate.getProperties().getValue("texture");
            texture.setImage(plate.getProperties().getValue("down"));
            changeDoors(plate);
        }
        plate.getProperties().increment("playerPressing", +1);
    }

    private void unpress(Entity plate) {
        plate.getProperties().increment("playerPressing", -1);
        if (plate.getProperties().getValue("playerPressing").equals(0)) { // unpress
            Texture texture = plate.getProperties().getValue("texture");
            texture.setImage(plate.getProperties().getValue("up"));
        }
    }

    private void changeDoors(Entity plate) {
        getGameWorld().getEntitiesByType(EntityType.DOOR, EntityType.EXIT).stream()
                .filter(door -> plate.getProperties().getValue("connected") ==
                        door.getProperties().getValue("connected"))
                .forEach(door -> {
                    Texture doorTexture = door.getProperties().getValue("texture");
                    Body body = door.getComponent(PhysicsComponent.class).getBody();
                    if (body.isActive()) { // door is closed
                        play("door-open.wav");
                        doorTexture.setImage(door.getProperties().getValue("open"));
                        if (door.getType().equals(EntityType.DOOR)) // exclude exit door
                            body.setActive(false); // open door physically
                    } else {
                        play("door-close.wav");
                        doorTexture.setImage(door.getProperties().getValue("closed"));
                        if (door.getType().equals(EntityType.DOOR)) // exclude exit door
                            body.setActive(true); // close door physically
                    }
                });
    }
}
