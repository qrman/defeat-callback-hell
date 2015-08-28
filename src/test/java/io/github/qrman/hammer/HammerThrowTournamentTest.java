package io.github.qrman.hammer;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.unit.Async;
import io.vertx.ext.unit.TestContext;
import io.vertx.ext.unit.junit.RunTestOnContext;
import io.vertx.ext.unit.junit.VertxUnitRunner;
import io.vertx.redis.RedisClient;
import java.io.IOException;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import redis.embedded.RedisServer;

@Log
@RunWith(VertxUnitRunner.class)
public class HammerThrowTournamentTest {

    @Rule
    public RunTestOnContext rule = new RunTestOnContext();

    private RedisServer redisServer;
    private HammerThrowTournament hammerThrowTournament;

    @Before
    public void initVerticle(TestContext context) throws IOException {
        redisServer = new RedisServer(7379);
        redisServer.start();

        hammerThrowTournament = new HammerThrowTournament(RedisClient.create(rule.vertx(), new JsonObject()
          .put("host", "127.0.0.1")
          .put("port", 7379)
          .put("encoding", "UTF-8")));
    }

    @Test
    public void will_register_player(TestContext context) {
        Async async = context.async();

        hammerThrowTournament.addTournamentPlayer(new Player(1000, "Anita Wlodarczyk"))
          .thenCompose(v -> hammerThrowTournament.addTournamentPlayer(new Player(1001, "Zhang Wenxiu")))
          .thenCompose(v -> hammerThrowTournament.addTournamentPlayer(new Player(1002, "Alexandra Tavernier")))
          .thenCompose(v -> hammerThrowTournament.registerdPlayer())
          .thenAccept(numberOfRegisteredPlayer -> {
              context.assertEquals(numberOfRegisteredPlayer, 3, "All Player were registerd");
              async.complete();
          });
    }

    @Test
    public void will_register_player_throw() {
        //TODO
    }

    @After
    public void after() {
        redisServer.stop();
    }
}
