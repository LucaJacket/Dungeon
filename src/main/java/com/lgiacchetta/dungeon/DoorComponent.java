package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.physics.PhysicsComponent;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
public class DoorComponent extends Component {
    private PhysicsComponent physics;
    private Texture texture;
    private final Image closed;
    private final Image open;
    private final int connectedPlate;
    public DoorComponent(int connectedPlate) {
        this.connectedPlate = connectedPlate;
        closed = FXGL.image("door/doors_leaf_closed.png");
        open = FXGL.image("door/doors_leaf_open.png");

        texture = new Texture(closed);
    }
    public void change() {
        if (isOpen()) {
            FXGL.play("door-close.wav");
            texture.setImage(closed);
            physics.getBody().setActive(true);
        } else {
            FXGL.play("door-open.wav");
            texture.setImage(open);
            physics.getBody().setActive(false);
        }
    }

    public boolean isOpen() {
        return texture.getImage() == open;
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    public int getConnectedPlate() {
        return connectedPlate;
    }
}
