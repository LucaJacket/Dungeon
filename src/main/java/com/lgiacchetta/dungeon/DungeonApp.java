package com.lgiacchetta.dungeon;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.scene.LoadingScene;
import com.almasb.fxgl.app.scene.SceneFactory;
import com.almasb.fxgl.app.scene.Viewport;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.input.UserAction;
import com.almasb.fxgl.physics.CollisionHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class DungeonApp extends GameApplication {
    private Entity player1;
    private Entity player2;
    private VBox healthBars;
    private int currentLevel = 0;
    private GameOverScene gameOverScene;

    @Override
    protected void initSettings(GameSettings settings) {
        settings.setWidth(1280);
        settings.setHeight(720);
        settings.setSceneFactory(new SceneFactory() {
            @Override
            public LoadingScene newLoadingScene() {
                return new MainLoadingScene();
            }
        });
    }

    @Override
    protected void initGameVars(Map<String, Object> vars) {
        vars.put("idlePlayer1", "hero/knight_m_idle_anim_f");
        vars.put("walkPlayer1", "hero/knight_m_run_anim_f");
        vars.put("idlePlayer2", "hero/knight_f_idle_anim_f");
        vars.put("walkPlayer2", "hero/knight_f_run_anim_f");
    }

    @Override
    protected void initInput() {
        FXGL.getInput().addAction(new UserAction("Up1") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).up();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.W); // move player1
        FXGL.getInput().addAction(new UserAction("Left1") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.A);
        FXGL.getInput().addAction(new UserAction("Down1") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).down();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.S);
        FXGL.getInput().addAction(new UserAction("Right1") {
            @Override
            protected void onAction() {
                player1.getComponent(PlayerComponent.class).right();
            }

            @Override
            protected void onActionEnd() {
                player1.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.D);
        FXGL.getInput().addAction(new UserAction("Up2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).up();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.UP); // move player2
        FXGL.getInput().addAction(new UserAction("Left2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).left();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.LEFT);
        FXGL.getInput().addAction(new UserAction("Down2") {
            @Override
            protected void onAction() {
                player2.getComponent(PlayerComponent.class).down();
            }

            @Override
            protected void onActionEnd() {
                player2.getComponent(PlayerComponent.class).stop();
            }
        }, KeyCode.DOWN);
        FXGL.getInput().addAction(new UserAction("Right2") {
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
        FXGL.getPhysicsWorld().setGravity(0,0); // top-down view
        FXGL.getPhysicsWorld().addCollisionHandler(
                new CollisionHandler(EntityType.PLAYER, EntityType.PLAYER) {
                    @Override
                    protected void onCollisionEnd(Entity a, Entity b) {
                        a.getComponent(PlayerComponent.class).stop();
                        b.getComponent(PlayerComponent.class).stop();
                    }
                });
        FXGL.getPhysicsWorld().addCollisionHandler( // damage player
                new CollisionHandler(EntityType.PLAYER, EntityType.SPIKE) {
            @Override
            protected void onCollision(Entity a, Entity b) {
                if (b.getComponent(SpikeComponent.class).isDangerous()) {
                    a.getComponent(PlayerComponent.class).damage(Utils.spikeDamage);
                    healthBars = updateHealth(healthBars);
                }
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler( // activate plate
                new CollisionHandler(EntityType.PLAYER, EntityType.PLATE) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                b.getComponent(PlateComponent.class).setPressed();
                List<Entity> entities = FXGL.getGameWorld().getEntitiesByType(EntityType.DOOR);
                for (Entity entity : entities) {
                    DoorComponent door = entity.getComponent(DoorComponent.class);
                    if (door.getConnectedPlate() ==
                            b.getComponent(PlateComponent.class).getConnectedDoor()) {
                        door.change();
                    }
                }
            }

            @Override
            protected void onCollisionEnd(Entity a, Entity b) {
                b.getComponent(PlateComponent.class).setUnpress();
            }
        });
        FXGL.getPhysicsWorld().addCollisionHandler( // teleport
                new CollisionHandler(EntityType.PLAYER, EntityType.LADDER) {
            @Override
            protected void onCollisionBegin(Entity a, Entity b) {
                List<Entity> ladders = FXGL.getGameWorld().getEntitiesByType(EntityType.LADDER);
                ladders.remove(b);
                for (Entity ladder : ladders) {
                    if (ladder.getComponent(LadderComponent.class).getConnectedLadder() ==
                        b.getComponent(LadderComponent.class).getConnectedLadder()) {
                        a.getComponent(PlayerComponent.class).teleport(ladder.getCenter());
                    }
                }
            }
        });
    }

    @Override
    protected void onPreInit() {
        FXGL.getSettings().setGlobalMusicVolume(0.25);
        FXGL.loopBGM("melody.wav");
    }

    @Override
    protected void initGame() {
        FXGL.getGameScene().setBackgroundColor(Color.BLACK);
        FXGL.getGameWorld().addEntityFactory(new DungeonFactory());
        gameOverScene = new GameOverScene();
        setLevel(currentLevel);
    }

    @Override
    protected void onUpdate(double tpf) {
        // check if player died
        if (player1.getComponent(PlayerComponent.class).getHealth() <= 0.0 ||
                player2.getComponent(PlayerComponent.class).getHealth() <= 0.0) {
            gameOverScene.onGameOver();
            onPlayerDied();
        }
    }

    private VBox updateHealth(VBox prevHealthBars) {
        VBox vbox = new VBox(2.0);
        vbox.setPadding(new Insets(8.0));

        Entity[] players = {player1, player2};
        Arrays.stream(players).forEach(player -> {
            HBox hbox = new HBox(2.0);
            hbox.setAlignment(Pos.BASELINE_CENTER);

            double health = player.getComponent(PlayerComponent.class).getHealth();

            hbox.getChildren().add(FXGL.getAssetLoader().loadTexture(
                    FXGL.gets("idlePlayer" + player.getProperties().getValue("type")) + "0.png"));
            for (int i = 0; i < Math.floor(health); i++) {
                hbox.getChildren().add(FXGL.getAssetLoader().loadTexture("heart/ui_heart_full.png"));
            }
            if (Math.ceil(health) > Math.floor(health)) {
                hbox.getChildren().add(FXGL.getAssetLoader().loadTexture("heart/ui_heart_half.png"));
            }
            for (int i = 0; i < 3 - Math.ceil(health); i++) {
                hbox.getChildren().add(FXGL.getAssetLoader().loadTexture("heart/ui_heart_empty.png"));
            }

            vbox.getChildren().add(hbox);
        });

        if (prevHealthBars != null)
            FXGL.getGameScene().getContentRoot().getChildren().remove(prevHealthBars);
        FXGL.getGameScene().getContentRoot().getChildren().add(vbox);

        return vbox;
    }

    public void setLevel(int level) {
        currentLevel = level;
        FXGL.setLevelFromMap("tmx/test-map-no-anim.tmx");

        FXGL.getGameWorld().getEntitiesByType(EntityType.PLAYER).forEach(player -> {
            if (player.getProperties().getValue("type").equals(1))
                player1 = player;
            else
                player2 = player;
        });

        Viewport viewport = FXGL.getGameScene().getViewport();
        viewport.bindToFit(FXGL.getAppWidth() / 4.0, FXGL.getAppHeight() / 4.0, player1, player2);
        viewport.setLazy(true); // feels better

        healthBars = updateHealth(null);
    }

    public void onPlayerDied() {
        setLevel(currentLevel);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
