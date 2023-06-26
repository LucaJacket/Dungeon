package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;

public class PlateComponent extends Component {
    private final Texture texture;
    private final Image unpressed;
    private final Image pressed;
    private final int connectedDoor;

    public PlateComponent(String color, int connectedDoor) {
        unpressed = FXGL.image("plate/button_" + color + "_up.png");
        pressed = FXGL.image("plate/button_" + color + "_down.png");
        this.connectedDoor = connectedDoor;
        texture = new Texture(unpressed);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    public void change() {
        if (isPressed() && !isBeingPressed()) {
            texture.setImage(unpressed);
        }
        if (!isPressed() && isBeingPressed()) {
            texture.setImage(pressed);

            FXGL.getGameWorld().getEntitiesByComponent(DoorComponent.class)
                    .stream()
                    .map(entity -> entity.getComponent(DoorComponent.class))
                    .filter(door -> door.getConnectedPlate() == getConnectedDoor())
                    .forEach(DoorComponent::change);
        }
    }

    private boolean isPressed() {
        return texture.getImage() == pressed;
    }

    private boolean isBeingPressed() {
        return FXGL.getGameWorld().getEntitiesByComponent(PlayerComponent.class)
                .stream().anyMatch(player -> entity.isColliding(player));
    }

    public int getConnectedDoor() {
        return connectedDoor;
    }
}
