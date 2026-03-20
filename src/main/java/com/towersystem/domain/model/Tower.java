package com.towersystem.domain.model;

/**
 * Interface representing a defensive tower in the game.
 * This is the Component interface in the Decorator pattern.
 * 
 * Following SOLID principles:
 * - Interface Segregation: Contains only essential tower methods
 * - Dependency Inversion: High-level modules depend on this abstraction
 */
public interface Tower {
    
    /**
     * Performs an attack action
     */
    void attack();
    
    /**
     * Gets the damage value of the tower
     * @return damage amount
     */
    int getDamage();
    
    /**
     * Gets the fire rate (attacks per second)
     * @return fire rate value
     */
    int getFireRate();
    
    /**
     * Gets a description of the tower and its enhancements
     * @return description string
     */
    String getDescription();
    
    /**
     * Gets the cost of the tower
     * @return cost value
     */
    int getCost();
    
    /**
     * Gets the range of the tower
     * @return range value
     */
    int getRange();
    
    /**
     * Gets the name of the tower
     * @return tower name
     */
    String getName();
}
