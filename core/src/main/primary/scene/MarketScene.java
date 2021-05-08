package main.primary.scene;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import main.primary.Global;
import main.primary.gameplay.*;

public class MarketScene extends Scene {

    private static final float SECTION_SCALE = 1.2f;
    private static final float ITEM_NAME_SCALE = 0.7f;
    private static final float ITEM_DESC_SCALE = 0.5f;
    private static final float ITEM_SIDE_PADDING = 10;

    private Player player;

    private final Market regionMarket;
    private Label statsBarLabel;

    public MarketScene(Market regionMarket) {
        this.regionMarket = regionMarket;
    }

    public void create() {
        this.player = Global.app.player;

        table.add(title(regionMarket.getCurrentRegion().getName() + " Market", Color.YELLOW));
        table.row();

        this.statsBarLabel = label(Global.app.player.generateStatsBar(), Color.YELLOW, 0.6f);
        table.add(statsBarLabel);
        table.row();

        table.add(itemsForSale());
        table.row();
        table.add(specialItemsForSale());
        table.row();
        table.add(shipCargo());
        table.row();
        table.add(upgradeSlots());
        table.row();

        table.add(textButton("Back", Color.RED, () -> {
            sceneLoader.setScene(new MapScene());
        }));
    }

    private Table itemsForSale() {
        Table itemsForSaleTable = new Table();

        final int ROWS_OF_ITEMS = 2;

        itemsForSaleTable.add(label("-- Items for sale --", Color.WHITE, SECTION_SCALE)).colspan(Market.ITEMS_PER_MARKET / ROWS_OF_ITEMS);
        itemsForSaleTable.row();

        for (int r = 0; r < ROWS_OF_ITEMS; r++) {
            for (int i = 0; i < Market.ITEMS_PER_MARKET / ROWS_OF_ITEMS; i++) {
                int itemIndex = i + r * Market.ITEMS_PER_MARKET / ROWS_OF_ITEMS;
                itemsForSaleTable.add(itemPane(regionMarket.getItemsOffering().get(itemIndex))).pad(ITEM_SIDE_PADDING);
            }
            itemsForSaleTable.row();
        }

        return itemsForSaleTable;
    }

    private Table itemPane(Item item) {
        Table itemPane = new Table();
        itemPane.add(label(item.getName(), Color.WHITE, ITEM_NAME_SCALE));
        itemPane.row();
        itemPane.add(label(item.getDescription() + " (Tech: " + item.getTechLevel() + ")", Color.WHITE, ITEM_DESC_SCALE));
        itemPane.row();

        double discountPercentage = Market.DISCOUNT_MERCHANT_LEVEL * Global.app.player.getSkillLevel(Skill.SkillType.MERCHANT);
        int itemFinalPrice = (int) (item.getAdjustedPrice() * (1.00 - 0.01 * discountPercentage));

        // Adding randomness for selling price
        int randomness = (int) ((Math.random() * (itemFinalPrice / 16)));
        item.setFinalSellPrice((int) (itemFinalPrice * Market.MARKET_DEPRECIATION - randomness));

        boolean canAfford = player.credits >= itemFinalPrice;
        Button buyBtn = textButton("Buy for " + itemFinalPrice + " (-" + discountPercentage + "%)", canAfford ? Color.GREEN : Color.RED, () -> {
            if (player.credits >= itemFinalPrice
                    && player.getShip().getCargo() > player.getShip().getTotalItems()) {
                player.getShip().addItem(item);
                player.credits -= itemFinalPrice;
                sceneLoader.setScene(new MarketScene(regionMarket));
            }
        });
        buyBtn.setTransform(true);
        buyBtn.setScale(0.5f);
        buyBtn.setOrigin(Align.center);
        itemPane.add(buyBtn);
        return itemPane;
    }

    private Table specialItemsForSale() {
        Table specialItemsForSaleTable = new Table();
        specialItemsForSaleTable.align(Align.center);

        specialItemsForSaleTable.add(label("-- Special Items --", Color.WHITE, SECTION_SCALE)).colspan(3);
        specialItemsForSaleTable.row();
        specialItemsForSaleTable.add(refuel()).pad(ITEM_SIDE_PADDING);
        specialItemsForSaleTable.add(specialItem()).pad(ITEM_SIDE_PADDING);
        specialItemsForSaleTable.add(restoreHP()).pad(ITEM_SIDE_PADDING);

        return specialItemsForSaleTable;
    }

    private Table specialItem() {
        Table specialItemTable = new Table();
        CharacterUpgrade specialItem = regionMarket.getSpecialItem();

        specialItemTable.add(label(specialItem.getName(), Color.WHITE, ITEM_NAME_SCALE));
        specialItemTable.row();
        specialItemTable.add(label(specialItem.getDescription()
                        + " (Tech: " + specialItem.getTechLevel() + " | " + Skill.typeToStr(specialItem.getSkillType())
                        + " +" + specialItem.getIncAmount() + ")",
                Color.WHITE, ITEM_DESC_SCALE));
        specialItemTable.row();

        double discountPercentage = Market.DISCOUNT_MERCHANT_LEVEL * Global.app.player.getSkillLevel(Skill.SkillType.MERCHANT);
        int specialItemFinalPrice = (int) (specialItem.getPrice() * (1.00 - 0.01 * discountPercentage));
        specialItem.setAdjustedPrice(specialItemFinalPrice);
        specialItem.setSellingPrice((int) (specialItemFinalPrice * CharacterUpgrade.DEPRECIATION));

        boolean canBuy = player.credits >= specialItemFinalPrice && !specialItem.isEquipped();
        Button buyBtn = textButton("Buy for " + specialItemFinalPrice + " (-" + discountPercentage + "%)", canBuy ? Color.GREEN : Color.RED, () -> {
            int itemFinalPrice = specialItem.getAdjustedPrice();
            if (player.credits >= itemFinalPrice
                    && player.getShip().hasUpgradeSlotSpace()
                    && !specialItem.isEquipped()) {
                player.getShip().addUpgrade(specialItem);
                specialItem.setEquipped(true);
                player.changeSkillLevel(specialItem.getSkillType(), specialItem.getIncAmount());
                player.credits -= itemFinalPrice;

                sceneLoader.setScene(new MarketScene(regionMarket));
            }
        });
        buyBtn.setTransform(true);
        buyBtn.setScale(0.5f);
        buyBtn.setOrigin(Align.center);
        specialItemTable.add(buyBtn);
        return specialItemTable;
    }

    private Table refuel() {
        Table refuel = new Table();

        refuel.add(label("Jug of fuel", Color.WHITE, ITEM_NAME_SCALE));
        refuel.row();
        refuel.add(label("It will refuel "
                + Market.FUEL_PER_JUG + " units to your ship", Color.WHITE, ITEM_DESC_SCALE));
        refuel.row();

        int fuelCost = (int) (Market.FUEL_COST_PER_UNIT * Market.FUEL_PER_JUG);

        Ship ship = player.getShip();
        boolean canBuy = player.credits >= fuelCost && ship.fuel < ship.getFuelCapacity();
        Button buyBtn = textButton("Buy for " + fuelCost, canBuy ? Color.GREEN : Color.RED, () -> {
            if (player.credits >= fuelCost && ship.fuel < ship.getFuelCapacity()) {
                player.credits -= fuelCost;
                ship.fuel = Math.min(ship.fuel + Market.FUEL_PER_JUG, ship.getFuelCapacity());
                sceneLoader.setScene(new MarketScene(regionMarket));
            }
        });
        buyBtn.setTransform(true);
        buyBtn.setScale(0.5f);
        buyBtn.setOrigin(Align.center);
        refuel.add(buyBtn);
        return refuel;
    }

    private Table restoreHP() {
        Table restoreHP = new Table();

        restoreHP.add(label("Eye drops", Color.WHITE, ITEM_NAME_SCALE));
        restoreHP.row();
        restoreHP.add(label("It will restore 1 HP", Color.WHITE, ITEM_DESC_SCALE));
        restoreHP.row();

        int healthPointCost = (int) (Market.COST_PER_HP
                * (1 - 0.05 * player.getSkillLevel(Skill.SkillType.ENGINEER)));

        boolean canBuy = player.credits >= healthPointCost
                && player.getShip().getHP() < player.getShip().getMaxHP();

        Button buyBtn = textButton("Buy for " + healthPointCost, canBuy ? Color.GREEN : Color.RED, () -> {
            if (player.credits >= healthPointCost
                    && player.getShip().getHP() < player.getShip().getMaxHP()) {
                player.credits -= healthPointCost;
                player.getShip().hp += 1;
                sceneLoader.setScene(new MarketScene(regionMarket));
            }
        });
        buyBtn.setTransform(true);
        buyBtn.setScale(0.5f);
        buyBtn.setOrigin(Align.center);
        restoreHP.add(buyBtn);
        return restoreHP;
    }

    public Table shipCargo() {
        Ship ship = player.getShip();

        Table shipCargoTable = new Table();
        shipCargoTable.add(label("-- Ship's Cargo --", Color.WHITE, SECTION_SCALE)).colspan(ship.getCargo());
        shipCargoTable.row();

        for (int i = 0; i < ship.getTotalItems(); i++) {
            Table itemOptions = new Table();
            Item item = ship.getItem(i);
            itemOptions.add(label(item.getName(), Color.WHITE, ITEM_NAME_SCALE)).pad(ITEM_SIDE_PADDING);
            itemOptions.row();
            Button itemSellBtn = textButton("Sell for " + item.getFinalSellPrice(), Color.YELLOW, () -> {
                player.credits += item.getFinalSellPrice();
                player.getShip().removeItem(item);
                sceneLoader.setScene(new MarketScene(regionMarket));
            });
            itemSellBtn.setTransform(true);
            itemSellBtn.setScale(0.5f);
            itemSellBtn.setOrigin(Align.top);
            itemOptions.add(itemSellBtn);
            shipCargoTable.add(itemOptions);
        }
        for (int c = ship.getTotalItems(); c < ship.getCargo(); c++) {
            shipCargoTable.add(label("Empty Slot\n#" + (c + 1), Color.WHITE, ITEM_NAME_SCALE)).pad(ITEM_SIDE_PADDING);
        }

        return shipCargoTable;
    }

    public Table upgradeSlots() {
        Ship ship = player.getShip();

        Table upgradeSlotsTable = new Table();
        upgradeSlotsTable.add(label("-- Upgrades --", Color.WHITE, 1.2f)).colspan(ship.getUpgradeSlots());
        upgradeSlotsTable.row();

        for (int i = 0; i < ship.getTotalUpgrades(); i++) {
            Table upgradeOptions = new Table();
            CharacterUpgrade upgrade = ship.getUpgrade(i);
            upgradeOptions.add(label(upgrade.getName(), Color.WHITE, ITEM_NAME_SCALE));
            upgradeOptions.row();
            upgradeOptions.add(label(
                    "(Tech: " + upgrade.getTechLevel()
                            + " | " + Skill.typeToStr(upgrade.getSkillType())
                            + " +" + upgrade.getIncAmount() + ")",
                    Color.WHITE, ITEM_DESC_SCALE));
            upgradeOptions.row();
            Button upgradeSellBtn = textButton("Sell for " + upgrade.getSellingPrice(), Color.YELLOW, () -> {
                player.credits += upgrade.getSellingPrice();
                upgrade.setEquipped(false);
                player.changeSkillLevel(upgrade.getSkillType(), -upgrade.getIncAmount());
                player.getShip().removeUpgrade(upgrade);
                sceneLoader.setScene(new MarketScene(regionMarket));
            });
            upgradeSellBtn.setTransform(true);
            upgradeSellBtn.setScale(0.5f);
            upgradeSellBtn.setOrigin(Align.top);
            upgradeOptions.add(upgradeSellBtn);
            upgradeSlotsTable.add(upgradeOptions);
        }
        for (int c = ship.getTotalUpgrades(); c < ship.getUpgradeSlots(); c++) {
            upgradeSlotsTable.add(label("Empty Slot\n#" + (c + 1), Color.WHITE, ITEM_NAME_SCALE)).pad(ITEM_SIDE_PADDING);
        }

        return upgradeSlotsTable;
    }
}
