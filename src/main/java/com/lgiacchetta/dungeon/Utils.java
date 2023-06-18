package com.lgiacchetta.dungeon;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.texture.AnimationChannel;
import com.almasb.fxgl.ui.FontFactory;
import javafx.scene.image.Image;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static final FontFactory UIFont = FXGL.getAssetLoader().loadFont("alagard.ttf");

    public static AnimationChannel getAnimation(String filename, int nFrames, double seconds) {
        List<Image> list = new ArrayList<>();
        for (int i = 0; i < nFrames; i++) {
            list.add(FXGL.image(filename + i + ".png"));
        }
        return new AnimationChannel(list, Duration.seconds(seconds));
    }
}
