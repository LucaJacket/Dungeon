package com.lgiacchetta.dungeon;

import com.almasb.fxgl.app.CursorInfo;
import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.FXGLMenu;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.components.HealthDoubleComponent;
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
import static com.lgiacchetta.dungeon.Utils.MUSIC_GAME;
import static com.lgiacchetta.dungeon.Utils.MUSIC_MENU;

public class DungeonApp extends GameApplication {
    private final static int FINAL_LEVEL = 3;
    private Entity player1;
    private Entity player2;
    private StringProperty texturePlayer1;
    private StringProperty texturePlayer2;
    private IntegerProperty chosenLevel;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    protected void onPreInit() {
        getSettings().setGlobalMusicVolume(0.5);
        getSettings().setGlobalSoundVolume(0.5);
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

        texturePlayer1 = new SimpleStringProperty();
        texturePlayer2 = new SimpleStringProperty();
        chosenLevel = new SimpleIntegerProperty(0);

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
        vars.put("idlePlayer1", texturePlayer1.getValue());
        vars.put("walkPlayer1", texturePlayer1.getValue().replace("idle", "run"));
        vars.put("idlePlayer2", texturePlayer2.getValue());
        vars.put("walkPlayer2", texturePlayer2.getValue().replace("idle", "run"));
        vars.put("level", chosenLevel.get());
        vars.put("levelTime", 0.0);
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
        getPhysicsWorld().addCollisionHandler(new PlayerExitHandler(this::onLevelEnded));
        getPhysicsWorld().addCollisionHandler(new PlayerPotionHandler());
        getPhysicsWorld().addCollisionHandler(new PlayerTriggerHandler());
    }

    private HBox getHealthBar(String texturePlayer, DoubleProperty health) {
        HBox hBox = new HBox(20.0);
        hBox.setAlignment(Pos.BASELINE_LEFT);

        Texture texture = getAssetLoader().loadTexture(texturePlayer);
        texture.setScaleX(2.0);
        texture.setScaleY(2.0);
        hBox.getChildren().add(texture);

        for (int i = 1; i <= 3; i++) {
            BooleanProperty isFull = new SimpleBooleanProperty();
            isFull.bind(health.greaterThanOrEqualTo(i));
            BooleanProperty isEmpty = new SimpleBooleanProperty();
            isEmpty.bind(health.lessThanOrEqualTo(i - 1));

            Texture heart = getAssetLoader().loadTexture("heart/ui_heart_full.png");
            heart.setScaleX(2.0);
            heart.setScaleY(2.0);
            heart.imageProperty().bind(Bindings.createObjectBinding(() -> {
                if (isFull.get()) return image("heart/ui_heart_full.png");
                else if (isEmpty.get()) return image("heart/ui_heart_empty.png");
                else return image("heart/ui_heart_half.png");
            }, isFull, isEmpty));
            hBox.getChildren().add(heart);
        }
        return hBox;
    }

    @Override
    protected void initUI() {
        GridPane gameUI = new GridPane();

        HBox healthBarPlayer1 = getHealthBar(texturePlayer1.get() + "0.png",
                player1.getComponent(HealthDoubleComponent.class).valueProperty());
        HBox healthBarPlayer2 = getHealthBar(texturePlayer2.get() + "0.png",
                player2.getComponent(HealthDoubleComponent.class).valueProperty());
        VBox healthUI = new VBox(30.0, healthBarPlayer1, healthBarPlayer2);
        healthUI.setAlignment(Pos.TOP_LEFT);
        healthUI.setPadding(new Insets(20.0));

        Text textLevel = getUIFactoryService().newText("", Color.WHITE, 32.0);
        Text textTime = getUIFactoryService().newText("", Color.WHITE, 32.0);
        textTime.textProperty().bind(Bindings.createStringBinding(() -> {
            int seconds = (int) getdp("levelTime").get();
            return Math.min(59, seconds / 60) + ":" + Math.min(59, seconds % 60);
        }, getdp("levelTime")));
        textLevel.textProperty().bind(Bindings.createStringBinding(() -> "Level " + geti("level"),
                getip("level")));
        VBox timeUI = new VBox(30.0, textLevel, textTime);
        timeUI.setAlignment(Pos.TOP_RIGHT);
        timeUI.setPadding(new Insets(20.0));

        gameUI.add(healthUI, 0, 0);
        gameUI.add(timeUI, 1, 0);
        for (int i = 0; i < gameUI.getColumnCount(); i++) {
            ColumnConstraints columnWidth = new ColumnConstraints();
            columnWidth.setPrefWidth((double) getAppWidth() / gameUI.getColumnCount());
            gameUI.getColumnConstraints().add(columnWidth);
        }
        getGameScene().addUINode(gameUI);
    }

    @Override
    protected void initGame() {
        getGameScene().setBackgroundColor(Color.BLACK);
        getGameWorld().addEntityFactory(new DungeonFactory());
        getAudioPlayer().stopMusic(MUSIC_MENU);
        getAudioPlayer().loopMusic(MUSIC_GAME);
        getGameScene().setCursorInvisible();
        setLevel();
    }

    @Override
    protected void onUpdate(double tpf) {
        if (player1.getComponent(HealthDoubleComponent.class).getValue() == 0.0 ||
                player2.getComponent(HealthDoubleComponent.class).getValue() == 0.0) {
            new GameOverScene(this::onPlayerDied).onGameOver();
        }
        inc("levelTime", tpf);
    }

    private void setLevel() {
        setLevelFromMap("tmx/level" + geti("level") + ".tmx");

        getGameWorld().getEntitiesByType(EntityType.PLAYER).forEach(player -> {
            if (player.getProperties().getInt("type") == 1) player1 = player;
            else player2 = player;
        });

        Viewport viewport = getGameScene().getViewport();
        viewport.bindToFit(getAppWidth() / 8.0, getAppHeight() / 8.0, player1, player2);
        viewport.setLazy(true);
    }

    public void onPlayerDied() {
        setLevel();
    }

    public void onLevelEnded() {
        set("levelTime", 0.0);
        inc("level", 1);
        if (geti("level") <= FINAL_LEVEL)
            setLevel();
        else new GameEndScene().onGameEnd();
    }
}
