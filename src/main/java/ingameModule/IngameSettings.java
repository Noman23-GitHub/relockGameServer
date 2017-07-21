package ingameModule;

import org.springframework.stereotype.Component;

@Component
public class IngameSettings {
    private int playerSpeed;

    public int getPlayerSpeed() {
        return playerSpeed;
    }

    public IngameSettings(int playerSpeed) {
        this.playerSpeed = playerSpeed;

    }
}
