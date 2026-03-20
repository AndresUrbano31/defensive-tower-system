package com.towersystem.domain.model;

/**
 * Concrete implementation of a basic defensive tower.
 * This is the ConcreteComponent in the Decorator pattern.
 * 
 * Following SOLID principles:
 * - Single Responsibility: Handles basic tower functionality only
 * - Open/Closed: Can be extended via decorators without modification
 */
public class BaseTower implements Tower {
    
    private final String name;
    private final int damage;
    private final int fireRate;
    private final int cost;
    private final int range;
    
    /**
     * Constructor for BaseTower
     * @param name tower name
     * @param damage damage value
     * @param fireRate attacks per second
     * @param cost gold cost
     * @param range attack range
     */
    public BaseTower(String name, int damage, int fireRate, int cost, int range) {
        this.name = name;
        this.damage = damage;
        this.fireRate = fireRate;
        this.cost = cost;
        this.range = range;
    }
    
    @Override
    public void attack() {
        System.out.println(name + " attacks with " + damage + " damage!");
        System.out.println("Fire rate: " + fireRate + " attacks/second");
        System.out.println("Range: " + range + " units");
    }
    
    @Override
    public int getDamage() {
        return damage;
    }
    
    @Override
    public int getFireRate() {
        return fireRate;
    }
    
    @Override
    public String getDescription() {
        return "Basic " + name + " (Damage: " + damage + 
               ", Fire Rate: " + fireRate + 
               ", Range: " + range + 
               ", Cost: " + cost + " gold)";
    }
    
    @Override
    public int getCost() {
        return cost;
    }
    
    @Override
    public int getRange() {
        return range;
    }
    
    @Override
    public String getName() {
        return name;
    }
}
