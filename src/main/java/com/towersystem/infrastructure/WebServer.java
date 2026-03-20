package com.towersystem.infrastructure;

import com.towersystem.application.TowerService;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Web server implementation using com.sun.net.httpserver.HttpServer
 * Provides REST API endpoints for tower management
 * 
 * Following SOLID principles:
 * - Single Responsibility: Handles HTTP requests and responses
 * - Dependency Inversion: Depends on TowerService abstraction
 */
public class WebServer {
    
    private static final int PORT = 8080;
    private HttpServer server;
    private final TowerService towerService;
    
    public WebServer(TowerService towerService) {
        this.towerService = towerService;
    }
    
    /**
     * Starts the web server
     * @throws IOException if server cannot be started
     */
    public void start() throws IOException {
        server = HttpServer.create(new InetSocketAddress(PORT), 0);
        
        // Register handlers
        server.createContext("/", new HomeHandler());
        server.createContext("/api/towers", new TowersHandler());
        server.createContext("/api/tower/create", new CreateTowerHandler());
        server.createContext("/api/tower/upgrade", new UpgradeTowerHandler());
        server.createContext("/api/tower/attack", new AttackTowerHandler());
        
        server.setExecutor(null);
        server.start();
        System.out.println("Server started on port " + PORT);
        System.out.println("Open http://localhost:" + PORT + " in your browser");
    }
    
    /**
     * Stops the web server
     */
    public void stop() {
        if (server != null) {
            server.stop(0);
            System.out.println("Server stopped");
        }
    }
    
    /**
     * Handler for serving the main HTML page
     */
    private class HomeHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String htmlPath = "src/main/resources/web/index.html";
                String content = new String(Files.readAllBytes(Paths.get(htmlPath)));
                
                exchange.getResponseHeaders().set("Content-Type", "text/html; charset=UTF-8");
                exchange.sendResponseHeaders(200, content.getBytes().length);
                
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(content.getBytes());
                }
            }
        }
    }
    
    /**
     * Handler for getting all towers
     */
    private class TowersHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("GET".equals(exchange.getRequestMethod())) {
                String response = towerService.getAllTowersJson();
                
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }
    
    /**
     * Handler for creating a new tower with player support
     */
    private class CreateTowerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> params = parseQueryParams(exchange.getRequestURI().getQuery());
                String towerType = params.get("type");
                String towerName = params.get("name");
                int playerId = Integer.parseInt(params.getOrDefault("player", "1"));
                
                String response = towerService.createTower(towerType, towerName, playerId);
                
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }
    
    /**
     * Handler for upgrading a tower with player support
     */
    private class UpgradeTowerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> params = parseQueryParams(exchange.getRequestURI().getQuery());
                int towerId = Integer.parseInt(params.get("id"));
                String upgradeType = params.get("upgrade");
                int playerId = Integer.parseInt(params.getOrDefault("player", "1"));
                
                String response = towerService.upgradeTower(towerId, upgradeType, playerId);
                
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }
    
    /**
     * Handler for tower attack simulation with player support
     */
    private class AttackTowerHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                Map<String, String> params = parseQueryParams(exchange.getRequestURI().getQuery());
                int towerId = Integer.parseInt(params.get("id"));
                int playerId = Integer.parseInt(params.getOrDefault("player", "1"));
                
                String response = towerService.attackWithTower(towerId, playerId);
                
                exchange.getResponseHeaders().set("Content-Type", "application/json");
                exchange.sendResponseHeaders(200, response.getBytes().length);
                
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }
    }
    
    /**
     * Parses query parameters from URI
     * @param query the query string
     * @return map of parameters
     */
    private Map<String, String> parseQueryParams(String query) {
        Map<String, String> params = new HashMap<>();
        if (query != null) {
            String[] pairs = query.split("&");
            for (String pair : pairs) {
                String[] keyValue = pair.split("=");
                if (keyValue.length == 2) {
                    params.put(keyValue[0], keyValue[1]);
                }
            }
        }
        return params;
    }
}
