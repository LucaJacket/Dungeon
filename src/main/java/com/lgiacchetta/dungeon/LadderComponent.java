package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.Texture;

public class LadderComponent extends Component {
    private final int connectedLadder;
    private Texture texture;

    public LadderComponent(int connectedLadder) {
        this.connectedLadder = connectedLadder;
        texture = new Texture(FXGL.image("ladder/floor_ladder.png"));
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    public int getConnectedLadder() {
        return connectedLadder;
    }
}