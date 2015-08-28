package io.github.qrman.hammer;

import com.google.inject.Inject;
import io.vertx.core.AsyncResult;
import io.vertx.core.json.JsonArray;
import io.vertx.redis.RedisClient;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class HammerThrowTournament {

    private final RedisClient redisClient;

    @Inject
    public HammerThrowTournament(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    public CompletableFuture<Void> addTournamentPlayer(Player p) {
        CompletableFuture<Void> promise = new CompletableFuture<>();
        Map<String, String> hmset = new HashMap<>();
        hmset.put("name", p.getName());
        redisClient.hmset("PLAYERS:" + p.getId(), hmset, (AsyncResult<String> event) -> {
            promise.complete(null);
        });

        return promise;
    }

    public CompletableFuture<Integer> registerdPlayer() {
        CompletableFuture<Integer> promise = new CompletableFuture<>();
        redisClient.keys("PLAYERS:*", (AsyncResult<JsonArray> hander) -> {
            promise.complete(hander.result().size());
        });
        return promise;
    }
}
