// src/main/java/com/example/Game.java
package anderson.app.lwjgl;

import java.net.URL;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;
import org.lwjgl.system.MemoryStack;

import java.util.*;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.io.File;

public class Game {
    private static final int WINDOW_WIDTH = 800;
    private static final int WINDOW_HEIGHT = 600;
    static final float WORLD_WIDTH = WINDOW_WIDTH * 3f;
    static final float WORLD_HEIGHT = WINDOW_HEIGHT;
    private static final String[] POSSIBLE_KEYS = {"Q", "A", "W", "S", "E", "D"};
    private static final int MAX_LIVES = 3;
    private static final int INITIAL_SCORE = 0;
    private static final int STAR_COUNT = 5;

    private final long window;
    private final boolean enableShuffling;
    private int[] digitTextures;
    private final Camera camera;
    private final Player player;
    private final List<Platform> platforms;
    private List<Star> stars;
    private final List<Mob> mobs;
    private int lives;
    private int score;
    private Map<String, Integer> keyMap;

    public Game() {
        this.window = initializeWindow();
        this.enableShuffling = false;
        this.camera = new Camera(WINDOW_WIDTH, WINDOW_HEIGHT, WORLD_WIDTH);
        this.player = new Player(100, 50);
        this.platforms = new ArrayList<>();
        this.stars = new ArrayList<>();
        this.mobs = new ArrayList<>();
        this.lives = MAX_LIVES;
        this.score = INITIAL_SCORE;
        initializeGame();
    }

    private long initializeWindow() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        long window = GLFW.glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Platformer", 0, 0);
        if (window == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }
        
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();
        glClearColor(0.5f, 0.8f, 1f, 0);
        
        return window;
    }

    private void initializeGame() {
        randomizeControls();
        generatePlatforms();
        spawnStars(STAR_COUNT);
    }

    private void randomizeControls() {
        List<Character> keys = new ArrayList<>(Arrays.asList('W', 'A', 'D', 'S', 'Q', 'E'));
        if (enableShuffling) {
            Collections.shuffle(keys);
        }
        
        keyMap = new HashMap<>();
        keyMap.put("UP", getKeyCode(keys.get(0)));
        keyMap.put("LEFT", getKeyCode(keys.get(1)));
        keyMap.put("RIGHT", getKeyCode(keys.get(2)));
        keyMap.put("DOWN", getKeyCode(keys.get(3)));
        keyMap.put("CHARACTER_SKILL", getKeyCode(keys.get(4)));
        
        printKeyMapping(keys);
    }

    private void printKeyMapping(List<Character> keys) {
        System.out.println("Key Mapping:");
        System.out.println("UP = " + keys.get(0));
        System.out.println("LEFT = " + keys.get(1));
        System.out.println("RIGHT = " + keys.get(2));
        System.out.println("DOWN = " + keys.get(3));
        System.out.println("CHARACTER_SKILL = " + keys.get(4));
    }

    private int getKeyCode(char c) {
        return switch (c) {
            case 'Q' -> GLFW.GLFW_KEY_Q;
            case 'A' -> GLFW.GLFW_KEY_A;
            case 'W' -> GLFW.GLFW_KEY_W;
            case 'S' -> GLFW.GLFW_KEY_S;
            case 'E' -> GLFW.GLFW_KEY_E;
            case 'D' -> GLFW.GLFW_KEY_D;
            default -> GLFW.GLFW_KEY_SPACE;
        };
    }

    private void generatePlatforms() {
        platforms.clear();
        // Ground
        platforms.add(new Platform(0, 0, WORLD_WIDTH, 20));
        // Floating platforms
        platforms.add(new Platform(400, 90, 200, 20));
        platforms.add(new Platform(800, 140, 150, 20));
        platforms.add(new Platform(1400, 290, 200, 20));
        platforms.add(new Platform(2000, 190, 200, 20));
    }

    private void spawnStars(int count) {
        stars = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < count; i++) {
            float x = 50 + rnd.nextFloat() * (WORLD_WIDTH - 100);
            float y = 50 + rnd.nextFloat() * (WORLD_HEIGHT - 200);
            stars.add(new Star(x, y));
        }
    }

    public void run() {
        gameLoop();
        cleanup();
    }

    private void gameLoop() {
        double lastTime = GLFW.glfwGetTime();
        while (!GLFW.glfwWindowShouldClose(window)) {
            double currentTime = GLFW.glfwGetTime();
            float deltaTime = (float) (currentTime - lastTime);
            lastTime = currentTime;

            update(deltaTime);
            render();
            
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

    private void update(float deltaTime) {
        handleInput();
        updateGameState(deltaTime);
        checkCollisions();
    }

    private void handleInput() {
        if (GLFW.glfwGetKey(window, keyMap.get("LEFT")) == GLFW.GLFW_PRESS) {
            player.moveHoriz(-1, false);
        }
        if (GLFW.glfwGetKey(window, keyMap.get("RIGHT")) == GLFW.GLFW_PRESS) {
            player.moveHoriz(1, true);
        }
        if (GLFW.glfwGetKey(window, keyMap.get("CHARACTER_SKILL")) == GLFW.GLFW_PRESS) {
            player.dash();
        }
        if (GLFW.glfwGetKey(window, keyMap.get("UP")) == GLFW.GLFW_PRESS) {
            player.jCharge();
        }
        if (GLFW.glfwGetKey(window, keyMap.get("UP")) == GLFW.GLFW_RELEASE) {
            player.jump();
        }
        if (GLFW.glfwGetKey(window, keyMap.get("DOWN")) == GLFW.GLFW_PRESS) {
            player.fall();
        }
    }

    private void updateGameState(float deltaTime) {
        player.applyGravity(deltaTime);
        player.update(deltaTime, platforms);
        camera.follow(player);
        
        for (Mob mob : mobs) {
            mob.update(deltaTime);
        }
    }

    private void checkCollisions() {
        // Collect stars
        stars.removeIf(star -> {
            if (player.collides(star)) {
                score++;
                return true;
            }
            return false;
        });

        if (stars.isEmpty()) {
            spawnStars(STAR_COUNT);
            randomizeControls();
        }

        // Check mob collisions
        mobs.removeIf(mob -> {
            if (player.collides(mob)) {
                lives--;
                return true;
            }
            return false;
        });
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        camera.begin();

        // Render game objects
        platforms.forEach(Platform::render);
        stars.forEach(Star::render);
        player.render();
        mobs.forEach(Mob::render);

        camera.end();
        renderUI();
    }

    private void renderUI() {
        setupUIProjection();
        renderLives();
        renderScore();
    }

    private void setupUIProjection() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, WINDOW_WIDTH, 0, WINDOW_HEIGHT, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    private void renderLives() {
        glColor3f(1, 0, 0);
        for (int i = 0; i < lives; i++) {
            glBegin(GL_QUADS);
            glVertex2f(10 + i * 25, WINDOW_HEIGHT - 10);
            glVertex2f(25 + i * 25, WINDOW_HEIGHT - 10);
            glVertex2f(25 + i * 25, WINDOW_HEIGHT - 25);
            glVertex2f(10 + i * 25, WINDOW_HEIGHT - 25);
            glEnd();
        }
    }

    private void renderScore() {
        if (digitTextures == null) {
            initializeDigitTextures();
        }

        String scoreStr = Integer.toString(score);
        float startX = WINDOW_WIDTH / 2f - (scoreStr.length() * 20) / 2f;
        float startY = WINDOW_HEIGHT - 30;

        renderScoreBackground(startX, startY, scoreStr.length());
        renderScoreDigits(scoreStr, startX, startY);
    }

    private void initializeDigitTextures() {
        digitTextures = new int[10];
        for (int i = 0; i < 10; i++) {
            String path = "/anderson/number_" + i + ".png";
            digitTextures[i] = loadTexture(path);
        }
    }

    private void renderScoreBackground(float startX, float startY, int scoreLength) {
        glColor3f(0.5f, 0.8f, 1f);
        glBegin(GL_QUADS);
        glVertex2f(startX - 10, startY - 5);
        glVertex2f(startX + scoreLength * 20 + 10, startY - 5);
        glVertex2f(startX + scoreLength * 20 + 10, startY + 25);
        glVertex2f(startX - 10, startY - 5);
        glEnd();
    }

    private void renderScoreDigits(String scoreStr, float startX, float startY) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_TEXTURE_2D);

        float currentX = startX;
        for (char c : scoreStr.toCharArray()) {
            int digit = c - '0';
            if (digit >= 0 && digit <= 9) {
                renderDigit(digit, currentX, startY);
            }
            currentX += 20;
        }

        glBindTexture(GL_TEXTURE_2D, 0);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
    }

    private void renderDigit(int digit, float x, float y) {
        glBindTexture(GL_TEXTURE_2D, digitTextures[digit]);
        glColor4f(1, 1, 1, 1);
        glBegin(GL_QUADS);
        glTexCoord2f(0, 0); glVertex2f(x, y);
        glTexCoord2f(1, 0); glVertex2f(x + 20, y);
        glTexCoord2f(1, 1); glVertex2f(x + 20, y + 20);
        glTexCoord2f(0, 1); glVertex2f(x, y + 20);
        glEnd();
    }

    private int loadTexture(String resourcePath) {
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            InputStream in = getClass().getResourceAsStream(resourcePath);
            if (in == null) {
                throw new RuntimeException("Image not found: " + resourcePath);
            }

            File tempFile = File.createTempFile("texture", ".png");
            tempFile.deleteOnExit();
            Files.copy(in, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            stbi_set_flip_vertically_on_load(true);
            ByteBuffer image = stbi_load(tempFile.getAbsolutePath(), width, height, channels, 4);
            if (image == null) {
                throw new RuntimeException("Failed to load texture: " + stbi_failure_reason());
            }

            int textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);
            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0, GL_RGBA, GL_UNSIGNED_BYTE, image);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

            stbi_image_free(image);
            return textureID;
        } catch (Exception e) {
            throw new RuntimeException("Error loading texture: " + resourcePath, e);
        }
    }

    private void cleanup() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }

    public static void main(String[] args) {
        new Game().run();
    }
}

// Camera follows the player
class Camera {
    private static final float NEAR_PLANE = -1f;
    private static final float FAR_PLANE = 1f;

    private final int screenWidth;
    private final int screenHeight;
    private final float worldWidth;
    private float x;

    public Camera(int width, int height, float worldWidth) {
        this.screenWidth = width;
        this.screenHeight = height;
        this.worldWidth = worldWidth;
        this.x = 0f;
    }

    public void follow(Player player) {
        x = player.getX() - screenWidth / 2f;
        x = Math.max(0, Math.min(x, worldWidth - screenWidth));
    }

    public void begin() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(x, x + screenWidth, 0, screenHeight, NEAR_PLANE, FAR_PLANE);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public void end() {
        // Nothing to do here
    }
}

// Simple player with gravity and collision
class Player {
    private static final float SPEED = 200f;
    private static final float JUMP_SPEED = 400f;
    private static final float HIGHER_JUMP_ADDITIONAL_SPEED = 300f;
    private static final float ADDITIONAL_JUMP_SPEED = 400f;
    private static final int MAX_ADDITIONAL_JUMP = 2;
    private static final int MAX_COUNTER = 300;
    private static final int COUNTER_INCREMENT = 10;
    private static final float DASH_COOLDOWN = 3.0f;
    private static final float DASH_DURATION = 0.2f;
    private static final float DASH_DISTANCE = 200f;
    private static final float JUMP_OVAL_DURATION = 0.3f;
    private static final float JUMP_OVAL_RADIUS_X = 15f;
    private static final float JUMP_OVAL_RADIUS_Y = 7f;
    private static final int JUMP_OVAL_SEGMENTS = 40;

    private float x, y;
    private float vx, vy;
    private boolean onGround;
    private int counter;
    private boolean enableAdditionalJump;
    private boolean dashLeftOrRight;
    private float dashCooldown;
    private int numberOfJump;
    private boolean showAdditionalJumpOval;
    private float jumpOvalTimer;
    private float jumpOvalX, jumpOvalY;
    private float jumpOvalAlpha;
    private final DashTriangle dashTriangle;
    private boolean isDashing;
    private float dashTimer;

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
        this.dashTriangle = new DashTriangle(x, y);
        this.dashCooldown = 0f;
        this.dashTimer = 0f;
        this.isDashing = false;
        this.showAdditionalJumpOval = false;
        this.jumpOvalTimer = 0f;
        this.jumpOvalAlpha = 0f;
        this.counter = 0;
        this.numberOfJump = 0;
        this.enableAdditionalJump = false;
    }

    public void moveHoriz(int dir, boolean leftOrRight) {
        vx = dir * SPEED;
        dashLeftOrRight = leftOrRight;
    }

    public void dash() {
        if (dashCooldown <= 0f) {
            isDashing = true;
            dashTimer = DASH_DURATION;
            x += dashLeftOrRight ? DASH_DISTANCE : -DASH_DISTANCE;
            dashCooldown = DASH_COOLDOWN;
        }
    }

    public void jCharge() {
        enableAdditionalJump = (!onGround && numberOfJump < MAX_ADDITIONAL_JUMP);

        if (onGround && counter < MAX_COUNTER) {
            counter += COUNTER_INCREMENT;
        }
        if (counter >= HIGHER_JUMP_ADDITIONAL_SPEED) {
            counter = MAX_COUNTER;
        }
    }

    public void jump() {
        if (counter > COUNTER_INCREMENT && onGround) {
            vy = JUMP_SPEED + counter;
            counter = 0;
        }
        if (enableAdditionalJump && !onGround) {
            vy = ADDITIONAL_JUMP_SPEED;
            enableAdditionalJump = false;
            numberOfJump++;
            showAdditionalJumpOval();
        }
    }

    private void showAdditionalJumpOval() {
        showAdditionalJumpOval = true;
        jumpOvalTimer = JUMP_OVAL_DURATION;
        jumpOvalX = x + 10;
        jumpOvalY = y - 5;
        jumpOvalAlpha = 0.5f;
    }

    public void fall() {
        y -= 20;
    }

    public void applyGravity(float dt) {
        vy -= 980 * dt;
    }

    public void update(float dt, List<Platform> platforms) {
        updatePosition(dt);
        updateDash(dt);
        updateJumpOval(dt);
        checkPlatformCollisions(platforms);
        resetVelocity();
    }

    private void updatePosition(float dt) {
        x += vx * dt;
        y += vy * dt;
        onGround = false;

        // World boundaries
        x = Math.max(0, Math.min(x, Game.WORLD_WIDTH - 20));
        y = Math.max(20, Math.min(y, Game.WORLD_HEIGHT - 20));

        if (y <= 20) {
            y = 20;
            onGround = true;
            vy = 0;
        }
    }

    private void updateDash(float dt) {
        if (isDashing) {
            dashTimer -= dt;
            if (dashTimer <= 0) {
                isDashing = false;
            }
            dashTriangle.update(x, y, dashLeftOrRight);
        }

        if (dashCooldown > 0f) {
            dashCooldown -= dt;
            if (dashCooldown < 0f) {
                dashCooldown = 0f;
            }
        }
    }

    private void updateJumpOval(float dt) {
        if (showAdditionalJumpOval) {
            jumpOvalTimer += dt;
            updateJumpOvalAlpha();
        }
    }

    private void updateJumpOvalAlpha() {
        if (jumpOvalTimer <= 0.2f) {
            jumpOvalAlpha = jumpOvalTimer / 0.2f;
        } else if (jumpOvalTimer <= 1.0f) {
            jumpOvalAlpha = 1f - (jumpOvalTimer - 0.2f) / 0.8f;
        } else {
            showAdditionalJumpOval = false;
            jumpOvalAlpha = 0f;
            jumpOvalTimer = 0f;
        }
    }

    private void checkPlatformCollisions(List<Platform> platforms) {
        for (Platform platform : platforms) {
            if (isOnPlatform(platform)) {
                handlePlatformCollision(platform);
            }
        }
    }

    private boolean isOnPlatform(Platform platform) {
        return platform.getX() - 20 < x && x < platform.getX() + platform.getWidth() &&
               platform.getY() + platform.getHeight() - 5 < y && y < platform.getY() + platform.getHeight() + 5;
    }

    private void handlePlatformCollision(Platform platform) {
        if (vy < 0) {
            y = platform.getY() + platform.getHeight();
            onGround = true;
            numberOfJump = 0;
            vy = 0;
        }
    }

    private void resetVelocity() {
        vx = 0;
    }

    public boolean collides(Star star) {
        if (isDashing && dashTriangle.collides(star)) {
            return true;
        }
        return Math.hypot((x + 10) - star.getX(), (y + 10) - star.getY()) < 15;
    }

    public boolean collides(Mob mob) {
        return x < mob.getX() + mob.getSize() && x + 20 > mob.getX() && 
               y < mob.getY() + mob.getSize() && y + 20 > mob.getY();
    }

    public void render() {
        renderJumpOval();
        renderDashTriangle();
        renderPlayer();
    }

    private void renderJumpOval() {
        if (!showAdditionalJumpOval || jumpOvalAlpha <= 0f) return;

        glPushMatrix();
        glTranslatef(jumpOvalX, jumpOvalY, 0);
        glColor4f(1f, 1f, 1f, jumpOvalAlpha);

        glBegin(GL_TRIANGLE_FAN);
        glVertex2f(0, 0);
        for (int i = 0; i <= JUMP_OVAL_SEGMENTS; i++) {
            double angle = 2 * Math.PI * i / JUMP_OVAL_SEGMENTS;
            float dx = (float) Math.cos(angle) * JUMP_OVAL_RADIUS_X;
            float dy = (float) Math.sin(angle) * JUMP_OVAL_RADIUS_Y;
            glVertex2f(dx, dy);
        }
        glEnd();

        glPopMatrix();
    }

    private void renderDashTriangle() {
        if (isDashing) {
            dashTriangle.render();
        }
    }

    private void renderPlayer() {
        if (counter == 0) {
            glColor3f(0, 0, 1);
        } else {
            float red = Math.min(1.0f, counter / 300f);
            glColor3f(red, 0, 1 - red);
        }

        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + 20, y);
        glVertex2f(x + 20, y + 20);
        glVertex2f(x, y + 20);
        glEnd();
    }

    public float getX() {
        return x;
    }
}

// New DashTriangle class
class DashTriangle {
    private static final float SIZE = 15f;
    private float x, y;
    private boolean facingRight;

    public DashTriangle(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void update(float playerX, float playerY, boolean facingRight) {
        this.x = playerX;
        this.y = playerY;
        this.facingRight = facingRight;
    }

    public boolean collides(Star star) {
        float[] vertices = getVertices();
        return pointInTriangle(star.getX() + 5, star.getY() + 7.5f, 
                             vertices[0], vertices[1], 
                             vertices[2], vertices[3], 
                             vertices[4], vertices[5]);
    }

    private float[] getVertices() {
        float[] vertices = new float[6];
        if (facingRight) {
            vertices[0] = x + 20; // tip
            vertices[1] = y + 10;
            vertices[2] = x;      // base left
            vertices[3] = y;
            vertices[4] = x;      // base right
            vertices[5] = y + 20;
        } else {
            vertices[0] = x;      // tip
            vertices[1] = y + 10;
            vertices[2] = x + 20; // base left
            vertices[3] = y;
            vertices[4] = x + 20; // base right
            vertices[5] = y + 20;
        }
        return vertices;
    }

    private boolean pointInTriangle(float px, float py, 
                                  float x1, float y1, 
                                  float x2, float y2, 
                                  float x3, float y3) {
        float A = 0.5f * (-y2 * x3 + y1 * (-x2 + x3) + x1 * (y2 - y3) + x2 * y3);
        float sign = A < 0 ? -1 : 1;
        float s = (y1 * x3 - x1 * y3 + (y3 - y1) * px + (x1 - x3) * py) * sign;
        float t = (x1 * y2 - y1 * x2 + (y1 - y2) * px + (x2 - x1) * py) * sign;
        return s > 0 && t > 0 && (s + t) < 2 * A * sign;
    }

    public void render() {
        glColor3f(1, 0.5f, 0);
        float[] vertices = getVertices();
        glBegin(GL_TRIANGLES);
        glVertex2f(vertices[0], vertices[1]);
        glVertex2f(vertices[2], vertices[3]);
        glVertex2f(vertices[4], vertices[5]);
        glEnd();
    }
}

// Static platforms
class Platform {
    private static final float DEFAULT_COLOR_R = 0.3f;
    private static final float DEFAULT_COLOR_G = 0.3f;
    private static final float DEFAULT_COLOR_B = 0.3f;

    private final float x;
    private final float y;
    private final float width;
    private final float height;

    public Platform(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public void render() {
        glColor3f(DEFAULT_COLOR_R, DEFAULT_COLOR_G, DEFAULT_COLOR_B);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + width, y);
        glVertex2f(x + width, y + height);
        glVertex2f(x, y + height);
        glEnd();
    }
}

// Collectible stars
class Star {
    private static final float DEFAULT_COLOR_R = 1f;
    private static final float DEFAULT_COLOR_G = 1f;
    private static final float DEFAULT_COLOR_B = 0f;
    private static final float SIZE = 10f;
    private static final float HEIGHT = 15f;

    private final float x;
    private final float y;

    public Star(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void render() {
        glColor3f(DEFAULT_COLOR_R, DEFAULT_COLOR_G, DEFAULT_COLOR_B);
        glBegin(GL_TRIANGLES);
        glVertex2f(x, y);
        glVertex2f(x + SIZE, y);
        glVertex2f(x + SIZE / 2, y + HEIGHT);
        glEnd();
    }
}

// Simple mob stub
class Mob {
    private static final float DEFAULT_COLOR_R = 1f;
    private static final float DEFAULT_COLOR_G = 0f;
    private static final float DEFAULT_COLOR_B = 1f;
    private static final float DEFAULT_SIZE = 20f;

    private float x;
    private float y;
    private final float size;

    public Mob(float x, float y) {
        this.x = x;
        this.y = y;
        this.size = DEFAULT_SIZE;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSize() {
        return size;
    }

    public void update(float deltaTime) {
        // Implement mob movement logic here
    }

    public void render() {
        glColor3f(DEFAULT_COLOR_R, DEFAULT_COLOR_G, DEFAULT_COLOR_B);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + size, y);
        glVertex2f(x + size, y + size);
        glVertex2f(x, y + size);
        glEnd();
    }
}