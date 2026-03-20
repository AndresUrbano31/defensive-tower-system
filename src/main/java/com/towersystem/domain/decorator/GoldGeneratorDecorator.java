package com.towersystem.domain.decorator;

import com.towersystem.domain.model.Tower;

/**
 * Concrete decorator that adds gold generation capability to a tower.
 * Generates 5 gold per attack cycle and adds 50 gold cost.
 * 
 * Following SOLID principles:
 * - Single Responsibility: Only handles gold generation enhancement
 * - Open/Closed: Can be combined with other decorators
 */
public class GoldGeneratorDecorator extends TowerDecorator {
    
    private static final int COST_INCREASE = 50;
    private static final int GOLD_PER_ATTACK = 5;
    private int totalGoldGenerated = 0;
    
    /**
     * Constructor for GoldGeneratorDecorator
     * @param tower the tower to enhance with gold generation
     */
    public GoldGeneratorDecorator(Tower tower) {
        super(tower);
    }
    
    @Override
    public void attack() {
        super.attack();
        int goldGenerated = generateGold();
        totalGoldGenerated += goldGenerated;
        System.out.println("💰 Gold Generator: +" + goldGenerated + " gold (Total: " + totalGoldGenerated + ")");
    }
    
    /**
     * Generates gold per attack
     * @return amount of gold generated
     */
    public int generateGold() {
        return GOLD_PER_ATTACK;
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " + [Gold Generator: +" + GOLD_PER_ATTACK + " gold/attack]";
    }
    
    @Override
    public int getCost() {
        return super.getCost() + COST_INCREASE;
    }
    
    /**
     * Gets the total gold generated
     * @return total gold generated
     */
    public int getTotalGoldGenerated() {
        return totalGoldGenerated;
    }
}
