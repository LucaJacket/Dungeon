package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class PlateComponent extends Component {
    private final String color;
    private final Texture texture;
    private final Image unpressed;
    private final Image pressed;

    public PlateComponent(String color) {
        this.color = color;
        unpressed = FXGL.image("plate/button_" + color + "_up.png");
        pressed = FXGL.image("plate/button_" + color + "_down.png");
        texture = new Texture(unpressed);
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    public void setPressed() {
        if (texture.getImage() == unpressed) {
            texture.setImage(pressed);
        }
    }

    public void setUnpress() {
        if (texture.getImage() == pressed) {
            texture.setImage(unpressed);
        }
    }

    public String getColor() {
        return color;
    }
}
