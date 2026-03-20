package com.towersystem.domain.decorator;

import com.towersystem.domain.model.Tower;

/**
 * Concrete decorator that adds rapid fire capability to a tower.
 * Increases the fire rate by 50% and adds 20 gold cost.
 * 
 * Following SOLID principles:
 * - Single Responsibility: Only handles rapid fire enhancement
 * - Open/Closed: Can be combined with other decorators
 */
public class RapidFireDecorator extends TowerDecorator {
    
    private static final int FIRE_RATE_BOOST = 2;
    private static final int COST_INCREASE = 20;
    
    /**
     * Constructor for RapidFireDecorator
     * @param tower the tower to enhance with rapid fire
     */
    public RapidFireDecorator(Tower tower) {
        super(tower);
    }
    
    @Override
    public void attack() {
        System.out.println("🔥 RAPID FIRE ACTIVATED!");
        super.attack();
        System.out.println("Additional shots fired due to rapid fire!");
    }
    
    @Override
    public int getFireRate() {
        return super.getFireRate() + FIRE_RATE_BOOST;
    }
    
    @Override
    public String getDescription() {
        return super.getDescription() + " + [Rapid Fire: +" + FIRE_RATE_BOOST + " fire rate]";
    }
    
    @Override
    public int getCost() {
        return super.getCost() + COST_INCREASE;
    }
}
