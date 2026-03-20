package com.towersystem.domain.decorator;

import com.towersystem.domain.model.Tower;

/**
 * Concrete decorator that adds defensive shield capability to a tower.
 * Increases tower durability and adds 40 gold cost.
 * 
 * Following SOLID principles:
 * - Single Responsibility: Only handles defensive shield enhancement
 * - Open/Closed: Can be combined with other decorators
 */
public class DefensiveShieldDecorator extends TowerDecorator {
    
    private static final int COST_INCREASE = 40;
    private static final int DEFENSE_BOOST = 50;
    private static final int RANGE_BOOST = 10;
    
    /**
     * Constructor for DefensiveShieldDecorator
     * @param tower the tower to enhance with defensive shield
     */
    public DefensiveShieldDecorator(Tower tower) {
        super(tower);
    }
    
    @Override
    public void attack() {
        System.out.println("🛡️ DEFENSIVE SHIELD ACTIVATED!");
        super.attack();
        System.out.println("Tower protected by energy shield!");
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " + [Defensive Shield: +" + DEFENSE_BOOST + " defense, +" + RANGE_BOOST + " range]";
    }
    
    @Override
    public int getCost() {
        return super.getCost() + COST_INCREASE;
    }
    
    @Override
    public int getRange() {
        return super.getRange() + RANGE_BOOST;
    }
    
    /**
     * Gets the defense value
     * @return defense value
     */
    public int getDefense() {
        return DEFENSE_BOOST;
    }
}
