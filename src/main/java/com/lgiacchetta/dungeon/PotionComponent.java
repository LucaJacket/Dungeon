package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

public class PotionComponent extends Component {
    private final double restore;
    private final Texture texture;

    public PotionComponent(double restore) {
        this.restore = restore;
        texture = new Texture(FXGL.image("potion/flask_big_green.png"));
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    public void restoreHealth(PlayerComponent player) {
        player.heal(restore);
    }
}
