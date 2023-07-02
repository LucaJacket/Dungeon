package com.lgiacchetta.dungeon.component;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import static com.lgiacchetta.dungeon.Utils.*;

public class SpikeComponent extends Component {
    private final AnimatedTexture texture;
    private final AnimationChannel safe;
    private final AnimationChannel danger;
    private boolean isDangerous;

    public SpikeComponent() {
        safe = getAnimation("spike/floor_spikes_anim_f", 8, 4.0);
        danger = getAnimation("spike/floor_spikes_danger_anim_f", 1, 2.0);
        texture = new AnimatedTexture(safe);
        texture.loop();
        isDangerous = false;
        loopBetweenTwo();
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    private void loopBetweenTwo() {
        texture.setOnCycleFinished(() -> {
            if (texture.getAnimationChannel() == safe) {
                texture.loopAnimationChannel(danger);
                isDangerous = true;
                FXGL.play("spike.wav");
            } else {
                texture.loopAnimationChannel(safe);
                isDangerous = false;
            }
            loopBetweenTwo();
        });
    }

    public boolean isDangerous() {
        return isDangerous;
    }
}
