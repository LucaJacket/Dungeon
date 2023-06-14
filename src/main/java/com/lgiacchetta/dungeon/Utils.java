package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.ui.FontFactory;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final double idleAnimationDuration = 1.0;
    public static final double walkAnimationDuration = 0.5;
    public static final double playerVelocity = 100.0;
    public static final double playerHealth = 3.0;
    public static final double playerDamageCooldown = 2.0;
    public static final double playerTeleportCooldown = 4.0;
    public static final double mobVelocity = 50.0;
    public static final double mobAttackRange = 10 * 16.0;
    public static final double spikeDamage = 0.5;
    public static final FontFactory UIFont = FXGL.getAssetLoader().loadFont("a-charming-font.ttf");
    public static AnimationChannel getAnimation(String filename, int nFrames, double seconds)
    {
        List<Image> list = new ArrayList<>();
        for (int i = 0; i < nFrames; i++) {
            list.add(FXGL.image(filename + i + ".png"));
        }
        return new AnimationChannel(list, Duration.seconds(seconds));
    }
}
