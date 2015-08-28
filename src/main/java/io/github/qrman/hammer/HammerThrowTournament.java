package io.github.qrman.hammer;

import com.google.inject.Inject;
import io.vertx.redis.RedisClient;
import java.util.Collections;
import java.util.List;

public class HammerThrowTournament {
    
    private final RedisClient redisClient;
    
    @Inject
    public HammerThrowTournament(RedisClient redisClient) {
        this.redisClient = redisClient;
    }
    
    
    public void addTournamentPlayer(Player p) {
        
    }

    List<Player> registerdPlayer() {
        return Collections.EMPTY_LIST;
    }
    
}
