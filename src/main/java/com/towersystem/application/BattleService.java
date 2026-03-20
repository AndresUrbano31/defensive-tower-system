package com.towersystem.application;

import com.towersystem.domain.model.Tower;
import java.util.*;

/**
 * Service for managing battle mechanics between two players
 * Handles turn-based combat, tower positioning, and battle resolution
 * 
 * Following SOLID principles:
 * - Single Responsibility: Manages battle logic only
 * - Open/Closed: Can be extended with new battle mechanics
 */
public class BattleService {
    
    private final TowerService towerService;
    private final Map<String, Object> battlefield;
    private boolean battleInProgress = false;
    private int currentTurn = 1;
    private List<String> battleLog;
    
    public BattleService(TowerService towerService) {
        this.towerService = towerService;
        this.battlefield = new HashMap<>();
        this.battleLog = new ArrayList<>();
    }
    
    /**
     * Starts a new battle between two players
     * @return JSON response with battle start result
     */
    public String startBattle() {
        if (battleInProgress) {
            return "{\"success\": false, \"message\": \"Battle already in progress\"}";
        }
        
        // Check if both players have towers
        if (!hasRequiredTowers()) {
            return "{\"success\": false, \"message\": \"Both players must have at least one tower\"}";
        }
        
        battleInProgress = true;
        currentTurn = 1;
        battleLog.clear();
        battleLog.add("⚔️ Battle started! Player 1's turn.");
        
        return String.format(
            "{\"success\": true, \"message\": \"Battle started\", \"currentTurn\": %d, \"battleLog\": \"%s\"}",
            currentTurn, String.join("\\n", battleLog)
        );
    }
    
    /**
     * Resets the current battle
     * @return JSON response with reset result
     */
    public String resetBattle() {
        battleInProgress = false;
        currentTurn = 1;
        battleLog.clear();
        battlefield.clear();
        
        return "{\"success\": true, \"message\": \"Battle reset\", \"currentTurn\": 1}";
    }
    
    /**
     * Executes a tower attack in battle
     * @param attackerPlayerId attacking player
     * @param attackerTowerId attacking tower
     * @param targetRow target row position
     * @param targetCol target column position
     * @return JSON response with attack result
     */
    public String executeBattleAttack(int attackerPlayerId, int attackerTowerId, int targetRow, int targetCol) {
        if (!battleInProgress) {
            return "{\"success\": false, \"message\": \"No battle in progress\"}";
        }
        
        if (currentTurn != attackerPlayerId) {
            return String.format(
                "{\"success\": false, \"message\": \"Not your turn. Player %d's turn\"}", 
                currentTurn
            );
        }
        
        // Get attacking tower
        Tower attackingTower = getTowerById(attackerPlayerId, attackerTowerId);
        if (attackingTower == null) {
            return "{\"success\": false, \"message\": \"Attacking tower not found\"}";
        }
        
        // Check if target is in range
        if (!isTargetInRange(attackingTower, targetRow, targetCol)) {
            return "{\"success\": false, \"message\": \"Target out of range\"}";
        }
        
        // Execute attack
        String attackResult = performAttack(attackingTower, attackerPlayerId, targetRow, targetCol);
        
        // Switch turns
        currentTurn = currentTurn == 1 ? 2 : 1;
        battleLog.add(String.format("🔄 Turn switched to Player %d", currentTurn));
        
        // Check for victory
        String victoryCheck = checkVictory();
        if (!victoryCheck.equals("continue")) {
            battleInProgress = false;
            return String.format(
                "{\"success\": true, \"message\": \"Battle ended\", \"winner\": %s, \"battleLog\": \"%s\"}",
                victoryCheck, String.join("\\n", battleLog)
            );
        }
        
        return String.format(
            "{\"success\": true, \"message\": \"Attack executed\", \"currentTurn\": %d, \"battleLog\": \"%s\"}",
            currentTurn, String.join("\\n", battleLog)
        );
    }
    
    /**
     * Places a tower on the battlefield
     * @param playerId player placing the tower
     * @param towerId tower to place
     * @param row row position
     * @param col column position
     * @return JSON response with placement result
     */
    public String placeTower(int playerId, int towerId, int row, int col) {
        if (battleInProgress) {
            return "{\"success\": false, \"message\": \"Cannot place towers during battle\"}";
        }
        
        // Check if position is valid for player
        if (!isValidPosition(playerId, row, col)) {
            return "{\"success\": false, \"message\": \"Invalid position for this player\"}";
        }
        
        // Check if position is occupied
        String positionKey = row + "," + col;
        if (battlefield.containsKey(positionKey)) {
            return "{\"success\": false, \"message\": \"Position already occupied\"}";
        }
        
        // Get tower
        Tower tower = getTowerById(playerId, towerId);
        if (tower == null) {
            return "{\"success\": false, \"message\": \"Tower not found\"}";
        }
        
        // Place tower
        battlefield.put(positionKey, new BattleTower(playerId, towerId, tower, 100 + tower.getCost()));
        
        return String.format(
            "{\"success\": true, \"message\": \"Tower placed at (%d,%d)\", \"playerId\": %d, \"towerId\": %d}",
            row, col, playerId, towerId
        );
    }
    
    /**
     * Gets current battle status
     * @return JSON response with battle status
     */
    public String getBattleStatus() {
        return String.format(
            "{\"battleInProgress\": %b, \"currentTurn\": %d, \"battleLog\": \"%s\"}",
            battleInProgress, currentTurn, String.join("\\n", battleLog)
        );
    }
    
    /**
     * Gets battlefield state
     * @return JSON response with battlefield
     */
    public String getBattlefieldState() {
        StringBuilder json = new StringBuilder();
        json.append("{\"battlefield\": [");
        
        boolean first = true;
        for (Map.Entry<String, Object> entry : battlefield.entrySet()) {
            if (!first) {
                json.append(",");
            }
            first = false;
            
            String[] pos = entry.getKey().split(",");
            BattleTower battleTower = (BattleTower) entry.getValue();
            
            json.append("{");
            json.append("\"row\": ").append(pos[0]).append(",");
            json.append("\"col\": ").append(pos[1]).append(",");
            json.append("\"playerId\": ").append(battleTower.playerId).append(",");
            json.append("\"towerId\": ").append(battleTower.towerId).append(",");
            json.append("\"towerName\": \"").append(battleTower.tower.getName()).append("\",");
            json.append("\"health\": ").append(battleTower.health);
            json.append("}");
        }
        
        json.append("]}");
        return json.toString();
    }
    
    // Private helper methods
    
    private boolean hasRequiredTowers() {
        // This would check with TowerService if both players have towers
        // For now, return true as a placeholder
        return true;
    }
    
    private Tower getTowerById(int playerId, int towerId) {
        // This would get the tower from TowerService
        // For now, return null as placeholder
        return null;
    }
    
    private boolean isTargetInRange(Tower tower, int targetRow, int targetCol) {
        // Simple range check - would need actual tower position
        int range = tower.getRange();
        // Placeholder logic
        return true;
    }
    
    private String performAttack(Tower attackingTower, int attackerPlayerId, int targetRow, int targetCol) {
        String positionKey = targetRow + "," + targetCol;
        BattleTower targetTower = (BattleTower) battlefield.get(positionKey);
        
        if (targetTower == null) {
            battleLog.add(String.format("💥 Player %d's %s attacks position (%d,%d) - no target", 
                attackerPlayerId, attackingTower.getName(), targetRow, targetCol));
            return "No target at position";
        }
        
        // Apply damage
        int damage = attackingTower.getDamage();
        targetTower.health -= damage;
        
        battleLog.add(String.format("⚔️ Player %d's %s attacks Player %d's %s for %d damage", 
            attackerPlayerId, attackingTower.getName(), 
            targetTower.playerId, targetTower.tower.getName(), damage));
        
        if (targetTower.health <= 0) {
            battlefield.remove(positionKey);
            battleLog.add(String.format("💀 Player %d's %s destroyed!", 
                targetTower.playerId, targetTower.tower.getName()));
        }
        
        return "Attack executed";
    }
    
    private boolean isValidPosition(int playerId, int row, int col) {
        // Player 1: columns 0-2, Player 2: columns 7-9
        if (playerId == 1) {
            return col >= 0 && col <= 2 && row >= 0 && row <= 7;
        } else {
            return col >= 7 && col <= 9 && row >= 0 && row <= 7;
        }
    }
    
    private String checkVictory() {
        int player1Towers = 0;
        int player2Towers = 0;
        
        for (Object obj : battlefield.values()) {
            BattleTower battleTower = (BattleTower) obj;
            if (battleTower.playerId == 1) {
                player1Towers++;
            } else {
                player2Towers++;
            }
        }
        
        if (player1Towers == 0) {
            battleLog.add("🏆 Player 2 wins! All Player 1 towers destroyed!");
            return "2";
        } else if (player2Towers == 0) {
            battleLog.add("🏆 Player 1 wins! All Player 2 towers destroyed!");
            return "1";
        }
        
        return "continue";
    }
    
    /**
     * Inner class to represent a tower in battle
     */
    private static class BattleTower {
        int playerId;
        int towerId;
        Tower tower;
        int health;
        
        BattleTower(int playerId, int towerId, Tower tower, int health) {
            this.playerId = playerId;
            this.towerId = towerId;
            this.tower = tower;
            this.health = health;
        }
    }
}
