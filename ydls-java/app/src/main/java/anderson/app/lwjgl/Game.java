// src/main/java/com/example/Game.java
package anderson.app.lwjgl;







import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import static org.lwjgl.opengl.GL11.*;
import java.util.*;

public class Game {
    private long window;
    private final int width = 800, height = 600;

    // world & camera
    private final float worldWidth = width * 3f, worldHeight = height;
    private Camera camera;
    private Player player;
    private List<Platform> platforms;
    private List<Star> stars;
    private List<Mob> mobs;

    // UI
    private int lives = 3;
    private int score = 0;

    // controls mapping
    private Map<String, Integer> keyMap;
    private final String[] possibleKeys = {"Q","A","W","S","E","D"};

    public void run() {
        init();
        loop();
        cleanup();
    }

    private void init() {
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) throw new IllegalStateException("Unable to init GLFW");
        GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GLFW.GLFW_FALSE);
        window = GLFW.glfwCreateWindow(width, height, "Platformer", 0, 0);
        GLFW.glfwMakeContextCurrent(window);
        GLFW.glfwShowWindow(window);
        GL.createCapabilities();
        glClearColor(0.5f, 0.8f, 1f, 0);

        // setup world
        camera = new Camera(width, height, worldWidth);
        randomizeControls();
        player = new Player(100, 50);
        generatePlatforms();
        spawnStars(5);
        mobs = new ArrayList<>();
    }

    private void randomizeControls() {
        List<Character> keys = new ArrayList<>(Arrays.asList('W','A','D','S','Q','E'));
        // Collections.shuffle(keys);
        keyMap = new HashMap<>();
        keyMap.put("UP",    getKeyCode(keys.get(0)));
        keyMap.put("LEFT",  getKeyCode(keys.get(1)));
        keyMap.put("RIGHT", getKeyCode(keys.get(2)));
        keyMap.put("DOWN",  getKeyCode(keys.get(3)));
    }

    // Map character to GLFW key code
    private int getKeyCode(char c) {
        switch (c) {
            case 'Q': return GLFW.GLFW_KEY_Q;
            case 'A': return GLFW.GLFW_KEY_A;
            case 'W': return GLFW.GLFW_KEY_W;
            case 'S': return GLFW.GLFW_KEY_S;
            case 'E': return GLFW.GLFW_KEY_E;
            case 'D': return GLFW.GLFW_KEY_D;
            default:  return GLFW.GLFW_KEY_SPACE;
        }
    }

    private void generatePlatforms() {
        platforms = new ArrayList<>();
        // ground
        platforms.add(new Platform(0, 0, worldWidth, 20));
        // floating
        platforms.add(new Platform(400, 90, 200, 20));
        platforms.add(new Platform(800, 140, 150, 20));
        platforms.add(new Platform(1400,  290, 200, 20));
        platforms.add(new Platform(2000, 190, 200, 20));
    }

    private void spawnStars(int count) {
        stars = new ArrayList<>();
        Random rnd = new Random();
        for (int i = 0; i < count; i++) {
            float x = 50 + rnd.nextFloat() * (worldWidth - 100);
            float y = 50 + rnd.nextFloat() * (worldHeight - 200);
            stars.add(new Star(x, y));
        }
    }

    private void loop() {
        double last = GLFW.glfwGetTime();
        while (!GLFW.glfwWindowShouldClose(window)) {
            double now = GLFW.glfwGetTime();
            float delta = (float)(now - last);
            last = now;

            update(delta);
            render();
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

    private void update(float dt) {
        
        // input
        if (GLFW.glfwGetKey(window, keyMap.get("LEFT")) == GLFW.GLFW_PRESS) player.moveHoriz(-1);
        if (GLFW.glfwGetKey(window, keyMap.get("RIGHT")) == GLFW.GLFW_PRESS) player.moveHoriz(1);
        if (GLFW.glfwGetKey(window, keyMap.get("UP")) == GLFW.GLFW_PRESS) player.chargejump();
        if (GLFW.glfwGetKey(window, keyMap.get("UP")) == GLFW.GLFW_RELEASE) player.jump();
        if (GLFW.glfwGetKey(window, keyMap.get("DOWN")) == GLFW.GLFW_PRESS) player.fall();
        

        player.applyGravity(dt);
        player.update(dt, platforms);
        camera.follow(player);

        // collect stars
        stars.removeIf(s -> {
            if (player.collides(s)) {
                score++;
                return true;
            }
            return false;
        });
        if (stars.isEmpty()) {
            spawnStars(5);
            randomizeControls();
        }

        // mobs update (simple spawning omitted)
        for (Mob m : mobs) m.update(dt);
        mobs.removeIf(m -> { if (player.collides(m)) { lives--; return true; } return false; });
    }

    private void render() {
        glClear(GL_COLOR_BUFFER_BIT);
        camera.begin();

        // draw platforms
        for (Platform p : platforms) p.render();
        // draw stars
        for (Star s : stars) s.render();
        // draw player
        player.render();
        // draw mobs
        for (Mob m : mobs) m.render();

        camera.end();

        // UI overlay
        renderUI();
    }

    private void renderUI() {
        // Simple UI: draw lives as red squares, score as quads count
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(0, width, 0, height, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();

        // Lives
        glColor3f(1,0,0);
        for (int i = 0; i < lives; i++) {
            glBegin(GL_QUADS);
            glVertex2f(10 + i*25, height - 10);
            glVertex2f(25 + i*25, height - 10);
            glVertex2f(25 + i*25, height - 25);
            glVertex2f(10 + i*25, height - 25);
            glEnd();
        }
        // Score indicator
        glColor3f(1,1,0);
        for (int i = 0; i < score; i++) {
            glBegin(GL_QUADS);
            glVertex2f(width/2 - 10 + i*15, height - 10);
            glVertex2f(width/2 + 10 + i*15, height - 10);
            glVertex2f(width/2 + 10 + i*15, height - 25);
            glVertex2f(width/2 - 10 + i*15, height - 25);
            glEnd();
        }
        // Controls under player
        // Could draw small quads below player, omitted for brevity
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
    private final int screenW, screenH;
    private final float worldW;
    private float x;
    public Camera(int w, int h, float worldW) { screenW=w; screenH=h; this.worldW=worldW; }
    public void follow(Player p) {
        x = p.x - screenW/2f;
        if (x<0) x=0;
        if (x>worldW-screenW) x=worldW-screenW;
    }
    public void begin() {
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(x, x+screenW, 0, screenH, -1, 1);
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }
    public void end() { /* nothing */ }
}

// Simple player with gravity and collision
class Player {
    public float x, y;
    private float vx, vy;
    private final float speed = 200, jumpSpeed = 400, higherJumpAdditionalSpeed = 300, additionalJumpSpeed = 400;
    private boolean onGround;
    private boolean isHigherJump;
    private int counter;
    private int additionalJumpinfo = 0;
    private int allowedJump = 2;
    private int numberOfJump;

    // For additional jump oval rendering
    private boolean showAdditionalJumpOval = false;
    private float jumpOvalTimer = 0f;  // Timer for how long to show the oval
    private final float jumpOvalDuration = 0.3f; // duration to show oval in seconds

    public Player(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void moveHoriz(int dir) {
        vx = dir * speed;
    }

    public void chargejump() {
        if (!onGround && numberOfJump < allowedJump) {
            additionalJumpinfo++;
        }
        if (onGround) {
            counter = counter + 5;
            additionalJumpinfo = 0;
        }
        if (counter >= higherJumpAdditionalSpeed) {
            counter = 300;
            jump();
        }
        if (additionalJumpinfo >= 20) {
            vy = additionalJumpSpeed;
            additionalJumpinfo = 5;
            numberOfJump++;

            // Show oval only when additional jump occurs
            showAdditionalJumpOval = true;
            jumpOvalTimer = jumpOvalDuration;
        }
    }

    public void jump() {
        if (counter > 5 && onGround) { // normal jump only on ground
            vy = jumpSpeed + counter;
            counter = 0;
        }
    }

    public void fall() {
        if (!onGround) {
            vy -= speed;
            y = y - 20;
        }
    }

    public void applyGravity(float dt) {
        vy -= 980 * dt;
    }

    public void update(float dt, List<Platform> plats) {
        x += vx * dt;
        y += vy * dt;
        onGround = false;

        for (Platform p : plats) {
            if (x + 20 > p.x && x < p.x + p.w && y <= p.y + p.h && y >= p.y) {
                y = p.y + p.h;
                vy = 0;
                onGround = true;
                additionalJumpinfo = 0;
                numberOfJump = 0;
            }
        }

        // Countdown timer for additional jump oval
        if (showAdditionalJumpOval) {
            jumpOvalTimer -= dt;
            if (jumpOvalTimer <= 0) {
                showAdditionalJumpOval = false;
                jumpOvalTimer = 0;
            }
        }

        if (y < 20) {
            y = 0;
            onGround = true;
            vy = 0;
        }

        vx = 0;
        if (y<20) { y=0; onGround=true; vy=0; }
        System.out.println("Player{x=" + x + 
        ", y=" + y + ", vx=" + vx + ", vy=" + vy + 
        ", onGround=" + onGround + 
        ", counter=" + counter + ", additionalJumpinfo=" + additionalJumpinfo +
        ", numberOfJump=" + numberOfJump +
        ", showJumpOval=" + showJumpOval +
        "}");
    }

    public boolean collides(Star s) {
        return Math.hypot((x + 10) - s.x, (y + 10) - s.y) < 15;
    }

    public boolean collides(Mob m) {
        return x < m.x + m.size && x + 20 > m.x && y < m.y + m.size && y + 20 > m.y;
    }

    public void renderJumpOval() {
        if (!showAdditionalJumpOval) return;

        glPushMatrix();
        glTranslatef(x + 10, y - 5, 0); // position oval just under player center

        glColor4f(1f, 1f, 1f, 0.5f); // semi-transparent white

        int segments = 40;
        float radiusX = 15f;
        float radiusY = 7f;

        glBegin(GL_TRIANGLE_FAN);
        glVertex2f(0, 0);
        for (int i = 0; i <= segments; i++) {
            double angle = 2 * Math.PI * i / segments;
            float dx = (float) Math.cos(angle) * radiusX;
            float dy = (float) Math.sin(angle) * radiusY;
            glVertex2f(dx, dy);
        }
        glEnd();

        glPopMatrix();
    }

    public void render() {
        renderJumpOval(); // draw oval if needed

        if (counter == 0) {
            glColor3f(0, 0, 1); // Blue when on ground
        } else {
            float red = Math.min(1.0f, counter / 300f); // Scale red based on charge
            glColor3f(red, 0, 1 - red); // Transition from blue to red
        }

        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x + 20, y);
        glVertex2f(x + 20, y + 20);
        glVertex2f(x, y + 20);
        glEnd();
    }
}



// Static platforms
class Platform {
    public float x, y, w, h;
    public Platform(float x, float y, float w, float h) { this.x=x; this.y=y; this.w=w; this.h=h; }
    public void render() {
        glColor3f(0.3f,0.3f,0.3f);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x+w, y);
        glVertex2f(x+w, y+h);
        glVertex2f(x, y+h);
        glEnd();
    }
}

// Collectible stars
class Star {
    public float x, y;
    public Star(float x, float y) { this.x=x; this.y=y; }
    public void render() {
        glColor3f(1,1,0);
        glBegin(GL_TRIANGLES);
        glVertex2f(x, y);
        glVertex2f(x+10, y);
        glVertex2f(x+5, y+15);
        glEnd();
    }
}

// Simple mob stub
class Mob {
    public float x, y; public float size=20;
    public Mob(float x, float y) { this.x=x; this.y=y; }
    public void update(float dt) { /* random or patterned movement */ }
    public void render() {
        glColor3f(1,0,1);
        glBegin(GL_QUADS);
        glVertex2f(x, y);
        glVertex2f(x+size, y);
        glVertex2f(x+size, y+size);
        glVertex2f(x, y+size);
        glEnd();
    }
}