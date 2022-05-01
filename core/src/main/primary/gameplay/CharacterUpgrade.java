package main.primary.gameplay;

import main.primary.Global;
import main.primary.gameplay.Skill.SkillType;

import java.util.Random;

/**
 * This class creates a Character Upgrade Object
 * <p>
 * This class holds two extra variables than Item: skillID and incAmount
 */
public class CharacterUpgrade implements java.io.Serializable{

    public static final double DEPRECIATION = 0.5;
   
    private String name;
    private String description;
    private int price;
    private SkillType skillType;
    private int incAmount;
    private int adjustedPrice;
    private int techLevel;
    private int sellingPrice;
    private boolean equipped;

    private static final int[][] probabilities = new int[][]{
            {1, 1, 1, 1, 1, 1, 1, 1, 2, 2}, //0
            {1, 1, 1, 1, 1, 1, 2, 2, 2, 2},
            {1, 1, 1, 1, 1, 2, 2, 2, 2, 3},
            {1, 1, 1, 2, 2, 2, 2, 2, 3, 3},
            {1, 1, 2, 2, 2, 2, 2, 3, 3, 3} // 4
    };
    private static final String[] adjectives = new String[]{"Basic", "Advanced", "Supreme"};

    /**
     * Constructor for character upgrade
     * <p>
     * For skill ID's:
     * 0 represents Pilot
     * 1 represents Fighter
     * 2 represents Merchant
     * 3 represents Engineer
     * <p>
     * incAmount is how much the Upgrade increments a skill, dependent on a probability scheme
     * The probability is 60% for an increase of 1, 30% for an increase of 2,
     * and 10% for an increase of 3 The incAmount also will slightly increase
     * the price of a character upgrade: Increase of 100 credits for 1, 200 credits for 1,
     * and 300 credits for 3
     */
    public CharacterUpgrade() {
        Random rand = Global.app.rand;
        String[] upgradeDescList = Global.app.characterUpgradeDescriptions;
        String upgradeDesc = upgradeDescList[rand.nextInt(upgradeDescList.length)];
        String[] upgradeProps = upgradeDesc.split(":");

        this.techLevel = Integer.parseInt(upgradeProps[4]);
        int[] probability = probabilities[techLevel - 1];

        this.incAmount = probability[rand.nextInt(10)];
        this.name = adjectives[incAmount - 1] + upgradeProps[0];
        this.description = upgradeProps[1];
        this.price = Integer.parseInt(upgradeProps[2]) * incAmount * 4;
        this.sellingPrice = 0;
        this.equipped = false;

        int skillTypeIndex = Integer.parseInt(upgradeProps[3]);
        if (skillTypeIndex < 0 || skillTypeIndex >= 4) {
            throw new IllegalStateException("Unexpected value: " + skillType);
        }
        skillType = SkillType.values()[skillTypeIndex];
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

    public SkillType getSkillType() {
        return skillType;
    }

    public int getIncAmount() {
        return incAmount;
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

    public int getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(int sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}
