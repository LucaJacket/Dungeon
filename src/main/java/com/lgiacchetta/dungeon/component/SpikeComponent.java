package com.lgiacchetta.dungeon.component;

import com.almasb.fxgl.entity.component.Component;
import com.almasb.fxgl.texture.AnimatedTexture;
import com.almasb.fxgl.texture.AnimationChannel;

import static com.almasb.fxgl.dsl.FXGL.play;
import static com.lgiacchetta.dungeon.Utils.createAnimationChannel;

/**
 * Spike Component added to spike entities.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @see Component
 */
public class SpikeComponent extends Component {
    private final AnimatedTexture texture;
    private final AnimationChannel safe;
    private final AnimationChannel danger;
    private boolean isDangerous;

    /**
     * Initializes SpikeComponent.
     */
    public SpikeComponent() {
        safe = createAnimationChannel("spike/floor_spikes_anim_f", 8, 4.0);
        danger = createAnimationChannel("spike/floor_spikes_danger_anim_f", 1, 2.0);
        texture = new AnimatedTexture(safe);
        texture.loop();
        isDangerous = false;
        loopBetweenTwo();
    }

    /**
     * Adds animated texture when added.
     */
    @Override
    public void onAdded() {
        entity.getViewComponent().addChild(texture);
    }

    private void loopBetweenTwo() {
        texture.setOnCycleFinished(() -> {
            if (texture.getAnimationChannel() == safe) {
                texture.loopAnimationChannel(danger);
                isDangerous = true;
                play("spike.wav");
            } else {
                texture.loopAnimationChannel(safe);
                isDangerous = false;
            }
            loopBetweenTwo();
        });
    }

    /**
     * Returns spike status.
     *
     * @return true if spike should deal damage, false otherwise
     */
    public boolean isDangerous() {
        return isDangerous;
    }
}
