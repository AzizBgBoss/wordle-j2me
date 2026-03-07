package com.azizbgboss.mapster;

import javax.microedition.lcdui.*;
import javax.microedition.lcdui.game.*;

public class GameCanvas extends javax.microedition.lcdui.Canvas implements Runnable {

    // --- Constants ---
    private static final int TILE_SIZE    = 32; // pixels
    private static final int MAP_COLS     = 32;
    private static final int MAP_ROWS     = 32;
    private static final int TARGET_FPS   = 20;
    private static final int FRAME_TIME   = 1000 / TARGET_FPS; // ms per frame

    // Player speed in pixels per frame
    private static final int PLAYER_SPEED = 4;  

    // --- Tile types ---
    private static final int TILE_FLOOR = 0;
    private static final int TILE_WALL  = 1;

    // --- Tile colors ---
    private static final int COLOR_FLOOR  = 0x888888;
    private static final int COLOR_WALL   = 0x333333;
    private static final int COLOR_PLAYER = 0x00FF00;
    private static final int COLOR_BG     = 0x000000;

    // --- Map ---
    // 0 = floor, 1 = wall
    private static final int[][] map = {
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
        {1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1},
    };

    // --- Player state ---
    private int playerX; // pixel position (top-left of player)
    private int playerY;

    // --- Camera ---
    private int camX;    // pixel offset of camera
    private int camY;

    // --- Input ---
    private boolean keyUp, keyDown, keyLeft, keyRight;

    // --- Game loop ---
    private Thread loopThread;
    private volatile boolean running;

    // --- MIDlet ref for exit ---
    private Mapster midlet;

    public GameCanvas(Mapster midlet) {
        this.midlet = midlet;
        setFullScreenMode(true);

        // Start player in the middle of the map (pixel coords)
        playerX = (MAP_COLS / 2) * TILE_SIZE;
        playerY = (MAP_ROWS / 2) * TILE_SIZE;
    }

    public void start() {
        running = true;
        loopThread = new Thread(this);
        loopThread.start();
    }

    public void stop() {
        running = false;
    }

    // --- Game Loop ---
    public void run() {
        while (running) {
            long startTime = System.currentTimeMillis();

            update();
            repaint();
            serviceRepaints(); // force paint() to execute now

            long elapsed = System.currentTimeMillis() - startTime;
            long sleep   = FRAME_TIME - elapsed;
            if (sleep > 0) {
                try { Thread.sleep(sleep); } catch (InterruptedException e) {}
            }
        }
    }

    // --- Update Logic ---
    private void update() {
        int dx = 0;
        int dy = 0;

        // Use numpad keys (2,4,6,8)
        if (keyDown)  dy =  PLAYER_SPEED;
        if (keyUp)    dy = -PLAYER_SPEED;
        if (keyLeft)  dx = -PLAYER_SPEED;
        if (keyRight) dx =  PLAYER_SPEED;

        // Move X, check collision
        if (dx != 0) {
            int newX = playerX + dx;
            if (!collidesWithMap(newX, playerY)) {
                playerX = newX;
            }
        }

        // Move Y, check collision
        if (dy != 0) {
            int newY = playerY + dy;
            if (!collidesWithMap(playerX, newY)) {
                playerY = newY;
            }
        }

        // Update camera to center on player
        int screenW = getWidth();
        int screenH = getHeight();
        camX = playerX - screenW / 2 + TILE_SIZE / 2;
        camY = playerY - screenH / 2 + TILE_SIZE / 2;

        // Clamp camera to map bounds
        int mapPixelW = MAP_COLS * TILE_SIZE;
        int mapPixelH = MAP_ROWS * TILE_SIZE;
        if (camX < 0) camX = 0;
        if (camY < 0) camY = 0;
        if (camX > mapPixelW - screenW) camX = mapPixelW - screenW;
        if (camY > mapPixelH - screenH) camY = mapPixelH - screenH;
    }

    // --- Collision Detection ---
    // Checks all 4 corners of the player rectangle against the map
    private boolean collidesWithMap(int px, int py) {
        // Player is TILE_SIZE x TILE_SIZE, shrink hitbox by 1px on each side
        int left   = px + 1;
        int right  = px + TILE_SIZE - 2;
        int top    = py + 1;
        int bottom = py + TILE_SIZE - 2;

        return isSolidTile(left,  top)
            || isSolidTile(right, top)
            || isSolidTile(left,  bottom)
            || isSolidTile(right, bottom);
    }

    private boolean isSolidTile(int pixelX, int pixelY) {
        int col = pixelX / TILE_SIZE;
        int row = pixelY / TILE_SIZE;
        // Out of bounds = solid
        if (col < 0 || row < 0 || col >= MAP_COLS || row >= MAP_ROWS) return true;
        return map[row][col] == TILE_WALL;
    }

    // --- Render ---
    protected void paint(Graphics g) {
        int screenW = getWidth();
        int screenH = getHeight();

        // Clear screen
        g.setColor(COLOR_BG);
        g.fillRect(0, 0, screenW, screenH);

        // Draw tiles
        for (int row = 0; row < MAP_ROWS; row++) {
            for (int col = 0; col < MAP_COLS; col++) {
                int screenX = col * TILE_SIZE - camX;
                int screenY = row * TILE_SIZE - camY;

                // Skip tiles off screen
                if (screenX + TILE_SIZE < 0 || screenX > screenW) continue;
                if (screenY + TILE_SIZE < 0 || screenY > screenH) continue;

                if (map[row][col] == TILE_WALL) {
                    g.setColor(COLOR_WALL);
                } else {
                    g.setColor(COLOR_FLOOR);
                }
                g.fillRect(screenX, screenY, TILE_SIZE, TILE_SIZE);

                // Grid lines (optional, remove later)
                g.setColor(0x555555);
                g.drawRect(screenX, screenY, TILE_SIZE, TILE_SIZE);
            }
        }

        // Draw player
        int playerScreenX = playerX - camX;
        int playerScreenY = playerY - camY;
        g.setColor(COLOR_PLAYER);
        g.fillRect(playerScreenX, playerScreenY, TILE_SIZE, TILE_SIZE);
    }

    // --- Input ---
    protected void keyPressed(int keyCode) {
        int action = getGameAction(keyCode);
        if (action == UP    || keyCode == KEY_NUM2) keyUp    = true;
        if (action == DOWN  || keyCode == KEY_NUM8) keyDown  = true;
        if (action == LEFT  || keyCode == KEY_NUM4) keyLeft  = true;
        if (action == RIGHT || keyCode == KEY_NUM6) keyRight = true;
    }

    protected void keyReleased(int keyCode) {
        int action = getGameAction(keyCode);
        if (action == UP    || keyCode == KEY_NUM2) keyUp    = false;
        if (action == DOWN  || keyCode == KEY_NUM8) keyDown  = false;
        if (action == LEFT  || keyCode == KEY_NUM4) keyLeft  = false;
        if (action == RIGHT || keyCode == KEY_NUM6) keyRight = false;
    }
}