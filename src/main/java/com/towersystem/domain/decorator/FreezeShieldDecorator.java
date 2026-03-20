package com.towersystem.domain.decorator;

import com.towersystem.domain.model.Tower;

/**
 * Concrete decorator that adds freeze shield capability to a tower.
 * Slows down enemies and adds defensive capabilities.
 * Adds 30 gold cost.
 * 
 * Following SOLID principles:
 * - Single Responsibility: Only handles freeze shield enhancement
 * - Open/Closed: Can be combined with other decorators
 */
public class FreezeShieldDecorator extends TowerDecorator {
    
    private static final int COST_INCREASE = 30;
    private static final int FREEZE_DURATION = 3;
    
    /**
     * Constructor for FreezeShieldDecorator
     * @param tower the tower to enhance with freeze shield
     */
    public FreezeShieldDecorator(Tower tower) {
        super(tower);
    }
    
    @Override
    public void attack() {
        System.out.println("❄️ FREEZE SHIELD ACTIVATED!");
        super.attack();
        System.out.println("Enemies frozen for " + FREEZE_DURATION + " seconds!");
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " + [Freeze Shield: " + FREEZE_DURATION + "s enemy freeze]";
    }
    
    @Override
    public int getCost() {
        return super.getCost() + COST_INCREASE;
    }
    
    /**
     * Gets the freeze duration
     * @return freeze duration in seconds
     */
    public int getFreezeDuration() {
        return FREEZE_DURATION;
    }
}
