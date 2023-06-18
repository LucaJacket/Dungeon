package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import static com.lgiacchetta.dungeon.Utils.*;

public class SpikeComponent extends Component {
    private final AnimatedTexture texture;
    private final AnimationChannel safe;
    private final AnimationChannel danger;

    public SpikeComponent() {
        safe = getAnimation("spike/floor_spikes_anim_f", 8, 4.0);
        danger = getAnimation("spike/floor_spikes_danger_anim_f", 1, 2.0);

        texture = new AnimatedTexture(safe);
        texture.loop();
    }

    @Override
    public void onUpdate(double tpf) {
        if (texture.getAnimationChannel() == safe) {
            texture.setOnCycleFinished(() -> {
                texture.loopAnimationChannel(danger);
                FXGL.play("spike.wav");
            });
        } else {
            texture.setOnCycleFinished(() -> texture.loopAnimationChannel(safe));
        }
    }

    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    public boolean isDangerous() {
        return texture.getAnimationChannel() == danger;
    }
}
