package com.towersystem;

import com.towersystem.domain.model.Tower;
import com.towersystem.domain.model.BaseTower;
import com.towersystem.domain.decorator.*;
import com.towersystem.application.TowerService;
import com.towersystem.infrastructure.WebServer;

/**
 * Main class demonstrating the Decorator Pattern implementation
 * for a Defensive Tower System.
 * 
 * This class shows:
 * 1. How to create base towers
 * 2. How to apply decorators dynamically
 * 3. How decorators can be stacked
 * 4. Web server startup for interactive demo
 * 
 * Following SOLID principles:
 * - Single Responsibility: Demonstrates pattern usage
 * - Dependency Inversion: Works with abstractions
 */
public class Main {
    
    public static void main(String[] args) {
        System.out.println("🏰 Defensive Tower System - Decorator Pattern Demo");
        System.out.println("============================================================");
        
        // Demonstrate decorator pattern usage
        demonstrateDecoratorPattern();
        
        // Start web server for interactive demo
        startWebServer();
    }
    
    /**
     * Demonstrates the Decorator Pattern implementation
     */
    private static void demonstrateDecoratorPattern() {
        System.out.println("\n📋 DEMOSTRACIÓN DEL PATRÓN DECORATOR");
        System.out.println("--------------------------------------------------");
        
        // 1. Create a basic tower
        Tower basicTower = new BaseTower("Alpha Tower", 10, 1, 100, 50);
        System.out.println("\n1. Torre Básica:");
        System.out.println("   " + basicTower.getDescription());
        basicTower.attack();
        
        // 2. Add Rapid Fire decorator
        Tower rapidFireTower = new RapidFireDecorator(basicTower);
        System.out.println("\n2. Torre con Rapid Fire:");
        System.out.println("   " + rapidFireTower.getDescription());
        rapidFireTower.attack();
        
        // 3. Add Freeze Shield decorator (stacking)
        Tower frozenRapidTower = new FreezeShieldDecorator(rapidFireTower);
        System.out.println("\n3. Torre con Rapid Fire + Freeze Shield:");
        System.out.println("   " + frozenRapidTower.getDescription());
        frozenRapidTower.attack();
        
        // 4. Add Gold Generator decorator (further stacking)
        Tower goldTower = new GoldGeneratorDecorator(frozenRapidTower);
        System.out.println("\n4. Torre con Rapid Fire + Freeze Shield + Gold Generator:");
        System.out.println("   " + goldTower.getDescription());
        goldTower.attack();
        
        // 5. Add Defensive Shield decorator (maximum stacking)
        Tower ultimateTower = new DefensiveShieldDecorator(goldTower);
        System.out.println("\n5. Torre Ultimate (todos los decorators):");
        System.out.println("   " + ultimateTower.getDescription());
        ultimateTower.attack();
        
        // 6. Show final stats
        System.out.println("\n📊 ESTADÍSTICAS FINALES:");
        System.out.println("   Daño: " + ultimateTower.getDamage());
        System.out.println("   Frecuencia de fuego: " + ultimateTower.getFireRate());
        System.out.println("   Rango: " + ultimateTower.getRange());
        System.out.println("   Costo total: " + ultimateTower.getCost() + " oro");
        
        // 7. Demonstrate decorator flexibility
        System.out.println("\n🔄 FLEXIBILIDAD DEL PATRÓN:");
        Tower anotherTower = new BaseTower("Beta Tower", 15, 2, 120, 60);
        Tower customTower = new DefensiveShieldDecorator(
            new GoldGeneratorDecorator(
                new RapidFireDecorator(anotherTower)
            )
        );
        System.out.println("   Torre personalizada (orden diferente):");
        System.out.println("   " + customTower.getDescription());
        
        // 8. Show pattern benefits
        System.out.println("\n✅ BENEFICIOS DEL PATRÓN DECORATOR:");
        System.out.println("   ✓ Comportamiento dinámico sin modificar clases existentes");
        System.out.println("   ✓ Múltiples mejoras pueden combinarse");
        System.out.println("   ✓ Cada decorador tiene una responsabilidad única");
        System.out.println("   ✓ Fácil añadir nuevos tipos de mejoras");
        System.out.println("   ✓ Sigue principios SOLID (Open/Closed, Single Responsibility)");
    }
    
    /**
     * Starts the web server for interactive demo
     */
    private static void startWebServer() {
        System.out.println("\n🌐 INICIANDO SERVIDOR WEB INTERACTIVO");
        System.out.println("--------------------------------------------------");
        
        TowerService towerService = new TowerService();
        WebServer webServer = new WebServer(towerService);
        
        try {
            webServer.start();
            
            System.out.println("\n📌 INSTRUCCIONES:");
            System.out.println("   1. Abre tu navegador web");
            System.out.println("   2. Ve a: http://localhost:8080");
            System.out.println("   3. Crea torres y aplica mejoras dinámicamente");
            System.out.println("   4. Experimenta con diferentes combinaciones");
            System.out.println("\n   Presiona Ctrl+C en la consola para detener el servidor");
            
            // Keep server running
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("\n🛑 Deteniendo servidor web...");
                webServer.stop();
            }));
            
        } catch (Exception e) {
            System.err.println("❌ Error al iniciar el servidor web: " + e.getMessage());
            e.printStackTrace();
        }
    }
}