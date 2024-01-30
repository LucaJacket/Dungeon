package com.lgiacchetta.dungeon;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.texture.Texture;
import com.lgiacchetta.dungeon.collision.*;
import com.lgiacchetta.dungeon.component.PlayerComponent;
import com.lgiacchetta.dungeon.menu.GameMenu;
import com.lgiacchetta.dungeon.menu.MainMenu;
import com.lgiacchetta.dungeon.scene.GameEndScene;
import com.lgiacchetta.dungeon.scene.GameOverScene;
import com.lgiacchetta.dungeon.scene.MainLoadingScene;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;
import static com.lgiacchetta.dungeon.Utils.*;

/**
 * Dungeon Game Application.
 *
 * @author Luca Giacchetta
 * @author Sofia Vita
 * @version 2023.07.09
 */
public class DungeonApp extends GameApplication {
    private final IntegerProperty texturePlayer1 = new SimpleIntegerProperty(0);
    private final IntegerProperty texturePlayer2 = new SimpleIntegerProperty(1);
    private final IntegerProperty chosenLevel = new SimpleIntegerProperty(0);

    private Entity player1;
    private Entity player2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.50);
        getSettings().setGlobalSoundVolume(0.50);
        getAudioPlayer().loopMusic(MUSIC_MENU);
    }

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setTitle("Dungeon");
        settings.setVersion("1.0");
        settings.setAppIcon("chest/chest_full_open_anim_f2.png");
        settings.setMainMenuEnabled(true);
        settings.setDefaultCursor(new CursorInfo("cursor.png", 5.0, 3.0));
        settings.setFontGame("alagard.ttf");
        settings.setFontMono("alagard.ttf");
        settings.setFontText("alagard.ttf");
        settings.setFontUI("alagard.ttf");

        settings.setSceneFactory(new SceneFactory() {
            @Override
            public @NotNull LoadingScene newLoadingScene() {
                return new MainLoadingScene();
            }

            @Override
            public @NotNull FXGLMenu newMainMenu() {
                return new MainMenu(texturePlayer1, texturePlayer2, chosenLevel);
            }

            @Override
            public @NotNull FXGLMenu newGameMenu() {
                return new GameMenu();
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("texturePlayer1", SKINS.get(texturePlayer1.get()));
        vars.put("texturePlayer2", SKINS.get(texturePlayer2.get()));
        vars.put("level", chosenLevel.get());
        vars.put("levelTime", 0.0);
        vars.put("deaths", 0);
        vars.put("points", 3);
        vars.put("healthPlayer1", 0.0);
        vars.put("healthPlayer2", 0.0);
    }

    @Override
    protected void initInput() {
        getInput().addAction(new UserAction("Up1") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).up();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.W); // move player1
        getInput().addAction(new UserAction("Left1") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);
        getInput().addAction(new UserAction("Down1") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).down();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.S);
        getInput().addAction(new UserAction("Right1") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D);
        getInput().addAction(new UserAction("Up2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).up();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.UP); // move player2
        getInput().addAction(new UserAction("Left2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.LEFT);
        getInput().addAction(new UserAction("Down2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).down();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.DOWN);
        getInput().addAction(new UserAction("Right2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.RIGHT);
    }

    @Override
    protected void initPhysics() {
        getPhysicsWorld().setGravity(0.0, 0.0);
        getPhysicsWorld().addCollisionHandler(new PlayerPlayerHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerSpikeHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerPlateHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerLadderHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerExitHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerPotionHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerTriggerHandler());
    }

    private Texture getPlayerIcon(int type) {
        Texture texture = getAssetLoader().loadTexture(gets("texturePlayer" + type) + "_idle_anim_f0.png");
        texture.setScaleX(2.0);
        texture.setScaleY(2.0);
        return texture;
    }

    private Texture getHearthIcon(int type, int count) {
        Texture texture = getAssetLoader().loadTexture("heart/ui_heart_full.png");
        texture.setScaleX(2.0);
        texture.setScaleY(2.0);
        DoubleProperty healthProperty = getdp("healthPlayer" + type);
        texture.imageProperty().bind(Bindings.createObjectBinding(() -> {
            double health = healthProperty.get();
            if (health >= count) return image("heart/ui_heart_full.png");
            else if (health > count - 1) return image("heart/ui_heart_half.png");
            else return image("heart/ui_heart_empty.png");
        }, healthProperty));
        return texture;
    }

    private HBox getHealthBar(int type) {
        HBox hBox = new HBox(20.0);
        hBox.setAlignment(Pos.BASELINE_LEFT);
        hBox.getChildren().add(getPlayerIcon(type));
        for (int i = 1; i <= 3; i++)
            hBox.getChildren().add(getHearthIcon(type, i));
        return hBox;
    }

    private VBox getHealthUI() {
        VBox healthUI = new VBox(32.0, getHealthBar(1), getHealthBar(2));
        healthUI.setPadding(new Insets(24.0));
        healthUI.setAlignment(Pos.TOP_LEFT);
        return healthUI;
    }

    private Text getTextLevel() {
        Text textLevel = getUIFactoryService().newText("", Color.WHITE, 32.0);
        textLevel.textProperty().bind(Bindings.createStringBinding(() -> "Level " + geti("level"), getip("level")));
        return textLevel;
    }

    private Text getTextTime() {
        Text textTime = getUIFactoryService().newText("", Color.WHITE, 32.0);
        textTime.textProperty().bind(Bindings.createStringBinding(() -> {
            int seconds = (int) getd("levelTime");
            return Math.min(59, seconds / 60) + ":" + Math.min(59, seconds % 60);
        }, getdp("levelTime")));
        return textTime;
    }

    private VBox getLevelTimeUI() {
        VBox levelTimeUI = new VBox(32.0, getTextLevel(), getTextTime());
        levelTimeUI.setPadding(new Insets(24.0));
        levelTimeUI.setAlignment(Pos.TOP_RIGHT);
        return levelTimeUI;
    }

    @Override
    protected void initUI() {
        GridPane gameUI = new GridPane();
        ColumnConstraints halfWidth = new ColumnConstraints(getAppWidth() / 2.0);
        gameUI.getColumnConstraints().addAll(halfWidth, halfWidth);
        gameUI.addRow(0, getHealthUI(), getLevelTimeUI());
        getGameScene().addUINode(gameUI);
    }

    private void calcPoints() {
        DoubleProperty levelTime = getdp("levelTime");
        DoubleProperty healthPlayer1 = getdp("healthPlayer1");
        DoubleProperty healthPlayer2 = getdp("healthPlayer2");
        getWorldProperties().intProperty("points").bind(Bindings.createIntegerBinding(() -> {
            int points = 0;
            if (levelTime.get() < 180.0) points++;
            if (healthPlayer1.get() == 3.0) points++;
            if (healthPlayer2.get() == 3.0) points++;
            return points;
        }, levelTime, healthPlayer1, healthPlayer2));
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.BLACK);
        getGameWorld().addEntityFactory(new DungeonFactory());
        getAudioPlayer().stopMusic(MUSIC_MENU);
        getAudioPlayer().loopMusic(MUSIC_GAME);
        getGameScene().setCursorInvisible();
        getWorldProperties().addListener("level", (oldValue, newValue) -> initLevel());
        getWorldProperties().addListener("deaths", (oldValue, newValue) -> initLevel());
        calcPoints();
        initLevel();
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player1.getComponent(PlayerComponent.class).getHealth() <= 0.0 || player2.getComponent(PlayerComponent.class).getHealth() <= 0.0) {
            new GameOverScene().push();
            return;
        }
        inc("levelTime", tpf);
    }

    private void initLevel() {
        int level = geti("level");
        if (level > 0) {
            new GameEndScene().push();
            return;
        }
        setLevelFromMap("tmx/level" + level + ".tmx");

        player1 = getGameWorld().getSingleton(e -> e.isType(EntityType.PLAYER) && e.getInt("type") == 1);
        getdp("healthPlayer1").bind(player1.getComponent(PlayerComponent.class).getHealthProperty());
        player2 = getGameWorld().getSingleton(e -> e.isType(EntityType.PLAYER) && e.getInt("type") == 2);
        getdp("healthPlayer2").bind(player2.getComponent(PlayerComponent.class).getHealthProperty());

        Viewport viewport = getGameScene().getViewport();
        viewport.bindToFit(getAppWidth() / 8.0, getAppHeight() / 8.0, player1, player2);
        viewport.setLazy(true);
    }
}
