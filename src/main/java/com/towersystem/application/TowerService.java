package com.towersystem.application;

import com.towersystem.domain.model.Tower;
import com.towersystem.domain.model.BaseTower;
import com.towersystem.domain.decorator.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service layer for tower management operations
 * Implements business logic and coordinates tower operations
 * 
 * Following SOLID principles:
 * - Single Responsibility: Handles tower business logic
 * - Open/Closed: Can be extended with new tower types
 * - Dependency Inversion: Depends on Tower abstraction
 */
public class TowerService {
    
    private final Map<Integer, Tower> towers = new HashMap<>();
    private final AtomicInteger nextId = new AtomicInteger(1);
    
    /**
     * Creates a new tower
     * @param towerType type of tower to create
     * @param towerName name of the tower
     * @return JSON response with tower creation result
     */
    public String createTower(String towerType, String towerName) {
        try {
            Tower tower = createBaseTower(towerType, towerName);
            int id = nextId.getAndIncrement();
            towers.put(id, tower);
            
            return String.format(
                "{\"success\": true, \"towerId\": %d, \"message\": \"Tower '%s' created successfully\", \"tower\": {\"id\": %d, \"name\": \"%s\", \"description\": \"%s\", \"cost\": %d}}",
                id, towerName, id, tower.getName(), tower.getDescription().replace("\"", "\\\""), tower.getCost()
            );
        } catch (Exception e) {
            return String.format("{\"success\": false, \"message\": \"Error creating tower: %s\"}", e.getMessage());
        }
    }
    
    /**
     * Upgrades an existing tower with a decorator
     * @param towerId ID of tower to upgrade
     * @param upgradeType type of upgrade to apply
     * @return JSON response with upgrade result
     */
    public String upgradeTower(int towerId, String upgradeType) {
        Tower tower = towers.get(towerId);
        if (tower == null) {
            return "{\"success\": false, \"message\": \"Tower not found\"}";
        }
        
        try {
            Tower upgradedTower = applyUpgrade(tower, upgradeType);
            towers.put(towerId, upgradedTower);
            
            return String.format(
                "{\"success\": true, \"towerId\": %d, \"message\": \"Tower upgraded with %s\", \"tower\": {\"id\": %d, \"name\": \"%s\", \"description\": \"%s\", \"cost\": %d, \"damage\": %d, \"fireRate\": %d, \"range\": %d}}",
                towerId, upgradeType, towerId, upgradedTower.getName(), 
                upgradedTower.getDescription().replace("\"", "\\\""), 
                upgradedTower.getCost(), upgradedTower.getDamage(), 
                upgradedTower.getFireRate(), upgradedTower.getRange()
            );
        } catch (Exception e) {
            return String.format("{\"success\": false, \"message\": \"Error upgrading tower: %s\"}", e.getMessage());
        }
    }
    
    /**
     * Simulates tower attack
     * @param towerId ID of tower to attack with
     * @return JSON response with attack result
     */
    public String attackWithTower(int towerId) {
        Tower tower = towers.get(towerId);
        if (tower == null) {
            return "{\"success\": false, \"message\": \"Tower not found\"}";
        }
        
        // Capture attack output for response
        StringBuilder attackLog = new StringBuilder();
        attackLog.append("Tower ").append(tower.getName()).append(" attacks!\\n");
        attackLog.append("Damage: ").append(tower.getDamage()).append("\\n");
        attackLog.append("Fire Rate: ").append(tower.getFireRate()).append("\\n");
        attackLog.append("Range: ").append(tower.getRange()).append("\\n");
        
        // Perform attack
        tower.attack();
        
        return String.format(
            "{\"success\": true, \"towerId\": %d, \"message\": \"Attack executed\", \"attackLog\": \"%s\"}",
            towerId, attackLog.toString()
        );
    }
    
    /**
     * Gets all towers as JSON
     * @return JSON string with all towers
     */
    public String getAllTowersJson() {
        StringBuilder json = new StringBuilder();
        json.append("{\"towers\": [");
        
        boolean first = true;
        for (Map.Entry<Integer, Tower> entry : towers.entrySet()) {
            if (!first) {
                json.append(",");
            }
            first = false;
            
            Tower tower = entry.getValue();
            json.append("{");
            json.append("\"id\": ").append(entry.getKey()).append(",");
            json.append("\"name\": \"").append(tower.getName()).append("\",");
            json.append("\"description\": \"").append(tower.getDescription().replace("\"", "\\\"")).append("\",");
            json.append("\"cost\": ").append(tower.getCost()).append(",");
            json.append("\"damage\": ").append(tower.getDamage()).append(",");
            json.append("\"fireRate\": ").append(tower.getFireRate()).append(",");
            json.append("\"range\": ").append(tower.getRange());
            json.append("}");
        }
        
        json.append("]}");
        return json.toString();
    }
    
    /**
     * Creates a base tower of specified type
     * @param towerType type of tower
     * @param towerName name of tower
     * @return created tower
     */
    private Tower createBaseTower(String towerType, String towerName) {
        switch (towerType.toLowerCase()) {
            case "basic":
                return new BaseTower(towerName, 10, 1, 100, 50);
            case "sniper":
                return new BaseTower(towerName, 25, 0, 200, 100);
            case "cannon":
                return new BaseTower(towerName, 50, 0, 300, 30);
            default:
                throw new IllegalArgumentException("Unknown tower type: " + towerType);
        }
    }
    
    /**
     * Applies an upgrade to a tower
     * @param tower tower to upgrade
     * @param upgradeType type of upgrade
     * @return upgraded tower
     */
    private Tower applyUpgrade(Tower tower, String upgradeType) {
        switch (upgradeType.toLowerCase()) {
            case "rapidfire":
                return new RapidFireDecorator(tower);
            case "freezeshield":
                return new FreezeShieldDecorator(tower);
            case "goldgenerator":
                return new GoldGeneratorDecorator(tower);
            case "defensiveshield":
                return new DefensiveShieldDecorator(tower);
            default:
                throw new IllegalArgumentException("Unknown upgrade type: " + upgradeType);
        }
    }
}
