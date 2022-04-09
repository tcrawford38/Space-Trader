package main.primary.gameplay;

import main.primary.Global;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * This class contains the items for each region's marketplace
 */
public class Market implements java.io.Serializable {

    public static final int ITEMS_PER_MARKET = 6;
    public static final double TECH_LEVEL_DISCOUNT_FACTOR = 0.1;
    public static final double DISCOUNT_MERCHANT_LEVEL = 3;
    public static final double MARKET_DEPRECIATION = 0.9;
    public static final double FUEL_COST_PER_UNIT = 1.5;
    public static final int FUEL_PER_JUG = 100;
    public static final int COST_PER_HP = 200;
    public static final int COST_WINNING_ITEM = 1000;

    private List<Item> itemsOffering;
    private CharacterUpgrade specialItem;

    private final Random rand;
    private final Region currentRegion;

    public Market(Region currentRegion) {
        this.rand = Global.app.rand;
        this.currentRegion = currentRegion;
    }

    public void generateMarket() {
        itemsOffering = new ArrayList<>();
        Item temp = null;
        for (int i = 0; i < ITEMS_PER_MARKET; i++) {
            boolean isNotValid = true;
            while (isNotValid) {
                temp = new Item();
                isNotValid = false;
                if (!itemsOffering.isEmpty()) {
                    for (Item item : itemsOffering) {
                        if (temp.getName().equals(item.getName())) {
                            isNotValid = true;
                            break;
                        }
                    }
                }
                if (temp.getTechLevel() > currentRegion.getTechLevel()) {
                    isNotValid = true;
                }
            }
            itemsOffering.add(temp);
            calculateAdjustedPrice(itemsOffering.get(i), currentRegion.getTechLevel());
            calculateAdjustedSellingPrice(itemsOffering.get(i), currentRegion.getTechLevel());
        }

        boolean isNotValidUpgrade = true;
        CharacterUpgrade tempUpgrade = null;
        while (isNotValidUpgrade) {
            tempUpgrade = new CharacterUpgrade();
            isNotValidUpgrade = tempUpgrade.getTechLevel() > currentRegion.getTechLevel();
        }
        specialItem = tempUpgrade;
    }

    //final price is going to be discounted based on the tech level
    // difference between item and region, plus a random
    //decrease or increase
    private void calculateAdjustedPrice(Item item, int regionTech) {
        item.setAdjustedPrice((int) (item.getPrice()
                - (regionTech - item.getTechLevel())
                * TECH_LEVEL_DISCOUNT_FACTOR * item.getPrice()
                + item.getPrice() * rand.nextDouble() / 5));
    }

    private void calculateAdjustedSellingPrice(Item item, int regionTech) {
        item.setAdjustedSellingPrice((int) (item.getPrice()
                - (regionTech - item.getTechLevel())
                * TECH_LEVEL_DISCOUNT_FACTOR * item.getPrice()
                + item.getPrice() * rand.nextDouble() / 5
                - (Math.log10(item.getAge()) < 1 ? Math.log10(item.getAge()) : 1) * item.getPrice() * 0.5));
    }

    private void calculateAdjustedPrice(CharacterUpgrade item, int regionTech) {
        item.setAdjustedPrice((int) (item.getPrice() - (regionTech - item.getTechLevel())
                * TECH_LEVEL_DISCOUNT_FACTOR * item.getPrice()
                + item.getPrice() * rand.nextDouble() / 5));
    }

    public List<Item> getItemsOffering() {
        return itemsOffering;
    }

    public Region getCurrentRegion() {
        return currentRegion;
    }

    public CharacterUpgrade getSpecialItem() {
        return specialItem;
    }

}