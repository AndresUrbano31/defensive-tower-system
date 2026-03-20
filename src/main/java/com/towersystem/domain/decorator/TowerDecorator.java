package com.towersystem.domain.decorator;

import com.towersystem.domain.model.Tower;

/**
 * Abstract decorator class that implements the Tower interface.
 * This is the Decorator class in the Decorator pattern.
 * 
 * Following SOLID principles:
 * - Single Responsibility: Manages decoration of towers
 * - Liskov Substitution: Can replace any Tower implementation
 * - Open/Closed: Can be extended without modification
 */
public abstract class TowerDecorator implements Tower {
    
    protected final Tower tower;
    
    /**
     * Constructor for TowerDecorator
     * @param tower the tower to be decorated
     */
    public TowerDecorator(Tower tower) {
        this.tower = tower;
    }
    
    @Override
    public void attack() {
        tower.attack();
    }
    
    @Override
    public int getDamage() {
        return tower.getDamage();
    }
    
    @Override
    public int getFireRate() {
        return tower.getFireRate();
    }
    
    @Override
    public String getDescription() {
        return tower.getDescription();
    }
    
    @Override
    public int getCost() {
        return tower.getCost();
    }
    
    @Override
    public int getRange() {
        return tower.getRange();
    }
    
    @Override
    public String getName() {
        return tower.getName();
    }
}
