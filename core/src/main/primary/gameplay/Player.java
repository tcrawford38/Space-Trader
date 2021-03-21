package main.primary.gameplay;

import java.util.EnumMap;

import main.primary.gameplay.Skill.SkillType;

public class Player {

    private final String name;
    public int credits; // Credits is used enough to the point where making it public is fine
    private int skillPoints;
    private final Ship ship;

    private final EnumMap<SkillType, Skill> skillLvls;

    public Player(String name, int credits, int skillPoints) {
        this.name = name;
        this.credits = credits;
        this.skillPoints = skillPoints;
        this.ship = new Ship();

        this.skillLvls = new EnumMap<>(SkillType.class);
        for (SkillType skill : SkillType.values()) {
            skillLvls.put(skill, new Skill());
        }
    }

    public String getName() {
        return name;
    }

    public Ship getShip() {
        return ship;
    }

    public int getSkillPoints() {
        return skillPoints;
    }

    public int getSkillLevel(SkillType type) {
        return skillLvls.get(type).getValue();
    }

    public void convertPointsToLevel(int amount, SkillType type) {
        Skill skillLevel = skillLvls.get(type);
        int adjustedAmt = Math.max(Math.min(skillPoints, amount), -skillLevel.getValue());
        this.skillPoints -= adjustedAmt;
        skillLevel.inc(adjustedAmt);
    }

    public void changeSkillLevel(SkillType type, int amount) {
        skillLvls.get(type).inc(amount);
    }

    public void resetSkillPoints() {
        for (Skill skill : skillLvls.values()) {
            this.skillPoints += skill.getValue();
            skill.setValue(0);
        }
    }

    public boolean rollSkillCheck(SkillType skillType) {
        return skillLvls.get(skillType).rollSkillCheck();
    }

    public String generateStatsBar() {
        String statsBar = "";
        statsBar += "Credits: " + credits + " | ";
        for (SkillType skillType : SkillType.values()) {
            statsBar += Skill.typeToStr(skillType) + ": " + getSkillLevel(skillType) + "  ";
        }
        statsBar += "| ";
        statsBar += "Ship: " + ship.getName() + "  ";
        statsBar += "Fuel: " + ship.fuel + "  ";
        statsBar += "HP: " + ship.getHP() + "/" + ship.getMaxHP() + "  ";
        statsBar += "Attack: " + ship.getAttack() + "  ";
        statsBar += "Capacity: " + ship.getTotalItems() + "/" + ship.getCargo() + "  ";
        statsBar += "Upgrades: " + ship.getTotalUpgrades() + "/" + ship.getUpgradeSlots() + "  ";
        return statsBar;
    }
}
