package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.entity.components.CollidableComponent;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;

public class DoorComponent extends Component {
    private PhysicsComponent physics;
    private final Texture texture;
    private final Image closed;
    private final Image open;
    private final int connectedPlate;

    public DoorComponent(int connectedPlate) {
        this.connectedPlate = connectedPlate;
        closed = FXGL.image("door/doors_leaf_closed.png");
        open = FXGL.image("door/doors_leaf_open.png");

        texture = new Texture(closed);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    public void change() {
        if (texture.getImage() == open) {
            FXGL.play("door-close.wav");
            texture.setImage(closed);
            if (entity.getType().equals(EntityType.DOOR))
                physics.getBody().setActive(true);
            else {
                entity.removeComponent(CollidableComponent.class);
            }
        } else {
            FXGL.play("door-open.wav");
            texture.setImage(open);
            if (entity.getType().equals(EntityType.DOOR))
                physics.getBody().setActive(false);
            else {
                entity.addComponent(new CollidableComponent(true));
            }
        }
    }

    public int getConnectedPlate() {
        return connectedPlate;
    }
}
