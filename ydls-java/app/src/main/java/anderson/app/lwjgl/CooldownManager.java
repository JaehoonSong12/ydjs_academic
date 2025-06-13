package anderson.app.lwjgl;

/**
 * Manages the cooldown state for dash actions, allowing the cooldown to
 * decay over time using externally provided delta time (dt) values.
 */
public class CooldownManager {
    private static final float MAX_COOLDOWN = 3.0f; // Changed to 3.0f to match game requirements
    private float dashCooldown = MAX_COOLDOWN;

    /**
     * Updates the dash cooldown based on the elapsed time.
     *
     * @param deltaTime Time elapsed since the last update (in seconds).
     */
    public void update(float deltaTime) {
        if (dashCooldown > 0) {
            dashCooldown = Math.max(0, dashCooldown - deltaTime);
        }
    }

    /**
     * Resets the dash cooldown to its maximum value.
     */
    public void reset() {
        dashCooldown = MAX_COOLDOWN;
    }

    /**
     * Checks if the cooldown has finished.
     *
     * @return true if the cooldown is over, false otherwise.
     */
    public boolean isReady() {
        return dashCooldown == 0;
    }

    /**
     * Gets the current remaining dash cooldown.
     *
     * @return Remaining cooldown time in seconds.
     */
    public float getRemainingCooldown() {
        return dashCooldown;
    }
} 