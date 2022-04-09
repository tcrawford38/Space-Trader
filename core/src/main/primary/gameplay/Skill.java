package main.primary.gameplay;

import main.primary.Global;

import java.util.Random;

/**
 * Simple wrapper class for a skill level value
 */
public class Skill implements java.io.Serializable {

    public enum SkillType {
        PILOT,
        FIGHTER,
        MERCHANT,
        ENGINEER,
    }

    public static String typeToStr(SkillType type) {
        switch (type) {
            case PILOT:
                return "Pilot";
            case FIGHTER:
                return "Fighter";
            case MERCHANT:
                return "Merchant";
            case ENGINEER:
                return "Engineer";
        }
        return "[UNKNOWN_SKILL_TYPE]";
    }

    private static final int SKILLCHECK_MULTIPLIER = 10;

    private int value;

    public boolean rollSkillCheck() {
        Random rand = Global.app.rand;
        int difficultyRank = Global.app.difficulty.ordinal();
        return rand.nextInt(value + 1) * SKILLCHECK_MULTIPLIER > difficultyRank * 40 + 40;
    }

    public void setValue(int val) {
        this.value = Math.max(val, 0);
    }

    public void inc(int amount) {
        this.setValue(value + amount);
    }

    public int getValue() {
        return value;
    }
}
