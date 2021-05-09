package main.primary.gameplay;

import main.primary.Global;

import java.util.Random;

/**
 * This class creates a Item Object
 */
public class Item {

    private String name;
    private String description;
    private int price;
    private int techLevel;
    private int adjustedPrice;
    private int adjustedSellingPrice;
    private int finalSellPrice;
    private int age;

    /**
     * Constructor for Item class
     */
    public Item() {
        Random rand = Global.app.rand;
        String[] itemDescList = Global.app.itemDescriptions;
        String itemDesc = itemDescList[rand.nextInt(itemDescList.length)];
        String[] itemProps = itemDesc.split(":");

        name = itemProps[0];
        description = itemProps[1];
        price = Integer.parseInt(itemProps[2]);
        techLevel = Integer.parseInt(itemProps[3]);
        age = 1;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getPrice() {
        return price;
    }

    public int getTechLevel() {
        return techLevel;
    }

    public int getAdjustedPrice() {
        return adjustedPrice;
    }

    public void setAdjustedPrice(int adjustedPrice) {
        this.adjustedPrice = adjustedPrice;
    }

    public int getAdjustedSellingPrice() {
        return adjustedSellingPrice;
    }

    public void setAdjustedSellingPrice(int adjustedSellingPrice) {
        this.adjustedSellingPrice = adjustedSellingPrice;
    }

    public int getFinalSellPrice() {
        return finalSellPrice;
    }

    public void setFinalSellPrice(int finalSellPrice) {
        this.finalSellPrice = finalSellPrice;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 5) {
            return;
        }
        this.age = age;
    }
}
