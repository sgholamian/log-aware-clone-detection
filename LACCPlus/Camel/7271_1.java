//,temp,VertxPlatformHttpServer.java,242,278,temp,VertxPlatformHttpServer.java,202,240
//,3
public class xxx {
    protected void stopVertx() {
        if (this.vertx == null || this.localVertx) {
            return;
        }

        try {
            CompletableFuture.runAsync(
                    () -> {
                        CountDownLatch latch = new CountDownLatch(1);

                        vertx.close(result -> {
                            try {
                                if (result.failed()) {
                                    LOGGER.warn("Failed to close Vert.x reason: {}",
                                            result.cause().getMessage());

                                    throw new RuntimeException(result.cause());
                                }

                                LOGGER.info("Vert.x stopped");
                            } finally {
                                latch.countDown();
                            }
                        });

                        try {
                            latch.await();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    executor).toCompletableFuture().join();
        } finally {
            this.vertx = null;
            this.localVertx = false;
        }
    }

};