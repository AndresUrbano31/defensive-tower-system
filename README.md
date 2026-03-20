# 🏰 Defensive Tower System - Decorator Pattern Implementation

## 📋 Overview

A complete implementation of the **Decorator Pattern** for a defensive tower system similar to Plants vs Zombies or Clash Royale. This project demonstrates how to dynamically add enhancements to towers using decorators while maintaining clean architecture and SOLID principles.

## 🎯 Project Features

- **Decorator Pattern Implementation**: Dynamic tower enhancements without modifying existing code
- **Web Interface**: Interactive HTML/CSS frontend for tower management
- **REST API**: JSON-based endpoints for tower operations
- **Multiple Tower Types**: Basic, Sniper, and Cannon towers
- **Four Enhancement Decorators**: Rapid Fire, Freeze Shield, Gold Generator, Defensive Shield
- **Real-time Tower Management**: Create, upgrade, and simulate tower attacks

## 🏗️ Architecture

### Package Structure
```
com.towersystem/
├── domain/
│   ├── model/
│   │   ├── Tower.java              # Component interface
│   │   └── BaseTower.java          # Concrete component
│   └── decorator/
│       ├── TowerDecorator.java     # Abstract decorator
│       ├── RapidFireDecorator.java # Concrete decorator
│       ├── FreezeShieldDecorator.java
│       ├── GoldGeneratorDecorator.java
│       └── DefensiveShieldDecorator.java
├── application/
│   └── TowerService.java          # Business logic layer
├── infrastructure/
│   └── WebServer.java             # HTTP server implementation
└── Main.java                      # Demo and entry point
```

### Decorator Pattern Components

1. **Component** (`Tower` interface): Defines the contract for all towers
2. **ConcreteComponent** (`BaseTower`): Basic tower implementation
3. **Decorator** (`TowerDecorator`): Abstract decorator maintaining tower reference
4. **ConcreteDecorators**: Specific enhancements that can be stacked

## 🎨 Available Decorators

| Decorator | Enhancement | Cost Increase | Special Effect |
|-----------|-------------|----------------|----------------|
| 🔥 Rapid Fire | +2 fire rate | +20 gold | Additional shots |
| ❄️ Freeze Shield | 3s enemy freeze | +30 gold | Slows enemies |
| 💰 Gold Generator | +5 gold/attack | +50 gold | Generates resources |
| 🛡️ Defensive Shield | +50 defense, +10 range | +40 gold | Increases durability |

## 🚀 How to Run

### Prerequisites
- Java 21 or higher
- Maven 3.6 or higher

### Steps

1. **Compile the project**:
```bash
mvn compile
```

2. **Run the application**:
```bash
mvn exec:java -Dexec.mainClass="com.towersystem.Main"
```

3. **Open the web interface**:
   Navigate to `http://localhost:8080` in your browser

### Alternative: Run directly from IDE
Run the `Main.java` class from your IDE (IntelliJ, Eclipse, etc.)

## 🎮 Usage Examples

### Console Demo
```java
// Create basic tower
Tower tower = new BaseTower("Alpha Tower", 10, 1, 100, 50);

// Apply decorators dynamically
Tower enhancedTower = new DefensiveShieldDecorator(
    new GoldGeneratorDecorator(
        new RapidFireDecorator(tower)
    )
);

// Use the enhanced tower
enhancedTower.attack();
System.out.println(enhancedTower.getDescription());
```

### Web Interface
1. Create towers with custom names
2. Apply upgrades in real-time
3. Simulate tower attacks
4. View combined effects

## 📐 SOLID Principles Applied

### ✅ Single Responsibility Principle (SRP)
- Each decorator has one specific enhancement responsibility
- `TowerService` handles only business logic
- `WebServer` manages only HTTP operations

### ✅ Open/Closed Principle (OCP)
- System is open for extension (new decorators)
- Closed for modification (no changes to existing code)
- New tower types can be added without modifying decorators

### ✅ Liskov Substitution Principle (LSP)
- Decorators can replace any Tower implementation
- All decorators maintain Tower interface contract
- No breaking changes when substituting implementations

### ✅ Interface Segregation Principle (ISP)
- Tower interface contains only essential methods
- No fat interfaces with unused methods
- Clients depend only on methods they use

### ✅ Dependency Inversion Principle (DIP)
- High-level modules depend on Tower abstraction
- Low-level modules implement Tower interface
- Dependency injection through constructors

## 🔄 Decorator Pattern Benefits

1. **Dynamic Behavior**: Add/remove enhancements at runtime
2. **Flexibility**: Combine multiple decorators in any order
3. **Extensibility**: Easy to add new enhancement types
4. **Maintainability**: Each decorator is self-contained
5. **Testability**: Individual decorators can be tested separately
6. **Composition over Inheritance**: Favors object composition

## 🌐 API Endpoints

| Endpoint | Method | Description |
|----------|--------|-------------|
| `/` | GET | Main HTML interface |
| `/api/towers` | GET | Get all towers |
| `/api/tower/create` | POST | Create new tower |
| `/api/tower/upgrade` | POST | Upgrade tower |
| `/api/tower/attack` | POST | Simulate attack |

## 📊 Example Tower Combinations

### Basic Tower
```
Basic Alpha Tower (Damage: 10, Fire Rate: 1, Range: 50, Cost: 100 gold)
```

### Fully Enhanced Tower
```
Basic Alpha Tower (Damage: 10, Fire Rate: 1, Range: 50, Cost: 100 gold) 
+ [Rapid Fire: +2 fire rate] 
+ [Freeze Shield: 3s enemy freeze] 
+ [Gold Generator: +5 gold/attack] 
+ [Defensive Shield: +50 defense, +10 range]
```

**Final Stats**: Damage: 10, Fire Rate: 3, Range: 60, Cost: 240 gold

## 🛠️ Technology Stack

- **Backend**: Java 21
- **Web Server**: com.sun.net.httpserver.HttpServer
- **Frontend**: HTML5 + CSS3 (No JavaScript)
- **Build Tool**: Maven
- **Architecture**: Clean Architecture with Domain-Driven Design

## 📝 Design Decisions

1. **No Frameworks**: Uses only Java standard library as requested
2. **HTML/CSS Only**: Frontend without JavaScript for simplicity
3. **Clean Architecture**: Separation of concerns across layers
4. **Decorator Focus**: Emphasizes pattern learning over complexity
5. **Interactive Demo**: Web interface for real-time experimentation

## 🎓 Learning Objectives

This project demonstrates:
- Decorator Pattern implementation in Java
- SOLID principles application
- Clean architecture practices
- REST API design without frameworks
- Dynamic behavior composition
- Object-oriented design principles

## 🔧 Extension Ideas

1. **New Decorators**: Poison, Area Damage, Life Steal
2. **Enemy System**: Target enemies with different resistances
3. **Game Loop**: Wave-based gameplay
4. **Database**: Persistent tower storage
5. **Multiplayer**: Shared tower management

---

**Author**: AI Assistant - Software Engineering Expert  
**Pattern**: Decorator Design Pattern  
**Language**: Java 21  
**Architecture**: Clean Architecture + SOLID Principles
