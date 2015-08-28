package io.github.qrman.hammer;

import com.google.inject.Inject;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.RedisClient;
import java.util.HashMap;
import java.util.Map;

public class HammerThrowTournament {
    
    private final RedisClient redisClient;
    
    @Inject
    public HammerThrowTournament(RedisClient redisClient) {
        this.redisClient = redisClient;
    }
    
    public void addTournamentPlayer(Player p, Handler<AsyncResult<String>> handler) {
        Map<String, String> hmset = new HashMap<>();
        hmset.put("name", p.getName());
        redisClient.hmset("PLAYERS:" + p.getId(), hmset, handler);
    }

    public void registerdPlayer(Handler<AsyncResult<JsonArray>> resultHandler) {
        redisClient.keys("PLAYERS:*", resultHandler);
    }
}
