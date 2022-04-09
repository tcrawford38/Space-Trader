package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.kotcrab.vis.ui.widget.VisLabel;
import com.kotcrab.vis.ui.widget.VisTextButton;
import main.primary.Global;
import main.primary.gameplay.Item;
import main.primary.gameplay.Region;
import main.primary.gameplay.Ship;
import main.primary.gameplay.Skill;

import java.util.ArrayList;
import java.util.List;

public class MapScene extends Scene {

    private List<RegionUI> regionUIList;
    private Label statsBarLabel;
    private static Table shipTable;

    public void create() {
        // Set map background
        sceneLoader.setBackground("images/map_background.jpg");

        // Set "Map" title at top of screen
        table.align(Align.top);
        table.add(title("Map", Color.YELLOW));
        table.row();

        // Generate all map buttons and relevant user interface panels
        this.regionUIList = new ArrayList<>();
        for (Region r : Global.app.map.getRegions()) {
            regionUIList.add(new RegionUI(r, this));
        }
        for (RegionUI ru : regionUIList) {
            ru.options.toFront();
        }

        this.statsBarLabel = label(Global.app.player.generateStatsBar(), Color.YELLOW, 0.6f);
        table.add(statsBarLabel);

        //Create table for ship text button
        this.shipTable = new Table();
        resize((int) table.getWidth(), (int) table.getHeight());

        shipTable.add(textButton("View Ship", Color.RED, () -> {
            shipTable.remove();
            sceneLoader.setScene(new ShipScene());
        })).padRight(200);
        shipTable.add(textButton("Training Ground", Color.RED, () -> {
            shipTable.remove();
            sceneLoader.setScene(new TrainingGroundScene());
        })).padRight(200);;
        shipTable.add(textButton("Save Game", Color.RED, () -> {
            shipTable.remove();
            sceneLoader.setScene(new SaveGameScene());
        }));
        shipTable.bottom().pad(30);
        sceneLoader.addActor(shipTable);
    }

    protected void resize(int width, int height) {
        super.resize(width, height);
        if (shipTable != null) {
            shipTable.setPosition(sceneLoader.xOffset, sceneLoader.yOffset);
            shipTable.setSize(table.getWidth(), table.getHeight());
        }
    }


    public void updateUI() {
        for (RegionUI ru : regionUIList) {
            ru.updateUI();
        }
        statsBarLabel.setText(Global.app.player.generateStatsBar());
    }

    public void reset() {
        for (RegionUI ru : regionUIList) {
            ru.reset();
        }
    }

    protected void render(SpriteBatch batch) {
        Region curRegion = Global.app.map.getCurrentLocation();
        ShapeRenderer sr = Global.app.shapeRenderer;

        // Draw debug circle around current region
        ShaderProgram s = batch.getShader();
        batch.setShader(null);
        sr.setColor(Color.RED);
        sr.begin(ShapeRenderer.ShapeType.Line);
        sr.circle(sceneLoader.xOffset + getStage().getWidth() / 2 + curRegion.getX(), sceneLoader.yOffset + getStage().getHeight() / 2 + curRegion.getY(), 30);
        sr.end();
        batch.setShader(s);
    }

    /**
     * Collection of all UI elements related to a given region on the map
     */
    static class RegionUI {

        private final Region region;
        private final MapScene mapScene;

        private final RegionOptions options;

        private final Button mapBtn;
        private final Label regionName;

        public RegionUI(Region region, MapScene mapScene) {
            this.region = region;
            this.mapScene = mapScene;

            this.options = new RegionOptions(region, mapScene);
            mapScene.sceneLoader.addActor(options);

            this.mapBtn = imageButton("images/unknown_location.png", () -> {
                mapScene.updateUI();
                this.options.setVisible(true);
            });
            mapBtn.setTransform(true);
            mapBtn.setScale(0.05f); // hasVisited and unknown location image are different sizes
            mapBtn.toBack();
            mapScene.sceneLoader.addActor(this.mapBtn);

            this.regionName = new VisLabel(region.getName()) {
                public void draw(Batch batch, float parentAlpha) {
                    ShaderProgram s = batch.getShader();
                    batch.setShader(null);
                    super.draw(batch, parentAlpha);
                    batch.setShader(s);
                }
            };
            regionName.setAlignment(Align.center);
            regionName.setFontScale(0.6f);
            mapScene.sceneLoader.centerActor(regionName);
            regionName.moveBy(region.getX(), region.getY() - 40f);
            mapScene.sceneLoader.addActor(regionName);
            this.regionName.setVisible(false);

            updateUI();
            updateUI();
        }

        public void updateUI() {
            options.updateUI();
            this.options.setVisible(false);

            // Update mapBtn
            Texture image = Global.app.assetManager.get(region.hasVisited() ? "images/visited_location.png" : "images/unknown_location.png");
            TextureRegionDrawable imageDrawable = new TextureRegionDrawable(image);
            ImageButton.ImageButtonStyle mapBtnStyle = new ImageButton.ImageButtonStyle();
            mapBtnStyle.up = imageDrawable;
            mapScene.sceneLoader.centerActor(mapBtn);
            mapBtn.moveBy(region.getX(), region.getY());
            mapBtn.setStyle(mapBtnStyle);

            // Update regionName
            regionName.setVisible(region.hasVisited());
            regionName.toBack();
        }

        public void reset() {
            mapBtn.setVisible(false);
            regionName.remove();
            options.remove();
        }
    }

    /**
     * Panel which gives a brief description of the region and options to travel to or enter the region
     */
    static class RegionOptions extends Table {
        private final Region region;
        private boolean isCurrentRegion; // Whether or not the corresponding region is where the player is at

        private final MapScene mapScene;

        private final Label description;
        private final TextButton interactBtn;

        public RegionOptions(Region region, MapScene mapScene) {
            this.region = region;
            this.mapScene = mapScene;


            setVisible(false);
            setBackground(coloredBG(Color.GRAY));

            this.description = new VisLabel();
            add(description);
            row();

            // Generate button to travel to region or enter the region
            this.interactBtn = new VisTextButton("");
            VisTextButton.VisTextButtonStyle interactBtnStyle = new VisTextButton.VisTextButtonStyle();
            interactBtnStyle.font = interactBtn.getStyle().font;
            interactBtn.setStyle(interactBtnStyle);
            interactBtn.pad(5);
            add(interactBtn);

            this.updateUI();
        }

        /**
         * Set regionDescription text, interactButton color, etc.
         */
        public void updateUI() {
            // Recalculate the fuel cost to travel to the given region
            Region curRegion = Global.app.map.getCurrentLocation();
            this.isCurrentRegion = curRegion.equalsRegion(region);
            double dist = curRegion.distanceTo(region);
            int pilotSkillLvl = Global.app.player.getSkillLevel(Skill.SkillType.PILOT);
            int pilotDiscountFactor = Global.PERCENT_FUEL_DISCOUNT_PER_PILOT_LVL * pilotSkillLvl;
            int fuelCost = (int) (dist * Global.FUEL_COST_PER_UNIT * (1 - 0.01 * pilotDiscountFactor)); // Calculate fuel cost based on distance and pilot skill level

            // Update panel description
            description.setText(region.hasVisited() ? region.getDescription() : "Unknown Location");
            if (!isCurrentRegion) {
                description.setText(description.getText()
                        + "\nDistance: " + (int) dist
                        + "\nFuel cost: " + fuelCost + " (-" + pilotDiscountFactor + "%) ");
            }

            // Update interact button (Travel To/Enter button)
            interactBtn.getListeners().clear();
            interactBtn.addListener(new ClickListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    if (isCurrentRegion) {
                        shipTable.remove();
                        mapScene.sceneLoader.setScene(new MarketScene(region.getRegionMarket()));
                    } else {
                        Ship ship = Global.app.player.getShip();
                        if (fuelCost <= ship.fuel) {
                            switch (Global.app.pickRandomEvent()) {
                                case TRADER:
                                    shipTable.remove();
                                    mapScene.sceneLoader.setScene(new TraderScene());
                                    break;
                                case BANDIT:
                                    shipTable.remove();
                                    mapScene.sceneLoader.setScene(new BanditScene());
                                    break;
                                case SPACEPOLICE:
                                    shipTable.remove();
                                    mapScene.sceneLoader.setScene(new SpacePoliceScene());
                                    break;
                                case MARKET:
                                    break;
                                case BENEFACTOR:
                                    shipTable.remove();
                                    mapScene.sceneLoader.setScene(new BenefactorScene());
                                    break;
                            }
                            ship.fuel -= fuelCost;

                            // ages all items in ship inventory
                            ship.ageItems();

                            // update new selling prices
                            for(Item i : ship.getItems()) {
                                System.out.println(i.getName() + " " + i.getAge());
                                int randomness = (int) ((Math.random() * (i.getAdjustedPrice() / 16 + i.getAdjustedPrice() / 16)) - i.getAdjustedPrice() / 16);
                                int ageDepreciation = (int) (i.getAge() * (i.getAdjustedSellingPrice() / 30));
                                System.out.println(ageDepreciation);
                                System.out.println(randomness);
                                i.setFinalSellPrice(i.getAdjustedPrice() - ageDepreciation + randomness);
                            }

                            Global.app.map.setCurrentLocation(region);
                            region.setHasVisited(true);
                            mapScene.updateUI();
                            setVisible(true);
                        }

                        // Generate a new market per region every time you travel to a new market
                        region.getRegionMarket().generateMarket();
                    }
                    return true;
                }
            });
            if (fuelCost <= Global.app.player.getShip().fuel) {
                interactBtn.setText(isCurrentRegion ? "Go to Market" : "Travel to");
                TextButton.TextButtonStyle interactBtnStyle = interactBtn.getStyle();
                interactBtnStyle.up = coloredBG(isCurrentRegion ? Color.BLUE : Color.GREEN);
            } else {
                interactBtn.setText("");
                interactBtn.setVisible(false);
            }

            adjustSize();
        }

        /**
         * Scale and position the options panel according to the map button
         */
        public void adjustSize() {
            GlyphLayout layout = Global.app.fontGlyphLayout;
            setSize(0, 0);
            mapScene.sceneLoader.centerActor(this);
            moveBy(region.getX(), region.getY());
            setTransform(true);
            setScale(0.3f);
            layout.setText(description.getStyle().font, description.getText() + "\n"
                    + interactBtn.getText()); // Calculate width and height of region options pane from text
            setWidth(layout.width + 30);
            setHeight(layout.height + 40);
            if (region.getX() > 0) { // Display region options on left or right of button depending on location of the region
                moveBy(-layout.width * getScaleX() - 50, -layout.height * getScaleY());
            } else {
                moveBy(40, -layout.height * getScaleY());
            }
        }

        public void draw(Batch batch, float parentAlpha) {
            ShaderProgram s = batch.getShader();
            batch.setShader(null); // HACK disable font shader
            super.draw(batch, parentAlpha);
            batch.setShader(s);
        }
    }

}
